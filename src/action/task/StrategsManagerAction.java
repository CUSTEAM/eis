package action.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
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
import action.BasePrintXmlAction;
import model.Message;
import model.PRJ;

public class StrategsManagerAction extends BasePrintXmlAction{
	
	private void setMenu() {
		List<Map>unit=df.sqlGet("SELECT u.id, u.name FROM CODE_UNIT u WHERE u.pid='0' AND u.service='1' ORDER BY u.name");
		for(int i=0; i<unit.size(); i++) {
			unit.get(i).put("units", df.sqlGet("SELECT u.id, u.name FROM CODE_UNIT u WHERE pid='"+unit.get(i).get("id")+"'"));
			
		}
		request.setAttribute("allUnit", unit);
	}
	
	public String execute() {
		
		setMenu();
		return SUCCESS;
	}
	
	public File upload;
	public String year, codeUnit;
	
	
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
		//boolean b=false;
		//String a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w;
		Date now=new Date();
		PRJ p;
		df.exSql("DELETE FROM PRJ WHERE year='"+year+"' AND codeUnit='"+codeUnit+"'");
		for (int x = sheet.getFirstRowNum()+1; x <= sheet.getPhysicalNumberOfRows(); x++) {				
			row = sheet.getRow(x);
			if(row==null)continue;
			try{
				if(readCellAsString(row.getCell(0))=="")continue;
				p=new PRJ();
				
				p.setYear(year);
				p.setCodeUnit(codeUnit);
				p.setUsername(df.sqlGetStr("SELECT cname FROM empl WHERE idno='"+getSession().getAttribute("userid")+"'"));
				p.setItemNo(readCellAsString(row.getCell(0)));
				p.setStrategy(readCellAsString(row.getCell(1)));
				p.setItems(readCellAsString(row.getCell(2)));
				p.setTarget_hope(readCellAsString(row.getCell(3)));
				p.setUnit(readCellAsString(row.getCell(4)));
				p.setKeyman(readCellAsString(row.getCell(5)));
				
				p.setHope_q1(readCellAsString(row.getCell(6)));
				p.setReal_q1(readCellAsString(row.getCell(7)));
				p.setRatio_q1(readCellAsString(row.getCell(8)));
				
				p.setHope_q2(readCellAsString(row.getCell(9)));
				p.setReal_q2(readCellAsString(row.getCell(10)));
				p.setRatio_q2(readCellAsString(row.getCell(11)));
				
				p.setHope_q3(readCellAsString(row.getCell(12)));
				p.setReal_q3(readCellAsString(row.getCell(13)));
				p.setRatio_q3(readCellAsString(row.getCell(14)));
				
				p.setHope_q4(readCellAsString(row.getCell(15)));
				p.setReal_q4(readCellAsString(row.getCell(16)));
				p.setRatio_q4(readCellAsString(row.getCell(17)));
				
				/*out.println ("    <Cell><Data ss:Type='String'>"+prj.get(j).get("target_real")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+prj.get(j).get("help_all")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+prj.get(j).get("real_all")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+prj.get(j).get("ratio_all")+"</Data></Cell>");
				
				out.println ("    <Cell><Data ss:Type='String'>"+prj.get(j).get("category")+"</Data></Cell>");*/
				
				
				p.setTarget_real(readCellAsString(row.getCell(18)));
				p.setHope_all(readCellAsString(row.getCell(19)));
				p.setReal_all(readCellAsString(row.getCell(20)));
				
				p.setRatio_all(readCellAsString(row.getCell(21)));
				p.setCategory(readCellAsString(row.getCell(22)));
				p.setPath("PRJ/"+now.getTime()+"xlsx");
				p.setEditdate(now);
				
				
				
				df.update(p);
				check++;
			}catch(Exception ex){
				//ex.printStackTrace();
				msg.addError("略過第"+(x+1)+"行, 因為該行在儲存中發生錯誤");
				continue;
			}										
						
		}
		
