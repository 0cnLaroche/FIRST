package first;

import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import view.*;

import controler.*;

public class FIRST  extends Application {
	

	public GridProjets gridProjets;
	public RUNModule runMod;
	public CostCenterModule ccMod;
	public QueryModule queryMod;
	public ProjectModule pMod;
	public FIRST me = this;
	
	public DataLayer manager;
	
	@Override
	public void start(Stage stage) throws Exception {
		
		stage.getIcons().add(new Image(FIRST.class.getResourceAsStream("/res/raccoon.png")));

		try {
			manager = new DataLayer();
		} catch (controler.DatabaseCommunicationsException e) {
			this.stop();
		}

		// Modules
		runMod = new RUNModule(this);
		ccMod = new CostCenterModule(this);
		queryMod = new QueryModule(this);
		pMod = new ProjectModule(this);
			
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
				if (!Admin.isAdmin()) {
					if(Admin.showLoginDialog()) {
				        ImageView imageView = new ImageView(new Image(getClass().getResource("/res/unlock.png").toExternalForm(),
				                40, 40, false, true));
				        btnLock.setGraphic(imageView);
				        hbox.getChildren().add(btnNew);
				        
					}
				} else {
					Admin.logoff();
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
        mainAnchor.getChildren().addAll(tabPane, hbox);
        AnchorPane.setTopAnchor(hbox, 3.0);
        AnchorPane.setRightAnchor(hbox, 5.0);
        AnchorPane.setTopAnchor(tabPane, 1.0);
        AnchorPane.setRightAnchor(tabPane, 1.0);
        AnchorPane.setLeftAnchor(tabPane, 1.0);
        AnchorPane.setBottomAnchor(tabPane, 1.0);
		
		
		Tab tabProjet = new Tab();
		tabProjet.setText("Projects");
		//tabProjet.setContent(projectAnchor);
		tabProjet.setContent(pMod);
		
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
		
		tabPane.getTabs().addAll(tabProjet,tabRun,tabCC,tabQueries,tabAbout);
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
		
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
		return pMod;
	}
	
	public static void main(String[] args) {
		
		launch(args);

	}
}
