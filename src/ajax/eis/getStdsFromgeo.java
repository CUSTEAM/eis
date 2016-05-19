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
		if(request.getParameter("cid")!=null){
			if(request.getParameter("cid").length()>0){
				this.setList(
					df.sqlGet("SELECT r.* FROM stmd s, Recruit_school r, Class c WHERE "
					+ "r.lat IS NOT NULL AND s.schl_code=r.no AND s.depart_class=c.ClassNo AND c.InstNo='"+request.getParameter("cid")+"'"));
				return SUCCESS;
			}
			
		}
		if(request.getParameter("did")!=null){
			if(request.getParameter("did").length()>0){	
				this.setList(
						df.sqlGet("SELECT r.* FROM stmd s, Recruit_school r, Class c WHERE "
								+ "r.lat IS NOT NULL AND s.schl_code=r.no AND s.depart_class=c.ClassNo AND c.DeptNo='"+request.getParameter("did")+"'"));	
				return SUCCESS;
			}
		}	
		return SUCCESS;
	}

}
