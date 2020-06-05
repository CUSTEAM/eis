package action.sa;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import action.BasePrintXmlAction;

/**
 * 學生查詢與報表列印程式
 * <p>
 * 教職員透過條件選擇列出學生資料
 * <ol>
 *  <li>基本資料欄位</li>
 *  <li>上課星期節次</li>
 *  <li>輸入名單</li>
 * </ol>
 * </p>
 * @version 3.1 新增照片匯出
 * @version 3.0.2 新增身份證條件至名單輸入查詢
 * @version 3.0.1 新增身份證條件至學號姓名欄位查詢
 * @version 3.0 新增依名單輸入查詢
 * @version 2.1 新增身份別欄位
 * @version 2.0 新增依節次單查詢
 * @version 1.0.1 新增同意條款
 * @version 1.0 init
 * 
 * @author shawn 
 */
public class StdSearch extends BasePrintXmlAction{
	
	/**
	 * 部制
	 */
	public String type;
	
	public String filter[];
	
	public String occur_status, occur_date_begin, occur_date_end, occur_year_begin, occur_year_end;
	
	/**
	 * 校區 部制 學制 院 科系 年級 班級
	 */
	public String cno, stno, sno, cono, dno, gno, zno, graduate;
	/**
	 * 學號或姓名
	 */
	public String stdName;
	/**
	 * 姓別
	 */
	public String sex;
	/**
	 * 身份別
	 */
	public String ident;
	/**
	 * 出生年月日起始範圍
	 */
	public String beginDate, endDate;
	/**
	 * 星期 開始節次 結束節次
	 */
	public String week, beginCls, endCls;
	/**
	 * 住址
	 */
	public String addr;
	/**
	 * 畢業學校
	 */
	public String sch;
	/**
	 * 輸入名單
	 */
	public String stds;
	
	/**
	 * init
	 */
	public String execute(){		
		return SUCCESS;
	}
	
	/**
	 * 查詢
	 * @return
	 * @throws IOException
	 */
	public String clSearch() throws IOException{	
		
		if(stds.trim().equals("") && week.equals("")){
			list();
		}
		
		if(!week.trim().equals("")){
			cls();
		}
		
		if(!stds.trim().equals("")){
			note();
		}
		
		return null;
	}
	
	private void pic(){
		
		
	}
	
