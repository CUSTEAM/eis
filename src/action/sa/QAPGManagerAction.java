package action.sa;

import java.util.List;
import java.util.Map;

import model.Message;
import action.BaseAction;

/**
 * Q&A類別管理
 * @author shawn
 */
public class QAPGManagerAction extends BaseAction{
	
	public String Oid, parent, action, name;
	
	public String execute(){
		
		List<Map>sys=df.sqlGet("SELECT * FROM SYS WHERE parent='0'");
		List<Map>pg;
		for(int i=0; i<sys.size(); i++){
			pg=df.sqlGet("SELECT * FROM SYS WHERE parent='"+sys.get(i).get("Oid")+"'");
			for(int j=0; j<pg.size(); j++){
				pg.get(j).put("sub", df.sqlGet("SELECT * FROM SYS WHERE parent='"+pg.get(j).get("Oid")+"'"));
			}
			sys.get(i).put("pg", pg);			
		}
		
		request.setAttribute("sys", sys);
		
		return SUCCESS;
	}
	
	public String save(){
		Message msg=new Message();
		try{
			df.exSql("INSERT INTO SYS(parent, name, action)VALUES("+parent+", '"+name+"', '"+action+"')");
			msg.setSuccess("已儲存");
		}catch(Exception e){
			msg.setError("功能重複");
		}
		this.savMessage(msg);
		List<Map>tmp=dm.sqlGet("SELECT * FROM SYS WHERE parent=0");		
		for(int i=0; i<tmp.size(); i++){
			tmp.get(i).put("sub", dm.sqlGet("SELECT * FROM SYS WHERE parent="+tmp.get(i).get("Oid")));
		}
		getContext().setAttribute("allsys", tmp);
		return execute();
	}

}
