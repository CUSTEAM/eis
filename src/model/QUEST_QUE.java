package model;

public class QUEST_QUE {
	
	private Integer Oid, Qid;
	public Integer getQid() {
		return Qid;
	}
	public void setQid(Integer qid) {
		Qid = qid;
	}
	private String value, category;
	public Integer getOid() {
		return Oid;
	}
	public void setOid(Integer oid) {
		Oid = oid;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

}
