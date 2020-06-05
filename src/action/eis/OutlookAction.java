package action.eis;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import action.BasePrintXmlAction;

/**
 * 圖表展示
 * @author shawn
 * @category TODO: 核心能力指標
 * ex: data:{cno:$("#").val(), stno:$("#").val(), sno:$("#").val(), cono:$("#").val(), dno:$("#").val(), gno:$("#").val(), zno:$("#").val()},
 */
public class OutlookAction extends BasePrintXmlAction{
	
	public String execute(){
		
		//併班
		if(request.getParameter("view").equals("merge")){			
			merge();
			return "merge";
		}
		
		//缺曠
		if(request.getParameter("view").equals("dilg")){			
			dilg();
			return "dilg";
		}
		
		//居住地
		if(request.getParameter("view").equals("stdaddr")){
			stdaddr();			
			return "stdaddr";
		}
		
		//生源
		if(request.getParameter("view").equals("stdfrom")){
			stdfrom();			
			return "stdfrom";
		}
		
		//流失
		if(request.getParameter("view").equals("stdlost")){
			stdlost();			
			return "stdlost";
		}
		
		if(request.getParameter("view").equals("desd"))return "desd";
		
		
		return SUCCESS;
	}
	
	//併班
	private void merge(){
		
		request.setAttribute("merge", df.sqlGet("SELECT cd.name, SUM(d.pid)as cnt FROM CODE_DEPT cd, Class c, Dtime d WHERE d.depart_class=c.ClassNo AND c.DeptNo=cd.id AND d.pid IS NOT NULL GROUP BY cd.id"));
		
	}
	
	//流失
	private void stdlost(){
		
	}
	
	//居住地
	private void stdaddr(){
		List<Map>tmp;
		JSONObject json;
		List<Map>col=df.sqlGet("SELECT id, name FROM CODE_COLLEGE");
		/*for(int i=0; i<col.size(); i++){
			tmp=df.sqlGet("SELECT s.geocode FROM stmd s, Class c WHERE s.depart_class=c.ClassNo AND s.geocode LIKE '%lat%' AND c.InstNo='"+col.get(i).get("id")+"'");
			for(int j=0; j<tmp.size(); j++){
				json=new JSONObject(tmp.get(j).get("geocode").toString());
				try{
					tmp.get(j).put("lat", json.get("lat"));
					tmp.get(j).put("lng", json.get("lng"));
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}			
			col.get(i).put("stds", tmp);			
		}*/
		List<Map>dep=df.sqlGet("SELECT cd.id, cd.name FROM CODE_DEPT cd ORDER BY cd.id");
		//List<Map>dep=df.sqlGet("SELECT cd.id, name FROM stmd s, Class c, CODE_DEPT cd WHERE s.depart_class=c.ClassNo AND c.DeptNo=cd.id AND s.geocode LIKE '%lat%' GROUP BY cd.id");
		/*for(int i=0; i<dep.size(); i++){
			tmp=df.sqlGet("SELECT s.geocode FROM stmd s, Class c WHERE s.depart_class=c.ClassNo AND s.geocode LIKE '%lat%' AND c.DeptNo='"+dep.get(i).get("id")+"'");
			for(int j=0; j<tmp.size(); j++){
				json=new JSONObject(tmp.get(j).get("geocode").toString());
				try{
					tmp.get(j).put("lat", json.get("lat"));
					tmp.get(j).put("lng", json.get("lng"));
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}			
			dep.get(i).put("stds", tmp);
		}*/		
		request.setAttribute("stdscol", col);
		request.setAttribute("stdsdep", dep);	
		
		request.setAttribute("stdsall", df.sqlGetInt("SELECT COUNT(*)FROM stmd"));
		request.setAttribute("stdsnoschl", df.sqlGetInt("SELECT COUNT(*) FROM stmd s WHERE geocode IS NULL"));		
		List<Map>stdsgeo=df.sqlGet("SELECT geocode FROM stmd WHERE geocode IS NOT NULL AND geocode LIKE'%lat%'");		
		for(int i=0; i<stdsgeo.size(); i++){			
			json=new JSONObject(stdsgeo.get(i).get("geocode").toString());
			try{
				stdsgeo.get(i).put("lat", json.get("lat"));
				stdsgeo.get(i).put("lng", json.get("lng"));
			}catch(Exception e){
				//System.out.println(json);
			}
		}
		request.setAttribute("stdsgeo", stdsgeo);		
	}
	
