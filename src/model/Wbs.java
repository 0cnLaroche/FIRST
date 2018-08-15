package model;

import java.util.ArrayList;


public class Wbs extends FinancialCode {
	
	private String approver;
	private byte stage;
	private ArrayList<Network> networks;
	private ArrayList<Wbs> subwbs;
	private Project project;
	private CostCenter costcenter;
	
	public ArrayList<Network> getNetworks() {
		return networks;
	}
	public Wbs() {
		this.networks = new ArrayList<Network>();
	}
	
	public CostCenter getCostcenter() {
		return costcenter;
	}

	public void setCostcenter(CostCenter costcenter) {
		this.costcenter = costcenter;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public byte getStage() {
		return stage;
	}

	public void setStage(byte stage) {
		this.stage = stage;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public Project getProject() {
		return project;
	}

	public void addNetwork(Network network) {
		this.networks.add(network);
	}
	public String toJSON() {
		String str = "{Id: '" + this.getId() + "', Name :'" + this.getNameEN() + "', CostCenter: '" + this.costcenter.getId() + "'";
		if (!this.networks.isEmpty()) {
			int i = 1;
			str += ", network: ";
			for (Network nw : this.networks) {
				str += nw.toString();
				if (i < this.networks.size()) {
					str += ", ";
				}
				i++;
			}
		}
		str += "}";
		return str;
	}
	public String toString() {
		return this.getId() + " | " + this.getNameEN();
	}
	

}
