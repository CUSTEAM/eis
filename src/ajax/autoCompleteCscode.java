package ajax;
 
import java.util.List;
import java.util.Map;
import action.BaseAction;

/**
 * 自動完成Cscode
 * <p>以cscode為關鍵對應科目代碼</p>
 * @author John
 * @category TODO 檢查是否可整合至AutoCompAnyCode中
 */
public class autoCompleteCscode extends BaseAction{
	
	private Object list[];

	public Object[] getList() {
		return list;
	}

	public void setList(Object[] list) {
		this.list = list;
	}

	public String execute(){
		List<Map>tmp=df.sqlGet("SELECT cscode, chi_name FROM Csno WHERE cscode LIKE'"+request.getParameter("nameno")+"%' OR chi_name LIKE'"+request.getParameter("nameno")+"%'");
		list=new Object[tmp.size()];
		for(int i=0; i<tmp.size(); i++){
			list[i]=tmp.get(i).get("cscode")+","+tmp.get(i).get("chi_name");
		}
		return SUCCESS;
	}
}