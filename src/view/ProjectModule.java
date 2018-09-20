package view;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.util.Optional;

import javax.swing.table.DefaultTableModel;

import controler.DataLayer;
import controler.NotFoundException;
import controler.TableTransferable;
import first.FIRST;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.*;


public class ProjectModule extends BorderPane {
	
	public FIRST main;
	private Project project;
	private GridPane gSearch;
	private Label label;
    private String txtStageStyle = "-fx-font:40px Tahoma;-fx-fill:white;";
	

	public ProjectModule(FIRST main) {
		
		this.main = main;
		
        gSearch = new GridPane();
        gSearch.setAlignment(Pos.CENTER);
		gSearch.setHgap(10);
		gSearch.setVgap(10);
		gSearch.setPadding(new Insets(25, 25, 25, 25));
	
		Label lbKeyword = new Label("Keyword");
		gSearch.add(lbKeyword, 0, 0);
		
		TextField tfKeyword = new TextField();
		//tfKeyword.prefWidthProperty().bind(this.widthProperty());
		gSearch.add(tfKeyword, 1, 0);
		GridPane.setHgrow(tfKeyword, Priority.ALWAYS);
		
		label = new Label("Projects are found here\nUse any keyword related to this project and FIRST will"
				+ " return you a list of results you can pick from.\nTry any keyword such as the name of the project, a proposal"
				+ " number, project manager, costcenter, network code, SAP code...");
		label.setTextAlignment(TextAlignment.CENTER);
		this.setCenter(label);
		
		Button btn = new Button("Go");
		btn.getStyleClass().add("primary");

		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				Dialog<Project> dialog = new Dialog<Project>();
				dialog.setTitle("Project Selection Dialog");
				dialog.setHeaderText("Please select your project");
				ButtonType selectButtonType = new ButtonType("Select", ButtonData.OK_DONE);
				dialog.getDialogPane().getButtonTypes().addAll(selectButtonType, ButtonType.CANCEL);

				ListView<Project> lv = new ListView<Project>();
				lv.setPrefSize(500, 300);


				try {

					ObservableList<Project> items = FXCollections
							.observableArrayList(DataLayer.queryProjects(tfKeyword.getText()));
					lv.setItems(items);
					MultipleSelectionModel<Project> lvSelModel = lv.getSelectionModel();
					dialog.getDialogPane().setContent(lv);
					
					lv.setOnMouseClicked(new EventHandler<MouseEvent>() {
					    @Override
					    public void handle(MouseEvent mouseEvent) {
					        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
					            if(mouseEvent.getClickCount() == 2){
					            	dialog.setResult(lvSelModel.selectedItemProperty().getValue());
					            	dialog.close();
					            }
					        }
					    }
					});
					
					dialog.setResultConverter(dialogButton -> {
					    if (dialogButton == selectButtonType) {
					        return lvSelModel.selectedItemProperty().getValue();
					    }
					    return null;
					});
					
					if (items.size() == 1) {
						setProject(items.get(0));
						clear();
						load();
					} else {
						Optional<Project> result = dialog.showAndWait();

						result.ifPresent(selection -> {
							setProject(selection);
							clear();
							load();

						});
						
					}


				} catch (NotFoundException e1) {
					System.err.println(e1);
					e1.showDialog();
				}
			}
		});
		
		gSearch.add(btn, 2, 0);
		this.setBottom(gSearch);
	}
	
	private class selectorTreeCellImpl extends TreeCell<Project>{
		
		@Override
		public void updateItem(Project item, boolean empty) {
			super.updateItem(item, empty);
            setText((item == null || empty) ? null : item.toString());
            setGraphic(null);
		}
	}
	private void setProject(Project project) {
		this.project = project;
	}
	public void load() {
		
		VBox vb,stage0,stage2,stage3,stage4,stage5;
		ScrollPane sp;	
		element.Project pdef;
		
		// setting source for project definition header
		pdef = new element.Project(main);
		pdef.setSource(project);
        pdef.render();
        
        vb = new VBox(5);
		sp = new ScrollPane();

        
        vb.setPadding(new Insets(10));
        sp = new ScrollPane(vb);

		stage0 = new VBox();
		//stage0.setPrefWidth(1440);
		stage0.setSpacing(10);
		stage0.setAlignment(Pos.TOP_LEFT);
		stage0.setStyle("-fx-background-color:rgb(150,206,180,0.5);-fx-padding:15"); // Color : #96CEB4
        
		stage2 = new VBox();
		//stage2.setPrefWidth(1440);
		stage2.setSpacing(10);
		stage2.setAlignment(Pos.TOP_LEFT);
		stage2.setStyle("-fx-background-color:#96ceb4;-fx-padding:15");
		Label txStage2 = new Label("Stage 2");
		txStage2.setStyle(txtStageStyle);
		stage2.getChildren().add(txStage2);
		
		stage3 = new VBox();
		//stage3.setPrefWidth(1440);
		stage3.setSpacing(10);
		stage3.setAlignment(Pos.TOP_LEFT);
		stage3.setStyle("-fx-background-color:#ffeead;-fx-padding:15");
		Label txStage3 = new Label("Stage 3");
		txStage3.setStyle(txtStageStyle);
		stage3.getChildren().add(txStage3);
		
		stage4 = new VBox();
		//stage4.setPrefWidth(1440);
		stage4.setSpacing(10);
		stage4.setAlignment(Pos.TOP_LEFT);
		stage4.setStyle("-fx-background-color:#ffcc5c;-fx-padding:15");
		Label txStage4 = new Label("Stage 4");
		txStage4.setStyle(txtStageStyle);
		stage4.getChildren().add(txStage4);
		
		stage5 = new VBox();
		//stage5.setPrefWidth(1440);
		stage5.prefWidthProperty().bind(this.widthProperty());
		stage5.setAlignment(Pos.TOP_LEFT);
		stage5.setStyle("-fx-background-color:#ff6f69;-fx-padding:15");
		Label txStage5 = new Label("Stage 5");
		txStage5.setStyle(txtStageStyle);
		stage5.getChildren().add(txStage5);
        
		for (Wbs w : project.getWbs()) {
        	// VBox flow;
        	VBox flow;
        	switch (w.getStage()) {
        	case 0 :
        		flow = stage0;
        		break;
        	case 2:
        		flow = stage2;
        		break;
        	case 3:
        		flow = stage3;
        		break;
        	case 4:
        		flow = stage4;
        		break;
        	case 5:
        		flow = stage5;
        		break;
        	default:
        		flow = stage0;
        		break;
            }
        	
        	for (Network n : w.getNetworks()) {
        		element.Network gn = new element.Network(main);
        		gn.setSource(n);
        		gn.render();
        		flow.getChildren().add(gn);
        	}
        }
		switch(project.getModel()) {
    	case Project.STAGEGATED :
            vb.getChildren().add(stage2);
            vb.getChildren().add(stage3);
            vb.getChildren().add(stage4);
            vb.getChildren().add(stage5);
            break;
    	case Project.BRANCHINITIATIVE:
    	case Project.LITE:
    	case Project.NONSTAGEGATED:
    		vb.getChildren().add(stage0);
    		break;
    	default :
    		vb.getChildren().add(stage0);
            vb.getChildren().add(stage2);
            vb.getChildren().add(stage3);
            vb.getChildren().add(stage4);
            vb.getChildren().add(stage5);
            break;
    }
        this.setTop(pdef);
        this.setCenter(sp);
	}
	public void clear() {
		this.setTop(null);
		this.setCenter(label);
	}
	public void toClipboard() {
		Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();

		Object[][] table = new Object[project.getWbs().size()][];
		
		for (int i = 0; i < project.getWbs().size(); i++) {
			for (int j = 0; j < project.getWbs().get(i).getNetworks().size(); j++) {
				Network nw = project.getWbs().get(i).getNetworks().get(j);
				table[i] = nw.toArray();
			}

			
		}
		DefaultTableModel model = new DefaultTableModel(table, new Object[] {"Network #", "Description", "Stage", "Cost Center", 
				"Approver", "Status"});
		cb.setContents(new TableTransferable(model), new ClipboardOwner() {
            @Override
            public void lostOwnership(Clipboard clipboard, Transferable contents) {
                System.out.println("You lose :(");
            }
        });
		main.notify("Copied Project to Clipboard");
	}

	
}




