package ajax.task;
 
import action.BaseAction;
//import org.apache.struts2.json.annotations.JSON;
import java.util.Map;

/**
 * 取工作單內容
 * @author John
 */
public class getTaskAppInfo extends BaseAction{
	
	private Map app;
	
	public Map getApp() {
		return app;
	}

	public void setApp(Map app) {
		this.app = app;
	}

	public String execute() {		
		app=df.sqlGetMap("SELECT ta.Oid, t.title, ta.sdate, ta.edate, e.cname, cts.name as sname, "
		+ "ta.status, e1.Oid as emplOid, e1.cname as emplName, ta.note FROM CODE_TASK_STATUS cts, Task_apply ta "
		+ "LEFT OUTER JOIN empl e1 ON ta.next_empl=e1.Oid, empl e, Task t WHERE ta.from_empl=e.Oid AND cts.id=ta.status "
		+ "AND t.Oid=ta.Task AND ta.Oid="+request.getParameter("Oid"));		
		app.put("hist", df.sqlGet("SELECT th.*, e.cname FROM Task_hist th, empl e WHERE "
		+ "th.empl=e.Oid AND th.Task_apply_oid="+request.getParameter("Oid")+" ORDER BY th.edate DESC"));				
        return SUCCESS;               
    }
	
}