package action.eis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import action.BasePrintXmlAction;
import model.Message;

public class StdScoreViewerAction extends BasePrintXmlAction{
	
	public File fileUpload;
    public String student_no;	
	
    private List<Map>stds;
	public String execute(){		
		return SUCCESS;
	}
	
	private Boolean checkStd(String student_no) {
		if(student_no.indexOf(",")<0&&fileUpload==null){			
			return false;
		}		
		return true;
	}
	
	public String print() throws IOException{
		
		Message msg=new Message();
		if(!checkStd(student_no)){
			msg.setError("查詢條件有誤");
			this.savMessage(msg);
			return SUCCESS;
		}
		
		stds=new ArrayList();
		if(student_no.indexOf(",")>0){			
			stds.add(getScore(student_no.substring(0, student_no.indexOf(","))));			
		}        
        
		if(fileUpload!=null){
    	   try{
    		   msg.setMsg(upload(true));
    		   this.savMessage(msg);
    		   //return SUCCESS;
    	   	}catch(Exception e){
    		   msg.setMsg("文件格式有誤");
    		   this.savMessage(msg);
    		   return SUCCESS;
    	   	}  
		}
		
		Date date=new Date();
		response.setContentType("text/html; charset=UTF-8");xml2ods(response, getRequest(), date);		
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
		out.println ("  <Created>2017-11-13T07:24:11Z</Created>");
		out.println ("  <LastSaved>2017-11-13T08:55:02Z</LastSaved>");
		out.println ("  <Version>15.00</Version>");
		out.println (" </DocumentProperties>");
		out.println (" <OfficeDocumentSettings xmlns='urn:schemas-microsoft-com:office:office'>");
		out.println ("  <AllowPNG/>");
		out.println (" </OfficeDocumentSettings>");
		out.println (" <ExcelWorkbook xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("  <WindowHeight>12390</WindowHeight>");
		out.println ("  <WindowWidth>28800</WindowWidth>");
		out.println ("  <WindowTopX>0</WindowTopX>");
		out.println ("  <WindowTopY>0</WindowTopY>");
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
		out.println (" </Styles>");
		out.println (" <Worksheet ss:Name='工作表1'>");
		out.println ("  <Table ss:ExpandedColumnCount='45' ss:ExpandedRowCount='"+(stds.size()+100)+"' x:FullColumns='1'");
		out.println ("   x:FullRows='1' ss:DefaultColumnWidth='54' ss:DefaultRowHeight='16.5'>");
		out.println ("   <Column ss:AutoFitWidth='0' ss:Width='75' ss:Span='2'/>");
		out.println ("   <Column ss:Index='4' ss:AutoFitWidth='0' ss:Width='37.5'/>");
		out.println ("   <Column ss:AutoFitWidth='0' ss:Width='75'/>");
		out.println ("   <Column ss:Width='76.5' ss:Span='23'/>");
		out.println ("   <Row>");
		out.println ("    <Cell><Data ss:Type='String'>班級</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>學號</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>姓名</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>性別</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>身分別</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第1學期學業</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第1學期操行</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第1學期名次</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第1學期應修學分</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第1學期不及格學分</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第2學期學業</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第2學期操行</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第2學期名次</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第2學期應修學分</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第2學期不及格學分</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第3學期學業</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第3學期操行</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第3學期名次</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第3學期應修學分</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第3學期不及格學分</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第4學期學業</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第4學期操行</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第4學期名次</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第4學期應修學分</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第4學期不及格學分</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第5學期學業</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第5學期操行</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第5學期名次</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第5學期應修學分</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第5學期不及格學分</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第6學期學業</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第6學期操行</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第6學期名次</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第6學期應修學分</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第6學期不及格學分</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第7學期學業</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第7學期操行</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第7學期名次</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第7學期應修學分</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第7學期不及格學分</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第8學期學業</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第8學期操行</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第8學期名次</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第8學期應修學分</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第8學期不及格學分</Data></Cell>");
		out.println ("   </Row>");
		for(int i=0; i<stds.size(); i++){			
			out.println ("   <Row>");
			out.println ("    <Cell><Data ss:Type='String'>"+stds.get(i).get("ClassName")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+stds.get(i).get("student_no")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+stds.get(i).get("student_name")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+stds.get(i).get("sex")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+stds.get(i).get("name")+"</Data></Cell>");			
			for(int j=1; j<=8; j++){
				//TODO 操他媽IFNULL無效應該是因為資料庫太舊幹				
				if(stds.get(i).get("score"+j)!=null){
					out.println ("    <Cell><Data ss:Type='Number'>"+stds.get(i).get("score"+j)+"</Data></Cell>");
				}else{
					out.println ("    <Cell>0</Cell>");
				}
				
				if(stds.get(i).get("score1"+j)!=null){
					out.println ("    <Cell><Data ss:Type='Number'>"+stds.get(i).get("score1"+j)+"</Data></Cell>");
				}else{
					out.println ("    <Cell>0</Cell>");
				}	
				
				if(stds.get(i).get("rank"+j)!=null && !stds.get(i).get("rank"+j).equals("")){
					out.println ("    <Cell><Data ss:Type='Number'>"+stds.get(i).get("rank"+j)+"</Data></Cell>");
				}else{
					out.println ("    <Cell>0</Cell>");
				}
				
				if(stds.get(i).get("tot"+j)!=null && !stds.get(i).get("tot"+j).equals("")){
					out.println ("    <Cell><Data ss:Type='Number'>"+stds.get(i).get("tot"+j)+"</Data></Cell>");
				}else{
					out.println ("    <Cell>0</Cell>");
				}
				
				if(stds.get(i).get("pas"+j)!=null && !stds.get(i).get("pas"+j).equals("")){
					out.println ("    <Cell><Data ss:Type='Number'>"+stds.get(i).get("pas"+j)+"</Data></Cell>");
				}else{
					out.println ("    <Cell>0</Cell>");
				}
				
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
		out.println ("   <Selected/>");
		out.println ("   <Panes>");
		out.println ("    <Pane>");
		out.println ("     <Number>3</Number>");
		out.println ("     <ActiveRow>19</ActiveRow>");
		out.println ("     <ActiveCol>23</ActiveCol>");
		out.println ("    </Pane>");
		out.println ("   </Panes>");
		out.println ("   <ProtectObjects>False</ProtectObjects>");
		out.println ("   <ProtectScenarios>False</ProtectScenarios>");
		out.println ("  </WorksheetOptions>");
		out.println (" </Worksheet>");
		out.println ("</Workbook>");
		out.close();
		out.flush();
		return null;
	}	
	
	private String upload(Boolean isScore) throws IOException{
		
		
		FileInputStream fis = new FileInputStream(fileUpload);
		XSSFSheet sheet;
		try{
			XSSFWorkbook xwb = new XSSFWorkbook(fis);
			sheet = xwb.getSheetAt(0);			
		}catch(Exception e){
			return "請另存為xlsx檔案重新上傳";			
		}
		
		XSSFRow row;	
		int cnt=0;
		for (int i = sheet.getFirstRowNum(); i <= sheet.getPhysicalNumberOfRows(); i++) {				
			row = sheet.getRow(i);							
			try{			
				if(isScore) {
					stds.add(getScore(row.getCell(0).toString()));
				}else {
					stds.add(getCompositData(row.getCell(0).toString()));
				}
				stds.add(getScore(row.getCell(0).toString()));
			}catch(Exception e){
				
				continue;
			}			
			cnt++;
		}
		
		fis.close();			
		return "查詢到"+cnt+"位學生歷年成績";
	}
	
	private Map getScore(String stdNo){
		
		List<Map>s=df.sqlGet("SELECT IFNULL((SELECT SUM(credit)FROM ScoreHist WHERE student_no=s.student_no AND school_year=s.school_year AND school_term=s.school_term),0)as tot, "
		+ "IFNULL((SELECT SUM(credit)FROM ScoreHist WHERE score<=60 AND student_no=s.student_no AND school_year=s.school_year AND school_term=s.school_term), 0)as pas, "
		+ "s.rank, s.score, c.score as score1 FROM Stavg s LEFT OUTER JOIN cond c ON "
		+ "s.student_no=c.student_no AND s.school_year=c.school_year AND s.school_term=c.school_term WHERE "
		+ "s.student_no='"+stdNo+"'ORDER BY s.school_year, s.school_term");
		
		Map m=df.sqlGetMap("SELECT csi.name, IF(s.sex='1','男' ,'女')as sex, c.ClassName, s.student_no, "
		+ "s.student_name FROM Class c, stmd s LEFT OUTER JOIN CODE_STMD_IDENT csi ON s.ident=csi.id WHERE "
		+ "c.ClassNo=s.depart_class AND s.student_no='"+stdNo+"'");
		
		if(m==null)m=df.sqlGetMap("SELECT csi.name, IF(s.sex='1','男' ,'女')as sex, c.ClassName, s.student_no, "
				+ "s.student_name FROM Class c, Gstmd s LEFT OUTER JOIN CODE_STMD_IDENT csi ON s.ident=csi.id WHERE "
				+ "c.ClassNo=s.depart_class AND s.student_no='"+stdNo+"'");
		
		
		for(int i=0; i<8; i++){
			
			if(i+1<=s.size()){
				m.put("rank"+(i+1), s.get(i).get("rank"));
				m.put("score"+(i+1), s.get(i).get("score"));
				m.put("score1"+(i+1), s.get(i).get("score1"));
				m.put("tot"+(i+1), s.get(i).get("tot"));
				m.put("pas"+(i+1), s.get(i).get("pas"));
			}else{
				m.put("rank"+(i+1), "");
				m.put("score"+(i+1), "");
				m.put("score1"+(i+1), "");
				m.put("tot"+(i+1), "0");
				m.put("pas"+(i+1), "0");
			}			
		}		
		return m;
	}
	
	private Map getCompositData(String stdNo) {
		Map map=df.sqlGetMap("SELECT cs.name as SchoolName, cd.name as DeptName, s.student_no, s.student_name, s.idno, s.sex, s.birthday FROM stmd s, Class c, CODE_SCHOOL cs, CODE_DEPT cd WHERE cd.id=c.DeptNo AND cs.id=c.SchoolNo AND s.depart_class=c.ClassNo AND s.student_no='"+stdNo+"'");
		if(map==null)map=df.sqlGetMap("SELECT cs.name as SchoolName, cd.name as DeptName, s.student_no, s.student_name, s.idno, s.sex, s.birthday FROM Gstmd s, Class c, CODE_SCHOOL cs, CODE_DEPT cd WHERE cd.id=c.DeptNo AND cs.id=c.SchoolNo AND s.depart_class=c.ClassNo AND s.student_no='"+stdNo+"'");
		
		List<Map>score=df.sqlGet("SELECT*FROM comb1 WHERE student_no='"+stdNo+"'ORDER BY school_year, school_term");
		List<Map>cond=df.sqlGet("SELECT score FROM cond WHERE student_no='"+stdNo+"'ORDER BY school_year, school_term");
		List<Map>comb2=df.sqlGet("SELECT c.ddate, c2.name as reasonName,  cc.name as kind1Name,c.cnt1,ccc.name as kind2Name,c.cnt2, c.school_year, c.school_term FROM (comb2 c LEFT OUTER JOIN CODE_COMB2 cc ON c.kind1=cc.id)LEFT OUTER JOIN CODE_COMB2 ccc ON c.kind2=ccc.id, code2 c2 WHERE c2.no=c.reason AND student_no='"+stdNo+"'ORDER BY school_year, school_term");
		
		map.put("score", score);
		map.put("cond", cond);
		map.put("comb2", comb2);
		return map;
	}
	
	public String StudentCompositData() throws IOException, ParseException {
		
		Message msg=new Message();
		if(!checkStd(student_no)){
			msg.setError("查詢條件有誤");
			this.savMessage(msg);
			return SUCCESS;
		}
		
		stds=new ArrayList();
		if(student_no.indexOf(",")>0){			
			stds.add(getCompositData(student_no.substring(0, student_no.indexOf(","))));			
		}        
        
		if(fileUpload!=null){
    	   try{
    		   msg.setMsg(upload(false));
    		   this.savMessage(msg);
    		   //return SUCCESS;
    	   	}catch(Exception e){
    		   msg.setMsg("文件格式有誤");
    		   this.savMessage(msg);
    		   return SUCCESS;
    	   	}  
		}
		
		
		Date date=new Date();
		response.setContentType("text/html; charset=UTF-8");xml2ods(response, getRequest(), date);		
		PrintWriter out=response.getWriter();
		
		out.println ("<?xml version='1.0'?>");
		out.println ("<?mso-application progid='Excel.Sheet'?>");
		out.println ("<Workbook xmlns='urn:schemas-microsoft-com:office:spreadsheet'");
		out.println (" xmlns:o='urn:schemas-microsoft-com:office:office'");
		out.println (" xmlns:x='urn:schemas-microsoft-com:office:excel'");
		out.println (" xmlns:ss='urn:schemas-microsoft-com:office:spreadsheet'");
		out.println (" xmlns:html='http://www.w3.org/TR/REC-html40'>");
		out.println (" <DocumentProperties xmlns='urn:schemas-microsoft-com:office:office'>");
		out.println ("  <Author>Jason</Author>");
		out.println ("  <LastAuthor>John</LastAuthor>");
		out.println ("  <LastPrinted>2020-09-10T02:44:51Z</LastPrinted>");
		out.println ("  <Created>2008-05-13T07:07:26Z</Created>");
		out.println ("  <LastSaved>2020-09-10T02:44:49Z</LastSaved>");
		out.println ("  <Company>CMT</Company>");
		out.println ("  <Version>15.00</Version>");
		out.println (" </DocumentProperties>");
		out.println (" <OfficeDocumentSettings xmlns='urn:schemas-microsoft-com:office:office'>");
		out.println ("  <AllowPNG/>");
		out.println (" </OfficeDocumentSettings>");
		out.println (" <ExcelWorkbook xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("  <WindowHeight>11400</WindowHeight>");
		out.println ("  <WindowWidth>28800</WindowWidth>");
		out.println ("  <WindowTopX>0</WindowTopX>");
		out.println ("  <WindowTopY>0</WindowTopY>");
		out.println ("  <ProtectStructure>False</ProtectStructure>");
		out.println ("  <ProtectWindows>False</ProtectWindows>");
		out.println (" </ExcelWorkbook>");
		out.println (" <Styles>");
		out.println ("  <Style ss:ID='Default' ss:Name='Normal'>");
		out.println ("   <Alignment ss:Vertical='Center'/>");
		out.println ("   <Borders/>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='12'/>");
		out.println ("   <Interior/>");
		out.println ("   <NumberFormat/>");
		out.println ("   <Protection/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007442016'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007442036'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007442056'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007438064'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007438084'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007438104'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007438124'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007438144'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007438164'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007432448'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007432468'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007432488'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007432508'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007432528'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007432548'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007438896'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007438916'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007438936'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007438956'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007438976'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007438996'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007432032'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007432052'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007432072'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007432092'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007432112'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007432132'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007432656'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007432676'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007432696'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007432716'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007432736'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007432756'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007433488'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007433508'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007433528'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007433548'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007433568'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007433588'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007443680'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007443700'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007443720'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007443740'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007443760'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007443780'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007437024'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007437044'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007437064'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007437084'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007437104'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007437124'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007441808'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007441828'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007441848'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007441868'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007441888'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007441908'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007441184'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007441204'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007441224'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007441244'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007441264'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007441284'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007443264'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007443284'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007443304'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007443324'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007443344'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007443364'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007440976'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007440996'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007441016'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007441036'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007441056'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007441076'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007441392'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007441412'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007441432'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007441452'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007441472'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007441492'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007436816'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='標楷體' x:CharSet='136' x:Family='Script'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007436836'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='標楷體' x:CharSet='136' x:Family='Script'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007436856'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='標楷體' x:CharSet='136' x:Family='Script'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007436876'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007436896'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007436916'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007442848'>");
		out.println ("   <Alignment ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007442868'>");
		out.println ("   <Alignment ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007442888'>");
		out.println ("   <Alignment ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007442908'>");
		out.println ("   <Alignment ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007442928'>");
		out.println ("   <Alignment ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007442948'>");
		out.println ("   <Alignment ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007436480'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center' ss:WrapText='1'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='標楷體' x:CharSet='136' x:Family='Script'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007436500'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center' ss:WrapText='1'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='標楷體' x:CharSet='136' x:Family='Script'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007436520'>");
		out.println ("   <Alignment ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007436540'>");
		out.println ("   <Alignment ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007436560'>");
		out.println ("   <Alignment ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m1679007436580'>");
		out.println ("   <Alignment ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s63'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center'/>");
		out.println ("   <Font ss:FontName='標楷體' x:CharSet='136' x:Family='Script' ss:Size='16'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s64'>");
		out.println ("   <Alignment ss:Vertical='Center'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s65'>");
		out.println ("   <Alignment ss:Horizontal='Right' ss:Vertical='Center'/>");
		out.println ("   <Font ss:FontName='標楷體' x:CharSet='136' x:Family='Script'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s67'>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s68'>");
		out.println ("   <Font ss:FontName='標楷體' x:CharSet='136' x:Family='Script'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s76'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='標楷體' x:CharSet='136' x:Family='Script'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s77'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s87'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center' ss:WrapText='1'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='標楷體' x:CharSet='136' x:Family='Script'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s94'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center' ss:WrapText='1'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='標楷體' x:CharSet='136' x:Family='Script'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s95'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center' ss:WrapText='1'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='標楷體' x:CharSet='136' x:Family='Script'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s98'>");
		out.println ("   <Alignment ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='標楷體' x:CharSet='136' x:Family='Script'/>");
		out.println ("  </Style>");
		out.println (" </Styles>");
		String styleId;
		Map std;
		List<Map>score;
		List<Map>cond;
		List<Map>comb2;
		SimpleDateFormat sf1=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sf2=new SimpleDateFormat("民國y年M月d日");
		Calendar c=Calendar.getInstance();
		for(int i=0; i<stds.size(); i++) {
			
			std=stds.get(i);
			c.setTime(   sf1.parse(std.get("birthday").toString() )    );
			c.add(Calendar.YEAR, -1911);
			score=(List<Map>) std.get("score");
			cond=(List<Map>) std.get("cond");
			comb2=(List<Map>) std.get("comb2");
			
			out.println (" <Worksheet ss:Name='"+std.get("student_no")+"'>");
			out.println ("  <Table ss:ExpandedColumnCount='12' ss:ExpandedRowCount='53' x:FullColumns='1'");
			out.println ("   x:FullRows='1' ss:DefaultColumnWidth='54' ss:DefaultRowHeight='16.5'>");
			out.println ("   <Column ss:AutoFitWidth='0' ss:Width='24'/>");
			out.println ("   <Column ss:AutoFitWidth='0' ss:Width='42.75'/>");
			out.println ("   <Column ss:AutoFitWidth='0' ss:Width='45' ss:Span='1'/>");
			out.println ("   <Column ss:Index='5' ss:AutoFitWidth='0' ss:Width='51'/>");
			out.println ("   <Column ss:AutoFitWidth='0' ss:Width='45' ss:Span='2'/>");
			out.println ("   <Column ss:Index='9' ss:AutoFitWidth='0' ss:Width='51'/>");
			out.println ("   <Column ss:AutoFitWidth='0' ss:Width='45' ss:Span='2'/>");
			
			out.println ("   <Row ss:AutoFitHeight='0'>");
			out.println ("    <Cell ss:StyleID='s64'/>");
			out.println ("    <Cell ss:StyleID='s64'/>");
			out.println ("    <Cell ss:StyleID='s64'/>");
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0'>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='s65'><Data ss:Type='String'>系(科)別：</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s67'><Data ss:Type='String'>"+std.get("DeptName")+" ("+std.get("SchoolName")+")</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s67'/>");
			out.println ("    <Cell ss:Index='6' ss:StyleID='s65'><Data ss:Type='String'>學號：</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s67'><Data ss:Type='String'>"+std.get("student_no")+"</Data></Cell>");
			out.println ("    <Cell ss:Index='9' ss:StyleID='s68'><Data ss:Type='String'>姓    名：</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s67'><Data ss:Type='String'>"+std.get("student_name")+"</Data></Cell>");
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0'>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='s65'><Data ss:Type='String'>身分證號：</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s67'><Data ss:Type='String'>"+std.get("idno")+"</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s67'/>");
			
			out.println ("    <Cell ss:Index='6' ss:StyleID='s65'><Data ss:Type='String'>性別：</Data></Cell>");
			if(String.valueOf(std.get("sex")).equals("1")) {
				out.println ("    <Cell ss:StyleID='s67'><Data ss:Type='String'>男</Data></Cell>");
			}else {
				out.println ("    <Cell ss:StyleID='s67'><Data ss:Type='String'>女</Data></Cell>");
			}
			
			out.println ("    <Cell ss:Index='9' ss:StyleID='s68'><Data ss:Type='String'>出生日期：</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s67'><Data ss:Type='String'>"+sf2.format(c.getTime())+"</Data></Cell>");
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0'/>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:MergeDown='4' ss:StyleID='m1679007436480'><Data ss:Type='String'>成績紀錄</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s76'><Data ss:Type='String'>項目</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s76'><Data ss:Type='String'>一上</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s76'><Data ss:Type='String'>一下</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s76'><Data ss:Type='String'>二上</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s76'><Data ss:Type='String'>二下</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s76'><Data ss:Type='String'>三上</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s76'><Data ss:Type='String'>三下</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s76'><Data ss:Type='String'>四上</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s76'><Data ss:Type='String'>四下</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s76'><Data ss:Type='String'>五上</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s76'><Data ss:Type='String'>五下</Data></Cell>");
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:Index='2' ss:StyleID='s76'><Data ss:Type='String'>操行</Data></Cell>");
			/*
			Map map=df.sqlGetMap("SELECT cs.name as SchoolName, cd.name as DeptName, s.student_no, s.student_name, s.idno, s.sex, s.birthday FROM stmd s, Class c, CODE_SCHOOL cs, CODE_DEPT cd WHERE cd.id=c.DeptNo AND cs.id=c.SchoolNo AND s.depart_class=c.ClassNo AND s.student_no='"+stdNo+"'");
			if(map==null)map=df.sqlGetMap("SELECT cs.name as SchoolName, cd.name as DeptName, s.student_no, s.student_name, s.idno, s.sex, s.birthday FROM Gstmd s, Class c, CODE_SCHOOL cs, CODE_DEPT cd WHERE cd.id=c.DeptNo AND cs.id=c.SchoolNo AND s.depart_class=c.ClassNo AND s.student_no='"+stdNo+"'");
			
			List<Map>score=df.sqlGet("SELECT*FROM comb1 WHERE student_no='"+stdNo+"'ORDER BY school_year, school_term");
			List<Map>cond=df.sqlGet("SELECT score FROM cond WHERE student_no='"+stdNo+"'ORDER BY school_year, school_term");
			List<Map>comb2=df.sqlGet("SELECT c2.name as reasonName,  cc.name as kind1Name,c.cnt1,ccc.name as kind2Name,c.cnt2, c.school_year, c.school_term FROM (comb2 c LEFT OUTER JOIN CODE_COMB2 cc ON c.kind1=cc.id)LEFT OUTER JOIN CODE_COMB2 ccc ON c.kind2=ccc.id, code2 c2 WHERE c2.no=c.reason AND student_no='"+stdNo+"'ORDER BY school_year, school_term");
			*/
			System.out.println("SELECT score FROM ScoreHist WHERE cscode='99999' AND student_no='"+std.get("student_no")+"' AND school_year='"+score.get(0).get("school_year")+"' AND school_term='"+score.get(0).get("school_year")+"'");
			try{out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+df.sqlGetStr("SELECT score FROM ScoreHist WHERE cscode='99999' AND student_no='"+std.get("student_no")+"' AND school_year='"+score.get(0).get("school_year")+"' AND school_term='"+score.get(0).get("school_term")+"'")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+df.sqlGetStr("SELECT score FROM ScoreHist WHERE cscode='99999' AND student_no='"+std.get("student_no")+"' AND school_year='"+score.get(1).get("school_year")+"' AND school_term='"+score.get(1).get("school_term")+"'")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+df.sqlGetStr("SELECT score FROM ScoreHist WHERE cscode='99999' AND student_no='"+std.get("student_no")+"' AND school_year='"+score.get(2).get("school_year")+"' AND school_term='"+score.get(2).get("school_term")+"'")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+df.sqlGetStr("SELECT score FROM ScoreHist WHERE cscode='99999' AND student_no='"+std.get("student_no")+"' AND school_year='"+score.get(3).get("school_year")+"' AND school_term='"+score.get(3).get("school_term")+"'")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+df.sqlGetStr("SELECT score FROM ScoreHist WHERE cscode='99999' AND student_no='"+std.get("student_no")+"' AND school_year='"+score.get(4).get("school_year")+"' AND school_term='"+score.get(4).get("school_term")+"'")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+df.sqlGetStr("SELECT score FROM ScoreHist WHERE cscode='99999' AND student_no='"+std.get("student_no")+"' AND school_year='"+score.get(5).get("school_year")+"' AND school_term='"+score.get(5).get("school_term")+"'")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+df.sqlGetStr("SELECT score FROM ScoreHist WHERE cscode='99999' AND student_no='"+std.get("student_no")+"' AND school_year='"+score.get(6).get("school_year")+"' AND school_term='"+score.get(6).get("school_term")+"'")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+df.sqlGetStr("SELECT score FROM ScoreHist WHERE cscode='99999' AND student_no='"+std.get("student_no")+"' AND school_year='"+score.get(7).get("school_year")+"' AND school_term='"+score.get(7).get("school_term")+"'")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+df.sqlGetStr("SELECT score FROM ScoreHist WHERE cscode='99999' AND student_no='"+std.get("student_no")+"' AND school_year='"+score.get(8).get("school_year")+"' AND school_term='"+score.get(8).get("school_term")+"'")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+df.sqlGetStr("SELECT score FROM ScoreHist WHERE cscode='99999' AND student_no='"+std.get("student_no")+"' AND school_year='"+score.get(9).get("school_year")+"' AND school_term='"+score.get(9).get("school_term")+"'")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			/*
			try{out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+df.sqlGetStr("SELECT score FROM ScoreHist WHERE cscode='99999' AND student_no='"+std.get("student_no")+"' AND school_year='"+score.get(0).get("school_year")+"' AND school_term='"+score.get(0).get("school_year")+"'")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+score.get(1).get("physical_score")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+score.get(2).get("physical_score")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+score.get(3).get("physical_score")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+score.get(4).get("physical_score")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+score.get(5).get("physical_score")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+score.get(6).get("physical_score")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+score.get(7).get("physical_score")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+score.get(8).get("physical_score")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+score.get(9).get("physical_score")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			*/
			
			/*
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>$J2$</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>$J3$</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>$J4$</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>$J5$</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>$J6$</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>$J7$</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>$J8$</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>$J9$</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>$J10$</Data></Cell>");
			*/
			
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:Index='2' ss:StyleID='s76'><Data ss:Type='String'>學業</Data></Cell>");
			try{out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+df.sqlGetStr("SELECT ROUND(score)as score FROM Stavg WHERE student_no='"+std.get("student_no")+"' AND school_year='"+score.get(0).get("school_year")+"' AND school_term='"+score.get(0).get("school_term")+"'")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+df.sqlGetStr("SELECT ROUND(score)as score FROM Stavg WHERE student_no='"+std.get("student_no")+"' AND school_year='"+score.get(1).get("school_year")+"' AND school_term='"+score.get(1).get("school_term")+"'")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+df.sqlGetStr("SELECT ROUND(score)as score FROM Stavg WHERE student_no='"+std.get("student_no")+"' AND school_year='"+score.get(2).get("school_year")+"' AND school_term='"+score.get(2).get("school_term")+"'")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+df.sqlGetStr("SELECT ROUND(score)as score FROM Stavg WHERE student_no='"+std.get("student_no")+"' AND school_year='"+score.get(3).get("school_year")+"' AND school_term='"+score.get(3).get("school_term")+"'")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+df.sqlGetStr("SELECT ROUND(score)as score FROM Stavg WHERE student_no='"+std.get("student_no")+"' AND school_year='"+score.get(4).get("school_year")+"' AND school_term='"+score.get(4).get("school_term")+"'")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+df.sqlGetStr("SELECT ROUND(score)as score FROM Stavg WHERE student_no='"+std.get("student_no")+"' AND school_year='"+score.get(5).get("school_year")+"' AND school_term='"+score.get(5).get("school_term")+"'")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+df.sqlGetStr("SELECT ROUND(score)as score FROM Stavg WHERE student_no='"+std.get("student_no")+"' AND school_year='"+score.get(6).get("school_year")+"' AND school_term='"+score.get(6).get("school_term")+"'")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+df.sqlGetStr("SELECT ROUND(score)as score FROM Stavg WHERE student_no='"+std.get("student_no")+"' AND school_year='"+score.get(7).get("school_year")+"' AND school_term='"+score.get(7).get("school_term")+"'")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+df.sqlGetStr("SELECT ROUND(score)as score FROM Stavg WHERE student_no='"+std.get("student_no")+"' AND school_year='"+score.get(8).get("school_year")+"' AND school_term='"+score.get(8).get("school_term")+"'")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+df.sqlGetStr("SELECT ROUND(score)as score FROM Stavg WHERE student_no='"+std.get("student_no")+"' AND school_year='"+score.get(9).get("school_year")+"' AND school_term='"+score.get(9).get("school_term")+"'")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			/*
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>$B2$</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>$B3$</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>$B4$</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>$B5$</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>$B6$</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>$B7$</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>$B8$</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>$B9$</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>$B10$</Data></Cell>");
			*/
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:Index='2' ss:StyleID='s76'><Data ss:Type='String'>體育</Data></Cell>");
			try{if(score.get(0).get("physical_score")!=null) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+score.get(0).get("physical_score")+"</Data></Cell>");}else {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{if(score.get(1).get("physical_score")!=null) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+score.get(1).get("physical_score")+"</Data></Cell>");}else {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{if(score.get(2).get("physical_score")!=null) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+score.get(2).get("physical_score")+"</Data></Cell>");}else {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{if(score.get(3).get("physical_score")!=null) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+score.get(3).get("physical_score")+"</Data></Cell>");}else {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{if(score.get(4).get("physical_score")!=null) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+score.get(4).get("physical_score")+"</Data></Cell>");}else {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{if(score.get(5).get("physical_score")!=null) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+score.get(5).get("physical_score")+"</Data></Cell>");}else {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{if(score.get(6).get("physical_score")!=null) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+score.get(6).get("physical_score")+"</Data></Cell>");}else {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{if(score.get(7).get("physical_score")!=null) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+score.get(7).get("physical_score")+"</Data></Cell>");}else {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{if(score.get(8).get("physical_score")!=null) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+score.get(8).get("physical_score")+"</Data></Cell>");}else {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{if(score.get(9).get("physical_score")!=null) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+score.get(9).get("physical_score")+"</Data></Cell>");}else {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			/*
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>$S1$</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>$S2$</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>$S3$</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>$S4$</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>$S5$</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>$S6$</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>$S7$</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>$S8$</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>$S9$</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>$S10$</Data></Cell>");
			*/
			
			
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:Index='2' ss:StyleID='s76'><Data ss:Type='String'>軍訓</Data></Cell>");
			try{if(score.get(0).get("military_score")!=null) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+score.get(0).get("military_score")+"</Data></Cell>");}else {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{if(score.get(1).get("military_score")!=null) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+score.get(1).get("military_score")+"</Data></Cell>");}else {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{if(score.get(2).get("military_score")!=null) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+score.get(2).get("military_score")+"</Data></Cell>");}else {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{if(score.get(3).get("military_score")!=null) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+score.get(3).get("military_score")+"</Data></Cell>");}else {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{if(score.get(4).get("military_score")!=null) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+score.get(4).get("military_score")+"</Data></Cell>");}else {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{if(score.get(5).get("military_score")!=null) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+score.get(5).get("military_score")+"</Data></Cell>");}else {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{if(score.get(6).get("military_score")!=null) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+score.get(6).get("military_score")+"</Data></Cell>");}else {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{if(score.get(7).get("military_score")!=null) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+score.get(7).get("military_score")+"</Data></Cell>");}else {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{if(score.get(8).get("military_score")!=null) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+score.get(8).get("military_score")+"</Data></Cell>");}else {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			try{if(score.get(9).get("military_score")!=null) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'>"+score.get(9).get("military_score")+"</Data></Cell>");}else {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}}catch(Exception e) {out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");}
			/*out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");
			out.println ("    <Cell ss:StyleID='s77'><Data ss:Type='String'></Data></Cell>");*/
			out.println ("   </Row>");
			out.println ("   <Row ss:Index='12' ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:MergeDown='9' ss:StyleID='m1679007436500'><Data ss:Type='String'>導               師              評             語  </Data></Cell>");
			out.println ("    <Cell ss:StyleID='s76'><Data ss:Type='String'>一上</Data></Cell>");
			try{out.println ("    <Cell ss:MergeAcross='9' ss:StyleID='m1679007436520'><Data ss:Type='String'>"+score.get(0).get("com_name")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:MergeAcross='9' ss:StyleID='m1679007436520'><Data ss:Type='String'></Data></Cell>");}
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:Index='2' ss:StyleID='s76'><Data ss:Type='String'>一下</Data></Cell>");
			try{out.println ("    <Cell ss:MergeAcross='9' ss:StyleID='m1679007436520'><Data ss:Type='String'>"+score.get(1).get("com_name")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:MergeAcross='9' ss:StyleID='m1679007436520'><Data ss:Type='String'></Data></Cell>");}
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:Index='2' ss:StyleID='s76'><Data ss:Type='String'>二上</Data></Cell>");
			try{out.println ("    <Cell ss:MergeAcross='9' ss:StyleID='m1679007436520'><Data ss:Type='String'>"+score.get(2).get("com_name")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:MergeAcross='9' ss:StyleID='m1679007436520'><Data ss:Type='String'></Data></Cell>");}
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:Index='2' ss:StyleID='s76'><Data ss:Type='String'>二下</Data></Cell>");
			try{out.println ("    <Cell ss:MergeAcross='9' ss:StyleID='m1679007436520'><Data ss:Type='String'>"+score.get(3).get("com_name")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:MergeAcross='9' ss:StyleID='m1679007436520'><Data ss:Type='String'></Data></Cell>");}
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:Index='2' ss:StyleID='s76'><Data ss:Type='String'>三上</Data></Cell>");
			try{out.println ("    <Cell ss:MergeAcross='9' ss:StyleID='m1679007436520'><Data ss:Type='String'>"+score.get(4).get("com_name")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:MergeAcross='9' ss:StyleID='m1679007436520'><Data ss:Type='String'></Data></Cell>");}
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:Index='2' ss:StyleID='s76'><Data ss:Type='String'>三下</Data></Cell>");
			try{out.println ("    <Cell ss:MergeAcross='9' ss:StyleID='m1679007436520'><Data ss:Type='String'>"+score.get(5).get("com_name")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:MergeAcross='9' ss:StyleID='m1679007436520'><Data ss:Type='String'></Data></Cell>");}
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:Index='2' ss:StyleID='s76'><Data ss:Type='String'>四上</Data></Cell>");
			try{out.println ("    <Cell ss:MergeAcross='9' ss:StyleID='m1679007436520'><Data ss:Type='String'>"+score.get(6).get("com_name")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:MergeAcross='9' ss:StyleID='m1679007436520'><Data ss:Type='String'></Data></Cell>");}
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:Index='2' ss:StyleID='s76'><Data ss:Type='String'>四下</Data></Cell>");
			try{out.println ("    <Cell ss:MergeAcross='9' ss:StyleID='m1679007436520'><Data ss:Type='String'>"+score.get(7).get("com_name")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:MergeAcross='9' ss:StyleID='m1679007436520'><Data ss:Type='String'></Data></Cell>");}
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:Index='2' ss:StyleID='s76'><Data ss:Type='String'>五上</Data></Cell>");
			try{out.println ("    <Cell ss:MergeAcross='9' ss:StyleID='m1679007436520'><Data ss:Type='String'>"+score.get(8).get("com_name")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:MergeAcross='9' ss:StyleID='m1679007436520'><Data ss:Type='String'></Data></Cell>");}
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:Index='2' ss:StyleID='s76'><Data ss:Type='String'>五下</Data></Cell>");
			try{out.println ("    <Cell ss:MergeAcross='9' ss:StyleID='m1679007436520'><Data ss:Type='String'>"+score.get(9).get("com_name")+"</Data></Cell>");}catch(Exception e) {out.println ("    <Cell ss:MergeAcross='9' ss:StyleID='m1679007436520'><Data ss:Type='String'></Data></Cell>");}
			out.println ("   </Row>");
			out.println ("   <Row ss:Index='23' ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:StyleID='s87'><Data ss:Type='String'> </Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007436816'><Data ss:Type='String'>時      間</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007436836'><Data ss:Type='String'>類      別</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='6' ss:StyleID='m1679007436856'><Data ss:Type='String'>事                    由</Data></Cell>");
			out.println ("   </Row>");
			
			
			styleId="s94";
			for(int j=0; j<28; j++) {
				if(j==27)styleId="s95";
				out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
				
				switch(j) {
					case 4:out.println ("    <Cell ss:StyleID='"+styleId+"'><Data ss:Type='String'>獎</Data></Cell>");break;
					case 8:out.println ("    <Cell ss:StyleID='"+styleId+"'><Data ss:Type='String'>懲</Data></Cell>");break;
					case 12:out.println ("    <Cell ss:StyleID='"+styleId+"'><Data ss:Type='String'>記</Data></Cell>");break;
					case 16:out.println ("    <Cell ss:StyleID='"+styleId+"'><Data ss:Type='String'>錄</Data></Cell>");break;
					default:out.println ("    <Cell ss:StyleID='"+styleId+"'><Data ss:Type='String'></Data></Cell>");
				}
				
				try {
					c.setTime(   sf1.parse(comb2.get(j).get("ddate").toString() )    );
					c.add(Calendar.YEAR, -1911);		
					out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007441392'><Data ss:Type='String'>"+sf2.format(c.getTime())+"</Data></Cell>");
					if(comb2.get(j).get("cnt2")==null) {
						out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007441392'><Data ss:Type='String'>"+comb2.get(j).get("kind1Name")+","+comb2.get(j).get("cnt1")+"次</Data></Cell>");
					}else {
						out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007441392'><Data ss:Type='String'>"+comb2.get(j).get("kind1Name")+","+comb2.get(j).get("cnt1")+"次"+comb2.get(j).get("kind2Name")+","+comb2.get(j).get("cnt2")+"次</Data></Cell>");
					}	
					out.println ("    <Cell ss:MergeAcross='6' ss:StyleID='m1679007441392'><Data ss:Type='String'>"+comb2.get(j).get("reasonName")+"</Data></Cell>");
				}catch(Exception e) {
					e.printStackTrace();
					out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007441392'><Data ss:Type='String'></Data></Cell>");
					out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007441392'><Data ss:Type='String'></Data></Cell>");
					out.println ("    <Cell ss:MergeAcross='6' ss:StyleID='m1679007441392'><Data ss:Type='String'></Data></Cell>");
				}				
				out.println ("   </Row>");
			}
			/*
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:StyleID='s94'/>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007441392'><Data ss:Type='Number'>1</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007441412'><Data ss:Type='Number'>2</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='6' ss:StyleID='m1679007441432'><Data ss:Type='Number'>3</Data></Cell>");
			out.println ("   </Row>");
			
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:StyleID='s94'/>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007441452'><Data ss:Type='Number'>1</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007441472'><Data ss:Type='Number'>2</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='6' ss:StyleID='m1679007441492'><Data ss:Type='Number'>3</Data></Cell>");
			out.println ("   </Row>");
			
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:StyleID='s94'/>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007440976'><Data ss:Type='Number'>1</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007440996'><Data ss:Type='Number'>2</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='6' ss:StyleID='m1679007441016'><Data ss:Type='Number'>3</Data></Cell>");
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:StyleID='s94'/>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007441036'><Data ss:Type='Number'>1</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007441056'><Data ss:Type='Number'>2</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='6' ss:StyleID='m1679007441076'><Data ss:Type='Number'>3</Data></Cell>");
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:StyleID='s94'><Data ss:Type='String'>獎</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007443264'><Data ss:Type='Number'>1</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007443284'><Data ss:Type='Number'>2</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='6' ss:StyleID='m1679007443304'><Data ss:Type='Number'>3</Data></Cell>");
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:StyleID='s94'/>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007443324'><Data ss:Type='Number'>1</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007443344'><Data ss:Type='Number'>2</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='6' ss:StyleID='m1679007443364'><Data ss:Type='Number'>3</Data></Cell>");
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:StyleID='s94'/>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007441184'><Data ss:Type='Number'>1</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007441204'><Data ss:Type='Number'>2</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='6' ss:StyleID='m1679007441224'><Data ss:Type='Number'>3</Data></Cell>");
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:StyleID='s94'/>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007441244'><Data ss:Type='Number'>1</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007441264'><Data ss:Type='Number'>2</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='6' ss:StyleID='m1679007441284'><Data ss:Type='Number'>3</Data></Cell>");
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:StyleID='s94'><Data ss:Type='String'>懲</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007441808'><Data ss:Type='Number'>1</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007441828'><Data ss:Type='Number'>2</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='6' ss:StyleID='m1679007441848'><Data ss:Type='Number'>3</Data></Cell>");
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:StyleID='s94'/>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007441868'><Data ss:Type='Number'>1</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007441888'><Data ss:Type='Number'>2</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='6' ss:StyleID='m1679007441908'><Data ss:Type='Number'>3</Data></Cell>");
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:StyleID='s94'/>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007437024'><Data ss:Type='Number'>1</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007437044'><Data ss:Type='Number'>2</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='6' ss:StyleID='m1679007437064'><Data ss:Type='Number'>3</Data></Cell>");
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:StyleID='s94'/>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007437084'><Data ss:Type='Number'>1</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007437104'><Data ss:Type='Number'>2</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='6' ss:StyleID='m1679007437124'><Data ss:Type='Number'>3</Data></Cell>");
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:StyleID='s94'><Data ss:Type='String'>記</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007443680'><Data ss:Type='Number'>1</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007443700'><Data ss:Type='Number'>2</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='6' ss:StyleID='m1679007443720'><Data ss:Type='Number'>3</Data></Cell>");
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:StyleID='s94'/>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007443740'><Data ss:Type='Number'>1</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007443760'><Data ss:Type='Number'>2</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='6' ss:StyleID='m1679007443780'><Data ss:Type='Number'>3</Data></Cell>");
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:StyleID='s94'/>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007433488'><Data ss:Type='Number'>1</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007433508'><Data ss:Type='Number'>2</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='6' ss:StyleID='m1679007433528'><Data ss:Type='Number'>3</Data></Cell>");
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:StyleID='s94'/>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007433548'><Data ss:Type='Number'>1</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007433568'><Data ss:Type='Number'>2</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='6' ss:StyleID='m1679007433588'><Data ss:Type='Number'>3</Data></Cell>");
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:StyleID='s94'><Data ss:Type='String'>錄</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007432656'><Data ss:Type='Number'>1</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007432676'><Data ss:Type='Number'>2</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='6' ss:StyleID='m1679007432696'><Data ss:Type='Number'>3</Data></Cell>");
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:StyleID='s94'/>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007432716'><Data ss:Type='Number'>1</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007432736'><Data ss:Type='Number'>2</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='6' ss:StyleID='m1679007432756'><Data ss:Type='Number'>3</Data></Cell>");
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:StyleID='s94'/>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007432032'><Data ss:Type='Number'>1</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007432052'><Data ss:Type='Number'>2</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='6' ss:StyleID='m1679007432072'><Data ss:Type='Number'>3</Data></Cell>");
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:StyleID='s94'/>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007432092'><Data ss:Type='Number'>1</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007432112'><Data ss:Type='Number'>2</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='6' ss:StyleID='m1679007432132'><Data ss:Type='Number'>3</Data></Cell>");
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:StyleID='s94'/>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007438896'><Data ss:Type='Number'>1</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007438916'><Data ss:Type='Number'>2</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='6' ss:StyleID='m1679007438936'><Data ss:Type='Number'>3</Data></Cell>");
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:StyleID='s94'/>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007438956'><Data ss:Type='Number'>1</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007438976'><Data ss:Type='Number'>2</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='6' ss:StyleID='m1679007438996'><Data ss:Type='Number'>3</Data></Cell>");
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:StyleID='s94'/>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007432448'><Data ss:Type='Number'>1</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007432468'><Data ss:Type='Number'>2</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='6' ss:StyleID='m1679007432488'><Data ss:Type='Number'>3</Data></Cell>");
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:StyleID='s94'/>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007432508'><Data ss:Type='Number'>1</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007432528'><Data ss:Type='Number'>2</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='6' ss:StyleID='m1679007432548'><Data ss:Type='Number'>3</Data></Cell>");
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:StyleID='s94'/>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007438064'><Data ss:Type='Number'>1</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007438084'><Data ss:Type='Number'>2</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='6' ss:StyleID='m1679007438104'><Data ss:Type='Number'>3</Data></Cell>");
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:StyleID='s94'/>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007438124'><Data ss:Type='Number'>1</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007438144'><Data ss:Type='Number'>2</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='6' ss:StyleID='m1679007438164'><Data ss:Type='Number'>3</Data></Cell>");
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='15'>");
			out.println ("    <Cell ss:StyleID='s95'/>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007442016'><Data ss:Type='Number'>1</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='1' ss:StyleID='m1679007442036'><Data ss:Type='Number'>2</Data></Cell>");
			out.println ("    <Cell ss:MergeAcross='6' ss:StyleID='m1679007442056'><Data ss:Type='Number'>3</Data></Cell>");
			out.println ("   </Row>");
			*/
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='26.25'>");
			out.println ("    <Cell ss:StyleID='s67'/>");
			out.println ("    <Cell ss:MergeAcross='2' ss:StyleID='s98'><Data ss:Type='String'>單位主管</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s68'/>");
			out.println ("    <Cell ss:StyleID='s68'><Data ss:Type='String'>承辦人</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s68'/>");
			out.println ("    <Cell ss:StyleID='s68'/>");
			out.println ("    <Cell ss:StyleID='s68'><Data ss:Type='String'>列印日期:</Data></Cell>");
			
			Date d=new Date();
			c.setTime(d);
			c.add(Calendar.YEAR, -1911);
			out.println ("    <Cell ss:StyleID='s67'><Data ss:Type='String'>"+sf2.format(c.getTime())+"</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s67'/>");
			out.println ("    <Cell ss:StyleID='s67'/>");
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0'>");
			out.println ("    <Cell ss:Index='2' ss:MergeAcross='2'/>");
			out.println ("   </Row>");
			out.println ("  </Table>");
			out.println ("  <WorksheetOptions xmlns='urn:schemas-microsoft-com:office:excel'>");
			out.println ("   <PageSetup>");
			out.println ("    <PageMargins x:Bottom='0.54' x:Left='0.64' x:Right='0.61' x:Top='0.53'/>");
			out.println ("   </PageSetup>");
			out.println ("   <Unsynced/>");
			out.println ("   <Print>");
			out.println ("    <ValidPrinterInfo/>");
			out.println ("    <PaperSizeIndex>9</PaperSizeIndex>");
			out.println ("    <HorizontalResolution>600</HorizontalResolution>");
			out.println ("    <VerticalResolution>600</VerticalResolution>");
			out.println ("   </Print>");
			out.println ("   <Selected/>");
			out.println ("   <Panes>");
			out.println ("    <Pane>");
			out.println ("     <Number>3</Number>");
			out.println ("     <ActiveRow>28</ActiveRow>");
			out.println ("     <ActiveCol>5</ActiveCol>");
			out.println ("     <RangeSelection>R29C6:R29C12</RangeSelection>");
			out.println ("    </Pane>");
			out.println ("   </Panes>");
			out.println ("   <ProtectObjects>False</ProtectObjects>");
			out.println ("   <ProtectScenarios>False</ProtectScenarios>");
			out.println ("  </WorksheetOptions>");
			out.println (" </Worksheet>");
			
			
			
		}
		
		
		
		out.println ("</Workbook>");		
		out.close();
		out.flush();
		
		return null;
	}
}