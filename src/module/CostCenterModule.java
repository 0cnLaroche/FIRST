package module;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import controler.DataLayer;
import controler.NotFoundException;
import controler.TableTransferable;
import first.FIRST;
import form.FormCostCenter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.util.Callback;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.*;

public class CostCenterModule extends BorderPane {

	protected BorderPane me = this;
	private GridPane gSearch;
	private TextField tfKeyword;
	protected TreeView<CostCenter> treeView;
	private FormCostCenter form;
	public FIRST main;

	public CostCenterModule(FIRST main) {
		this.main = main;
		load();
	}
	public FormCostCenter getForm() {
		return form;
	}

	public void load() {

		gSearch = new GridPane();
		gSearch.setAlignment(Pos.CENTER);
		gSearch.setHgap(10);
		gSearch.setVgap(10);
		gSearch.setPadding(new Insets(25, 25, 25, 25));

		Label lbKeyword = new Label("Keyword");
		gSearch.add(lbKeyword, 0, 0);

		tfKeyword = new TextField();
		gSearch.add(tfKeyword, 1, 0);
		GridPane.setHgrow(tfKeyword, Priority.ALWAYS);

		Button btn = new Button("Go");
		btn.getStyleClass().add("primary");

		btn.setOnAction((event) -> {
			treeView.getSelectionModel().clearSelection();
			treeView.refresh();
			highlightCostCenter(treeView.getRoot());
		});
		
		this.setOnKeyPressed((event) -> {
			if (event.getCode() == KeyCode.ENTER) {
				treeView.getSelectionModel().clearSelection();
				treeView.refresh();
				highlightCostCenter(treeView.getRoot());
			}
		});

		gSearch.add(btn, 2, 0);
		this.setBottom(gSearch);

		TreeItem<CostCenter> troot = new TreeItem<>();

		try {
			model.CostCenter ccroot = DataLayer.getCostCenter("103100");
			troot.setValue(ccroot);
			troot.setExpanded(true); // Expended at start
			troot.getChildren().addAll(populate(troot, ccroot));
			treeView = new TreeView<CostCenter>(troot);
			treeView.setPrefWidth(500);
			treeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			treeView.setCellFactory(new Callback<TreeView<CostCenter>, TreeCell<CostCenter>>() {
				@Override
				public TreeCell<CostCenter> call(TreeView<CostCenter> p) {
					return new selectorTreeCellImpl();
					// return new highlightTreeCellImpl();
				}
			});

			this.setLeft(treeView);
			
		} catch (NotFoundException e) {
			e.showDialog();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	// Populates the TreeView with cost centers from the list. **Recursive**
	private ArrayList<TreeItem<CostCenter>> populate(TreeItem<CostCenter> pitem, CostCenter pcc) {

		ArrayList<TreeItem<CostCenter>> children = new ArrayList<TreeItem<CostCenter>>();

		for (CostCenter cc : pcc.getChildren()) {
			if (cc.getClosingDate() == null) {
				TreeItem<CostCenter> item = new TreeItem<CostCenter>();
				item.setValue(cc);
				item.getChildren().addAll(populate(item, cc));
				children.add(item);
			}
		}
		return children;
	}
	public void toClipboard() {
		Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();

		Object[][] table = new Object[][] {form.getCostCenter().toArray()};
		

		DefaultTableModel model = new DefaultTableModel(table, 
				new Object[] {"CostCenter #", "Description", "Manager", "Status"});
		cb.setContents(new TableTransferable(model), new ClipboardOwner() {
            @Override
            public void lostOwnership(Clipboard clipboard, Transferable contents) {
                System.out.println("You lose :(");
            }
        });
		main.notifyUser("Copied Cost Center to Clipboard");
	}

	private void highlightCostCenter(TreeItem<CostCenter> root) {
		// For now this just select the treeItem with cost center matching the keyword
		// May do something fancier one day
		String keyword = tfKeyword.getText().toLowerCase();
		CostCenter cc = root.getValue();
		if (cc.getId().contains(keyword) || cc.getNameEN().toLowerCase().contains(keyword)
				|| cc.getManager().toLowerCase().contains(keyword)) {
			
			treeView.getSelectionModel().select(root);
			
			if (root.getParent() != null) {
				root.getParent().setExpanded(true);
			}
		}
		if (!root.getChildren().isEmpty()) {

			for (TreeItem<CostCenter> child : root.getChildren()) {
				highlightCostCenter(child);
			}
		}

	}
	// Cell Factory 
	private class selectorTreeCellImpl extends TreeCell<CostCenter> {

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
					form = new FormCostCenter(main);
					form.edit(getTreeItem().getValue());
					me.setCenter(null);
					me.setCenter(form);
				}

			});
		}
		

	}
	@SuppressWarnings("unused")
	@Deprecated
	private class highlightTreeCellImpl extends TreeCell<CostCenter> {
		// this is unused for now
		@Override
		public void updateItem(CostCenter item, boolean empty) {
			super.updateItem(item, empty);
			setText((item == null || empty) ? null : item.toString());
			Circle circle = new Circle();
			circle.setRadius(8.0);
			circle.setFill(Color.GREENYELLOW);
			setGraphic(circle);

		}
		@Deprecated
		public highlightTreeCellImpl() {
			super.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					// ccSelector.setCC(getTreeItem().getValue().getId());
					FormCostCenter form = new FormCostCenter(main);
					form.edit(getTreeItem().getValue());
					me.setCenter(null);
					me.setCenter(form);
				}
			});
		}
		
	}
}
