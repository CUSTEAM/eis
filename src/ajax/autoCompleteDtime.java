package ajax;
 
import java.util.List;
import java.util.Map;
import action.BaseAction;

/**
 * 自動完成Dtime
 * <p>以任何欄位自動對應課程</p>
 * @author John
 */
public class autoCompleteDtime extends BaseAction{ 
	
	private Object list[];	
	
	public String execute() {
		String key=request.getParameter("key");
		List<Map>tmp=df.sqlGet("SELECT d.Oid," +
		"d.Oid,c.ClassNo,c.ClassName, IFNULL(e.cname, '未設定')as cname,d1.name as opt,d.credit,cs.cscode, cs.chi_name FROM Dtime d LEFT OUTER JOIN " +
		"empl e ON d.techid=e.idno, Csno cs, Class c, CODE_DTIME_OPT d1 WHERE d.cscode=cs.cscode AND " +
		"c.ClassNo=d.depart_class AND d1.id=d.opt AND d.Sterm ='"+request.getParameter("term")+"' AND " +
		"(e.cname LIKE '"+key+"%'||" +
		"e.idno LIKE '"+key+"%'||" +
		"c.ClassNo LIKE '"+key+"%'||" +
		"c.ClassName LIKE '"+key+"%'||" +
		"d.cscode LIKE '"+key+"%'||" +
		"cs.chi_name LIKE'%"+key+"%'||" +
		"d.Oid LIKE '"+key+"%')" +
		"ORDER BY d.depart_class LIMIT 30");
		
		list=new Object[tmp.size()];
		List<Map>dc;
		StringBuilder sb;
		for(int i=0; i<tmp.size(); i++){
			sb=new StringBuilder(tmp.get(i).get("Oid")+","+
			tmp.get(i).get("opt")+","+
			tmp.get(i).get("credit")+"學分,"+							
			tmp.get(i).get("ClassNo")+","+
			tmp.get(i).get("ClassName")+","+
			tmp.get(i).get("cname")+"老師,"+			
			tmp.get(i).get("cscode")+","+
			tmp.get(i).get("chi_name")+",");			
			dc=df.sqlGet("SELECT * FROM Dtime_class WHERE Dtime_oid="+tmp.get(i).get("Oid"));			
			for(int j=0; j<dc.size(); j++){
				sb.append("週"+dc.get(j).get("week")+"第"+dc.get(j).get("begin")+"~"+dc.get(j).get("end")+"節");
			}			
			list[i]=sb.toString();
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