package model;

import java.util.ArrayList;

/**Project Class is a representation of a project Definition in SAP. It also contains the wbs and network childs.
 * @author samuel.laroche
 *
 */
public class Project extends FinancialCode {
	
	private String proposal, model, lead;
	private ArrayList<Wbs> wbs;
	public static final String STAGEGATED = "Stage-Gated";
	public static final String NONSTAGEGATED = "Non Stage Gated";
	public static final String LITE = "Lite";
	public static final String BRANCHINITIATIVE = "Branch Initiatives";
	
	public Project() {
		this.wbs = new ArrayList<Wbs>();
	}
	public static String[] getModelList() {
		return new String[]{STAGEGATED, NONSTAGEGATED, LITE, BRANCHINITIATIVE};
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
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
	/**
	 * @return String formatted as JSON representing the Project Definition and its WBS elements.
	 */
	public String toJSON() {
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
	public String toString() {
		return this.getId() + " | " + this.getNameEN();
	}
	public String getLead() {
		return lead;
	}
	public void setLead(String lead) {
		this.lead = lead;
	}
	@Override
	public String[] toArray() {
		
		return new String[] {this.getId(), this.getNameEN(), this.getModel(), this.getProposal(), this.getLead(),
				this.getStatus()};
		};
}

