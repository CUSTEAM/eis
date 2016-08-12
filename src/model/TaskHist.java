package model;

import java.util.Date;

public class TaskHist {
	
	private Integer Oid;
	private Integer Task_apply_oid;
	private String empl;
	private String reply;
	private Date edate;
	public Integer getOid() {
		return Oid;
	}
	public void setOid(Integer oid) {
		Oid = oid;
	}
	public Integer getTask_apply_oid() {
		return Task_apply_oid;
	}
	public void setTask_apply_oid(Integer task_apply_oid) {
		Task_apply_oid = task_apply_oid;
	}
	public String getEmpl() {
		return empl;
	}
	public void setEmpl(String empl) {
		this.empl = empl;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public Date getEdate() {
		return edate;
	}
	public void setEdate(Date edate) {
		this.edate = edate;
	}

}
