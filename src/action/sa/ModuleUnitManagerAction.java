package action.sa;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import action.BaseAction;

public class ModuleUnitManagerAction extends BaseAction{
	
	public String unit;
	
	public String execute(){
		
		List<Map>list=(List<Map>) getContext().getAttribute("CODE_UNIT");
		for(int i=0; i<list.size(); i++){
			list.get(i).put("moduleCnt", df.sqlGetInt("SELECT COUNT(*)FROM SYS_MODULE_UNIT WHERE unit_id='"+list.get(i).get("id")+"'"));
		}
		getContext().setAttribute("CODE_UNIT", list);
		return SUCCESS;
	}
	
	public String changUnit(){
		
		Cookie c[]=request.getCookies();
		for(int i=0; i<c.length; i++){
			if(c[i].getName().equals("unit")){
				c[i].setValue(c[i].getValue()+","+unit);
			}
			c[i].setMaxAge(60*60*24*365); // 瀏覽器關閉失效   	
			c[i].setDomain(".cust.edu.tw");
			c[i].setPath("/");
	    	response.addCookie(c[i]);
			System.out.println(c[i].getName()+":"+c[i].getValue());
		}
		
		return SUCCESS;
	}

}
