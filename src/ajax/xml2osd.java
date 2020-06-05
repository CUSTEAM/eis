package ajax;

import javax.servlet.http.Cookie;

import action.BaseAction;

public class xml2osd extends BaseAction{
	
	private boolean flag;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	public String execute() {
		
		Cookie[] cookies = request.getCookies();
		boolean b=false;
		if(cookies!=null)
		for(int i=0;i<cookies.length;i++){			
			if(cookies[i].getName().equals("doctype")){
				cookies[i].setDomain(".cust.edu.tw");
				cookies[i].setPath("/");
		        cookies[i].setMaxAge(0);
		        cookies[i].setValue(null);
		        response.addCookie(cookies[i]);
				
				b=true;
			}
			
			if(!b){
				Cookie cookie = new Cookie("doctype", "odf");	    	
				cookie.setMaxAge(60*60*24*365); // 瀏覽器關閉失效   	
		    	cookie.setDomain(".cust.edu.tw");
		    	cookie.setPath("/");
		    	response.addCookie(cookie);
			}
			
        }
		
		
	
		
		
		
		this.setFlag(b);
		
		return SUCCESS;
	}

}
