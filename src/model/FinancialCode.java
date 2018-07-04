package model;

import java.time.LocalDate;;

public abstract class FinancialCode {
	
	private String id, nameEN, nameFR;
	LocalDate effectiveDate, closingDate;
	private byte status;
	public static final byte UNRELEASED = 0;
	public static final byte ACTIVE = 1;
	public static final byte CLOSED = 2;
	
	
	public byte getStatus() {
		return status;
	}
	public String getStatusString() {
		switch (status) {
		case 0:
			return "Unreleased";
		case 1:
			return "Active";
		case 2:
			return "Closed";
		default:
			return "";
		}
		
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public LocalDate getEffectiveDate() {
			return effectiveDate;
	}

	/*public void setEffectiveDate(LocalDate effectiveDate) {
		this.effectiveDate = effectiveDate;
	}*/
	
	public void setEffectiveDate(LocalDate effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public LocalDate getClosingDate() {
			return closingDate;
	}

	public void setClosingDate(LocalDate closingDate) {
		this.closingDate = closingDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNameEN() {
		return nameEN;
	}

	public void setNameEN(String nameEN) {
		this.nameEN = nameEN;
	}

	public String getNameFR() {
		return nameFR;
	}

	public void setNameFR(String nameFR) {
		this.nameFR = nameFR;
	}
}