	/**
	 * 以標準方式查詢
	 * @throws IOException
	 */
	private void list() throws IOException{
		StringBuilder sb=new StringBuilder("SELECT cp9.id4, cp.id as peid, w.inco, s.*,c.ClassName, "
		+ "d.name as DeptName, c5.name,c51.name as team,c3.name as county,"
		+ "c31.name as province, c52.name as status_name, c53.name as caurse_name,"
		+ "c54.name as ident_name2 FROM((((((((("+type+" s LEFT OUTER JOIN Class c ON "
		+ "s.depart_class=c.ClassNo)LEFT OUTER JOIN code5 c5 ON c5.idno=s.ident AND "
		+ "c5.category='Identity')LEFT OUTER JOIN code5 c51 ON c51.idno=s.divi AND "
		+ "c51.category='GROUP')LEFT OUTER JOIN code3 c3 ON s.birth_county=c3.no)LEFT "
		+ "OUTER JOIN code3 c31 ON c31.no=s.birth_province)LEFT OUTER JOIN code5 c52 ON "
		+ "s.occur_status=c52.idno AND c52.category='Status')LEFT OUTER JOIN code5 c53 ON "
		+ "s.occur_cause=c53.idno AND c53.category='Cause')LEFT OUTER JOIN code5 c54 ON "
		+ "s.ident_basic=c54.idno AND c53.category='Identity', CODE_DEPT d)LEFT OUTER JOIN wwpass w ON s.student_no=w.username "
		+ "LEFT OUTER JOIN CODE_PE11 cp ON cp.id1=c.SchoolNo)LEFT OUTER JOIN CODE_PE9 cp9 ON c.DeptNo=cp9.id1 WHERE "
		+ "d.id=c.DeptNo");
		if(!occur_status.equals(""))sb.append(" AND s.occur_status='"+occur_status+"'");
		if(!occur_date_begin.equals(""))sb.append(" AND s.occur_date>='"+occur_date_begin+"'");
		if(!occur_date_end.equals(""))sb.append(" AND s.occur_date<='"+occur_date_end+"'");
		if(!occur_year_begin.equals(""))sb.append(" AND s.occur_year>='"+occur_year_begin+"'");
		if(!occur_year_end.equals(""))sb.append(" AND s.occur_year<='"+occur_year_end+"'");
		if(!graduate.equals(""))sb.append(" AND c.graduate='"+graduate+"'");
		
		if(!cno.equals(""))sb.append(" AND c.CampusNo='"+cno+"'");			
		if(!stno.equals(""))sb.append(" AND c.SchoolType='"+stno+"'");
		if(!sno.equals(""))sb.append(" AND c.SchoolNo='"+sno+"'");
		if(!cono.equals(""))sb.append(" AND c.InstNo='"+cono+"'");
		if(!dno.equals(""))sb.append(" AND c.DeptNo='"+dno+"'");
		if(!gno.equals(""))sb.append(" AND c.Grade='"+gno+"'");
		if(!zno.equals(""))sb.append(" AND c.SeqNo='"+zno+"'");			
		if(!sex.equals(""))sb.append(" AND s.sex='"+sex+"'");
		if(!ident.equals(""))sb.append(" AND s.ident='"+ident+"'");
		if(!beginDate.equals(""))sb.append(" AND s.birthday>='"+beginDate+"'");
		if(!endDate.equals(""))sb.append(" AND s.birthday<'"+endDate+"'");			
		if(!sch.equals(""))sb.append(" AND s.schl_name LIKE'%"+sch+"%'");
		if(!stdName.equals("")){
			sb.append(" AND (s.student_no LIKE'%"+stdName+"%' OR s.student_name LIKE '%"+stdName+"%' OR s.idno LIKE '%"+stdName+"%')");
		}
		if(!addr.equals(""))sb.append(" AND s.perm_addr LIKE'%"+addr+"%' || s.curr_addr LIKE'%"+addr+"%'");
			
		sb.append(" ORDER BY c.ClassNo, s.student_no");		
		//System.out.println(sb);
		print(df.sqlGet(sb.toString()));		
	}
	
	/**
	 * 以節次查詢並立即產生excel
	 * @throws IOException
	 */
	private void cls() throws IOException{
		Date d=new Date();
		xml2ods(response, getRequest(), d);
		String sterm=getContext().getAttribute("school_term").toString();
		StringBuilder sb=new StringBuilder("SELECT c.ClassNo, c.ClassName FROM Class c WHERE c.Type='p'");
		if(!cno.equals(""))sb.append("AND c.CampusNo='"+cno+"'");			
		if(!stno.equals(""))sb.append(" AND c.SchoolType='"+stno+"'");
		if(!sno.equals(""))sb.append(" AND c.SchoolNo='"+sno+"'");
		if(!cono.equals(""))sb.append(" AND c.InstNo='"+cono+"'");
		if(!dno.equals(""))sb.append(" AND c.DeptNo='"+dno+"'");
		if(!gno.equals(""))sb.append(" AND c.Grade='"+gno+"'");
		if(!zno.equals(""))sb.append(" AND c.SeqNo='"+zno+"'");	
		sb.append("ORDER BY c.SchoolType, c.ClassNo");
		PrintWriter out=response.getWriter();
		
		List<Map>list=df.sqlGet(sb.toString());		
		out.println("<style>td{font-size:18px;}</style>");
		List<Map>students;
		List<Map>selds;
		String str[];
		int x;
		int y;
		for(int i=0; i<list.size(); i++){
			students=df.sqlGet("SELECT s.student_no, s.student_name FROM "+type+" s WHERE s.depart_class='"+list.get(i).get("ClassNo")+"'");
			
			if(students.size()<1){
				continue;
			}
			out.println("<table border='1'>");
			out.println("  <tr>");
			out.println("    <td colspan='16'>"+list.get(i).get("ClassName")+"</td>");			
			out.println("  </tr>");
			out.println("  <tr>");			
			out.println("    <td>學號</td>");
			out.println("    <td>姓名</td>");
			out.println("    <td>1</td>");
			out.println("    <td>2</td>");
			out.println("    <td>3</td>");
			out.println("    <td>4</td>");
			out.println("    <td>5</td>");
			out.println("    <td>6</td>");
			out.println("    <td>7</td>");
			out.println("    <td>8</td>");
			out.println("    <td>9</td>");
			out.println("    <td>10</td>");
			out.println("    <td>1</td>");
			out.println("    <td>2</td>");
			out.println("    <td>3</td>");
			out.println("    <td>4</td>");
			out.println("  </tr>");
			
			for(int j=0; j<students.size(); j++){
				str=new String[]{"","","","","","","","","","","","","","",};
				selds=df.sqlGet("SELECT dc.* FROM Seld s, Dtime d, Dtime_class dc WHERE " +
				"dc.Dtime_oid=d.Oid AND dc.week='"+week+"' AND " +
				"d.Oid=s.Dtime_oid AND d.Sterm='"+sterm+"' AND " +
				"s.student_no='"+students.get(j).get("student_no")+"'");
				
				for(int k=0; k<selds.size(); k++){
					x=Integer.parseInt(selds.get(k).get("begin").toString())-1;
					y=Integer.parseInt(selds.get(k).get("end").toString())-1;
					str[x]="*";
					str[y]="*";
					if(y-x>=2){
						for(int k1=0; k1<(y-x); k1++){
							str[x+k1]="*";
						}
					}
				}
				
				out.println("  <tr>");
				out.println("    <td>"+students.get(j).get("student_no")+"&nbsp</td>");
				out.println("    <td>"+students.get(j).get("student_name")+"</td>");
				
				for(int k=0; k<str.length; k++){
					out.println("    <td>"+str[k]+"</td>");
					
				}
				out.println("  </tr>");				
			}
			out.println("  </table>");
			out.println("  <p style='page-break-before:always;'></p>");
		}
		out.close();
		
	}
	
