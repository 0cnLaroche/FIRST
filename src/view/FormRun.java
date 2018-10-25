package view;

import java.sql.SQLException;
import java.time.LocalDate;

import controler.Admin;
import controler.DataLayer;
import first.FIRST;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.CostCenter;
import model.Run;
import element.SolutionMap;

/**The RUN form has two event handlers one to edit and the other one to create a new run code. The constructor sets 
 * the form in "Creation Mode";
 * @author samuel.laroche
 */
public class FormRun extends GridPane {
	
	TextField tfRunID, tfDescEN, tfDescFR, tfCostCenter, tfApprover, tfReplacedBy;
	ChoiceBox<String> cbType,cbStatus;
	DatePicker datePicker;
	Button submit;
	Run run;
	EventHandler<ActionEvent> editHandler, newHandler;
	SolutionMap map;
	private FormRun me = this;
	
	public FormRun(FIRST main) {

		super();

		// SECTION RUN

		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));
		
		//CSD Mapping
		map = new SolutionMap();

		this.add(new Label("RUN Code"), 0, 0);
		tfRunID = new TextField();
		tfRunID.setMaxWidth(75.0);
		this.add(tfRunID, 1, 0);

		this.add(new Label("Description EN"), 0, 1);
		tfDescEN = new TextField();
		this.add(tfDescEN, 1, 1);
		
		this.add(new Label("Description FR"), 0, 2);
		tfDescFR = new TextField();
		this.add(tfDescFR, 1, 2);
		
		this.add(new Label("Type"), 0, 3);
		cbType = new ChoiceBox<String>(); 
		cbType.setItems(FXCollections.observableArrayList(Run.getTypeList()));
		cbType.getSelectionModel().selectedItemProperty().addListener((arg, oldVal, newVal) -> {
			if (newVal.equals(Run.MAINTENANCE)) {
				this.add(map, 1, 10);
			} else {
				this.getChildren().remove(map);
			}
		});
		this.add(cbType, 1, 3);
		
		this.add(new Label("Cost Center"), 0, 4);
		tfCostCenter = new TextField();
		tfCostCenter.setMaxWidth(80);
		this.add(tfCostCenter, 1, 4);

		this.add(new Label("Responsible"), 0, 5);
		tfApprover = new TextField();
		this.add(tfApprover, 1, 5);
		
		this.add(new Label("Replaced By"), 0, 6);
		tfReplacedBy = new TextField();
		this.add(tfReplacedBy, 1, 6);
		
		this.add(new Label("Status"), 0, 7);
		cbStatus = new ChoiceBox<String>(); 
		cbStatus.setItems(FXCollections.observableArrayList(Run.getStatusList()));
		this.add(cbStatus, 1, 7);
		
		this.add(new Label("Closing Date"), 0, 8);
		datePicker = new DatePicker();
		this.add(datePicker, 1, 8);
		
		// TODO : Add CSD and Service ID
		
		submit = new Button();
		submit.setGraphic(new ImageView(new Image(FormRun.class.getResourceAsStream("/res/save.png"), 40, 40, false, true)));
		this.add(submit, 1, 9);
				
		editHandler = new EventHandler<ActionEvent>() {
			// Action to change data
			@Override
			public void handle(ActionEvent e) {
				System.out.println("Edit run");
				
				if (main.getAdministrationModule().isAdmin()) {
					
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
						
						try {
							DataLayer.updateRun(run);
							main.notify("Update of IO " + run.getId() + ": SUCCESS");
						} catch (SQLException e1) {
							main.notify("Update of IO " + run.getId() + ": FAILED");
							System.err.println(e1.getSQLState());
						}

						main.notify("Update of IO " + run.getId() + ": SUCCESS");
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
		
		newHandler = new EventHandler<ActionEvent>() { 

			@Override
			public void handle(ActionEvent event) {
				
				Run run = new Run();
				
				run.setId(me.tfRunID.getText());
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
				run.setEffectiveDate(LocalDate.now());
				
				try {
					DataLayer.insertRun(run);
					main.notify("Creation of IO " + run.getId() + ": SUCCESS");
				} catch (SQLException e) {
					main.notify("Creation of IO " + run.getId() + ": FAILED");
					System.err.println(e.getSQLState());
				}
			}
			
		};
		
		submit.setOnAction(newHandler);

	}
	/**A run object is passed to this method to switch to edit mode. Admin authentification is required in order to 
	 * process a modification
	 * @param RUN Object
	 */
	public void edit(Run run) {
		
		this.run = run;
		submit.setOnAction(editHandler);
		
		/*if (Admin.isAdmin()) {
			tfRunID.setEditable(false);
			tfDescEN.setEditable(false);
			tfDescFR.setEditable(false);
			// cbType // TODO : can disable apparently
			tfCostCenter.setEditable(false);
			tfApprover.setEditable(false);
			tfReplacedBy.setEditable(false);
			// cbStatus
			datePicker.setEditable(false);
		}*/

		
		tfRunID.setText(run.getId());
		tfDescEN.setText(run.getNameEN());
		tfDescFR.setText(run.getNameFR());
		cbType.setValue(run.getType()); // TODO : Change that to scrolling list
		tfCostCenter.setText(run.getCostcenter().getId());
		tfApprover.setText(run.getResponsible());
		tfReplacedBy.setText(run.getReplacedBy());
		cbStatus.setValue(run.getStatus());
		datePicker.setValue(run.getClosingDate());
		
		if (run.getCsdAllocation() != null) {
			map.set(run.getCsdAllocation());
		}
		//this.add(map, 1, 10);
				

	}
}
