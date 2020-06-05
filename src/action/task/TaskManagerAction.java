package action.task;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import action.BaseAction;
import model.Mail;
import model.MailReceiver;
import model.Message;
import model.Task;
import model.Task_hist;

public class TaskManagerAction extends BaseAction{
	
	public String Task_oid, Oid, Task_hist_oid, note, feedback, units[],close, subNote, begin, end;
	
	private File[] fileUpload, fileUpload1;
    private String[] fileUploadFileName, fileUpload1FileName;
    private String[] fileUploadContentType, fileUpload1ContentType;
    
    public String title;
    
    public File[] getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(File[] fileUpload) {
		this.fileUpload = fileUpload;
	}

	public File[] getFileUpload1() {
		return fileUpload1;
	}

	public void setFileUpload1(File[] fileUpload1) {
		this.fileUpload1 = fileUpload1;
	}

	public String[] getFileUploadFileName() {
		return fileUploadFileName;
	}

	public void setFileUploadFileName(String[] fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}

	public String[] getFileUpload1FileName() {
		return fileUpload1FileName;
	}

	public void setFileUpload1FileName(String[] fileUpload1FileName) {
		this.fileUpload1FileName = fileUpload1FileName;
	}

	public String[] getFileUploadContentType() {
		return fileUploadContentType;
	}

	public void setFileUploadContentType(String[] fileUploadContentType) {
		this.fileUploadContentType = fileUploadContentType;
	}

	public String[] getFileUpload1ContentType() {
		return fileUpload1ContentType;
	}

	public void setFileUpload1ContentType(String[] fileUpload1ContentType) {
		this.fileUpload1ContentType = fileUpload1ContentType;
	}
	
	public String execute(){	
		getUnits();	//即時更新單位列表
		List list=df.sqlGet("SELECT th.open, th.edate, t.userid,th.Oid,(SELECT e1.cname FROM empl e1, Task_hist h1 WHERE "
		+ "e1.idno=h1.empl AND h1.Oid=th.Task_hist_oid)as pname,"
		+ "(SELECT u.name FROM CODE_UNIT u, empl e2 WHERE e2.unit_module=u.id AND e2.idno=t.userid)as unit,"
		+ "(SELECT cname FROM empl WHERE idno=t.userid)as cname,"
		+ "(SELECT student_name FROM stmd WHERE student_no=t.userid)as student_name,"
		+ "(SELECT COUNT(*)FROM Task_hist WHERE Task_hist_oid=th.Oid AND feedback IS NOT NULL)as b,"
		+ "(SELECT COUNT(*)FROM Task_hist WHERE Task_hist_oid=th.Oid AND feedback IS NULL)as n,"
		+ "(SELECT COUNT(*)FROM Task_hist WHERE Task_hist_oid=th.Oid AND open='0')as o ,"
		+ "t.title, t.sdate FROM Task t,Task_hist th WHERE th.open='1' AND t.Oid=th.Task_oid AND th.empl='"+getSession().getAttribute("userid")+"'ORDER BY th.Oid DESC");
		
		//追加近10筆已結案
		list.addAll(df.sqlGet("SELECT th.open, th.edate, t.userid,th.Oid,(SELECT e1.cname FROM empl e1, Task_hist h1 WHERE "
		+ "e1.idno=h1.empl AND h1.Oid=th.Task_hist_oid)as pname,"
		+ "(SELECT u.name FROM CODE_UNIT u, empl e2 WHERE e2.unit_module=u.id AND e2.idno=t.userid)as unit,"
		+ "(SELECT cname FROM empl WHERE idno=t.userid)as cname,"
		+ "(SELECT student_name FROM stmd WHERE student_no=t.userid)as student_name,"
		+ "(SELECT COUNT(*)FROM Task_hist WHERE Task_hist_oid=th.Oid AND feedback IS NOT NULL)as b,"
		+ "(SELECT COUNT(*)FROM Task_hist WHERE Task_hist_oid=th.Oid AND feedback IS NULL)as n,"
		+ "(SELECT COUNT(*)FROM Task_hist WHERE Task_hist_oid=th.Oid AND open='0')as o ,"
		+ "t.title, t.sdate FROM Task t,Task_hist th WHERE th.open='0' AND t.Oid=th.Task_oid AND th.empl='"+getSession().getAttribute("userid")+"'ORDER BY th.Oid DESC LIMIT 10"));
				
		request.setAttribute("myTasks", list);
		
		list=df.sqlGet("SELECT t.title, t.Oid, t.sdate, t.edate,"
		+ "(SELECT COUNT(*)FROM Task_hist WHERE Task_oid=t.Oid AND Task_hist_oid IS NULL AND feedback IS NOT NULL)as b,"
		+ "(SELECT COUNT(*)FROM Task_hist WHERE Task_oid=t.Oid AND Task_hist_oid IS NULL AND feedback IS NULL)as n,"
		+ "(SELECT COUNT(*)FROM Task_hist WHERE Task_oid=t.Oid AND Task_hist_oid IS NULL AND open='0')as o "
		+ "FROM Task t WHERE t.edate IS NULL AND t.userid='"+getSession().getAttribute("userid")+"'ORDER BY t.sdate DESC");
		
		//追加近10筆已結案
		list.addAll(df.sqlGet("SELECT t.title, t.Oid, t.sdate, t.edate,"
		+ "(SELECT COUNT(*)FROM Task_hist WHERE Task_oid=t.Oid AND Task_hist_oid IS NULL AND feedback IS NOT NULL)as b,"
		+ "(SELECT COUNT(*)FROM Task_hist WHERE Task_oid=t.Oid AND Task_hist_oid IS NULL AND feedback IS NULL)as n,"
		+ "(SELECT COUNT(*)FROM Task_hist WHERE Task_oid=t.Oid AND Task_hist_oid IS NULL AND open='0')as o "
		+ "FROM Task t WHERE t.edate IS NOT NULL AND t.userid='"+getSession().getAttribute("userid")+"'ORDER BY t.sdate DESC LIMIT 10"));
		request.setAttribute("tasks", list);
		
		return SUCCESS;
	}
	
