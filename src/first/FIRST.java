package first;

import javafx.application.*;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import module.CostCenterModule;
import module.FAModule;
import module.FundModule;
import module.GLModule;
import module.ProjectModule;
import module.QueryModule;
import module.RUNModule;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JOptionPane;

import controler.*;
import element.Notification;
import form.*;

public class FIRST  extends Application {
	
	public Stage console;
	private RUNModule runMod;
	private CostCenterModule ccMod;
	private QueryModule queryMod;
	private ProjectModule projectMod;
	private Admin adminMod;
	private Notificator notificator;
	private VBox notbox;
	private AnchorPane mainAnchor;
	private About about;
	public FIRST me = this;
	private DataLayer manager;
	private FundModule fundMod;
	private FAModule faMod;
	private GLModule glMod;
	private RefreshService refreshService;
	
	/**
	 * Loads all objects before start()
	 */
	@Override
	public void init() throws Exception {

		// Loading Admin module
		adminMod = new Admin(this);

		// Loading the data layer
		try {
			manager = new DataLayer();
			manager.connect();
			manager.load();
			//manager.getRefreshService().start();
		} catch (controler.DatabaseCommunicationsException e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "FIRST can't connect to its database... Contact NC-ART-GD@hrsdc-rhdcc.gc.ca \n"
					+ "for support");
			this.stop();
			System.exit(1);
		}
				
		refreshService = new RefreshService(this);
		refreshService.start();

		// Modules
		runMod = new RUNModule(this);
		ccMod = new CostCenterModule(this);
		queryMod = new QueryModule(this);
		projectMod = new ProjectModule(this);
		fundMod = new FundModule(this);
		fundMod.setList(this.manager.getFundArray());
		faMod = new FAModule(this);
		faMod.setList(this.getManager().getFunctionalAreaArray()); 
		glMod = new GLModule(this);
		glMod.setList(this.manager.getGLArray()); 
		about = new About(this);

		// Notification Module
		notbox = new VBox();
		notificator = new Notificator(notbox);

		// Tabulation
		TabPane tabPane = new TabPane();

		// HBox of control buttons
		HBox hbox = new HBox();
		hbox.setSpacing(2);

