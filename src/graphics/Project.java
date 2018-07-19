package graphics;



import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import view.FormProject;

public class Project extends Sprite {
	
	private model.Project prj;
	private Text name, model, proposal, id;
	private Canvas canvas;
	private GraphicsContext gc;
	
	private String paneStyle = "-fx-padding:10;";
	private String textStyle = "-fx-fill: black; -fx-font: 35px Tahoma;";
	
	public Project() {
		super();
		padding = 15;
		this.setDimension(200, 100);
		//this.setMinHeight(150);
		canvas = new Canvas(this.getWidth() + padding, this.getHeight() + padding);
		gc = canvas.getGraphicsContext2D();
		
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
		DropShadow shadow = new DropShadow();
		
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(25);
		grid.setVgap(5);
		grid.setPadding(new Insets(20,10,5,10));
		
		
		name = new Text(prj.getNameEN());
		name.setStyle(textStyle);
		grid.add(name, 0, 0);
		
		model = new Text();
		model.setStyle(textStyle);
		grid.add(model, 0, 1);
		
		proposal = new Text(prj.getProposal());
		proposal.setStyle(textStyle);
		grid.add(proposal, 0, 2);
		
		id = new Text(prj.getId());
		id.setStyle(textStyle);
		grid.add(id, 1, 0);
		
		// grid.setStyle("-fx-border-color:purple;-fx-border-style:solid;-fx-border-radius:20;");
		
		grid.setMinHeight(125);
		
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
				FormProject form = new FormProject();
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
