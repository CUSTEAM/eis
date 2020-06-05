package action.nabbr.print;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import action.BasePrintXmlAction;

public class PubNabbrSearchAction extends BasePrintXmlAction{
	
	public String building;
	public String floor;
	public String type;
	public String dept;
	public String unit;
	public String begin;
	public String end;
	
	private Float ap, tp;
	
	public String execute(){
		
		
		return SUCCESS;
	}
	
	public String printMap(){
		
		
		StringBuilder sb=new StringBuilder("SELECT(SELECT COUNT(DISTINCT rc_oid)FROM NabbrBorApp nba, Nabbr n, NabbrBoro nb WHERE nb.BorAppOid=nba.Oid AND ");
		
		if(!type.equals(""))sb.append("n.boro='"+type+"'AND ");		
		if(!dept.equals(""))sb.append("n.dept='"+dept+"' AND ");
		if(!begin.equals("")&&!end.equals(""))sb.append("(nb.boro_date>='2016-01-22 00:00' AND nb.boro_date<='2016-07-31 00:00')AND ");
		
		sb.append("n.room_id=nba.room_id AND n.dept=cd.id)as pro,(SELECT COUNT(*)FROM Nabbr WHERE dept=cd.id ");
		if(!building.equals(""))sb.append("AND building='"+building+"' ");
		if(!type.equals(""))sb.append("AND boro='"+type+"' ");
		
		sb.append(")as rom,cd.sname, cd.id FROM CODE_DEPT cd ");
		if(!dept.equals(""))sb.append("WHERE cd.id='"+dept+"' ");
		sb.append("HAVING rom>0 ORDER BY cd.id");
		
		request.setAttribute("rooMap", df.sqlGet(sb.toString()));
		
		
		return SUCCESS;
	}
	
	public String printMapOpt(){
		//TODO 慢的要死
		StringBuilder sb=new StringBuilder("SELECT cd.id, cd.sname FROM CODE_DEPT cd, Nabbr n WHERE n.dept=cd.id ");
		if(!dept.equals(""))sb.append("AND n.dept='"+dept+"'");
		sb.append("GROUP BY cd.id ORDER BY cd.id");
		List<Map>list=df.sqlGet(sb.toString());
		List<Map>code=df.sqlGet("SELECT id, name FROM CODE_BORROW ORDER BY id");		
		request.setAttribute("code", code);
		List<Map>ncode;
		StringBuilder ssb;
		for(int i=0; i<list.size(); i++){	
			ncode=new ArrayList();
			//ncode.addAll(code);
			for(int j=0; j<code.size(); j++){
				ssb=new StringBuilder("SELECT COUNT(DISTINCT nba.rc_oid)as pro,'"+code.get(j).get("id")+"'as rcode, '"+list.get(i).get("id")+"'as dept FROM "
				+ "Nabbr n, NabbrBorApp nba, NabbrBoro nb WHERE "
				+ "n.dept='"+list.get(i).get("id")+"'AND nba.rc_code='"+code.get(j).get("id")+"' AND n.room_id=nba.room_id ");
				if(!building.equals(""))ssb.append("AND n.building='"+building+"'");
				if(!begin.equals("")&&!end.equals(""))ssb.append("AND(nb.boro_date>='2016-01-22 00:00' AND nb.boro_date<='2016-07-31 00:00')");
				if(!type.equals(""))sb.append("AND n.boro='"+type+"' ");
				
				//list.get(i).put(code.get(j).get("id"), df.sqlGetMap(ssb.toString()));
				
				//System.out.println(ssb);
				//ncode.get(j).put("pro", df.sqlGetMap(ssb.toString()));
				ncode.addAll(df.sqlGet(ssb.toString()));
			}
			
			list.get(i).put("codes", ncode);
			//list.get(i).put("ncode", ncode);
			
		}
		
		request.setAttribute("optMap", list);
		
		return SUCCESS;
	}
	
	
	
	
	private List count(String SchoolType, int week, int day, int cls, int wday){
		
		StringBuilder sql;
		StringBuilder sqla=new StringBuilder();
		List<Map>list,tmp;
		
		if(!building.equals(""))sqla.append("AND n.building='"+building+"'");
		if(!floor.equals(""))sqla.append("AND n.floor='"+floor+"'");
		if(!dept.equals(""))sqla.append("AND n.dept='"+dept+"'");
		if(!unit.equals(""))sqla.append("AND n.unit='"+unit+"'");
		
		sql=new StringBuilder("SELECT n.room_id, IFNULL(cb.name,'') as bname, n.floor,"
		+ "IFNULL(cd.name,'') as dname,IFNULL(cu.name,'') as uname,n.name2 as nname,"
		+ "IFNULL((SELECT cname FROM empl e,NabbrInCharge nic WHERE e.Oid=nic.empl_oid AND "
		+ "room_oid=n.Oid LIMIT 1),'')as cname FROM((Nabbr n LEFT OUTER JOIN CODE_BUILDING cb "
		+ "ON n.building=cb.id)LEFT OUTER JOIN CODE_UNIT cu ON n.unit=cu.id)LEFT OUTER JOIN "
		+ "CODE_DEPT cd ON n.dept=cd.id WHERE n.boro LIKE'"+type+"%'");
		sql.append(sqla.toString());
		sql.append("ORDER BY n.room_id");
		list=df.sqlGet(sql.toString());		
		
		sql=new StringBuilder("SELECT n.room_id,(SELECT COUNT(*)FROM Dtime_class dc1, "
		+ "Class c1, Dtime d1 WHERE dc1.Dtime_oid=d1.Oid AND d1.depart_class=c1.ClassNo ");
		
		if(SchoolType!=null){		
			if(SchoolType.equals("D")){
				sql.append("AND dc1.week<=5 AND dc1.end<=10 ");
			}
			
			if(SchoolType.equals("N")){
				sql.append("AND dc1.week<=5 AND dc1.end>10 ");
			}
			
			if(SchoolType.equals("H")){
				sql.append("AND dc1.week>5 AND dc1.end<=12 ");
			}
		}
		
		
		
		sql.append("AND dc1.place=n.room_id) as dc,SUM((SELECT COUNT(*) "
		+ "FROM Seld WHERE Dtime_oid=d.Oid))as du,SUM( ((dc.end-dc.begin)+1))as dt FROM "
		+ "Nabbr n, Dtime_class dc, Dtime d, Class c WHERE d.depart_class=c.ClassNo ");
		if(SchoolType!=null){
			sql.append("AND c.SchoolType='"+SchoolType+"' ");
		}		
		sql.append("AND n.room_id=dc.place AND dc.Dtime_oid=d.Oid AND d.Sterm='"+getContext().getAttribute("school_term")+"'"
		+ "GROUP BY n.room_id");
		tmp=df.sqlGet(sql.toString());
		
		for(int i=0; i<tmp.size(); i++){			
			for(int j=0; j<list.size(); j++){				
				if(tmp.get(i).get("room_id").equals(list.get(j).get("room_id"))){					
					list.get(j).putAll(tmp.get(i));					
				}				
			}
		}
		
		sql=new StringBuilder("SELECT nba.room_id,COUNT(DISTINCT nba.rc_oid)as nc,"
		+ "COUNT(*)as nt,SUM(nba.users)as nu FROM Nabbr n, NabbrBorApp nba, NabbrBoro nb WHERE "
		+ "n.room_id=nba.room_id AND nb.BorAppOid=nba.Oid ");
		
		if(SchoolType!=null){
			if(SchoolType.equals("D")){
				sql.append("AND nb.week<=5 AND nb.cls<=10 ");
			}
			
			if(SchoolType.equals("N")){
				sql.append("AND nb.week<=5 AND nb.cls>10 ");
			}
			
			if(SchoolType.equals("H")){
				sql.append("AND nb.week>5 AND nb.cls<=12 ");
			}			
		}
		
		
		if(!begin.equals(""))sqla.append("AND nb.boro_date>'"+this.begin+"'");
		if(!end.equals(""))sqla.append("AND nb.boro_date<='"+this.end+"'");		
		sql.append(sqla.toString());
		sql.append("GROUP BY nba.room_id");
		tmp=df.sqlGet(sql.toString());
		
		for(int i=0; i<tmp.size(); i++){			
			for(int j=0; j<list.size(); j++){				
				if(tmp.get(i).get("room_id").equals(list.get(j).get("room_id"))){					
					list.get(j).putAll(tmp.get(i));					
				}				
			}
		}
		
		int du,nu,eff=0;
		float dt, nt, dp, np;
		int ftime=cls*wday*week;
		int ntime=day*wday;
		tp=0f;
		
		for(int i=0; i<list.size(); i++){
			
			if(list.get(i).get("dc")==null){
				list.get(i).put("dc", 0);
			}
			
			if(list.get(i).get("du")==null){
				du=0;
				list.get(i).put("du", 0);
			}else{
				du=Integer.parseInt(list.get(i).get("du").toString())*week;
				list.get(i).put("du", du);
			}
			
			if(list.get(i).get("dt")==null){
				dt=0;
				list.get(i).put("dt", 0);
			}else{
				dt=Math.round(Float.parseFloat(list.get(i).get("dt").toString()))*week;				
				list.get(i).put("dt", dt);				
			}	
			
			dp=dt/ftime;
			list.get(i).put("dp", dp);
			
			
			if(list.get(i).get("nc")==null){
				list.get(i).put("nc", 0);
			}
			if(list.get(i).get("nu")==null){
				nu=0;
				list.get(i).put("nu", 0);
			}else{
				nu=Integer.parseInt(list.get(i).get("nu").toString());
				list.get(i).put("nu", nu);
			}
			if(list.get(i).get("nt")==null){
				nt=0;
				list.get(i).put("nt", 0);
			}else{
				nt=Integer.parseInt(list.get(i).get("nt").toString());
				list.get(i).put("nt", nt);
			}			
			
			np=nt/ntime;
			list.get(i).put("np", np);			
			list.get(i).put("au", nu+du);
			list.get(i).put("at", nt+dt);
			
			if(dt==0&&nt==0){
				ap=0f;
				list.get(i).put("ap", 0);
			}else{				
				
				ap=dp+np;
				if(ap>1)ap=1f;
				list.get(i).put("ap", ap);			
				
				eff+=1;
				tp+=ap;
			}			
		}
		
		
		if(eff>0&&tp>0){
			//System.out.println(tp+"/"+eff+"="+tp/eff);
			tp=tp/eff;
		}else{
			tp=0f;
		}
				
		return list;
	}	
	
