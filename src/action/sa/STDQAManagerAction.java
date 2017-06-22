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
	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
	Date now=new Date();
	
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
		request.setAttribute("inity", "yes");
		
		request.setAttribute("quests", df.sqlGet("SELECT e.cname, q.*,(SELECT COUNT(*)FROM QUEST_RES WHERE Qid=q.Oid)as cnt,"
		+ "(SELECT COUNT(*)FROM QUEST_RES WHERE Qid=q.Oid AND reply IS NOT NULL)as rel, (SELECT COUNT(*)FROM QUEST_QUE WHERE Qid=q.Oid)as qs "
		+ "FROM QUEST q LEFT OUTER JOIN empl e ON e.idno=q.owner WHERE q.beginDate<'"+sf.format(now)+"'AND q.enDate>'"+sf.format(now)+"'"));
		return SUCCESS;
	}
	
	public String search(){
		//request.setAttribute("now", false);
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
		Map quest=df.sqlGetMap("SELECT q.*, e.cname FROM QUEST q LEFT OUTER JOIN empl e ON e.idno=q.owner WHERE q.Oid="+request.getParameter("Oid"));
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
	
	public String print() throws IOException{
		Date date=new Date();
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition","attachment;filename="+date.getTime()+".xls");
		List<Map>ans=df.sqlGet("SELECT student_no, IFNULL(reply,'')as reply FROM QUEST_RES WHERE Qid="+Oid+" ORDER BY student_no");
		List<Map>que=dm.sqlGet("SELECT * FROM QUEST_QUE WHERE Qid="+Oid);
		for(int i=0; i<que.size(); i++){
			que.get(i).put("opt", dm.sqlGet("SELECT * FROM QUEST_OPT WHERE Qid="+que.get(i).get("Oid")+" ORDER BY Oid"));
		}
		PrintWriter out=response.getWriter();	
		
		out.println ("<?xml version='1.0'?>");
		out.println ("<?mso-application progid='Excel.Sheet'?>");
		out.println ("<Workbook xmlns='urn:schemas-microsoft-com:office:spreadsheet'");
		out.println (" xmlns:o='urn:schemas-microsoft-com:office:office'");
		out.println (" xmlns:x='urn:schemas-microsoft-com:office:excel'");
		out.println (" xmlns:ss='urn:schemas-microsoft-com:office:spreadsheet'");
		out.println (" xmlns:html='http://www.w3.org/TR/REC-html40'>");
		out.println (" <DocumentProperties xmlns='urn:schemas-microsoft-com:office:office'>");
		out.println ("  <Author>John</Author>");
		out.println ("  <LastAuthor>John</LastAuthor>");
		out.println ("  <Created>2017-06-02T07:15:18Z</Created>");
		out.println ("  <LastSaved>2017-06-22T05:28:36Z</LastSaved>");
		out.println ("  <Version>15.00</Version>");
		out.println (" </DocumentProperties>");
		out.println (" <OfficeDocumentSettings xmlns='urn:schemas-microsoft-com:office:office'>");
		out.println ("  <AllowPNG/>");
		out.println (" </OfficeDocumentSettings>");
		out.println (" <ExcelWorkbook xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("  <WindowHeight>9645</WindowHeight>");
		out.println ("  <WindowWidth>13380</WindowWidth>");
		out.println ("  <WindowTopX>0</WindowTopX>");
		out.println ("  <WindowTopY>0</WindowTopY>");
		out.println ("  <ActiveSheet>1</ActiveSheet>");
		out.println ("  <ProtectStructure>False</ProtectStructure>");
		out.println ("  <ProtectWindows>False</ProtectWindows>");
		out.println (" </ExcelWorkbook>");
		out.println (" <Styles>");
		out.println ("  <Style ss:ID='Default' ss:Name='Normal'>");
		out.println ("   <Alignment ss:Vertical='Center'/>");
		out.println ("   <Borders/>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='12'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("   <Interior/>");
		out.println ("   <NumberFormat/>");
		out.println ("   <Protection/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s16'>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='16'");
		out.println ("    ss:Color='#000000' ss:Bold='1'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s17'>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='16'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("  </Style>");
		out.println (" </Styles>");
		out.println (" <Worksheet ss:Name='題目格式'>");
		out.println ("  <Table ss:ExpandedColumnCount='999' ss:ExpandedRowCount='"+(que.size()+10)+"' x:FullColumns='1'");
		out.println ("   x:FullRows='1' ss:StyleID='s17' ss:DefaultColumnWidth='54'");
		out.println ("   ss:DefaultRowHeight='21'>");
		out.println ("   <Column ss:StyleID='s17' ss:Width='43.5'/>");
		out.println ("   <Column ss:StyleID='s17' ss:Width='372'/>");
		out.println ("   <Column ss:StyleID='s17' ss:Width='57.75' ss:Span='3'/>");
		out.println ("   <Column ss:Index='7' ss:StyleID='s17' ss:Width='51.75' ss:Span='4'/>");
		out.println ("   <Row ss:StyleID='s16'>");
		out.println ("    <Cell><Data ss:Type='String'>題型</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>問題內容</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>選項1</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>選項2</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>選項3</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>選項4</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>選項5</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>選項6</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>選項7</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>選項8</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>選項9</Data></Cell>");
		out.println ("   </Row>");
		List<Map>opt;
		for(int i=0; i<que.size(); i++){
			out.println ("   <Row>");
			out.println ("    <Cell><Data ss:Type='String'>"+que.get(i).get("category")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+que.get(i).get("value")+"</Data></Cell>");
			opt=(List<Map>)que.get(i).get("opt");
			for(int j=0; j<opt.size(); j++){
				out.println ("    <Cell><Data ss:Type='String'>"+opt.get(j).get("value")+"</Data></Cell>");
			}			
			out.println ("   </Row>");
			
		}
		
		
		out.println ("  </Table>");
		out.println ("  <WorksheetOptions xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("   <PageSetup>");
		out.println ("    <Header x:Margin='0.3'/>");
		out.println ("    <Footer x:Margin='0.3'/>");
		out.println ("    <PageMargins x:Bottom='0.75' x:Left='0.7' x:Right='0.7' x:Top='0.75'/>");
		out.println ("   </PageSetup>");
		out.println ("   <Print>");
		out.println ("    <ValidPrinterInfo/>");
		out.println ("    <PaperSizeIndex>9</PaperSizeIndex>");
		out.println ("    <HorizontalResolution>-1</HorizontalResolution>");
		out.println ("    <VerticalResolution>-1</VerticalResolution>");
		out.println ("   </Print>");
		out.println ("   <ProtectObjects>False</ProtectObjects>");
		out.println ("   <ProtectScenarios>False</ProtectScenarios>");
		out.println ("  </WorksheetOptions>");
		out.println (" </Worksheet>");
		out.println (" <Worksheet ss:Name='答案格式'>");
		out.println ("  <Table ss:ExpandedColumnCount='2' ss:ExpandedRowCount='"+(ans.size()+100)+"' x:FullColumns='1'");
		out.println ("   x:FullRows='1' ss:StyleID='s17' ss:DefaultColumnWidth='54'");
		out.println ("   ss:DefaultRowHeight='21'>");
		out.println ("   <Column ss:StyleID='s17' ss:Width='83.25'/>");
		out.println ("   <Column ss:StyleID='s17' ss:Width='124.5'/>");
		
		
		for(int i=0; i<ans.size(); i++){
			out.println ("   <Row>");
			out.println ("    <Cell><Data ss:Type='String'>"+ans.get(i).get("student_no")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+ans.get(i).get("reply")+"</Data></Cell>");
			out.println ("   </Row>");
			
		}
		
		
		
		
		
		
		out.println ("  </Table>");
		out.println ("  <WorksheetOptions xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("   <PageSetup>");
		out.println ("    <Header x:Margin='0.3'/>");
		out.println ("    <Footer x:Margin='0.3'/>");
		out.println ("    <PageMargins x:Bottom='0.75' x:Left='0.7' x:Right='0.7' x:Top='0.75'/>");
		out.println ("   </PageSetup>");
		out.println ("   <Print>");
		out.println ("    <ValidPrinterInfo/>");
		out.println ("    <PaperSizeIndex>9</PaperSizeIndex>");
		out.println ("    <HorizontalResolution>-1</HorizontalResolution>");
		out.println ("    <VerticalResolution>-1</VerticalResolution>");
		out.println ("   </Print>");
		out.println ("   <Selected/>");
		out.println ("   <ProtectObjects>False</ProtectObjects>");
		out.println ("   <ProtectScenarios>False</ProtectScenarios>");
		out.println ("  </WorksheetOptions>");
		out.println (" </Worksheet>");
		out.println ("</Workbook>");
		out.println ("");
		
		
		out.close();
		out.flush();
		
		
		
		return null;
	}

}
