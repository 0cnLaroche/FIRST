package model;

import java.time.LocalDate;

/**Base class for all type of financial coding
 * @author samuel.laroche
 */
public abstract class FinancialCode {
	
	private String id, nameEN, nameFR;
	LocalDate effectiveDate, closingDate;
	private String status;
	public static final String UNRELEASED = "Unreleased";
	public static final String ACTIVE = "Active";
	public static final String CLOSED = "Closed";
	
	
	public String getStatus() {
		return status;
	}
	/*public static Status[] getStatusList() {
		Status[] values = {new Status((byte) 0,"Unreleased"), new Status((byte) 1,"Active"), new Status((byte) 3,"Closed")};
		return values;
	}*/
	public static String[] getStatusList() {
		return new String[]{UNRELEASED, ACTIVE, CLOSED};
	}

	public void setStatus(String status) {
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