	/**
	 * 以名單查詢
	 * @throws IOException
	 */
	private void note() throws IOException{
		
		List<Map<String,String>>s=df.sqlGet("SELECT student_no, student_name FROM "+type);
		List<Map>in=new ArrayList();
		for(int i=0; i<s.size(); i++){			
			if(stds.indexOf(s.get(i).get("student_name"))>=0){
				in.add(s.get(i));
			}			
		}
		StringBuilder sb=new StringBuilder("SELECT w.inco,s.*,c.ClassName, "
		+ "d.name as DeptName, c5.name,c51.name as team,c3.name as county,"
		+ "c31.name as province, c52.name as status_name, c53.name as caurse_name,"
		+ "c54.name as ident_name2 FROM(((((((("+type+" s LEFT OUTER JOIN Class c ON "
		+ "s.depart_class=c.ClassNo)LEFT OUTER JOIN code5 c5 ON c5.idno=s.ident AND "
		+ "c5.category='Identity')LEFT OUTER JOIN code5 c51 ON c51.idno=s.divi AND "
		+ "c51.category='GROUP')LEFT OUTER JOIN code3 c3 ON s.birth_county=c3.no)LEFT "
		+ "OUTER JOIN code3 c31 ON c31.no=s.birth_province)LEFT OUTER JOIN code5 c52 ON "
		+ "s.occur_status=c52.idno AND c52.category='Status')LEFT OUTER JOIN code5 c53 ON "
		+ "s.occur_cause=c53.idno AND c53.category='Cause')LEFT OUTER JOIN code5 c54 ON "
		+ "s.ident_basic=c54.idno AND c53.category='Identity', CODE_DEPT d)LEFT OUTER JOIN wwpass w ON s.student_no=w.username WHERE "
		+ "d.id=c.DeptNo AND s.student_no IN(");		
		for(int i=0; i<in.size(); i++){
			sb.append("'"+in.get(i).get("student_no")+"',");
		}
		sb.delete(sb.length()-1, sb.length());		
		sb.append(")ORDER BY c.ClassNo");		
		try{
			print(df.sqlGet(sb.toString()));
		}catch(Exception e){
			print(new ArrayList());
		}		
	}
	
