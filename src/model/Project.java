package model;

import java.util.ArrayList;

public class Project extends FinancialCode {
	
	private String type, proposal, model;
	private ArrayList<Wbs> wbs;
	
	public Project() {
		this.wbs = new ArrayList<Wbs>();
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProposal() {
		return proposal;
	}
	public void setProposal(String proposal) {
		this.proposal = proposal;
	}
	public void addWbs(Wbs wbs) {
		this.wbs.add(wbs);
	}
	public ArrayList<Wbs> getWbs(){
		return this.wbs;
	}
	public String toString() {
		String str = "{Id: '" + this.getId() + "', Name :'" + this.getNameEN() + "'";
		if (!this.wbs.isEmpty()) {
			int i = 1;
			str += ", wbs: ";
			for (Wbs wbs : this.wbs) {
				str += wbs.toString();
				if (i < this.wbs.size()) {
					str += ", \n";
				}
				i++;
			}
		}
		str += "}\n";
		return str;
	}
	
	//TODO: Get WBS list 


}
