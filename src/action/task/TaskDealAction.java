package action.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import model.Message;
import action.BaseAction;

public class TaskDealAction extends BaseAction{
	
	public String Oid;
	public String next_empl;
	public String status;
	public String reply;
	
	
	public String execute(){		
		
		String unit=df.sqlGetStr("SELECT unit_module FROM empl WHERE idno='"+getSession().getAttribute("userid")+"'");
		
		//單位未分派
		List que=df.sqlGet("SELECT e1.cname as next, ta.Oid, t.title, ta.sdate, ta.edate, e.cname, cts.name as status FROM CODE_TASK_STATUS cts,"
		+ "Task_apply ta LEFT OUTER JOIN empl e1 ON ta.next_empl=e1.Oid, empl e, Task t WHERE ta.from_empl=e.Oid AND cts.id=ta.status AND t.Oid=ta.Task AND t.unit='"+
		unit+"' AND ta.status='N' ORDER BY edate DESC");		
		
		//個人未處理
		que.addAll(df.sqlGet("SELECT e1.cname as next, ta.Oid, t.title, ta.sdate, ta.edate, e.cname, cts.name as status FROM "
				+ "CODE_TASK_STATUS cts, Task_apply ta LEFT OUTER JOIN empl e1 ON ta.next_empl=e1.Oid, empl e, Task t WHERE "
				+ "ta.from_empl=e.Oid AND cts.id=ta.status AND t.Oid=ta.Task AND "
				+ "ta.next_empl="+am.getEmplOid(getSession().getAttribute("userid").toString())+" AND ta.status !='C' AND ta.status !='R'  ORDER BY edate DESC"));
		
		request.setAttribute("que", que);
		
		//已完成
		request.setAttribute("fin", df.sqlGet("SELECT e1.cname as next, ta.Oid, t.title, ta.sdate, ta.edate, e.cname, cts.name as status FROM "
				+ "CODE_TASK_STATUS cts, Task_apply ta LEFT OUTER JOIN empl e1 ON ta.next_empl=e1.Oid, empl e, Task t WHERE "
				+ "ta.from_empl=e.Oid AND cts.id=ta.status AND t.Oid=ta.Task AND "
				+ "ta.next_empl="+am.getEmplOid(getSession().getAttribute("userid").toString())+" AND ta.status !='P'  ORDER BY edate DESC"));
		
		
		return SUCCESS;
	}
	
	public String edit(){
		
		String s=df.sqlGetStr("SELECT status FROM Task_apply WHERE Oid="+Oid);
		if(s.equals("N")||s.equals("T")){//未有人處理或移轉中
			df.exSql("UPDATE Task_apply SET status='P', next_empl="+am.getEmplOid(getSession().getAttribute("userid").toString())+" WHERE Oid="+Oid);
		}
		
		request.setAttribute("status", df.sqlGet("SELECT * FROM CODE_TASK_STATUS"));//狀態
		
		request.setAttribute("task", df.sqlGetMap("SELECT ta.Oid, t.title, ta.sdate, e.cname, cts.name as sname, "
		+ "ta.status, e1.Oid as emplOid, e1.cname as emplName, ta.note FROM CODE_TASK_STATUS cts, Task_apply ta "
		+ "LEFT OUTER JOIN empl e1 ON ta.next_empl=e1.Oid, empl e, Task t WHERE ta.from_empl=e.Oid AND cts.id=ta.status "
		+ "AND t.Oid=ta.Task AND ta.Oid="+Oid));
		
		request.setAttribute("hist", df.sqlGet("SELECT th.*, e.cname FROM Task_hist th, empl e WHERE th.empl=e.Oid AND th.Task_apply_oid="+Oid+" ORDER BY th.edate DESC"));
		
		return "edit";
	}
	
	public String deal(){
		Message msg=new Message();
		if(status.equals("")){
			msg.setError("請選擇狀態");
			this.savMessage(msg);
			return edit();
		}
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm");		
		
		df.exSql("INSERT INTO Task_hist(Task_apply_oid,empl,reply,edate)VALUES("+Oid+","+am.getEmplOid(getSession().getAttribute("userid").toString())+",'"+reply+"','"+sf.format(new Date())+"');");
		
		if(status.equals("T")){
			df.exSql("UPDATE Task_apply SET status='T', next_empl='"+next_empl.substring(0, next_empl.indexOf(","))+"' WHERE Oid="+Oid);
			msg.setSuccess("已儲存");
			this.savMessage(msg);		
			return execute();
		}
		
		if(status.equals("R")){
			df.exSql("UPDATE Task_apply SET next_empl=null WHERE Oid="+Oid);
			msg.setSuccess("已儲存");
			this.savMessage(msg);		
			return execute();
		}		
		
		StringBuilder sb=new StringBuilder("UPDATE Task_apply SET status='"+status+"', next_empl='"+next_empl.substring(0, next_empl.indexOf(","))+"'");
		if(status.equals("C")){
			sb.append(",edate='"+sf.format(new Date())+"'");
		}
		sb.append("WHERE Oid="+Oid);
		df.exSql(sb.toString());
		msg.setSuccess("已儲存");
		this.savMessage(msg);		
		return edit();
	}

}
