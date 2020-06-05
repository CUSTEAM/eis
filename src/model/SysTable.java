package model;

/**
 * SysTable entity. @author MyEclipse Persistence Tools
 */

public class SysTable implements java.io.Serializable {

	// Fields

	private Integer oid;
	private String tableName;
	private String chiName;
	private String note;

	// Constructors

	/** default constructor */
	public SysTable() {
	}

	/** full constructor */
	public SysTable(String tableName, String chiName, String note) {
		this.tableName = tableName;
		this.chiName = chiName;
		this.note = note;
	}

	// Property accessors

	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getChiName() {
		return this.chiName;
	}

	public void setChiName(String chiName) {
		this.chiName = chiName;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}