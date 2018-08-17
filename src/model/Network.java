package model;

import java.util.ArrayList;

public class Network extends FinancialCode {
	
	private Wbs wbs;
	private String replacedBy;

	private ArrayList<Activity> activities;
	
	public Network () {
		this.activities = new ArrayList<Activity>();
	}
	public Wbs getWbs() {
		return wbs;
	}
	public void setWbs(Wbs wbs) {
		this.wbs = wbs;
	}
	public String toString() {
		String str = "{Id: '" + this.getId() + "', Name :'" + this.getNameEN() + "'";
		if (!this.activities.isEmpty()) {
			int i = 1;
			str += ", activity: ";
			for (Activity act : this.activities) {
				str += act.toString();
				if (i < this.activities.size()) {
					str += ", ";
				}
				i++;
			}
		}
		str += "}";
		return str;
	}
	public String getReplacedBy() {
		return replacedBy;
	}
	public void setReplacedBy(String replacedBy) {
		this.replacedBy = replacedBy;
	}
	@Override
	public String[] toArray() {
		
		return new String[] {this.getId(), this.getNameEN(), this.getWbs().getCostcenter().getId(), 
				this.getWbs().getApprover(), this.getStatus()};
		};
	}
	
	
