package model;

import java.util.Date;

public class TaskApply {
	
	private Integer Oid;
	private Integer Task;
	private String from_empl;
	private String from_stmd;
	private String note;
	private String next_empl;
	private Date edate;
	private String status;
	private String email;
	private Date sdate;
	private String checker;
	private Integer ensure;
	
	public Integer getEnsure() {
		return ensure;
	}
	public void setEnsure(Integer ensure) {
		this.ensure = ensure;
	}
	public String getChecker() {
		return checker;
	}
	public void setChecker(String checker) {
		this.checker = checker;
	}
	public Integer getOid() {
		return Oid;
	}
	public void setOid(Integer oid) {
		Oid = oid;
	}
	public Integer getTask() {
		return Task;
	}
	public void setTask(Integer task) {
		Task = task;
	}
	public String getFrom_empl() {
		return from_empl;
	}
	public void setFrom_empl(String from_empl) {
		this.from_empl = from_empl;
	}
	public String getFrom_stmd() {
		return from_stmd;
	}
	public void setFrom_stmd(String from_stmd) {
		this.from_stmd = from_stmd;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getNext_empl() {
		return next_empl;
	}
	public void setNext_empl(String next_empl) {
		this.next_empl = next_empl;
	}
	public Date getEdate() {
		return edate;
	}
	public void setEdate(Date edate) {
		this.edate = edate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getSdate() {
		return sdate;
	}
	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}

}
