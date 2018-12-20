package first;

import javafx.application.Preloader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * Show a loading animation while the main application loads
 * @author samuel.laroche
 *
 */
public class Splash extends Preloader {
	
	Stage stage;
	ProgressBar pb;
	
	@Override
	public void start(Stage stage) throws Exception {
		
		this.stage = stage;
		
		pb = new ProgressBar();

		VBox vb = new VBox();
		vb.setAlignment(Pos.CENTER);
		vb.getChildren().addAll(
				new ImageView(new Image( FIRST.class.getResourceAsStream("/res/raccoon.png"))),
				new Label("Please wait while FIRST 2 is loading ..."), pb);
	
		vb.setSpacing(10.0);
		stage.setScene(new Scene (vb,300,300));
		stage.initStyle(StageStyle.UTILITY);
		//stage.setAlwaysOnTop(true);
		stage.show();
		
	}
    @Override
    public void handleProgressNotification(ProgressNotification pn) {
        //pb.setProgress(pn.getProgress());
    }
    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
        if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
            stage.hide();
        }
    } 

}
