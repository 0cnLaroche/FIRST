package view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import first.FIRST;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;

public class About extends BorderPane {
	
	private WebView guide;
	
	public About(FIRST main) {
		guide = new WebView();
		String html = new String();
		
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
	}

}