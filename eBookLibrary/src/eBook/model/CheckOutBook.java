package eBook.model;

import java.sql.Timestamp;

public class CheckOutBook {
	protected int checkOutBookId;
	protected String loginName;
	protected String iSBN;
	protected Timestamp timeStart;
	protected Timestamp timeEnd;
	protected int permitCheckOutTime;
	
	public CheckOutBook(int checkOutBookId, String loginName, String iSBN, Timestamp timeStart, Timestamp timeEnd,
			int permitCheckOutTime) {
		this.checkOutBookId = checkOutBookId;
		this.loginName = loginName;
		this.iSBN = iSBN;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.permitCheckOutTime = permitCheckOutTime;
	}
	
	public CheckOutBook(String loginName, String iSBN, Timestamp timeStart, Timestamp timeEnd,
			int permitCheckOutTime) {
		this.loginName = loginName;
		this.iSBN = iSBN;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.permitCheckOutTime = permitCheckOutTime;
	}

	public int getCheckOutBookId() {
		return checkOutBookId;
	}

	public void setCheckOutBookId(int checkOutBookId) {
		this.checkOutBookId = checkOutBookId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getiSBN() {
		return iSBN;
	}

	public void setiSBN(String iSBN) {
		this.iSBN = iSBN;
	}

	public Timestamp getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Timestamp timeStart) {
		this.timeStart = timeStart;
	}

	public Timestamp getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Timestamp timeEnd) {
		this.timeEnd = timeEnd;
	}

	public int getPermitCheckOutTime() {
		return permitCheckOutTime;
	}

	public void setPermitCheckOutTime(int permitCheckOutTime) {
		this.permitCheckOutTime = permitCheckOutTime;
	}
}
