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
		System.out.println("cid="+request.getParameter("cid"));
		System.out.println("did="+request.getParameter("did"));
		if(request.getParameter("cid")!=""){
			this.setList(df.sqlGet("SELECT s.geocode FROM stmd s, Class c WHERE s.depart_class=c.ClassNo AND s.geocode LIKE '%lat%' AND c.InstNo='"+request.getParameter("cid")+"'"));
			return SUCCESS;
		}
		if(request.getParameter("did")!=""){
			System.out.println("SELECT s.geocode FROM stmd s, Class c WHERE s.depart_class=c.ClassNo AND s.geocode LIKE '%lat%' AND c.DeptNo='"+request.getParameter("did")+"'");
			this.setList(df.sqlGet("SELECT s.geocode FROM stmd s, Class c WHERE s.depart_class=c.ClassNo AND s.geocode LIKE '%lat%' AND c.DeptNo='"+request.getParameter("did")+"'"));
			return SUCCESS;
		}	
		return SUCCESS;
	}

}
