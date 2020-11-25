package action.counselor;

import java.util.List;
import java.util.Map;

import action.BaseAction;
import model.Counselor_msg;
import model.Counselor_stmd;
import model.Message;

public class CounseFeedbackAction extends BaseAction{
	
	public String beginDate, endDate;
	
	public String Oid, student_name, cell_phone, SchoolNo, DeptNo, add_time, note, reply;
	
	
	private List GetDepts() {	
		
		return df.sqlGet("SELECT * FROM Counselor_charge WHERE idno='"+getSession().getAttribute("userid")+"'");
	}
	
	public String execute() {	
		List<Map>dept=GetDepts();
		if(dept.size()==0) {
			Message msg=new Message();
			msg.setError("無負責科系");
			this.savMessage(msg);
			return SUCCESS;
		}
		StringBuilder sql=new StringBuilder("SELECT(SELECT COUNT(*)FROM Counselor_msg WHERE stmd_oid=s.Oid)as cnt,"
		+ "d.name as DeptName,s.* FROM Counselor_stmd s, CODE_DEPT d WHERE s.DeptNo=d.id AND s.DeptNo IN(");
		for(int i=0; i<dept.size(); i++) {
			sql.append("'"+dept.get(i).get("DeptNo")+"',");
		}
		sql.delete(sql.length()-1, sql.length());
		sql.append(")");			
		request.setAttribute("stds", df.sqlGet(sql.toString()));		
		return SUCCESS;
	}
	
	public String search() {
		List<Map>dept=GetDepts();
		if(dept.size()==0) {
			Message msg=new Message();
			msg.setError("無負責科系");
			this.savMessage(msg);
			return SUCCESS;
		}
		StringBuilder sql=new StringBuilder("SELECT(SELECT COUNT(*)FROM Counselor_msg WHERE stmd_oid=s.Oid)as cnt,"
				+ "d.name as DeptName,s.* FROM Counselor_stmd s, CODE_DEPT d WHERE s.DeptNo=d.id AND s.DeptNo IN(");
				for(int i=0; i<dept.size(); i++) {
					sql.append("'"+dept.get(i).get("DeptNo")+"',");
				}
				sql.delete(sql.length()-1, sql.length());
				sql.append(")");
				
				if(beginDate!=null)
				if(!beginDate.equals("")) {
					sql.append("AND add_time>='"+beginDate+"'");
				}
				if(endDate!=null)
				if(!endDate.equals("")) {
					sql.append("AND add_time<='"+endDate+"'");
				}
				request.setAttribute("stds", df.sqlGet(sql.toString()));
		return SUCCESS;
		
		
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