	/**
	 * 查詢
	 * @return
	 */
	public String search(){		
		getUnits();	//即時更新單位列表
		StringBuilder sb=new StringBuilder("SELECT th.open, th.edate, t.userid,th.Oid,(SELECT e1.cname FROM empl e1, Task_hist h1 WHERE "
		+ "e1.idno=h1.empl AND h1.Oid=th.Task_hist_oid)as pname,"
		+ "(SELECT u.name FROM CODE_UNIT u, empl e2 WHERE e2.unit_module=u.id AND e2.idno=t.userid)as unit,"
		+ "(SELECT cname FROM empl WHERE idno=t.userid)as cname,"
		+ "(SELECT student_name FROM stmd WHERE student_no=t.userid)as student_name,"
		+ "(SELECT COUNT(*)FROM Task_hist WHERE Task_hist_oid=th.Oid AND feedback IS NOT NULL)as b,"
		+ "(SELECT COUNT(*)FROM Task_hist WHERE Task_hist_oid=th.Oid AND feedback IS NULL)as n,"
		+ "(SELECT COUNT(*)FROM Task_hist WHERE Task_hist_oid=th.Oid AND open='0')as o ,"
		+ "t.title, t.sdate FROM Task t,Task_hist th WHERE t.Oid=th.Task_oid AND th.empl='"+getSession().getAttribute("userid")+"'");
		if(begin.trim().length()>=10)sb.append("AND th.sdate>='"+begin+"'");
		if(end.trim().length()>=10)sb.append("AND th.sdate<='"+end+"'");
		sb.append("ORDER BY th.Oid DESC");
		List list=df.sqlGet(sb.toString());
		request.setAttribute("myTasks", list);
		
		sb=new StringBuilder("SELECT t.title, t.Oid, t.sdate, t.edate,"
		+ "(SELECT COUNT(*)FROM Task_hist WHERE Task_oid=t.Oid AND Task_hist_oid IS NULL AND feedback IS NOT NULL)as b,"
		+ "(SELECT COUNT(*)FROM Task_hist WHERE Task_oid=t.Oid AND Task_hist_oid IS NULL AND feedback IS NULL)as n,"
		+ "(SELECT COUNT(*)FROM Task_hist WHERE Task_oid=t.Oid AND Task_hist_oid IS NULL AND open='0')as o "
		+ "FROM Task t WHERE t.userid='"+getSession().getAttribute("userid")+"'");
		if(begin.trim().length()>=10)sb.append("AND t.sdate>='"+begin+"'");
		if(end.trim().length()>=10)sb.append("AND t.sdate<='"+end+"'");
		sb.append("ORDER BY t.Oid DESC");		
		list=df.sqlGet(sb.toString());
		request.setAttribute("tasks", list);
		
		return SUCCESS;
	}
	
