package action.counselor;

import action.BaseAction;
import model.Counselor_msg;
import model.Counselor_stmd;
import model.Message;

public class CounselorManagerAction extends BaseAction{
	
	public String beginDate, endDate, DeptStd, DeptMamber, techid, Oid, reply, note;
	
	public String execute() {
		
		if(request.getParameter("delMember")!=null) {
			
			//System.out.println("DELETE FROM Counselor_stmd WHERE Oid="+request.getParameter("delMember"));
			df.exSql("DELETE FROM Counselor_charge WHERE Oid="+request.getParameter("delMember"));
			Message msg=new Message();
			msg.setError("已刪除");
			this.savMessage(msg);
		}
		
		
		return SUCCESS;
	}
	
	public String searchStds() {
		
		StringBuilder sql=new StringBuilder("SELECT(SELECT COUNT(*)FROM Counselor_msg WHERE stmd_oid=s.Oid)as cnt,"
		+ "d.name as DeptName,s.* FROM Counselor_stmd s, CODE_DEPT d WHERE s.DeptNo=d.id ");
		if(!DeptStd.contentEquals("")) {
			sql.append("AND d.id='"+DeptStd+"'");
		}
		if(!beginDate.equals("")) {
			sql.append("AND add_time>='"+beginDate+"'");
		}
		
		if(!endDate.equals("")) {
			sql.append("AND add_time<='"+endDate+"'");
		}
		request.setAttribute("stds", df.sqlGet(sql.toString()));		
				
		
		return SUCCESS;
	}
	
	public String searchMambers() {
		
		StringBuilder sql=new StringBuilder("SELECT c.*, cd.name as DeptName, e.cname FROM empl e, Counselor_charge c, CODE_DEPT cd WHERE c.DeptNo=cd.id AND c.idno=e.idno ");
		if(!DeptMamber.trim().equals(""))sql.append(" AND c.DeptNo='"+DeptMamber+"'");
		if(!techid.trim().equals(""))sql.append(" AND e.idno='"+df.sqlGetStr("SELECT idno FROM empl WHERE Oid='"+techid.substring(0, techid.indexOf(","))+"'")+"'");
		//System.out.println(sql);
		request.setAttribute("members", df.sqlGet(sql.toString()));
		return SUCCESS;
	}
	
	public String addMambers() {
		
		Message msg=new Message();
		if(techid.trim().indexOf(",")<1) {
			msg.setError("未指定人員");
			this.savMessage(msg);
			return SUCCESS;
		}
		
		StringBuilder sql=new StringBuilder("INSERT INTO Counselor_charge(DeptNo, idno)VALUES('"+DeptMamber+"', '"+df.sqlGetStr("SELECT idno FROM empl WHERE Oid='"+techid.substring(0, techid.indexOf(","))+"'")+"');");
		
		try {
			df.exSql(sql.toString());
			msg.setError("已建立");
		}catch(Exception e) {
			msg.setError("重複建立!");
		}
		
		//Message msg=new Message();
		
		this.savMessage(msg);
		
		return searchMambers();
	}

	public String edit() {		
		request.setAttribute("info", df.sqlGetMap("SELECT s.*, cs.name as DeptName FROM Counselor_stmd s LEFT OUTER JOIN CODE_DEPT cs ON cs.id=s.SchoolNo WHERE s.Oid="+Oid));
		request.setAttribute("ms", df.sqlGet("SELECT*FROM Counselor_msg WHERE stmd_oid="+Oid+" ORDER BY Oid DESC"));
		
		return SUCCESS;
	}
	
	public String save() {
		Counselor_stmd s;
		s=(Counselor_stmd) df.hqlGetListBy("FROM Counselor_stmd WHERE Oid="+Oid).get(0);		
		s.setReply(reply);
		//s.setNote(note);
		df.update(s);
		
		Counselor_msg m=new Counselor_msg();
		m.setStmd_oid(Integer.parseInt(Oid));
		m.setName(df.sqlGetStr("SELECT cname FROM empl WHERE idno='"+getSession().getAttribute("userid")+"'"));
		m.setNote(note);
		df.update(m);
		
		request.setAttribute("info", df.sqlGetMap("SELECT s.*, cs.name as DeptName FROM Counselor_stmd s LEFT OUTER JOIN CODE_DEPT cs ON cs.id=s.SchoolNo WHERE s.Oid="+Oid));
		request.setAttribute("ms", df.sqlGet("SELECT*FROM Counselor_msg WHERE stmd_oid="+Oid +" ORDER BY Oid DESC"));
		return edit();
	}
}
