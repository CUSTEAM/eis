package ajax.reg;

import java.util.Map;
import action.BaseAction;
 
/**
 * 學生基本資料
 * @author John
 */
public class getStdContectInfo extends BaseAction{	
	
	public String info="";
	public String getInfo() {
		return info;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}

	public String execute() {
		Map map=df.sqlGetMap("SELECT schl_name, CellPhone, TelePhone, Email, parent_name FROM stmd WHERE student_no='"+request.getParameter("student_no")+"'");
		
		setInfo("電話:"+map.get("TelePhone")+"<br>行動電話:"+
		map.get("CellPhone")+"<br>電子郵件:"+map.get("Email")+"<br>家長代表:"+map.get("parent_name")+"<br>畢業學校:"+map.get("schl_name"));
		
		return SUCCESS;
    } 
}