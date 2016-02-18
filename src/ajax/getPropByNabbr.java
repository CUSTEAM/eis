package ajax;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import action.BaseAction;

/**
 * 取得教室財產
 * <p>以以教室代碼取得教室財產清單</p>
 * @author shawn
 *
 */
public class getPropByNabbr extends BaseAction{
	
	private List list;
	

	public List getList() {
		return list;
	}



	public void setList(List list) {
		this.list = list;
	}

	public String execute(){		
		
		String roomid=request.getParameter("roomid");
		
		this.setList(df.sqlGet("SELECT COUNT(*)as cnt, propid, propname FROM propmid WHERE labid='"+roomid+"'GROUP BY propname"));
		
		//System.out.println("SELECT propid, propname FROM propmid WHERE labid='"+roomid+"'");
				
		return SUCCESS;
	}	
	
}