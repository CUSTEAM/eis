package ajax.task;
 
import java.util.List;

//import org.apache.struts2.json.annotations.JSON;

import action.BaseAction;

/**
 * 工作單內容細節？
 * @author shawn
 *
 */
public class getTaskInfo extends BaseAction{
	
	private List list;
	private List files;
	public List getFiles() {
		return files;
	}

	public void setFiles(List files) {
		this.files = files;
	}

	private String unitName;
	
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

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
			setList(df.sqlGet("SELECT (SELECT COUNT(*)FROM Task_apply WHERE Task=t.Oid AND status!='C')as cnt, t.title, t.Oid FROM Task t WHERE t.unit='"+request.getParameter("unit")+"'"));
			setUnitName(df.sqlGetStr("SELECT name FROM CODE_UNIT WHERE id='"+request.getParameter("unit")+"'"));
			setFiles(df.sqlGet("SELECT f.path, f.file_name FROM Task_file f, Task t WHERE f.Task_oid=t.Oid AND t.unit="+request.getParameter("unit")));
		}
		
		if(request.getParameter("Oid")!=null){
			setList(df.sqlGet("SELECT t.title,t.template, t.ensure FROM Task t WHERE t.Oid="+request.getParameter("Oid")));
			setFiles(df.sqlGet("SELECT path, file_name FROM Task_file WHERE Task_oid="+request.getParameter("Oid")));
		}
		
		
				
        return SUCCESS;               
    }
	
}