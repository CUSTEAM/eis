package action.sa;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import action.BaseAction;

public class StdSearch extends BaseAction{
	
	public String type;
	public String cno, stno, sno, cono, dno, gno, zno;
	public String stdName;
	public String sex;
	public String ident;
	public String beginDate, endDate;
	public String week, beginCls, endCls;
	public String addr;
	public String sch;
	public String stds;
	
	public String execute(){		
		return SUCCESS;
	}
	
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
	
	private void list() throws IOException{
		StringBuilder sb=new StringBuilder("SELECT w.inco, s.*,c.ClassName, "
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
		+ "d.id=c.DeptNo");
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
		System.out.println(sb);
		print(df.sqlGet(sb.toString()));
		
		
	}
	
	private void cls() throws IOException{
		Date d=new Date();
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename="+d.getTime()+".xls");
		
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
	
	private void print(List<Map>list) throws IOException{
		Date d=new Date();
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename="+d.getTime()+".xls");		
		PrintWriter out = response.getWriter();
		out.println("<style>td{font-size:18px; font-family:微軟正黑體;}</style>");
		out.println("<table border='1'>  ");

		out.println("<tr bgcolor='#dddddd'>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>班級名稱</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>班級代碼</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>姓名</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>學號</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>性別</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>身分證字號</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>出生日期</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>入學年月</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>前學程畢業</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>身份</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>組別</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>出生省縣</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>出生鄉鎮市</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>電話</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>行動電話</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>現居郵編</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>現居地址</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>戶籍郵編</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>戶籍地址</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>戶籍里</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>畢業學校代碼</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>畢業學校名稱</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>畢業科系</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>畢業狀態</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>家長姓名</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>最後變更學年</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>最後變更學期</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>最後變更狀態</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>最後變更原因</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>最後變更日期</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>最後變更文號</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>畢業號</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>輔系/雙主修</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>輔系/雙主修科系</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>電子郵件</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>身份備註</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>英譯姓名</td>");
		out.println("<td align='center' style='mso-number-format:\\@' nowrap>卡號</td>");
		out.println("</tr>");			
		for(int i=0; i<list.size(); i++){
			
			if(i%2==0){
				out.println("<tr>");
			}else{
				out.println("<tr bgcolor='#dddddd'>");
			}
			
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+list.get(i).get("depart_class") + "</td>");
			out.println("<td align='center' style='mso-number-format:\\@'>"+list.get(i).get("ClassName") + "</td>");

			out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+list.get(i).get("student_name") + "</td>");
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+list.get(i).get("student_no") + "</td>");

			// 性別
			if (list.get(i).get("sex").equals("1")) {
				out.println("<td align='center' style='mso-number-format:\\@' nowrap>男</td>");
			} else {
				out.println("<td align='center' style='mso-number-format:\\@' nowrap>女</td>");
			}
			
			out.println("<td align='center' style='mso-number-format:\\@'>"+list.get(i).get("idno") + "</td>");// 身份證
			out.println("<td align='center' style='mso-number-format:\\@' nowrap>"+list.get(i).get("birthday") + "</td>"); // 生日
			if (list.get(i).get("entrance") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+list.get(i).get("entrance") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (list.get(i).get("gradyear") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+list.get(i).get("gradyear") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (list.get(i).get("name") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+list.get(i).get("name") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (list.get(i).get("team") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+list.get(i).get("team") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (list.get(i).get("province") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+list.get(i).get("province") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (list.get(i).get("county") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+list.get(i).get("county") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (list.get(i).get("telephone") != null) {
				out.println("<td align='left' style='mso-number-format:\\@'>"+list.get(i).get("telephone") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (list.get(i).get("CellPhone") != null) {
				out.println("<td align='left' style='mso-number-format:\\@'>"+list.get(i).get("CellPhone") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (list.get(i).get("curr_post") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+list.get(i).get("curr_post") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (list.get(i).get("curr_addr") != null) {
				out.println("<td align='left' style='mso-number-format:\\@' nowrap>"+list.get(i).get("curr_addr") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (list.get(i).get("perm_post") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+list.get(i).get("perm_post") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (list.get(i).get("perm_addr") != null) {
				out.println("<td align='left' style='mso-number-format:@' nowrap>"+list.get(i).get("perm_addr") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}
			
			if (list.get(i).get("liner") != null) {
				out.println("<td align='left' style='mso-number-format:@' nowrap>"+list.get(i).get("liner") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (list.get(i).get("schl_code") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+list.get(i).get("schl_code") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (list.get(i).get("schl_name") != null) {
				out.println("<td align='left' style='mso-number-format:\\@'>"+list.get(i).get("schl_name") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (list.get(i).get("grad_dept") != null) {
				out.println("<td align='left' style='mso-number-format:\\@'>"+list.get(i).get("grad_dept") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (list.get(i).get("gradu_status") != null) {
				if (list.get(i).get("gradu_status").equals("1")) {
					out.println("<td align='center' style='mso-number-format:\\@'>畢</td>");
				} else {
					out.println("<td align='center' style='mso-number-format:\\@'>肆</td>");
				}
			} else {
				out.println("<td align='center' style='mso-number-format:\\@'>啥</td>");
			}

			if (list.get(i).get("parent_name") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+list.get(i).get("parent_name") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (list.get(i).get("occur_year") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+list.get(i).get("occur_year") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (list.get(i).get("occur_term") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+list.get(i).get("occur_term") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (list.get(i).get("status_name") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+list.get(i).get("status_name") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (list.get(i).get("caurse_name") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+list.get(i).get("caurse_name") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (list.get(i).get("occur_date") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+list.get(i).get("occur_date") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (list.get(i).get("occur_docno") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+list.get(i).get("occur_docno") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (list.get(i).get("occur_graduate_no") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+list.get(i).get("occur_graduate_no") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (list.get(i).get("ExtraStatus") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+list.get(i).get("ExtraStatus") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (list.get(i).get("ExtraDept") != null) {
				out.println("<td align='center' style='mso-number-format:\\@'>"+list.get(i).get("ExtraDept") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			if (list.get(i).get("Email") != null) {
				out.println("<td align='left' style='mso-number-format:\\@'>"+list.get(i).get("Email") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			// out.println("<td align='left'
			// style='mso-number-format:\\@'>"+list.get(i).get("ident_remark")+"</td>");
			
			//if (rmark != null) {
				//out.println("<td align='left' style='mso-number-format:\\@'>"+ rmark.get("remark_name") + rmark.get("military")+ rmark.get("certificate") + "</td>");
			//} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			//}

			if (list.get(i).get("student_ename") != null) {
				out.println("<td align='left' style='mso-number-format:\\@'>"+list.get(i).get("student_ename") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}
			
			if (list.get(i).get("inco") != null) {
				out.println("<td align='left' style='mso-number-format:\\@'>"+list.get(i).get("inco") + "</td>");
			} else {
				out.println("<td align='left' style='mso-number-format:\\@'></td>");
			}

			out.println("	</tr>");			
		}
		out.println("</table>");
		out.close();
		
		
	}

}
