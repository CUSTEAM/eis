package ajax.student;
 
import java.util.List;
import java.util.Map;
import service.impl.StudAffairManager;
import action.BaseAction;
 
/**
 * 學生缺曠圖表資料
 * @author John
 */
public class getStdDilgChart extends BaseAction{
	
	private Object cData[][];
	private Object dData[][];
	

	public Object[][] getcData() {
		return cData;
	}

	public void setcData(Object[][] cData) {
		this.cData = cData;
	}

	public Object[][] getdData() {
		return dData;
	}

	public void setdData(Object[][] dData) {
		this.dData = dData;
	}

	public String execute() {
		StudAffairManager sam= (StudAffairManager) get("StudAffairManager");
		Map m=sam.StudentDilg(request.getParameter("stdNo"));

		cData=new Object[9][2];
		
		cData[0][0]="住院";
		cData[0][1]=m.get("abs1");
		
		cData[1][0]="曠課";
		cData[1][1]=m.get("abs2");
		
		cData[2][0]="病假";
		cData[2][1]=m.get("abs3");
		
		cData[3][0]="事假";
		cData[3][1]=m.get("abs4");
		
		cData[4][0]="遲到";
		cData[4][1]=m.get("abs5");
		
		cData[5][0]="公假";
		cData[5][1]=m.get("abs6");
		
		cData[6][0]="喪假";
		cData[6][1]=m.get("abs7");
		
		cData[7][0]="婚假";
		cData[7][1]=m.get("abs8");
		
		cData[8][0]="產假";
		cData[8][1]=m.get("abs9");
		m=null;
		
		List<Map>list=df.sqlGet("SELECT c.chi_name,(SELECT COUNT(*)FROM Dilg WHERE " +
		"student_no=s.student_no AND Dtime_oid=d.Oid AND abs!='5')as cnt " +
		"FROM stmd st, Seld s, Dtime d, Csno c WHERE d.Sterm='"+getContext().getAttribute("school_term")+"' " +
		"AND st.student_no='"+request.getParameter("stdNo")+"' AND c.cscode=d.cscode AND st.student_no=s.student_no AND d.Oid=s.Dtime_oid");
		dData=new Object[list.size()+1][2];
		dData[0][0]="課程";
		dData[0][1]="缺席";
		for(int i=0; i<list.size(); i++){
			dData[i+1][0]=list.get(i).get("chi_name");
			dData[i+1][1]=list.get(i).get("cnt");
		}		
        return SUCCESS;               
	} 
}