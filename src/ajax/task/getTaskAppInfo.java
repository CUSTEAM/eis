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
		+ "LEFT OUTER JOIN empl e1 ON ta.next_empl=e1.idno, empl e, Task t WHERE ta.from_empl=e.idno AND cts.id=ta.status "
		+ "AND t.Oid=ta.Task AND ta.Oid="+request.getParameter("Oid"));		
		
		app.put("taskFile", df.sqlGet("SELECT path, file_name FROM Task_file WHERE Task_app_oid="+request.getParameter("Oid")));
		
		
		app.put("hist", df.sqlGet("SELECT tf.path, th.*, e.cname FROM Task_hist th LEFT OUTER JOIN Task_file tf ON th.Oid=tf.Task_hist_oid, empl e WHERE "
		+ "th.empl=e.idno AND th.Task_apply_oid="+request.getParameter("Oid")+" ORDER BY th.edate DESC"));				
        
		
		
		
		return SUCCESS;               
    }
	
}