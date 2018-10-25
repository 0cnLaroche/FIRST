package model;

import java.util.HashMap;

import csd.Allocation;

public class Run extends FinancialCode {

	private String type;
	private String responsible;
	private String replacedBy;
	private CostCenter costcenter;
	private Allocation[] csdalloc;
	public static final String MAINTENANCE = "MNT-Maintenance";
	public static final String SERVICE = "SRV-Service";
	public static final String BUSINESSMANAGEMENT = "BMT-Business Management";
	public static final String INVESTMENT = "INV-Investment";
	//TODO : On veut qu'a chaque fois qu'il y a un update du mapping csd le weight soit updaté
	
	public Run() {
		super();
	}
	
	public Allocation[] getCsdAllocation() {
		return csdalloc;
	}

	public void setCsdAllocation(Allocation[] csdalloc) {
		this.csdalloc = csdalloc;
	}
	
	public void addAllocation(Allocation alloc) {
		if (csdalloc == null) {
			csdalloc = new Allocation[] {alloc};
		} else {
			Allocation[] temp = new Allocation[csdalloc.length + 1];
			for (int i = 1; i < temp.length; i++) {
				temp[i] = csdalloc[i-1];
			}
			temp[0] = alloc;
			this.csdalloc = temp;
		}
	}

	public static String[] getTypeList() {
		return new String[]{MAINTENANCE, SERVICE, BUSINESSMANAGEMENT, INVESTMENT};
	}
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

	public String getReplacedBy() {
		return replacedBy;
	}

	public void setReplacedBy(String replacedBy) {
		this.replacedBy = replacedBy;
	}
	public String[] toArray() {
		return new String[] {this.getId(), this.getNameEN(), this.getType(), this.getCostcenter().getId(), 
				this.getResponsible(), this.getStatus()};
	}
}
