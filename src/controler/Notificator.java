package controler;

import java.util.Timer;
import java.util.TimerTask;

import element.Notification;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Notificator {
	
	private VBox stack;
	
	public Notificator (VBox vbox) {
		this.stack = vbox;

	}
	
	public void add(Notification notif) {
		stack.getChildren().add(notif);
		stack.setSpacing(5.0);

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
				  Platform.runLater(() -> {
					  
					  System.out.println("starting timer");
					  
						FadeTransition ft = new FadeTransition(Duration.millis(5*1000), notif);
						ft.setFromValue(1.0);
						ft.setToValue(0.0);
						ft.play();
						
						ft.setOnFinished(new EventHandler<ActionEvent> () {

							@Override
							public void handle(ActionEvent e) {
								stack.getChildren().remove(notif);
								
							}
							
						});

				  });
				  }
					  


			}, 1*5*1000);
		

	}
	
	
}

