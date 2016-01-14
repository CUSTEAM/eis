package ajax;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import action.BaseAction;

/**
 * 自動完成Nabbr
 * <p>教室名稱及代碼自動對應</p>
 * @author John
 * @category TODO 檢查是否可整合至AutoCompAnyCode中
 */
public class autoCompleteNabbrPropmid extends BaseAction{
	
	private Object list[];
	private Object props[];
	
	public String execute() {
		
		List<Map<String, String>>tmp=df.sqlGet("SELECT room_id, name2 FROM Nabbr WHERE room_id LIKE'"+request.getParameter("nameno")+"%'LIMIT 10");
		List<Map<String, String>>tmp1=df.sqlGet("SELECT propid, propname FROM propmid WHERE propid='"+request.getParameter("nameno")+"' ");
		
		list=new Object[tmp.size()];
		props=new Object[tmp.size()];
		for(int i=0; i<tmp.size(); i++){
			//m=new HashMap();
			//m.put("room_id", tmp.get(i).get("room_id")+", "+tmp.get(i).get("name2"));
			//m.put("props", df.sqlGet("SELECT propid, propname FROM propmid WHERE labid='"+tmp.get(i).get("room_id")+"'"));
			list[i]=tmp.get(i).get("room_id")+", "+tmp.get(i).get("name2");			
			//list[i]=m;
		}
		for(int i=0; i<tmp1.size(); i++){
			//m=new HashMap();
			//m.put("room_id", tmp.get(i).get("room_id")+", "+tmp.get(i).get("name2"));
			//m.put("props", df.sqlGet("SELECT propid, propname FROM propmid WHERE labid='"+tmp.get(i).get("room_id")+"'"));
			props[i]=tmp1.get(i).get("propid")+", "+tmp.get(i).get("propname");			
			//list[i]=m;
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