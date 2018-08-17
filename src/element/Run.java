package element;

import first.FIRST;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import view.FormRun;
import view.RUNModule;

public class Run extends HBox {
	
	private boolean selected = false;
	public Label id, desc, type, resp, cc, status;
	private String defaultStyle = "-fx-background-color:#FFFFFF;-fx-border-style: solid; -fx-border-width: 0 0 2 0; -fx-border-color: #4f5d75;";
	
	private String hoverStyle = "-fx-background-color:#ededed;-fx-border-style: solid; -fx-border-width: 0 0 2 0; -fx-border-color: #4f5d75;";
	private String textStyle = "-fx-text-fill: #2d3142;";
	private Run me = this;
	
	public Run(model.Run r, FIRST main) {
		
		id = new Label(r.getId());
		desc = new Label(r.getNameEN());
		desc.setPrefWidth(305);
		type = new Label(r.getType());
		type.setPrefWidth(190);
		resp = new Label(r.getResponsible());
		resp.setPrefWidth(160);
		cc = new Label(r.getCostcenter().getId());
		cc.setPrefWidth(55);
		status = new Label("" + r.getStatus().toString());
		
		Tooltip tpCC = new Tooltip();
		tpCC.setText(r.getCostcenter().getNameEN() + "\nManager: " + r.getCostcenter().getManager());
		cc.setTooltip(tpCC);
		
		this.getChildren().addAll(id,desc,type,resp,cc,status);
		for (Node n : this.getChildren()) {
			n.setStyle(textStyle);
		}
		if (r.getStatus() == model.Run.CLOSED) {
			status.setStyle("-fx-text-fill: #ef8354;-fx-font-weight: bold;");
		}
		
		this.setStyle(defaultStyle);
		this.setPadding(new Insets(5,10,5,10));
		this.setSpacing(10);
		
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				FormRun form = new FormRun(main);
				form.edit(r);
				Scene scene = new Scene(form,600,600);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.setTitle("RUN form");
				stage.show();
				
			}
			
		});
		// The RUN is highlighted when the user move the mouse on it
		this.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				me.setStyle(hoverStyle);
			}
			
		});
		this.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				me.setStyle(defaultStyle);
			}
			
		});
		

		this.setOnDragDetected(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO : Something to selection a region
				
			}
			
		});
		this.setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				selected = !selected;
				if (selected) {
					me.setStyle("-fx-border-style: solid; -fx-border-width: 0 0 2 0; -fx-border-color: #003459;"
							+ "-fx-background-color:#00a8e8");
				} else {
					me.setStyle(defaultStyle);
				}
			}
			
		});
		
		
	}
	

}
