package element;

import controler.Admin;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Notification extends Label {
	
	public Notification(String text) {
		
		// this.getStyleClass().add("Alert");
		this.setPrefWidth(300.0);
		this.setStyle("-fx-background-color:#e6e6e6;-fx-background-radius:10;-fx-padding:15");
		
		this.setText(text);
		
	}
	public Notification(String text, String imgPath) {
		
		// this.getStyleClass().add("Alert");
		this.setPrefWidth(300.0);
		this.setStyle("-fx-background-color:#e6e6e6;-fx-background-radius:10;-fx-padding:15");
		
		this.setText(text);
		this.setGraphic(new ImageView(new Image(Admin.class.getResourceAsStream(imgPath)
				, 40, 40, false, true)));
		
	}

}
