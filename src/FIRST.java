import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.stage.Stage;

import view.*;

import controler.DataLayer;

public class FIRST  extends Application {
	
	
	DataLayer manager;
	
	@Override
	public void start(Stage stage) throws Exception {
	      /* 
	      Code for JavaFX application. 
	      (Stage, scene, scene graph) 
	      */    
		try {
			manager = new DataLayer();
		} catch (controler.DatabaseCommunicationsException e) {
			this.stop();
		}
		// Search Grids
		GridProjets gridProjets = new GridProjets();
		//GridRun gridRun = new GridRun();
		//GridCostCenter gridCC = new GridCostCenter();
		
		// Modules
		RUNModule runMod = new RUNModule(DataLayer.getRunList());
		CostCenterModule ccMod = new CostCenterModule();
		QueryModule queryMod = new QueryModule();
		//ProjectModule pMod = new ProjectModule();
	
		
		//Tabulation
		TabPane tabPane = new TabPane();
		
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
		tabQueries.setText("Queries");	
		tabQueries.setContent(queryMod);
		
		Tab tabAbout = new Tab();
		tabAbout.setText("About");
		
		tabPane.getTabs().addAll(tabProjet,tabRun,tabCC,tabQueries,tabAbout);
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
		
		Scene scene = new Scene(tabPane, 1440, 900);

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
}
