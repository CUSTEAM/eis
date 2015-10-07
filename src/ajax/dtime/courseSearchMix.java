package ajax.dtime;
 
import java.util.List;
import java.util.Map;
import action.BaseAction;

/**
 * 混合條件課程查詢
 * 產生JSONP
 * @author shawn
 *
 */
public class courseSearchMix extends BaseAction{
	
	private Object list[];
	
	public String execute() {		
		
		List<Map>tmp=df.sqlGet("SELECT cdo.name, d.Oid, d.opt, d.credit, d.thour, d.elearning, cs.cscode,cs.chi_name, c.ClassNo, c.ClassName, IFNULL(e.cname, '')as cname " +
		"FROM CODE_DTIME_OPT cdo, Dtime d LEFT OUTER JOIN empl e ON d.techid=e.idno, Class c, Csno cs WHERE d.Sterm='"+request.getParameter("term")+"'AND cdo.id=d.opt AND cs.cscode=d.cscode AND d.depart_class=c.ClassNo AND" +
		"(c.ClassNo LIKE '"+request.getParameter("nameno")+"%' OR e.cname LIKE'"+request.getParameter("nameno")+"%' OR d.Oid LIKE'"+
		request.getParameter("nameno")+"%' OR cs.chi_name LIKE'"+request.getParameter("nameno")+"%' OR cs.cscode LIKE '"+request.getParameter("nameno")+"%')" +
		"ORDER BY d.Sterm, c.ClassNo, d.cscode LIMIT 30");
		
		list=new Object[tmp.size()];
		
		StringBuilder sb;
		for(int i=0; i<tmp.size(); i++){
			sb=new StringBuilder(tmp.get(i).get("Oid").toString()+", ");
			/*
			if(tmp.get(i).get("Sterm").toString().equals("1")){
				sb.append("上學期, ");
			}else{
				sb.append("下學期, ");
			}*/
			
			sb.append(tmp.get(i).get("name").toString()+", ");
			sb.append(tmp.get(i).get("ClassNo").toString());
			sb.append(tmp.get(i).get("ClassName").toString()+", ");
			sb.append(tmp.get(i).get("cscode").toString()+", ");
			sb.append(tmp.get(i).get("chi_name").toString()+", ");
			sb.append(tmp.get(i).get("cname").toString()+", ");
			sb.append(tmp.get(i).get("credit").toString()+"學分, ");
			sb.append(tmp.get(i).get("thour").toString()+"時數, ");
			if(!tmp.get(i).get("elearning").toString().equals("0"))
			sb.append("含遠距");
			list[i]=sb.toString();
			//list[i]=tmp.get(i).get("Oid")+",第"+tmp.get(i).get("Sterm")+"學期, "+tmp.get(i).get("ClassNo")+", "+tmp.get(i).get("ClassName"+", "+tmp.get(i).get("chi_name"));
		}		
        return SUCCESS;
    }
 
	public Object[] getList() {		
		return list;
	}
 
	public void setList(Object[] list) {
		this.list = list;
	}
}