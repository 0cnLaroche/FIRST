package controler;

import java.security.MessageDigest;
import java.util.Optional;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class Admin {
	
	private static boolean adminUsr = false;
	
	public static boolean login(String user, String pwrd)  {
		
		String hash = DataLayer.getUserHash(user);  // Get user hashcode from db
		
		try {
			if (compareHash(pwrd,hash)) {
				adminUsr = true;
				System.out.println(user + " : Administrator access granted");
			} else {
				adminUsr = false;
				System.out.println(user + " : Administrator login failed");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return adminUsr;
			
		// compare

        
	}
	public static void logoff() {
		adminUsr = false;
	}
	public static boolean showLoginDialog() {
		
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Login Dialog");
		dialog.setHeaderText("Need access to change my data? I will need some identification please");
		
		GridPane grid = new GridPane();

		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setHgap(5);
		grid.setVgap(5);

		Label lblUserName = new Label("Username");
		final TextField txtUserName = new TextField();
		txtUserName.setPromptText("Username");
		Label lblPassword = new Label("Password");
		final PasswordField pf = new PasswordField();
		pf.setPromptText("Password");

		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		grid.add(lblUserName, 0, 0);
		grid.add(txtUserName, 1, 0);
		grid.add(lblPassword, 0, 1);
		grid.add(pf, 1, 1);
		// grid.add(btnLogin, 2, 1);
		// grid.add(lblMessage, 1, 2);

		/*Reflection r = new Reflection();
		r.setFraction(0.7f);
		grid.setEffect(r);*/
		
		dialog.getDialogPane().setContent(grid);
		
		Platform.runLater(() -> txtUserName.requestFocus());
		
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == loginButtonType) {
		        return new Pair<>(txtUserName.getText(), pf.getText());
		    }
		    return null;
		});
		
		Optional<Pair<String, String>> result = dialog.showAndWait();

		// Optional<Pair<String, String>> result = new LoginForm().showAndWait();
		
		result.ifPresent(usernamePassword -> {
			Admin.login(usernamePassword.getKey(), usernamePassword.getValue());
		});
		return adminUsr;
	}
	public static boolean isAdmin() {
		return adminUsr;
	}
	private static boolean compareHash(String text, String hash) throws Exception {
		boolean pass;
		
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(text.getBytes());
		
		byte byteData[] = md.digest();
		
		// Converts to Hexadecimal
		StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        
        String hashedTxt = sb.toString();
        System.out.println(hashedTxt);
        
        if (hashedTxt.equals(hash)) {
        	pass = true;
        } else {
        	pass = false;
        }
        
		return pass;
	}
}
