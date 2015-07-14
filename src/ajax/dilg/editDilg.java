package ajax.dilg;

import java.util.Map;
import action.BaseAction;

/**
 * 教師點名
 * @author John
 */
public class editDilg extends BaseAction{
	
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
		String abs=request.getParameter("abs");
		String date=request.getParameter("date");
		String Oid=request.getParameter("Oid");		
		
		Map map=df.sqlGetMap("SELECT * FROM Dilg WHERE student_no='"+no+"' AND date='"+date+"' AND cls='"+cls+"'");
		
		//初次點名-
		if(map==null){
			//耍白痴
			if(abs.equals("")||abs.equals("6")){
				setTotal(df.sqlGetInt("SELECT COUNT(*)FROM Dilg d WHERE d.student_no='"+no+"' AND d.abs IN(SELECT id FROM Dilg_rules WHERE exam='1') AND d.Dtime_oid="+Oid));
				return SUCCESS;
			}
			df.exSql("INSERT INTO Dilg(student_no, date, cls, abs, Dtime_oid)VALUE('"+no+"', '"+date+"', '"+cls+"', '"+abs+"', '"+Oid+"')");
			
		//點名修正	
		}else{			
			//刪除缺課
			if(abs.equals("")){
				df.exSql("DELETE FROM Dilg WHERE student_no='"+no+"' AND date='"+date+"' AND cls='"+cls+"'");				
			}else{
			//更新缺課
				df.exSql("UPDATE Dilg SET abs='"+abs+"' WHERE student_no='"+no+"' AND date='"+date+"' AND cls='"+cls+"'");
			}
		}
		
		setTotal(df.sqlGetInt("SELECT COUNT(*)FROM Dilg d WHERE d.student_no='"+no+"' AND d.abs IN(SELECT id FROM Dilg_rules WHERE exam='1') AND d.Dtime_oid="+Oid));
		return SUCCESS;
    } 
}