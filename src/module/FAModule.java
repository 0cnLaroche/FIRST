package module;

import element.SearchBar;
import first.FIRST;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.FunctionalArea;
import model.Fund;

public class FAModule extends BorderPane {
	
	private ScrollPane container;
	private VBox list;
	private SearchBar sb;
	
	public FAModule(FIRST main) {
		
		this.list = new VBox();
		this.sb = new SearchBar();
		
		this.container = new ScrollPane(list);
		container.setStyle("-fx-background: #7a5cff; -fx-padding:15"); // Purple
		
		this.setCenter(container);
		this.setBottom(sb);
		
		
	}
	
	public void setList(FunctionalArea[] fas) {
		
		for (int i = 0; i < fas.length; i++) {
			element.FunctionalArea item = new element.FunctionalArea(fas[i]);
			list.getChildren().add(item);
		}
	}

}
