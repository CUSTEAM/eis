package action.nabbr.print;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import action.BaseAction;

public class UseDetail extends BaseAction{
	
	private List<String>list;
	private String room_id;
	private String begin, end;
	
	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public List getList() {
		return list;
	}

	public String getRoom_id() {
		return room_id;
	}

	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}

	public String execute() throws IOException{
		
		if(request.getParameter("room_id")!=null){
			this.room_id=request.getParameter("room_id");
			this.begin=request.getParameter("begin");
			this.end=request.getParameter("end");
		}
		
		print(list, room_id, begin, end);
		
		return null;
	}
	
	public void print(List<String>list, String room_id, String begin, String end) throws IOException{
		
		if(list==null){
			list=new ArrayList();
			list.add(room_id);
		}
		
		Date date=new Date();
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition","attachment;filename="+date.getTime()+".xls");		
		
		PrintWriter out=response.getWriter();
		out.println ("<?xml version='1.0'?>");
		out.println ("<?mso-application progid='Excel.Sheet'?>");
		out.println ("<Workbook xmlns='urn:schemas-microsoft-com:office:spreadsheet'");
		out.println (" xmlns:o='urn:schemas-microsoft-com:office:office'");
		out.println (" xmlns:x='urn:schemas-microsoft-com:office:excel'");
		out.println (" xmlns:ss='urn:schemas-microsoft-com:office:spreadsheet'");
		out.println (" xmlns:html='http://www.w3.org/TR/REC-html40'>");
		out.println (" <DocumentProperties xmlns='urn:schemas-microsoft-com:office:office'>");
		out.println ("  <Author>shawn</Author>");
		out.println ("  <LastAuthor>shawn</LastAuthor>");
		out.println ("  <Created>2015-11-10T08:10:44Z</Created>");
		out.println ("  <Version>15.00</Version>");
		out.println (" </DocumentProperties>");
		out.println (" <OfficeDocumentSettings xmlns='urn:schemas-microsoft-com:office:office'>");
		out.println ("  <AllowPNG/>");
		out.println (" </OfficeDocumentSettings>");
		out.println (" <ExcelWorkbook xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("  <WindowHeight>11880</WindowHeight>");
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
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <NumberFormat ss:Format='@'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s63'>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='微軟正黑體' x:CharSet='136' x:Family='Swiss' ss:Size='12'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("   <NumberFormat ss:Format='@'/>");
		out.println ("  </Style>");
		out.println (" </Styles>");		
		
		for(int i=0; i<list.size(); i++){
			out.println (" <Worksheet ss:Name='"+list.get(i).replace("-", "")+"'>");
			out.println ("  <Names>");
			out.println ("   <NamedRange ss:Name='Print_Titles' ss:RefersTo='="+list.get(i).replace("-", "")+"!R1'/>");
			out.println ("  </Names>");
			out.println ("  <Table ss:ExpandedColumnCount='7' ss:ExpandedRowCount='2' x:FullColumns='1'");
			out.println ("   x:FullRows='1' ss:DefaultColumnWidth='54' ss:DefaultRowHeight='16.5'>");
			out.println ("   <Column ss:StyleID='s62' ss:AutoFitWidth='0' ss:Width='67.5'/>");
			out.println ("   <Column ss:StyleID='s62' ss:AutoFitWidth='0' ss:Width='60'/>");
			out.println ("   <Column ss:StyleID='s62' ss:AutoFitWidth='0' ss:Width='30'/>");
			out.println ("   <Column ss:StyleID='s62' ss:AutoFitWidth='0' ss:Width='120'/>");
			out.println ("   <Column ss:StyleID='s62' ss:AutoFitWidth='0' ss:Width='225'/>");
			out.println ("   <Column ss:StyleID='s62' ss:AutoFitWidth='0' ss:Width='30'/>");
			out.println ("   <Column ss:StyleID='s62' ss:AutoFitWidth='0' ss:Width='55.5'/>");
			out.println ("   <Row ss:AutoFitHeight='0'>");
			out.println ("    <Cell ss:StyleID='s63'><Data ss:Type='String'>日期</Data><NamedCell");
			out.println ("      ss:Name='Print_Titles'/></Cell>");
			out.println ("    <Cell ss:StyleID='s63'><Data ss:Type='String'>節次</Data><NamedCell");
			out.println ("      ss:Name='Print_Titles'/></Cell>");
			out.println ("    <Cell ss:StyleID='s63'><Data ss:Type='String'>時數</Data><NamedCell");
			out.println ("      ss:Name='Print_Titles'/></Cell>");
			out.println ("    <Cell ss:StyleID='s63'><Data ss:Type='String'>使用型態</Data><NamedCell");
			out.println ("      ss:Name='Print_Titles'/></Cell>");
			out.println ("    <Cell ss:StyleID='s63'><Data ss:Type='String'>名稱</Data><NamedCell");
			out.println ("      ss:Name='Print_Titles'/></Cell>");
			out.println ("    <Cell ss:StyleID='s63'><Data ss:Type='String'>人數</Data><NamedCell");
			out.println ("      ss:Name='Print_Titles'/></Cell>");
			out.println ("    <Cell ss:StyleID='s63'><Data ss:Type='String'>申請人</Data><NamedCell");
			out.println ("      ss:Name='Print_Titles'/></Cell>");
			out.println ("   </Row>");
			out.println ("   <Row ss:AutoFitHeight='0'>");
			out.println ("    <Cell ss:StyleID='s63'><Data ss:Type='String'>2015-12-31</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s63'><Data ss:Type='String'>2,3,4</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s63'><Data ss:Type='String'>3</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s63'><Data ss:Type='String'>授課</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s63'><Data ss:Type='String'>C十十程式設計</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s63'><Data ss:Type='Number'>25</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s63'><Data ss:Type='String'>蕭國裕</Data></Cell>");
			out.println ("   </Row>");
			out.println ("  </Table>");
			out.println ("  <WorksheetOptions xmlns='urn:schemas-microsoft-com:office:excel'>");
			out.println ("   <PageSetup>");
			out.println ("    <Header x:Margin='0.31496062992125984'");
			out.println ("     x:Data='&amp;L2014-12-31 至 2015-12-31&amp;C&amp;24 1B309教室使用記錄'/>");
			out.println ("    <Footer x:Margin='0.31496062992125984' x:Data='&amp;C&amp;P/&amp;N&amp;R&amp;D &amp;T'/>");
			out.println ("    <PageMargins x:Bottom='0.74803149606299213' x:Left='0.23622047244094491'");
			out.println ("     x:Right='0.23622047244094491' x:Top='0.74803149606299213'/>");
			out.println ("   </PageSetup>");
			out.println ("   <Unsynced/>");
			out.println ("   <Print>");
			out.println ("    <ValidPrinterInfo/>");
			out.println ("    <PaperSizeIndex>9</PaperSizeIndex>");
			out.println ("    <HorizontalResolution>-1</HorizontalResolution>");
			out.println ("    <VerticalResolution>-1</VerticalResolution>");
			out.println ("   </Print>");
			out.println ("   <Selected/>");
			out.println ("   <Panes>");
			out.println ("    <Pane>");
			out.println ("     <Number>3</Number>");
			out.println ("     <RangeSelection>R1</RangeSelection>");
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
	}

}
