package ajax;
 
import java.util.List;
import java.util.Map;
import action.BaseAction;

/**
 * 自動完成stmd
 * <p>學號與學生姓名自動對應</p>
 * @author John
 */
public class autoCompleteStmd extends BaseAction{
	
	private Object list[];
	
	public String execute() {
		List<Map>tmp=df.sqlGet("SELECT IFNULL(css.name,'一般生')as status, s.student_no, s.student_name, c.ClassName FROM stmd s LEFT OUTER JOIN CODE_STMD_STATUS css ON s.occur_status=css.id, Class c WHERE " +
		"s.depart_class=c.ClassNo AND (s.student_no LIKE '"+request.getParameter("nameno")+"%' || s.student_name LIKE '%"+request.getParameter("nameno")+"%') LIMIT 10");
		list=new Object[tmp.size()];
		for(int i=0; i<tmp.size(); i++){
			list[i]=tmp.get(i).get("student_no")+","+tmp.get(i).get("student_name")+" - "+tmp.get(i).get("ClassName")+", "+tmp.get(i).get("status");
		}		
        return SUCCESS;
    }
 
	public Object[] getList() {		
		return list;
	}
 
	public void setList(Object[] list) {
		this.list = list;
	}
}