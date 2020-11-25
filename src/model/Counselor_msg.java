package model;

public class Counselor_msg {
	
	private Integer Oid;
	private Integer stmd_oid;
	private String name;
	private String note;
	public Integer getOid() {
		return Oid;
	}
	public void setOid(Integer oid) {
		Oid = oid;
	}
	public Integer getStmd_oid() {
		return stmd_oid;
	}
	public void setStmd_oid(Integer stmd_oid) {
		this.stmd_oid = stmd_oid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

}
