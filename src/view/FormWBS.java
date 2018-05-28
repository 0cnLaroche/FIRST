package view;

import java.util.ArrayList;

import controler.DataLayer;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Project;
import model.Wbs;

public class FormWBS extends GridPane {
	
	Label lblwbsID, lblDescEN, lblDescFR, lblStage, lblCostCenter, lblApprover;
	TextField tfWbsID, tfDescEN, tfDescFR, tfCostCenter, tfApprover;
	ChoiceBox<Byte> cbStage;
	

	public FormWBS(DataLayer manager, Project pj) {

		super();

		// SECTION WBS and Network

		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));

		lblwbsID = new Label("WBS ID");
		this.add(lblwbsID, 0, 0);
		tfWbsID = new TextField();
		this.add(tfWbsID, 1, 0);

		lblDescEN = new Label("Description EN");
		this.add(lblDescEN, 0, 1);
		tfDescEN = new TextField();
		this.add(tfDescEN, 1, 1);
		
		lblDescFR = new Label("Description FR");
		this.add(lblDescFR, 0, 2);
		tfDescFR = new TextField();
		this.add(tfDescFR, 1, 2);
		
		lblStage = new Label("Stage");
		this.add(lblStage, 0, 3);
		cbStage = new ChoiceBox<Byte>();
		cbStage.setItems(FXCollections.observableArrayList((byte)0,(byte)1,(byte)2,(byte)3,(byte)4,(byte)5));
		this.add(cbStage, 1, 3);
		
		lblCostCenter = new Label("Cost Center");
		this.add(lblCostCenter, 0, 4);
		tfCostCenter = new TextField();
		this.add(tfCostCenter, 1, 4);

		lblApprover = new Label("Responsible");
		this.add(lblApprover, 0, 5);
		tfApprover = new TextField();
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
	
	public void edit(Wbs wbs) {
		tfWbsID.setText(wbs.getId());
		tfDescEN.setText(wbs.getNameEN());
		tfDescFR.setText(wbs.getNameFR());
		cbStage.setValue(wbs.getStage());
		tfCostCenter.setText(wbs.getCostcenter().getId());
		tfApprover.setText(wbs.getApprover());

		
	}
}
