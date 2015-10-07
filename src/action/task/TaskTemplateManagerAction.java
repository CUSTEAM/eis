package action.task;

import model.Message;
import action.BaseAction;

/**
 * 單位內部任務管理
 * 工作單
 * @author shawn
 */
public class TaskTemplateManagerAction extends BaseAction{
	
	public String Oid[];
	public String unit[];
	public String title[];
	public String template[];
	public String open[];
	
	public String execute(){
		String unit=df.sqlGetStr("SELECT unit_module FROM empl WHERE idno='"+getSession().getAttribute("userid")+"'");
		request.setAttribute("unit", unit);		
		request.setAttribute("ot", df.sqlGet("SELECT * FROM Task WHERE unit='"+unit+"'"));
		return SUCCESS;
	}
	
	public String add(){
		Message msg=new Message();
		if(title[0].trim().equals("")){
			msg.setError("必須有名稱");
			this.savMessage(msg);
			return execute();
		}
		try{
			df.exSql("INSERT INTO Task(unit,title,template,open)VALUES('"+unit[0]+"','"+title[0]+"','"+template[0]+"','"+open[0]+"')");
			msg.setSuccess("建立完成");
		}catch(Exception e){
			msg.setError("建立失敗，請檢查內容");
		}		
		this.savMessage(msg);
		return execute();
	}
	
	public String edit(){
		Message msg=new Message();		
		for(int i=1; i<Oid.length; i++){			
			if(!Oid[i].equals("")){
				if(title[i].trim().equals("")){
					msg.setError("必須有名稱");
					this.savMessage(msg);
					return execute();
				}
				try{
					df.exSql("UPDATE Task SET unit='"+unit[i]+"', title='"+title[i]+"', template='"+template[i]+"', open='"+open[i]+"' WHERE Oid="+Oid[i]);
					msg.setSuccess("修改完成");
				}catch(Exception e){
					msg.setError("修改失敗，請檢查內容");
				}
			}
		}		
		this.savMessage(msg);
		return execute();
	}
}