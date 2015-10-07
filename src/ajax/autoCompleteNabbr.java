package ajax;
 
import java.util.List;
import java.util.Map;
import action.BaseAction;

/**
 * 自動完成Nabbr
 * <p>教室名稱及代碼自動對應</p>
 * @author John
 * @category TODO 檢查是否可整合至AutoCompAnyCode中
 */
public class autoCompleteNabbr extends BaseAction{
	
	private Object list[];
	
	public String execute() {
		List<Map<String, String>>tmp=df.sqlGet("SELECT room_id, name2 FROM Nabbr WHERE (room_id LIKE '"+request.getParameter("nameno")+"%' OR name2 LIKE'"+request.getParameter("nameno")+"%') LIMIT 10");
		list=new Object[tmp.size()];
		
		for(int i=0; i<tmp.size(); i++){
			list[i]=tmp.get(i).get("room_id")+", "+tmp.get(i).get("name2");
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