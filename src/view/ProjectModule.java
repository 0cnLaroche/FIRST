package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.*;


public class ProjectModule extends Stage {
	
	protected BorderPane root;
	protected HBox hb;
	protected ScrollPane sp;	
	protected GridPane grid;
	
	
	public ProjectModule (Project project) {
		
		root = new BorderPane();
		hb = new HBox(5);
		sp = new ScrollPane();

		// IDée : Créer un group pour chaque element et ajouter le text comme objet Text qui pourront être accedé
        
        graphics.Project prj = new graphics.Project();
        prj.setSource(project);
        prj.setPrefWidth(1440);
        prj.render();
        
        String txtStageStyle = "-fx-font:40px Tahoma;-fx-fill:white;";
        
		FlowPane stage0 = new FlowPane();
		stage0.setVgap(10);
		stage0.setHgap(10);
		//stage0.setMaxWidth(1440);
		//stage0.setPrefWrapLength(1440);
		stage0.setAlignment(Pos.TOP_CENTER);
		stage0.setStyle("-fx-background-color:rgb(150,206,180,0.5);-fx-padding:10"); // Color : #96CEB4
        
		FlowPane stage2 = new FlowPane();
		stage2.setVgap(10);
		stage2.setHgap(10);
		stage2.setMaxWidth(1440);
		stage2.setAlignment(Pos.TOP_CENTER);
		stage2.setStyle("-fx-background-color:#96ceb4;-fx-padding:10");
		stage2.prefWidthProperty().bind(root.widthProperty().divide(4));
		Label txStage2 = new Label("Stage 2");
		txStage2.setStyle(txtStageStyle);
		stage2.getChildren().add(txStage2);
		
		FlowPane stage3 = new FlowPane();
		stage3.setVgap(10);
		stage3.setHgap(10);
		stage3.setMaxWidth(1440);
		// stage3.setMinWidth(1000);
		stage3.setAlignment(Pos.BASELINE_LEFT);
		stage3.setStyle("-fx-background-color:#ffeead;-fx-padding:10");
		stage3.prefWidthProperty().bind(root.widthProperty().divide(4));
		Label txStage3 = new Label("Stage 3");
		txStage3.setStyle(txtStageStyle);
		stage3.getChildren().add(txStage3);
		
		FlowPane stage4 = new FlowPane();
		stage4.setVgap(10);
		stage4.setHgap(10);
		stage4.setMaxWidth(1440);
		stage4.setAlignment(Pos.TOP_CENTER);
		stage4.setStyle("-fx-background-color:#ffcc5c;-fx-padding:10");
		stage4.prefWidthProperty().bind(root.widthProperty().divide(4));
		Label txStage4 = new Label("Stage 4");
		txStage4.setStyle(txtStageStyle);
		stage4.getChildren().add(txStage4);
		
		FlowPane stage5 = new FlowPane();
		stage5.setVgap(10);
		stage5.setHgap(10);
		stage5.setMaxWidth(1440);
		stage5.setAlignment(Pos.TOP_CENTER);
		stage5.setStyle("-fx-background-color:#ff6f69;-fx-padding:10");
		stage5.prefWidthProperty().bind(root.widthProperty().divide(4));
		Label txStage5 = new Label("Stage 5");
		txStage5.setStyle(txtStageStyle);
		stage5.getChildren().add(txStage5);
		
           for (Wbs w : project.getWbs()) {
        	// VBox flow;
        	FlowPane flow;
        	switch (w.getStage()) {
        	case 0 :
        		flow = stage0;
        		break;
        	case 2:
        		flow = stage2;
        		break;
        	case 3:
        		flow = stage3;
        		break;
        	case 4:
        		flow = stage4;
        		break;
        	case 5:
        		flow = stage5;
        		break;
        	default:
        		flow = stage0;
        		break;
            }
        	
        	for (Network n : w.getNetworks()) {
        		graphics.Network gn = new graphics.Network();
        		gn.setSource(n);
        		gn.render();
        		flow.getChildren().add(gn);
        	}
        }
        

        Canvas bg = new Canvas(600, 600);
        GraphicsContext gc = bg.getGraphicsContext2D();
        
        //gc.strokeLine(nw.getConnector(Sprite.TOP).getX(), nw.getConnector(Sprite.TOP).getY(), 
        //		prj.getConnector(Sprite.BUTTOM).getX(), prj.getConnector(Sprite.BUTTOM).getY());
        
        hb.setPadding(new Insets(10));
        
        switch(project.getModel()) {
        	case Project.STAGEGATED :
                hb.getChildren().add(stage2);
                hb.getChildren().add(stage3);
                hb.getChildren().add(stage4);
                hb.getChildren().add(stage5);
                break;
        	case Project.BRANCHINITIATIVE:
        	case Project.LITE:
        	case Project.NONSTAGEGATED:
        		hb.getChildren().add(stage0);
        		break;
        	default :
        		hb.getChildren().add(stage0);
                hb.getChildren().add(stage2);
                hb.getChildren().add(stage3);
                hb.getChildren().add(stage4);
                hb.getChildren().add(stage5);
                break;
        }

        sp = new ScrollPane(hb);
        sp.setFitToHeight(true);
        
        
        root.setTop(prj);
        root.setAlignment(prj, Pos.CENTER);
        root.setCenter(sp);
        
        //root.getChildren().add(sp);
		
		Scene scene = new Scene(root, 1440, 900);
		
		this.setTitle("Projects");
		this.setScene(scene);
		
	}
}
