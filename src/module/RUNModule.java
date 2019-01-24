package module;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.table.DefaultTableModel;

import controler.DataLayer;
import controler.TableTransferable;
import first.FIRST;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.*;


public class RUNModule extends BorderPane {
	
	public FIRST main;
	private ArrayList<Run> source;
	private HBox hbLegend;
	private VBox filtersBox, resultBox, list;
	private ScrollPane spFilters, spList;	
	private CheckBox cbActive, cbClosed, cbMnt, cbSrv, cbBmt, cbInv;
	private ComboBox<String> cbCostCenter, cbManager;
	private TextField tfId, tfDesc;
	private Button filterReset;
	private String labelStyle = "-fx-text-fill: #ffffff;-fx-font: 16 Geneva; -fx-font-weight:bold;";
	private String filterStyle = "-fx-text-fill: #ffffff;-fx-font: 14 Geneva;-fx-text-weight:bold;";
	
	public RUNModule(FIRST main) {
		
		this.main = main;
		source = DataLayer.getRunList();

        hbLegend = new HBox();
        resultBox = new VBox();
        filtersBox = new VBox();
        spList = new ScrollPane();
        list = new VBox();
        
        Label lbTitle = new Label("RUN");
        lbTitle.setStyle("-fx-text-fill: #ef8354;-fx-font: 35 Tahoma;-fx-font-weight: bold;");
        
        cbActive = new CheckBox("Active");
        cbActive.setStyle(filterStyle);
        cbActive.setSelected(true);

        cbActive.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				list.getChildren().clear();
				generateList(filter());
			}

        });
        
        cbClosed = new CheckBox("Closed");
        cbClosed.setStyle(filterStyle);
        cbClosed.setSelected(false);
        cbClosed.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				list.getChildren().clear();
				generateList(filter());
			}

        });
        
        Label lbStatus = new Label("Status");
        lbStatus.setStyle(labelStyle);
        
        filtersBox.getChildren().addAll(lbTitle, lbStatus, cbActive, cbClosed);
        
        filtersBox.setStyle("-fx-background-color: #4f5d75"); // setting background color
        filtersBox.setSpacing(5);
        filtersBox.setPadding(new Insets(10,5,10,10));
        
        cbMnt = new CheckBox("Maintenance");
        cbMnt.setStyle(filterStyle);
        cbMnt.setSelected(true);
        cbMnt.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				list.getChildren().clear();
				generateList(filter());
			}

        });
        cbSrv = new CheckBox("Service");
        cbSrv.setSelected(true);
        cbSrv.setStyle(filterStyle);
        cbSrv.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				list.getChildren().clear();
				generateList(filter());
			}

        });
        cbBmt = new CheckBox("Business Management");
        cbBmt.setSelected(true);
        cbBmt.setStyle(filterStyle);
        cbBmt.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				list.getChildren().clear();
				generateList(filter());
			}

        });
        cbInv = new CheckBox("Anticipatory Projects");
        cbInv.setStyle(filterStyle);
        cbInv.setSelected(true);
        cbInv.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				list.getChildren().clear();
				generateList(filter());
			}

        });
        
        Label lbType = new Label("Type");
        lbType.setStyle(labelStyle);
        
        filtersBox.getChildren().addAll(lbType, cbMnt, cbSrv, cbBmt, cbInv);
        
        cbCostCenter = new ComboBox<String>();
        loadCCfilter(source);
        cbCostCenter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				list.getChildren().clear();
				generateList(filter());
			}
        	
        });
        Label lbCostCenter = new Label("Cost Center");
        lbCostCenter.setStyle(labelStyle);
        
        filtersBox.getChildren().addAll(lbCostCenter, cbCostCenter);
        
        cbManager = new ComboBox<String>();
        loadManagerFilter(source);
        cbManager.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				list.getChildren().clear();
				generateList(filter());
			}
        	
        });
        Label lbManager = new Label("Manager");
        lbManager.setStyle(labelStyle);
        
        filtersBox.getChildren().addAll(lbManager, cbManager);
        
        Label lbId = new Label("#");
        lbId.setStyle(labelStyle);
        
        tfId = new TextField();
        tfId.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				list.getChildren().clear();
				generateList(filter());
				
			}
        	
        });
        
        filtersBox.getChildren().addAll(lbId,tfId);
        
        Label lbDesc = new Label("Keyword");
        lbDesc.setStyle(labelStyle);
        
        tfDesc = new TextField();
        tfDesc.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				list.getChildren().clear();
				generateList(filter());
				
			}
        	
        });
        
        filtersBox.getChildren().addAll(lbDesc,tfDesc);

        
        filterReset = new Button("Clear");
        filterReset.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				list.getChildren().clear();
				source = DataLayer.getRunList();
				generateList(source);
				cbActive.setSelected(true);
				cbClosed.setSelected(true);
				cbMnt.setSelected(true);
				cbSrv.setSelected(true);
				cbBmt.setSelected(true);
				cbInv.setSelected(true);
				tfId.clear();
				tfDesc.clear();
				cbCostCenter.getSelectionModel().clearSelection();
				cbManager.getSelectionModel().clearSelection();
				
			}
        	
        });
        
        filtersBox.getChildren().add(filterReset);
        
        spFilters = new ScrollPane(filtersBox);
        // spFilters.setStyle("-fx-background-color: #4f5d75");
        
		Label hid = new Label("#     ");
		Label hdesc = new Label("Description");
		hdesc.setPrefWidth(305);
		Label htype = new Label("Type");
		htype.setPrefWidth(190);
		Label hresp = new Label("Responsible");
		hresp.setPrefWidth(160);
		Label hcc = new Label("Cost Center");
		hcc.setPrefWidth(55);
		Label hstatus = new Label("Status");
		
		
		hbLegend.getChildren().addAll(hid,hdesc,htype,hresp,hcc,hstatus);
		hbLegend.setPadding(new Insets(5,10,5,10));
		hbLegend.setSpacing(10);
       
        spList = new ScrollPane(list);
        
		resultBox.getChildren().addAll(hbLegend, spList);
        
        this.setLeft(spFilters);
        
        this.setCenter(resultBox);
		this.load();
	}
	public void load() {
        
        generateList(filter());

	}
	public ArrayList<Run> filter() {
		@SuppressWarnings("unchecked")
		ArrayList<Run> filtered = (ArrayList<Run>) DataLayer.getRunList().clone();

		for (Run r : DataLayer.getRunList()) {
			
			try {
				
				if(tfId.getText() != null && tfId.getText().length() > 0) {
					if (!r.getId().contains(tfId.getText())) {
						filtered.remove(r);
					}
				}
				if(tfDesc.getText() != null && tfDesc.getText().length() > 0) {
					if (!r.getNameEN().toLowerCase().contains(tfDesc.getText().toLowerCase())) {
						filtered.remove(r);
					}
				}
				if (!cbActive.isSelected()) {
					if (r.getStatus().equals(FinancialCode.ACTIVE)) {
						filtered.remove(r);
					}
				}
				if (!cbClosed.isSelected()) {
					if (r.getStatus().equals(FinancialCode.CLOSED)) {
						filtered.remove(r);
					}
				}
				if (!cbMnt.isSelected()) {
					if (r.getType().equals("MNT-Maintenance")) {
						filtered.remove(r);
					}
				}
				if (!cbSrv.isSelected()) {
					if (r.getType().equals("SRV-Service")) {
						filtered.remove(r);
					}
				}
				if (!cbBmt.isSelected()) {
					if (r.getType().equals("BMT-Business Management")) {
						filtered.remove(r);
					}
				}
				if (!cbInv.isSelected()) {
					if (r.getType().equals("INV-Investment")) {
						filtered.remove(r);
					}
				}
				if (cbCostCenter.getValue() != null) {
					if (!r.getCostcenter().getId().equals(cbCostCenter.getValue())) {
						filtered.remove(r);
					}
				}
				if (cbManager.getValue() != null) {
						if (!r.getResponsible().equals(cbManager.getValue())) {
							filtered.remove(r);
						}
				}
				
			} catch (NullPointerException e) {
				System.err.println("Problem filtering RUN " + r.toString());
			}

		}
		this.source = filtered;
		return filtered;
	}
		
	public void generateList(ArrayList<Run> run) {
		
        for (Run r : run) {
        	element.Run line = new element.Run(r,main);
        	list.getChildren().add(line);
        }
	}
	private void loadCCfilter(ArrayList<Run> run) {
		
		Set<String>  set = new HashSet<String>();
		String[] array;

		for (int i = 0; i < run.size() -1; i++) {
			
			if (run.get(i).getCostcenter().getId().equals(null)) {
				set.add("");
			} else {
				set.add(run.get(i).getCostcenter().getId());
			}

		}
		array = set.toArray(new String[set.size()]);
		Arrays.sort(array);
		
		ObservableList<String> options = FXCollections.observableArrayList(array);
		cbCostCenter.setItems(options);
	}
	private void loadManagerFilter(ArrayList<Run> run) {
		Set<String>  set = new HashSet<String>();
		String[] array;
		
		
		for (int i = 0; i < run.size() -1; i++) {
			
			if (run.get(i).getResponsible() == null) {
				set.add("");
			} else {
				set.add(run.get(i).getResponsible());
			}
		}
		array = set.toArray(new String[set.size()]);
		Arrays.sort(array);
		
		ObservableList<String> options = FXCollections.observableArrayList(array);
		cbManager.setItems(options);
		
	}
	public void toClipboard() {
		Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();

		Object[][] table = new Object[getSource().size()][];
		
		for (int i = 0; i < getSource().size(); i++) {
			
			Run run = source.get(i);
			table[i] = run.toArray();
			
		}
		DefaultTableModel model = new DefaultTableModel(table, new Object[] {"#", "Description", "Type", "Cost Center", 
                "Responsible", "Status"});
		cb.setContents(new TableTransferable(model), new ClipboardOwner() {
			
            @Override
            public void lostOwnership(Clipboard clipboard, Transferable contents) {
                System.out.println("You lose :(");
            }
        });
		main.notifyUser("Copied RUN to Clipboard");
		                                                                  
	
	}
	public ArrayList<Run> getSource() {
		return source;
	}
	

}
