import javafx.*;
import javafx.application.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import view.*;
import controler.DataLayer;

public class Window  extends Application {

	@Override
	public void start(Stage stage) throws Exception {
	      /* 
	      Code for JavaFX application. 
	      (Stage, scene, scene graph) 
	      */    
		DataLayer manager = new DataLayer();
		
		GridProjets gridProjets = new GridProjets();
		GridRun gridRun = new GridRun();
		GridCostCenter gridCC = new GridCostCenter();
	
		
		//Tabulation
		TabPane tabPane = new TabPane();
		
		Tab tabProjet = new Tab();
		tabProjet.setText("Projects");
		tabProjet.setContent(gridProjets);
		
		Tab tabRun = new Tab();
		tabRun.setText("RUN");
		tabRun.setContent(gridRun);
		
		Tab tabCC = new Tab();
		tabCC.setText("Cost Centers");
		tabCC.setContent(gridCC);
		
		Tab tabQueries = new Tab();
		tabQueries.setText("Queries");
		
		Tab tabAbout = new Tab();
		tabAbout.setText("About");
		
		tabPane.getTabs().addAll(tabProjet,tabRun,tabCC,tabQueries,tabAbout);
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
		
		Scene scene = new Scene(tabPane,500,500, Color.BEIGE);
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