	//來源學校
	private void stdfrom(){		
		request.setAttribute("stdsall", df.sqlGetInt("SELECT COUNT(*)FROM stmd"));
		request.setAttribute("stdsnoschl", df.sqlGetInt("SELECT COUNT(*)FROM stmd s, Recruit_school r WHERE s.schl_code=r.no"));
		request.setAttribute("schools", df.sqlGet("SELECT COUNT(*)as cnt, r.lat, r.lng, r.name FROM stmd s, Recruit_school r WHERE r.lat IS NOT NULL AND s.schl_code=r.no GROUP BY r.no"));
	}
	
	public String cno, stno, sno, cono, dno, gno, zno;
	//來源校表
	public String stdFromPrint() throws IOException{
		Date date=new Date();
		xml2ods(response, getRequest(), date);			
		PrintWriter out=response.getWriter();
		StringBuilder sql=new StringBuilder("SELECT COUNT(*)as cnt, r.name, r.no FROM stmd s, Recruit_school r, Class c WHERE r.geocode IS NOT NULL AND s.schl_code=r.no AND s.depart_class=c.ClassNo ");
		if(!request.getParameter("cno").equals(""))sql.append("AND c.CampusNo='"+request.getParameter("cno")+"'");
		if(!request.getParameter("stno").equals(""))sql.append("AND c.SchoolType='"+request.getParameter("stno")+"'");
		if(!request.getParameter("sno").equals(""))sql.append("AND c.SchoolNo='"+request.getParameter("sno")+"'");
		if(!request.getParameter("cono").equals(""))sql.append("AND c.InstNo='"+request.getParameter("cono")+"'");
		if(!request.getParameter("dno").equals(""))sql.append("AND c.DeptNo='"+request.getParameter("dno")+"'");
		if(!request.getParameter("gno").equals(""))sql.append("AND c.Grade='"+request.getParameter("gno")+"'");
		if(!request.getParameter("zno").equals(""))sql.append("AND c.SeqNo='"+request.getParameter("zno")+"'");
		sql.append("GROUP BY r.zip ORDER BY r.localNo");
		List<Map>list=df.sqlGet(sql.toString());
		if(list.size()<1){
			out.println ("Not enough columns given to draw the requested table");
			return null;
		}
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
		out.println ("  <Created>2016-11-15T05:56:50Z</Created>");
		out.println ("  <LastSaved>2016-11-15T07:20:59Z</LastSaved>");
		out.println ("  <Version>15.00</Version>");
		out.println (" </DocumentProperties>");
		out.println (" <OfficeDocumentSettings xmlns='urn:schemas-microsoft-com:office:office'>");
		out.println ("  <AllowPNG/>");
		out.println (" </OfficeDocumentSettings>");
		out.println (" <ExcelWorkbook xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("  <WindowHeight>13185</WindowHeight>");
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
		out.println ("  <Style ss:ID='s120'>");
		out.println ("   <Font ss:FontName='微軟正黑體' x:CharSet='136' x:Family='Swiss' ss:Size='18'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s121'>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='微軟正黑體' x:CharSet='136' x:Family='Swiss' ss:Size='18'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s122'>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='微軟正黑體' x:CharSet='136' x:Family='Swiss' ss:Size='18'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s123'>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='微軟正黑體' x:CharSet='136' x:Family='Swiss' ss:Size='18'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s160'>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='微軟正黑體' x:CharSet='136' x:Family='Swiss' ss:Size='18'");
		out.println ("    ss:Color='#FFFFFF'/>");
		out.println ("   <Interior ss:Color='#404040' ss:Pattern='Solid'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s161'>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='微軟正黑體' x:CharSet='136' x:Family='Swiss' ss:Size='18'");
		out.println ("    ss:Color='#FFFFFF'/>");
		out.println ("   <Interior ss:Color='#404040' ss:Pattern='Solid'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s162'>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='微軟正黑體' x:CharSet='136' x:Family='Swiss' ss:Size='18'");
		out.println ("    ss:Color='#FFFFFF'/>");
		out.println ("   <Interior ss:Color='#404040' ss:Pattern='Solid'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s178'>");
		out.println ("   <Font ss:FontName='微軟正黑體' x:CharSet='136' x:Family='Swiss' ss:Size='18'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("   <Interior ss:Color='#404040' ss:Pattern='Solid'/>");
		out.println ("  </Style>");
		out.println (" </Styles>");
		out.println (" <Worksheet ss:Name='工作表1'>");
		out.println ("  <Table ss:ExpandedColumnCount='3' ss:ExpandedRowCount='"+(list.size()+999)+"' x:FullColumns='1'");
		out.println ("   x:FullRows='1' ss:StyleID='s120' ss:DefaultColumnWidth='54'");
		out.println ("   ss:DefaultRowHeight='23.25'>");
		out.println ("   <Column ss:StyleID='s121' ss:AutoFitWidth='0' ss:Width='86.25'/>");
		out.println ("   <Column ss:StyleID='s122' ss:AutoFitWidth='0' ss:Width='425.25'/>");
		out.println ("   <Column ss:StyleID='s123' ss:AutoFitWidth='0' ss:Width='60'/>");
		out.println ("   <Row ss:StyleID='s178'>");
		out.println ("    <Cell ss:StyleID='s160'><Data ss:Type='String'>學校代碼</Data></Cell>");
		out.println ("    <Cell ss:StyleID='s161'><Data ss:Type='String'>學校名稱</Data></Cell>");
		out.println ("    <Cell ss:StyleID='s162'><Data ss:Type='String'>人數</Data></Cell>");
		out.println ("   </Row>");
		for(int i=0; i<list.size(); i++){
			out.println ("   <Row>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("no")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("name")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+list.get(i).get("cnt")+"</Data></Cell>");
			out.println ("   </Row>");
		}
		
		out.println ("  </Table>");
		out.println ("  <WorksheetOptions xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("   <PageSetup>");
		out.println ("    <Header x:Margin='0.3' x:Data='&amp;C&amp;&quot;微軟正黑體,標準&quot;&amp;18台北日間部四技資訊管理系一年級甲班'/>");
		out.println ("    <Footer x:Margin='0.3' x:Data='&amp;L&amp;D &amp;T&amp;R&amp;P/&amp;N'/>");
		out.println ("    <PageMargins x:Bottom='0.75' x:Left='0.25' x:Right='0.25' x:Top='0.75'/>");
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
		out.println ("     <ActiveRow>28</ActiveRow>");
		out.println ("     <ActiveCol>1</ActiveCol>");
		out.println ("    </Pane>");
		out.println ("   </Panes>");
		out.println ("   <ProtectObjects>False</ProtectObjects>");
		out.println ("   <ProtectScenarios>False</ProtectScenarios>");
		out.println ("  </WorksheetOptions>");
		out.println (" </Worksheet>");
		out.println ("</Workbook>");
		
		return null;
	}
	
	//缺曠
	private void dilg(){
		List<Map>all=df.sqlGet("SELECT c.InstNo, c.DeptNo, c.SchoolType, c.Grade, d.* FROM BATCH_DILG_CLASS d, Class c WHERE d.dilgs>0 AND c.InstNo IS NOT NULL AND d.ClassNo=c.ClassNo");
		Map map;
		List<Map>tmp;
		
		int cnt[];		
		//校日曆圖
		//request.setAttribute("days", df.sqlGet("SELECT COUNT(*)as cnt, "
		//+ "DATE_FORMAT(date,'%Y')as y, DATE_FORMAT(date,'%c')as m, DATE_FORMAT(date,'%e')as d FROM Dilg WHERE abs<5 GROUP BY date"));
		//校18週線圖
		tmp=new ArrayList();
		
		for(int i=1; i<19; i++){
			cnt=new int[1];
			map=new HashMap();
			map.put("week", i);
			for(int j=0; j<all.size(); j++)
			if(  
				all.get(j).get("week").toString().equals(String.valueOf(i))					
			){						
				cnt[0]+=Integer.parseInt(all.get(j).get("dilgs").toString());						
			}
			map.put("cnt", cnt);
			tmp.add(map);
		}
		request.setAttribute("schcnt", tmp);		
		List<Map>col=df.sqlGet("SELECT id, name, (SELECT COUNT(*)FROM stmd s, Class c WHERE s.depart_class=c.ClassNo AND c.InstNo=CODE_COLLEGE.id)as stds FROM CODE_COLLEGE");
		
		
		
		
		//院18週日圖
		/*for(int i=0; i<col.size(); i++){
			col.get(i).put("days", df.sqlGet("SELECT COUNT(*)as cnt,DATE_FORMAT(d.date,'%Y')as y, "
					+ "DATE_FORMAT(d.date,'%c')as m,DATE_FORMAT(d.date,'%e')as d "
					+ "FROM Dilg d, Class c, stmd s WHERE d.abs<5 AND d.student_no=s.student_no "
					+ "AND s.depart_class=c.ClassNo AND c.InstNo='"+col.get(i).get("id")+"'GROUP BY date"));
		}*/
			
		//院18週線圖		
		for(int i=0; i<col.size(); i++){			
			cnt=new int[18];
			for(int j=0; j<18; j++){				
				for(int k=0; k<all.size(); k++){					
					if(  
						all.get(k).get("InstNo").toString().equals(col.get(i).get("id").toString()) &&
						all.get(k).get("week").toString().equals(String.valueOf(j+1))
							
					){		
						cnt[j]+=Integer.parseInt(all.get(k).get("dilgs").toString());
					}
					
				}
				
			}
			col.get(i).put("cnt", cnt);
		}		
		request.setAttribute("col", col);		
		
		//系18週線圖
		List<Map>dep=df.sqlGet("SELECT id, college, sname, name,(SELECT COUNT(*)FROM stmd s, Class c WHERE s.depart_class=c.ClassNo AND c.DeptNo=CODE_DEPT.id)as stds FROM CODE_DEPT WHERE id!='0' AND college IS NOT NULL");		
		for(int i=0; i<dep.size(); i++){
			if(Integer.parseInt(dep.get(i).get("stds").toString())==0)continue;
			cnt=new int[18];
			for(int j=0; j<18; j++){
				
				for(int k=0; k<all.size(); k++){					
					if(  
						all.get(k).get("DeptNo").toString().equals(dep.get(i).get("id").toString()) &&
						all.get(k).get("week").toString().equals(String.valueOf(j+1))
							
					){		
						cnt[j]+=Integer.parseInt(all.get(k).get("dilgs").toString());
					}
				}				
			}			
			dep.get(i).put("cnt", cnt);
			dep.get(i).put("days", df.sqlGet("SELECT COUNT(*)as cnt,DATE_FORMAT(d.date,'%Y')as y, "
					+ "DATE_FORMAT(d.date,'%c')as m,DATE_FORMAT(d.date,'%e')as d "
					+ "FROM Dilg d, Class c, stmd s WHERE d.abs<5 AND d.student_no=s.student_no "
					+ "AND s.depart_class=c.ClassNo AND c.DeptNo='"+dep.get(i).get("id")+"'GROUP BY date"));
		}		
		request.setAttribute("dep", dep);	
		
	}

}