	/**
	 * 編輯Task_hist
	 * @return
	 */
	public String edit(){
		
		Task_hist th=(Task_hist)df.hqlGetListBy("FROM Task_hist WHERE Oid="+Oid).get(0);		
		getUnits();	
		
		request.setAttribute("TaskInfo", df.sqlGetMap("SELECT (SELECT e1.cname FROM Task_hist t1, empl e1 WHERE t1.Oid=th.Task_hist_oid AND e1.idno=t1.empl)as fName, t.Oid as taskOid, t.note as onote, t.title, t.sdate, th.*, e.cname, s.student_no, s.student_name FROM"
		+ "(Task t LEFT OUTER JOIN empl e ON t.userid=e.idno)LEFT OUTER JOIN stmd s ON s.student_no=t.userid, "
		+ "Task_hist th WHERE t.Oid=th.Task_Oid AND th.Oid="+Oid));
		
		//下層工作
		List<Map>list=df.sqlGet("SELECT u.name as unit, e.cname, th.* FROM Task_hist th, empl e LEFT OUTER JOIN CODE_UNIT u ON e.unit_module=u.id WHERE th.empl=e.idno AND th.task_hist_oid="+Oid);
		for(int i=0; i<list.size(); i++){
			list.get(i).put("files", df.sqlGet("SELECT * FROM Task_file WHERE back='0' AND Task_hist_oid="+list.get(i).get("Oid")));
			//System.out.println("SELECT * FROM Task_file WHERE back='1' AND Task_hist_oid="+list.get(i).get("Oid"));
			list.get(i).put("bFiles", df.sqlGet("SELECT * FROM Task_file WHERE back='1' AND Task_hist_oid="+list.get(i).get("Oid")));
		}
		request.setAttribute("TaskHistInfo", list);	
		request.setAttribute("TaskFile", df.sqlGet("SELECT * FROM Task_file WHERE back='0' AND Task_oid="+th.getTask_oid()));
		
		request.setAttribute("histFile", df.sqlGet("SELECT * FROM Task_file WHERE back='0' AND Task_hist_oid="+Oid));		
		request.setAttribute("myFile", df.sqlGet("SELECT * FROM Task_file WHERE back='1' AND Task_hist_oid="+Oid));		
		
		return "deal";
	}
	
	private void getUnits(){
		List<Map>list=df.sqlGet("SELECT c.id,(SELECT name FROM CODE_UNIT WHERE id=c.pid)as pName, e.idno, e.cname, c.* FROM CODE_UNIT c, empl e WHERE pid='0'AND c.leader=e.idno");
		
		for(int i=0; i<list.size(); i++)list.get(i).put("units", df.sqlGet("SELECT '"+list.get(i).get("idno")+"'as lid, c.pid,(SELECT name FROM CODE_UNIT WHERE id=c.pid)as pName, e.idno, e.cname, c.* FROM CODE_UNIT c, empl e WHERE c.leader=e.idno AND pid='"+list.get(i).get("id")+"'"));
		
		request.setAttribute("ulist", list);
	}
	