	/**
	 * 輸出EXCEL
	 * @param list 學生具體資料
	 * 
	 * @throws IOException
	 */
	private void print(List<Map>list) throws IOException{		
				
		xml2ods(response, getRequest(), new Date());
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
		out.println ("  <Created>2016-08-22T06:46:42Z</Created>");
		out.println ("  <Version>15.00</Version>");
		out.println (" </DocumentProperties>");
		out.println (" <OfficeDocumentSettings xmlns='urn:schemas-microsoft-com:office:office'>");
		out.println ("  <AllowPNG/>");
		out.println (" </OfficeDocumentSettings>");
		out.println (" <ExcelWorkbook xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("  <WindowHeight>13845</WindowHeight>");
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
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'");
		out.println ("     ss:Color='#000000'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'");
		out.println ("     ss:Color='#000000'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'");
		out.println ("     ss:Color='#000000'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'");
		out.println ("     ss:Color='#000000'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='微軟正黑體' x:CharSet='136' x:Family='Swiss' ss:Size='14'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("   <Interior/>");
		out.println ("   <NumberFormat ss:Format='@'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s63'>");
		out.println ("   <Interior/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s64'>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'");
		out.println ("     ss:Color='#000000'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'");
		out.println ("     ss:Color='#000000'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'");
		out.println ("     ss:Color='#000000'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'");
		out.println ("     ss:Color='#000000'/>");
		out.println ("   </Borders>");
		out.println ("  </Style>");
		out.println ("<Style ss:ID='s68'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'");
		out.println ("     ss:Color='#000000'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'");
		out.println ("     ss:Color='#000000'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'");
		out.println ("     ss:Color='#000000'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'");
		out.println ("     ss:Color='#000000'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='微軟正黑體' x:CharSet='136' x:Family='Swiss' ss:Size='14'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("   <Interior/>");
		out.println ("   <NumberFormat ss:Format='[TWN][$-404]y/m/d;@'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s69'>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'");
		out.println ("     ss:Color='#000000'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'");
		out.println ("     ss:Color='#000000'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'");
		out.println ("     ss:Color='#000000'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'");
		out.println ("     ss:Color='#000000'/>");
		out.println ("   </Borders>");
		out.println ("   <NumberFormat ss:Format='[TWN][$-404]y/m/d;@'/>");
		out.println ("  </Style>");
		out.println (" </Styles>");
		out.println (" <Worksheet ss:Name='工作表1'>");
		out.println ("  <Table ss:ExpandedColumnCount='41' ss:ExpandedRowCount='"+(list.size()+999)+"' x:FullColumns='1'");
		out.println ("   x:FullRows='1' ss:DefaultColumnWidth='54' ss:DefaultRowHeight='16.5'>");
		out.println ("   <Column ss:StyleID='s64' ss:Span='38'/>");
		
		
		boolean col[]=new boolean[40];
		for(int i=0; i<filter.length; i++){			
			col[Integer.parseInt(filter[i])]=true;
		}
		
		
		out.println ("   <Row ss:Height='18.75' ss:StyleID='s63'>");
		
		if(col[0]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>系所名稱</Data></Cell>");
		if(col[1]==true){
			out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>班級代碼</Data></Cell>");
			out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>班級名稱</Data></Cell>");
		}
		if(col[2]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>姓名</Data></Cell>");
		if(col[3]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>學號</Data></Cell>");
		if(col[4]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>性別</Data></Cell>");
		if(col[5]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>身分證字號</Data></Cell>");
		if(col[6]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>出生日期</Data></Cell>");
		if(col[7]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>入學年月</Data></Cell>");
		if(col[8]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>前學程畢業</Data></Cell>");
		if(col[9]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>身份</Data></Cell>");
		if(col[10]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>組別</Data></Cell>");
		if(col[11]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>出生省縣</Data></Cell>");
		if(col[12]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>出生鄉鎮市</Data></Cell>");
		if(col[13]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>電話</Data></Cell>");
		if(col[14]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>行動電話</Data></Cell>");
		if(col[15]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>現居郵編</Data></Cell>");
		if(col[16]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>現居地址</Data></Cell>");
		if(col[17]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>戶籍郵編</Data></Cell>");
		if(col[18]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>戶籍地址</Data></Cell>");
		if(col[19]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>戶籍里</Data></Cell>");
		if(col[20]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>畢業學校代碼</Data></Cell>");
		if(col[21]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>畢業學校名稱</Data></Cell>");
		if(col[22]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>畢業科系</Data></Cell>");
		if(col[23]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>畢業狀態</Data></Cell>");
		if(col[24]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>家長姓名</Data></Cell>");
		if(col[25]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>最後變更學年</Data></Cell>");
		if(col[26]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>最後變更學期</Data></Cell>");
		if(col[27]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>最後變更狀態</Data></Cell>");
		if(col[28]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>最後變更原因</Data></Cell>");
		if(col[29]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>最後變更日期</Data></Cell>");
		if(col[30]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>最後變更文號</Data></Cell>");
		if(col[31]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>畢業號</Data></Cell>");
		if(col[32]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>輔系/雙主修</Data></Cell>");
		//if(col[33]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>輔系/雙主修科系</Data></Cell>");
		if(col[33]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>電子郵件</Data></Cell>");
		if(col[34]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>身份備註</Data></Cell>");
		if(col[35]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>英譯姓名</Data></Cell>");
		if(col[36]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>卡號</Data></Cell>");
		
		out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>教育部學制代碼</Data></Cell>");
		out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>教育部科系代碼</Data></Cell>");
		
		out.println ("   </Row>");
		
		for(int i=0; i<list.size(); i++){
			out.println ("   <Row ss:Height='18.75'>");
			if(col[0]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("DeptName") + "</Data></Cell>");//系所名稱
			if(col[1]==true){
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("depart_class") + "</Data></Cell>");//班級代碼
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("ClassName") + "</Data></Cell>");//班級名稱
			}
			
			if(col[2]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("student_name") + "</Data></Cell>");//姓名
			if(col[3]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("student_no") + "</Data></Cell>");//學號
			
			
			if(col[4]==true)
			if (list.get(i).get("sex").equals("1")) {//性別
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>男</Data></Cell>");
			} else {
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>女</Data></Cell>");
			}
						
			if(col[5]==true)out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("idno") + "</Data></Cell>");//身分證字號
			//ss68 民國年格式
			if(col[6]==true)out.println ("    <Cell ss:StyleID='s68'><Data ss:Type='DateTime'>"+list.get(i).get("birthday") + "</Data></Cell>");//出生日期
			
			if(col[7]==true)
			if (list.get(i).get("entrance") != null) {//入學年月
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("entrance") + "</Data></Cell>");
			} else {
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}
			
			if(col[8]==true)
			if (list.get(i).get("gradyear") != null) {//前學程畢業
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("gradyear") + "</Data></Cell>");
			} else {
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}
			
			if(col[9]==true)
			if (list.get(i).get("name") != null) {//身份
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("name") + "</Data></Cell>");
			} else {
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}
			
			if(col[10]==true)
			if (list.get(i).get("team") != null) {//組別
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("team") + "</Data></Cell>");
			} else {
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}
			
			if(col[11]==true)
			if (list.get(i).get("province") != null) {//出生省縣
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("province") + "</Data></Cell>");
			} else {
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}
			
			if(col[12]==true)
			if (list.get(i).get("county") != null) {//出生鄉鎮市
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("county") + "</Data></Cell>");
			} else {
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}
			
			if(col[13]==true)
			if (list.get(i).get("telephone") != null) {//電話
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("telephone") + "</Data></Cell>");
			} else {
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}
			
			if(col[14]==true)
			if (list.get(i).get("CellPhone") != null) {//電話
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("CellPhone") + "</Data></Cell>");
			} else {
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}
			
			if(col[15]==true)
			if (list.get(i).get("curr_post") != null) {
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("curr_post") + "</Data></Cell>");
			} else {
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}
			
			if(col[16]==true)
			if (list.get(i).get("curr_addr") != null) {
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("curr_addr") + "</Data></Cell>");
			} else {
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}
			
			if(col[17]==true)
			if (list.get(i).get("perm_post") != null) {
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("perm_post") + "</Data></Cell>");
			} else {
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}
			
			if(col[18]==true)
			if (list.get(i).get("perm_addr") != null) {
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("perm_addr") + "</Data></Cell>");
			} else {
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}
			
			if(col[19]==true)
			if (list.get(i).get("liner") != null) {
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("liner") + "</Data></Cell>");
			} else {
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}
			
			if(col[20]==true)
			if (list.get(i).get("schl_code") != null) {//畢業學校代碼
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("schl_code") + "</Data></Cell>");
			} else {
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}
			
			if(col[21]==true)
			if (list.get(i).get("schl_name") != null) {//畢業學校名稱
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("schl_name") + "</Data></Cell>");
			} else {
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}
			
			if(col[22]==true)
			if (list.get(i).get("grad_dept") != null) {//畢業科系
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("grad_dept") + "</Data></Cell>");
			} else {
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}
			
			if(col[23]==true)
			if (list.get(i).get("gradu_status") != null) {//畢業狀態
				if (list.get(i).get("gradu_status").equals("1")) {
					out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>畢</Data></Cell>");
				} else {
					out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>肆</Data></Cell>");
				}
			} else {
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}
			
			if(col[24]==true)
			if (list.get(i).get("parent_name") != null) {//家長姓名
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("parent_name") + "</Data></Cell>");
			} else {
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}
			
			if(col[25]==true)
			if (list.get(i).get("occur_year") != null) {//最後變更學年
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("occur_year") + "</Data></Cell>");
			} else {
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}
			
			if(col[26]==true)
			if (list.get(i).get("occur_term") != null) {//
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("occur_term") + "</Data></Cell>");
			} else {
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}
			
			if(col[27]==true)
			if (list.get(i).get("status_name") != null) {//最後變更狀態
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("status_name") + "</Data></Cell>");
			} else {
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}
			
			if(col[28]==true)
			if (list.get(i).get("caurse_name") != null) {//最後變更原因
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("caurse_name") + "</Data></Cell>");
			} else {
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}
			
			if(col[29]==true)
			if (list.get(i).get("occur_date") != null) {//最後變更日期
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("occur_date") + "</Data></Cell>");
			} else {
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}
			
			if(col[30]==true)
			if (list.get(i).get("occur_docno") != null) {//最後變更文號
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("occur_docno") + "</Data></Cell>");
			} else {
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}
			
			if(col[31]==true)
			if (list.get(i).get("occur_graduate_no") != null) {//畢業號
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("occur_graduate_no") + "</Data></Cell>");
			} else {
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}
			
			/*if(col[32]==true)
			if (list.get(i).get("ExtraStatus") != null) {//
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("ExtraStatus") + "</Data></Cell>");
			} else {
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}*/
			
			if(col[32]==true)
			if (list.get(i).get("ExtraDept") != null) {//
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("ExtraDept") + "</Data></Cell>");
			} else {
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}
			
			if(col[33]==true)
			if (list.get(i).get("Email") != null) {//電子郵件
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("Email") + "</Data></Cell>");
			} else {
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}
			
			//身份備註
			if(col[34]==true)out.println ("    <Cell ss:StyleID='s62'></Cell>");
			
			if(col[35]==true)
			if (list.get(i).get("student_ename") != null) {//
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("student_ename") + "</Data></Cell>");
			} else {
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}
			
			if(col[36]==true)
			if (list.get(i).get("inco") != null) {
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("inco") + "</Data></Cell>");
			} else {
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}
			
			if (list.get(i).get("peid") != null) {
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("peid")+"</Data></Cell>");
			}else{
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
			}
			
			if (list.get(i).get("id4") != null) {
				out.println ("    <Cell ss:StyleID='s62'><Data ss:Type='String'>"+list.get(i).get("id4")+"</Data></Cell>");
			}else{
				out.println ("    <Cell ss:StyleID='s62'></Cell>");
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
		out.println ("   <LeftColumnVisible>15</LeftColumnVisible>");
		out.println ("   <Panes>");
		out.println ("    <Pane>");
		out.println ("     <Number>3</Number>");
		out.println ("     <ActiveRow>19</ActiveRow>");
		out.println ("     <ActiveCol>36</ActiveCol>");
		out.println ("    </Pane>");
		out.println ("   </Panes>");
		out.println ("   <ProtectObjects>False</ProtectObjects>");
		out.println ("   <ProtectScenarios>False</ProtectScenarios>");
		out.println ("  </WorksheetOptions>");
		out.println (" </Worksheet>");
		out.println ("</Workbook>");		
		out.close();
		
		
	}

}
