package view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import controler.QueryWriter;
import graphics.Loading;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class QueryModule extends StackPane {

	StackPane me = this;
	GridPane grid;

	public QueryModule() {
		
		Loading loadAnim = new Loading();

		// Queries Elements
		ObservableList<String> queryList = FXCollections.observableArrayList("Networks by Approvers", "RUN codes", 
				"Cost Centers", "Cost Center by level"); // Add other queries here


		ListView<String> lvQueries = new ListView<String>(queryList);
		lvQueries.setPrefSize(250, 120);

		MultipleSelectionModel<String> lvSelModel = lvQueries.getSelectionModel();

		lvSelModel.selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> changed, String oldVal, String newVal) {
				// TODO: add description of queries in a label
			}
		});

		Button btnQuery = new Button("Open");

		grid = new GridPane();
		grid.add(lvQueries, 0, 0);
		grid.add(btnQuery, 0, 1);
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		this.getChildren().add(grid);

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
				
				// TODO: Add cost center lists by level and on a single column

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
					} catch (IOException e) {

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
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				}

				me.getChildren().remove(loadAnim);
			}

		});

	}
}
