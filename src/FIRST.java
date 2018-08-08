import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import view.*;
import controler.*;

public class FIRST  extends Application {
	
	GridProjets gridProjets;
	RUNModule runMod;
	CostCenterModule ccMod;
	QueryModule queryMod;
	
	DataLayer manager;
	
	@Override
	public void start(Stage stage) throws Exception {
		
		stage.getIcons().add(new Image(FIRST.class.getResourceAsStream("res/raccoon.png")));

		try {
			manager = new DataLayer();
		} catch (controler.DatabaseCommunicationsException e) {
			this.stop();
		}
		
		// Search Grids
		gridProjets = new GridProjets();
		//GridRun gridRun = new GridRun();
		//GridCostCenter gridCC = new GridCostCenter();
		
		// Modules
		runMod = new RUNModule();
		ccMod = new CostCenterModule();
		queryMod = new QueryModule();
		//ProjectModule pMod = new ProjectModule();
			
		//Tabulation
		TabPane tabPane = new TabPane();
		// HBox of control buttons
		HBox hbox = new HBox();
		hbox.setSpacing(2);
		
		Button btnCopy = createButton("copy.png");
		btnCopy.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				// TODO copy to clipboard
				
			}
			
		});
		
		Button btnExport = createButton("export.png");
		btnExport.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Export to PDF
				
			}
			
		});
		

		Button btnLock = createButton("lock.png");
		
		btnLock.setOnAction(new EventHandler<ActionEvent>() {
			// Just switch the image whether or not the user is logged as admin
			@Override
			public void handle(ActionEvent event) {
				if (!Admin.isAdmin()) {
					if(Admin.showLoginDialog()) {
				        ImageView imageView = new ImageView(new Image(getClass().getResource("res/unlock.png").toExternalForm(),
				                40, 40, false, true));
				        btnLock.setGraphic(imageView);
					}
				} else {
					Admin.logoff();
			        ImageView imageView = new ImageView(new Image(getClass().getResource("res/lock.png").toExternalForm(),
			                40, 40, false, true));
			        btnLock.setGraphic(imageView);
				}
			}
		});
		
		
		Button btnRefresh = createButton("refresh.png");
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
        AnchorPane anchor = new AnchorPane();
        anchor.getChildren().addAll(tabPane, hbox);
        AnchorPane.setTopAnchor(hbox, 3.0);
        AnchorPane.setRightAnchor(hbox, 5.0);
        AnchorPane.setTopAnchor(tabPane, 1.0);
        AnchorPane.setRightAnchor(tabPane, 1.0);
        AnchorPane.setLeftAnchor(tabPane, 1.0);
        AnchorPane.setBottomAnchor(tabPane, 1.0);
		
		
		Tab tabProjet = new Tab();
		tabProjet.setText("Projects");
		tabProjet.setContent(gridProjets);
		
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
		
		
		Scene scene = new Scene(anchor, 1440, 900);

		stage.setTitle("FIRST");
		
		stage.setScene(scene);
		
		String css = FIRST.class.getResource("bootstrap3.css").toExternalForm();
		
		scene.getStylesheets().clear();
		scene.getStylesheets().add(css);
		
		stage.show();
		
		stage.setOnCloseRequest(e -> {
			manager.disconnect();
			Platform.exit();
		});
		

	}
	public static void main(String[] args) {
		
		launch(args);

	}
    private Button createButton(String iconName) {
        Button button = new Button();
        ImageView imageView = new ImageView(new Image(getClass().getResource("res/" + iconName).toExternalForm(),
                40, 40, false, true));
        button.setGraphic(imageView);
        button.getStyleClass().add("main-button");
        return button;
    }
}
