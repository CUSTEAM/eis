package model;

import java.util.Date;

public class Counselor_stmd {
	
	private Integer Oid;
	private String student_name,
	cell_phone,
	SchoolNo,
	DeptNo,
	//add_time,
	note,
	reply;
	public Integer getOid() {
		return Oid;
	}
	public void setOid(Integer oid) {
		Oid = oid;
	}
	public String getStudent_name() {
		return student_name;
	}
	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}
	public String getCell_phone() {
		return cell_phone;
	}
	public void setCell_phone(String cell_phone) {
		this.cell_phone = cell_phone;
	}
	public String getSchoolNo() {
		return SchoolNo;
	}
	public void setSchoolNo(String schoolNo) {
		SchoolNo = schoolNo;
	}
	public String getDeptNo() {
		return DeptNo;
	}
	public void setDeptNo(String deptNo) {
		DeptNo = deptNo;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	
}
