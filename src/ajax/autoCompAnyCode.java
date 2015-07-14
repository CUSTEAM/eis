package ajax;
 
import java.util.List;
import java.util.Map;

import action.BaseAction;

/**
 * 自動完成各項代碼 * 
 * idCol ID欄位
 * nameCol NAME欄位
 * table 資料表
 * value 查詢值
 * 一般範例 /eis/autoCompAnyCode?idCol=id&nameCol=name&table=CODE_UNIT&value=電
 * bootstrap範例 /eis/autoCompAnyCode?bootstrap=1&idCol=id&nameCol=name&table=CODE_UNIT&value=電
 * @author shawn
 *
 */
public class autoCompAnyCode extends BaseAction{
	
	private Object list[];

	public Object[] getList() {
		return list;
	}

	public void setList(Object[] list) {
		this.list = list;
	}

	public String execute(){
		if(request.getParameter("bootstrap")==null){
			setList(df.sqlGet("SELECT "+request.getParameter("idCol")+" as id, "+request.getParameter("nameCol")+" as name FROM "+request.getParameter("table")+
			" WHERE id LIKE'"+request.getParameter("value")+"%' OR name LIKE'"+request.getParameter("value")+"%'").toArray());		
			return SUCCESS;
		}else{
			List<Map>tmp=df.sqlGet("SELECT "+request.getParameter("idCol")+" as id, "+request.getParameter("nameCol")+" as name FROM "+request.getParameter("table")+
					" WHERE "+request.getParameter("idCol")+" LIKE'"+request.getParameter("value")+"%' OR "+request.getParameter("nameCol")+" LIKE'"+request.getParameter("value")+"%'");
			
			list=new Object[tmp.size()];
			for(int i=0; i<tmp.size(); i++){
				list[i]=tmp.get(i).get("id")+","+tmp.get(i).get("name");
			}			
			return SUCCESS;
		}
		
	}
}