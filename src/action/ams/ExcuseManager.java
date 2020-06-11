package action.ams;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import model.AmsDocApply;
import model.Message;

import action.BaseAction;

/**
 * 加班補休申請
 * @author shawn
 */
public class ExcuseManager extends BaseAction{
	
	public String startDate;
	public String endDate;
	public String reason;
	public String agent;
	
	public String startDate1;
	public String endDate1;
	public String reason1;
	
	String oMonth="6";
	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sf1=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	SimpleDateFormat sf2=new SimpleDateFormat("yyyy-MM-dd");
	int beginYear, endYear, month;
	Long totalDay, totalHour;
	
	public String execute() throws ParseException{		
		
		if(request.getParameter("dOid")!=null){
			df.exSql("DELETE FROM AMS_DocApply WHERE Oid="+request.getParameter("dOid"));
			Message msg=new Message();
			msg.setSuccess("已刪除");
			this.savMessage(msg);
		}
		
		
		
		setInfo();		
		return SUCCESS;
	}
	
	
	/**
	 * 補休
	 * @return
	 * @throws ParseException
	 */
	public String add() throws ParseException{
		if(startDate.equals("")||endDate.equals("")){
			setInfo();
			Message msg=new Message();
			msg.setError("請填寫日期");
			this.savMessage(msg);			
			return SUCCESS;
		}
		
		if(agent.trim().equals("")){
			setInfo();
			Message msg=new Message();
			msg.setError("必需有代理人");
			this.savMessage(msg);			
			return SUCCESS;
		}
		
		if(reason.trim().equals("")){
			setInfo();
			Message msg=new Message();
			msg.setError("必需填寫補休原因");
			this.savMessage(msg);			
			return SUCCESS;
		}
		
		if(sf1.parse(startDate).getTime()>=sf1.parse(endDate).getTime()){
			setInfo();
			Message msg=new Message();
			msg.setError("起始時間有誤");
			this.savMessage(msg);			
			return SUCCESS;
		}
		
		Calendar begin=Calendar.getInstance(), end=Calendar.getInstance();		
		begin.setTime(sf2.parse(startDate.substring(0, 10)));
		end.setTime(sf2.parse(endDate.substring(0, 10)));

		//總日數
		totalDay=(((end.getTimeInMillis()-begin.getTimeInMillis())/1000/(60*60)/24));
		begin.setTime( sf1.parse(startDate.substring(0, 10)+" "+startDate.substring(11, startDate.length())));
		end.setTime( sf1.parse(startDate.substring(0, 10)+" "+endDate.substring(11, startDate.length())));
		float sum=(float)(end.getTimeInMillis()-begin.getTimeInMillis())/1000/(60*60);
		totalHour=Long.parseLong(String.valueOf(((int)Math.ceil(sum))));
		if(sum==8.5) totalHour=Long.parseLong(String.valueOf((totalDay+1)*8));
		countDay();
		
		AmsDocApply doc=new AmsDocApply();
		doc.setAgent(df.sqlGetStr("SELECT idno FROM empl WHERE Oid="+agent));
		doc.setAskLeaveType("09");
		doc.setCreateDate(new Date());
		doc.setDocType("1");
		doc.setEndDate(sf1.parse(endDate));
		doc.setIdno(getSession().getAttribute("userid").toString());
		doc.setReason(reason);
		doc.setSn(String.valueOf(new Date().getTime()));
		doc.setStartDate(sf1.parse(startDate));
		doc.setTotalDay(Integer.valueOf(String.valueOf(totalDay)));
		doc.setTotalHour(Integer.valueOf(String.valueOf(totalHour)));
		doc.setTotalMinute(Integer.valueOf(String.valueOf(0)));
		try{
			df.update(doc);
		}catch(Exception e){
			doc=null;
			setInfo();
			Message msg=new Message();
			msg.setError("重複申請");
			this.savMessage(msg);			
			return SUCCESS;
		}
		
		setYearMonth();
		if(getTotal(beginYear, endYear)-getUsed(beginYear, endYear)<0){			
			df.exSql("DELETE FROM AMS_DocApply WHERE Oid="+doc.getOid());
			doc=null;
			setInfo();
			Message msg=new Message();
			msg.setError("超出可用時數");
			this.savMessage(msg);			
			return SUCCESS;
			
		}else{
			setInfo();
			Message msg=new Message();
			msg.setSuccess("已申請"+((totalDay*8)+totalHour)+"小時");
			savMessage(msg);			
			return SUCCESS;
		}		
	}
	
