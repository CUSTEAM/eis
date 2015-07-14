package action.calendar;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



import model.AqAnser;
import model.Message;

import action.BaseAction;

public class Aqansw extends BaseAction{
	
	public String ans_oid;
	public String ans;
	
	
	public String dept;
	public String category;
	public String years;
	
	
	public String execute(){		
		
		if(request.getParameter("doid")!=null){			
			df.exSql("DELETE FROM AQ_anser WHERE Oid="+request.getParameter("doid"));
			if(df.sqlGetInt("SELECT COUNT(*)FROM AQ_anser WHERE idno='"+getSession().getAttribute("userid")+"'")<3){
				//getSession().setAttribute("tqst", true);//鎖定
				getSession().setAttribute("directory", "Teacher");				
				SetAqForm(getSession(), request, getSession().getAttribute("userid").toString());
				return SUCCESS;
			}
		}		
		
		if(request.getParameter("uid")!=null){			
			AqAnser aq=new AqAnser();
			aq.setIdno(getSession().getAttribute("userid").toString());
			aq.setUid(request.getParameter("uid"));
			
			if(request.getParameter("id2")!=null){
				//外系
				aq.setUid("Q");
				aq.setUid2(request.getParameter("id2"));
				request.setAttribute("qs", df.sqlGet("SELECT * FROM AQ_question WHERE uid='Q'"));
				
			}else{
				//行政單位
				request.setAttribute("qs", df.sqlGet("SELECT * FROM AQ_question WHERE uid='"+request.getParameter("uid")+"'"));
			}
			
			try{
				df.update(aq);
				request.setAttribute("ans_oid", aq.getOid());
				request.setAttribute("qform", df.sqlGet("SELECT * FROM AQ_anser WHERE Oid='"+aq.getOid()+"'"));
			}catch(Exception e){
				e.printStackTrace();
				//ActionMessages error = new ActionMessages();	//建立共用錯誤訊息
				//error.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("Course.messageN1", "問卷重複作答，有可能您點選了「重新整理」或F5"));
				//saveErrors(request, error);
				return SUCCESS;
			}
		}
		
		//初入
		//UserCredential c = (UserCredential) session.getAttribute("Credential");		
		getSession().setAttribute("dept", df.sqlGetStr("SELECT c.idno2 FROM CodeEmpl c, empl e WHERE e.unit=c.idno AND e.idno='"+getSession().getAttribute("userid")+"'"));
		getSession().setAttribute("depts", df.sqlGet("SELECT * FROM AQ_unit WHERE id='Q'"));
		getSession().setAttribute("myduty", df.sqlGetStr("SELECT Director FROM empl WHERE idno='"+getSession().getAttribute("userid")+"'"));		
		
		//本系免重做
		List<Map>qlist=df.sqlGet("SELECT * FROM AQ_anser WHERE uid='P' AND idno='"+getSession().getAttribute("userid")+"'");
		if(qlist.size()<1){
			request.setAttribute("mydept", df.sqlGet("SELECT * FROM AQ_unit WHERE id='P'"));
		}

		//外系免重做
		qlist.addAll(df.sqlGet("SELECT * FROM AQ_anser WHERE uid='Q' AND idno='"+getSession().getAttribute("userid")+"'"));
		
		
		//已作答問卷
		qlist.addAll(df.sqlGet("SELECT * FROM AQ_anser WHERE uid NOT IN('P', 'Q') AND idno='"+getSession().getAttribute("userid")+"'"));
		for(int i=0; i<qlist.size(); i++){
			//系用
			
			if(qlist.get(i).get("uid2")!=null){
				try{
					qlist.get(i).put("name", df.sqlGetStr("SELECT name FROM AQ_unit WHERE id2='"+qlist.get(i).get("uid2")+"'"));
				}catch(Exception e){
					qlist.get(i).put("name", df.sqlGetStr("SELECT name FROM AQ_unit WHERE id='"+qlist.get(i).get("uid2")+"'"));
				}
				
			}else{
				qlist.get(i).put("name", df.sqlGetStr("SELECT name FROM AQ_unit WHERE id='"+qlist.get(i).get("uid")+"' AND id2 IS NULL"));
			}
		}
		
		request.setAttribute("qlist", qlist);	
		
		
		StringBuilder sb=new StringBuilder("SELECT * FROM AQ_unit WHERE id NOT IN('P', 'Q', ");
		StringBuilder sb1=new StringBuilder("SELECT * FROM AQ_unit WHERE id='Q' AND id2 NOT IN(");
		for(int i=0; i<qlist.size(); i++){			
			sb.append("'"+qlist.get(i).get("uid")+"', ");
			sb1.append("'"+qlist.get(i).get("uid2")+"', ");
		}		
		sb.delete(sb.length()-2, sb.length());
		sb.append(")");
		
		
		sb1.delete(sb1.length()-2, sb1.length());
		sb1.append(")");
		//System.out.println(sb);
		request.setAttribute("allunit", df.sqlGet(sb.toString()));
		try{
			request.setAttribute("other", df.sqlGet(sb1.toString()));
		}catch(Exception e){
			request.setAttribute("other", df.sqlGet("SELECT * FROM AQ_unit WHERE id='Q'"));
		}
		
		return SUCCESS;
	}
	
	
	public String next(){
		
		AqAnser aq=new AqAnser();
		aq.setIdno(getSession().getAttribute("userid").toString());
		aq.setUid("Q");
		aq.setUid2(dept);
		
		try{
			df.update(aq);
			request.setAttribute("ans_oid", aq.getOid());
			request.setAttribute("qform", df.sqlGet("SELECT * FROM AQ_anser WHERE Oid='"+aq.getOid()+"'"));
		}catch(Exception e){
			return SUCCESS;
		}
		
		
		if(dept.equals("")){
			//本系
			request.setAttribute("qs", df.sqlGet("SELECT * FROM AQ_question WHERE uid='P'"));
		}else{
			//外系
			request.setAttribute("qs", df.sqlGet("SELECT * FROM AQ_question WHERE uid='Q'"));
		}
		
		
		return SUCCESS;
	}
	
