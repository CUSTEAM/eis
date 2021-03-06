package ajax;
 
import java.util.List;
import java.util.Map;
import action.BaseAction;

/**
 * 自動完成empl
 * <p>以Oid為關鍵對應教職員</p>
 * @author John
 * @category TODO 檢查是否可整合至AutoCompAnyCode中
 */
public class autoCompleteEmplOid extends BaseAction{
	
	private Object list[];

	public Object[] getList() {
		return list;
	}

	public void setList(Object[] list) {
		this.list = list;
	}

	public String execute(){		
		List<Map>tmp=df.sqlGet("SELECT e.cname, e.Oid as idno, IFNULL(d.name,'')as edunit, IFNULL(u.name,'')as unit FROM " +
		"(empl e LEFT OUTER JOIN CODE_DEPT d ON e.unit=d.id)LEFT OUTER JOIN CODE_UNIT u ON e.unit_module=u.id " +
		"WHERE (e.cname LIKE '"+request.getParameter("nameno")+"%')");
		list=new Object[tmp.size()];
		for(int i=0; i<tmp.size(); i++){
			list[i]=tmp.get(i).get("idno")+","+tmp.get(i).get("cname")+" - "+tmp.get(i).get("edunit")+", "+tmp.get(i).get("unit");
		}
		return SUCCESS;
	}
}