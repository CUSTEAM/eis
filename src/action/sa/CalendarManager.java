package action.sa;

import model.Message;
import action.BaseAction;

/**
 * 個人/會議 行事曆管理
 * @author shawn
 *
 */
public class CalendarManager extends BaseAction{
	
	public String cdate[];
	public String name[];
	
	public String execute(){
		
		/*String sys="";
		if(request.getParameter("sys")!=null || getSession().getAttribute("sys_calendar")!=null){
			
			if(request.getParameter("sys")!=null){
				getSession().setAttribute("sys_calendar", request.getParameter("sys"));
				sys=request.getParameter("sys");
			}else{
				sys=getSession().getAttribute("sys_calendar").toString();
			}
		}
		request.setAttribute("cals", df.sqlGet("SELECT *, DATE_FORMAT(cdate,'%Y-%m-%d %H:%i')as date FROM SYS_CALENDAR WHERE sys LIKE'"+sys+"%'"));*/
		//if(getSession().getAttribute("sys_cal"))
		StringBuilder sql=new StringBuilder("SELECT *, DATE_FORMAT(cdate,'%Y-%m-%d %H:%i')as date FROM SYS_CALENDAR ");
		
		if(request.getParameter("sys")==null){			
			if(getSession().getAttribute("sys_calendar")!=null){
				if(!getSession().getAttribute("sys_calendar").equals("cisa")){
					sql.append("WHERE sys='"+getSession().getAttribute("sys_calendar")+"'");
				}
				sql.append("ORDER BY s_group");
				request.setAttribute("cals", df.sqlGet(sql.toString()));
			}			
		}else{
			
			getSession().setAttribute("sys_calendar", request.getParameter("sys"));
			if(!request.getParameter("sys").equals("cisa")){
				sql.append("WHERE sys='"+getSession().getAttribute("sys_calendar")+"'");
			}
			sql.append("ORDER BY s_group");
			request.setAttribute("cals", df.sqlGet(sql.toString()));			
		}
		
		
		return SUCCESS;
	}
	
	public String update(){
		for(int i=0; i<name.length; i++){			
			if(!name[i].trim().equals("")){
				df.exSql("UPDATE SYS_CALENDAR SET cdate='"+cdate[i]+"', editor='"+df.sqlGetStr("SELECT cname FROM empl WHERE idno='"+session.get("userid")+"'")+"' WHERE name='"+name[i]+"'");
			}			
		}
		
		request.setAttribute("lock", "1");
		Message msg=new Message();
		msg.setSuccess("已儲存, 將於15分鐘後生效");
		this.savMessage(msg);
		return execute();
	}

}
