package action;

import javax.servlet.http.Cookie;

/**
 * 登出
 * @author John
 */
public class Logout extends BaseAction{	
	//sso logout
	public String execute() throws Exception {	
		/*Cookie c[]=request.getCookies();
		if(c.length>0)
		for(int i=0; i<c.length; i++){	
			System.out.println(c[i].getPath()+":"+c[i].getName()+":"+c[i].getValue()+":"+c[i].getMaxAge());
			c[i].setValue(null);
            //c[i].setPath(null);
            c[i].setMaxAge(0);            
	    	response.addCookie(c[i]);
		}*/
		Cookie cookie = new Cookie("unit", null);
		cookie.setMaxAge(0); //瀏覽器關閉失效    	
    	cookie.setDomain(".cust.edu.tw");
    	cookie.setPath("/");
    	
    	response.addCookie(cookie);
		getSession().invalidate();
		response.sendRedirect("/ssos/");//轉送至ssos
		return null;
	}
}
