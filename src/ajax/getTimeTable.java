package ajax;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import action.BaseAction;

/**
 * 學生或教職員課表
 * <p>以學號或身分證號產生excel課表</p>
 * @author shawn
 *
 */
public class getTimeTable extends BaseAction{
	
	private List list;
	

	public List getList() {
		return list;
	}



	public void setList(List list) {
		this.list = list;
	}



	public String execute(){		
		String ClassNo=request.getParameter("ClassNo");
		String student_no=request.getParameter("student_no");
		String emplOid=request.getParameter("emplOid");
		String nabbr=request.getParameter("nabbr");
		String term=request.getParameter("term");
		if(term==null)term=getContext().getAttribute("school_term").toString();
		
		//取班級課表
		if(ClassNo!=null){
			setList(getTime(ClassNo, "ClassNo", term));
			return SUCCESS;
		}
		
		//取學生課表
		if(student_no!=null){
			setList(getTime(student_no, "student", term));
			return SUCCESS;
		}
		
		//取教師課表
		if(emplOid!=null){
			setList(getTime(df.sqlGetStr("SELECT idno FROM empl WHERE Oid="+emplOid), "techid", term));
			return SUCCESS;
		}
		
		//取教室課表
		if(nabbr!=null){
			
			setList(getTime(nabbr, "nabbr", term));
			//setList(getTime(nabbr, "nabbr", getContext().getAttribute("school_term").toString()).toArray());
			return SUCCESS;
		}		
		return SUCCESS;
	}	
	
	/**
	 * 
	 * @param value
	 * @param target
	 * @param term
	 * @return
	 */
	public List getTime(String value, String target, String term){
		StringBuilder sb=new StringBuilder();
		switch(target){
			case"ClassNo":return df.sqlGet("SELECT d.Oid, dc.week, dc.begin, dc.end, IFNULL(dc.place,'')as place,"
					+ "cs.chi_name,IFNULL(e.cname, '')as cname,c.ClassName FROM Dtime d LEFT OUTER JOIN empl e ON "
					+ "d.techid=e.idno, Dtime_class dc, Class c, Csno cs WHERE d.Sterm='"+term+"'AND cs.cscode=d.cscode AND "
					+ "c.ClassNo=d.depart_class AND d.Oid=dc.Dtime_oid AND d.depart_class ='"+value+"'");
			case"techid":return df.sqlGet("SELECT d.Oid, dc.week, dc.begin, dc.end, IFNULL(dc.place,'')as place,"
					+ "cs.chi_name,IFNULL(e.cname, '')as cname, c.ClassName FROM Dtime d LEFT OUTER JOIN empl e ON "
					+ "d.techid=e.idno, Dtime_class dc, Class c, Csno cs WHERE d.Sterm='"+term+"'AND cs.cscode=d.cscode AND "
					+ "c.ClassNo=d.depart_class AND d.Oid=dc.Dtime_oid AND d.techid ='"+value+"'");
			case"nabbr":return df.sqlGet("SELECT d.Oid, dc.week, dc.begin, dc.end, IFNULL(dc.place,'')as place,"
					+ "cs.chi_name,IFNULL(e.cname, '')as cname, c.ClassName FROM Dtime d LEFT OUTER JOIN empl e ON "
					+ "d.techid=e.idno, Dtime_class dc, Class c, Csno cs WHERE cs.cscode=d.cscode AND "
					+ "c.ClassNo=d.depart_class AND d.Oid=dc.Dtime_oid AND dc.place ='"+value+"' AND d.Sterm='"+term+"'");
			case"student":return df.sqlGet("SELECT d.Oid, dc.week, dc.begin, dc.end, IFNULL(dc.place,'')as place,"
					+ "cs.chi_name,IFNULL(e.cname, '')as cname, c.ClassName FROM Dtime d LEFT OUTER JOIN empl e ON "
					+ "d.techid=e.idno, Dtime_class dc, Class c, Csno cs WHERE d.Sterm='"+term+"'AND cs.cscode=d.cscode AND "
					+ "c.ClassNo=d.depart_class AND d.Oid=dc.Dtime_oid AND d.Oid IN(SELECT Dtime_oid FROM Seld WHERE student_no='"+value+"')");
		}		
		return null;
	}
	
	
}