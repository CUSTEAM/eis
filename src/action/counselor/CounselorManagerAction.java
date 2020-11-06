package action.counselor;

import action.BaseAction;
import model.Message;

public class CounselorManagerAction extends BaseAction{
	
	public String beginDate, endDate, DeptStd, DeptMamber, techid;
	
	public String execute() {
		
		if(request.getParameter("delMember")!=null) {
			//System.out.println("DELETE FROM Counselor_stmd WHERE Oid="+request.getParameter("delMember"));
			df.exSql("DELETE FROM Counselor_charge WHERE Oid="+request.getParameter("delMember"));
			return searchMambers();
		}
		
		return SUCCESS;
	}
	
	public String searchStds() {
		
		StringBuilder sql=new StringBuilder("SELECT (SELECT COUNT(*)FROM Counselor_msg WHERE stmd_oid=c.Oid)as cnt, c.* FROM Counselor_stmd c WHERE c.student_name IS NOT NULL");
		if(!beginDate.trim().equalsIgnoreCase(""))sql.append(" AND c.add_time>='"+beginDate+"'");
		if(!endDate.trim().equalsIgnoreCase(""))sql.append(" AND c.add_time<='"+beginDate+"'");
		if(!DeptStd.trim().equalsIgnoreCase(""))sql.append(" AND c.DeptNo='"+DeptStd+"'");
		//System.out.println(sql);
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
			msg.addError("未指定人員");
			return SUCCESS;
		}
		
		StringBuilder sql=new StringBuilder("INSERT INTO Counselor_charge(DeptNo, idno)VALUES('"+DeptMamber+"', '"+df.sqlGetStr("SELECT idno FROM empl WHERE Oid='"+techid.substring(0, techid.indexOf(","))+"'")+"');");
		
		
		//request.setAttribute("", df.sqlGet(sql.toString()));
		
		
		
		df.exSql(sql.toString());
		
		
		return searchMambers();
	}

}
