package action.nabbr;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import action.BaseAction;
import model.Message;

public class NabbrManagerAction extends BaseAction{
	
	/**
	 * 分析身份權限
	 * 職員工unit第[0]個為A衝突航機系故由1開始
	 * @param str
	 * @return
	 */
	private String parseUnit(String str[]){
		
		StringBuilder sb=new StringBuilder();
		for(int i=1; i<str.length; i++){
			sb.append("'"+str[i]+"',");
		}
		sb.delete(sb.length()-1, sb.length());
		return sb.toString();
	}
	
	public String name2, Oid, boro, remark, seat, begin, end, charge[];
	
	public String execute(){
		
		
		//String idno=getSession().getAttribute("userid").toString();
		
		List<Map>dept=df.sqlGet("SELECT id FROM CODE_DEPT WHERE assistant='"+getSession().getAttribute("userid")+"'");
		Cookie c[]=request.getCookies();
		String unit=null;
		for(int i=0; i<c.length; i++){
			if(c[i].getName().equals("unit")){
				unit=parseUnit(c[i].getValue().split(","));
			}
		}
		
		
		StringBuilder sb=new StringBuilder("SELECT * FROM Nabbr n WHERE ");
		if(dept.size()>0){
			sb.append("dept IN(");
			for(int i=0; i<dept.size(); i++){
				sb.append("'"+dept.get(i).get("id")+"',");
			}
			sb.delete(sb.length()-1, sb.length());
			sb.append(")");
		}
		if(dept.size()>0)sb.append("OR ");
		if(unit.length()>0){
			String units[]=unit.split(",");
			sb.append("unit IN(");
			for(int i=0; i<units.length; i++){
				sb.append(units[i]+",");
			}
			sb.delete(sb.length()-1, sb.length());
			sb.append(")");
		}
		
		List list=df.sqlGet(sb.toString());
		if(list.size()<1){
			Message msg=new Message();
			msg.setError("沒有分配教室給您的單位");
			this.savMessage(msg);		
			return SUCCESS;
		}
		
		request.setAttribute("rooms", list);	
		return SUCCESS;
	}
	
	public String edit(){		
		Map map=df.sqlGetMap("SELECT * FROM Nabbr WHERE Oid="+Oid);
		map.put("charge", df.sqlGet("SELECT e.Oid, e.cname FROM NabbrInCharge n, empl e WHERE e.Oid=n.empl_oid AND n.room_oid="+Oid));
		request.setAttribute("room", map);		
		return SUCCESS;
	}
	
	public String save(){
		Message msg=new Message();
		try{
			df.exSql("UPDATE Nabbr SET remark='"+remark+"', seat='"+seat+"' WHERE Oid="+Oid);
		}catch(Exception e){
			msg.addError("教室資料有誤");
		}
		
		try{			
			df.exSql("DELETE FROM NabbrInCharge WHERE room_oid='"+Oid+"'");
			for(int i=0; i<charge.length; i++){				
				if(!charge[i].trim().equals("")){
					df.exSql("INSERT INTO NabbrInCharge(room_oid, empl_oid)VALUES("+Oid+", "+charge[i].split(",")[0]+");");
				}
			}
		}catch(Exception e){
			msg.addError("負責人員資料有誤");
		}		
		this.savMessage(msg);
		return edit();
	}
	
	public String print() throws IOException{
		
		
		return null;
	}
}