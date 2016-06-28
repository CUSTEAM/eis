package ajax.eis;

import java.util.List;

import action.BaseAction;

public class getStdsgeo extends BaseAction{
	
	private List list;
	
	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public String execute(){
		//院
		if(request.getParameter("cid")!=null){
			if(request.getParameter("cid").length()>0){
				this.setList(df.sqlGet("SELECT s.geocode FROM stmd s, Class c WHERE s.depart_class=c.ClassNo AND s.geocode LIKE '%lat%' AND c.InstNo='"+request.getParameter("cid")+"'"));
				return SUCCESS;
			}
			
		}
		//系
		if(request.getParameter("did")!=null){
			if(request.getParameter("did").length()>0){	
				this.setList(df.sqlGet("SELECT s.geocode FROM stmd s, Class c WHERE s.depart_class=c.ClassNo AND s.geocode LIKE '%lat%' AND c.DeptNo='"+request.getParameter("did")+"'"));
				return SUCCESS;
			}
		}	
		//學生
		if(request.getParameter("stdNo")!=null){
			if(request.getParameter("stdNo").length()>0){	
				String geocode=df.sqlGetStr("SELECT s.geocode FROM stmd s WHERE s.geocode LIKE '%lat%' AND s.student_no='"+request.getParameter("stdNo")+"'");
				this.setList(df.sqlGet("SELECT s.geocode FROM stmd s WHERE s.geocode LIKE '%lat%' AND s.student_no='"+request.getParameter("stdNo")+"'"));
				return SUCCESS;
			}
		}
		
		
		return SUCCESS;
	}

}
