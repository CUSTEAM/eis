package action.nabbr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import action.BaseAction;
import model.Message;
import model.NabbrBorApp;
import model.NabbrBoro;

/**
 * 教室使用登記
 * @author shawn
 *
 */
public class NabbrBoroRegisterAction extends BaseAction{
	
	public String workday[], cls[], propname[], Oid, date, beginDate, endDate, users, RcTableCode, room_id;
	
	public String execute(){		
		List<Map>list=(List)getContext().getAttribute("CODE_BORROW");
		for(int i=0; i<list.size(); i++){
			
			list.get(i).put("rc", 
					df.sqlGet("SELECT school_year, Oid, "+list.get(i).get("name_field")+" as title,"
					+ "(SELECT COUNT(*) FROM NabbrBorApp nba1, NabbrBoro nb1 WHERE "
					+ "nba1.rc_code='"+list.get(i).get("id")+"' AND nba1.rc_oid="+list.get(i).get("rc_table")+".Oid AND nba1.Oid=nb1.BorAppOid)as cnt "
					+ "FROM "+list.get(i).get("rc_table")+" WHERE idno='"+getSession().getAttribute("userid")+"'ORDER BY school_year DESC"));
			
			
		}
		
		request.setAttribute("rclist", list);	
		return SUCCESS;
	}
	
	/**
	 * 新增記錄
	 * @return
	 */
	public String create(){		
		Message msg=new Message();
		String code[], room[];
		//Date db, de;		
		if(RcTableCode.equals("")){
			msg.setError("未指定專案");
			this.savMessage(msg);
			return execute();
		}
		code=rcCode(RcTableCode);
		
		if(room_id.indexOf(",")<0){
			msg.setError("未指定教室");
			this.savMessage(msg);
			return execute();
		}
		
		List<Map>range;
		try {
			range=parseDate(beginDate, endDate);
			if(range==null){
				msg.setError("日期不完整或衝突");
				this.savMessage(msg);
				return execute();
			}
		} catch (ParseException e) {
			msg.setError("日期不完整或衝突");
			this.savMessage(msg);
			return execute();
		}
		
		
		
		int u=0;
		try{
			u=Integer.parseInt(users);
		}catch(Exception e){
			msg.setError("人數有誤");
			this.savMessage(msg);
			return execute();
		}
		
		NabbrBorApp app=new NabbrBorApp();
		app.setBorrower(getSession().getAttribute("userid").toString());
		app.setDate(new Date());
		app.setRc_code(code[0]);
		app.setRc_oid(Integer.parseInt(code[1]));
		app.setRoom_id(room_id.substring(0, room_id.indexOf(",")));
		app.setUsers(u);
		df.update(app);
		
		//設備
		//List<Map>props=new ArrayList();
		if(propname!=null)
		for(int i=0; i<propname.length; i++){			
			df.exSql("INSERT INTO NabbrBorPro(NabbrBorAppOid, propname)VALUES("+app.getOid()+",'"+propname[i]+"');");
		}
		
		NabbrBoro bor;	
		int g=0;
		
		String day[];
		int w;
		//System.out.println(range.size());
		for(int i=0; i<range.size(); i++){		
			
			for(int j=0; j<workday.length; j++){				
				day=workday[j].split("-");
				w=Integer.parseInt(day[0]);
				if(w==7)w=0;
				System.out.println(range.get(i).get("week")+", "+String.valueOf(w));
				if(range.get(i).get("week").toString().equals(String.valueOf(w))){					
					bor=new NabbrBoro();
					bor.setBorAppOid(app.getOid());
					bor.setBoro_date((Date)range.get(i).get("date"));
					if(w==0)w=7;
					bor.setWeek(w);
					bor.setCls(Integer.parseInt(day[1]));					
					df.update(bor);
					
					
					g++;
				}
			}
		}
		
		msg.setSuccess("時間範圍 "+range.size()+"日, 總計"+g+"時數");
		this.savMessage(msg);
		return search();
	}
	
	private boolean checkDay(int w){
		
		
		
		
		
		return true;
	}
	