	/**
	 * 加班
	 * @return
	 * @throws ParseException 
	 */
	public String add1() throws ParseException{
		if(startDate1.equals("")||endDate1.equals("")){
			setInfo();
			Message msg=new Message();
			msg.setError("請填寫日期");
			this.savMessage(msg);			
			return SUCCESS;
		}
		
		if(reason1.trim().equals("")){
			setInfo();
			Message msg=new Message();
			msg.setError("必需填寫加班原因");
			this.savMessage(msg);			
			return SUCCESS;
		}
		
		if(sf1.parse(startDate1).getTime()>=sf1.parse(endDate1).getTime()){
			setInfo();
			Message msg=new Message();
			msg.setError("起始時間有誤");
			this.savMessage(msg);			
			return SUCCESS;
		}
		
		if(!startDate1.substring(0, 10).equals(endDate1.substring(0, 10))){
			setInfo();
			Message msg=new Message();
			msg.setError("加班不得超過1日");
			this.savMessage(msg);			
			return SUCCESS;			
		}
		
		Calendar begin=Calendar.getInstance(), end=Calendar.getInstance();		
		String work_date=startDate1.substring(0, 10);
		String work_begin=startDate1.substring(11, startDate1.length());
		String work_end=endDate1.substring(11, startDate1.length());
		
		//總時數
		//begin.setTime(sf2.parse(startDate1.substring(0, 10)));
		//end.setTime(sf2.parse(endDate1.substring(0, 10)));
		//totalDay=(((end.getTimeInMillis()-begin.getTimeInMillis())/1000/(60*60)/24));		
		begin.setTime( sf1.parse(work_date+" "+work_begin));
		end.setTime( sf1.parse(work_date+" "+work_end));		
		totalHour=((end.getTimeInMillis()-begin.getTimeInMillis())/1000/(60*60));	
		
		//偵測不得小於4小時
		if(totalHour<4){
			setInfo();
			Message msg=new Message();
			msg.setError("依規定加班單最小為4小時");
			this.savMessage(msg);			
			return SUCCESS;
		}
		
		//偵測不得大於8小時
		/*
		if(totalHour>8){
			setInfo();
			Message msg=new Message();
			msg.setError("依規定加班單最多為8小時");
			this.savMessage(msg);			
			return SUCCESS;
		}
		*/
		
		//偵測是否為4時數
		/*
		//if(((totalDay*8)+totalHour)%4!=0){
		if(totalHour%4!=0){
			setInfo();
			Message msg=new Message();
			msg.setError("每個加班單位為4小時");
			this.savMessage(msg);			
			return SUCCESS;
		}
		*/
		
		//偵測當日排班
		Map<Time, Time>workdate=df.sqlGetMap("SELECT set_in, set_out FROM AMS_Workdate WHERE idno='"+getSession().getAttribute("userid")+"' AND wdate='"+startDate1.substring(0, 10)+"'");
		if(workdate!=null){	
			//若排班期間包含加班開時或結束
			if(workdate.get("set_in")!=null)
			if(checkTime(begin.getTimeInMillis(), end.getTimeInMillis(), sf1.parse(work_date+" "+workdate.get("set_in")).getTime(), sf1.parse(work_date+" "+workdate.get("set_out")).getTime())){
				setInfo();
				Message msg=new Message();
				msg.setError("加班期間中有排定上班");
				this.savMessage(msg);			
				return SUCCESS;			
			}
					
		}
		
		AmsDocApply doc=new AmsDocApply();
		//doc.setAgent(df.sqlGetStr("SELECT idno FROM empl WHERE Oid="+agent));
		//doc.setDocType("2");
		doc.setCreateDate(new Date());
		doc.setDocType("2");
		doc.setEndDate(sf1.parse(endDate1));
		doc.setIdno(getSession().getAttribute("userid").toString());		
		doc.setReason(reason1);
		doc.setSn(String.valueOf(new Date().getTime()));
		doc.setStartDate(sf1.parse(startDate1));
		doc.setTotalDay(Integer.valueOf("0"));
		if(totalHour>4&&totalHour<8){
			totalHour=4L;
		}
		if(totalHour>8){
			totalHour=8L;
		}
		doc.setTotalHour(Integer.valueOf(String.valueOf(totalHour)));
		doc.setTotalMinute(Integer.valueOf(String.valueOf(0)));
		try{
			df.update(doc);
		}catch(Exception e){
			e.printStackTrace();
			doc=null;
			setInfo();
			Message msg=new Message();
			msg.setError("重複申請");
			this.savMessage(msg);			
			return SUCCESS;
		}
		
		setYearMonth();
		setInfo();		
		return SUCCESS;
	}
	
