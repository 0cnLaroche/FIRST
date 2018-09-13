package controler;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DatabaseCommunicationsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3120968240735735917L;

	// Will open a new window that says not found 
	public DatabaseCommunicationsException() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Connection error");
		alert.setHeaderText("Oups..");
		alert.setContentText("FIRST can't connect to its database... Make sure your are connected to ESDC's network");
		alert.show();
	}
	
}
