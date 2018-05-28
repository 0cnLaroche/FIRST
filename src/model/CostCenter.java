package model;

import java.util.ArrayList;

public class CostCenter extends FinancialCode {
	
	private String manager;
	
	private ArrayList<CostCenter> children;
	private CostCenter parent;
	
	public CostCenter() {
		children = new ArrayList<CostCenter>();
		//parent = new CostCenter();
		
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public CostCenter getParent() {
		return parent;
	}

	public void setParent(CostCenter parent) {
		this.parent = parent;
	}

	public ArrayList<CostCenter> getChildren() {
		return children;
	}
	public void addChild(CostCenter child) {
		children.add(child);
	}
	public String toString() {
		String str = "{Id: '" + this.getId() + "', Name: '" + this.getNameEN() + "'";
		
		if (!children.isEmpty()) {
			str += ", children: ";
			int i = 1;
			for (CostCenter cc : children) {
				str += cc.toString();
				if (i<children.size()) {
					str += ", ";
				}
				i++;
			}
		}
		
		str += "}\n";
		return str;
		
	}
	
}
