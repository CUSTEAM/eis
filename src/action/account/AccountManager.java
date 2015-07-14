package action.account;

import java.text.SimpleDateFormat;
import java.util.Date;
import model.Message;
import service.impl.DataFinder;
import action.BaseAction;

/**
 * 變更帳號密碼
 * @author John
 */
public class AccountManager extends BaseAction{
	
	public String freename;
	public String password;
	DataFinder df = (DataFinder) get("DataFinder");
	public String execute() throws Exception {		
		request.setAttribute("wwpass", df.sqlGetMap("SELECT * FROM wwpass WHERE username='"+getSession().getAttribute("userid")+"'"));
		return SUCCESS;
	}
	
	public String update() throws Exception{		
		if(freename.trim().length()>4){
			df.exSql("UPDATE wwpass SET freename='"+freename+"' WHERE username='"+getSession().getAttribute("userid")+"'");
		}
		
		if(password.trim().length()>4){
			SimpleDateFormat sf=new SimpleDateFormat("yyyy/MM/dd HH:mm");
			df.exSql("UPDATE wwpass SET password='"+password+"', updated='"+sf.format(new Date())+"' WHERE username='"+getSession().getAttribute("userid")+"'");
		}
		
		Message msg=new Message();
		msg.setSuccess("已儲存");
		savMessage(msg);
		return execute();
	}
}