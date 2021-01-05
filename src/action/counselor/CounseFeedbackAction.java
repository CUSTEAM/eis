package action.counselor;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import action.BaseAction;
import action.BasePrintXmlAction;
import model.Counselor_msg;
import model.Counselor_stmd;
import model.Message;

public class CounseFeedbackAction extends BasePrintXmlAction{
	
	public String beginDate, endDate;
	
	public String Oid, student_name, cell_phone, SchoolNo, DeptNo, add_time, note, reply;
	
	
	private List GetDepts() {	
		
		return df.sqlGet("SELECT * FROM Counselor_charge WHERE idno='"+getSession().getAttribute("userid")+"'");
	}
	
	public String execute() {	
		List<Map>dept=GetDepts();
		if(dept.size()==0) {
			Message msg=new Message();
			msg.setError("無負責科系");
			this.savMessage(msg);
			return SUCCESS;
		}
		StringBuilder sql=new StringBuilder("SELECT(SELECT COUNT(*)FROM Counselor_msg WHERE stmd_oid=s.Oid)as cnt,"
		+ "d.name as DeptName,s.* FROM Counselor_stmd s, CODE_DEPT d WHERE s.DeptNo=d.id AND s.DeptNo IN(");
		for(int i=0; i<dept.size(); i++) {
			sql.append("'"+dept.get(i).get("DeptNo")+"',");
		}
		sql.delete(sql.length()-1, sql.length());
		sql.append(")");			
		request.setAttribute("stds", df.sqlGet(sql.toString()));		
		return SUCCESS;
	}
	
	private List<Map> find(List<Map>dept){
		StringBuilder sql=new StringBuilder("SELECT(SELECT COUNT(*)FROM Counselor_msg WHERE stmd_oid=s.Oid)as cnt,"
				+ "d.name as DeptName,s.* FROM Counselor_stmd s, CODE_DEPT d WHERE s.DeptNo=d.id AND s.DeptNo IN(");
				for(int i=0; i<dept.size(); i++) {
					sql.append("'"+dept.get(i).get("DeptNo")+"',");
				}
				sql.delete(sql.length()-1, sql.length());
				sql.append(")");
				
				if(beginDate!=null)
				if(!beginDate.equals("")) {
					sql.append("AND add_time>='"+beginDate+"'");
				}
				if(endDate!=null)
				if(!endDate.equals("")) {
					sql.append("AND add_time<='"+endDate+"'");
				}
		
		return df.sqlGet(sql.toString());
	}
	
	public String search() {
		List<Map>dept=GetDepts();
		if(dept.size()==0) {
			Message msg=new Message();
			msg.setError("無負責科系");
			this.savMessage(msg);
			return SUCCESS;
		}
		request.setAttribute("stds", find(dept));
		return SUCCESS;		
	}
	
	public String edit() {
		
		request.setAttribute("info", df.sqlGetMap("SELECT s.*, cs.name as DeptName FROM Counselor_stmd s LEFT OUTER JOIN CODE_DEPT cs ON cs.id=s.SchoolNo WHERE s.Oid="+Oid));
		request.setAttribute("ms", df.sqlGet("SELECT*FROM Counselor_msg WHERE stmd_oid="+Oid+" ORDER BY Oid DESC"));
		
		return SUCCESS;
	}
	
	public String save() {
		Counselor_stmd s;
		s=(Counselor_stmd) df.hqlGetListBy("FROM Counselor_stmd WHERE Oid="+Oid).get(0);		
		s.setReply(reply);
		//s.setNote(note);
		df.update(s);
		
		Counselor_msg m=new Counselor_msg();
		m.setStmd_oid(Integer.parseInt(Oid));
		m.setName(df.sqlGetStr("SELECT cname FROM empl WHERE idno='"+getSession().getAttribute("userid")+"'"));
		m.setNote(note);
		df.update(m);
		
		request.setAttribute("info", df.sqlGetMap("SELECT s.*, cs.name as DeptName FROM Counselor_stmd s LEFT OUTER JOIN CODE_DEPT cs ON cs.id=s.SchoolNo WHERE s.Oid="+Oid));
		request.setAttribute("ms", df.sqlGet("SELECT*FROM Counselor_msg WHERE stmd_oid="+Oid +" ORDER BY Oid DESC"));
		return edit();
	}
	
