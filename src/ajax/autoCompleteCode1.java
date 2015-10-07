package ajax;
 
import java.util.List;
import java.util.Map;
import action.BaseAction;

/**
 * 自動完成Code1
 * <p>以id為關鍵對應code1資料表</p>
 * @author John
 * @category TODO 檢查是否可整合至AutoCompAnyCode中
 */
public class autoCompleteCode1 extends BaseAction{
	
	private Object list[];
	
	public String execute() {
		List<Map<String, String>>tmp=df.sqlGet("SELECT name FROM code1 WHERE " +
		"(no LIKE '"+request.getParameter("nameno")+"%' || name LIKE '%"+request.getParameter("nameno")+"%') LIMIT 10");
		list=new Object[tmp.size()];
		for(int i=0; i<tmp.size(); i++){
			list[i]=((Map)tmp.get(i)).get("name");
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