		fis.close();
		//request.setAttribute("complete", complete);
		//request.setAttribute("fail", fail);
		msg.setSuccess("文件匯入完成, 共"+check+"筆資料");
		this.savMessage(msg);
		return search();
	}
	
	private List getPRJs() {
		
		StringBuilder sql=new StringBuilder("SELECT COUNT(*)as cnt, p.codeUnit, u.name, p.year, p.editdate, p.username FROM PRJ p, CODE_UNIT u WHERE p.codeUnit=u.id ");
		if(!codeUnit.equals(""))sql.append("AND p.codeUnit='"+codeUnit+"'");
		if(!year.equals(""))sql.append("AND p.year='"+year+"'");
		sql.append("GROUP BY p.year, p.codeUnit ORDER BY p.codeUnit, p.year DESC");
		return df.sqlGet(sql.toString());
	}
	
	public String search() {
		
		request.setAttribute("prjs", getPRJs());
		
		setMenu();		
		return SUCCESS;
	}
	
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

	public String print() throws IOException {
		
		Date date=new Date();
		response.setContentType("text/html; charset=UTF-8");
		xml2ods(response, getRequest(), date);
		List<Map>prjs=getPRJs();
		
		PrintWriter out=response.getWriter();
		
		
				
		
		
		List<Map>list=getPRJs();
		
		
		
		
		
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
		out.println ("  <Created>2019-06-11T08:37:59Z</Created>");
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
		out.println ("  <Style ss:ID='s62'>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='微軟正黑體' x:CharSet='136' x:Family='Swiss' ss:Size='16'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("  </Style>");
		out.println (" </Styles>");
		
		
		
		
		List<Map>prj;
		for(int i=0; i<list.size(); i++) {
			
			out.println (" <Worksheet ss:Name='"+list.get(i).get("year")+"-"+list.get(i).get("name")+"'>");
			out.println ("  <Table ss:ExpandedColumnCount='23' ss:ExpandedRowCount='999' x:FullColumns='1'");
			out.println ("   x:FullRows='1' ss:DefaultColumnWidth='54' ss:DefaultRowHeight='16.5'>");
			out.println ("   <Column ss:AutoFitWidth='0' ss:Width='90'/>");
			out.println ("   <Column ss:AutoFitWidth='0' ss:Width='225' ss:Span='1'/>");
			out.println ("   <Column ss:Index='4' ss:Width='144.75'/>");
			out.println ("   <Column ss:Width='75'/>");
			out.println ("   <Column ss:Width='179.25'/>");
			out.println ("   <Column ss:Width='120'/>");
			out.println ("   <Column ss:Width='116.25'/>");
			out.println ("   <Column ss:Width='63.75'/>");
			out.println ("   <Column ss:Width='120'/>");
			out.println ("   <Column ss:Width='116.25'/>");
			out.println ("   <Column ss:Width='63.75'/>");
			out.println ("   <Column ss:Width='120'/>");
			out.println ("   <Column ss:Width='116.25'/>");
			out.println ("   <Column ss:Width='63.75'/>");
			out.println ("   <Column ss:Width='120'/>");
			out.println ("   <Column ss:Width='116.25'/>");
			out.println ("   <Column ss:Width='179.25' ss:Span='1'/>");
			out.println ("   <Column ss:Index='20' ss:Width='92.25'/>");
			out.println ("   <Column ss:Width='127.5'/>");
			out.println ("   <Column ss:Width='75'/>");
			out.println ("   <Row ss:Height='21' ss:StyleID='s62'>");
			out.println ("    <Cell><Data ss:Type='String'>項次</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>發展策略</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>行動目標重點工作項目</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>預定達成績效指標</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>績效單位</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>該【項次】業務承辦人</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>第1季績效指標</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>Q1實際達成值</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>Q1成效</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>第2季績效指標</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>Q2實際達成值</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>Q2成效</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>第3季績效指標</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>Q3實際達成值</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>Q3成效</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>第4季績效指標</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>Q4實際達成值</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>Q4成效</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>檢視預定達成績效指標</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>學年實際達成績效指標</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>完成百分比</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>學年績效達成率</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>項目類型</Data></Cell>");
			out.println ("   </Row>");
			
			
			
			prj=df.sqlGet("SELECT*FROM PRJ WHERE year='"+list.get(i).get("year")+"' AND codeUnit='"+list.get(i).get("codeUnit")+"'");
			for(int j=0; j<prj.size(); j++) {
				
				
				out.println ("   <Row ss:Height='21' ss:StyleID='s62'>");
				out.println ("    <Cell><Data ss:Type='String'>"+prj.get(j).get("itemNo")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+prj.get(j).get("strategy")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+prj.get(j).get("items")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+prj.get(j).get("target_hope")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+prj.get(j).get("unit")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+prj.get(j).get("username")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+prj.get(j).get("hope_q1")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+prj.get(j).get("real_q1")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+prj.get(j).get("ratio_q1")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+prj.get(j).get("hope_q2")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+prj.get(j).get("real_q2")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+prj.get(j).get("ratio_q2")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+prj.get(j).get("hope_q3")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+prj.get(j).get("real_q3")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+prj.get(j).get("ratio_q3")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+prj.get(j).get("hope_q4")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+prj.get(j).get("real_q4")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+prj.get(j).get("ratio_q4")+"</Data></Cell>");
				
				
				out.println ("    <Cell><Data ss:Type='String'>"+prj.get(j).get("target_real")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+prj.get(j).get("hope_all")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+prj.get(j).get("real_all")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+prj.get(j).get("ratio_all")+"</Data></Cell>");
				
				out.println ("    <Cell><Data ss:Type='String'>"+prj.get(j).get("category")+"</Data></Cell>");
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
			out.println ("     <ActiveRow>5</ActiveRow>");
			out.println ("     <ActiveCol>1</ActiveCol>");
			out.println ("    </Pane>");
			out.println ("   </Panes>");
			out.println ("   <ProtectObjects>False</ProtectObjects>");
			out.println ("   <ProtectScenarios>False</ProtectScenarios>");
			out.println ("  </WorksheetOptions>");
			out.println (" </Worksheet>");
			
			
			
			
			
			
		}
		
		
		
		
		
		
		
		
		
		out.println ("</Workbook>");
		out.println ("");
		
		
		
		
		
			
		out.close();
		out.flush();
		
		
		return null;
	}
}
