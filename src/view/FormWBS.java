package view;

import java.time.LocalDate;
import java.util.ArrayList;

import controler.Admin;
import controler.DataLayer;
import controler.NotFoundException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.*;

public class FormWBS extends GridPane {
	
	TextField tfNetwork, tfWbsID, tfDescEN, tfDescFR, tfCostCenter, tfApprover;
	ChoiceBox<Byte> cbStage;
	ChoiceBox<String> cbStatus;
	DatePicker datePicker;
	FormWBS me = this;
	Network nw;
	

	public FormWBS() {

		super();

		// SECTION WBS and Network

		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));
		
		this.add(new Label("Network"),0,0);
		tfNetwork = new TextField();
		this.add(tfNetwork, 1, 0);
		
		this.add(new Label("Parent WBS"), 3, 0);
		tfWbsID = new TextField();
		this.add(tfWbsID, 4, 0);

		this.add(new Label("Description EN"), 0, 1);
		tfDescEN = new TextField();
		this.add(tfDescEN, 1, 1);
		
		this.add(new Label("Description FR"), 0, 2);
		tfDescFR = new TextField();
		this.add(tfDescFR, 1, 2);
		
		this.add(new Label("Stage"), 0, 3);
		cbStage = new ChoiceBox<Byte>();
		cbStage.setItems(FXCollections.observableArrayList((byte)0,(byte)1,(byte)2,(byte)3,(byte)4,(byte)5));
		this.add(cbStage, 1, 3);
		
		this.add(new Label("Cost Center"), 0, 4);
		tfCostCenter = new TextField();
		this.add(tfCostCenter, 1, 4);

		this.add(new Label("Responsible"), 0, 5);
		tfApprover = new TextField();
		this.add(tfApprover, 1, 5);
		
		this.add(new Label("Status"),0,6);
		cbStatus = new ChoiceBox<String>(); // TODO: Replace with a dropdown list
		cbStatus.setItems(FXCollections.observableArrayList("Unreleased", "Active", "Closed"));
		this.add(cbStatus, 1, 6);
		
		this.add(new Label("Closing Date"),0,7);
		datePicker = new DatePicker();
		this.add(datePicker, 1, 7);
		
		
		// TODO : Add CSD and Service ID

	}
	
	public void edit(Network nw) {
		this.nw = nw;
		tfNetwork.setText(nw.getId());
		tfWbsID.setText(nw.getWbs().getId());
		tfDescEN.setText(nw.getNameEN());
		tfDescFR.setText(nw.getNameFR());
		cbStage.setValue(nw.getWbs().getStage());
		tfCostCenter.setText(nw.getWbs().getCostcenter().getId());
		tfApprover.setText(nw.getWbs().getApprover());
		datePicker.setValue(nw.getClosingDate());
		cbStatus.setValue(nw.getStatus().toString());
		
		Button btn = new Button("Save");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				
				if (Admin.isAdmin()) {
					
					if (me.nw != null ) {
						
						model.Network nw = new Network();
						nw.setId(tfNetwork.getText());
						nw.setNameEN(tfDescEN.getText());
						nw.setNameFR(tfDescFR.getText());
						nw.setClosingDate(datePicker.getValue());
						nw.setEffectiveDate(LocalDate.now());
						nw.setStatus(cbStatus.getValue());
			
						model.Wbs wbs = new model.Wbs();
						wbs.setId(tfWbsID.getText());
						wbs.setNameEN(tfDescEN.getText());
						wbs.setNameFR(tfDescFR.getText());
						wbs.setApprover(tfApprover.getText());
						wbs.setEffectiveDate(LocalDate.now());
						wbs.setClosingDate(datePicker.getValue());
						wbs.setStage(cbStage.getValue());
						wbs.setStatus(cbStatus.getValue());
						
						
						try {
							wbs.setCostcenter(DataLayer.getCostCenter(tfCostCenter.getText()));
						} catch (NotFoundException e1) {
							// TODO Auto-generated catch block
							System.err.println(e);
						}
						
						nw.setWbs(wbs);
						wbs.addNetwork(nw);

						try {
							wbs.setProject(DataLayer.getProject(wbs.getId().substring(0, 6)));
							// REmplacer par quelque chose de plus fiable. Dropdown List?
						} catch (NotFoundException e1) {
							System.err.println(e);
						}
						
						DataLayer.updateProject(nw);

						System.out.println("Update Project Success");
					}
					
				} else {
					Admin.showLoginDialog();
				}
				


			}
		});
		
		this.add(btn, 1, 8);

		
	}
}
