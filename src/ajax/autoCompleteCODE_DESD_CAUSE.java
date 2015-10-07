package ajax;
 
import java.util.List;
import java.util.Map;
import action.BaseAction;

/**
 * 自動完成CODE_DESD_CAUSE
 * @author John
 * @category TODO 檢查是否可整合至AutoCompAnyCode中
 */
public class autoCompleteCODE_DESD_CAUSE extends BaseAction{
	
	private Object list[];
	
	public String execute() {
		List tmp=df.sqlGet("SELECT id, name FROM CODE_DESD_CAUSE WHERE " +
		"(id LIKE '"+request.getParameter("nameno")+"%' || name LIKE '%"+request.getParameter("nameno")+"%') ORDER BY id LIMIT 20");
		list=new Object[tmp.size()];
		for(int i=0; i<tmp.size(); i++){
			list[i]=((Map)tmp.get(i)).get("id")+", "+((Map)tmp.get(i)).get("name");
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