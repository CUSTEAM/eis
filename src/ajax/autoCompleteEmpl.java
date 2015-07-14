package ajax;
 
import java.util.List;
import java.util.Map;
import action.BaseAction;

public class autoCompleteEmpl extends BaseAction{
	
	private Object list[];

	public Object[] getList() {
		return list;
	}

	public void setList(Object[] list) {
		this.list = list;
	}

	public String execute(){
		
		List<Map>tmp=df.sqlGet("SELECT e.cname, e.idno, IFNULL(d.name,'')as edunit, IFNULL(u.name,'')as unit FROM " +
		"(empl e LEFT OUTER JOIN CODE_DEPT d ON e.unit=d.id)LEFT OUTER JOIN CODE_UNIT u ON e.unit_module=u.id " +
		"WHERE (e.cname LIKE '"+request.getParameter("nameno")+"%' OR e.idno LIKE '"+request.getParameter("nameno")+"%')");
		
		list=new Object[tmp.size()];
		for(int i=0; i<tmp.size(); i++){
			list[i]=tmp.get(i).get("idno")+","+tmp.get(i).get("cname")+" - "+tmp.get(i).get("edunit")+", "+tmp.get(i).get("unit");
		}	
		
		return SUCCESS;
	}
}