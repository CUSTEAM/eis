package ajax.dilg;
 
import java.util.List;
import java.util.Map;
import action.BaseAction;
 
/**
 * 取學生缺曠
 * @author John
 */
public class getStdDilg extends BaseAction{ 
	
	private String info="";
	
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String execute() {
		StringBuilder sb=new StringBuilder("SELECT dr.name, d.*, c.chi_name FROM Dilg_rules dr, Dilg d, Dtime dt, Csno c WHERE dr.id=d.abs AND " +
		"d.Dtime_oid=dt.Oid AND dt.cscode=c.cscode AND d.student_no='"+request.getParameter("student_no")+"'");
		
		if(!request.getParameter("Dtime_oid").trim().equals("")){
			sb.append("AND dt.Oid="+request.getParameter("Dtime_oid"));
		}
		sb.append(" ORDER BY d.date, d.cls");
		List tmp=df.sqlGet(sb.toString());
		if(tmp.size()<1){
			info="無缺曠記錄:-)";
			return SUCCESS;
		}
		
		for(int i=0; i<tmp.size(); i++){
			if(info!=null){
				info=info+"<span class='label label-important'>"+
				((Map)tmp.get(i)).get("name")+"</span>"+((Map)tmp.get(i)).get("date")+" 第 "+((Map)tmp.get(i)).get("cls")+
				"節, "+((Map)tmp.get(i)).get("chi_name")+"<br>";
			}
			
		}		
		return SUCCESS;
               
	} 
}