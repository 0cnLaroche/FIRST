package view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import controler.QueryWriter;
import element.Loading;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**This view includes the features to export data from the database to excel
 * @author samuel.laroche
 * 
 *
 */
public class QueryModule extends StackPane {

	StackPane me = this;
	GridPane grid;
	TextArea text;
	private String[] names = {"Networks by Approvers", "Project List", "RUN codes", 
			"Cost Centers by level"};

	public <FIRST>QueryModule(FIRST main) {
		
		Loading loadAnim = new Loading();
		
		grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		
		// This is where will be displayed the descriptions of queries
		text = new TextArea();
		text.setEditable(false);
		grid.add(text, 1, 0);

		// Queries Elements
		ObservableList<String> queryList = FXCollections.observableArrayList(names); // Add other queries here

		ListView<String> lvQueries = new ListView<String>(queryList);
		lvQueries.setPrefSize(250, 120);

		MultipleSelectionModel<String> lvSelModel = lvQueries.getSelectionModel();

		// Show a description when an element is selected
		lvSelModel.selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> changed, String oldVal, String newVal) {
				switch (newVal) {
				case "Networks by Approvers":
					text.setText("A list of all network codes with the following fields :\n"
							+ "ID, Description, Approver, Status, Stage, Project ID, GCIT Attributes");
					break;
				case "Project List":
					text.setText("A list of all projects including Stage-Gated, Branch Initiatives and Lite.\n"
							+ " Included fields are Project SAP code, Project Name, Model, Proposal #, "
							+ "\nProject Manager, Status, Closing Date and GCIT Attributes.");
					break;
				case "RUN codes":
					text.setText("A list of all RUN Codes including fields :\n"
							+ "ID, Description, Type, Cost Center, Status, Solution (CSD) and Service ID");
					break;
				case "Cost Centers by level":
					text.setText("A list of cost centers followed by their reporting cost center (parents) on 6 levels.");
					break;
				}
			}
		});
		grid.add(lvQueries, 0, 0);
		
		Button btnQuery = new Button("Open");
		btnQuery.getStyleClass().add("primary");
		btnQuery.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				me.getChildren().add(loadAnim);

				QueryWriter qw = new QueryWriter();
				File selectedFile = null;
				FileChooser fChooser = new FileChooser();
				fChooser.setInitialDirectory(new File("C://"));
				fChooser.getExtensionFilters().add(new ExtensionFilter("excel", ".xlsx"));

				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Query export confirmation");
				alert.setHeaderText("Yah, export succsessful!");
				alert.setContentText("Do you wish to open the saved file?");
				ButtonType btnYes = new ButtonType("Yes");
				ButtonType btnNo = new ButtonType("No", ButtonData.CANCEL_CLOSE);
				alert.getButtonTypes().setAll(btnYes, btnNo);

				Desktop desktop = Desktop.getDesktop();

				switch (lvSelModel.selectedItemProperty().getValue()) {

				case "Cost Centers by level":

					fChooser.setInitialFileName("CostCentersByLevel");
					selectedFile = fChooser.showSaveDialog(null);

					try {
						qw.exportCostCenterByLevel(selectedFile);;

						Optional<ButtonType> result = alert.showAndWait();

						if (result.get() == btnYes) {
							desktop.open(selectedFile);
						} else {
							// ... user chose CANCEL or closed the dialog
						}

					} catch (NullPointerException e) {
						System.err.println("Export cancelled by User");
					} catch (IOException e) {
						System.err.println("Export : Error with file");
						e.printStackTrace();
					}
					break;
				case "RUN codes":

					fChooser.setInitialFileName("RUNs");
					selectedFile = fChooser.showSaveDialog(null);

					try {

						qw.exportRunList(selectedFile);

						Optional<ButtonType> result = alert.showAndWait();

						if (result.get() == btnYes) {
							desktop.open(selectedFile);
						} else {
							// ... user chose CANCEL or closed the dialog
						}

					} catch (NullPointerException e) {
						System.out.println("Export cancelled by User");
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case "Networks by Approvers":

					fChooser.setInitialFileName("NetworksByApprover");
					selectedFile = fChooser.showSaveDialog(null);

					try {
						qw.exportNetworkList(selectedFile);

						Optional<ButtonType> result = alert.showAndWait();

						if (result.get() == btnYes) {
							desktop.open(selectedFile);
						} else {
							// ... user chose CANCEL or closed the dialog
						}

					} catch (NullPointerException e) {
						System.out.println("Export cancelled by User");
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

					break;
					
				case "Project List":
					fChooser.setInitialFileName("ProjectList");
					selectedFile = fChooser.showSaveDialog(null);

					try {
						qw.exportProjectList(selectedFile);

						Optional<ButtonType> result = alert.showAndWait();

						if (result.get() == btnYes) {
							desktop.open(selectedFile);
						} else {
							// ... user chose CANCEL or closed the dialog
						}
					} catch (NullPointerException e) {
						System.out.println("Export cancelled by User");
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;					
				}
				me.getChildren().remove(loadAnim);
			}
		});
		grid.add(btnQuery, 0, 1);

		this.getChildren().add(grid);

	}
}
