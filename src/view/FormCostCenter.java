package view;

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
	
	TextField tfID, tfDescEN, tfDescFR, tfManager;
	DatePicker datePicker;
	
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
		
		this.add(new Label("Closing Date"), 0, 4);
		datePicker = new DatePicker();
		this.add(datePicker, 1, 4);
		
		// TODO : Add CSD and Service ID


		Button btn = new Button("Go");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println("Edit run");

			}
		});
		
		this.add(btn, 1, 5);
	}
	public void edit(CostCenter cc) {
		
		tfID.setText(cc.getId());
		tfDescEN.setText(cc.getNameEN());
		tfDescFR.setText(cc.getNameFR());
		tfManager.setText(cc.getManager());
		datePicker.setValue(cc.getClosingDate());

	}
}
