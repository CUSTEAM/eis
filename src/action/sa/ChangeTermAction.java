package action.sa;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Message;
import action.BaseAction;

/**
 * 學期轉換
 * @author shawn
 * @category TODO: 下次實做前再測試
 */
public class ChangeTermAction extends BaseAction{
	
	static Calendar now=Calendar.getInstance();
	
	/**
	 * 計算各年級學生量
	 * @return
	 */
	private String dataCnt(){		
		Map m=df.sqlGetMap("SELECT "
		+ "COUNT(CASE WHEN Grade =1 THEN 1 ELSE NULL END )as g1,"
		+ "COUNT(CASE WHEN Grade =2 THEN 1 ELSE NULL END )as g2,"
		+ "COUNT(CASE WHEN Grade =3 THEN 1 ELSE NULL END )as g3,"
		+ "COUNT(CASE WHEN Grade =4 THEN 1 ELSE NULL END )as g4,"
		+ "COUNT(CASE WHEN Grade =5 THEN 1 ELSE NULL END )as g5 "
		+ "FROM Class c, stmd s WHERE c.ClassNo=s.depart_class");		
		return "1年級"+m.get("g1")+"人<br>2年級"+m.get("g2")+"人<br>3年級"+m.get("g3")+"人<br>4年級"+m.get("g4")+"人<br>5年級"+m.get("g5")+"人<br>";
	}

	public String execute(){		
		
		//重用table列表
		request.setAttribute("reuse", listReuseTable());
		
		//檢查本年度作業
		List<Map>stor=df.sqlGet("SELECT * FROM SYS_REUSE_STOR");
		for(int i=0; i<stor.size();i++){
			stor.get(i).put("cnt", df.sqlGetInt("SELECT COUNT(*)FROM "+stor.get(i).get("table_name")+" WHERE school_year='"+getContext().getAttribute("school_year")+"' AND school_term='"+getContext().getAttribute("school_term")+"'"));
		}
		request.setAttribute("stor", stor);		
		return SUCCESS;
	}
	
	/**
	 * 轉換
	 * @return
	 * @throws ParseException 
	 */
	public String termChange() throws ParseException{
		String year=getContext().getAttribute("school_year").toString();
		String term=getContext().getAttribute("school_term").toString();
		Message msg=new Message();
		msg.setMsg("學期轉換工作報告<br>");
	
		//期間測試
		if(!checkNow(Integer.parseInt(year))){
			msg.setError("非轉換期間");
			this.savMessage(msg);
			return execute();
		}
		
		//學期時間範圍
		if(begin.equals("")||end.equals("")){
			msg.setError("未設定開學期間");
			this.savMessage(msg);
			return execute();
		}		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm");		
		if(sf.parse(getContext().getAttribute("school_term_begin").toString()).getTime()>=
		sf.parse(begin).getTime()||
		sf.parse(begin).getTime()>sf.parse(end).getTime()){
			msg.setError("開學期間設定有誤");
			this.savMessage(msg);
			return execute();
		}
		
		try{			
			//備份與清除備份
			msg.addMsg(duplicateTable());//備份資料
			msg.addMsg(saveDtime(year, term));//課程轉歷年
			msg.addMsg(clearColumn(term));//清除非重用欄位
			msg.addMsg("<br>資料清除工作<br>");
			msg.addMsg(clearReuseTable());//清重用資料表
		}catch(Exception e){
			msg.setMsg("執行失敗，請檢查是否重複執行<br>"+e);
			this.savMessage(msg);
			return execute();
		}
		
		//第2學期升級
		if(term.equals("2")){
			msg.addMsg("<br>升級工作<br>");
			List<Map>cls=getCls();//原始班級列表		
			msg.addMsg("轉換前<br>"+dataCnt());//點人數
			
			//升級班存在測試
			List<Map>cc=checkClass(cls);
			if(cc.size()>0){
				msg.addMsg("<br>建立新班級提供升級<br>");
				for(int i=0; i<cc.size(); i++){
					msg.addMsg(cc.get(i).get("ClassName")+","+cc.get(i).get("cnt")+"人<br>");
				}
				//return execute();
				msg.addMsg("請至班級管理設定細節<br>");
				cls=getCls();//重新取得新建班級列表
			}
			
			//升級
			msg.addMsg(upGrade(cls));		
			msg.addMsg("轉換後<br>"+dataCnt());//點人數
			
			year=String.valueOf(Integer.parseInt(year)+1);
			term="1";
			df.exSql("UPDATE Parameter SET Value='"+term+"' WHERE Name='School_term'");//學年
			df.exSql("UPDATE Parameter SET Value='"+year+"' WHERE Name='School_year'");//學期
			msg.addMsg(year+"學年第"+term+"學期轉換完成<br>系統於15分鐘內生效");
			
		}else{//第1學期沒事
			term="2";
			df.exSql("UPDATE Parameter SET Value='"+term+"' WHERE Name='School_term'");//學年
			df.exSql("UPDATE Parameter SET Value='"+year+"' WHERE Name='School_year'");//學期
			msg.addMsg(year+"學年第"+term+"學期轉換完成<br>系統於15分鐘內生效");
		}
		
		//學期上課時間
		df.exSql("UPDATE SYS_CALENDAR SET cdate='"+begin+"' WHERE name='school_term_begin'");
		df.exSql("UPDATE SYS_CALENDAR SET cdate='"+end+"' WHERE name='school_term_end'");
		
		this.savMessage(msg);
		df.exSql("INSERT INTO SYS_SCHEDULE_LOG(note,subject)VALUES('"+msg.getMsg()+"','學期轉換工作報告');");
		return execute();
	}
	
