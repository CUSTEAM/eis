package action;

/**
 * 登出
 * @author John
 */
public class Logout extends BaseAction{
	
	//sso logout
	public String execute() throws Exception {		
		getSession().invalidate();
		response.sendRedirect("/ssos/Logout");//轉送至ssos
		return null;
	}
}
