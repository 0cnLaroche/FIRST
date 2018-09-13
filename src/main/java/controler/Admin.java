package controler;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import first.App;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;


/** Manage user permissions. Passwords are encrypted with SHA-1
 * @author samuel.laroche
 *
 */
public class Admin {
	
	private boolean adminUsr = false;
	private App main;
	
	public Admin(App main) {
		this.main = main;
	}
	
	public boolean login(String user, String pwrd)  {
		
		String hash = DataLayer.getUserHash(user);  // Get user hashcode from db
		
		try {
			if (compareHash(pwrd,hash)) {
				adminUsr = true;
				// TODO : Connect as firstadmin
				main.getManager().disconnect();
				main.getManager().connect("firstadmin", "Pa$$w0rd");
				System.out.println(user + " : Administrator access granted");
			} else {
				adminUsr = false;
				System.out.println(user + " : Administrator login failed");
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return adminUsr;
			
		// compare

        
	}
	public void logoff() {
		main.getManager().disconnect();
		try {
			main.getManager().connect();
		} catch (DatabaseCommunicationsException e) {

		}
		adminUsr = false;
	}
	/**Creates a dialog for the user to login. Check if the credentials match an administrator username and password
	 * in the database. Informs the user if login is successful or not.
	 * @return true if the user is logged in as admin false if not
	 */
	public boolean showLoginDialog() {
		
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Login Dialog");
		dialog.setHeaderText("Need access to change my data?\nI will need some identification please");
		dialog.setGraphic(new ImageView(new Image(Admin.class.getResourceAsStream("/res/user.png"))));
		
		GridPane grid = new GridPane();

		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setHgap(5);
		grid.setVgap(5);

		Label lblUserName = new Label("UserName");
		final TextField txtUserName = new TextField();
		txtUserName.setPromptText("UserName");
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
			
			if (this.login(usernamePassword.getKey(), usernamePassword.getValue())) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Login Confirmation");
				alert.setHeaderText("Administrator access granted!");
				alert.setGraphic(new ImageView(new Image(Admin.class.getResourceAsStream("/res/check.png"))));
				alert.show();
				
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Login failed");
				alert.setContentText("UserName and/or password incorrect, please try again");
				alert.setGraphic(new ImageView(new Image(Admin.class.getResourceAsStream("/res/block.png"))));
				alert.showAndWait();
			}
		});
		return adminUsr;
	}
	public boolean isAdmin() {
		return adminUsr;
	}
	private boolean compareHash(String text, String hash) throws Exception {
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
        
        if (hashedTxt.equals(hash)) {
        	pass = true;
        } else {
        	pass = false;
        }
        
		return pass;
	}
	public static String hash(String text) throws NoSuchAlgorithmException {
		
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
        
		return hashedTxt;
		
	}
}
