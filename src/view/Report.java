package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Report extends Stage {
	
	public Report (String data) {
		
		Text text = new Text(20,20, data);
		text.setFont(Font.font(15));
		
		Group group = new Group(text);
		
		Scene scene = new Scene(group, 600, 600);
		
		this.setTitle("Report");
		this.setScene(scene);
		
	}
}
