package ajax;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import model.PubCalendar;
import action.BaseAction;

/**
 * 編輯個人/會議行事曆
 * @author JOHN
 */
public class editMySchedule extends BaseAction {

	public String execute() throws Exception {
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		String method = request.getParameter("method");
		String start_date = request.getParameter("start_date");
		String end_date = request.getParameter("end_date");
		String details = request.getParameter("details");
		String members = request.getParameter("members");
		String rec_type = request.getParameter("rec_type");
		String name =  request.getParameter("name");
		String event_length = request.getParameter("event_length");
		String id = request.getParameter("id");
		if (id.indexOf("#") > 0) {
			id = id.substring(0, request.getParameter("id").indexOf("#"));
		}
		
		PubCalendar c;

		// 修改
		if (method.equals("changeEvent")) {
			try {
				// 擁有者，刪除自己和所有人，再塞一份新的給自己和所有人
				c = (PubCalendar) df.hqlGetListBy(
				"FROM PubCalendar WHERE no='" + id + "' AND sender='"
				+ getSession().getAttribute("userid") + "'").get(0);//
				c.setOid(null);
				c.setBegin(sf.parse(start_date));
				c.setEnd(sf.parse(end_date));
				c.setNote(details);
				c.setMembers(members);
				c.setName(name);

				df.exSql("DELETE FROM Calendar WHERE no='" + id + "'");
				if (!members.trim().equals("")) {
					
					List list = am.analyseEmpl(bl.analyse(members));
					for (int i = 0; i < list.size(); i++) {
						c.setAccount(((Map) list.get(i)).get("idno").toString());
						df.update(c);
						c.setOid(null);
					}
				}
				c.setAccount((String) getSession().getAttribute("userid"));// 自己
				df.update(c);

			} catch (Exception e) {
				e.printStackTrace();
				// 非擁有者，刪除自己，再塞一份給自己
				c = (PubCalendar) df.hqlGetListBy(
				"FROM PubCalendar WHERE no='" + id + "' AND account='"
				+ getSession().getAttribute("userid") + "'").get(0);
				c.setOid(null);
				df.exSql("DELETE FROM Calendar WHERE no='" + id
						+ "' AND account='" + getSession().getAttribute("userid") + "'");
				c.setBegin(sf.parse(start_date));
				c.setEnd(sf.parse(end_date));
				c.setNote(details);
				c.setMembers(members);
				c.setName(name);
				// if(!rec_type.equals("")&&!rec_type.equals("undefined")){c.setRec_type(rec_type);}//重複型態
				df.update(c);
			}
		}
		System.out.println("work??????");
		// 新增
		if (method.equals("addEvent")) {
			c = new PubCalendar();
			c.setBegin(sf.parse(start_date));
			c.setEnd(sf.parse(end_date));
			c.setNote(details);
			c.setBegin(sf.parse(start_date));
			c.setEnd(sf.parse(end_date));
			c.setNote(details);
			c.setMembers(members);
			c.setName(name);
			c.setSender((String) getSession().getAttribute("userid"));
			// c.setNo(String.valueOf(manager.ezGetInt("SELECT no FROM Calendar ORDER BY no DESC LIMIT 1")+1));
			c.setNo(id);
			
			if (!rec_type.equals("") && !rec_type.equals("undefined")) {
				// 若為重複型態要設定時間長度
				c.setRec_type(rec_type);
				c.setRec_event_length(Integer.parseInt(event_length));
			}
			System.out.println(members);
			List<Map>list = am.analyseEmpl(bl.analyse(members));
			if (list.size() > 0)
				for (int i = 0; i < list.size(); i++) {
					c.setAccount(list.get(i).get("idno").toString());
					df.update(c);
					
					c.setOid(null);
				}

			c.setAccount((String) getSession().getAttribute("userid"));// 自己
			df.update(c);
		}

		if (method.equals("deleteEvent")) {
			df.exSql("DELETE FROM Calendar WHERE no='" + id + "' AND account='"
					+ getSession().getAttribute("userid") + "'");
		}

		PrintWriter out = response.getWriter();
		out.println("<data>");
		out.println("<msg>finish</msg>");
		out.println("</data>");
		out.close();

		return null;
	}
	
	private void sendMail(List<Map>list){
		
		//bio.sendMail(host, receiver, sender, subject, mess, username, password);
		
		
	}
}
