package controler;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class NotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3120968240735735917L;

	// Will open a new window that says not found 
	public NotFoundException() {
		System.err.println("oups not found");

	}
	public void showDialog() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Search Dialog");
		alert.setHeaderText("Oups..");
		alert.setContentText("Your search criterias didn't return any results.");
		alert.show();
	}
	
}
