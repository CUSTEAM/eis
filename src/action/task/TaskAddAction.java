package action.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import action.BaseAction;

/**
 * 工作單建立
 * @author shawn
 */
public class TaskAddAction extends BaseAction{
	
	public String Oid, addOid;
	public String appInfo;
	public String unitSearch, begin, end;
	public String execute(){		
		request.setAttribute("myApps", df.sqlGet("SELECT cts.name as status, e.cname, ta.Oid, cu.name, t.title, ta.sdate, ta.edate FROM "
		+ "CODE_TASK_STATUS cts,Task t, Task_apply ta LEFT OUTER JOIN empl e ON e.Oid=ta.next_empl, CODE_UNIT cu WHERE "
		+ "cts.id=ta.status AND t.unit=cu.id AND t.Oid=ta.Task AND ta.from_empl='"+
		df.sqlGetStr("SELECT Oid FROM empl WHERE idno='"+getSession().getAttribute("userid")+"'")+"'"));		
		request.setAttribute("aunit", getUnit());
		return SUCCESS;
	}

	public String save(){		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		df.exSql("INSERT INTO Task_apply(Task, from_empl, note, sdate)VALUES("+addOid+","+
		df.sqlGetStr("SELECT Oid FROM empl WHERE idno='"+getSession().getAttribute("userid")+"'")+", '"+appInfo+"', '"+sf.format(new Date())+"');");
		return execute();
	}
	
	public String search(){
		StringBuilder sb=new StringBuilder("SELECT cts.name as status, e.cname, ta.Oid, cu.name, t.title, ta.edate, ta.sdate FROM "
		+ "CODE_TASK_STATUS cts,Task t, Task_apply ta LEFT OUTER JOIN empl e ON e.Oid=ta.next_empl, CODE_UNIT cu WHERE "
		+ "cts.id=ta.status AND t.unit=cu.id AND t.Oid=ta.Task AND cu.id LIKE'"+unitSearch+"%'");
		if(!begin.trim().equals("")){
			sb.append("AND sdate>='"+begin+"'");
		}
		if(!end.trim().equals("")){
			sb.append("AND sdate<='"+end+"'");
		}		
		request.setAttribute("myApps", df.sqlGet(sb.toString()));
		request.setAttribute("aunit", getUnit());
		return SUCCESS;
	}
	
	private List getUnit(){
		//單位
		List<Map>campus=dm.sqlGet("SELECT id, name FROM CODE_CAMPUS c");
		List<Map>tmp;		
		
		for(int i=0; i<campus.size(); i++){
			tmp=dm.sqlGet("SELECT (SELECT COUNT(*) FROM Task WHERE open='e' AND unit IN(SELECT id FROM CODE_UNIT WHERE pid=c.id))as tal, (SELECT COUNT(*)FROM Task WHERE unit=c.id)as cnt, c.id, c.name FROM CODE_UNIT c WHERE pid='0' AND campus='"+campus.get(i).get("id")+"'");			
			//tmp=dm.sqlGet("SELECT (SELECT COUNT(*)FROM Task WHERE unit=c.id)as cnt, c.id, c.name FROM CODE_UNIT c WHERE pid='0' AND campus='"+campus.get(i).get("id")+"'");
			for(int j=0; j<tmp.size(); j++){				
				tmp.get(j).put("sub_unit", dm.sqlGet("SELECT (SELECT COUNT(*)FROM Task WHERE open='e' AND unit=c.id)as cnt, c.id, c.name FROM CODE_UNIT c WHERE pid='"+tmp.get(j).get("id")+"'"));
			}			
			campus.get(i).put("unit", tmp);
		}
		return campus;
	}
	
	private String getId(String str){
		try{
			return str.substring(0, str.indexOf(","));
		}catch(Exception e){
			return "";
		}
	}
	

}
