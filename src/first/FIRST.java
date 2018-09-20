package first;

import javafx.animation.FadeTransition;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import javafx.util.Duration;
import view.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Timer;
import java.util.TimerTask;

import controler.*;
import element.Notification;

public class FIRST  extends Application {
	
	public Stage console;
	private GridProjets gridProjets;
	private RUNModule runMod;
	private CostCenterModule ccMod;
	private QueryModule queryMod;
	private ProjectModule projectMod;
	private Admin adminMod;
	private Notificator notificator;
	VBox notbox;
	
	public About about;
	public FIRST me = this;
	
	public DataLayer manager;
	
	@Override
	public void start(Stage stage) throws Exception {

		//This is an opening message while FIRST is loading
		Stage opening = new Stage();
		VBox vb = new VBox();
		vb.setAlignment(Pos.CENTER);
		vb.getChildren().addAll(
				new ImageView(new Image( FIRST.class.getResourceAsStream("/res/raccoon.png"))),
				new Label("Please wait while FIRST 2 is loading ..."));
		vb.setSpacing(10.0);
		opening.setScene(new Scene (vb,300,300));
		opening.initStyle(StageStyle.UTILITY);
		opening.setAlwaysOnTop(true);
		opening.show();
		
		//Show the console
		 redirectSystemStreams();
		
		console = new Stage();
		TextArea cout = new TextArea();
		console.setScene(new Scene(cout));
		console.initStyle(StageStyle.UTILITY);
		//console.toBack();
		console.show();	
		
		//Application icon
		stage.getIcons().add(new Image(FIRST.class.getResourceAsStream("/res/raccoon.png")));
		
		//Loading Admin module
		adminMod = new Admin(this);
		
		//Loading the data layer
		try {
			manager = new DataLayer();
		} catch (controler.DatabaseCommunicationsException e) {
			this.stop();
		}

		// Modules
		runMod = new RUNModule(this);
		ccMod = new CostCenterModule(this);
		queryMod = new QueryModule(this);
		projectMod = new ProjectModule(this);
		about = new About(this);
		
		// Notificator
		notbox = new VBox();
		notificator = new Notificator(notbox);
		
			
		//Tabulation
		TabPane tabPane = new TabPane();
		// HBox of control buttons
		HBox hbox = new HBox();
		hbox.setSpacing(2);
		
		Button btnNew = createButton("new.png");
		btnNew.setOnAction(new EventHandler<ActionEvent>(){

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
		
		Button btnExport = createButton("export.png");
		btnExport.setTooltip(new Tooltip("Export to PDF"));
		btnExport.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Export to PDF
				
			}
			
		});
		

		Button btnLock = createButton("lock.png");
		btnLock.setTooltip(new Tooltip("Administrator Access"));
		
		btnLock.setOnAction(new EventHandler<ActionEvent>() {
			// Just switch the image whether or not the user is logged as admin
			@Override
			public void handle(ActionEvent event) {
				if (!adminMod.isAdmin()) {
					if(adminMod.showLoginDialog()) {
				        ImageView imageView = new ImageView(new Image(getClass().getResource("/res/unlock.png").toExternalForm(),
				                40, 40, false, true));
				        btnLock.setGraphic(imageView);
				        hbox.getChildren().add(btnNew);
				        
					}
				} else {
					adminMod.logoff();
			        ImageView imageView = new ImageView(new Image(getClass().getResource("/res/lock.png").toExternalForm(),
			                40, 40, false, true));
			        btnLock.setGraphic(imageView);
			        hbox.getChildren().remove(btnNew);
				}
			}
		});
		
		Button btnRefresh = createButton("refresh.png");
		btnRefresh.setTooltip(new Tooltip("Refresh"));
		btnRefresh.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				DataLayer.refresh(); // refresh data from db
				// reload modules
				ccMod.load();
				runMod.load();
				// Project Module doesn't need to be reloaded right now since we still need a keywork to open it
			}
		});
		hbox.getChildren().addAll(btnCopy,btnExport,btnLock,btnRefresh);
		
		
        // Anchor the controls
        AnchorPane mainAnchor = new AnchorPane();
        mainAnchor.getChildren().addAll(tabPane, hbox,notbox);
        AnchorPane.setTopAnchor(hbox, 3.0);
        AnchorPane.setRightAnchor(hbox, 5.0);
        AnchorPane.setTopAnchor(tabPane, 1.0);
        AnchorPane.setRightAnchor(tabPane, 1.0);
        AnchorPane.setLeftAnchor(tabPane, 1.0);
        AnchorPane.setBottomAnchor(tabPane, 1.0);
        AnchorPane.setBottomAnchor(notbox, 75.0);
        AnchorPane.setRightAnchor(notbox, 30.0);
		
		
		Tab tabProjet = new Tab();
		tabProjet.setText("Projects");
		//tabProjet.setContent(projectAnchor);
		tabProjet.setContent(projectMod);
		
		Tab tabRun = new Tab();
		tabRun.setText("RUN");
		tabRun.setContent(runMod);
		
		Tab tabCC = new Tab();
		tabCC.setText("Cost Centers");
		tabCC.setContent(ccMod);
		
		Tab tabQueries = new Tab();
		tabQueries.setText("Export");	// Changed that from 'Queries' to 'Export' cause seems to make more sense 
		tabQueries.setContent(queryMod); // to from a user perspective
		
		Tab tabAbout = new Tab();
		tabAbout.setText("About");
		tabAbout.setContent(about);
		
		
		tabPane.getTabs().addAll(tabProjet,tabRun,tabCC,tabQueries,tabAbout);
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
		
		Scene scene = new Scene(mainAnchor, 1440, 900);

		stage.setTitle("FIRST");
		
		stage.setScene(scene);
		
		String css = FIRST.class.getResource("/bootstrap3.css").toExternalForm();
		
		scene.getStylesheets().clear();
		scene.getStylesheets().add(css);
		
		stage.show();
		opening.close();
		
		
		/*FadeTransition ft = new FadeTransition(Duration.millis(5*1000), notbox);
		ft.setFromValue(1.0);
		ft.setToValue(0.0);
		ft.play();*/
		
		stage.setOnCloseRequest(e -> {
			manager.disconnect();
			Platform.exit();
		});
		

	}
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
	
	public static void main(String[] args) {
		
		launch(args);

	}
	public void notify(String text) {
		
		notificator.add(new Notification(text));
		
	}
}
