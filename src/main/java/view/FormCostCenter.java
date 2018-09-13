package view;

import java.time.LocalDate;
import java.util.Optional;

import controler.Admin;
import controler.DataLayer;
import controler.NotFoundException;
import first.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.CostCenter;

public class FormCostCenter extends GridPane {
	
	TextField tfID, tfDescEN, tfDescFR, tfManager, tfDirectorate, tfReportTo;
	DatePicker datePicker;
	Label lbReportTo, lbParent;
	CostCenter cc;
	FormCostCenter me = this;
	EventHandler<ActionEvent> hnew, hedit;
	Button btn;
	
	public FormCostCenter(App main) {

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
		
		this.add(new Label("Reporting Cost Center"), 0, 5);
		tfReportTo = new TextField();
		this.add(tfReportTo, 1, 5);
		
		this.add(new Label("Closing Date"), 0, 6);
		datePicker = new DatePicker();
		this.add(datePicker, 1, 6);
		
		lbParent = new Label();
		this.add(lbParent, 2, 5);
		
		hnew = 	new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				
				if (main.getAdministrationModule().isAdmin()) {
					
					tfID.setEditable(true);
					tfDescEN.setEditable(true);
					tfDescFR.setEditable(true);
					tfManager.setEditable(true);
					tfDirectorate.setEditable(true);
					datePicker.setEditable(true);
					

						try {
							CostCenter newcc = new CostCenter();
							newcc.setId(tfID.getText());
							newcc.setNameEN(tfDescEN.getText());
							newcc.setNameFR(tfDescFR.getText());
							newcc.setManager(tfManager.getText());
							newcc.setClosingDate(datePicker.getValue());
							newcc.setDirectorate(tfDirectorate.getText()); // TODO : Create a choiceBox
							CostCenter parent = DataLayer.getCostCenter(tfReportTo.getText());
							newcc.setParent(parent);
							lbParent.setText(parent.getNameEN() + " - " + parent.getManager());
							if (cc == null) {
								newcc.setEffectiveDate(LocalDate.now());
							} else {
								newcc.setEffectiveDate(cc.getEffectiveDate());
							}

						
							main.getManager().insertCostCenter(newcc);
							
							main.getCostCenterModule().load();


							
						} catch (NotFoundException e1) { // Will never get triggered since it is checked already
							lbParent.setText("Invalid");
							lbParent.setStyle("-fx-text-fill: red;");
						}
				
				} else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Admin Dialog");
					alert.setHeaderText("Hey, seems like you don't have access to change that");
					alert.setContentText("You can contact your CATS Administration Team to request a change. "
							+ "For Admins, click on the lock button to enter your credentials.");
					alert.showAndWait();
				}
				

			}
		};
				
		hedit = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				
				if (main.getAdministrationModule().isAdmin()) {
					
					tfID.setEditable(true);
					tfDescEN.setEditable(true);
					tfDescFR.setEditable(true);
					tfManager.setEditable(true);
					tfDirectorate.setEditable(true);
					datePicker.setEditable(true);
					
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confirmation Dialog");
					alert.setHeaderText("Updating Cost Center");
					alert.setContentText("Are you sure you want to update this cost center?");

					Optional<ButtonType> result = alert.showAndWait();
					
					if (result.get() == ButtonType.OK) {

						try {
							CostCenter newcc = new CostCenter();
							newcc.setId(tfID.getText());
							newcc.setNameEN(tfDescEN.getText());
							newcc.setNameFR(tfDescFR.getText());
							newcc.setManager(tfManager.getText());
							newcc.setClosingDate(datePicker.getValue());
							newcc.setDirectorate(tfDirectorate.getText()); // TODO : Create a choiceBox
							CostCenter parent = DataLayer.getCostCenter(tfReportTo.getText());
							newcc.setParent(parent);
							lbParent.setText(parent.getNameEN() + " - " + parent.getManager());
							if (cc == null) {
								newcc.setEffectiveDate(LocalDate.now());
							} else {
								newcc.setEffectiveDate(cc.getEffectiveDate());
							}

							
							DataLayer.updateCostCenter(newcc);
							
							main.getCostCenterModule().load();


							
						} catch (NotFoundException e1) { // Will never get triggered since it is checked already
							lbParent.setText("Invalid");
							lbParent.setStyle("-fx-text-fill: red;");
						}

						

						
					} else {
						
					}
				
				} else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Admin Dialog");
					alert.setHeaderText("Hey, seems like you don't have access to change that");
					alert.setContentText("You can contact your CATS Administration Team to request a change. "
							+ "For Admins, click on the lock button to enter your credentials.");
					alert.showAndWait();
				}
				

			}
		};

		btn = new Button("Create");
		btn.setOnAction(hnew);
		this.add(btn, 1, 7);
	}
	public void edit(CostCenter cc) {
		
		this.cc = cc;
		
		/*if (!Admin.isAdmin()) {
			tfID.setEditable(false);
			tfDescEN.setEditable(false);
			tfDescFR.setEditable(false);
			tfManager.setEditable(false);
			tfDirectorate.setEditable(false);
			datePicker.setEditable(false);
		}*/
		
		tfID.setText(cc.getId());
		tfDescEN.setText(cc.getNameEN());
		tfDescFR.setText(cc.getNameFR());
		tfManager.setText(cc.getManager());
		tfDirectorate.setText(cc.getDirectorate());
		if (cc.getParent() != null) {
			tfReportTo.setText(cc.getParent().getId());
			lbParent.setText(cc.getParent().getNameEN() + " - " + cc.getParent().getManager());
		}

		datePicker.setValue(cc.getClosingDate());
		btn.setOnAction(hedit);

	}
	private boolean checkParent() {
		try {
			CostCenter parent = DataLayer.getCostCenter(tfReportTo.getText());
			lbParent.setText(parent.getNameEN() + " - " + parent.getManager());
			return true;
		} catch (NotFoundException e){
			lbParent.setText("Invalid");
			lbParent.setStyle("-fx-text-fill: red;");
			return false;
		}
		

	}
	public CostCenter getCostCenter() {
		return cc;
	}
}
