package element;

import java.util.function.Function;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;

public class SearchBar extends GridPane {
	
	Button btn;
	TextField tfKeyword;
	EventHandler action;
	
	public SearchBar() {
		
		super();
	    this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));

		Label lbKeyword = new Label("Keyword");
		this.add(lbKeyword, 0, 0);
		
		tfKeyword = new TextField();
		this.add(tfKeyword, 1, 0);
		GridPane.setHgrow(tfKeyword, Priority.ALWAYS);
		
		btn = new Button("Go");
		btn.getStyleClass().add("primary");
		
		// Works only when cursor is in the text field
		this.setOnKeyPressed((event) -> {
			if (event.getCode() == KeyCode.ENTER) {
			
			}
		});


		
		this.add(btn, 2, 0);
		
		
		
	}
	
	public Button getBtn() {
		return btn;
	}

	public TextField getTextField() {
		return tfKeyword;
	}

	public String getKeyword() {
		return tfKeyword.getText();
	}
	
	
	
	


}