	/**
	 * 回覆Task_hist
	 * @return
	 */
	public String back(){	
		
		Task_hist th=(Task_hist)df.hqlGetListBy("FROM Task_hist WHERE Oid="+Oid).get(0);
		Task t=(Task)df.hqlGetListBy("FROM Task WHERE Oid="+th.getTask_oid()).get(0);
		Date now=new Date();		
		th.setFeedback(feedback);
		th.setEdate(now);
		System.out.println(th.getFeedback());
		df.update(th);		
		if(fileUpload!=null){
			saveDb(th, fileUpload, fileUploadFileName, now.getTime(), "1");//分派附件
			saveFile(th.getTask_oid(), fileUpload, fileUploadFileName, now.getTime());//儲存附件
		}		
		
		List users=new ArrayList();
		Map user;
		//學生結案		
		if(th.getTask_hist_oid()==null){//第1順位掛名受理			
			if(df.sqlGetInt("SELECT COUNT(*)FROM stmd WHERE student_no='"+t.getUserid()+"'")>0){//是學生
				t.setEdate(new Date());
				th.setOpen("0");
				df.update(t);
				df.update(th);
			}
			//mail to	
			//System.out.println("SELECT '"+t.getEmail()+"'as email, student_name as name FROM stmd WHERE student_no='"+t.getUserid()+"'");
			user=df.sqlGetMap("SELECT '"+t.getEmail()+"'as email, student_name as name FROM stmd WHERE student_no='"+t.getUserid()+"'");
			if(user==null)user=df.sqlGetMap("SELECT '"+t.getEmail()+"'as email, cname as name FROM empl WHERE idno='"+t.getUserid()+"'");
					
		}else{
			//非第1順位
			if(th.getTask_hist_oid()!=null){//mail to hist				
				th=(Task_hist)df.hqlGetListBy("FROM Task_hist WHERE Task_hist_oid="+th.getTask_hist_oid()).get(0);
				user=df.sqlGetMap("SELECT Email as email, cname as name FROM empl WHERE idno='"+df.sqlGetStr("SELECT e.idno FROM empl e, Task_hist th WHERE e.idno=th.empl AND th.Oid="+th.getTask_hist_oid())+"'");
				
			}else{//mail to task
				user=df.sqlGetMap("SELECT Email as email, cname as name FROM empl WHERE idno='"+th.getEmpl()+"'");
			}
		}		
		users.add(user);
		sendMail(users, "意見反應有了新回覆", feedback);				
		return edit();
	}
	
	/**
	 * 以Task_hist建立Task_hist
	 * @return
	 */
	public String send(){
		Message msg=new Message();		
		
		if(units==null){
			msg.setError("未指定單位");
			this.savMessage(msg);
			return edit();
		}
		
		Date now=new Date();
		Task_hist th;
		List users=new ArrayList();
		for(int i=0; i<units.length; i++){
			th=new Task_hist();
			th.setEmpl(units[i]);
			th.setNote(subNote);
			th.setOpen("1");
			th.setSdate(now);
			//if(!Task_hist_oid.equals(""))
			th.setTask_hist_oid(Integer.parseInt(Oid));			
			th.setTask_oid(Integer.parseInt(Task_oid));			
			df.update(th);
			if(fileUpload1!=null){
				saveDb(th,fileUpload1, fileUpload1FileName, now.getTime(), "0");//分派附件
			}
			users.add(df.sqlGetMap("SELECT Email as email, cname as name FROM empl WHERE idno='"+units[i]+"'"));
		}
		th=(Task_hist)df.hqlGetListBy("FROM Task_hist WHERE Oid="+Task_oid).get(0);		
		//if(fileUpload!=null)saveFile(th, fileUpload, fileUploadFileName, now.getTime());
		if(fileUpload1!=null)saveFile(th.getTask_oid(), fileUpload1, fileUpload1FileName, now.getTime());	//儲存附件
		sendMail(users, "同仁的意見反應", subNote);
		return edit();
	}
	
	private void sendMail(List<Map<String, String>>users, String subject, String content){
		
		Mail m=new Mail();
		m.setContent("<style>blockquote{background:#f9f9f9;border-left:10px solid #ccc; margin:1.5em 10px; padding:0.5em 10px;} blockquote:before{color:#ccc;content:open-quote;font-size:4em;line-height:0.1em;margin-right:0.25em;vertical-align:-0.4em;}blockquote p{display:inline;}</style>"+"您好<br><br>以下內容由資訊系統自動轉發<br><br><blockquote><b>"+subject+"</b><br><br>"+content.substring(0, content.length()/2)+"...</blockquote><br><br>更多內容及附件檔案請登入<a href='http://ap.cust.edu.tw/ssos'>資訊系統</a>【意見反應單】讀取");
		m.setFrom_addr("CIS@cc.cust.edu.tw");
		m.setSender("中華科技大學資訊系統");
		m.setSend("0");
		m.setSubject(subject);
		df.update(m);
		
		MailReceiver r;
		for(int i=0; i<users.size(); i++){
			r=new MailReceiver();
			r.setAddr(users.get(i).get("email"));
			r.setMail_oid(m.getOid());
			r.setName(users.get(i).get("name"));
			r.setType("to");
			df.update(r);
		}
		
	}
	
