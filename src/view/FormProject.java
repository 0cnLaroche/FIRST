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

	public FormProject(DataLayer manager, Project pj) {

		super();

		// SECTION RUN

		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));

		Label lblRunID = new Label("Project ID");
		this.add(lblRunID, 0, 0);
		TextField tfRunID = new TextField(pj.getId());
		this.add(tfRunID, 1, 0);

		Label lblDescEN = new Label("Description EN");
		this.add(lblDescEN, 0, 1);
		TextField tfDescEN = new TextField(pj.getNameEN());
		this.add(tfDescEN, 1, 1);
		
		Label lblDescFR = new Label("Description FR");
		this.add(lblDescFR, 0, 2);
		TextField tfDescFR = new TextField(pj.getNameFR());
		this.add(tfDescFR, 1, 2);
		
		Label lblType = new Label("Governance");
		this.add(lblType, 0, 3);
		TextField tfType = new TextField(pj.getType()); // TODO : Change that to scrolling list
		this.add(tfType, 1, 3);
		
		Label lblProposal = new Label("Proposal #");
		this.add(lblProposal, 0, 4);
		TextField tfProposal = new TextField();
		this.add(tfProposal, 1, 4);

		Label lblApprover = new Label("Responsible");
		this.add(lblApprover, 0, 5);
		TextField tfApprover = new TextField();
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
}
