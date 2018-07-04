package controler;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class NotFoundException extends Exception {
	// Will open a new window that says not found 
	public NotFoundException() {
		System.out.println("oups nothing found");
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Search Dialog");
		alert.setHeaderText("Oups..");
		alert.setContentText("Your search criterias didn't return any results.");
		alert.show();
	}
	
}
