package ajax.task;
 
import java.util.List;
import java.util.Map;

//import org.apache.struts2.json.annotations.JSON;

import action.BaseAction;

/**
 * 工作單內容細節？
 * @author shawn
 *
 */
public class getTaskInfo extends BaseAction{
	
	
	
	private List list;
	
	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	

	public String execute() {		
		List<Map>list=null;
		if(request.getParameter("histOid")!=null){
			list=df.sqlGet("SELECT cu.name as unitName, e.cname, th.* FROM (Task_hist th LEFT OUTER JOIN empl e ON th.empl=e.idno)LEFT OUTER JOIN CODE_UNIT cu ON cu.id=e.unit_module WHERE th.Oid="+request.getParameter("histOid"));
			for(int i=0; i<list.size(); i++){
				list.get(i).put("files", df.sqlGet("SELECT * FROM Task_file WHERE back='0' AND Task_hist_oid="+list.get(i).get("Oid")));
				list.get(i).put("bFiles", df.sqlGet("SELECT * FROM Task_file WHERE back='1' AND Task_hist_oid="+list.get(i).get("Oid")));
			}
		}
		if(request.getParameter("taskOid")!=null){			
			list=df.sqlGet("SELECT cu.name as unitName, e.cname,t.note as TaskNote,t.title, th.* FROM (Task t, Task_hist th LEFT OUTER JOIN empl e ON th.empl=e.idno)LEFT OUTER JOIN CODE_UNIT cu ON cu.id=e.unit_module WHERE th.Task_oid=t.Oid AND th.Task_hist_oid IS NULL AND th.Task_oid="+request.getParameter("taskOid"));
			for(int i=0; i<list.size(); i++){
				list.get(i).put("files", df.sqlGet("SELECT * FROM Task_file WHERE back='0' AND Task_oid="+list.get(i).get("Task_oid")));
				list.get(i).put("bFiles", df.sqlGet("SELECT * FROM Task_file WHERE back='1' AND Task_hist_oid="+list.get(i).get("Oid")));
			}		
		}
		if(list.isEmpty())return SUCCESS;		
		this.setList(list);					
        return SUCCESS;               
    }
	
}