	/**
	 * 以Task建立Task_hist
	 * @return
	 */
	public String sendTask(){
		List users=new ArrayList();
		Message msg=new Message();			
		if(units==null){
			msg.setError("未指定單位");
			this.savMessage(msg);
			return ediTask();
		}
		
		Date now=new Date();
		Task_hist th;
		for(int i=0; i<units.length; i++){
			th=new Task_hist();
			th.setEmpl(units[i]);
			th.setNote(subNote);
			th.setOpen("1");
			th.setSdate(now);
			//if(!Task_hist_oid.equals(""))
			th.setTask_hist_oid(Integer.parseInt(Oid));			
			th.setTask_oid(Integer.parseInt(Task_oid));			
			df.update(th);
			if(fileUpload1!=null){
				saveDb(th,fileUpload1, fileUpload1FileName, now.getTime(), "0");//分派附件			
			}
			users.add(df.sqlGetMap("SELECT Email as email, cname as name FROM empl WHERE idno='"+units[i]+"'"));
		}
		th=(Task_hist)df.hqlGetListBy("FROM Task_hist WHERE Oid="+Task_oid).get(0);
		if(fileUpload1!=null)saveFile(th.getTask_oid(), fileUpload1, fileUpload1FileName, now.getTime());	//儲存附件
		sendMail(users, "同仁的意見反應", subNote);
		return ediTask();
	}	
	
	/**
	 * 建立Task
	 * @return
	 */
	public String addTask(){
		List users=new ArrayList();
		Date now=new Date();
		Message msg=new Message();		
		if(units==null){
			msg.setError("未指定單位");
			this.savMessage(msg);
			return execute();
		}
		
		Task t=new Task();
		t.setSdate(now);
		t.setEmail(df.sqlGetStr("SELECT Email FROM empl WHERE idno='"+getSession().getAttribute("userid")+"'"));
		t.setTitle(title);
		t.setNote(note);
		t.setUserid(getSession().getAttribute("userid").toString());
		
		df.update(t);
		Task_hist th = null;
		for(int i=0; i<units.length; i++){
			th=new Task_hist();
			th.setEmpl(units[i]);
			//th.setNote(note);
			th.setOpen("1");
			th.setSdate(now);		
			th.setTask_oid(t.getOid());				
			df.update(th);
			
			if(fileUpload1!=null)
			for (int j = 0; j < fileUpload1.length; j++) {
				df.exSql("INSERT INTO Task_file(Task_oid, Task_hist_oid, path, file_name, back)VALUES"
				+ "("+th.getTask_oid()+","+th.getOid()+",'task/"+th.getTask_oid()+"/', '"+now.getTime()+"-"+j+bio.getExtention(fileUpload1FileName[j])+"', '0');");
			}
			users.add(df.sqlGetMap("SELECT Email as email, cname as name FROM empl WHERE idno='"+units[i]+"'"));
		}
		
		if(fileUpload1!=null){
			//saveDb(th,fileUpload1, fileUpload1FileName, now.getTime(), "0");//分派附件
			saveFile(t.getOid(), fileUpload1, fileUpload1FileName, now.getTime());	//儲存附件			
		}
		sendMail(users, "同仁的意見反應", note);
		msg.setMsg("已建立並分派"+units.length+"個單位");
		this.savMessage(msg);
		return execute();
	}
	
