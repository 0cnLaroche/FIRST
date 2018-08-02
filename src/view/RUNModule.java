package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import controler.DataLayer;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.*;


public class RUNModule extends BorderPane {
	
	protected ArrayList<Run> source;
	protected BorderPane root;
	protected HBox hbLegend;
	protected VBox filtersBox, resultBox, list;
	protected ScrollPane spFilters, spList;	
	protected GridPane grid;
	protected CheckBox cbActive, cbClosed, cbMnt, cbSrv, cbBmt, cbInv;
	protected ComboBox<String> cbCostCenter, cbManager;
	protected TextField tfId, tfDesc;
	protected Button filterReset;
	private String labelStyle = "-fx-text-fill: #ffffff;-fx-font: 16 Geneva; -fx-font-weight:bold;";
	private String filterStyle = "-fx-text-fill: #ffffff;-fx-font: 14 Geneva;-fx-text-weight:bold;";
	
	public RUNModule() {
		
		source = DataLayer.getRunList();

        root = new BorderPane();
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
        loadMangerFilter(source);
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
        
        // root.setLeft(spFilters);  
        //root.setCenter(resultBox);
        
        this.setLeft(spFilters);
        
        this.setCenter(resultBox);
        
        generateList(source);

		/*Scene scene = new Scene(root, 1440, 900);
		this.setTitle("RUN");
		this.setScene(scene);*/
	}
	public ArrayList<Run> filter() {
		ArrayList<Run> filtered = (ArrayList<Run>) source.clone();

		for (Run r : source) {
			if(tfId.getText() != null) {
				if (!r.getId().contains(tfId.getText())) {
					filtered.remove(r);
				}
			}
			if(tfDesc.getText() != null || tfDesc.getText().length() < 2) {
				if (!r.getNameEN().toLowerCase().contains(tfDesc.getCharacters()) 
						/*|| !r.getNameFR().contains(tfDesc.getCharacters())*/) {
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
				try {
					if (!r.getResponsible().equals(cbManager.getValue())) {
						filtered.remove(r);
					}
				} catch (NullPointerException e) {
					System.out.println(r.toString());
				}

			}
			// TODO: Add filters for cost centers
		}
		return filtered;
		
	}
	// public void generateList(ArrayList<String> run) {
	public void generateList(ArrayList<Run> run) {
		
        for (Run r : run) {

        	graphics.Run line = new graphics.Run(r);
        	
/*        	HBox hb = new HBox(5);
        	Text id = new Text(r.getId());
        	Text descEN = new Text(r.getNameEN());
        	Text type = new Text(r.getType());
        	Text resp = new Text(r.getResponsible());
        	Text cc = new Text(r.getCostcenter().getId());
        	Text status = new Text("" + r.getStatusString()); // TODO: Ajouter un if pour mettre le status en textuel
        	hb.getChildren().addAll(id,descEN,type,resp,cc,status);*/
        	list.getChildren().add(line);
        	
        }
	}
	private void loadCCfilter(ArrayList<Run> run) {
		
		
		Set<String>  set = new HashSet<String>();
		String[] array;
		
		
		for (int i = 0; i < run.size() -1; i++) {
			
			if (run.get(i).getCostcenter().getId().equals(null)) {
				set.add("");
				// array[i] = "";
			} else {
				set.add(run.get(i).getCostcenter().getId());
				// array[i] = run.get(i).getCostcenter().getId();
			}

		}
		array = set.toArray(new String[set.size()]);
		Arrays.sort(array);
		
		ObservableList<String> options = FXCollections.observableArrayList(array);
		cbCostCenter.setItems(options);
	}
	private void loadMangerFilter(ArrayList<Run> run) {
		Set<String>  set = new HashSet<String>();
		String[] array;
		
		
		for (int i = 0; i < run.size() -1; i++) {
			
			if (run.get(i).getResponsible() == null) {
				set.add("");
				// array[i] = "";
			} else {
				set.add(run.get(i).getResponsible());
				// array[i] = run.get(i).getCostcenter().getId();
			}

		}
		array = set.toArray(new String[set.size()]);
		Arrays.sort(array);
		
		ObservableList<String> options = FXCollections.observableArrayList(array);
		cbManager.setItems(options);
		
	}
	

}
