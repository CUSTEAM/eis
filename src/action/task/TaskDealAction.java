package action.task;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import model.Message;
import model.TaskApply;
import model.TaskHist;
import action.BaseAction;

/**
 * 工作單處理
 * @author shawn
 */
public class TaskDealAction extends BaseAction{
	
	private File[] fileUpload;
    private String[] fileUploadFileName;
    private String[] fileUploadContentType;
    public File[] getFileUpload() {
        return fileUpload;
    }
 
    public void setFileUpload(File[] fileUploads) {
        this.fileUpload = fileUploads;
    }
 
    public String[] getFileUploadFileName() {
        return fileUploadFileName;
    }
 
    public void setFileUploadFileName(String[] fileUploadFileNames) {
        this.fileUploadFileName = fileUploadFileNames;
    }
 
    public String[] getFileUploadContentType() {
        return fileUploadContentType;
    }
 
    public void setFileUploadContentType(String[] fileUploadContentTypes) {
        this.fileUploadContentType = fileUploadContentTypes;
    }
	
	public String Oid;
	public String next_empl;
	public String status;
	public String reply;
	
	
	public String execute(){		
		
		String unit=df.sqlGetStr("SELECT unit_module FROM empl WHERE idno='"+getSession().getAttribute("userid")+"'");
		
		
		
		
		//單位未分派
		List que=df.sqlGet("SELECT e1.cname as next, ta.Oid, t.title, ta.sdate, ta.edate, e.cname, cts.name as status FROM CODE_TASK_STATUS cts,"
		+ "Task_apply ta LEFT OUTER JOIN empl e1 ON ta.next_empl=e1.idno, empl e, Task t WHERE ta.from_empl=e.idno AND cts.id=ta.status AND t.Oid=ta.Task AND t.unit='"+
		unit+"' AND ta.status='N' ORDER BY ta.Oid DESC");
		
		//個人未處理
		que.addAll(df.sqlGet("SELECT e1.cname as next, ta.Oid, t.title, ta.sdate, ta.edate, e.cname, cts.name as status FROM "
				+ "CODE_TASK_STATUS cts, Task_apply ta LEFT OUTER JOIN empl e1 ON ta.next_empl=e1.idno, empl e, Task t WHERE "
				+ "ta.from_empl=e.idno AND cts.id=ta.status AND t.Oid=ta.Task AND "
				+ "ta.next_empl='"+getSession().getAttribute("userid")+"' AND ta.status !='C' AND ta.status !='R'ORDER BY ta.Oid DESC"));
		
		
		que.addAll(df.sqlGet("SELECT e1.cname as next, ta.Oid, t.title, ta.sdate, ta.edate, e.cname, cts.name as status FROM "
				+ "CODE_TASK_STATUS cts, Task_apply ta LEFT OUTER JOIN empl e1 ON ta.next_empl=e1.idno, empl e, Task t WHERE "
				+ "ta.from_empl=e.idno AND cts.id=ta.status AND t.Oid=ta.Task AND "
				+ "ta.next_empl='"+getSession().getAttribute("userid")+"' AND ta.status !='P'  ORDER BY ta.Oid DESC"));
		
		
		request.setAttribute("que", que);
		
		
		//已完成
		/*request.setAttribute("fin", df.sqlGet("SELECT e1.cname as next, ta.Oid, t.title, ta.sdate, ta.edate, e.cname, cts.name as status FROM "
				+ "CODE_TASK_STATUS cts, Task_apply ta LEFT OUTER JOIN empl e1 ON ta.next_empl=e1.idno, empl e, Task t WHERE "
				+ "ta.from_empl=e.idno AND cts.id=ta.status AND t.Oid=ta.Task AND "
				+ "ta.next_empl='"+getSession().getAttribute("userid")+"' AND ta.status !='P'  ORDER BY edate DESC"));
		
		*/
		return SUCCESS;
	}
	