	/**
	 * 課程轉歷年
	 * 備份至Savedtime
	 * 清除相關欄位
	 * @return 處理結果
	 */
	private String saveDtime(String year, String term){		
		//刪除已存在
		df.exSql("DELETE FROM Savedtime WHERE school_year='"+year+"' AND school_term='"+term+"'");
		//轉開課資料
		df.exSql("INSERT INTO Savedtime(school_year,school_term,depart_class,cscode,techid,"
		+ "opt,credit,thour,stu_select,avg,samples,Introduction,Syllabi,Syllabi_sub,effsamples)"
		+ "SELECT'"+year+"','"+term+"',depart_class,cscode,techid,opt,credit,thour,stu_select,(coansw*20),"
		+ "samples,Introduction,Syllabi,Syllabi_sub,effsamples FROM Dtime WHERE Sterm='"+term+"'");		
		return "Savedtime匯入"+year+"學年第"+term+"學期資料<br>";
	}
	
	/**
	 * 清除Dtime欄位
	 * 授課教師、排課、問卷
	 * @return
	 */
	private String clearColumn(String term){
		//清除Dtime
		df.exSql("UPDATE Dtime SET techid=null,samples=0,effsamples=0 WHERE Sterm='"+term+"'");	
		//清除Dtime_class
		df.exSql("DELETE FROM Dtime_class WHERE Dtime_oid IN(SELECT Oid FROM Dtime WHERE Sterm='"+term+"')");
		df.exSql("DELETE FROM Seld WHERE Dtime_oid IN(SELECT Oid FROM Dtime WHERE Sterm='"+term+"')");
		return "清除第"+term+"學期教師/排課/問卷/選課<br>";
	}
	
	/**
	 * 取得重用資料表
	 * @return
	 */
	private List listReuseTable(){
		List<Map>ts=df.sqlGet("SELECT * FROM SYS_REUSE_TABLE ORDER BY table_name");
		for(int i=0; i<ts.size(); i++){
			ts.get(i).put("cnt", df.sqlGetStr("SELECT COUNT(*)FROM "+ts.get(i).get("table_name")));
		}
		return ts;
	}
	
	/**
	 * 清除重用資料表
	 * @return
	 */
	private String clearReuseTable(){
		List<Map>list=listReuseTable();
		StringBuilder sb=new StringBuilder();
		for(int i=0; i<list.size(); i++){
			if(list.get(i).get("auto").toString().equals("1"))
			df.exSql("DELETE FROM "+list.get(i).get("table_name"));
			sb.append(list.get(i).get("table_name")+"已清除<br>");
		}
		
		return sb.toString();
	}
	
