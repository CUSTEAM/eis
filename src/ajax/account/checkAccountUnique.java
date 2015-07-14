package ajax.account;

import action.BaseAction;

/**
 * 檢查帳號重複
 * @author John
 */
public class checkAccountUnique extends BaseAction{
	
	private String pass;	
	
	public String execute() {
		pass="0";
		
		System.out.println("SELECT COUNT(*)FROM wwpass WHERE freename='"+request.getParameter("freename")+"'");
		if(df.sqlGetInt("SELECT COUNT(*)FROM wwpass WHERE freename='"+request.getParameter("freename")+"'")>0){
			pass="1";
		}
		return SUCCESS;
    }

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
}