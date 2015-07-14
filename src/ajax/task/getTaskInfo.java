package ajax.task;
 
import java.util.List;

//import org.apache.struts2.json.annotations.JSON;

import action.BaseAction;

public class getTaskInfo extends BaseAction{
	
	private List list;
	
	//@JSON(format="yyyy-MM-dd HH:mm:ss")
	//@JSON(serialize=false)
	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
	
	public String execute() {
		
		if(request.getParameter("unit")!=null){
			setList(df.sqlGet("SELECT t.title, t.Oid FROM Task t WHERE t.unit='"+request.getParameter("unit")+"'"));
		}
		
		if(request.getParameter("Oid")!=null){
			setList(df.sqlGet("SELECT t.template FROM Task t WHERE t.Oid="+request.getParameter("Oid")));
		}
				
        return SUCCESS;               
    }
	
}