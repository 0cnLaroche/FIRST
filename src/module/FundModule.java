package module;

import element.SearchBar;
import first.FIRST;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.Fund;

public class FundModule extends BorderPane {
	
	private ScrollPane container;
	private VBox list;
	private SearchBar sb;
	
	public FundModule(FIRST main) {
		
		this.list = new VBox();
		this.sb = new SearchBar();
		
		list.setSpacing(10.0);
		this.container = new ScrollPane(list);
		container.setStyle("-fx-background: #5c90ff; -fx-padding:15"); // Blue
		this.setCenter(container);
		this.setBottom(sb);
		 

	}
	
	public void setList(Fund[] funds) {
		
		for (int i = 0; i < funds.length; i++) {
			element.Fund item = new element.Fund(funds[i]);
			list.getChildren().add(item);
		}
	}

}
