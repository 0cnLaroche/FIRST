package view;

import java.util.ArrayList;
import controler.*;
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
import model.Run;

public class GridRun extends GridPane {
	
	public GridRun() {
		super();

		// SECTION RUN

		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));


		Label lblRunID = new Label("RUN Code");
		this.add(lblRunID, 0, 0);

		Label lblCostCenter = new Label("Cost Center");
		this.add(lblCostCenter, 0, 1);

		Label lblApprover = new Label("Approver");
		this.add(lblApprover, 0, 2);
		
		Label lblKeyword = new Label("Keyword");
		this.add(lblKeyword, 0, 3);

		TextField tfRunID = new TextField();
		this.add(tfRunID, 1, 0);

		TextField tfCostCenter = new TextField();
		this.add(tfCostCenter, 1, 1);

		TextField tfApprover = new TextField();
		this.add(tfApprover, 1, 2);
		
		TextField tfKeyword = new TextField();
		this.add(tfKeyword, 1, 3);


		Button btn = new Button("Go");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println("Find run");
				try {
					RUNReport report;
					if (!tfRunID.getText().equals("")) {
						ArrayList<Run> res = new ArrayList<Run>();
						res.add(DataLayer.getRun(tfRunID.getText()));
						report = new RUNReport(res);
						report.setTitle("RUN codes");
						report.show();
					} else {
						
						report = new RUNReport(DataLayer.queryRuns(tfKeyword.getText(), tfApprover.getText(), tfCostCenter.getText()));
						report.setTitle("RUN codes");
						report.show();
					}
				} catch (NotFoundException ex) {
					// TODO : Afficher introuvable dans une nouvelle fenêtre 
				}
			}
		});
		this.add(btn, 1, 4);
	}
}
