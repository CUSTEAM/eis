package ajax;
 
import java.util.List;
import java.util.Map;
import action.BaseAction;

/**
 * 自動完成empl
 * <p>以idno為關鍵對應教職員</p>
 * @author John
 * @category TODO 檢查是否可整合至AutoCompAnyCode中
 */
public class autoCompleteSchoolUser extends BaseAction{
	
	private Object list[];

	public Object[] getList() {
		return list;
	}

	public void setList(Object[] list) {
		this.list = list;
	}

	public String execute(){
		
		List<Map>tmp=df.sqlGet("SELECT w.Oid, e.cname as username,CONCAT(IFNULL(cd.name,''),IF"
		+ "(cu.name IS NOT NULL && cd.name IS NOT nULL,', ',''), IFNULL(cu.name,''))as unit FROM "
		+ "wwpass w,(empl e LEFT OUTER JOIN CODE_UNIT cu ON e.unit_module=cu.id)LEFT OUTER JOIN "
		+ "CODE_DEPT cd ON cd.id=e.unit  WHERE w.username=e.idno AND (e.cname LIKE'%"+request.getParameter("nameno")+"%' OR "
		+ "cu.name LIKE'%"+request.getParameter("nameno")+"%' OR cd.name LIKE'%"+request.getParameter("nameno")+"%');");
		
		tmp.addAll(df.sqlGet("SELECT w.Oid, s.student_name as username, CONCAT(c.ClassName,', ',s.student_no) as unit "
		+ "FROM wwpass w, stmd s, Class c WHERE w.username=s.student_no AND s.depart_class=c.ClassNo AND s.student_name "
		+ "LIKE '%"+request.getParameter("nameno")+"%' ORDER BY s.student_no"));
				
		
		list=new Object[tmp.size()];
		for(int i=0; i<tmp.size(); i++){
			list[i]=tmp.get(i).get("Oid")+","+tmp.get(i).get("username")+", "+tmp.get(i).get("unit");
		}	
		
		return SUCCESS;
	}
}