	public String edit(){
		
		String s=df.sqlGetStr("SELECT status FROM Task_apply WHERE Oid="+Oid);
		if(s.equals("N")||s.equals("T")){//未有人處理或移轉中
			df.exSql("UPDATE Task_apply SET status='P', next_empl='"+getSession().getAttribute("userid")+"' WHERE Oid="+Oid);
		}
		
		request.setAttribute("appFiles", df.sqlGet("SELECT f.file_name, f.path FROM Task_file f WHERE f.Task_app_oid="+Oid));
		
		request.setAttribute("task", df.sqlGetMap("SELECT ta.Oid, t.title, ta.sdate, e.cname, cts.name as sname, "
		+ "ta.status, e1.Oid as emplOid, e1.cname as emplName, ta.note FROM CODE_TASK_STATUS cts, Task_apply ta "
		+ "LEFT OUTER JOIN empl e1 ON ta.next_empl=e1.idno, empl e, Task t WHERE ta.from_empl=e.idno AND cts.id=ta.status "
		+ "AND t.Oid=ta.Task AND ta.Oid="+Oid));
		
		
		
		List<Map>hist=df.sqlGet("SELECT th.*, e.cname FROM Task_hist th, empl e WHERE th.empl=e.idno AND th.Task_apply_oid="+Oid+" ORDER BY th.edate DESC");
		for(int i=0; i<hist.size(); i++){
			hist.get(i).put("files", df.sqlGet("SELECT f.file_name, f.path FROM Task_file f WHERE f.Task_hist_oid="+hist.get(i).get("Oid")));
		}
		
		
		request.setAttribute("hist", hist);
		
		return "edit";
	}
	
	public String deal(){
		Message msg=new Message();
		if(status.equals("")){
			msg.setError("請選擇狀態");
			this.savMessage(msg);
			return edit();
		}
		
		if(reply.trim().equals("")){
			msg.setError("請輸入處理過程");
			this.savMessage(msg);
			return edit();
		}
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date now=new Date();
		
		TaskHist h=new TaskHist();
		h.setTask_apply_oid(Integer.parseInt(Oid));
		h.setEdate(now);
		h.setEmpl(getSession().getAttribute("userid").toString());
		h.setReply(reply);
		df.update(h);
		
		//處理附加檔案
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
	            fileName=now.getTime()+"-"+i+bio.getExtention(fileUploadFileName[i]);//置換檔名            
	            filePath=getContext().getRealPath("/tmp" )+"/"+fileName;            
	            if(!df.testOnlineServer()){//測試的情況
	    			target="host_debug";
	    			filePath=filePath.replace("\\", "/");
	    			tmp_path=tmp_path.replace("\\", "/");
	    		}
	            dst=new File(tmp_path);//暫存資料夾			
				if(!dst.exists())dst.mkdir();
				bio.copyFile(fileUpload[i], new File(filePath));
				
				ftpinfo=df.sqlGetMap("SELECT "+target+" as host, username, password, path FROM SYS_HOST WHERE useid='TaskFile'");
				
				
				bio.putFTPFile(ftpinfo.get("host"), ftpinfo.get("username"), ftpinfo.get("password"), tmp_path+"/", ftpinfo.get("path")+"/"+h.getTask_apply_oid()+"/", fileName);
				df.exSql("INSERT INTO Task_file(Task_hist_oid, path, file_name)VALUES("+h.getOid()+",'task/"+h.getTask_apply_oid()+"/', '"+fileName+"');");		            
	        }
			
		}
		
		if(status.equals("T")){			
			//ta.setNext_empl(df.sqlGetStr("SELECT idno FROM empl WHERE Oid=")+next_empl.indexOf(","));
			df.exSql("UPDATE Task_apply SET status='T', next_empl='"+df.sqlGetStr("SELECT idno FROM empl WHERE Oid="+next_empl.substring(0, next_empl.indexOf(",")))+"' WHERE Oid="+Oid);
			msg.setSuccess("已儲存");
			this.savMessage(msg);		
			return execute();
		}
		
		if(status.equals("R")){
			df.exSql("UPDATE Task_apply SET next_empl=null WHERE Oid="+Oid);
			msg.setSuccess("已儲存");
			this.savMessage(msg);		
			return execute();
		}		
		
		StringBuilder sb=new StringBuilder("UPDATE Task_apply SET status='"+status+"', next_empl='"+df.sqlGetStr("SELECT idno FROM empl WHERE Oid="+next_empl.substring(0, next_empl.indexOf(",")))+"'");
		if(status.equals("C")){
			sb.append(",edate='"+sf.format(now)+"'");
		}
		sb.append("WHERE Oid="+Oid);
		df.exSql(sb.toString());
		
		
		
		
		
		
		
		
		
		
		msg.setSuccess("已儲存");
		this.savMessage(msg);		
		return edit();
	}

}
