package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import controler.DataLayer;
import controler.NotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.*;

public class CostCenterModule extends BorderPane {
	
	protected BorderPane me = this;
	protected TreeView<CostCenter> treeView;
	
	public CostCenterModule() {
		load();		
	}
	public void load() {
		try {
			model.CostCenter ccroot = DataLayer.getCostCenter("103100");
			TreeItem<CostCenter> troot = new TreeItem<CostCenter>();
			troot.setValue(ccroot);
			troot.setExpanded(true); // Expended at start
			troot.getChildren().addAll(populate(troot,ccroot));
			treeView = new TreeView<CostCenter>(troot);
			treeView.setPrefWidth(500);
			treeView.setCellFactory(new Callback<TreeView<CostCenter>,TreeCell<CostCenter>>(){
	            @Override
	            public TreeCell<CostCenter> call(TreeView<CostCenter> p) {
	                return new selectorTreeCellImpl();
	            }
	        });

			// root = new BorderPane();
			this.setLeft(treeView);

			/*Scene scene = new Scene(root, 1440, 900);
			this.setTitle("RUN");
			this.setScene(scene);
			*/
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	private ArrayList<TreeItem<CostCenter>> populate(TreeItem<CostCenter> pitem, CostCenter pcc ){
		
		ArrayList<TreeItem<CostCenter>> children = new ArrayList<TreeItem<CostCenter>>();
		
		for (CostCenter cc : pcc.getChildren()) {
			if (cc.getStatus() != CostCenter.CLOSED) {
				TreeItem<CostCenter> item = new TreeItem<CostCenter>();
				item.setValue(cc);
				item.getChildren().addAll(populate(item,cc));
				children.add(item);
			}
		}
		return children;		
	}
	private class selectorTreeCellImpl extends TreeCell<CostCenter>{
		
		@Override
		public void updateItem(CostCenter item, boolean empty) {
			super.updateItem(item, empty);
            setText((item == null || empty) ? null : item.toString());
            setGraphic(null);
		}
		
		
		public selectorTreeCellImpl() {
			
			super.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					// ccSelector.setCC(getTreeItem().getValue().getId());
					FormCostCenter form = new FormCostCenter();
					form.edit(getTreeItem().getValue());
					me.setCenter(null);
					me.setCenter(form);
					
					
				}
				
			});
		}
		
	}
	

}
