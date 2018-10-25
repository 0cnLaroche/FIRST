package element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import csd.Solution;
import csd.Allocation;
import csd.CorporateSolutionDirectory;

public class SolutionMap extends Group {
	
	VBox list;
	ArrayList<Solution> solutions;
	Button addbtn;
	CorporateSolutionDirectory csd;
	boolean manAllocation;
	
	public SolutionMap() {
		
		csd = new CorporateSolutionDirectory();
		list = new VBox();
		list.setStyle("	-fx-border-color: #cccccc;" + 
				"-fx-background-color: #FFFFFF;" + 
				"-fx-border-radius: 4;" + 
				"-fx-background-radius: 4;" + 
				"-fx-effect: innershadow(gaussian, transparent, 0, 0, 0, 0);");
		addbtn = new Button("+");
		
		addbtn.setOnAction((event) -> {

		});
		
		HBox box = new HBox();
		box.setSpacing(10.0);
		
		Label idf = new Label("csd #");
		idf.setPrefWidth(45.0);
		idf.setAlignment(Pos.CENTER);
		Label name = new Label("Solution");
		name.setPrefWidth(200);
		name.setAlignment(Pos.CENTER_LEFT);
		Label w = new Label("Weight");
		w.setAlignment(Pos.CENTER_LEFT);
		
		box.getChildren().addAll(idf, name, w);
		
		list.getChildren().add(box);
		
		//this.getChildren().add(addbtn);

	}
	public void set(Allocation[] map) {
		
		for (int i = 0; i < map.length; i++) {

			add(map[i].solutionId, map[i].weight);
		}
		
		this.getChildren().add(list);
	}
	public void add(int id, double weight) {
		
		Label idf;
		Label name, w;
		Button del;
		HBox box;
		Solution sol;
		
		try {
			sol = csd.getSolution(id);
		} catch (IOException e) {
			System.err.println(e.getMessage());;
			sol = new Solution();
			sol.setId(id);
			sol.setName("not found");
		}

		box = new HBox();
		box.setSpacing(10.0);
		
		idf = new Label(Integer.toString(sol.getId()));
		idf.setPrefWidth(45.0);
		idf.setAlignment(Pos.CENTER);
		name = new Label(sol.getName());
		name.setPrefWidth(200);
		name.setAlignment(Pos.CENTER_LEFT);
		w = new Label(Double.toString(weight));
		w.setAlignment(Pos.CENTER_LEFT);
		del = new Button("x");
		
		del.setOnAction((event) -> {
			// TODO: Update db to remove mapping
			// TODO: Check if Admin rights
			list.getChildren().remove(box);
		});
		
		box.getChildren().addAll(idf, name, w, del);
		
		list.getChildren().add(box);
		
	}
	public void add() {
		
		TextField idf;
		Label name, w;
		Button del;
		HBox box;
		Solution sol;

		box = new HBox();
		box.setSpacing(10.0);
		
		idf = new TextField();
		idf.setPrefWidth(45.0);
		idf.setAlignment(Pos.CENTER);
		name = new Label();
		name.setPrefWidth(200);
		name.setAlignment(Pos.CENTER_LEFT);
		w = new Label();
		w.setAlignment(Pos.CENTER_LEFT);
		del = new Button("x");
		
		del.setOnAction((event) -> {
			// TODO: Update db to remove mapping
			// TODO: Check if Admin rights
			list.getChildren().remove(box);
		});
		
		box.getChildren().addAll(idf, name, w, del);
		
		list.getChildren().add(box);
		
	}

}


