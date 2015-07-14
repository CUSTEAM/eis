package ajax.student;
 
import java.util.List;
import java.util.Map;
import action.BaseAction;

/**
 * 學生成績圖表資料
 * @author John
 */
public class getStdScoreChart extends BaseAction{
	
	private Object cData[][];
	private Object bData[][];

	public Object[][] getbData() {
		return bData;
	}

	public void setbData(Object[][] bData) {
		this.bData = bData;
	}

	public Object[][] getcData() {
		return cData;
	}

	public void setcData(Object[][] cData) {
		this.cData = cData;
	}

	public String execute() {
		
		cData=new Object[6][2];
		
		cData[0][0]="範圍";
		cData[0][1]="數量";
		
		cData[1][0]="90+";
		cData[1][1]=df.sqlGetInt("SELECT COUNT(*)FROM ScoreHist WHERE student_no='"+request.getParameter("stdNo")+"' AND score>=90");
		
		cData[2][0]="80-89";
		cData[2][1]=df.sqlGetInt("SELECT COUNT(*)FROM ScoreHist WHERE student_no='"+request.getParameter("stdNo")+"' AND score>=80 AND score<90");
		
		cData[3][0]="70-79";
		cData[3][1]=df.sqlGetInt("SELECT COUNT(*)FROM ScoreHist WHERE student_no='"+request.getParameter("stdNo")+"' AND score>=70 AND score<80");
		
		cData[4][0]="60-69";
		cData[4][1]=df.sqlGetInt("SELECT COUNT(*)FROM ScoreHist WHERE student_no='"+request.getParameter("stdNo")+"' AND score>=60 AND score<70");
		
		cData[5][0]="60-69";
		cData[5][1]=df.sqlGetInt("SELECT COUNT(*)FROM ScoreHist WHERE student_no='"+request.getParameter("stdNo")+"' AND score<60");
		
		List<Map>list=df.sqlGet("SELECT s.school_year, s.school_term, IFNULL(s.rank, 0)as rank, ROUND(s.score,2)as score FROM Stavg s " +
		"WHERE student_no='"+request.getParameter("stdNo")+"'");
		bData=new Object[list.size()+1][3];
		
		bData[0][0]="學年學期";
		bData[0][1]="平均分數";
		bData[0][2]="全班排名";
		for(int i=0; i<list.size(); i++){
			bData[i+1][0]=list.get(i).get("school_year")+"-"+list.get(i).get("school_term");
			bData[i+1][1]=list.get(i).get("score");
			bData[i+1][2]=list.get(i).get("rank");
		}
		return SUCCESS;               
	} 
}