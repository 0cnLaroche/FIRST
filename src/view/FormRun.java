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
import model.Run;

public class FormRun extends GridPane {
	
	TextField tfRunID, tfDescEN, tfDescFR, tfType, tfCostCenter, tfApprover;
	DatePicker datePicker;
	
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
		tfType = new TextField(); // TODO : Change that to scrolling list
		this.add(tfType, 1, 3);
		
		this.add(new Label("Cost Center"), 0, 4);
		tfCostCenter = new TextField();
		this.add(tfCostCenter, 1, 4);

		this.add(new Label("Responsible"), 0, 5);
		tfApprover = new TextField();
		this.add(tfApprover, 1, 5);
		
		this.add(new Label("Closing Date"), 0, 7);
		datePicker = new DatePicker();
		this.add(datePicker, 1, 7);
		
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
	public void edit(Run run) {
		
		tfRunID.setText(run.getId());
		tfDescEN.setText(run.getNameEN());
		tfDescFR.setText(run.getNameFR());
		tfType.setText(run.getType()); // TODO : Change that to scrolling list
		tfCostCenter.setText(run.getCostcenter().getId());
		tfApprover.setText(run.getResponsible());
		datePicker.setValue(run.getClosingDate());

	}
}
