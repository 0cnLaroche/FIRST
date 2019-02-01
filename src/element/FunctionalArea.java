package element;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class FunctionalArea extends Pane {
	
	private VBox vb;
	private HBox details;
	private Label definition, id, name;
	
	private String style = "-fx-padding: 15; -fx-border-radius:20;-fx-border-width:5;-fx-background-radius:15;-fx-background-color:white;";
	
	public FunctionalArea(model.FunctionalArea fa) {
		
		this.vb = new VBox();
		this.details = new HBox();
		this.definition = new Label(fa.getDefinition());
		this.id = new Label(fa.getId());
		this.name = new Label(fa.getNameEN());
		
		definition.setMaxWidth(800);
		definition.setTextAlignment(TextAlignment.JUSTIFY);
		definition.setWrapText(true);
		
		details.setSpacing(15.0);
		details.getChildren().addAll(id, name);
		
		if (fa.getDefinition().length() > 0) {

			details.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					vb.getChildren().add(definition);
				}

			});

			details.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					vb.getChildren().remove(definition);
				}

			});

		}
		
		vb.setStyle(style);
		vb.setSpacing(15.0);
		vb.setPadding(new Insets(5,10,5,10));
		vb.getChildren().add(details);
		this.getChildren().add(vb);
		
	}

}