	/**
	 * 確認班級可供升級
	 * 建立無法升上的班級
	 * @param cls
	 * @return 自動建班列表
	 */
	private List checkClass(List<Map>cls){		
		
		List list=new ArrayList();
		Map m=new HashMap();
		
		boolean b;
		for(int i=0; i<cls.size(); i++){
			if(!upCheck(cls.get(i)))continue;
			
			b=false;//預設無班可升
			m.putAll(cls.get(i));//載入暫存班級
			m.put("Grade", String.valueOf(  Integer.parseInt(cls.get(i).get("Grade").toString())+1));//暫存班升級			
			if(m.get("graduate").toString().equals("1"))m.put("SeqNo", "1");//若為畢業班升延修班強置轉為甲班
			
			for(int j=0; j<cls.size(); j++){					
				if(
					cls.get(j).get("CampusNo").toString().equals(m.get("CampusNo").toString())&&
					cls.get(j).get("SchoolNo").toString().equals(m.get("SchoolNo").toString())&&
					cls.get(j).get("DeptNo").toString().equals(m.get("DeptNo").toString())&&
					cls.get(j).get("Grade").toString().equals(m.get("Grade").toString())&&
					cls.get(j).get("SeqNo").toString().equals(m.get("SeqNo").toString())
				){						
					b=true;//有班可升
					break;
				}
			}	
			
			if(!b){//班級迴圈測完無班可升
				//TODO 無法準確建立各項欄位,需手動設定細節欄位
				createClass(m);//建立班級
				list.add(cls.get(i));//載入自動建班警告
			}				
		}
		return list;//回傳自動建班列表
	}
	
	/**
	 * 建立班級
	 * 根據升級前班級資訊推估班級代碼
	 * @param m
	 */
	private void createClass(Map<String,String>m){	
		String ClassNo=String.valueOf(m.get("CampusNo")+m.get("SchoolNo")+m.get("DeptNo")+m.get("Grade")+m.get("SeqNo"));
		df.exSql("INSERT INTO Class(ClassNo,ClassName,CampusNo,"+ "SchoolNo,SchoolType,DeptNo,Grade,SeqNo,Type,ShortName,"
		+ "Dept)VALUES("+ "'"+ClassNo+"','"+ClassNo+"','"+m.get("CampusNo")+"',"
		+ "'"+m.get("SchoolNo")+"','"+m.get("SchoolType")+"','"+m.get("DeptNo")+"','"+m.get("Grade")+"','"+m.get("SeqNo")
		+"','"+m.get("Type")+"',ShortName,"+ "'"+m.get("Dept")+"');");
	}
	
	/**
	 * 以年級反向排序取班級與人數
	 * @return 班級列表
	 */
	private List getCls(){
		return df.sqlGet("SELECT c.*, (SELECT COUNT(*)FROM stmd WHERE depart_class=c.ClassNo)as cnt FROM Class c ORDER BY c.Grade DESC");
	}
	
	/**
	 * 檢查是否需要升級
	 * 條件:人數為0, 延修班, 年級為0
	 * @param m
	 * @return 是否需要
	 */
	private boolean upCheck(Map m){
		if(Integer.parseInt(m.get("cnt").toString())<1)return false;//無人班跳過
		if(m.get("Type").toString().equals("E"))return false;//延修班不測試是否有班級可升級
		if(m.get("Grade").toString().equals("0"))return false;//推廣與跨校不測試是否有班級可升級
		return true;
	}
	
