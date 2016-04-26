package ajax.dilg;

import java.util.Map;
import action.BaseAction;

/**
 * 教師重大集會點名
 * @author John
 */
public class editImpDilg extends BaseAction{
	
	public int total=0;
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String execute() {
		String no=request.getParameter("no");
		String cls=request.getParameter("cls");
		String Oid=request.getParameter("Oid");		
		
		
		df.exSql("DELETE FROM Dilg_imp WHERE student_no='"+no+"'AND Dilg_imp_date_oid="+Oid);
		if(!cls.equals(""))df.exSql("INSERT INTO Dilg_imp(student_no, Dilg_imp_date_oid, cls)VALUES('"+no+"', "+Oid+", "+cls+");");
		//setTotal(df.sqlGetInt("SELECT COUNT(*)FROM Dilg d WHERE d.student_no='"+no+"' AND d.abs IN(SELECT id FROM Dilg_rules WHERE exam='1') AND d.Dtime_oid="+Oid));
		return SUCCESS;
    } 
}