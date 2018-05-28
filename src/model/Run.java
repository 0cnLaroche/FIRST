package model;

public class Run extends FinancialCode {
	private String type, responsible;
	private CostCenter costcenter;

	public CostCenter getCostcenter() {
		return costcenter;
	}

	public void setCostcenter(CostCenter costcenter) {
		this.costcenter = costcenter;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getResponsible() {
		return responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}
	public String toString() {
		String str = "{Id: '" + this.getId() + ", Name: '" + this.getNameEN() + "', CostCenter: '" + this.costcenter.getId() + "'}";
		return str;
	}
}
