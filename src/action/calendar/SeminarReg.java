package action.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import model.Message;
import action.BaseAction;

public class SeminarReg extends BaseAction{
	
	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	public String level;
	public String no;
	
	public String execute() throws ParseException{
		
		
		List<Map>exams=df.sqlGet("SELECT (SELECT COUNT(*)FROM LC_exam_regs ls, empl s WHERE ls.student_no=s.idno AND ls.level=e.level AND ls.no=e.no)as ecnt,(SELECT COUNT(*)FROM LC_exam_regs ls, stmd s, Class c WHERE " +
				"ls.student_no=s.student_no AND c.ClassNo=s.depart_class AND c.Grade='1' AND ls.level=e.level AND ls.no=e.no)as cnt1," +
				"(SELECT COUNT(*)FROM LC_exam_regs ls, stmd s, Class c WHERE ls.student_no=s.student_no AND " +
				"c.ClassNo=s.depart_class AND c.Grade='2' AND ls.level=e.level AND ls.no=e.no)as cnt2,(SELECT COUNT(*)FROM " +
				"LC_exam_regs ls, stmd s, Class c WHERE ls.student_no=s.student_no AND c.ClassNo=s.depart_class AND c.Grade='3' " +
				"AND ls.level=e.level AND ls.no=e.no)as cnt3,(SELECT COUNT(*)FROM LC_exam_regs ls, stmd s, Class c WHERE " +
				"ls.student_no=s.student_no AND c.ClassNo=s.depart_class AND c.Grade='4' AND ls.level=e.level AND ls.no=e.no)as cnt4," +
				"s.regdate, e.* FROM LC_exam e LEFT OUTER JOIN LC_exam_regs s ON " +
				"e.level=s.level AND e.no=s.no AND s.student_no='"+getSession().getAttribute("userid")+"' ORDER BY e.level,e.no");
		for(int i=0; i<exams.size(); i++){
			exams.get(i).put("time",(sf.parse(exams.get(i).get("exam_end").toString()).getTime()-sf.parse(exams.get(i).get("exam_begin").toString()).getTime())/1000/60);
		}
		request.setAttribute("exams", exams);		
		return SUCCESS;
	}
	
	/**
	 * 報名
	 * @return
	 * @throws ParseException 
	 */
	public String sign() throws ParseException{
		
		String idno=getSession().getAttribute("userid").toString();
		Message msg=new Message();
		
		int stds=df.sqlGetInt("SELECT COUNT(*) FROM LC_exam_regs r, stmd s WHERE " +
		"r.student_no=s.student_no AND r.level='"+level+"' AND r.no='"+no+"'");
		
		int quota=df.sqlGetInt("SELECT empls FROM LC_exam WHERE level='"+level+"' AND no='"+no+"'");
		
		if(stds>=quota){			
			msg.setError("人數已滿");
			savMessage(msg);
			return execute();
		}
					
		try{			
			df.exSql("INSERT INTO LC_exam_regs(student_no,level,no)VALUES('"+idno+"','"+level+"','"+no+"');");
		}catch(Exception e){
			e.printStackTrace();
			msg.setError("報名失敗，相同梯次或場次重複報名");
			savMessage(msg);
			return execute();
		}
		
		msg.setSuccess("報名完成");
		savMessage(msg);
		return execute();
	}
	
	/**
	 * 取消
	 * @return
	 * @throws ParseException 
	 */
	public String cancel() throws ParseException{
		
		df.exSql("DELETE FROM LC_exam_regs WHERE student_no='"+getSession().getAttribute("userid")+"' AND level='"+level+"' AND no='"+no+"'");
		
		Message msg=new Message();
		msg.setSuccess("已取消");
		savMessage(msg);
		return execute();	}

}