	/**
	 * 編輯Task
	 * @return
	 */
	public String ediTask(){
		
		Task t=(Task)df.hqlGetListBy("FROM Task WHERE Oid="+Task_oid).get(0);		
		getUnits();
		
		request.setAttribute("TaskInfo", df.sqlGetMap("SELECT 'fName'as fName, t.Oid as taskOid, t.note as onote, t.title, t.edate, t.sdate, e.cname, s.student_no, s.student_name FROM"
		+ "(Task t LEFT OUTER JOIN empl e ON t.userid=e.idno)LEFT OUTER JOIN stmd s ON s.student_no=t.userid "
		+ " WHERE t.Oid="+Task_oid));
		
		//下層工作
		List<Map>list=df.sqlGet("SELECT u.name as unit, e.cname, th.* FROM Task_hist th, "
		+ "empl e LEFT OUTER JOIN CODE_UNIT u ON e.unit_module=u.id WHERE th.empl=e.idno AND th.Task_hist_oid IS NULL AND th.Task_oid="+Task_oid);
		for(int i=0; i<list.size(); i++){
			list.get(i).put("files", df.sqlGet("SELECT * FROM Task_file WHERE back='0' AND Task_hist_oid="+list.get(i).get("Oid")));
			
			list.get(i).put("bFiles", df.sqlGet("SELECT * FROM Task_file WHERE back='1' AND Task_hist_oid="+list.get(i).get("Oid")));
		}
		request.setAttribute("TaskHistInfo", list);	
		request.setAttribute("TaskFile", df.sqlGet("SELECT * FROM Task_file WHERE back='0' AND Task_oid="+t.getOid()));
		
		
		
		return "dealTask";
	}
	
	/**
	 * 存檔記錄
	 * @param th
	 * @param fileUpload
	 * @param fileUploadFileName
	 * @param now
	 */
	private void saveDb(Task_hist th, File fileUpload[], String fileUploadFileName[], long now, String back){
		//複製檔案給各單位
		for (int i = 0; i < fileUpload.length; i++) {
			df.exSql("INSERT INTO Task_file(Task_hist_oid, path, file_name, back)VALUES"
			+ "("+th.getOid()+",'task/"+th.getTask_oid()+"/', '"+now+"-"+i+bio.getExtention(fileUploadFileName[i])+"', '"+back+"');");
		}
	}	
	
	/**
	 * 存檔FTP
	 * @param th
	 * @param fileUpload
	 * @param fileUploadFileName
	 */
	private void saveFile(Integer TaskOid, File fileUpload[], String fileUploadFileName[], long now){
		
		if(fileUpload!=null){
			
			String fileName;		
			String filePath;
			String tmp_path=getContext().getRealPath("/tmp");//本機目錄
			String target="host_runtime";
			File dst;
			Map<String, String>ftpinfo;
			File uploadedFile;
			for (int i = 0; i < fileUpload.length; i++) {			
	            uploadedFile = fileUpload[i];            
	            fileName=now+"-"+i+bio.getExtention(fileUploadFileName[i]);//置換檔名            
	            filePath=getContext().getRealPath("/tmp" )+"/"+fileName;            
	            if(getContext().getAttribute("isServer").equals("0")){//測試的情況
	    			target="host_debug";
	    			filePath=filePath.replace("\\", "/");
	    			tmp_path=tmp_path.replace("\\", "/");
	    		}
	            dst=new File(tmp_path);//暫存資料夾			
				if(!dst.exists())dst.mkdir();
				bio.copyFile(fileUpload[i], new File(filePath));
				ftpinfo=df.sqlGetMap("SELECT "+target+" as host, username, password, path FROM SYS_HOST WHERE useid='TaskFile'");
				
				//存放於TaskOid資料夾內
				bio.putFTPFile(ftpinfo.get("host"), ftpinfo.get("username"), ftpinfo.get("password"), tmp_path+"/", ftpinfo.get("path")+"/"+TaskOid+"/", fileName);

	        }
			
		}		
	}
	
	/**
	 * 關閉Task_hist
	 * @return
	 */
	public String close(){
		
		Message msg=new Message();
		df.exSql("UPDATE Task_hist SET open='0' WHERE Oid="+close);
		msg.setMsg("分派結案");		
		this.savMessage(msg);
		if(Oid.equals("")){
			return ediTask();
		}else{
			return edit();
		}
		
	}
	
	/**
	 * 關閉Task
	 * @return
	 */
	public String closeTask(){
		Message msg=new Message();
		Task t=(Task)df.hqlGetListBy("FROM Task WHERE Oid="+Task_oid).get(0);
		t.setEdate(new Date());
		df.update(t);
		msg.setMsg("已結案");
		this.savMessage(msg);
		
		return ediTask();
	}

}
