package view;

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

public class GridProjets extends GridPane {
	
	public GridProjets(DataLayer manager) {
		super();

		// SECTION PROJETS

		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));

		Label lblProjectID = new Label("Project Code");
		this.add(lblProjectID, 0, 0);

		Label lblProposal = new Label("Proposal");
		this.add(lblProposal, 0, 1);

		Label lblNetwork = new Label("Network");
		this.add(lblNetwork, 0, 2);

		Label lblApprover = new Label("Approver");
		this.add(lblApprover, 0, 3);

		Label lblProjectName = new Label("Project Name");
		this.add(lblProjectName, 0, 4);

		TextField tfProjectID = new TextField();
		this.add(tfProjectID, 1, 0);

		TextField tfProposal = new TextField();
		this.add(tfProposal, 1, 1);

		TextField tfNetwork = new TextField();
		this.add(tfNetwork, 1, 2);

		TextField tfApprover = new TextField();
		this.add(tfApprover, 1, 3);

		TextField tfProjectName = new TextField();
		this.add(tfProjectName, 1, 4);

		Button btn = new Button("Go");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println("Find project");
				//System.out.println(manager.getProject(tfProjectID.getText()));
				Report report = new Report(manager.getProject(tfProjectID.getText()).toString());
				report.setTitle("Projects");
				
				report.show();
			}
		});
		this.add(btn, 1, 5);
	}
}
