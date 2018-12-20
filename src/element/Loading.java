package element;

import javafx.util.Duration;

import javafx.animation.RotateTransition;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Loading extends StackPane {
	
	public Loading() {
		Image raccoon = new Image("res/rocking-chair.png");
		ImageView image = new ImageView(raccoon);
		this.setStyle("-fx-background-color: rgba(50, 50, 50, 0.5);");
		this.setAlignment(Pos.CENTER);
		
		image.setFitHeight(80);
		image.setFitWidth(80);

		RotateTransition rotateTransition = new RotateTransition();
		rotateTransition.setDuration(Duration.millis(750));
		rotateTransition.setByAngle(-15);
		rotateTransition.setAutoReverse(true);
		rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
		rotateTransition.setNode(image);
		rotateTransition.play();
		
		this.getChildren().add(image);
		
		

	}

}
