package ajax.empl;
 
import java.util.Map;
import action.BaseAction;
 
/**
 * 依學號取導師
 * @author John
 */
public class getTutor extends BaseAction{
	
	private Map tutor;
	
	public Map getTutor() {
		return tutor;
	}

	public void setTutor(Map tutor) {
		this.tutor = tutor;
	}

	public String execute() {
		if(request.getParameter("stdNo")!=null){
			setTutor(df.sqlGetMap("SELECT e.cname, e.CellPhone, " +
			"e.Email, cd.name as deptName,cc.name as schoolName,cc.address, cd.location, " +
			"cd.phone,cd.fax FROM empl e, Class c, CODE_DEPT cd, CODE_CAMPUS cc " +
			"WHERE cc.id=c.CampusNo AND cd.id=c.DeptNo AND e.idno=c.tutor AND " +
			"c.ClassNo='"+df.sqlGetStr("SELECT depart_class FROM stmd WHERE student_no='"+
			request.getParameter("stdNo")+"'")+"'"));
		}		
        return SUCCESS;
    } 
}