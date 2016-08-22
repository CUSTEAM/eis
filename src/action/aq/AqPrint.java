package action.aq;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import action.BaseAction;

public class AqPrint extends BaseAction{
	
	
	public String execute() throws IOException{
		
		Date date=new Date();
		//response.setContentType("text/html; charset=UTF-8");
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
		out.println ("  <Created>2016-05-11T03:51:34Z</Created>");
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
		out.println ("  <Style ss:ID='s63'>");
		out.println ("   <Font ss:FontName='微軟正黑體' x:CharSet='136' x:Family='Swiss' ss:Size='12'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("   <NumberFormat ss:Format='@'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s64'>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='微軟正黑體' x:CharSet='136' x:Family='Swiss' ss:Size='12'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("   <NumberFormat ss:Format='@'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s65'>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='微軟正黑體' x:CharSet='136' x:Family='Swiss' ss:Size='12'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("   <NumberFormat ss:Format='@'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s66'>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='微軟正黑體' x:CharSet='136' x:Family='Swiss' ss:Size='12'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("   <NumberFormat ss:Format='@'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s67'>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='微軟正黑體' x:CharSet='136' x:Family='Swiss' ss:Size='12'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("   <NumberFormat ss:Format='@'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s68'>");
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
		out.println ("  <Style ss:ID='s69'>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='微軟正黑體' x:CharSet='136' x:Family='Swiss' ss:Size='12'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("   <NumberFormat ss:Format='@'/>");
		out.println ("  </Style>");
		out.println (" </Styles>");
		
		
		
		List<Map>list=df.sqlGet("SELECT e.cname,a.* FROM AQ_anser a, empl e WHERE a.idno=e.idno AND a.anser!=''ORDER BY a.uid, a.uid2");
		
		out.println (" <Worksheet ss:Name='工作表1'>");
		out.println ("  <Names>");
		out.println ("   <NamedRange ss:Name='Print_Titles' ss:RefersTo='=工作表1!R1'/>");
		out.println ("  </Names>");
		out.println ("  <Table ss:ExpandedColumnCount='7' ss:ExpandedRowCount='"+(list.size()+100)+"' x:FullColumns='1'");
		out.println ("   x:FullRows='1' ss:StyleID='s63' ss:DefaultColumnWidth='54'");
		out.println ("   ss:DefaultRowHeight='15.75'>");
		out.println ("   <Column ss:StyleID='s67' ss:AutoFitWidth='0'/>");
		out.println ("   <Column ss:StyleID='s68' ss:AutoFitWidth='0' ss:Width='64.5' ss:Span='1'/>");
		out.println ("   <Column ss:Index='4' ss:StyleID='s68' ss:AutoFitWidth='0' ss:Span='2'/>");
		out.println ("   <Column ss:Index='7' ss:StyleID='s69' ss:AutoFitWidth='0' ss:Width='233.25'/>");
		out.println ("   <Row>");
		out.println ("    <Cell ss:StyleID='s64'><Data ss:Type='String'>填寫者</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s65'><Data ss:Type='String'>問卷編號1</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s65'><Data ss:Type='String'>問卷編號2</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s65'><Data ss:Type='String'>兼任行政</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s65'><Data ss:Type='String'>所屬單位</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s65'><Data ss:Type='String'>年資</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s66'><Data ss:Type='String'>問卷答案</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("   </Row>");
		
		
		
		for(int i=0; i<list.size(); i++){
			
			out.println ("   <Row>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("cname")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("uid")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("uid2")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("category")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("unit")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("years")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("anser")+"</Data></Cell>");
			out.println ("   </Row>");
			
		}
		
		out.println ("  ");
		out.println ("  </Table>");
		out.println ("  <WorksheetOptions xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("   <PageSetup>");
		out.println ("    <Header x:Margin='0.31496062992125984' x:Data='&amp;C&amp;&quot;微軟正黑體,標準&quot;&amp;20行政滿意度問卷列表'/>");
		out.println ("    <Footer x:Margin='0.31496062992125984'/>");
		out.println ("    <PageMargins x:Bottom='0.74803149606299213' x:Left='0.23622047244094491'");
		out.println ("     x:Right='0.23622047244094491' x:Top='0.74803149606299213'/>");
		out.println ("   </PageSetup>");
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
		out.println ("     <ActiveRow>15</ActiveRow>");
		out.println ("     <ActiveCol>6</ActiveCol>");
		out.println ("    </Pane>");
		out.println ("   </Panes>");
		out.println ("   <ProtectObjects>False</ProtectObjects>");
		out.println ("   <ProtectScenarios>False</ProtectScenarios>");
		out.println ("  </WorksheetOptions>");
		out.println (" </Worksheet>");
		out.println ("</Workbook>");
		out.println ("");
		
		
		
		
		
		
		out.flush();
		out.close();
		
		
		return null;
	}

}