		// New Action
		Button btnNew = createButton("new.png");
		btnNew.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				Scene scene;
				Stage stage;
				switch (tabPane.getSelectionModel().getSelectedIndex()) {
				case 0: // Project
					FormProject formProject = new FormProject(me);
					scene = new Scene(formProject, 600, 600);
					stage = new Stage();
					stage.setScene(scene);
					stage.setTitle("New Project");
					stage.showAndWait();
					stage = null;
					scene = null;
					break;
				case 1: // RUN
					FormRun formRun = new FormRun(me);
					scene = new Scene(formRun, 600, 600);
					stage = new Stage();
					stage.setScene(scene);
					stage.setTitle("New RUN");
					stage.showAndWait();
					stage = null;
					scene = null;
					break;
				case 2: // CostCenter
					FormCostCenter formcc = new FormCostCenter(me);
					scene = new Scene(formcc, 600, 600);
					stage = new Stage();
					stage.setScene(scene);
					stage.setTitle("New Cost Center");
					stage.showAndWait();
					stage = null;
					scene = null;

					break;

				}

			}

		});

		// Copy Action
		Button btnCopy = createButton("copy.png");
		btnCopy.setTooltip(new Tooltip("Copy to clipboard"));
		btnCopy.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				switch (tabPane.getSelectionModel().getSelectedIndex()) {
				case 0: // Project
					getProjectModule().toClipboard();
					break;
				case 1: // RUN
					getRunModule().toClipboard();
					break;
				case 2: // CostCenter
					getCostCenterModule().toClipboard();
					break;

				}

			}

		});

		// Export Action
		Button btnExport = createButton("export.png");
		btnExport.setTooltip(new Tooltip("Export to PDF"));
		btnExport.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Export to PDF

			}

		});

		// Admin Access Action
		Button btnLock = createButton("lock.png");
		btnLock.setTooltip(new Tooltip("Administrator Access"));
		btnLock.setOnAction(new EventHandler<ActionEvent>() {
			// Just switch the image whether or not the user is logged as admin
			@Override
			public void handle(ActionEvent event) {
				if (!adminMod.isAdmin()) {
					if (adminMod.showLoginDialog()) {
						ImageView imageView = new ImageView(new Image(
								getClass().getResource("/res/unlock.png").toExternalForm(), 40, 40, false, true));
						btnLock.setGraphic(imageView);
						hbox.getChildren().add(btnNew);

					}
				} else {
					adminMod.logoff();
					ImageView imageView = new ImageView(
							new Image(getClass().getResource("/res/lock.png").toExternalForm(), 40, 40, false, true));
					btnLock.setGraphic(imageView);
					hbox.getChildren().remove(btnNew);
				}
			}
		});

		// Refresh Action
		Button btnRefresh = createButton("refresh.png");
		btnRefresh.setTooltip(new Tooltip("Refresh"));
		btnRefresh.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				manager.refresh(); // refresh data from db
				// reload modules
				ccMod.load();
				runMod.load();
				projectMod.load();
			}
		});
		hbox.getChildren().addAll(btnCopy, btnExport, btnLock, btnRefresh);

		// Anchor the controls
		mainAnchor = new AnchorPane();
		mainAnchor.getChildren().addAll(tabPane, hbox, notbox);
		AnchorPane.setTopAnchor(hbox, 3.0);
		AnchorPane.setRightAnchor(hbox, 5.0);
		AnchorPane.setTopAnchor(tabPane, 1.0);
		AnchorPane.setRightAnchor(tabPane, 1.0);
		AnchorPane.setLeftAnchor(tabPane, 1.0);
		AnchorPane.setBottomAnchor(tabPane, 1.0);
		AnchorPane.setBottomAnchor(notbox, 75.0);
		AnchorPane.setRightAnchor(notbox, 30.0);

		// Adding Tabs

		Tab tabProjet = new Tab();
		tabProjet.setText("Projects");
		tabProjet.setContent(projectMod);

		Tab tabRun = new Tab();
		tabRun.setText("RUN");
		tabRun.setContent(runMod);

		Tab tabCC = new Tab();
		tabCC.setText("Cost Centers");
		tabCC.setContent(ccMod);
		
		Tab tabFund = new Tab();
		tabFund.setText("Funds");
		tabFund.setContent(fundMod);
		
		Tab tabFA = new Tab();
		tabFA.setText("Functional Area");
		tabFA.setContent(faMod);
		
		Tab tabGL = new Tab();
		tabGL.setText("GL");
		tabGL.setContent(glMod);

		Tab tabQueries = new Tab();
		tabQueries.setText("Export"); // Changed that from 'Queries' to 'Export' cause seems to make more sense
		tabQueries.setContent(queryMod); // from a user perspective

		Tab tabAbout = new Tab();
		tabAbout.setText("About");
		tabAbout.setContent(about);

		// tabPane.getTabs().addAll(tabProjet, tabRun, tabCC, tabFund, tabFA, tabGL, tabQueries, tabAbout);
		// TODO: Remove once new tabs are implemented
		tabPane.getTabs().addAll(tabProjet, tabRun, tabCC, tabQueries, tabAbout); 
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

	}
	@Override
	public void start(Stage stage) throws Exception {
				
		//Application icon
		stage.getIcons().add(new Image(FIRST.class.getResourceAsStream("/res/raccoon.png")));
		
		// Creating main scene
		
		Scene scene = new Scene(mainAnchor, 1440, 900);

		stage.setTitle("FIRST");
		
		stage.setScene(scene);
		
		String css = FIRST.class.getResource("/bootstrap3.css").toExternalForm();
		
		scene.getStylesheets().clear();
		scene.getStylesheets().add(css);

		stage.show();
		
		stage.setOnCloseRequest(e -> {
			manager.disconnect();
			Platform.exit();
			System.out.println("Bye bye");
			System.exit(0);
		});
		
	}
	/**
	 * Fetch an icon image from resources and applies graphic to a new button
	 * @param iconName name of file
	 * @return new button with graphic
	 */
    private Button createButton(String iconName) {
        Button button = new Button();
        ImageView imageView = new ImageView(new Image(getClass().getResource("/res/" + iconName).toExternalForm(),
                40, 40, false, true));
        button.setGraphic(imageView);
        button.getStyleClass().add("main");
        return button;
    }
	public Admin getAdministrationModule() {
		return adminMod;
	}
	public RUNModule getRunModule() {
		return runMod;
	}
	public CostCenterModule getCostCenterModule() {
		return ccMod;
	}
	public QueryModule getQueryModule() {
		return queryMod;
	}
	public ProjectModule getProjectModule() {
		return projectMod;
	}
	public DataLayer getManager() {
		return manager;
	}
	private void updateTextArea(final String text) {
		Platform.runLater( new Runnable() {

			@Override
			public void run() {
				TextArea cout = (TextArea) console.getScene().getRoot();
				cout.appendText(text);
				
			}
			
		});
	}
	private void redirectSystemStreams() {
		  OutputStream out = new OutputStream() {
		    @Override
		    public void write(int b) throws IOException {
		      updateTextArea(String.valueOf((char) b));
		    }
		 
		    @Override
		    public void write(byte[] b, int off, int len) throws IOException {
		      updateTextArea(new String(b, off, len));
		    }
		 
		    @Override
		    public void write(byte[] b) throws IOException {
		      write(b, 0, b.length);
		    }
		  };
		 
		  System.setOut(new PrintStream(out, true));
		  System.setErr(new PrintStream(out, true));
		}

	/**
	 * Main method for the program is now called from the Main class so that the preloader
	 * can be called.
	 * @param args none
	 */
	@Deprecated
	public static void main(String[] args) {
		
		if (getVersion() >= 1.8) {
			launch(args);
			//LauncherImpl.launchApplication(FIRST.class, Splash.class, args);
		} else {
			JOptionPane.showMessageDialog(null, "FIRST can't run because your version of Java is outdated. "
					+ "Please contact your Service Desk in order to have Java 1.8 or higher installed on your"
					+ " computer.\nYour Java version is " + getVersion());
		}

	}
	public void notifyUser(String text) {
		notificator.add(new Notification(text));
	}
	public static Double getVersion() {
		Double version = Double.parseDouble(System.getProperty("java.specification.version"));
		return version;
	}
	public void initConsole() {
		
		//Redirect system.out and system.err to a window 'console'
		 redirectSystemStreams();
		console = new Stage();
		TextArea cout = new TextArea();
		console.setScene(new Scene(cout));
		console.initStyle(StageStyle.UTILITY);
		console.show();
	}
	
}
