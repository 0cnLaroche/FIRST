package element;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import controler.DataLayer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import csd.Solution;
import first.FIRST;
import csd.Allocation;
import csd.CorporateSolutionDirectory;
/**
 * View that contains the mapping of CSD solution to run codes and their weights
 * @author samuel.laroche
 *
 */
public class SolutionMap extends Group {
	
	private VBox list;
	private ArrayList<Allocation> allocation;
	private Button addbtn;
	private CorporateSolutionDirectory csd;
	private model.Run run;
	private boolean manAllocation;
	private TextField newidf;
	private TextField newweight;
	private boolean admin;
	private FIRST main;
	
	
	public SolutionMap(FIRST main) {
		this.main = main;
		this.allocation = new ArrayList<Allocation>(5);
		this.admin = false;
		
		this.csd = new CorporateSolutionDirectory();
		this.list = new VBox();
		list.setStyle("	-fx-border-color: #cccccc;" + 
				"-fx-background-color: #FFFFFF;" + 
				"-fx-border-radius: 4;" + 
				"-fx-background-radius: 4;" + 
				"-fx-effect: innershadow(gaussian, transparent, 0, 0, 0, 0);");
		list.setPadding(new Insets(5.0));
		list.setSpacing(2.5);
		this.addbtn = new Button("add");
		
		addbtn.setOnAction((event) -> {
			if (main.getAdministrationModule().isAdmin()) {
				list.getChildren().remove(addbtn);
				this.addBlank();
				list.getChildren().add(addbtn);
			}

		});
		
		HBox head = new HBox();
		head.setSpacing(10.0);
		
		Label idl = new Label("csd #");
		idl.setPrefWidth(45.0);
		idl.setAlignment(Pos.CENTER);
		Label name = new Label("Solution");
		name.setPrefWidth(200);
		name.setAlignment(Pos.CENTER_LEFT);
		Label w = new Label("Weight");
		w.setAlignment(Pos.CENTER_LEFT);
		
		head.getChildren().addAll(idl, name, w);
		
		list.getChildren().add(head);
		list.getChildren().add(addbtn);

		this.getChildren().add(list);

	}
	/**
	 * Adds a line for each solution allocated to the RUN code.
	 * @param map Array of allocation
	 */
	public void set(Allocation[] map) {
		
		list.getChildren().remove(addbtn);
		
		for (int i = 0; i < map.length; i++) {
			allocation.add(map[i]);
			add(map[i]);
			
		}
		
		list.getChildren().add(addbtn);
	}
	/**
	 * Adds an allocation line to view.
	 * @param a Allocation
	 */
	public void add(Allocation a) {
		
		Label idf;
		Label name, w;
		Button del;
		HBox box;
		Solution sol;
		
		try {
			sol = csd.getSolution(a.solutionId);
		} catch (IOException e) {
			System.err.println(e.getMessage());;
			sol = new Solution();
			sol.setId(a.solutionId);
			sol.setName("");
		}

		box = new HBox();
		box.setSpacing(10.0);
		
		idf = new Label(Integer.toString(sol.getId()));
		idf.setPrefWidth(45.0);
		idf.setAlignment(Pos.CENTER);
		idf.setOnMouseClicked((event) -> {
			csd.openInBrowser(a.solutionId);
		});

		name = new Label(sol.getName());
		name.setPrefWidth(200);
		name.setAlignment(Pos.CENTER_LEFT);
		name.setOnMouseClicked((event) -> {
			csd.openInBrowser(a.solutionId);
		});
		name.setOnMouseEntered((event) -> {
			this.getScene().setCursor(Cursor.HAND);
			idf.setStyle("-fx-underline: true;"); //  -fx-text-decoration-color:#66afe9"
			name.setStyle("-fx-underline: true;");
		});
		name.setOnMouseExited((event) -> {
			this.getScene().setCursor(Cursor.DEFAULT);
			idf.setStyle("");
			name.setStyle("");
		});
		idf.setOnMouseEntered((event) -> {
			this.getScene().setCursor(Cursor.HAND);
			idf.setStyle("-fx-underline: true;"); //  -fx-text-decoration-color:#66afe9"
			name.setStyle("-fx-underline: true;");
		});
		idf.setOnMouseExited((event) -> {
			this.getScene().setCursor(Cursor.DEFAULT);
			idf.setStyle("");
			name.setStyle("");
		});
		w = new Label(Double.toString(a.weight));
		w.setAlignment(Pos.CENTER_LEFT);
		w.setPrefWidth(30.0);
		del = new Button("x");
		
		del.setOnAction((event) -> {
			// FIXME DELETE command denied to user 'firstadmin'@'n30013192.hrdc-drhc.net' for table 'RUN_Solution'
			if (main.getAdministrationModule().isAdmin()) {
				
				list.getChildren().remove(box);

				try {
					DataLayer.deleteCsdMapping(a);
					main.notify("Deleted mapping for CSD ID# " + a.solutionId);
				} catch (SQLException e) {
					System.err.println(e.getMessage());
				}
				
			}

			
		});	
		box.getChildren().addAll(idf, name, w, del);
		
		list.getChildren().add(box);
		
	}
	/**
	 * Adds a blank space to allow user to enter a new allocation
	 */
	public void addBlank() {
		
		TextField idf, w;
		Label name;
		Button del;
		HBox box;
		Solution sol;

		box = new HBox();
		box.setSpacing(10.0);
		
		newidf = new TextField();
		newidf.setPrefWidth(45.0);
		newidf.setAlignment(Pos.CENTER);
		name = new Label();
		name.setPrefWidth(200);
		name.setAlignment(Pos.CENTER_LEFT);
		newweight = new TextField();
		//w.setPrefWidth(15.0);
		//w.setAlignment(Pos.CENTER_LEFT);
		del = new Button("x");
		
		del.setOnAction((event) -> {
			// TODO: Update db to remove mapping
			// TODO: Check if Admin rights
			list.getChildren().remove(box);
		});
		
		box.getChildren().addAll(newidf, name, newweight, del);
		
		list.getChildren().add(box);
		
	}
	public void save() throws SQLException {
				
				Allocation a = new Allocation();
				a.solutionId = Integer.parseInt(newidf.getText());
				a.weight = Double.parseDouble(newweight.getText());
				a.runId = this.run.getId();

				DataLayer.insertCsdMapping(a);
		
	}
	public void setRun(model.Run run) {
		this.run = run;
	}
	public void edit(boolean admin) {
		this.admin = admin;
	}

}