	/**
	 * 升級
	 * 備份學生資料為stmd+今天日期
	 * 當今天已進行則出錯離開,除非有經過手動還原
	 * @return
	 */
	private String upGrade(List<Map>cls){
		
		StringBuilder msg=new StringBuilder();
		String tmp_cls;
		Map m=new HashMap();
		boolean b;
			
		for(int i=0; i<cls.size(); i++){			
			if(!upCheck(cls.get(i)))continue;
			
			b=false;//無班指標
			m.putAll(cls.get(i));//載入暫存班
			m.put("Grade", String.valueOf(Integer.parseInt(cls.get(i).get("Grade").toString())+1));//暫存班升級
			if(m.get("graduate").toString().equals("1"))m.put("SeqNo", "1");//畢業班升延修班一律為甲班
			
			for(int j=0; j<cls.size(); j++){					
				if(
					cls.get(j).get("CampusNo").toString().equals(m.get("CampusNo").toString())&&
					cls.get(j).get("SchoolNo").toString().equals(m.get("SchoolNo").toString())&&
					cls.get(j).get("DeptNo").toString().equals(m.get("DeptNo").toString())&&
					cls.get(j).get("Grade").toString().equals(m.get("Grade").toString())&&
					cls.get(j).get("SeqNo").toString().equals(m.get("SeqNo").toString())
				){						
					b=true;
					
					tmp_cls=df.sqlGetStr("SELECT ClassNo FROM Class WHERE CampusNo='"+
					m.get("CampusNo")+"'AND SchoolNo='"+m.get("SchoolNo")+"'AND DeptNo='"
					+m.get("DeptNo")+"'AND Grade='"+m.get("Grade")+"'AND SeqNo='"+m.get("SeqNo")+"'");
					
					df.exSql("UPDATE stmd SET depart_class='"+tmp_cls+"'WHERE depart_class='"+cls.get(i).get("ClassNo")+"'");
					break;
				}
			}
			
			if(!b){
				msg.append(m.get("ClassNo")+
				":"+df.sqlGetInt("SELECT COUNT(*)FROM stmd WHERE depart_class='"+
				m.get("ClassNo")+"'")+"人無班可升<br>");
			}			
		}		
		return msg.toString();
	}
	
	/**
	 * 偵測執行時機
	 * 第1學期僅可在1,2月
	 * 第2學期僅可在7,8月
	 * @return 是否執行
	 */
	private boolean checkNow(int term){
		if(term==1){//第1學期
			if(now.get(Calendar.MONTH)==1||now.get(Calendar.MONTH)==0)return true;//2月或1月			
		}else{//第2學期
			if(now.get(Calendar.MONTH)==6||now.get(Calendar.MONTH)==7)return true;//7月或8月
		}		
		return false;//其餘不可執行
	}
	
	/**
	 * 備份資料表
	 */
	private String duplicateTable(){		
		//備份Class
		df.exSql("DROP TABLE IF EXISTS Class"+bName+";");
		df.exSql("CREATE TABLE Class"+bName+" LIKE Class;");
		df.exSql("INSERT Class"+bName+" SELECT * FROM Class;");		
		//備份Dtime
		df.exSql("DROP TABLE IF EXISTS Dtime"+bName+";");
		df.exSql("CREATE TABLE Dtime"+bName+" LIKE Dtime;");
		df.exSql("INSERT Dtime"+bName+" SELECT * FROM Dtime;");	
		//備份stmd
		df.exSql("DROP TABLE IF EXISTS stmd"+bName+";");
		df.exSql("CREATE TABLE stmd"+bName+" LIKE stmd;");
		df.exSql("INSERT stmd"+bName+" SELECT * FROM stmd;");
		//備份Seld
		df.exSql("DROP TABLE IF EXISTS Seld"+bName+";");
		df.exSql("CREATE TABLE Seld"+bName+" LIKE Seld;");
		df.exSql("INSERT Seld"+bName+" SELECT * FROM Seld;");
		//備份Dtime_class
		df.exSql("DROP TABLE IF EXISTS Dtime_class"+bName+";");
		df.exSql("CREATE TABLE Dtime_class"+bName+" LIKE Dtime;");
		df.exSql("INSERT Dtime_class"+bName+" SELECT * FROM Dtime;");
		return "備份資料表名稱結尾+"+bName+"<br>";
	}
	
	/**
	 * 手動清除資料表
	 * @return
	 */
	public String manuClear(){
		Message msg=new Message();
		for(int i=0; i<ctable.length; i++){
			if(!ctable[i].equals("")){
				df.exSql("DELETE FROM "+ctable[i]);
				msg.setSuccess(ctable[i]+"刪除完成");
				this.savMessage(msg);
				return execute();
			}
		}		
		return execute();
	}	
	
	public String ctable[], begin, end;
	String bName="_AUTOBACKUP";

}
