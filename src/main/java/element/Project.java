package element;

import first.App;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.FormProject;
import view.ProjectModule;

public class Project extends Sprite {
	private App main;
	private model.Project prj;
	private Text name, model, proposal, id;
	private String paneStyle = "-fx-padding:10;";
	private String textStyle = "-fx-fill: black; -fx-font: 35px Tahoma;";
	
	public Project(App main) {
		super();
		this.main = main;
		padding = 15;
		this.setDimension(200, 100);		
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				// Open network form
				System.out.println(prj.getNameEN());
				
			}
			
		});
	}
	
	public void setSource(model.Project project) {
		
		this.prj = project;
	}

	public void render() {
		
		GridPane grid = new GridPane();
		
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(25);
		grid.setVgap(5);
		grid.setPadding(new Insets(20,10,5,10));
		
		id = new Text(prj.getId());
		id.setStyle(textStyle);
		grid.add(id, 0, 0);
		
		name = new Text(prj.getNameEN());
		name.setStyle(textStyle);
		GridPane.setHgrow(name, Priority.ALWAYS);
		grid.add(name, 1, 0);
		
		model = new Text(prj.getModel());
		model.setStyle(textStyle);
		grid.add(model, 2, 0);
		
		proposal = new Text(prj.getProposal());
		proposal.setStyle(textStyle);
		grid.add(proposal, 3, 0);
		
		// grid.setStyle("-fx-border-color:purple;-fx-border-style:solid;-fx-border-radius:20;");
		
		//grid.setMinHeight(125);
		
		grid.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// grid.setEffect(shadow);
				grid.getScene().setCursor(Cursor.HAND);
				
				
			}
			
		});
		grid.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// grid.setEffect(null);
				grid.getScene().setCursor(Cursor.DEFAULT);
				
			}
			
		});
		
		grid.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				FormProject form = new FormProject(main);
				form.edit(prj);
				Scene scene = new Scene(form,600,600);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.setTitle("Form Project");
				stage.show();
				
			}
			
		});
		this.getChildren().addAll(grid);
		this.setMinHeight(grid.getMinHeight());
		this.setStyle(paneStyle);
		
	}
}
