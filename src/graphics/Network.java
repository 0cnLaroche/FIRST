package graphics;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.FinancialCode;
import view.FormWBS;

public class Network extends Sprite {
	
	private model.Network nw;
	private Label name, id, cc, approver;
	private Canvas canvas;
	private GraphicsContext gc;
	
	private Label pjmngt, analyse, plan, design, build, test, deploy, stabilize;
	
	String nwStyle = "-fx-border-radius:20;-fx-border-width:5;-fx-background-radius:20;-fx-background-color:white;";
	
	
	private ToggleButton showAct;
	
	public Network() {
		super();
		padding = 10;
		this.setDimension(200, 100);
		
		canvas = new Canvas(this.getWidth() + padding * 2, this.getHeight() + padding * 2);
		gc = canvas.getGraphicsContext2D();
		
		

	}
	
	public void setSource(model.Network network) {
		
		this.nw = network;
	}

	public void render() {
		
		VBox vb = new VBox();
		DropShadow shadow = new DropShadow();
		
		vb.setSpacing(2);
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER_LEFT);
		grid.setHgap(5);
		grid.setVgap(5);
		grid.setPadding(new Insets(padding + 5, padding + 5, padding + 5, padding + 5));
		
		name = new Label(nw.getNameEN());
		grid.add(name, 0, 0);
		
		cc = new Label(nw.getWbs().getCostcenter().getId());
		grid.add(cc, 0, 1);
		
		approver = new Label(nw.getWbs().getApprover());
		grid.add(approver, 0, 2);
		
		id = new Label(nw.getId());
		grid.add(id, 1, 0);
		
		showAct = new ToggleButton("+");
		grid.add(showAct, 1, 2);
		
		showAct.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				if (showAct.isSelected()) {
					setActivities(vb);
				} else {
					vb.getChildren().clear();
					vb.getChildren().add(grid);
				}
				
			}
			
		});
		
		grid.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				grid.setEffect(shadow);
				grid.getScene().setCursor(Cursor.HAND);
				
				
			}
			
		});
		grid.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				grid.setEffect(null);
				grid.getScene().setCursor(Cursor.DEFAULT);
				
			}
			
		});
		grid.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				FormWBS form = new FormWBS();
				form.edit(nw);
				Scene scene = new Scene(form,600,600);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.setTitle("Network form");
				stage.show();
				
			}
			
		});
		
		vb.getChildren().add(grid);
		
		switch (nw.getStatus()) {
		case FinancialCode.ACTIVE :
			grid.setStyle(nwStyle + "-fx-border-color:white;-fx-border-style:solid;");
			for (Node lb : grid.getChildren()) {
				lb.setStyle("-fx-text-fill: black");
			}
			break;
		case FinancialCode.CLOSED :
			// grid.setStyle(nwStyle + "-fx-border-color: red;-fx-border-style:solid;");
			grid.setStyle(nwStyle);
			for (Node lb : grid.getChildren()) {
				lb.setStyle("-fx-text-fill: red");
			}
			break; //-fx-background-color:LightGray;
		case FinancialCode.UNRELEASED :
			grid.setStyle("-fx-border-radius:20;-fx-border-width:5;"
					+ "-fx-border-color:white;-fx-border-style:dotted;");
			for (Node lb : grid.getChildren()) {
				lb.setStyle("-fx-text-fill: black;");
			}
			break;
		default : 
			grid.setStyle(nwStyle + "-fx-border-color:white;-fx-border-style:solid;");
			for (Node lb : grid.getChildren()) {
				lb.setStyle("-fx-text-fill: black");
			}
			break;
		}
		
		
		
		//this.setDimension(grid.getBoundsInLocal().getWidth(), grid.getBoundsInLocal().getHeight());
		
		/*r = new Rectangle(0 + padding , 0 + padding , grid.getWidth() - padding /2 , grid.getHeight() - padding / 2);
		r.setArcHeight(10);
		r.setArcWidth(10);
		r.setFill(Color.TRANSPARENT);
		r.setStroke(Color.GREEN);*/
		
		this.getChildren().addAll(vb);
		
	}
	private void setActivities(VBox vb) {
		
		String actStyle = "-fx-background-color:white;-fx-background-radius:20;-fx-border-color:white;-fx-border-style:solid;-fx-border-radius:20;-fx-border-width:5;-fx-padding:5";
		
		pjmngt = new Label("0010 - Project Management");
		pjmngt.setStyle(actStyle);
		analyse = new Label("0020 - Analyse");
		analyse.setStyle(actStyle);
		plan = new Label("0030 - Plan");
		plan.setStyle(actStyle);
		design = new Label("0040 - Design");
		design.setStyle(actStyle);
		build = new Label("0050 - Build");
		build.setStyle(actStyle);
		test = new Label("0060 - Test");
		test.setStyle(actStyle);
		deploy = new Label("0070 - Deploy");
		deploy.setStyle(actStyle);
		stabilize = new Label("0080 - Stabilize/Close");
		stabilize.setStyle(actStyle);
		
		switch(nw.getWbs().getStage()) {
		case 0 : // Ou bien si le projet est Lite ou branch initiative? Devrait enlever stage 0 plus tard
			vb.getChildren().addAll(pjmngt,analyse,plan,design,build,test,deploy,stabilize);
			break;
		case 2: 
			vb.getChildren().addAll(pjmngt,analyse);
			break;
		case 3 :
			vb.getChildren().addAll(pjmngt,plan);
			break;
		case 4 : 
			vb.getChildren().addAll(pjmngt,design,build,test,deploy);
			break;
		case 5 :
			vb.getChildren().addAll(pjmngt,stabilize);
			break;
		default:
			break;
		
		}
	}
}
