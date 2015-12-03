package model;

import java.util.Date;

public class NabbrBorApp {
	
	private Integer Oid;
	private String room_id;
	private String borrower;
	private String lender;
	private String result;
	private String rc_code;
	private Integer rc_oid;
	private Integer users;
	private Date date;	
	
	public Integer getOid() {
		return Oid;
	}
	public void setOid(Integer oid) {
		Oid = oid;
	}
	public String getRoom_id() {
		return room_id;
	}
	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}
	public String getBorrower() {
		return borrower;
	}
	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}
	public String getLender() {
		return lender;
	}
	public void setLender(String lender) {
		this.lender = lender;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getRc_code() {
		return rc_code;
	}
	public void setRc_code(String rc_code) {
		this.rc_code = rc_code;
	}
	public Integer getRc_oid() {
		return rc_oid;
	}
	public void setRc_oid(Integer rc_oid) {
		this.rc_oid = rc_oid;
	}	
	public Integer getUsers() {
		return users;
	}
	public void setUsers(Integer users) {
		this.users = users;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}