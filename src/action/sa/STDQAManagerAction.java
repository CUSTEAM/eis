package action.sa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import action.BaseAction;
import model.Message;
import model.QUEST_QUE;

public class STDQAManagerAction extends BaseAction{
	
	public String cno, stno, sno, cono, dno, gno, zno;
	public String Oid, title, beginDate, enDate;
	public File upload;
	
	/**
	 * excel欄位強制轉文字 2003+
	 * @param cell
	 * @return
	 */
	private String readCellAsString(XSSFCell cell) {
		if (cell == null) {
			return null;
		}
		final DecimalFormat df = new DecimalFormat("####################0.##########");

		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_BLANK:
			return "";
		case HSSFCell.CELL_TYPE_BOOLEAN:
			return Boolean.valueOf(cell.getBooleanCellValue()).toString().trim();
		case HSSFCell.CELL_TYPE_NUMERIC:
			return df.format(cell.getNumericCellValue()).trim();
		case HSSFCell.CELL_TYPE_STRING:
			return cell.getStringCellValue().trim();
		case HSSFCell.CELL_TYPE_FORMULA:
			return cell.getCellFormula().trim();
		case HSSFCell.CELL_TYPE_ERROR:
			return Byte.toString(cell.getErrorCellValue());
		default:
			return "##POI## Unknown cell type";
		}
	}
	
	public String upload() throws IOException{
		Message msg=new Message();
		if(upload==null){			
			msg.setError("未指定檔案");
			this.savMessage(msg);
			return SUCCESS;
		}
		
		FileInputStream fis = new FileInputStream(upload);
		XSSFSheet sheet;
		try{
			XSSFWorkbook xwb = new XSSFWorkbook(fis);
			sheet = xwb.getSheetAt(0);
		}catch(Exception e){
			msg.setError("請另存為xlsx檔案重新上傳");
			this.savMessage(msg);
			return SUCCESS;
		}
		
		XSSFRow row;	
		
		int check=0;
		//List<String>opts;
		df.exSql("DELETE FROM QUEST_RES WHERE Qid="+Oid);
		df.exSql("DELETE FROM QUEST_QUE WHERE Qid="+Oid);
		df.exSql("DELETE FROM QUEST_OPT WHERE Qid="+Oid);
		for (int i = sheet.getFirstRowNum()+1; i < sheet.getPhysicalNumberOfRows(); i++) {				
			row = sheet.getRow(i);
			//opts=new ArrayList();			
			try{
				System.out.println(readCellAsString(row.getCell(0))+", "+readCellAsString(row.getCell(1)));
				//df.exSql("INSERT INTO QUEST_QUE(Qid, valu)VALUES();");
				QUEST_QUE q=new QUEST_QUE();
				q.setQid(Integer.parseInt(Oid));
				q.setCategory(readCellAsString(row.getCell(0)));
				q.setValue(readCellAsString(row.getCell(1)));
				df.update(q);
				for(int j=2; j<999; j++){					
					if(readCellAsString(row.getCell(j))==null)break;
					System.out.print(readCellAsString(row.getCell(j))+",");
					df.exSql("INSERT INTO QUEST_OPT(Qid, value)VALUES("+q.getOid()+", '"+readCellAsString(row.getCell(j))+"');");
				}
				
				
				
				
				System.out.println();
				/*
				opts.add(row.getCell(0));
				
				y=readCellAsString(row.getCell(0));
				t=readCellAsString(row.getCell(1));
				k=readCellAsString(row.getCell(2));
				s=readCellAsString(row.getCell(3));
				o=readCellAsString(row.getCell(4));
				a=readCellAsString(row.getCell(5));
				m=readCellAsString(row.getCell(6));	
				mm=readCellAsString(row.getCell(7));
				*/				
				check+=1;
			
			
			
			}catch(Exception e){
				e.printStackTrace();
				//msg.addError("第"+(i+1)+"行在儲存中發生錯誤");
				continue;
			}										
						
		}
		
		fis.close();
		//request.setAttribute("complete", complete);
		//request.setAttribute("fail", fail);
		msg.setSuccess("文件匯入完成, 共"+check+"筆資料");
		this.savMessage(msg);
		return edit();
	}
	
	public String execute(){
		
		
		return SUCCESS;
	}
	
	public String search(){
		StringBuilder sql=new StringBuilder("SELECT e.cname, q.*,(SELECT COUNT(*)FROM QUEST_RES WHERE Qid=q.Oid)as cnt,"
				+ "(SELECT COUNT(*)FROM QUEST_RES WHERE Qid=q.Oid AND reply IS NOT NULL)as rel, (SELECT COUNT(*)FROM QUEST_QUE WHERE Qid=q.Oid)as qs "
				+ "FROM QUEST q LEFT OUTER JOIN empl e ON e.idno=q.owner WHERE q.Oid IS NOT NULL ");
		if(!title.equals(""))sql.append("AND q.title='"+title+"'");
		if(!beginDate.equals(""))sql.append("AND q.beginDate>='"+beginDate+"'");
		if(!enDate.equals(""))sql.append("AND q.enDate<='"+enDate+"'");
		
		request.setAttribute("quests", df.sqlGet(sql.toString()));
		
		return SUCCESS;
	}
	
	public String preview() throws IOException{
		
		Date date=new Date();
		response.setContentType("application/vnd.ms-word; charset=UTF-8");
		response.setHeader("Content-disposition","attachment;filename="+date.getTime()+".doc");				
		PrintWriter out=response.getWriter();	
		
		out.println("<html>");
		out.println("<head>");
		out.println("<meta content='text/html; charset=UTF-8' http-equiv='Content-Type'/>");
		out.println("<style>td{border:1px solid;}</style>");
		out.println("</head>");
		out.println("<body>");
		
		List<Map>quests=df.sqlGet("SELECT * FROM QUEST_QUE WHERE Qid="+request.getParameter("Oid")+" ORDER BY Oid");
		
		Map quest=df.sqlGetMap("SELECT q.*, e.cname FROM QUEST q LEFT OUTER JOIN empl e ON e.idno=q.owner");
		List<Map>opt;		
		out.println("<h1>"+quest.get("title")+"</h1>");		
		
		for(int i=0; i<quests.size(); i++){
			
			out.println((i+1)+". "+quests.get(i).get("value"));
			if(quests.get(i).get("category").toString().equals("A"))out.println("(單選題)<br>");
			if(quests.get(i).get("category").toString().equals("B"))out.println("(多選題)<br>");
			if(quests.get(i).get("category").toString().equals("C"))out.println("(申論題)<br>");
				
			opt=df.sqlGet("SELECT * FROM QUEST_OPT WHERE Qid="+quests.get(i).get("Oid")+" ORDER BY Oid");
			for(int j=0; j<opt.size(); j++){
				out.println("____"+(j+1)+". "+opt.get(j).get("value")+"<br>");
			}
			
			out.println("<br>");
		}
		
		out.println("</body>");
		out.println("</html>");
		
		out.close();
		out.flush();
		return null;
	}
	
	public String create(){
		Message msg=new Message();
		Date now=new Date();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		try {
			if(sf.parse(beginDate).getTime()<=now.getTime()){				
				msg.setError("不可建立當日立即生效問卷");
				this.savMessage(msg);
				return SUCCESS;
			}
			if(sf.parse(enDate).getTime()<=sf.parse(beginDate).getTime()){				
				msg.setError("日期矛盾");
				this.savMessage(msg);
				return SUCCESS;
			}
		} catch (ParseException e1) {
			msg.setError("標題、時間範圍不可空白");
			this.savMessage(msg);
			return SUCCESS;
		}
		
		try{
			df.exSql("INSERT INTO QUEST(title, beginDate, enDate, owner)VALUES('"+title+"','"+beginDate+"','"+enDate+"', '"+getSession().getAttribute("userid")+"');");
		}catch(Exception e){
			msg.setError("標題、時間範圍資料輸入有誤, 或重複建立");
			this.savMessage(msg);
			return SUCCESS;
		}
		msg.setMsg("新增完成！請點選編輯上傳題目並設定學生範圍。");
		this.savMessage(msg);
		return search();
	}
	
	public String edit(){
		
		Map quest;
		if(Oid==null){
			quest=df.sqlGetMap("SELECT * FROM QUEST WHERE Oid="+request.getParameter("Oid"));
		}else{
			quest=df.sqlGetMap("SELECT * FROM QUEST WHERE Oid="+Oid);
		}
		
		cno=String.valueOf(quest.get("cno"));
		stno=String.valueOf(quest.get("stno"));
		sno=String.valueOf(quest.get("sno"));
		cono=String.valueOf(quest.get("cono"));
		dno=String.valueOf(quest.get("dno"));
		if(quest.get("gno")!=null)gno=quest.get("gno").toString();
		System.out.println(gno);
		request.setAttribute("quest", quest);
		
		
		return SUCCESS;
	}
	
	public String delete(){		
		df.exSql("DELETE FROM QUEST WHERE Oid="+Oid);//刪問卷
		df.exSql("DELETE FROM QUEST_QUE WHERE Qid="+Oid);//刪問題
		df.exSql("DELETE FROM QUEST_OPT WHERE Qid NOT IN(SELECT Oid FROM QUEST_QUE)");//刪選項
		df.exSql("DELETE FROM QUEST_RES WHERE Qid NOT IN(SELECT Oid FROM QUEST_OPT)");//刪答案
		return search();
	}
	
	
	
	public String save(){
		//public String cno, stno, sno, cono, dno, gno, zno;
		Message msg=new Message();
		StringBuilder sql=new StringBuilder("UPDATE QUEST SET Oid=Oid ");
		if(!cno.equals("")){sql.append(",cno='"+cno+"'");}else{sql.append(",cno=null");}
		if(!stno.equals("")){sql.append(",stno='"+stno+"'");}else{sql.append(",stno=null");}
		if(!sno.equals("")){sql.append(",sno='"+sno+"'");}else{sql.append(",sno=null");}
		if(!cono.equals("")){sql.append(",cono='"+cono+"'");}else{sql.append(",cono=null");}
		if(!dno.equals("")){sql.append(",dno='"+dno+"'");}else{sql.append(",dno=null");}
		if(!gno.equals("")){sql.append(",gno='"+gno+"'");}else{sql.append(",gno=null");}
		df.exSql(sql.toString());
		msg.setMsg("已儲存規則");
		
		
		df.exSql("DELETE FROM QUEST_RES WHERE Qid="+Oid);
		
		sql=new StringBuilder("INSERT INTO QUEST_RES(student_no, Qid)SELECT student_no,"+Oid+" FROM stmd WHERE depart_class IN(SELECT ClassNo FROM Class WHERE Oid=Oid ");
		if(!cno.equals(""))sql.append("AND CampusNo='"+cno+"'");
		if(!stno.equals(""))sql.append("AND SchoolType='"+stno+"'");
		if(!sno.equals(""))sql.append("AND SchoolNo='"+sno+"'");
		if(!cono.equals(""))sql.append("AND InstNo='"+cono+"'");
		if(!dno.equals(""))sql.append("AND DeptNo='"+dno+"'");
		if(!gno.equals(""))sql.append("AND Grade='"+gno+"'");
		sql.append(")");
		System.out.println(sql);
		df.exSql(sql.toString());
		//System.out.println();
		
		
		msg.addMsg("並重新建立 "+df.sqlGetStr("SELECT COUNT(*)FROM QUEST_RES WHERE Qid="+Oid)+"份問卷");
		this.savMessage(msg);
		return edit();
	}

}
