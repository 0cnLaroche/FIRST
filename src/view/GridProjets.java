package view;

import java.util.Optional;

import controler.DataLayer;
import controler.NotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.CostCenter;
import model.Project;

public class GridProjets extends GridPane {
	
	/*public GridProjets() {
		super();

		// SECTION PROJETS

		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));
	

		Label lbKeyword = new Label("Keyword");
		this.add(lbKeyword, 0, 0);
		
		TextField tfKeyword = new TextField();
		//tfKeyword.prefWidthProperty().bind(this.widthProperty());
		this.add(tfKeyword, 1, 0);
		GridProjets.setHgrow(tfKeyword, Priority.ALWAYS);
		
		Label label = new Label("Projects are found here\nUse any keyword related to this project and FIRST will"
				+ " return you a list of results you can pick from.\nTry any keyword such as the name of the project, a proposal"
				+ " number, project manager, costcenter, network code, SAP code...");
		label.setTextAlignment(TextAlignment.CENTER);
		this.add(label, 1, 1);
		
		Button btn = new Button("Go");
		btn.getStyleClass().add("primary");

		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				Dialog<Project> dialog = new Dialog<Project>();
				dialog.setTitle("Project Selection Dialog");
				dialog.setHeaderText("Please select your project");
				// dialog.setContentText("Please enter your name:");

				ListView<Project> lv = new ListView<Project>();
				lv.setPrefSize(500, 300);
				ButtonType selectButtonType = new ButtonType("Select", ButtonData.OK_DONE);
				dialog.getDialogPane().getButtonTypes().addAll(selectButtonType, ButtonType.CANCEL);

				try {

					ObservableList<Project> items = FXCollections
							.observableArrayList(DataLayer.queryProjects(tfKeyword.getText()));
					lv.setItems(items);
					MultipleSelectionModel<Project> lvSelModel = lv.getSelectionModel();
					dialog.getDialogPane().setContent(lv);
					
					// Convert the result to a username-password-pair when the login button is clicked.
					dialog.setResultConverter(dialogButton -> {
					    if (dialogButton == selectButtonType) {
					        return lvSelModel.selectedItemProperty().getValue();
					    }
					    return null;
					});

					Optional<Project> result = dialog.showAndWait();

					result.ifPresent(selection -> {
						ProjectModule report;

						//try {
							report = new ProjectModule(selection);
							report.setTitle("Projects");
							report.show();
						//} catch (NotFoundException e2) {
							
						//}

					});

				} catch (NotFoundException e1) {
					System.err.println(e1);
				}
			}
		});
		this.add(btn, 2, 0);
	}
	private class selectorTreeCellImpl extends TreeCell<Project>{
		
		@Override
		public void updateItem(Project item, boolean empty) {
			super.updateItem(item, empty);
            setText((item == null || empty) ? null : item.toString());
            setGraphic(null);
		}
	}*/
}
