package action.eis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import action.BaseAction;
import model.Message;

public class StdScoreViewerAction extends BaseAction{
	
	public File fileUpload;
    public String student_no;	
	
    private List<Map>stds;
	public String execute(){		
		return SUCCESS;
	}
	
	public String print() throws IOException{
		
		Message msg=new Message();
		if(student_no.indexOf(",")<0&&fileUpload==null){
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
    		   msg.setMsg(upload());
    		   this.savMessage(msg);
    		   //return SUCCESS;
    	   	}catch(Exception e){
    		   msg.setMsg("文件格式有誤");
    		   this.savMessage(msg);
    		   return SUCCESS;
    	   	}  
		}
		
		Date date=new Date();
		response.setContentType("text/html; charset=UTF-8");response.setContentType("application/vnd.ms-excel");response.setHeader("Content-disposition","attachment;filename="+date.getTime()+".xls");		
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
		out.println ("  <Table ss:ExpandedColumnCount='29' ss:ExpandedRowCount='"+(stds.size()+100)+"' x:FullColumns='1'");
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
		out.println ("    <Cell><Data ss:Type='String'>第2學期學業</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第2學期操行</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第2學期名次</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第3學期學業</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第3學期操行</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第3學期名次</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第4學期學業</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第4學期操行</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第4學期名次</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第5學期學業</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第5學期操行</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第5學期名次</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第6學期學業</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第6學期操行</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第6學期名次</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第7學期學業</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第7學期操行</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第7學期名次</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第8學期學業</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第8學期操行</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>第8學期名次</Data></Cell>");
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
					out.println ("    <Cell></Cell>");
				}
				
				if(stds.get(i).get("score1"+j)!=null){
					out.println ("    <Cell><Data ss:Type='Number'>"+stds.get(i).get("score1"+j)+"</Data></Cell>");
				}else{
					out.println ("    <Cell></Cell>");
				}	
				
				if(stds.get(i).get("rank"+j)!=null && !stds.get(i).get("rank"+j).equals("")){
					out.println ("    <Cell><Data ss:Type='Number'>"+stds.get(i).get("rank"+j)+"</Data></Cell>");
				}else{
					out.println ("    <Cell></Cell>");
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
	
	private String upload() throws IOException{
		
		
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
		
		List<Map>s=df.sqlGet("SELECT s.rank, s.score, c.score as score1 FROM Stavg s LEFT OUTER JOIN cond c ON "
		+ "s.student_no=c.student_no AND s.school_year=c.school_year AND s.school_term=c.school_term WHERE "
		+ "s.student_no='"+stdNo+"'ORDER BY s.school_year, s.school_term");
		
		Map m=df.sqlGetMap("SELECT csi.name, IF(s.sex='1','男' ,'女')as sex, c.ClassName, s.student_no, "
		+ "s.student_name FROM Class c, stmd s LEFT OUTER JOIN CODE_STMD_IDENT csi ON s.ident=csi.id WHERE "
		+ "c.ClassNo=s.depart_class AND s.student_no='"+stdNo+"'");
		for(int i=0; i<8; i++){
			
			if(i+1<=s.size()){
				m.put("rank"+(i+1), s.get(i).get("rank"));
				m.put("score"+(i+1), s.get(i).get("score"));
				m.put("score1"+(i+1), s.get(i).get("score1"));
			}else{
				m.put("rank"+(i+1), "");
				m.put("score"+(i+1), "");
				m.put("score1"+(i+1), "");
			}			
		}		
		return m;
	}
}