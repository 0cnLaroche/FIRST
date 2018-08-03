package view;

import java.util.Optional;

import controler.Admin;
import controler.DataLayer;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import model.CostCenter;
import model.FinancialCode;
import model.Run;

public class FormRun extends GridPane {
	
	TextField tfRunID, tfDescEN, tfDescFR, tfCostCenter, tfApprover, tfReplacedBy;
	ChoiceBox<String> cbType,cbStatus; // TODO: Implement
	DatePicker datePicker;
	Run run;
	private FormRun me = this;
	
	public FormRun() {

		super();

		// SECTION RUN

		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));

		this.add(new Label("RUN Code"), 0, 0);
		tfRunID = new TextField();
		this.add(tfRunID, 1, 0);

		this.add(new Label("Description EN"), 0, 1);
		tfDescEN = new TextField();
		this.add(tfDescEN, 1, 1);
		
		this.add(new Label("Description FR"), 0, 2);
		tfDescFR = new TextField();
		this.add(tfDescFR, 1, 2);
		
		this.add(new Label("Type"), 0, 3);
		cbType = new ChoiceBox<String>(); // TODO: Replace with a dropdown list
		cbType.setItems(FXCollections.observableArrayList(Run.MAINTENANCE,Run.SERVICE,Run.BUSINESSMANAGEMENT,
				Run.INVESTMENT));
		this.add(cbType, 1, 3);
		
		this.add(new Label("Cost Center"), 0, 4);
		tfCostCenter = new TextField();
		this.add(tfCostCenter, 1, 4);

		this.add(new Label("Responsible"), 0, 5);
		tfApprover = new TextField();
		this.add(tfApprover, 1, 5);
		
		this.add(new Label("Replaced By"), 0, 6);
		tfReplacedBy = new TextField();
		this.add(tfReplacedBy, 1, 6);
		
		this.add(new Label("Status"), 0, 7);
		cbStatus = new ChoiceBox<String>(); // TODO: Replace with a dropdown list
		cbStatus.setItems(FXCollections.observableArrayList("Unreleased", "Active", "Closed"));
		this.add(cbStatus, 1, 7);
		
		
		this.add(new Label("Closing Date"), 0, 8);
		datePicker = new DatePicker();
		this.add(datePicker, 1, 8);
		
		// TODO : Add CSD and Service ID


		Button btn = new Button("Go");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println("Edit run");
				
				if (Admin.isAdmin()) {
					
					if (me.run != null ) {
						
						Run run = me.run;
						
						tfRunID.setEditable(true);
						tfDescEN.setEditable(true);
						tfDescFR.setEditable(true);
						// cbType // TODO : can disable apparently
						tfCostCenter.setEditable(true);
						tfApprover.setEditable(true);
						tfReplacedBy.setEditable(true);
						// cbStatus
						datePicker.setEditable(true);
						
						run.setNameEN(me.tfDescEN.getText());
						run.setNameFR(me.tfDescFR.getText());
						CostCenter cc = new CostCenter();
						cc.setId(me.tfCostCenter.getText());
						run.setCostcenter(cc);
						run.setResponsible(me.tfApprover.getText());
						run.setStatus(me.cbStatus.getValue()); 
						run.setType(me.cbType.getValue()); // this should be changed once we switch to choice box instead
						run.setReplacedBy(me.tfReplacedBy.getText());
						run.setClosingDate(datePicker.getValue());
						
						DataLayer.updateRun(run);
						System.out.println("Update Run Success");
					}
					
				} else {
					Admin.showLoginDialog();
				}
				
				
				

			}
		});
		
		this.add(btn, 1, 9);
	}
	public void edit(Run run) {
		
		this.run = run;
		
		if (Admin.isAdmin()) {
			tfRunID.setEditable(false);
			tfDescEN.setEditable(false);
			tfDescFR.setEditable(false);
			// cbType // TODO : can disable apparently
			tfCostCenter.setEditable(false);
			tfApprover.setEditable(false);
			tfReplacedBy.setEditable(false);
			// cbStatus
			datePicker.setEditable(false);
		}

		
		tfRunID.setText(run.getId());
		tfDescEN.setText(run.getNameEN());
		tfDescFR.setText(run.getNameFR());
		cbType.setValue(run.getType()); // TODO : Change that to scrolling list
		tfCostCenter.setText(run.getCostcenter().getId());
		tfApprover.setText(run.getResponsible());
		tfReplacedBy.setText(run.getReplacedBy());
		cbStatus.setValue(run.getStatus());
		datePicker.setValue(run.getClosingDate());

	}
}
