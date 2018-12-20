package first;

import javax.swing.JOptionPane;

import com.sun.javafx.application.LauncherImpl;
/**
 * Loads Preloader and Application
 * @author samuel.laroche
 *
 */
@SuppressWarnings("restriction")
public class Main {
	
	public static void main(String[] args) {
		
		if (getVersion() >= 1.8) {

			LauncherImpl.launchApplication(FIRST.class, Splash.class, args);
		} else {
			JOptionPane.showMessageDialog(null, "FIRST can't run because your version of Java is outdated. "
					+ "Please contact your Service Desk in order to have Java 1.8 or higher installed on your"
					+ " computer.\nYour Java version is " + getVersion());
		}

	}
	public static Double getVersion() {
		Double version = Double.parseDouble(System.getProperty("java.specification.version"));
		return version;
	}

}
