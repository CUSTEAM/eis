package ajax.eis;

import java.util.List;

import action.BaseAction;

public class getStdsFromgeo extends BaseAction{
	
	private List list;
	
	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public String execute(){
		StringBuilder sql;
		if(request.getParameter("cluster")!=null){
			sql=new StringBuilder("SELECT r.lat, r.lng, r.name FROM stmd s, Recruit_school r, Class c WHERE r.geocode IS NOT NULL AND s.schl_code=r.no AND s.depart_class=c.ClassNo ");
			if(!request.getParameter("cno").equals(""))sql.append("AND c.CampusNo='"+request.getParameter("cno")+"'");
			if(!request.getParameter("stno").equals(""))sql.append("AND c.SchoolType='"+request.getParameter("stno")+"'");
			if(!request.getParameter("sno").equals(""))sql.append("AND c.SchoolNo='"+request.getParameter("sno")+"'");
			if(!request.getParameter("cono").equals(""))sql.append("AND c.InstNo='"+request.getParameter("cono")+"'");
			if(!request.getParameter("dno").equals(""))sql.append("AND c.DeptNo='"+request.getParameter("dno")+"'");
			if(!request.getParameter("gno").equals(""))sql.append("AND c.Grade='"+request.getParameter("gno")+"'");
			if(!request.getParameter("zno").equals(""))sql.append("AND c.SeqNo='"+request.getParameter("zno")+"'");
			this.setList(df.sqlGet(sql.toString()));	
			return SUCCESS;
		}
		
		
		
		sql=new StringBuilder("SELECT COUNT(*)as cnt, r.lat, r.lng, r.name FROM stmd s, Recruit_school r, Class c WHERE r.geocode IS NOT NULL AND s.schl_code=r.no AND s.depart_class=c.ClassNo ");
		if(!request.getParameter("cno").equals(""))sql.append("AND c.CampusNo='"+request.getParameter("cno")+"'");
		if(!request.getParameter("stno").equals(""))sql.append("AND c.SchoolType='"+request.getParameter("stno")+"'");
		if(!request.getParameter("sno").equals(""))sql.append("AND c.SchoolNo='"+request.getParameter("sno")+"'");
		if(!request.getParameter("cono").equals(""))sql.append("AND c.InstNo='"+request.getParameter("cono")+"'");
		if(!request.getParameter("dno").equals(""))sql.append("AND c.DeptNo='"+request.getParameter("dno")+"'");
		if(!request.getParameter("gno").equals(""))sql.append("AND c.Grade='"+request.getParameter("gno")+"'");
		if(!request.getParameter("zno").equals(""))sql.append("AND c.SeqNo='"+request.getParameter("zno")+"'");
		sql.append("GROUP BY r.no");		
		this.setList(df.sqlGet(sql.toString()));		
		return SUCCESS;
	}
	
	

}
