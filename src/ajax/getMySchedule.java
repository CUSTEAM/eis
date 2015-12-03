package ajax;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import action.BaseAction;

/**
 * 取得個人/會議行事曆
 * @author shawn
 *
 */
public class getMySchedule extends BaseAction{
	
	public String execute()throws Exception {			
		//可用變數
		String week;		
		String start;
		String end;
		Date vstart;
		Date vend;
		SimpleDateFormat sf1=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");		
		
		Date school_term_begin=sf.parse((String)getContext().getAttribute("school_term_begin"));//取得開學日期
		Date school_term_end=sf.parse((String)getContext().getAttribute("school_term_end"));//取得學期結束日期			
		Date now=new Date();
		
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out=response.getWriter();
		out.println("<data>");				
		
		//掛名課程
		List<Map>list=df.sqlGet("SELECT cl.CampusNo,  cl.SchoolNo, " +
		"dc.*, cl.ClassName, cs.chi_name FROM Dtime d, Csno cs, Class cl, Dtime_class dc " +
		"WHERE d.cscode=cs.cscode AND d.depart_class=cl.ClassNo AND d.Oid=dc.Dtime_oid AND " +
		"d.Sterm='"+getContext().getAttribute("school_term")+"' AND d.techid='"+getSession().getAttribute("userid")+"'");
		//多教師課程
		list.addAll(df.sqlGet("SELECT cl.CampusNo,  cl.SchoolNo, dc.*, cl.ClassName, cs.chi_name FROM Dtime_teacher dt, Dtime d, "
		+ "Csno cs, Class cl, Dtime_class dc WHERE dt.Dtime_oid=d.Oid AND d.cscode=cs.cscode AND d.depart_class=cl.ClassNo AND "
		+ "d.Oid=dc.Dtime_oid AND d.Sterm='"+getContext().getAttribute("school_term")+"' AND dt.teach_id='"+getSession().getAttribute("userid")+"'"));
		
		Calendar begin=Calendar.getInstance();
		Calendar show;
		begin.setTime(school_term_begin);
		
		for(int i=0; i<list.size(); i++){
			show=Calendar.getInstance();			
			show.setTime(begin.getTime());
			week=list.get(i).get("week").toString();
			show=setCalendar(show, week);
			start=df.sqlGetStr("SELECT d.DSbegin FROM Dtimestamp d WHERE " +
					"Cidno IS NULL AND d.DSweek='"+week+"' AND d.DSreal="+list.get(i).get("begin"));
					end=df.sqlGetStr("SELECT d.DSend FROM Dtimestamp d WHERE " +
					"d.Cidno IS NULL AND d.DSweek='"+list.get(i).get("week")+"' AND d.DSreal="+list.get(i).get("end"));
			
			//TODO 課程未排時間
			try{
				vstart=sf1.parse("1970-01-01 "+start);
				vend=sf1.parse("1970-01-01 "+end);
			}catch(Exception e){
				vstart=sf1.parse("2012-12-21 00:00");
				vend=sf1.parse("2012-12-21 01:50");
			}
			
			out.println("<event>");
			out.println("<id><![CDATA["+(i+1)+"]]></id>");
			out.println("<text><![CDATA["+list.get(i).get("ClassName")+", "+list.get(i).get("chi_name")+"]]></text>");
			out.println("<start_date><![CDATA["+sf.format(begin.getTime())+" "+start+"]]></start_date>");
			out.println("<end_date><![CDATA["+sf.format(school_term_end)+" "+end+"]]></end_date>");
			out.println("<rec_type><![CDATA[week_1___"+week+"#no]]></rec_type>");
			out.println("<event_length><![CDATA["+(vend.getTime()-vstart.getTime())/1000+"]]></event_length>");
			out.println("<event_pid><![CDATA[0]]></event_pid>");
			out.println("<color><![CDATA[#555555]]></color>");
			out.println("<textColor><![CDATA[#FFFFFF]]></textColor>");
			out.println("<details><![CDATA["+list.get(i).get("ClassName")+"]]></details>");//參與者
			out.println("<members><![CDATA["+list.get(i).get("ClassName")+"選課同學]]></members>");
			out.println("<status_no><![CDATA[1]]></status_no>");			
			out.println("</event>");			
		}		
		//留校時間, 一五以64, 六日以72的時間為準
		list=df.sqlGet("SELECT '1'as CampusNo,  (if(e.week=6||e.week=7,'72','64'))as SchoolNo, e.week, e.period as begin, "
				+ "e.period as end, e.kind as ClassName, 'Office Hour'as chi_name FROM Empl_stay_info e WHERE e.idno='"+
				getSession().getAttribute("userid")+"'");
		
		for(int i=0; i<list.size(); i++){
			show=Calendar.getInstance();			
			show.setTime(begin.getTime());
			week=list.get(i).get("week").toString();
			
			show=setCalendar(show, week);			
			//計算起迄時間差 TODO 各部制時間表
			start=df.sqlGetStr("SELECT d.DSbegin FROM Dtimestamp d WHERE " +
			"Cidno IS NULL AND d.DSweek='"+week+"' AND d.DSreal="+list.get(i).get("begin"));
			end=df.sqlGetStr("SELECT d.DSend FROM Dtimestamp d WHERE " +
			"d.Cidno IS NULL AND d.DSweek='"+list.get(i).get("week")+"' AND d.DSreal="+list.get(i).get("end"));		
			
			//TODO 課程未排時間
			try{
				vstart=sf1.parse("1970-01-01 "+start);
				vend=sf1.parse("1970-01-01 "+end);
			}catch(Exception e){
				vstart=sf1.parse("2012-12-21 00:00");
				vend=sf1.parse("2012-12-21 01:50");
			}
			
			out.println("<event>");
			out.println("<id><![CDATA["+(i+1001)+"]]></id>");
			
			out.println("<start_date><![CDATA["+sf.format(begin.getTime())+" "+start+"]]></start_date>");
			out.println("<end_date><![CDATA["+sf.format(school_term_end)+" "+end+"]]></end_date>");
			out.println("<rec_type><![CDATA[week_1___"+week+"#no]]></rec_type>");
			out.println("<event_length><![CDATA["+(vend.getTime()-vstart.getTime())/1000+"]]></event_length>");
			out.println("<event_pid><![CDATA[0]]></event_pid>");
			if(list.get(i).get("ClassName").toString().equals("1")){
				out.println("<color><![CDATA[#2b547e]]></color>");//藍
				out.println("<text><![CDATA[留校時間]]></text>");
			}else{
				out.println("<color><![CDATA[#2a623d]]></color>");//綠
				out.println("<text><![CDATA[生活輔導]]></text>");
			}
			
			
			out.println("<textColor><![CDATA[#FFFFFF]]></textColor>");
			out.println("<details><![CDATA["+list.get(i).get("ClassName")+"]]></details>");//參與者
			out.println("<members><![CDATA["+list.get(i).get("ClassName")+"選課同學]]></members>");
			out.println("<status_no><![CDATA[1]]></status_no>");			
			out.println("</event>");			
		}
		
		//假期部份
		list=(List)getContext().getAttribute("holiday");		
		for(int i=0; i<list.size(); i++){
			show=Calendar.getInstance();
			show.setTime(sf.parse(list.get(i).get("Date").toString()));
			show.add(Calendar.DAY_OF_YEAR, 1);
			out.println("<event>");
			out.println("<id><![CDATA["+(i+101)+"]]></id>");
			out.println("<text><![CDATA["+bl.replaceXMLSymbol(list.get(i).get("Name").toString())+"]]></text>");
			out.println("<start_date><![CDATA["+list.get(i).get("Date")+" 00:00]]></start_date>");
			out.println("<end_date><![CDATA["+sf.format(show.getTime())+" 00:00]]></end_date>");				
			out.println("<color><![CDATA[#C70000]]></color>");
			out.println("<textColor><![CDATA[#FFFFFF]]></textColor>");			
			out.println("<status_no><![CDATA[1]]></status_no>");
			out.println("<details><![CDATA["+bl.replaceXMLSymbol(list.get(i).get("Name").toString())+"]]></details>");//參與者
			out.println("<members><![CDATA[]]></members>");			
			out.println("</event>");			
		}
		
		//個人行事曆
		begin.add(Calendar.MONTH, -3);//提前3個月
		list=df.sqlGet("SELECT * FROM Calendar WHERE (account='"+getSession().getAttribute("userid")+"'||account='all')AND " +
		""+"begin>='"+sf.format(begin.getTime())+"'");		
		for(int i=0; i<list.size(); i++){	
			
			out.println("<event>");
			out.println("<id><![CDATA["+list.get(i).get("no")+"]]></id>");
			out.println("<text><![CDATA["+bl.replaceXMLSymbol(list.get(i).get("name").toString())+"]]></text>");
			out.println("<start_date><![CDATA["+list.get(i).get("begin")+"]]></start_date>");
			out.println("<end_date><![CDATA["+list.get(i).get("end")+"]]></end_date>");
			
			/*循環*/
			if(((Map)list.get(i)).get("rec_type")!=null){
				//若為循環							
				out.println("<rec_type><![CDATA["+list.get(i).get("rec_type")+"]]></rec_type>");
				out.println("<event_length><![CDATA["+list.get(i).get("rec_event_length")+"]]></event_length>");
				out.println("<event_pid><![CDATA["+list.get(i).get("no")+"]]></event_pid>");
				//色彩
				out.println("<color><![CDATA[#555555]]></color>");
				out.println("<textColor><![CDATA[#FFFFFF]]></textColor>");
			}else{
				//非循環				
				if(((Map)list.get(i)).get("sender").equals(getSession().getAttribute("userid"))){
					out.println("<color><![CDATA[#5484ED]]></color>");
					out.println("<textColor><![CDATA[#FFFFFF]]></textColor>");
				}else{
					out.println("<color><![CDATA[#FFAD46]]></color>");
					out.println("<textColor><![CDATA[#555555]]></textColor>");
				}			
			}
			
			
			if(list.get(i).get("sender")==null){
				out.println("<color><![CDATA[#5484ED]]></color>");
				out.println("<textColor><![CDATA[#FFFFFF]]></textColor>");
			}else{
				if(list.get(i).get("sender").equals(getSession().getAttribute("userid"))){
					//本人
					out.println("<color><![CDATA[#5484ED]]></color>");
					out.println("<textColor><![CDATA[#FFFFFF]]></textColor>");
				}else{
					//受邀
					out.println("<color><![CDATA[#FFAD46]]></color>");
					out.println("<textColor><![CDATA[#555555]]></textColor>");
				}
			}
			
			out.println("<status_no><![CDATA[0]]></status_no>");
			try{
				out.println("<details><![CDATA["+bl.replaceXMLSymbol(list.get(i).get("note").toString())+"]]></details>");//參與者
			}catch(Exception e){
				out.println("<details><![CDATA[]]></details>");//參與者
			}
			
			out.println("<members><![CDATA["+bl.replaceXMLSymbol(list.get(i).get("members").toString())+"]]></members>");			
			out.println("</event>");
			
		}
		
		//排班
		/*
		show=Calendar.getInstance();
		show.add(Calendar.MONTH, +3);//後3個月		
		list=df.sqlGet("SELECT w.wdate, w.set_in, w.set_out FROM AMS_Workdate w WHERE w.wdate>='"+
		sf.format(begin.getTime())+"' AND w.wdate<'"+sf.format(show.getTime())+"' AND idno='"+getSession().getAttribute("userid")+"'");		
		for(int i=0; i<list.size(); i++){	
			if(list.get(i).get("set_in")!=null){				
				out.println("<event>");
				out.println("<id><![CDATA["+show.getTimeInMillis()+i+"]]></id>");
				out.println("<text><![CDATA[排班]]></text>");
				out.println("<start_date><![CDATA["+list.get(i).get("wdate")+" "+list.get(i).get("set_in")+"]]></start_date>");
				out.println("<end_date><![CDATA["+list.get(i).get("wdate")+" "+list.get(i).get("set_out")+"]]></end_date>");					
				out.println("<color><![CDATA[#5484ED]]></color>");
				out.println("<textColor><![CDATA[#FFFFFF]]></textColor>");				
				out.println("<status_no><![CDATA[1]]></status_no>");
				out.println("<details><![CDATA[]]></details>");//參與者
				out.println("<members><![CDATA[]]></members>");			
				out.println("</event>");				
			}			
		}
		*/ 
		
		out.println("</data>");		
		out.close();		
		return null;
	}
	
	private Calendar setCalendar(Calendar show, String week){				
		switch(week){
            case "1":show.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); break;
            case "2":show.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY); break;
            case "3":show.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY); break;
            case "4":show.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY); break;
            case "5":show.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY); break;
            case "6":show.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY); break;
            case "7":show.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); break;
        }		
		return show;
	}

}
