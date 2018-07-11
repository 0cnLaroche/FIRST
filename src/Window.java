import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.FlowPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import view.*;

import java.io.File;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import controler.DataLayer;

public class Window  extends Application {
	
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
		
		GridProjets gridProjets = new GridProjets();
		GridRun gridRun = new GridRun();
		GridCostCenter gridCC = new GridCostCenter();
		// Modules
		RUNModule runMod = new RUNModule(DataLayer.getRunList());
		CostCenterModule ccMod = new CostCenterModule();
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

		Button btnQuery = new Button("Open");
		btnQuery.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				DirectoryChooser dChooser = new DirectoryChooser();
				dChooser.setInitialDirectory(new File("C://"));
				dChooser.showDialog(stage);
				
			}
			
		});
		FlowPane queriesBox = new FlowPane(btnQuery);
		queriesBox.setAlignment(Pos.CENTER);
		tabQueries.setContent(queriesBox);
		
		Tab tabAbout = new Tab();
		tabAbout.setText("About");
		
		tabPane.getTabs().addAll(tabProjet,tabRun,tabCC,tabQueries,tabAbout);
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
		
		Scene scene = new Scene(tabPane, 1440, 900);
		// Scene scene = new Scene(form,500,500, Color.BEIGE);
		//scene.setFill(Color.GRAY);


		stage.setTitle("FIRST");
		
		stage.setScene(scene);
		
		String css = Window.class.getResource("bootstrap3.css").toExternalForm();
		
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