	public String print() throws IOException, ParseException{		
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		Date school_term_begin=(Date)getContext().getAttribute("date_school_term_begin");
		Date school_term_end=(Date)getContext().getAttribute("date_school_term_end");
		Date begin=sf.parse(this.begin);
		Date end=sf.parse(this.end);
		
		String showBegin=sf.format(begin);
		String showEnd=sf.format(end);
		
		Date realBegin, realEnd;
		
		//計算研究日數
		int nDay=(int) ((end.getTime()-begin.getTime())/(24*60*60*1000)); 
		
		//計算排課		
		if(begin.getTime()<school_term_begin.getTime()||begin.getTime()>school_term_end.getTime()){
			realBegin=school_term_begin;
		}else{
			realBegin=begin;
		}
		
		if(end.getTime()>school_term_end.getTime()||end.getTime()<school_term_begin.getTime()){
			realEnd=school_term_end;
		}else{
			realEnd=end;
		}
		
		//System.out.println(sf.format(realBegin)+","+sf.format(realBegin));
		
		int day=(int)((realEnd.getTime()-realBegin.getTime())/(24*60*60*1000)); 
		
		if(day>0&&day<7){day=7;}
		int week=Math.round(day/7);		
		
		Calendar b=Calendar.getInstance();
		Calendar e=Calendar.getInstance();
		
		b.setTime(begin);
		e.setTime(end);		
		
		Date date=new Date();
		response.setContentType("text/html; charset=UTF-8");
		xml2ods(response, getRequest(), date);
					
		
		
		
		Float ap;		
		PrintWriter out=response.getWriter();		
		List<Map>list=count(null, week, day, 14, 7);
		printInit(out);
		
		out.println (" <Worksheet ss:Name='全校'>");
		out.println ("  <Names>");
		out.println ("   <NamedRange ss:Name='_FilterDatabase' ss:RefersTo='=全校!R1C1:R2C5' ss:Hidden='1'/>");
		out.println ("   <NamedRange ss:Name='Print_Titles' ss:RefersTo='=全校!R1:R2'/>");
		out.println ("  </Names>");
		out.println ("  <Table ss:ExpandedColumnCount='18' ss:ExpandedRowCount='"+(list.size()+999)+"' x:FullColumns='1'");
		out.println ("   x:FullRows='1' ss:StyleID='s62' ss:DefaultColumnWidth='54'");
		out.println ("   ss:DefaultRowHeight='16.5'>");
		out.println ("   <Column ss:StyleID='s63' ss:AutoFitWidth='0' ss:Width='55.5'/>");
		out.println ("   <Column ss:StyleID='s64' ss:AutoFitWidth='0' ss:Width='55.5'/>");
		out.println ("   <Column ss:StyleID='s65' ss:AutoFitWidth='0' ss:Width='32.25'/>");
		out.println ("   <Column ss:StyleID='s66' ss:AutoFitWidth='0' ss:Width='87.75'/>");
		out.println ("   <Column ss:StyleID='s65' ss:AutoFitWidth='0' ss:Width='120'/>");
		out.println ("   <Column ss:StyleID='s67' ss:AutoFitWidth='0' ss:Width='48.75'/>");
		out.println ("   <Column ss:StyleID='s63' ss:AutoFitWidth='0' ss:Width='32.25'/>");
		out.println ("   <Column ss:StyleID='s64' ss:AutoFitWidth='0' ss:Width='39.75' ss:Span='1'/>");
		out.println ("   <Column ss:Index='10' ss:StyleID='s68' ss:AutoFitWidth='0' ss:Width='39.75'/>");
		out.println ("   <Column ss:StyleID='s69' ss:AutoFitWidth='0' ss:Width='32.25'/>");
		out.println ("   <Column ss:StyleID='s64' ss:AutoFitWidth='0' ss:Width='32.25'/>");
		out.println ("   <Column ss:StyleID='s64' ss:AutoFitWidth='0' ss:Width='39.75'/>");
		out.println ("   <Column ss:StyleID='s70' ss:AutoFitWidth='0' ss:Width='39.75'/>");
		out.println ("   <Column ss:StyleID='s64' ss:AutoFitWidth='0' ss:Width='39.75' ss:Span='1'/>");
		out.println ("   <Column ss:Index='17' ss:StyleID='s70' ss:AutoFitWidth='0' ss:Width='39.75'/>");
		out.println ("   <Column ss:StyleID='s68' ss:AutoFitWidth='0' ss:Width='39.75'/>");
		out.println ("   <Row ss:AutoFitHeight='0' ss:StyleID='s71'>");
		out.println ("    <Cell ss:MergeDown='1' ss:StyleID='m381791857408'><Data ss:Type='String'>編號</Data><NamedCell");
		out.println ("      ss:Name='_FilterDatabase'/><NamedCell ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeDown='1' ss:StyleID='m381791857428'><Data ss:Type='String'>大樓</Data><NamedCell");
		out.println ("      ss:Name='_FilterDatabase'/><NamedCell ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeDown='1' ss:StyleID='m381791857448'><Data ss:Type='String'>樓層</Data><NamedCell");
		out.println ("      ss:Name='_FilterDatabase'/><NamedCell ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeDown='1' ss:StyleID='m381791857468'><Data ss:Type='String'>單位系所</Data><NamedCell");
		out.println ("      ss:Name='_FilterDatabase'/><NamedCell ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeDown='1' ss:StyleID='m381791857488'><Data ss:Type='String'>教室名稱</Data><NamedCell");
		out.println ("      ss:Name='_FilterDatabase'/><NamedCell ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeDown='1' ss:StyleID='m381791857508'><Data ss:Type='String'>管理人&#10;代表</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeAcross='3' ss:StyleID='m381791861984'><Data ss:Type='String'>排課</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeAcross='3' ss:StyleID='m381791862004'><Data ss:Type='String'>研究</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeAcross='2' ss:StyleID='m381791862024'><Data ss:Type='String'>總計</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeDown='1' ss:StyleID='m381791862044'><Data ss:Type='String'>績效</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("   </Row>");
		out.println ("   <Row ss:AutoFitHeight='0' ss:StyleID='s71'>");
		out.println ("    <Cell ss:Index='7' ss:StyleID='s104'><Data ss:Type='String'>課程</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s105'><Data ss:Type='String'>人次</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s105'><Data ss:Type='String'>時數</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s106'><Data ss:Type='String'>使用率</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s107'><Data ss:Type='String'>計畫</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s105'><Data ss:Type='String'>人次</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s105'><Data ss:Type='String'>時數</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s108'><Data ss:Type='String'>使用率</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s105'><Data ss:Type='String'>人次</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s105'><Data ss:Type='String'>時數</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s108'><Data ss:Type='String'>使用率</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("   </Row>");		
		
		for(int i=0; i<list.size(); i++){
			out.println ("   <Row ss:AutoFitHeight='0'>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("room_id")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("bname")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+list.get(i).get("floor")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("dname")+list.get(i).get("uname")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("nname")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("cname")+"</Data></Cell>");			
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("dc")+"</Data></Cell>");
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("du")+"</Data></Cell>");
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("dt")+"</Data></Cell>");
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("dp")+"</Data></Cell>");
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("nc")+"</Data></Cell>");			
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("nu")+"</Data></Cell>");			
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("nt")+"</Data></Cell>");
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("np")+"</Data></Cell>");			
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("au")+"</Data></Cell>");
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("at")+"</Data></Cell>");			
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("ap")+"</Data></Cell>");			
			
			ap=Float.parseFloat(list.get(i).get("ap").toString());
			
			if(tp>0){
				out.println ("    <Cell><Data ss:Type='Number'>"+(ap/tp)+"</Data></Cell>");		
			}else{
				out.println ("    <Cell><Data ss:Type='Number'>0</Data></Cell>");		
			}	
			out.println ("   </Row>");
		}
		
		out.println ("  </Table>");
		out.println ("  <WorksheetOptions xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("   <PageSetup>");
		out.println ("    <Layout x:Orientation='Landscape'/>");
		out.println ("    <Header x:Margin='0.31496062992125984'");
		out.println ("     x:Data='&amp;C&amp;&quot;-,粗體&quot;&amp;24 "+sf.format(begin)+" 至 "+sf.format(end)+" 全校績效表'/>");
		out.println ("    <Footer x:Margin='0.31496062992125984' x:Data='&amp;L資料統計至 "+sf.format(date)+"&amp;R&amp;P/&amp;N'/>");
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
		out.println ("   <PageBreakZoom>160</PageBreakZoom>");
		out.println ("   <Selected/>");
		out.println ("   <FreezePanes/>");
		out.println ("   <FrozenNoSplit/>");
		out.println ("   <SplitHorizontal>2</SplitHorizontal>");
		out.println ("   <TopRowBottomPane>2</TopRowBottomPane>");
		out.println ("   <ActivePane>2</ActivePane>");
		out.println ("   <Panes>");
		out.println ("    <Pane>");
		out.println ("     <Number>3</Number>");
		out.println ("    </Pane>");
		out.println ("    <Pane>");
		out.println ("     <Number>2</Number>");
		out.println ("     <ActiveRow>0</ActiveRow>");
		out.println ("     <RangeSelection>R1C1:R2C1</RangeSelection>");
		out.println ("    </Pane>");
		out.println ("   </Panes>");
		out.println ("   <ProtectObjects>False</ProtectObjects>");
		out.println ("   <ProtectScenarios>False</ProtectScenarios>");
		out.println ("  </WorksheetOptions>");
		out.println (" </Worksheet>");		
		
		list=count("D", week, day, 10, 5);
		out.println (" <Worksheet ss:Name='日間'>");
		out.println ("  <Names>");
		out.println ("   <NamedRange ss:Name='_FilterDatabase' ss:RefersTo='=日間!R1C1:R2C5' ss:Hidden='1'/>");
		out.println ("   <NamedRange ss:Name='Print_Titles' ss:RefersTo='=日間!R1:R2'/>");
		out.println ("  </Names>");
		out.println ("  <Table ss:ExpandedColumnCount='18' ss:ExpandedRowCount='"+(list.size()+999)+"' x:FullColumns='1'");
		out.println ("   x:FullRows='1' ss:StyleID='s62' ss:DefaultColumnWidth='54'");
		out.println ("   ss:DefaultRowHeight='16.5'>");
		out.println ("   <Column ss:StyleID='s63' ss:AutoFitWidth='0' ss:Width='55.5'/>");
		out.println ("   <Column ss:StyleID='s64' ss:AutoFitWidth='0' ss:Width='55.5'/>");
		out.println ("   <Column ss:StyleID='s65' ss:AutoFitWidth='0' ss:Width='32.25'/>");
		out.println ("   <Column ss:StyleID='s66' ss:AutoFitWidth='0' ss:Width='87.75'/>");
		out.println ("   <Column ss:StyleID='s65' ss:AutoFitWidth='0' ss:Width='120'/>");
		out.println ("   <Column ss:StyleID='s67' ss:AutoFitWidth='0' ss:Width='48.75'/>");
		out.println ("   <Column ss:StyleID='s63' ss:AutoFitWidth='0' ss:Width='32.25'/>");
		out.println ("   <Column ss:StyleID='s64' ss:AutoFitWidth='0' ss:Width='39.75' ss:Span='1'/>");
		out.println ("   <Column ss:Index='10' ss:StyleID='s68' ss:AutoFitWidth='0' ss:Width='39.75'/>");
		out.println ("   <Column ss:StyleID='s69' ss:AutoFitWidth='0' ss:Width='32.25'/>");
		out.println ("   <Column ss:StyleID='s64' ss:AutoFitWidth='0' ss:Width='32.25'/>");
		out.println ("   <Column ss:StyleID='s64' ss:AutoFitWidth='0' ss:Width='39.75'/>");
		out.println ("   <Column ss:StyleID='s70' ss:AutoFitWidth='0' ss:Width='39.75'/>");
		out.println ("   <Column ss:StyleID='s64' ss:AutoFitWidth='0' ss:Width='39.75' ss:Span='1'/>");
		out.println ("   <Column ss:Index='17' ss:StyleID='s70' ss:AutoFitWidth='0' ss:Width='39.75'/>");
		out.println ("   <Column ss:StyleID='s68' ss:AutoFitWidth='0' ss:Width='39.75'/>");
		out.println ("   <Row ss:AutoFitHeight='0' ss:StyleID='s71'>");
		out.println ("    <Cell ss:MergeDown='1' ss:StyleID='m381791857408'><Data ss:Type='String'>編號</Data><NamedCell");
		out.println ("      ss:Name='_FilterDatabase'/><NamedCell ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeDown='1' ss:StyleID='m381791857428'><Data ss:Type='String'>大樓</Data><NamedCell");
		out.println ("      ss:Name='_FilterDatabase'/><NamedCell ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeDown='1' ss:StyleID='m381791857448'><Data ss:Type='String'>樓層</Data><NamedCell");
		out.println ("      ss:Name='_FilterDatabase'/><NamedCell ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeDown='1' ss:StyleID='m381791857468'><Data ss:Type='String'>單位系所</Data><NamedCell");
		out.println ("      ss:Name='_FilterDatabase'/><NamedCell ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeDown='1' ss:StyleID='m381791857488'><Data ss:Type='String'>教室名稱</Data><NamedCell");
		out.println ("      ss:Name='_FilterDatabase'/><NamedCell ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeDown='1' ss:StyleID='m381791857508'><Data ss:Type='String'>管理人&#10;代表</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeAcross='3' ss:StyleID='m381791861984'><Data ss:Type='String'>排課</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeAcross='3' ss:StyleID='m381791862004'><Data ss:Type='String'>研究</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeAcross='2' ss:StyleID='m381791862024'><Data ss:Type='String'>總計</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeDown='1' ss:StyleID='m381791862044'><Data ss:Type='String'>績效</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("   </Row>");
		out.println ("   <Row ss:AutoFitHeight='0' ss:StyleID='s71'>");
		out.println ("    <Cell ss:Index='7' ss:StyleID='s104'><Data ss:Type='String'>課程</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s105'><Data ss:Type='String'>人次</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s105'><Data ss:Type='String'>時數</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s106'><Data ss:Type='String'>使用率</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s107'><Data ss:Type='String'>計畫</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s105'><Data ss:Type='String'>人次</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s105'><Data ss:Type='String'>時數</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s108'><Data ss:Type='String'>使用率</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s105'><Data ss:Type='String'>人次</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s105'><Data ss:Type='String'>時數</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s108'><Data ss:Type='String'>使用率</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("   </Row>");		
		
		for(int i=0; i<list.size(); i++){			
			
			out.println ("   <Row ss:AutoFitHeight='0'>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("room_id")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("bname")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+list.get(i).get("floor")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("dname")+list.get(i).get("uname")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("nname")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("cname")+"</Data></Cell>");			
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("dc")+"</Data></Cell>");			
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("du")+"</Data></Cell>");			
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("dt")+"</Data></Cell>");
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("dp")+"</Data></Cell>");						
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("nc")+"</Data></Cell>");			
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("nu")+"</Data></Cell>");			
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("nt")+"</Data></Cell>");
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("np")+"</Data></Cell>");			
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("au")+"</Data></Cell>");
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("at")+"</Data></Cell>");			
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("ap")+"</Data></Cell>");
			
			ap=Float.parseFloat(list.get(i).get("ap").toString());
			
			if(tp>0){
				out.println ("    <Cell><Data ss:Type='Number'>"+(ap/tp)+"</Data></Cell>");		
			}else{
				out.println ("    <Cell><Data ss:Type='Number'>0</Data></Cell>");		
			}		
			out.println ("   </Row>");
		}		
		
		out.println ("  </Table>");
		out.println ("  <WorksheetOptions xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("   <PageSetup>");
		out.println ("    <Layout x:Orientation='Landscape'/>");
		out.println ("    <Header x:Margin='0.31496062992125984'");
		out.println ("     x:Data='&amp;C&amp;&quot;-,粗體&quot;&amp;24 "+sf.format(begin)+" 至 "+sf.format(end)+" 日間績效表'/>");
		out.println ("    <Footer x:Margin='0.31496062992125984' x:Data='&amp;L資料統計至 "+sf.format(date)+"&amp;R&amp;P/&amp;N'/>");
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
		out.println ("   <PageBreakZoom>160</PageBreakZoom>");
		out.println ("   <Selected/>");
		out.println ("   <FreezePanes/>");
		out.println ("   <FrozenNoSplit/>");
		out.println ("   <SplitHorizontal>2</SplitHorizontal>");
		out.println ("   <TopRowBottomPane>2</TopRowBottomPane>");
		out.println ("   <ActivePane>2</ActivePane>");
		out.println ("   <Panes>");
		out.println ("    <Pane>");
		out.println ("     <Number>3</Number>");
		out.println ("    </Pane>");
		out.println ("    <Pane>");
		out.println ("     <Number>2</Number>");
		out.println ("     <ActiveRow>0</ActiveRow>");
		out.println ("     <RangeSelection>R1C1:R2C1</RangeSelection>");
		out.println ("    </Pane>");
		out.println ("   </Panes>");
		out.println ("   <ProtectObjects>False</ProtectObjects>");
		out.println ("   <ProtectScenarios>False</ProtectScenarios>");
		out.println ("  </WorksheetOptions>");
		out.println (" </Worksheet>");		
		
		list=count("N", week, day, 4, 5);
		out.println (" <Worksheet ss:Name='夜間'>");
		out.println ("  <Names>");
		out.println ("   <NamedRange ss:Name='_FilterDatabase' ss:RefersTo='=夜間!R1C1:R2C5' ss:Hidden='1'/>");
		out.println ("   <NamedRange ss:Name='Print_Titles' ss:RefersTo='=夜間!R1:R2'/>");
		out.println ("  </Names>");
		out.println ("  <Table ss:ExpandedColumnCount='18' ss:ExpandedRowCount='"+(list.size()+999)+"' x:FullColumns='1'");
		out.println ("   x:FullRows='1' ss:StyleID='s62' ss:DefaultColumnWidth='54'");
		out.println ("   ss:DefaultRowHeight='16.5'>");
		out.println ("   <Column ss:StyleID='s63' ss:AutoFitWidth='0' ss:Width='55.5'/>");
		out.println ("   <Column ss:StyleID='s64' ss:AutoFitWidth='0' ss:Width='55.5'/>");
		out.println ("   <Column ss:StyleID='s65' ss:AutoFitWidth='0' ss:Width='32.25'/>");
		out.println ("   <Column ss:StyleID='s66' ss:AutoFitWidth='0' ss:Width='87.75'/>");
		out.println ("   <Column ss:StyleID='s65' ss:AutoFitWidth='0' ss:Width='120'/>");
		out.println ("   <Column ss:StyleID='s67' ss:AutoFitWidth='0' ss:Width='48.75'/>");
		out.println ("   <Column ss:StyleID='s63' ss:AutoFitWidth='0' ss:Width='32.25'/>");
		out.println ("   <Column ss:StyleID='s64' ss:AutoFitWidth='0' ss:Width='39.75' ss:Span='1'/>");
		out.println ("   <Column ss:Index='10' ss:StyleID='s68' ss:AutoFitWidth='0' ss:Width='39.75'/>");
		out.println ("   <Column ss:StyleID='s69' ss:AutoFitWidth='0' ss:Width='32.25'/>");
		out.println ("   <Column ss:StyleID='s64' ss:AutoFitWidth='0' ss:Width='32.25'/>");
		out.println ("   <Column ss:StyleID='s64' ss:AutoFitWidth='0' ss:Width='39.75'/>");
		out.println ("   <Column ss:StyleID='s70' ss:AutoFitWidth='0' ss:Width='39.75'/>");
		out.println ("   <Column ss:StyleID='s64' ss:AutoFitWidth='0' ss:Width='39.75' ss:Span='1'/>");
		out.println ("   <Column ss:Index='17' ss:StyleID='s70' ss:AutoFitWidth='0' ss:Width='39.75'/>");
		out.println ("   <Column ss:StyleID='s68' ss:AutoFitWidth='0' ss:Width='39.75'/>");
		out.println ("   <Row ss:AutoFitHeight='0' ss:StyleID='s71'>");
		out.println ("    <Cell ss:MergeDown='1' ss:StyleID='m381791857408'><Data ss:Type='String'>編號</Data><NamedCell");
		out.println ("      ss:Name='_FilterDatabase'/><NamedCell ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeDown='1' ss:StyleID='m381791857428'><Data ss:Type='String'>大樓</Data><NamedCell");
		out.println ("      ss:Name='_FilterDatabase'/><NamedCell ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeDown='1' ss:StyleID='m381791857448'><Data ss:Type='String'>樓層</Data><NamedCell");
		out.println ("      ss:Name='_FilterDatabase'/><NamedCell ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeDown='1' ss:StyleID='m381791857468'><Data ss:Type='String'>單位系所</Data><NamedCell");
		out.println ("      ss:Name='_FilterDatabase'/><NamedCell ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeDown='1' ss:StyleID='m381791857488'><Data ss:Type='String'>教室名稱</Data><NamedCell");
		out.println ("      ss:Name='_FilterDatabase'/><NamedCell ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeDown='1' ss:StyleID='m381791857508'><Data ss:Type='String'>管理人&#10;代表</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeAcross='3' ss:StyleID='m381791861984'><Data ss:Type='String'>排課</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeAcross='3' ss:StyleID='m381791862004'><Data ss:Type='String'>研究</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeAcross='2' ss:StyleID='m381791862024'><Data ss:Type='String'>總計</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeDown='1' ss:StyleID='m381791862044'><Data ss:Type='String'>績效</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("   </Row>");
		out.println ("   <Row ss:AutoFitHeight='0' ss:StyleID='s71'>");
		out.println ("    <Cell ss:Index='7' ss:StyleID='s104'><Data ss:Type='String'>課程</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s105'><Data ss:Type='String'>人次</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s105'><Data ss:Type='String'>時數</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s106'><Data ss:Type='String'>使用率</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s107'><Data ss:Type='String'>計畫</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s105'><Data ss:Type='String'>人次</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s105'><Data ss:Type='String'>時數</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s108'><Data ss:Type='String'>使用率</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s105'><Data ss:Type='String'>人次</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s105'><Data ss:Type='String'>時數</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s108'><Data ss:Type='String'>使用率</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("   </Row>");		
		
		for(int i=0; i<list.size(); i++){			
			
			out.println ("   <Row ss:AutoFitHeight='0'>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("room_id")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("bname")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+list.get(i).get("floor")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("dname")+list.get(i).get("uname")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("nname")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("cname")+"</Data></Cell>");			
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("dc")+"</Data></Cell>");			
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("du")+"</Data></Cell>");			
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("dt")+"</Data></Cell>");
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("dp")+"</Data></Cell>");						
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("nc")+"</Data></Cell>");			
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("nu")+"</Data></Cell>");			
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("nt")+"</Data></Cell>");
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("np")+"</Data></Cell>");			
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("au")+"</Data></Cell>");
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("at")+"</Data></Cell>");			
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("ap")+"</Data></Cell>");
			
			ap=Float.parseFloat(list.get(i).get("ap").toString());
			
			if(tp>0){
				out.println ("    <Cell><Data ss:Type='Number'>"+(ap/tp)+"</Data></Cell>");		
			}else{
				out.println ("    <Cell><Data ss:Type='Number'>0</Data></Cell>");		
			}
			out.println ("   </Row>");
		}
		
		out.println ("  </Table>");
		out.println ("  <WorksheetOptions xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("   <PageSetup>");
		out.println ("    <Layout x:Orientation='Landscape'/>");
		out.println ("    <Header x:Margin='0.31496062992125984'");
		out.println ("     x:Data='&amp;C&amp;&quot;-,粗體&quot;&amp;24 "+sf.format(begin)+" 至 "+sf.format(end)+" 夜間績效表'/>");
		out.println ("    <Footer x:Margin='0.31496062992125984' x:Data='&amp;L資料統計至 "+sf.format(date)+"&amp;R&amp;P/&amp;N'/>");
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
		out.println ("   <PageBreakZoom>160</PageBreakZoom>");
		out.println ("   <Selected/>");
		out.println ("   <FreezePanes/>");
		out.println ("   <FrozenNoSplit/>");
		out.println ("   <SplitHorizontal>2</SplitHorizontal>");
		out.println ("   <TopRowBottomPane>2</TopRowBottomPane>");
		out.println ("   <ActivePane>2</ActivePane>");
		out.println ("   <Panes>");
		out.println ("    <Pane>");
		out.println ("     <Number>3</Number>");
		out.println ("    </Pane>");
		out.println ("    <Pane>");
		out.println ("     <Number>2</Number>");
		out.println ("     <ActiveRow>0</ActiveRow>");
		out.println ("     <RangeSelection>R1C1:R2C1</RangeSelection>");
		out.println ("    </Pane>");
		out.println ("   </Panes>");
		out.println ("   <ProtectObjects>False</ProtectObjects>");
		out.println ("   <ProtectScenarios>False</ProtectScenarios>");
		out.println ("  </WorksheetOptions>");
		out.println (" </Worksheet>");
		
		
		/*out.println (" <Worksheet ss:Name='1B308'>");
		out.println ("  <Table ss:ExpandedColumnCount='1' ss:ExpandedRowCount='1' x:FullColumns='1'");
		out.println ("   x:FullRows='1' ss:DefaultColumnWidth='54' ss:DefaultRowHeight='16.5'>");
		out.println ("   <Row ss:AutoFitHeight='0'/>");
		out.println ("  </Table>");
		out.println ("  <WorksheetOptions xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("   <PageSetup>");
		out.println ("    <Header x:Margin='0.3'/>");
		out.println ("    <Footer x:Margin='0.3'/>");
		out.println ("    <PageMargins x:Bottom='0.75' x:Left='0.7' x:Right='0.7' x:Top='0.75'/>");
		out.println ("   </PageSetup>");
		out.println ("   <Unsynced/>");
		out.println ("   <Print>");
		out.println ("    <ValidPrinterInfo/>");
		out.println ("    <PaperSizeIndex>9</PaperSizeIndex>");
		out.println ("    <HorizontalResolution>-1</HorizontalResolution>");
		out.println ("    <VerticalResolution>-1</VerticalResolution>");
		out.println ("   </Print>");
		out.println ("   <ProtectObjects>False</ProtectObjects>");
		out.println ("   <ProtectScenarios>False</ProtectScenarios>");
		out.println ("  </WorksheetOptions>");
		out.println (" </Worksheet>");*/
		
		
		
		list=count("H", week, day, 12, 2);
		out.println (" <Worksheet ss:Name='假日'>");
		out.println ("  <Names>");
		out.println ("   <NamedRange ss:Name='_FilterDatabase' ss:RefersTo='=假日!R1C1:R2C5' ss:Hidden='1'/>");
		out.println ("   <NamedRange ss:Name='Print_Titles' ss:RefersTo='=假日!R1:R2'/>");
		out.println ("  </Names>");
		out.println ("  <Table ss:ExpandedColumnCount='18' ss:ExpandedRowCount='"+(list.size()+999)+"' x:FullColumns='1'");
		out.println ("   x:FullRows='1' ss:StyleID='s62' ss:DefaultColumnWidth='54'");
		out.println ("   ss:DefaultRowHeight='16.5'>");
		out.println ("   <Column ss:StyleID='s63' ss:AutoFitWidth='0' ss:Width='55.5'/>");
		out.println ("   <Column ss:StyleID='s64' ss:AutoFitWidth='0' ss:Width='55.5'/>");
		out.println ("   <Column ss:StyleID='s65' ss:AutoFitWidth='0' ss:Width='32.25'/>");
		out.println ("   <Column ss:StyleID='s66' ss:AutoFitWidth='0' ss:Width='87.75'/>");
		out.println ("   <Column ss:StyleID='s65' ss:AutoFitWidth='0' ss:Width='120'/>");
		out.println ("   <Column ss:StyleID='s67' ss:AutoFitWidth='0' ss:Width='48.75'/>");
		out.println ("   <Column ss:StyleID='s63' ss:AutoFitWidth='0' ss:Width='32.25'/>");
		out.println ("   <Column ss:StyleID='s64' ss:AutoFitWidth='0' ss:Width='39.75' ss:Span='1'/>");
		out.println ("   <Column ss:Index='10' ss:StyleID='s68' ss:AutoFitWidth='0' ss:Width='39.75'/>");
		out.println ("   <Column ss:StyleID='s69' ss:AutoFitWidth='0' ss:Width='32.25'/>");
		out.println ("   <Column ss:StyleID='s64' ss:AutoFitWidth='0' ss:Width='32.25'/>");
		out.println ("   <Column ss:StyleID='s64' ss:AutoFitWidth='0' ss:Width='39.75'/>");
		out.println ("   <Column ss:StyleID='s70' ss:AutoFitWidth='0' ss:Width='39.75'/>");
		out.println ("   <Column ss:StyleID='s64' ss:AutoFitWidth='0' ss:Width='39.75' ss:Span='1'/>");
		out.println ("   <Column ss:Index='17' ss:StyleID='s70' ss:AutoFitWidth='0' ss:Width='39.75'/>");
		out.println ("   <Column ss:StyleID='s68' ss:AutoFitWidth='0' ss:Width='39.75'/>");
		out.println ("   <Row ss:AutoFitHeight='0' ss:StyleID='s71'>");
		out.println ("    <Cell ss:MergeDown='1' ss:StyleID='m381791857408'><Data ss:Type='String'>編號</Data><NamedCell");
		out.println ("      ss:Name='_FilterDatabase'/><NamedCell ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeDown='1' ss:StyleID='m381791857428'><Data ss:Type='String'>大樓</Data><NamedCell");
		out.println ("      ss:Name='_FilterDatabase'/><NamedCell ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeDown='1' ss:StyleID='m381791857448'><Data ss:Type='String'>樓層</Data><NamedCell");
		out.println ("      ss:Name='_FilterDatabase'/><NamedCell ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeDown='1' ss:StyleID='m381791857468'><Data ss:Type='String'>單位系所</Data><NamedCell");
		out.println ("      ss:Name='_FilterDatabase'/><NamedCell ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeDown='1' ss:StyleID='m381791857488'><Data ss:Type='String'>教室名稱</Data><NamedCell");
		out.println ("      ss:Name='_FilterDatabase'/><NamedCell ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeDown='1' ss:StyleID='m381791857508'><Data ss:Type='String'>管理人&#10;代表</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeAcross='3' ss:StyleID='m381791861984'><Data ss:Type='String'>排課</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeAcross='3' ss:StyleID='m381791862004'><Data ss:Type='String'>研究</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeAcross='2' ss:StyleID='m381791862024'><Data ss:Type='String'>總計</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:MergeDown='1' ss:StyleID='m381791862044'><Data ss:Type='String'>績效</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("   </Row>");
		out.println ("   <Row ss:AutoFitHeight='0' ss:StyleID='s71'>");
		out.println ("    <Cell ss:Index='7' ss:StyleID='s104'><Data ss:Type='String'>課程</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s105'><Data ss:Type='String'>人次</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s105'><Data ss:Type='String'>時數</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s106'><Data ss:Type='String'>使用率</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s107'><Data ss:Type='String'>計畫</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s105'><Data ss:Type='String'>人次</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s105'><Data ss:Type='String'>時數</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s108'><Data ss:Type='String'>使用率</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s105'><Data ss:Type='String'>人次</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s105'><Data ss:Type='String'>時數</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("    <Cell ss:StyleID='s108'><Data ss:Type='String'>使用率</Data><NamedCell");
		out.println ("      ss:Name='Print_Titles'/></Cell>");
		out.println ("   </Row>");		
		
		for(int i=0; i<list.size(); i++){			
			
			out.println ("   <Row ss:AutoFitHeight='0'>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("room_id")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("bname")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+list.get(i).get("floor")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("dname")+list.get(i).get("uname")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("nname")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("cname")+"</Data></Cell>");			
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("dc")+"</Data></Cell>");			
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("du")+"</Data></Cell>");			
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("dt")+"</Data></Cell>");
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("dp")+"</Data></Cell>");						
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("nc")+"</Data></Cell>");			
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("nu")+"</Data></Cell>");			
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("nt")+"</Data></Cell>");
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("np")+"</Data></Cell>");			
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("au")+"</Data></Cell>");
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("at")+"</Data></Cell>");			
			out.println ("<Cell><Data ss:Type='Number'>"+list.get(i).get("ap")+"</Data></Cell>");
			
			ap=Float.parseFloat(list.get(i).get("ap").toString());
			if(tp>0){
				out.println ("    <Cell><Data ss:Type='Number'>"+(ap/tp)+"</Data></Cell>");		
			}else{
				out.println ("    <Cell><Data ss:Type='Number'>0</Data></Cell>");		
			}
				
			out.println ("   </Row>");
		}		
		out.println ("  </Table>");
		out.println ("  <WorksheetOptions xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("   <PageSetup>");
		out.println ("    <Layout x:Orientation='Landscape'/>");
		out.println ("    <Header x:Margin='0.31496062992125984'");
		out.println ("     x:Data='&amp;C&amp;&quot;-,粗體&quot;&amp;24 "+sf.format(begin)+" 至 "+sf.format(end)+" 假日績效表'/>");
		out.println ("    <Footer x:Margin='0.31496062992125984' x:Data='&amp;L資料統計至 "+sf.format(date)+"&amp;R&amp;P/&amp;N'/>");
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
		out.println ("   <PageBreakZoom>160</PageBreakZoom>");
		out.println ("   <Selected/>");
		out.println ("   <FreezePanes/>");
		out.println ("   <FrozenNoSplit/>");
		out.println ("   <SplitHorizontal>2</SplitHorizontal>");
		out.println ("   <TopRowBottomPane>2</TopRowBottomPane>");
		out.println ("   <ActivePane>2</ActivePane>");
		out.println ("   <Panes>");
		out.println ("    <Pane>");
		out.println ("     <Number>3</Number>");
		out.println ("    </Pane>");
		out.println ("    <Pane>");
		out.println ("     <Number>2</Number>");
		out.println ("     <ActiveRow>0</ActiveRow>");
		out.println ("     <RangeSelection>R1C1:R2C1</RangeSelection>");
		out.println ("    </Pane>");
		out.println ("   </Panes>");
		out.println ("   <ProtectObjects>False</ProtectObjects>");
		out.println ("   <ProtectScenarios>False</ProtectScenarios>");
		out.println ("  </WorksheetOptions>");
		out.println (" </Worksheet>");
		
		List<Map>tmp;
		Map tmp1;
		for(int i=0; i<list.size(); i++){
			tmp=df.sqlGet("SELECT nba.rc_code,nba.rc_oid, cb.name as type, cb.name_field, "
			+ "cb.rc_table FROM CODE_BORROW cb, Nabbr n, NabbrBorApp nba, NabbrBoro nb "
			+ "WHERE cb.id=nba.rc_code AND nb.BorAppOid=nba.Oid AND"
			+ "(nb.boro_date>='"+showBegin+"' AND nb.boro_date<='"+showEnd+"')AND n.room_id=nba.room_id "
			+ "AND n.room_id='"+list.get(i).get("room_id")+"' GROUP BY nba.rc_oid, nba.rc_code");
			
			if(tmp.size()<1)continue;
			
			for(int j=0; j<tmp.size(); j++){
				tmp1=df.sqlGetMap("SELECT r.school_year, r."+tmp.get(j).get("name_field")+", e.cname "
				+ "FROM "+tmp.get(j).get("rc_table")+" as r LEFT OUTER JOIN empl e ON r.idno=e.idno WHERE r.Oid="+tmp.get(j).get("rc_oid"));
				if(tmp1==null){
					tmp.get(j).put("school_year", 0);
					tmp.get(j).put(tmp.get(j).get("name_field"), "專案已被刪除");
				}else{
					tmp.get(j).putAll(tmp1);
				}
				
				
				
				
			}			
			
			/*for(int j=0; j<tmp.size(); j++){
				System.out.println(tmp.get(j));
			}*/
			
			tmp=bm.sortListByKey(tmp, "school_year", true);			
			out.println (" <Worksheet ss:Name='"+list.get(i).get("room_id")+"'>");
			
			out.println ("  <Names>");
			out.println ("   <NamedRange ss:Name='_FilterDatabase' ss:RefersTo='=1B308!R1C1:R2C5' ss:Hidden='1'/>");
			out.println ("   <NamedRange ss:Name='Print_Titles' ss:RefersTo='=1B308!R1:R2'/>");
			out.println ("  </Names>");
			
			out.println ("  <Table ss:ExpandedColumnCount='5' ss:ExpandedRowCount='9999' x:FullColumns='1'");
			out.println ("   x:FullRows='1' ss:StyleID='s162' ss:DefaultColumnWidth='54'");
			out.println ("   ss:DefaultRowHeight='23.25'>");
			out.println ("   <Column ss:StyleID='s172' ss:AutoFitWidth='0' ss:Width='44.25'/>");
			out.println ("   <Column ss:StyleID='s173' ss:AutoFitWidth='0' ss:Width='121.5'/>");
			out.println ("   <Column ss:StyleID='s174' ss:AutoFitWidth='0' ss:Width='63'/>");
			out.println ("   <Column ss:StyleID='s175' ss:AutoFitWidth='0' ss:Width='361.5'/>");
			out.println ("   <Column ss:StyleID='s164' ss:AutoFitWidth='0' ss:Width='45'/>");
			out.println ("   <Row ss:AutoFitHeight='0' ss:Height='24'>");
			out.println ("    <Cell ss:StyleID='s168'><Data ss:Type='String'>學年度</Data><NamedCell ss:Name='Print_Titles'/><NamedCell ss:Name='Print_Area'/></Cell>");
			out.println ("    <Cell ss:StyleID='s169'><Data ss:Type='String'>研究類型</Data><NamedCell ss:Name='Print_Titles'/><NamedCell ss:Name='Print_Area'/></Cell>");
			out.println ("    <Cell ss:StyleID='s170'><Data ss:Type='String'>主持人</Data><NamedCell ss:Name='Print_Titles'/><NamedCell ss:Name='Print_Area'/></Cell>");
			out.println ("    <Cell ss:StyleID='s171'><Data ss:Type='String'>研究名稱</Data><NamedCell ss:Name='Print_Titles'/><NamedCell ss:Name='Print_Area'/></Cell>");
			out.println ("    <Cell ss:StyleID='s166'><NamedCell ss:Name='Print_Titles'/></Cell>");
			out.println ("   </Row>");
			
			for(int j=0; j<tmp.size(); j++){
				if(tmp.get(j).get("school_year").toString().equals("0"))continue;
				out.println ("   <Row ss:AutoFitHeight='0'>");
				out.println ("    <Cell><Data ss:Type='String'>"+tmp.get(j).get("school_year")+"</Data><NamedCell ss:Name='Print_Area'/></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+tmp.get(j).get("type")+"</Data><NamedCell ss:Name='Print_Area'/></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+tmp.get(j).get("cname")+"</Data><NamedCell ss:Name='Print_Area'/></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+tmp.get(j).get(tmp.get(j).get("name_field"))+"</Data><NamedCell");
				out.println ("      ss:Name='Print_Area'/></Cell>");
				out.println ("   </Row>");
				
			}
			
			
			
			out.println ("  </Table>");
			
			
			
			
			
			out.println ("  <WorksheetOptions xmlns='urn:schemas-microsoft-com:office:excel'>");
			out.println ("   <PageSetup>");
			out.println ("    <Header x:Margin='0.31496062992125984' x:Data='&amp;L&amp;&quot;微軟正黑體,標準&quot;&amp;16管理人代表: "+list.get(i).get("cname")+"&amp;C&amp;&quot;微軟正黑體,標準&quot;&amp;20 "+list.get(i).get("room_id")+"教室研究成果&amp;R&amp;&quot;微軟正黑體,標準&quot;&amp;10統計期間&#10;"+sf.format(begin)+" 至 "+sf.format(end)+"&#10;'/>");
			out.println ("    <Footer x:Margin='0.31496062992125984' x:Data='&amp;C&amp;&quot;微軟正黑體,標準&quot;&amp;P/&amp;N&amp;R&amp;&quot;微軟正黑體,標準&quot;製表時間  "+sf.format(date)+"'/>");
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
			out.println ("     <ActiveRow>13</ActiveRow>");
			out.println ("     <ActiveCol>3</ActiveCol>");
			out.println ("    </Pane>");
			out.println ("   </Panes>");
			out.println ("   <ProtectObjects>False</ProtectObjects>");
			out.println ("   <ProtectScenarios>False</ProtectScenarios>");
			out.println ("  </WorksheetOptions>");
			out.println (" </Worksheet>");
		
		}
		
		
		/*
		out.println (" <Worksheet ss:Name='1B308'>");
		out.println ("  <Names>");
		out.println ("   <NamedRange ss:Name='Print_Area' ss:RefersTo='='1B308'!C1:C4'/>");
		out.println ("   <NamedRange ss:Name='Print_Titles' ss:RefersTo='='1B308'!R1'/>");
		out.println ("  </Names>");
		
		out.println ("  <Table ss:ExpandedColumnCount='1' ss:ExpandedRowCount='1' x:FullColumns='1'");
		out.println ("   x:FullRows='1' ss:DefaultColumnWidth='54' ss:DefaultRowHeight='16.5'>");
		out.println ("   <Row ss:AutoFitHeight='0'/>");
		out.println ("  </Table>");
		out.println ("  <Table ss:ExpandedColumnCount='5' ss:ExpandedRowCount='34' x:FullColumns='1'");
		out.println ("   x:FullRows='1' ss:StyleID='s162' ss:DefaultColumnWidth='54'");
		out.println ("   ss:DefaultRowHeight='23.25'>");
		out.println ("   <Column ss:StyleID='s172' ss:AutoFitWidth='0' ss:Width='44.25'/>");
		out.println ("   <Column ss:StyleID='s173' ss:AutoFitWidth='0' ss:Width='121.5'/>");
		out.println ("   <Column ss:StyleID='s174' ss:AutoFitWidth='0' ss:Width='63'/>");
		out.println ("   <Column ss:StyleID='s175' ss:AutoFitWidth='0' ss:Width='361.5'/>");
		out.println ("   <Column ss:StyleID='s164' ss:AutoFitWidth='0' ss:Width='45'/>");
		out.println ("   <Row ss:AutoFitHeight='0' ss:Height='24'>");
		out.println ("    <Cell ss:StyleID='s168'><Data ss:Type='String'>學年度</Data><NamedCell ss:Name='Print_Titles'/><NamedCell ss:Name='Print_Area'/></Cell>");
		out.println ("    <Cell ss:StyleID='s169'><Data ss:Type='String'>研究類型</Data><NamedCell ss:Name='Print_Titles'/><NamedCell ss:Name='Print_Area'/></Cell>");
		out.println ("    <Cell ss:StyleID='s170'><Data ss:Type='String'>主持人</Data><NamedCell ss:Name='Print_Titles'/><NamedCell ss:Name='Print_Area'/></Cell>");
		out.println ("    <Cell ss:StyleID='s171'><Data ss:Type='String'>研究名稱</Data><NamedCell ss:Name='Print_Titles'/><NamedCell ss:Name='Print_Area'/></Cell>");
		out.println ("    <Cell ss:StyleID='s166'><NamedCell ss:Name='Print_Titles'/></Cell>");
		out.println ("   </Row>");
		out.println ("   <Row ss:AutoFitHeight='0'>");
		out.println ("    <Cell><Data ss:Type='Number'>103</Data><NamedCell ss:Name='Print_Area'/></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>期刊論文發表</Data><NamedCell ss:Name='Print_Area'/></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>蕭國裕</Data><NamedCell ss:Name='Print_Area'/></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>連桿元件特別是腳踏車區曲柄的製法與製成</Data><NamedCell");
		out.println ("      ss:Name='Print_Area'/></Cell>");
		out.println ("   </Row>");
		
		out.println ("  </Table>");
		
		
		out.println ("  <WorksheetOptions xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("   <PageSetup>");
		out.println ("    <Header x:Margin='0.31496062992125984'");
		out.println ("     x:Data='&amp;C&amp;&quot;微軟正黑體,標準&quot;&amp;20 1B308教室研究成果&amp;R&amp;&quot;微軟正黑體,標準&quot;&amp;10統計期間&#10;2016-01-01至2016-07-31&#10;'/>");
		out.println ("    <Footer x:Margin='0.31496062992125984'");
		out.println ("     x:Data='&amp;C&amp;&quot;微軟正黑體,標準&quot;&amp;P/&amp;N&amp;R&amp;&quot;微軟正黑體,標準&quot;結算日期 2016-06-07'/>");
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
		out.println ("     <ActiveRow>13</ActiveRow>");
		out.println ("     <ActiveCol>3</ActiveCol>");
		out.println ("    </Pane>");
		out.println ("   </Panes>");
		out.println ("   <ProtectObjects>False</ProtectObjects>");
		out.println ("   <ProtectScenarios>False</ProtectScenarios>");
		out.println ("  </WorksheetOptions>");
		*/
		
		
		
		out.println ("</Workbook>");
		out.close();
		out.flush();
		
		
		
		
		
		return null;
	}
	
	private void printInit(PrintWriter out){
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
		out.println ("  <LastPrinted>2016-03-03T06:23:56Z</LastPrinted>");
		out.println ("  <Created>2016-03-02T01:33:34Z</Created>");
		out.println ("  <LastSaved>2016-03-03T02:49:27Z</LastSaved>");
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
		out.println ("  <Style ss:ID='m381791861984'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='12'");
		out.println ("    ss:Color='#000000' ss:Bold='1'/>");
		out.println ("   <Interior ss:Color='#D9D9D9' ss:Pattern='Solid'/>");
		out.println ("   <NumberFormat/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m381791862004'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='12'");
		out.println ("    ss:Color='#000000' ss:Bold='1'/>");
		out.println ("   <Interior ss:Color='#D9D9D9' ss:Pattern='Solid'/>");
		out.println ("   <NumberFormat/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m381791862024'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='12'");
		out.println ("    ss:Color='#000000' ss:Bold='1'/>");
		out.println ("   <Interior ss:Color='#D9D9D9' ss:Pattern='Solid'/>");
		out.println ("   <NumberFormat/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m381791862044'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='12'");
		out.println ("    ss:Color='#000000' ss:Bold='1'/>");
		out.println ("   <Interior ss:Color='#D9D9D9' ss:Pattern='Solid'/>");
		out.println ("   <NumberFormat ss:Format='0%'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m381791857408'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='12'");
		out.println ("    ss:Color='#000000' ss:Bold='1'/>");
		out.println ("   <Interior ss:Color='#D9D9D9' ss:Pattern='Solid'/>");
		out.println ("   <NumberFormat/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m381791857428'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='12'");
		out.println ("    ss:Color='#000000' ss:Bold='1'/>");
		out.println ("   <Interior ss:Color='#D9D9D9' ss:Pattern='Solid'/>");
		out.println ("   <NumberFormat/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m381791857448'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='12'");
		out.println ("    ss:Color='#000000' ss:Bold='1'/>");
		out.println ("   <Interior ss:Color='#D9D9D9' ss:Pattern='Solid'/>");
		out.println ("   <NumberFormat/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m381791857468'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='12'");
		out.println ("    ss:Color='#000000' ss:Bold='1'/>");
		out.println ("   <Interior ss:Color='#D9D9D9' ss:Pattern='Solid'/>");
		out.println ("   <NumberFormat ss:Format='@'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m381791857488'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='12'");
		out.println ("    ss:Color='#000000' ss:Bold='1'/>");
		out.println ("   <Interior ss:Color='#D9D9D9' ss:Pattern='Solid'/>");
		out.println ("   <NumberFormat/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='m381791857508'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center' ss:WrapText='1'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='12'");
		out.println ("    ss:Color='#000000' ss:Bold='1'/>");
		out.println ("   <Interior ss:Color='#D9D9D9' ss:Pattern='Solid'/>");
		out.println ("   <NumberFormat/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s62'>");
		out.println ("   <Borders/>");
		out.println ("   <NumberFormat/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s63'>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <NumberFormat/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s64'>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <NumberFormat/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s65'>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <NumberFormat/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s66'>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <NumberFormat/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s67'>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <NumberFormat/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s68'>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <NumberFormat ss:Format='0%'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s69'>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <NumberFormat/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s70'>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <NumberFormat ss:Format='0%'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s71'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center'/>");
		out.println ("   <Borders/>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='12'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("   <Interior ss:Color='#D9D9D9' ss:Pattern='Solid'/>");
		out.println ("   <NumberFormat/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s104'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='12'");
		out.println ("    ss:Color='#000000' ss:Bold='1'/>");
		out.println ("   <Interior ss:Color='#D9D9D9' ss:Pattern='Solid'/>");
		out.println ("   <NumberFormat/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s105'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='12'");
		out.println ("    ss:Color='#000000' ss:Bold='1'/>");
		out.println ("   <Interior ss:Color='#D9D9D9' ss:Pattern='Solid'/>");
		out.println ("   <NumberFormat/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s106'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='12'");
		out.println ("    ss:Color='#000000' ss:Bold='1'/>");
		out.println ("   <Interior ss:Color='#D9D9D9' ss:Pattern='Solid'/>");
		out.println ("   <NumberFormat ss:Format='0%'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s107'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='12'");
		out.println ("    ss:Color='#000000' ss:Bold='1'/>");
		out.println ("   <Interior ss:Color='#D9D9D9' ss:Pattern='Solid'/>");
		out.println ("   <NumberFormat/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s108'>");
		out.println ("   <Alignment ss:Horizontal='Center' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='12'");
		out.println ("    ss:Color='#000000' ss:Bold='1'/>");
		out.println ("   <Interior ss:Color='#D9D9D9' ss:Pattern='Solid'/>");
		out.println ("   <NumberFormat ss:Format='0%'/>");
		out.println ("  </Style>");
		
		
		out.println ("  <Style ss:ID='s162'>");
		out.println ("   <Alignment ss:Vertical='Top'/>");
		out.println ("   <Font ss:FontName='微軟正黑體' x:CharSet='136' x:Family='Swiss' ss:Size='18'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("   <NumberFormat ss:Format='@'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s164'>");
		out.println ("   <Alignment ss:Vertical='Top' ss:ShrinkToFit='1'/>");
		out.println ("   <Font ss:FontName='微軟正黑體' x:CharSet='136' x:Family='Swiss' ss:Size='18'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("   <NumberFormat ss:Format='@'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s166'>");
		out.println ("   <Alignment ss:Vertical='Top' ss:ShrinkToFit='1'/>");
		out.println ("   <Font ss:FontName='微軟正黑體' x:CharSet='136' x:Family='Swiss' ss:Size='18'");
		out.println ("    ss:Color='#000000' ss:Bold='1'/>");
		out.println ("   <NumberFormat ss:Format='@'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s168'>");
		out.println ("   <Alignment ss:Horizontal='Right' ss:Vertical='Top'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='微軟正黑體' x:CharSet='136' x:Family='Swiss' ss:Size='18'");
		out.println ("    ss:Color='#000000' ss:Bold='1'/>");
		out.println ("   <NumberFormat ss:Format='@'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s169'>");
		out.println ("   <Alignment ss:Vertical='Top' ss:ShrinkToFit='1'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='微軟正黑體' x:CharSet='136' x:Family='Swiss' ss:Size='18'");
		out.println ("    ss:Color='#000000' ss:Bold='1'/>");
		out.println ("   <NumberFormat ss:Format='@'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s170'>");
		out.println ("   <Alignment ss:Vertical='Top'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='微軟正黑體' x:CharSet='136' x:Family='Swiss' ss:Size='18'");
		out.println ("    ss:Color='#000000' ss:Bold='1'/>");
		out.println ("   <NumberFormat ss:Format='@'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s171'>");
		out.println ("   <Alignment ss:Vertical='Top' ss:ShrinkToFit='1'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='微軟正黑體' x:CharSet='136' x:Family='Swiss' ss:Size='18'");
		out.println ("    ss:Color='#000000' ss:Bold='1'/>");
		out.println ("   <NumberFormat ss:Format='@'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s172'>");
		out.println ("   <Alignment ss:Horizontal='Right' ss:Vertical='Top'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='微軟正黑體' x:CharSet='136' x:Family='Swiss' ss:Size='18'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("   <NumberFormat ss:Format='@'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s173'>");
		out.println ("   <Alignment ss:Vertical='Top' ss:ShrinkToFit='1'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='微軟正黑體' x:CharSet='136' x:Family='Swiss' ss:Size='18'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("   <NumberFormat ss:Format='@'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s174'>");
		out.println ("   <Alignment ss:Vertical='Top'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='微軟正黑體' x:CharSet='136' x:Family='Swiss' ss:Size='18'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("   <NumberFormat ss:Format='@'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s175'>");
		out.println ("   <Alignment ss:Vertical='Top' ss:ShrinkToFit='1'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Left' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Right' ss:LineStyle='Continuous' ss:Weight='2'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Continuous' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='微軟正黑體' x:CharSet='136' x:Family='Swiss' ss:Size='18'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("   <NumberFormat ss:Format='@'/>");
		out.println ("  </Style>");
		
		
		out.println (" </Styles>");
	}

}
