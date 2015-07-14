package ajax.student;
 
import java.util.List;
import java.util.Map;
import action.BaseAction;

/**
 * 學生輔導記錄
 * @author John
 */
public class getStudCounseling extends BaseAction{
	
	private List<Map>list;
	
	public String execute() {
		list=df.sqlGet("SELECT e.cname, IFNULL(c.chi_name, '')as chi_name, cc.itemName, s.schoolYear, " +
		"s.schoolTerm, s.cdate, IFNULL(s.content,'')as content FROM StudCounseling s LEFT OUTER JOIN Csno c ON " +
		"c.cscode=s.cscode, CODE_CARE cc, empl e WHERE e.idno=s.teacherid AND cc.itemNo=s.itemNo AND " +
		"s.studentNo='"+request.getParameter("stdNo")+"'ORDER BY s.cdate DESC");		
		
        return SUCCESS;
               
    }

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
 
}