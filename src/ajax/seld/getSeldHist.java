package ajax.seld;
 
import java.util.List;

//import org.apache.struts2.json.annotations.JSON;

import action.BaseAction;

/**
 * 取得學生加退選歷史
 * @author John
 */
public class getSeldHist extends BaseAction{
	
	private List list;
	
	//@JSON(format="yyyy-MM-dd HH:mm:ss")
	//@JSON(serialize=false)
	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
	
	//@JSON(format="yyyy-MM-dd HH:mm:ss")
	//@JSON(serialize=false)
	public String execute() {	
		setList(df.sqlGet("SELECT c.ClassName,cs.chi_name,IFNULL(e.cname, '')as cname,s.edate,s.type FROM SeldHist s " +
		"LEFT OUTER JOIN empl e ON s.idno=e.idno, Class c, Csno cs WHERE " +
		"s.cscode=cs.cscode AND s.depart_class=c.ClassNo AND s.studentNo='"+request.getParameter("stdNo")+"'ORDER BY s.edate DESC"));		
        return SUCCESS;               
    }
	
}