	public String search(){		
		request.removeAttribute("cls");
		/*StringBuilder sb=new StringBuilder("SELECT a.*, e.cname,"
		+ "(SELECT COUNT(*)FROM NabbrBoro WHERE BorAppOid=a.Oid)as cnt,"
		+ "(SELECT boro_date FROM NabbrBoro WHERE BorAppOid=a.Oid ORDER BY boro_date LIMIT 1)as begin,"
		+ "(SELECT boro_date FROM NabbrBoro WHERE BorAppOid=a.Oid ORDER BY boro_date DESC LIMIT 1)as end "
		+ "FROM NabbrBorApp a LEFT OUTER JOIN empl e ON a.lender=e.idno WHERE a.borrower='"
		+getSession().getAttribute("userid")+"'ORDER BY a.date DESC");*/
		StringBuilder sb=new StringBuilder("SELECT cb.name_field, cb.rc_table, a.*, e.cname,(SELECT COUNT(*)FROM NabbrBoro WHERE "
		+ "BorAppOid=a.Oid)as cnt FROM CODE_BORROW cb, NabbrBorApp a LEFT OUTER JOIN empl e ON a.lender=e.idno "
		+ "WHERE cb.id=a.rc_code AND a.borrower='"+getSession().getAttribute("userid")+"'ORDER BY a.date DESC");
		
		
		
		List<Map>list=df.sqlGet(sb.toString());
		for(int i=0; i<list.size(); i++){
			list.get(i).put("name", df.sqlGetStr("SELECT "+list.get(i).get("name_field")+" FROM "+list.get(i).get("rc_table")+" WHERE Oid="+list.get(i).get("rc_oid")));
		}
		
		
		
		
		request.setAttribute("boros", list);
		return execute();
	}
	
	/**
	 * 刪除申請單
	 * @return
	 */
	public String delApp(){
		
		df.exSql("DELETE FROM NabbrBorApp WHERE Oid="+Oid);
		df.exSql("DELETE FROM NabbrBoro WHERE BorAppOid="+Oid);
		Message msg=new Message();
		msg.setSuccess("編號 "+Oid+"申請單已刪除");
		this.savMessage(msg);
		return search();
	}
	
	/**
	 * 選取申請單
	 * @return
	 */
	public String edit(){
		//this.Oid=Oid;
		request.setAttribute("cls", df.sqlGet("SELECT * FROM NabbrBoro WHERE BorAppOid="+Oid));
		
		return execute();
	}
	
	/**
	 * 刪除節次
	 * @return
	 */
	public String delCls(){
		for(int i=0; i<cls.length; i++){
			if(!cls[i].equals(""))
			df.exSql("DELETE FROM NabbrBoro WHERE Oid="+cls[i]);
		}
		Message msg=new Message();
		msg.setSuccess("已刪除");
		this.savMessage(msg);
		return edit();
	}
	
	/**
	 * 分析研究成果資料表與編號
	 * @param RcTableCode
	 * @return
	 */
	private String[] rcCode(String RcTableCode){		
		return RcTableCode.split(",");
	}
	
	/**
	 * 推算日期範圍
	 * @param begin
	 * @param end
	 * @return
	 * @throws ParseException
	 */
	private List parseDate(String begin, String end) throws ParseException{
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar bg=Calendar.getInstance();
		Calendar ed=Calendar.getInstance();
		if(begin.trim().equals("")){
			return null;
		}		
		List list=new ArrayList();
		Map map;
		
		if(end.trim().equals("")){
			map=new HashMap();
			bg.setTime(sf.parse(begin));
			map.put("week", bg.get(Calendar.DAY_OF_WEEK)-1);
			map.put("date", bg.getTime());
			list.add(map);
			return list;
		}else{			
			bg.setTime(sf.parse(begin));
			ed.setTime(sf.parse(end));
			if(bg.getTimeInMillis()>ed.getTimeInMillis()){				
				return null;
			}			
			while(bg.getTimeInMillis()<=ed.getTimeInMillis()){
				map=new HashMap();
				map.put("date", bg.getTime());
				map.put("week", bg.get(Calendar.DAY_OF_WEEK)-1);
				list.add(map);
				bg.add(Calendar.DAY_OF_YEAR, 1);
			}				
			//System.out.println(list.size());
			return list;
		}		
	}	
}