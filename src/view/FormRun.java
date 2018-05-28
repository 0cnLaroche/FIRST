package view;

import java.util.ArrayList;

import controler.DataLayer;
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

public class FormRun extends GridPane {

	public FormRun(DataLayer manager, Run run) {

		super();

		// SECTION RUN

		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));

		Label lblRunID = new Label("RUN Code");
		this.add(lblRunID, 0, 0);
		TextField tfRunID = new TextField(run.getId());
		this.add(tfRunID, 1, 0);

		Label lblDescEN = new Label("Description EN");
		this.add(lblDescEN, 0, 1);
		TextField tfDescEN = new TextField(run.getNameEN());
		this.add(tfDescEN, 1, 1);
		
		Label lblDescFR = new Label("Description FR");
		this.add(lblDescFR, 0, 2);
		TextField tfDescFR = new TextField(run.getNameFR());
		this.add(tfDescFR, 1, 2);
		
		Label lblType = new Label("Type");
		this.add(lblType, 0, 3);
		TextField tfType = new TextField(run.getType()); // TODO : Change that to scrolling list
		this.add(tfType, 1, 3);
		
		Label lblCostCenter = new Label("Cost Center");
		this.add(lblCostCenter, 0, 4);
		TextField tfCostCenter = new TextField(run.getCostcenter().getId());
		this.add(tfCostCenter, 1, 4);

		Label lblApprover = new Label("Responsible");
		this.add(lblApprover, 0, 5);
		TextField tfApprover = new TextField(run.getResponsible());
		this.add(tfApprover, 1, 5);
		
		// TODO : Add CSD and Service ID


		Button btn = new Button("Go");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println("Edit run");

			}
		});
		
		this.add(btn, 1, 6);
	}
}
