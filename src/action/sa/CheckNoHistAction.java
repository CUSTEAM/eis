package action.sa;

import java.util.List;

import model.Message;
import action.BaseAction;

public class CheckNoHistAction extends BaseAction{
	
	public String cno, tno, year, term, gno, target;
	
	public String execute(){
		
		//df.sqlGet("");
		
		
		return SUCCESS;
	}
	
	public String check(){
		
		
		Message msg=new Message();
		List stds=df.sqlGet("SELECT css.name, c.ClassNo, c.ClassName, st.student_no, st.student_name FROM "
		+ "stmd st LEFT OUTER JOIN CODE_STMD_STATUS css ON st.occur_status=css.id, Class c WHERE c.CampusNo='"+cno+"' AND c.SchoolType='"+tno+"' AND c.Grade='"+gno+"' AND "
		+ "st.depart_class=c.ClassNo AND st.student_no NOT IN(SELECT s.student_no FROM "+target+" s "
		+ "WHERE s.school_year='"+year+"' AND s.school_term='"+term+"')ORDER BY c.CampusNo, c.DeptNo, c.Grade");
		request.setAttribute("stds", stds);
		msg.setSuccess(stds.size()+"位學生查無當年度資料");
		this.savMessage(msg);
		return SUCCESS;
	}
}
