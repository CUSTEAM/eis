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
		}
		
		if(request.getParameter("Oid")!=null){
			setList(df.sqlGet("SELECT t.title,t.template FROM Task t WHERE t.Oid="+request.getParameter("Oid")));
		}
				
        return SUCCESS;               
    }
	
}