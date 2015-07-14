package ajax;
 
import java.util.List;
import java.util.Map;
import action.BaseAction;

public class autoCompleteCode1 extends BaseAction{
	
	private Object list[];
	
	public String execute() {
		List<Map<String, String>>tmp=df.sqlGet("SELECT name FROM code1 WHERE " +
		"(no LIKE '"+request.getParameter("nameno")+"%' || name LIKE '%"+request.getParameter("nameno")+"%') LIMIT 10");
		list=new Object[tmp.size()];
		for(int i=0; i<tmp.size(); i++){
			list[i]=((Map)tmp.get(i)).get("name");
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