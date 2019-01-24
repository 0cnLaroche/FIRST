package module;

import element.SearchBar;
import first.FIRST;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.GL;;

public class GLModule extends BorderPane {
	
	private ScrollPane container;
	private VBox list;
	private SearchBar sb;
	
	public GLModule(FIRST main) {
		
		this.list = new VBox();
		this.sb = new SearchBar();
		
		this.container = new ScrollPane(list);
		container.setStyle("-fx-background: #5ce1ff; -fx-padding:15"); // light blue
		
		this.setBottom(sb);
		this.setCenter(container);
		
	}
	
	public void setList(GL[] gls) {
		
		for (int i = 0; i < gls.length; i++) {
			element.GL item = new element.GL(gls[i]);
			list.getChildren().add(item);
		}
	}

}
