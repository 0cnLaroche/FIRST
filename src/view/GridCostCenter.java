package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GridCostCenter extends GridPane {
	
	public GridCostCenter() {
		super();

		// SECTION COST CENTER

		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));

		Label lblCostCenter = new Label("Cost Center");
		this.add(lblCostCenter, 0, 0);

		Label lblManager = new Label("Manager");
		this.add(lblManager, 0, 1);

		TextField tfCostCenter = new TextField();
		this.add(tfCostCenter, 1, 0);

		TextField tfManager = new TextField();
		this.add(tfManager, 1, 1);

		Button btn = new Button("Go");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println("Find cost center ");
				try {
					//System.out.println(DataLayer.getCostCenter(tfCostCenter.getText()).toString());
					Scene scene = new Scene(new CostCenterModule());
					Stage report = new Stage();
					report.setScene(scene);
					report.setTitle("Cost Center");
					report.show();
				} catch (Exception e1) {

					e1.printStackTrace();
				}

			}
		});
		this.add(btn, 1, 4);
	}
}