	public String print() throws IOException {
		
		List<Map>dept=GetDepts();
		if(dept.size()==0) {
			Message msg=new Message();
			msg.setError("無負責科系");
			this.savMessage(msg);
			return SUCCESS;
		}
		List<Map>stds=find(dept);
		
		Date date=new Date();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		xml2ods(response, getRequest(), date);
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
		out.println ("  <Created>2021-01-05T03:51:13Z</Created>");
		out.println ("  <Version>15.00</Version>");
		out.println (" </DocumentProperties>");
		out.println (" <OfficeDocumentSettings xmlns='urn:schemas-microsoft-com:office:office'>");
		out.println ("  <AllowPNG/>");
		out.println (" </OfficeDocumentSettings>");
		out.println (" <ExcelWorkbook xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("  <WindowHeight>11325</WindowHeight>");
		out.println ("  <WindowWidth>29010</WindowWidth>");
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
		out.println ("  <Style ss:ID='s73'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <NumberFormat ss:Format='@'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s77'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <NumberFormat ss:Format='@'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s78'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <NumberFormat ss:Format='@'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s79'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <NumberFormat ss:Format='@'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s116'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='12'");
		out.println ("    ss:Color='#FFFFFF' ss:Bold='1'/>");
		out.println ("   <Interior ss:Color='#404040' ss:Pattern='Solid'/>");
		out.println ("   <NumberFormat ss:Format='@'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s117'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='12'");
		out.println ("    ss:Color='#FFFFFF' ss:Bold='1'/>");
		out.println ("   <Interior ss:Color='#404040' ss:Pattern='Solid'/>");
		out.println ("   <NumberFormat ss:Format='@'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s118'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='12'");
		out.println ("    ss:Color='#FFFFFF' ss:Bold='1'/>");
		out.println ("   <Interior ss:Color='#404040' ss:Pattern='Solid'/>");
		out.println ("   <NumberFormat ss:Format='@'/>");
		out.println ("  </Style>");
		out.println (" </Styles>");
		out.println (" <Worksheet ss:Name='工作表1'>");
		out.println ("  <Table ss:ExpandedColumnCount='10' ss:ExpandedRowCount='"+(stds.size()*33)+"' x:FullColumns='1'");
		out.println ("   x:FullRows='1' ss:StyleID='s73' ss:DefaultColumnWidth='54'");
		out.println ("   ss:DefaultRowHeight='16.5'>");
		out.println ("   <Column ss:StyleID='s77' ss:AutoFitWidth='0' ss:Width='80.25'/>");
		out.println ("   <Column ss:StyleID='s78' ss:AutoFitWidth='0' ss:Width='106.5'/>");
		out.println ("   <Column ss:StyleID='s78' ss:AutoFitWidth='0'/>");
		out.println ("   <Column ss:StyleID='s78' ss:AutoFitWidth='0' ss:Width='134.25'/>");
		out.println ("   <Column ss:StyleID='s78' ss:AutoFitWidth='0' ss:Span='1'/>");
		out.println ("   <Column ss:Index='7' ss:StyleID='s78' ss:AutoFitWidth='0' ss:Width='69.75'/>");
		out.println ("   <Column ss:StyleID='s78' ss:AutoFitWidth='0' ss:Width='114.75'/>");
		out.println ("   <Column ss:StyleID='s78' ss:AutoFitWidth='0' ss:Width='80.25'/>");
		out.println ("   <Column ss:StyleID='s79' ss:AutoFitWidth='0' ss:Width='160.5'/>");
		out.println ("   <Row>");
		out.println ("    <Cell ss:StyleID='s116'><Data ss:Type='String'>狀態</Data></Cell>");
		out.println ("    <Cell ss:StyleID='s117'><Data ss:Type='String'>申請日期 </Data></Cell>");
		out.println ("    <Cell ss:StyleID='s117'><Data ss:Type='String'>連繫 </Data></Cell>");
		out.println ("    <Cell ss:StyleID='s117'><Data ss:Type='String'>科系 </Data></Cell>");
		out.println ("    <Cell ss:StyleID='s117'><Data ss:Type='String'>學制 </Data></Cell>");
		out.println ("    <Cell ss:StyleID='s117'><Data ss:Type='String'>姓名 </Data></Cell>");
		out.println ("    <Cell ss:StyleID='s117'><Data ss:Type='String'>電話 </Data></Cell>");
		out.println ("    <Cell ss:StyleID='s117'><Data ss:Type='String'>備註</Data></Cell>");
		out.println ("    <Cell ss:StyleID='s117'><Data ss:Type='String'>連絡日期</Data></Cell>");
		out.println ("    <Cell ss:StyleID='s118'><Data ss:Type='String'>連絡記錄</Data></Cell>");
		out.println ("   </Row>");
		
		
		for(int i=0; i<stds.size(); i++) {			
			stds.get(i).put("cnts", df.sqlGet("SELECT*FROM Counselor_msg WHERE stmd_oid='"+stds.get(i).get("Oid")+"'"));
			
		}
		
		
		
		List<Map>cnts;
		
		for(int i=0; i<stds.size(); i++) {
			
			out.println ("   <Row>");
			
			switch(String.valueOf(stds.get(i).get("reply"))) { 		            
            case "Y": 
            	out.println ("    <Cell><Data ss:Type='String'>有意願</Data></Cell>");
                break; 
            case "N": 
            	out.println ("    <Cell><Data ss:Type='String'>無意願</Data></Cell>");
                break; 
            default: 
            	out.println ("    <Cell><Data ss:Type='String'>未決定</Data></Cell>");
			}	
			out.println ("    <Cell><Data ss:Type='String'>"+stds.get(i).get("add_time")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+stds.get(i).get("cnt")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+stds.get(i).get("DeptName")+" </Data></Cell>");
			
			switch(String.valueOf(stds.get(i).get("SchoolNo"))) { 		            
            case "2": 
            	out.println ("    <Cell><Data ss:Type='String'>二專 </Data></Cell>");
                break; 
            case "B2": 
            	out.println ("    <Cell><Data ss:Type='String'>四技 </Data></Cell>");
                break; 
            case "C": 
            	out.println ("    <Cell><Data ss:Type='String'>二技 </Data></Cell>");
                break; 
            case "M": 
            	out.println ("    <Cell><Data ss:Type='String'>碩士 </Data></Cell>");
                break; 
            default: 
            	out.println ("    <Cell><Data ss:Type='String'>未指定 </Data></Cell>");
			}
			out.println ("    <Cell><Data ss:Type='String'>"+stds.get(i).get("student_name")+" </Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+stds.get(i).get("cell_phone")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+stds.get(i).get("note")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'></Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'></Data></Cell>");
			out.println ("   </Row>");
			
			
			
			
			try {
				cnts=(List<Map>)stds.get(i).get("cnts");
				if(cnts.size()>0)
				for(int j=0; j<cnts.size(); j++) {
					out.println ("   <Row>");
					
					switch(String.valueOf(stds.get(i).get("reply"))) { 		            
		            case "Y": 
		            	out.println ("    <Cell><Data ss:Type='String'>有意願</Data></Cell>");
		                break; 
		            case "N": 
		            	out.println ("    <Cell><Data ss:Type='String'>無意願</Data></Cell>");
		                break; 
		            default: 
		            	out.println ("    <Cell><Data ss:Type='String'>未決定</Data></Cell>");
					}	
					out.println ("    <Cell><Data ss:Type='String'>"+stds.get(i).get("add_time")+"</Data></Cell>");
					out.println ("    <Cell><Data ss:Type='Number'>"+stds.get(i).get("cnt")+"</Data></Cell>");
					out.println ("    <Cell><Data ss:Type='String'>"+stds.get(i).get("DeptName")+" </Data></Cell>");
					
					switch(String.valueOf(stds.get(i).get("SchoolNo"))) { 		            
		            case "2": 
		            	out.println ("    <Cell><Data ss:Type='String'>二專 </Data></Cell>");
		                break; 
		            case "B2": 
		            	out.println ("    <Cell><Data ss:Type='String'>四技 </Data></Cell>");
		                break; 
		            case "C": 
		            	out.println ("    <Cell><Data ss:Type='String'>二技 </Data></Cell>");
		                break; 
		            case "M": 
		            	out.println ("    <Cell><Data ss:Type='String'>碩士 </Data></Cell>");
		                break; 
		            default: 
		            	out.println ("    <Cell><Data ss:Type='String'>未指定 </Data></Cell>");
					}
					out.println ("    <Cell><Data ss:Type='String'>"+stds.get(i).get("student_name")+" </Data></Cell>");
					out.println ("    <Cell><Data ss:Type='Number'>"+stds.get(i).get("cell_phone")+"</Data></Cell>");
					out.println ("    <Cell><Data ss:Type='String'>"+stds.get(i).get("note")+"</Data></Cell>");
					out.println ("    <Cell><Data ss:Type='String'>"+cnts.get(j).get("editDate")+"</Data></Cell>");
					out.println ("    <Cell><Data ss:Type='String'>"+cnts.get(j).get("name")+":"+cnts.get(j).get("note")+"</Data></Cell>");
					out.println ("   </Row>");
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			
			
			
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
		
		out.close();
		out.flush();
		
		
		return null;
	}

}
