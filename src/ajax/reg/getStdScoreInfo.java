package ajax.reg;

import java.util.List;
import java.util.Map;
import action.BaseAction;

/**
 * 本學期成績
 * @author John
 */
public class getStdScoreInfo extends BaseAction{	
	
	public String info="";

	public String getInfo() {
		return info;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}

	public String execute() {		
		List<Map>avg=df.sqlGet("SELECT s.school_year, s.school_term, ROUND(s.score,1)as score, s.rank FROM Stavg s " +
		"WHERE s.student_no='"+request.getParameter("student_no")+"'ORDER BY s.school_year DESC, s.school_term");
		List<Map>score;
		for(int i=0; i<avg.size(); i++){			
			avg.get(i).put("scores", df.sqlGet("SELECT c.chi_name, s.score FROM ScoreHist s, stmd st, Csno c WHERE " +
			"s.student_no=st.student_no AND c.cscode=s.cscode AND " +
			"st.student_no='"+request.getParameter("student_no")+"' AND s.school_year='"+
			avg.get(i).get("school_year")+"' AND s.school_term='"+avg.get(i).get("school_term")+"'"));				
		}
		
		StringBuilder sb=new StringBuilder();
		for(int i=0; i<avg.size(); i++){
			score=(List)avg.get(i).get("scores");
			sb.append(avg.get(i).get("school_year")+"學年第");
			sb.append(avg.get(i).get("school_term")+"學期, ");
			sb.append("平均"+avg.get(i).get("score")+"分, 全班排名");
			sb.append(avg.get(i).get("rank")+"<br>");
			
			for(int j=0; j<score.size(); j++){
				sb.append(score.get(j).get("chi_name")+"\t"+score.get(j).get("score")+"<br>");
			}
			sb.append("<br>");
		}		
		setInfo(sb.toString());		
		return SUCCESS;
    }
}