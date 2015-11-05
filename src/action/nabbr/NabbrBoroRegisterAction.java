package action.nabbr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import action.BaseAction;
import model.Message;

/**
 * 教室使用登記
 * @author shawn
 *
 */
public class NabbrBoroRegisterAction extends BaseAction{
	
	public String date, beginDate, endDate, beginCls, endCls, RcTableCode, room_id;
	
	
	
	public String execute(){
		
		
		List<Map>list=(List)getContext().getAttribute("CODE_BORROW");
		for(int i=0; i<list.size(); i++){
			list.get(i).put("rc", 
					df.sqlGet("SELECT school_year, Oid, "+list.get(i).get("name_field")
					+" as title FROM "+list.get(i).get("rc_table")
					//+" WHERE idno='"+getSession().getAttribute("userid")+"' AND school_year="+getContext().getAttribute("school_year")));
					+" WHERE idno='"+getSession().getAttribute("userid")+"'ORDER BY school_year DESC"));
		}
		
		request.setAttribute("rclist", list);
		
		
		return SUCCESS;
	}
	
	/**
	 * 新增記錄
	 * @return
	 */
	public String create(){		
		
		String code[];
		Date db, de;
		
		try{
			code=rcConvert(RcTableCode);
		}catch(Exception e){
			Message msg=new Message();
			msg.setError("教室名稱或代碼不完整");
			this.savMessage(msg);
			return execute();
		}
		
		List range;
		try {
			range=parseDate(beginDate, endDate);
		} catch (ParseException e) {
			Message msg=new Message();
			msg.setError("日期不完整或衝突");
			this.savMessage(msg);
			return execute();
		}
		
		for(int i=0; i<range.size(); i++){
			
			df.exSql("INSERT INTO NabbrBoro()");
			
			
			
		}
		
		
		
		
		return execute();
	}
	
	public String search(){
		
		
		return execute();
	}
	
	/**
	 * 分析研究成果資料表與編號
	 * @param RcTableCode
	 * @return
	 */
	private String[] rcConvert(String RcTableCode){		
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
		if(begin.trim().equals("")){
			return null;
		}		
		List list=new ArrayList();
		if(end.trim().equals("")){
			list.add(begin);
			return list;
		}else{
			Calendar bg=Calendar.getInstance();
			Calendar ed=Calendar.getInstance();
			bg.setTime(sf.parse(begin));
			ed.setTime(sf.parse(end));
			if(bg.getTimeInMillis()>ed.getTimeInMillis()){				
				return null;
			}			
			while(bg.getTimeInMillis()<=ed.getTimeInMillis()){
				list.add(sf.format(bg.getTime()));
				bg.add(Calendar.DAY_OF_YEAR, 1);
			}
			
			return list;
		}	
		
	}

}
