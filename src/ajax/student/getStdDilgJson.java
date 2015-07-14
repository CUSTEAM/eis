package ajax.student;
 
import java.util.List;
import action.BaseAction;

/**
 * 學年缺曠記錄
 * @author John
 */
public class getStdDilgJson extends BaseAction{
	
	private List list;	
	
	public String execute() {
		setList(df.sqlGet("SELECT c.chi_name, DATE_FORMAT(d.date,'%m月 %d日')as date, " +
		"d.cls, dr.name FROM stmd s, Dilg d, Dtime dt, Csno c, Dilg_rules dr WHERE " +
		"d.Dtime_oid=dt.Oid AND dt.cscode=c.cscode AND s.student_no=d.student_no AND " +
		"d.abs=dr.id AND d.student_no='"+request.getParameter("stdNo")+"'ORDER BY d.date DESC, d.cls"));
		
        return SUCCESS;               
    }

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	} 
}