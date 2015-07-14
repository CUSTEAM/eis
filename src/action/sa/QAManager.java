package action.sa;

import java.util.List;
import java.util.Map;

import model.Message;
import model.SYSDOC;

import action.BaseAction;

public class QAManager extends BaseAction{
	
	public String parent_oid;
	public String sub_oid;	
	public String campus;
	public String parent_unit;
	public String sub_unit;
	public String per;
	
	public String Oid;
	public String send_unit;
	public String sys;
	public String title;
	public String type;	
	public String sender;	
	public String question;
	public String note;	
	public String tester;
	public String editor;
	public String reply;
	public String review;
	public String review_final;

	public String execute(){
		
		if(request.getParameter("Oid")!=null){
			SYSDOC doc=(SYSDOC) df.hqlGetListBy("FROM SYSDOC WHERE Oid="+request.getParameter("Oid")).get(0);
			
			Map m=df.sqlGetMap("SELECT * FROM SYS WHERE Oid="+doc.getSys());
			sys=request.getParameter("Oid").toString();
			if(m.get("parent").toString().equals("0")){				
				parent_oid=doc.getSys().toString();				
			}else{
				parent_oid=String.valueOf(m.get("parent"));
				sub_oid=String.valueOf(m.get("Oid"));
			}
			
			if(doc.getSend_unit()!=null&&!doc.getSend_unit().equals("")){
				m=df.sqlGetMap("SELECT * FROM CODE_UNIT WHERE id='"+doc.getSend_unit()+"'");
				campus=String.valueOf(m.get("campus"));
				if(m.get("pid").toString().equals("0")){
					parent_unit=String.valueOf(m.get("id"));
				}else{
					sub_unit=String.valueOf(m.get("id"));
					parent_unit=String.valueOf(m.get("pid"));
				}
			}			
			request.setAttribute("doc", doc);
			request.setAttribute("qFile", df.sqlGet("SELECT * FROM SYS_DOC_FILE WHERE type='q' AND doc_oid="+doc.getOid()));
			request.setAttribute("nFile", df.sqlGet("SELECT * FROM SYS_DOC_FILE WHERE type='n' AND doc_oid="+doc.getOid()));
			request.setAttribute("rFile", df.sqlGet("SELECT * FROM SYS_DOC_FILE WHERE type='r' AND doc_oid="+doc.getOid()));
		}
		
		/*if(request.getParameter("dOid")!=null){
			df.exSql("DELETE FROM SYS_DOC WHERE Oid="+request.getParameter("dOid"));			
			return list();
		}*/
		
		return SUCCESS;
	}
	
	public String add(){
		
		if(title.equals("")||sys.equals("")){
			Message msg=new Message();
			msg.setError("欄位不齊全");
			this.savMessage(msg);
			return SUCCESS;
		}
		
		SYSDOC doc=new SYSDOC();
		doc.setSys(Integer.parseInt(sys));
		doc.setTitle(title);
		doc.setType(type);
		df.update(doc);
		request.setAttribute("doc", doc);		
		return SUCCESS;
	}
	
	private StringBuilder genSQL(StringBuilder sql){
		if(per.equals("tester")){sql.append(" AND (tester IS NULL OR tester='')");}
		if(per.equals("editor")){sql.append(" AND (editor IS NULL OR editor='')AND (tester IS NOT NULL AND tester !='')");}	
		if(per.equals("review")){sql.append(" AND (review IS NULL OR review='')AND (tester IS NOT NULL AND tester !='')AND (editor IS NOT NULL AND editor !='')");}
		if(per.equals("review_final")){sql.append(" AND (review_final IS NULL OR review_final='')AND (tester IS NOT NULL AND tester !='')AND (editor IS NOT NULL AND editor !='')AND (review IS NOT NULL AND review!='')");}
		if(per.equals("final"))sql.append(" AND (tester IS NOT NULL AND tester!='')AND(editor IS NOT NULL AND editor!='')AND(review IS NOT NULL AND review!='')AND(review_final IS NOT NULL AND review_final!='')");
		return sql;
	}
	
	public String list(){		
		StringBuilder sql=new StringBuilder("SELECT s1.name as parentName, s.name, d.editor, d.tester, d.review, d.review_final, " +
		"d.title, d.Oid FROM SYS s LEFT OUTER JOIN SYS s1 ON s.parent=s1.Oid, SYS_DOC d WHERE " +
		"s.Oid=d.sys AND s.Oid LIKE'"+sys+"%' AND s.parent=0");
		if(!per.equals(""))genSQL(sql);
		List docs=df.sqlGet(sql.toString());
		
		sql=new StringBuilder("SELECT s1.name as parentName, s.name, d.editor, d.tester, d.review, d.review_final, " +
		"d.title, d.Oid FROM SYS s LEFT OUTER JOIN SYS s1 ON s.parent=s1.Oid, SYS_DOC d WHERE s.Oid=d.sys AND " +
		"s.parent='"+sys+"' AND s.parent!=0");
		if(!per.equals(""))genSQL(sql);
		docs.addAll(df.sqlGet(sql.toString()));
		
		sql=new StringBuilder("SELECT s1.name as parentName, s.name, d.editor, d.tester, d.review, d.review_final,d.title, d.Oid FROM SYS s " +
		"LEFT OUTER JOIN SYS s1 ON s.parent=s1.Oid, SYS_DOC d WHERE s.Oid=d.sys AND s.Oid LIKE'"+sys+"%'AND s.parent!=0");
		if(!per.equals(""))genSQL(sql);
		docs.addAll(df.sqlGet(sql.toString()));
		
		request.setAttribute("docs", docs);		
		return SUCCESS;
	}
	
	public String delete(){
		df.exSql("DELETE FROM SYS_DOC WHERE Oid="+Oid);
		Message msg=new Message();
		msg.setSuccess("已刪除");
		this.savMessage(msg);
		return list();
	}
	
	public String save(){
		
		SYSDOC doc=(SYSDOC) df.hqlGetListBy("FROM SYSDOC WHERE Oid="+Oid).get(0);
		doc.setSend_unit(send_unit);
		doc.setSender(sender);
		doc.setQuestion(question);
		doc.setNote(note);
		doc.setTester(tester);
		doc.setReply(reply);
		doc.setEditor(editor);
		doc.setReview(review);
		doc.setReview_final(review_final);		
		df.update(doc);
		request.setAttribute("doc", doc);
		
		request.setAttribute("qFile", df.sqlGet("SELECT * FROM SYS_DOC_FILE WHERE type='q' AND doc_oid="+doc.getOid()));
		request.setAttribute("nFile", df.sqlGet("SELECT * FROM SYS_DOC_FILE WHERE type='n' AND doc_oid="+doc.getOid()));
		request.setAttribute("rFile", df.sqlGet("SELECT * FROM SYS_DOC_FILE WHERE type='r' AND doc_oid="+doc.getOid()));
		
		Message msg=new Message();
		msg.setSuccess("已儲存");
		this.savMessage(msg);
		return SUCCESS;
	}
	
	public String leave(){
		
		if(tester.equals("")){
			per="tester";
			return list();
		}
		if(editor.equals("")){
			per="editor";
			return list();
		}
		if(review.equals("")){
			per="review";
			return list();
		}
		if(review_final.equals("")){
			per="review_final";
			return list();
		}
		per="final";
		return list();
	}
	
	public String print(){
		
		
		return null;
	}
}
