package view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import first.FIRST;
import javafx.application.Platform;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
/**
 * About page
 * @author samuel.laroche
 *
 */
public class About extends BorderPane {
	
	private WebView guide;
	private String html = new String();
	
	public About(FIRST main) {
		
		// This will be run later by the main application thread
		Platform.runLater(() -> {
			
			guide = new WebView();
			InputStream is = About.class.getResourceAsStream("/res/guide.html");
			
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"))){
				String line;
				while ((line = reader.readLine()) != null){
					html += line;
				}
				
			} catch (FileNotFoundException e) {
				System.err.println("File not Found");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			guide.getEngine().loadContent(html);
			this.setCenter(guide);
		});
		

	}

}
