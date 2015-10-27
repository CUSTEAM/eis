package action.sa;

import javax.servlet.http.Cookie;

import action.BaseAction;

public class ModuleUnitManagerAction extends BaseAction{
	
	public String unit;
	
	public String execute(){
		
		
		
		return SUCCESS;
	}
	
	public String changUnit(){
		
		Cookie c[]=request.getCookies();
		for(int i=0; i<c.length; i++){
			if(c[i].getName().equals("unit")){
				c[i].setValue(c[i].getValue()+","+unit);
			}
			System.out.println(c[i].getName()+":"+c[i].getValue());
		}
		
		return SUCCESS;
	}

}
