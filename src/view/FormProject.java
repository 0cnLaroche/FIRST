package view;

import java.sql.SQLException;

import controler.DataLayer;
import controler.NotFoundException;
import first.FIRST;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Project;

public class FormProject extends GridPane {
	private Label lblProjectID, lblDescEN, lblDescFR, lblModel, lblProposal, lblLead;
	private TextField tfProjectID, tfDescEN, tfDescFR, tfProposal, tfLead;
	private ChoiceBox<String> cbModel, cbStatus;
	private EventHandler<ActionEvent> newHandler, editHandler;
	private Button submit, btnWbs;
	private Project project;
	FormProject me = this;
	FIRST main;

	public FormProject(FIRST main) {

		super();
		this.main = main;

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
		
		lblModel = new Label("Governance");
		this.add(lblModel, 0, 3);
		cbModel = new ChoiceBox<String>(); 
		cbModel.setItems(FXCollections.observableArrayList(Project.getModelList()));
		this.add(cbModel, 1, 3);
		
		lblProposal = new Label("Proposal #");
		this.add(lblProposal, 0, 4);
		tfProposal = new TextField();
		this.add(tfProposal, 1, 4);

		lblLead = new Label("Lead");
		this.add(lblLead, 0, 5);
		tfLead = new TextField();
		this.add(tfLead, 1, 5);
		
		this.add(new Label("Status"), 0, 6);
		cbStatus = new ChoiceBox<String>(); 
		cbStatus.setItems(FXCollections.observableArrayList(Project.getStatusList()));
		this.add(cbStatus, 1, 6);


		btnWbs = new Button("Add Network");
		btnWbs.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				
				if(getProject() == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setHeaderText("You need to save the project before adding Networks");
					alert.showAndWait();
					
				} else {
					
					FormNetwork form = new FormNetwork(main);
					form.add(project);

					Scene scene = new Scene(form, 600, 600);
					Stage stage = new Stage();
					stage.setScene(scene);
					stage.setTitle("New Network");
					stage.show();
					
				}
				
			}
		});
		this.add(btnWbs, 1, 7);
		
		editHandler = new EventHandler<ActionEvent>() {
			// Action to change data
			@Override
			public void handle(ActionEvent e) {
				System.out.println("Edit project");
				
				if (main.getAdministrationModule().isAdmin()) {
					
					if (me.project != null ) {
						
						Project project = me.project;
						
						tfProjectID.setEditable(true);
						tfDescEN.setEditable(true);
						tfDescFR.setEditable(true);
						// cbType // TODO : can disable apparently
						// tfCostCenter.setEditable(true);
						tfLead.setEditable(true);
						// tfReplacedBy.setEditable(true);
						// cbStatus
						// datePicker.setEditable(true);
						
						project.setNameEN(me.tfDescEN.getText());
						project.setNameFR(me.tfDescFR.getText());
						project.setLead(me.tfLead.getText());
						project.setStatus(me.cbStatus.getValue()); 
						project.setModel(me.cbModel.getValue());
						project.setProposal(me.tfProposal.getText());
 
						try {
							main.getManager().updateProject(project);
							main.notify("Update of Project " + project.getId() + ": SUCCESS");
							main.getProjectModule().setProject(main.getManager().getProject(project.getId()));
							main.getProjectModule().clear();
							main.getProjectModule().load();
						} catch (SQLException e1) {
							main.notify("Update of Project " + project.getId() + ": FAILED");
							System.err.println(e1.getSQLState());
						} catch (NotFoundException e1) {
							
							e1.printStackTrace();
						}
						
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
				
				Project project = new Project();
				
				project.setId(me.tfProjectID.getText());
				project.setNameEN(me.tfDescEN.getText());
				project.setNameFR(me.tfDescFR.getText());
				project.setLead(me.tfLead.getText());
				project.setStatus(me.cbStatus.getValue()); 
				project.setModel(me.cbModel.getValue());
				project.setProposal(me.tfProposal.getText());
				//project.setClosingDate(datePicker.getValue());
				
				try {
					DataLayer.insertProject(project);
					main.notify("Creation of Cost Center " + project.getId() + ": SUCCESS");
					
					me.setProject(
							main.getManager().getProject(project.getId()));
					main.getProjectModule().setProject(me.getProject());
					main.getProjectModule().clear();
					main.getProjectModule().load();
				} catch (SQLException e) {
					main.notify("Creation of Cost Center " + project.getId() + ": FAILED");
					System.err.println(e.getSQLState());
				} catch (NotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}



			}
			
		};
		
		submit = new Button();
		submit.setGraphic(new ImageView(new Image(FormRun.class.getResourceAsStream("/res/save.png"), 40, 40, false, true)));
		submit.setOnAction(newHandler);
		this.add(submit, 1, 8);
		

	}
	public void edit(Project pj) {
		submit.setOnAction(editHandler);
		this.project = pj;
		tfProjectID.setText(pj.getId());
		tfDescEN.setText(pj.getNameEN());
		tfDescFR.setText(pj.getNameFR());
		cbModel.setValue(pj.getModel());
		cbStatus.setValue(pj.getStatus());
		tfProposal.setText(pj.getProposal());
		tfLead.setText(pj.getLead());
		
		
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	
}