	/**
	 * 本系問卷
	 * @return
	 */
	public String myDept(){
		
		AqAnser aq=new AqAnser();
		aq.setIdno(getSession().getAttribute("userid").toString());
		aq.setUid("P");
		aq.setUid2(dept);
		
		try{
			df.update(aq);
			request.setAttribute("ans_oid", aq.getOid());
			request.setAttribute("qform", df.sqlGet("SELECT * FROM AQ_anser WHERE Oid='"+aq.getOid()+"'"));
		}catch(Exception e){
			
			return execute();
		}
		
		
		request.setAttribute("qs", df.sqlGet("SELECT * FROM AQ_question WHERE uid='P'"));
		
		
		
		return SUCCESS;
	}
	
	public String save(){
		
		AqAnser aq=(AqAnser) df.hqlGetListBy("FROM AqAnser WHERE Oid="+ans_oid).get(0);
		aq.setAnser(ans);
		aq.setUnit(dept);
		aq.setCategory(category);
		aq.setYears(years);		
		df.update(aq);		
		Message msg=new Message();	//建立共用錯誤訊息
		msg.setError("已儲存");
		this.savMessage(msg);		
		return execute();
	}
	
	/**
	 * 抓問卷
	 * @param session
	 * @param request
	 * @param userid
	 */
	private void SetAqForm(HttpSession session, HttpServletRequest request, String userid){
		
		
		
		//初入			
		session.setAttribute("dept", df.sqlGet("SELECT c.idno2 FROM CodeEmpl c, empl e WHERE e.unit=c.idno AND e.idno='"+userid+"'"));
		session.setAttribute("depts", df.sqlGet("SELECT * FROM AQ_unit WHERE id='Q'"));
		session.setAttribute("myduty", df.sqlGetStr("SELECT Director FROM empl WHERE idno='"+userid+"'"));
		
		//本系免重做
		List<Map>qlist=df.sqlGet("SELECT * FROM AQ_anser WHERE uid='P' AND idno='"+userid+"'");
		if(qlist.size()<1){
			request.setAttribute("mydept", df.sqlGet("SELECT * FROM AQ_unit WHERE id='P'"));
		}

		//外系免重做
		qlist.addAll(df.sqlGet("SELECT * FROM AQ_anser WHERE uid='Q' AND idno='"+userid+"'"));
		
		
		//已作答問卷
		qlist.addAll(df.sqlGet("SELECT * FROM AQ_anser WHERE uid NOT IN('P', 'Q') AND idno='"+userid+"'"));
		for(int i=0; i<qlist.size(); i++){
			//系用
			if(qlist.get(i).get("uid2")!=null){
				try{
					qlist.get(i).put("name", df.sqlGetStr("SELECT name FROM AQ_unit WHERE id2='"+((Map)qlist.get(i)).get("uid2")+"'"));
				}catch(Exception e){
					qlist.get(i).put("name", df.sqlGetStr("SELECT name FROM AQ_unit WHERE id='"+((Map)qlist.get(i)).get("uid2")+"'"));
				}
				
			}else{
				qlist.get(i).put("name", df.sqlGetStr("SELECT name FROM AQ_unit WHERE id='"+((Map)qlist.get(i)).get("uid")+"' AND id2 IS NULL"));
			}
		}			
		request.setAttribute("qlist", qlist);			
		StringBuilder sb=new StringBuilder("SELECT * FROM AQ_unit WHERE id NOT IN('P', 'Q', ");
		StringBuilder sb1=new StringBuilder("SELECT * FROM AQ_unit WHERE id='P' AND id2 NOT IN(");
		for(int i=0; i<qlist.size(); i++){			
			sb.append("'"+((Map)qlist.get(i)).get("uid")+"', ");
			sb1.append("'"+((Map)qlist.get(i)).get("uid2")+"', ");
		}		
		sb.delete(sb.length()-2, sb.length());
		sb.append(")ORDER BY Oid");
		sb1.delete(sb1.length()-2, sb1.length());
		sb1.append(")");
		//System.out.println(sb);
		request.setAttribute("allunit", df.sqlGet(sb.toString()));
		try{
			request.setAttribute("other", df.sqlGet(sb1.toString()));
		}catch(Exception e){
			request.setAttribute("other", df.sqlGet("SELECT * FROM AQ_unit WHERE id='Q'"));
		}
		//session.setAttribute("tqst", true);
		//session.setAttribute("aqnum", n);
		return;
	
	}

}
