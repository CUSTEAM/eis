package ajax.student;
 
import java.util.List;
import java.util.Map;
import action.BaseAction;
 
/**
 * 歷年獎懲
 * @author John
 *
 */
public class getStDesd extends BaseAction{
	
	private List<Map>list;
	
	public String execute() {
		List list=df.sqlGet("SELECT ''as school_year, ''as school_term, cd.name, c1.name as name1, d.cnt1," +
		"c2.name as name2, d.cnt2 FROM (desd d LEFT OUTER JOIN CODE_DESD c2 ON d.kind2=c2.id) " +
		"LEFT OUTER JOIN CODE_DESD_CAUSE cd ON d.reason=cd.id, CODE_DESD c1 WHERE d.kind1=c1.id AND d.student_no='"+
		request.getParameter("stdNo")+"'");
		
		list.addAll(df.sqlGet("SELECT d.school_year, d.school_term, cd.name, c1.name as name1, d.cnt1," +
		"c2.name as name2, d.cnt2 FROM (comb2 d LEFT OUTER JOIN CODE_DESD c2 ON d.kind2=c2.id)" +
		"LEFT OUTER JOIN CODE_DESD_CAUSE cd ON d.reason=cd.id, CODE_DESD c1 WHERE d.kind1=c1.id AND d.student_no='"+
		request.getParameter("stdNo")+"'"));
		
		setList(list);
        return SUCCESS;               
    }

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	} 
}