package view;

import controler.Admin;
import controler.DataLayer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.CostCenter;

public class FormCostCenter extends GridPane {
	
	TextField tfID, tfDescEN, tfDescFR, tfManager, tfDirectorate;
	DatePicker datePicker;
	CostCenter cc;
	
	public FormCostCenter() {

		super();

		// SECTION RUN

		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));

		this.add(new Label("Cost Center"), 0, 0);
		tfID = new TextField();
		this.add(tfID, 1, 0);

		this.add(new Label("Description EN"), 0, 1);
		tfDescEN = new TextField();
		this.add(tfDescEN, 1, 1);
		
		this.add(new Label("Description FR"), 0, 2);
		tfDescFR = new TextField();
		this.add(tfDescFR, 1, 2);

		this.add(new Label("Manager"), 0, 3);
		tfManager = new TextField();
		this.add(tfManager, 1, 3);
		
		this.add(new Label("Directorate"), 0, 4);
		tfDirectorate = new TextField();
		this.add(tfDirectorate, 1, 4);
		
		this.add(new Label("Closing Date"), 0, 5);
		datePicker = new DatePicker();
		this.add(datePicker, 1, 5);
		
		// TODO : - Add CSD and Service ID
		//	- Add status indication after successful update


		Button btn = new Button("Update");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				
				if (Admin.isAdmin()) {
					
					tfID.setEditable(true);
					tfDescEN.setEditable(true);
					tfDescFR.setEditable(true);
					tfManager.setEditable(true);
					tfDirectorate.setEditable(true);
					datePicker.setEditable(true);
					
					CostCenter newcc = new CostCenter();
					newcc.setId(tfID.getText());
					newcc.setNameEN(tfDescEN.getText());
					newcc.setNameFR(tfDescFR.getText());
					newcc.setManager(tfManager.getText());
					newcc.setClosingDate(datePicker.getValue());
					newcc.setDirectorate(cc.getDirectorate()); // TODO : Create a choiceBox
					newcc.setParent(cc.getParent());
					newcc.setEffectiveDate(cc.getEffectiveDate());
					
					DataLayer.updateCostCenter(newcc);
					System.out.println("Update Cost Center Success");
				
				} else {
					Admin.showLoginDialog();
				}
				

			}
		});
		
		this.add(btn, 1, 6);
	}
	public void edit(CostCenter cc) {
		
		this.cc = cc;
		
		if (!Admin.isAdmin()) {
			tfID.setEditable(false);
			tfDescEN.setEditable(false);
			tfDescFR.setEditable(false);
			tfManager.setEditable(false);
			tfDirectorate.setEditable(false);
			datePicker.setEditable(false);
		}
		
		tfID.setText(cc.getId());
		tfDescEN.setText(cc.getNameEN());
		tfDescFR.setText(cc.getNameFR());
		tfManager.setText(cc.getManager());
		tfDirectorate.setText(cc.getDirectorate());
		datePicker.setValue(cc.getClosingDate());

	}
}
