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
import model.Project;

public class FormProject extends GridPane {
	Label lblProjectID, lblDescEN, lblDescFR, lblType, lblProposal, lblApprover;
	TextField tfProjectID, tfDescEN, tfDescFR, tfType, tfProposal, tfApprover;

	public FormProject() {

		super();

		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));

		lblProjectID = new Label("Project ID");
		this.add(lblProjectID, 0, 0);
		tfProjectID = new TextField();
		this.add(tfProjectID, 1, 0);

		lblDescEN = new Label("Description EN");
		this.add(lblDescEN, 0, 1);
		tfDescEN = new TextField();
		this.add(tfDescEN, 1, 1);
		
		lblDescFR = new Label("Description FR");
		this.add(lblDescFR, 0, 2);
		tfDescFR = new TextField();
		this.add(tfDescFR, 1, 2);
		
		lblType = new Label("Governance");
		this.add(lblType, 0, 3);
		tfType = new TextField(); // TODO : Change that to scrolling list
		this.add(tfType, 1, 3);
		
		lblProposal = new Label("Proposal #");
		this.add(lblProposal, 0, 4);
		tfProposal = new TextField();
		this.add(tfProposal, 1, 4);

		lblApprover = new Label("Responsible");
		this.add(lblApprover, 0, 5);
		tfApprover = new TextField();
		this.add(tfApprover, 1, 5);
		
		// TODO : Add CSD and Service ID


		Button btnwbs = new Button("Add WBS");
		btnwbs.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
			}
		});
		
		this.add(btnwbs, 1, 6);
	}
	public void edit(Project pj) {
		tfProjectID.setText(pj.getId());
		tfDescEN.setText(pj.getNameEN());
		tfDescFR.setText(pj.getNameFR());
		tfType.setText(pj.getModel());
		tfProposal.setText(pj.getProposal());
		// tfApprover.setText();
		
	}
}
