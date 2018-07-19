package model;

import java.time.LocalDate;
import java.util.HashMap;

public abstract class FinancialCode {
	
	private String id, nameEN, nameFR;
	LocalDate effectiveDate, closingDate;
	private Status status;
	public static final byte UNRELEASED = 0;
	public static final byte ACTIVE = 1;
	public static final byte CLOSED = 2;
	
	
	public Status getStatus() {
		return status;
	}
	public Status[] getStatusList() {
		Status[] values = {new Status((byte) 0,"Unreleased"), new Status((byte) 1,"Active"), new Status((byte) 3,"Closed")};
		return values;
	}

	/*public String getStatusString() {
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
		
	}*/

	public void setStatus(byte status) {
		this.status = this.getStatusList()[status];
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
	public class Status {
		private Byte value;
		private String text;
		
		public Status(Byte value, String text) {
			this.value = value;
			this.text = text;
		}
		
		public String toString() {
			return this.text;
		}
		public byte getValue() {
			return this.value;
		}
	}
}
