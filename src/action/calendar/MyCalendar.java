package action.calendar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import model.Message;
import service.impl.DataFinder;
import action.BaseAction;

/**
 * 個人行事曆
 * @author John
 */
public class MyCalendar extends BaseAction{	
	
	public String execute() throws Exception {	
		Message msg=new Message();
		msg.setMsg("歡迎");
	
		/*DataFinder df = (DataFinder) get("DataFinder");		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		Map map=df.sqlGetMap("SELECT e.cname,w.updated FROM wwpass w,empl e WHERE w.username=e.idno AND w.username='"+getSession().getAttribute("userid")+"'");	
		if(map.get("updated")==null){
			msg.setMsg(map.get("cname")+"同仁您好<br>您的密碼久未更新, 建議重新設定密碼");
			msg.addMsg("<br>教職員公用系統→個人資料→<a href='AccountManager'>變更密碼</a>");			
		}else{
			Date updated=sf.parse(map.get("updated").toString());
			if((new Date().getTime()-updated.getTime())>15552000000L){
				msg.setMsg(map.get("cname")+"同仁您好<br>您的密碼久未更新, 建議重新設定密碼");
				msg.addMsg("<br>教職員公用系統→個人資料→<a href='AccountManager'>變更密碼</a>");
			}
		}*/
		List<Map>tasks=df.sqlGet("SELECT Oid FROM Task_hist th WHERE th.empl='"+getSession().getAttribute("userid")+"'AND edate IS NULL");
		if(tasks.size()>0)msg.addMsg("<br>目前有 "+tasks.size()+"項意見反應尚未回覆");
		savMessage(msg);
		request.setAttribute("emplOid", df.sqlGetStr("SELECT Oid FROM empl WHERE idno='"+getSession().getAttribute("userid")+"'"));
		return "intro";
	}
}