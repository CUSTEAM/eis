package ajax;
 
import java.util.List;
import java.util.Map;

import action.BaseAction;

/**
 * 自動完成各項代碼 * 
 * <p>idCol ID欄位
 * nameCol NAME欄位
 * table 資料表
 * value 查詢值
 * 一般範例 /eis/autoCompAnyCode?idCol=id&nameCol=name&table=CODE_UNIT&value=電
 * bootstrap範例 /eis/autoCompAnyCode?bootstrap=1&idCol=id&nameCol=name&table=CODE_UNIT&value=電</p>
 * @author shawn
 *
 */
public class autoCompRcAio extends BaseAction{
	
	private Object list[];

	public Object[] getList() {
		return list;
	}

	public void setList(Object[] list) {
		this.list = list;
	}

	public String execute(){
		
		
		StringBuilder sql=new StringBuilder("SELECT r.Oid, e.cname, c.name as typeName, r.Rc_name, r.school_year FROM Rc_aio r LEFT OUTER JOIN empl e ON r.idno=e.idno, CODE_RC_TABLE c "
		+ "WHERE r.Rc_table=c.id AND(e.cname LIKE'%"+request.getParameter("query")+"%'OR r.Oid='"+request.getParameter("query")+"'OR r.Rc_name LIKE'%"+request.getParameter("query")+"%')");
		if(request.getParameter("year")!=null){
			sql.append("AND r.school_year>("+getContext().getAttribute("school_year")+"-"+request.getParameter("year")+")");
		}
		sql.append("ORDER BY r.school_year DESC LIMIT 50");
		//System.out.println(sql);
		
		List<Map>tmp=df.sqlGet(sql.toString());
		//TODO 加入離職教職員後奇慢無比
		/*sql=new StringBuilder("SELECT r.Oid, e.cname, c.name as typeName, r.Rc_name, r.school_year FROM Rc_aio r LEFT OUTER JOIN dempl e ON r.idno=e.idno, CODE_RC_TABLE c "
				+ "WHERE r.Rc_table=c.id AND(e.cname LIKE'%"+request.getParameter("query")+"%'OR r.Oid='"+request.getParameter("query")+"'OR r.Rc_name LIKE'%"+request.getParameter("query")+"%')");
				if(request.getParameter("year")!=null){
					sql.append("AND r.school_year>("+getContext().getAttribute("school_year")+"-"+request.getParameter("year")+")");
				}
				sql.append("ORDER BY r.school_year DESC LIMIT 50");
		tmp.addAll(df.sqlGet(sql.toString()));*/
		
		
		list=new Object[tmp.size()];
		for(int i=0; i<tmp.size(); i++){
			list[i]=tmp.get(i).get("Oid")+","+tmp.get(i).get("cname")+","+tmp.get(i).get("school_year")+"年,"+tmp.get(i).get("typeName")+","+tmp.get(i).get("Rc_name");		
		}
		
		return SUCCESS;
	}
}