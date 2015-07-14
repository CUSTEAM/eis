package ajax.student;
 
import java.util.List;
import java.util.Map;
import action.BaseAction;

/**
 * 歷年成績
 * @author John
 */
public class getStdScore extends BaseAction{
	
	private List<Map>list;
	
	public String execute() {
		list=df.sqlGet("SELECT s.school_year, s.school_term, ROUND(s.score, 2)as avg, " +
		"IFNULL(s.rank, 0)as rank FROM Stavg s WHERE s.student_no='"+request.getParameter("stdNo")+"' ORDER BY school_year DESC, school_term");
		
		for(int i=0; i<list.size(); i++){
			
			list.get(i).put("score", df.sqlGet("SELECT c.chi_name, cd.name as opt, s.credit, s.score FROM " +
			"ScoreHist s, Csno c, CODE_DTIME_OPT cd WHERE cd.id=s.opt AND " +
			"s.cscode=c.cscode AND s.school_year='"+list.get(i).get("school_year")+"' " +
			"AND s.school_term='"+list.get(i).get("school_term")+"' AND s.student_no='"+
			request.getParameter("stdNo")+"' ORDER BY s.opt"));
			
		}		
        return SUCCESS;
    }

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
}