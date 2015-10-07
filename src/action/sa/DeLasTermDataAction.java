package action.sa;

import java.util.List;
import java.util.Map;

import model.Message;
import action.BaseAction;

/**
 * 清除前學期資料
 * - 停用中 -
 * @author shawn
 */
public class DeLasTermDataAction extends BaseAction{
	
	
	public String term[];
	public String Oid[];
	
	
	public String execute(){
		List<Map>tables=df.sqlGet("SELECT * FROM SYS_REUSE_TABLE");
		for(int i=0; i<tables.size(); i++){
			tables.get(i).put("cnt", df.sqlGetInt("SELECT COUNT(*)FROM "+tables.get(i).get("table_name")));
		}
		
		request.setAttribute("tables", tables);
		
		
		/*List<Map>list=df.sqlGet("SELECT s.school_year, s.school_term, depart_class, COUNT(*) FROM Stavg s WHERE "
		+ "s.depart_class!='' GROUP BY s.school_year, s.school_term, s.depart_class");
		List<Map>scores;
		for(int i=0; i<list.size(); i++){			
			scores=df.sqlGet("SELECT Oid, score FROM " +
			"Stavg s WHERE s.school_year='"+list.get(i).get("school_year")+"' AND " +
			"s.school_term='"+list.get(i).get("school_term")+"' AND s.depart_class='"+list.get(i).get("depart_class")+"'");			
			scores=bm.sortListByKey(scores, "score", true);
			for(int j=0; j<scores.size(); j++){						
				df.exSql("UPDATE Stavg SET rank="+(j+1)+" WHERE Oid="+scores.get(j).get("Oid"));
			}
		}*/
		
		return SUCCESS;
	}
	
	
	public String confirm(){
		Message msg=new Message();
		List<Map>tables=df.sqlGet("SELECT * FROM SYS_REUSE_TABLE WHERE clear='1'");
		msg.setSuccess("已清除<br>");
		for(int i=0; i<tables.size(); i++){
			df.exSql("DELETE FROM "+tables.get(i).get("table_name"));
			msg.addSuccess(tables.get(i).get("table_name")+":"+tables.get(i).get("note")+"<br>");
		}
		this.savMessage(msg);		
		return execute();
	}

}
