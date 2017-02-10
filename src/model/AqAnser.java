package model;

/**
 * AqAnser generated by MyEclipse Persistence Tools
 */

public class AqAnser implements java.io.Serializable {

	// Fields
	private Integer oid;
	private String uid;
	private String uid2;
	private String anser;
	private String idno;
	private String category;
	private String unit;
	private String years;
	// Constructors
	/** default constructor */
	public AqAnser() {
	}

	/** full constructor */
	public AqAnser(String uid, String uid2, String anser, String idno,
			String category, String unit, String years) {
		this.uid = uid;
		this.uid2 = uid2;
		this.anser = anser;
		this.idno = idno;
		this.category = category;
		this.unit = unit;
		this.years = years;
	}

	// Property accessors
	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getUid() {
		return this.uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUid2() {
		return this.uid2;
	}

	public void setUid2(String uid2) {
		this.uid2 = uid2;
	}

	public String getAnser() {
		return this.anser;
	}

	public void setAnser(String anser) {
		this.anser = anser;
	}

	public String getIdno() {
		return this.idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getYears() {
		return this.years;
	}

	public void setYears(String years) {
		this.years = years;
	}

}