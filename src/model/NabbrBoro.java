package model;

import java.util.Date;

public class NabbrBoro {
	
	private Integer Oid;
	private Date boro_date;
	private Integer BorAppOid;
	private Integer week;
	private Integer cls;
	
	public Integer getOid() {
		return Oid;
	}
	public void setOid(Integer oid) {
		Oid = oid;
	}
	public Date getBoro_date() {
		return boro_date;
	}
	public void setBoro_date(Date boro_date) {
		this.boro_date = boro_date;
	}
	public Integer getBorAppOid() {
		return BorAppOid;
	}
	public void setBorAppOid(Integer borAppOid) {
		BorAppOid = borAppOid;
	}
	
	public Integer getWeek() {
		return week;
	}
	public void setWeek(Integer week) {
		this.week = week;
	}
	public Integer getCls() {
		return cls;
	}
	public void setCls(Integer cls) {
		this.cls = cls;
	}
	
}