	/**
	 * 約分
	 * @param totalDay
	 * @param totalHour
	 */
	private void countDay(){
		if(totalHour>8){
			totalDay=totalHour/8;
			totalHour=totalHour%8;
		}		
	}
	
	/**
	 * ...
	 */
	private void setYearMonth(){			
		Calendar c=Calendar.getInstance();		
		month=c.get(Calendar.MONTH)+1;		
		if(month<6){
			beginYear=c.get(Calendar.YEAR)-1;
			endYear=c.get(Calendar.YEAR);
		}else{
			beginYear=c.get(Calendar.YEAR);
			endYear=c.get(Calendar.YEAR)+1;
		}
	}
	
	
	private void setInfo(){
		
		setYearMonth();
		
		//加班記錄的時數總和
		request.setAttribute("total", getTotal(beginYear, endYear));		
		//找到已補休的時數總和
		request.setAttribute("used", getUsed(beginYear, endYear));
		
		//補休單09列表
		request.setAttribute("docs09", df.sqlGet("SELECT d.*,((d.totalDay*8)+d.totalHour)as hours FROM AMS_DocApply d WHERE " +
		"d.startDate>='"+beginYear+"/"+oMonth+"/1' AND d.startDate<='"+endYear+"/"+oMonth+"/1' AND d.idno='"+getSession().getAttribute("userid")+
		"' AND d.askLeaveType='09' ORDER BY d.endDate DESC"));
		
		//加班單2列表
		List docs2=df.sqlGet("SELECT d.*,((d.totalDay*8)+d.totalHour)as hours FROM AMS_DocApply d WHERE (d.status!='0'OR d.status IS NULL)AND " +
		"d.startDate>='"+beginYear+"/"+oMonth+"/1' AND d.startDate<='"+endYear+"/"+oMonth+"/1' AND d.idno='"+getSession().getAttribute("userid")+"' AND docType='2' ORDER BY d.endDate DESC");
		docs2.addAll(df.sqlGet("SELECT hours, '補償'as endDate, '-'as status FROM AMS_Redeem WHERE idno='"+getSession().getAttribute("userid")+"' AND expiry>='"+this.sf.format(new Date())+"'"));
		request.setAttribute("docs2", docs2);
	}
	
	/**
	 * 加班總時數含補償時數
	 * @return
	 */
	private int getTotal(int beginYear, int endYear){		
		return df.sqlGetInt("SELECT IFNULL(((SUM(totalDay)*8)+SUM(totalHour)),0)as hours FROM AMS_DocApply WHERE (status!='0' AND status!='2' AND status IS NOT NULL)AND " +
		"docType='2' AND startDate>='"+beginYear+"/"+oMonth+"/1' AND startDate<='"+endYear+"/"+oMonth+"/1' AND idno='"+getSession().getAttribute("userid")+"'")+
		df.sqlGetInt("SELECT IFNULL(SUM(hours),0)as hours FROM AMS_Redeem WHERE idno='"+getSession().getAttribute("userid")+"' AND expiry>='"+this.sf.format(new Date())+"'");
	}
	
	private int getUsed(int beginYear, int endYear){		
		return df.sqlGetInt("SELECT IFNULL(((SUM(totalDay)*8)+SUM(totalHour)),0)as hours FROM AMS_DocApply WHERE (status!='0' AND status!='2' OR status IS NULL) AND " +
		"askLeaveType='09' AND startDate>='"+beginYear+"/"+oMonth+"/1' AND startDate<='"+endYear+"/"+oMonth+"/1' AND idno='"+getSession().getAttribute("userid")+"'");
	}
	
	private boolean checkTime(long in, long out, long setIn, long setOut){	
		if(in>setIn && in<setOut){
			return true;
		}
		if(out>setIn && out<setOut){
			return true;
		}
		if(in<=setIn && out>=setOut){
			return true;
		}
		return false;
	}
	
}
