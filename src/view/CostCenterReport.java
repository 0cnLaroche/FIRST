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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.*;

public class CostCenterReport extends Stage {
	
	protected BorderPane root;
	protected TreeView<String> h;
	
	public CostCenterReport() {
		
		try {
			model.CostCenter ccroot = DataLayer.getCostCenter("103100");
			TreeItem<String> troot = new TreeItem<String>(ccroot.getId() + " - " + ccroot.getNameEN());
			troot.setExpanded(true); // Expended at start
			troot.getChildren().addAll(populate(troot,ccroot));
			h = new TreeView<String>(troot);
			
			
			root = new BorderPane();
			// root.setLeft(h);
			root.setCenter(h);
			
			Scene scene = new Scene(root, 1440, 900);
			this.setTitle("RUN");
			this.setScene(scene);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	private ArrayList<TreeItem<String>> populate(TreeItem<String> pitem, CostCenter pcc ){
		ArrayList<TreeItem<String>> children = new ArrayList<TreeItem<String>>();
		
		
		for (CostCenter cc : pcc.getChildren()) {
			TreeItem<String> item = new TreeItem<String>(cc.getId() + " - " + cc.getNameEN());
			item.getChildren().addAll(populate(item,cc));
			children.add(item);
		}
		return children;
		
	}
	

}
