package action.task;

import model.Message;
import model.Task;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import action.BaseAction;

/**
 * 單位內部任務管理
 * 工作單
 * @author shawn
 */
public class TaskTemplateManagerAction extends BaseAction{
	
	private File[] fileUpload;
    private String[] fileUploadFileName;
    private String[] fileUploadContentType;
    public String[] ensure;
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
	
	public String Oid[];
	public String unit[];
	public String title[];
	public String template[];
	public String open[];
	
	public String execute(){
		//TODO 考慮在登入時將unit加入session
		String unit=df.sqlGetStr("SELECT unit_module FROM empl WHERE idno='"+getSession().getAttribute("userid")+"'");
		request.setAttribute("unit", unit);			
		List<Map>ot=df.sqlGet("SELECT * FROM Task WHERE unit='"+unit+"'");
		for(int i=0; i<ot.size(); i++){
			
			ot.get(i).put("files", df.sqlGet("SELECT path, file_name FROM Task_file WHERE Task_oid="+ot.get(i).get("Oid")));
		
		}
		
		request.setAttribute("ot", ot);
		return SUCCESS;
	}
	
	public String add(){
		Message msg=new Message();
		if(title[0].trim().equals("")){
			msg.setError("必須有名稱");
			this.savMessage(msg);
			return execute();
		}
		try{			
			
			Date now=new Date();	
			String fileName;		
			String filePath;
			String tmp_path=getContext().getRealPath("/tmp");//本機目錄
			String target="host_runtime";
			File dst;
			Map<String, String>ftpinfo;
			File uploadedFile;
			Task task=new Task();
			task.setTarget("A");
			task.setTemplate(template[0]);
			task.setTitle(title[0]);
			task.setType("R");
			task.setEnsure(Integer.parseInt(ensure[0]));
			task.setUnit(unit[0]);
			df.update(task);
			//處理附加檔案
			if(fileUpload!=null)
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
				//String filePath=ftpinfo.get("path")+"/"+t.getOid()+"/", file_name;
				bio.putFTPFile(ftpinfo.get("host"), ftpinfo.get("username"), ftpinfo.get("password"), tmp_path+"/", ftpinfo.get("path")+"/", fileName);
				df.exSql("INSERT INTO Task_file(Task_oid, path, file_name)VALUES("+task.getOid()+",'task/', '"+fileName+"');");		            
	        }			
			msg.setSuccess("建立完成");
		}catch(Exception e){
			e.printStackTrace();
			msg.setError("建立失敗，請檢查內容");
		}		
		this.savMessage(msg);
		return execute();
	}
	
	public String edit(){
		Message msg=new Message();		
		for(int i=1; i<Oid.length; i++){			
			if(!Oid[i].equals("")){
				if(title[i].trim().equals("")){
					msg.setError("必須有名稱");
					this.savMessage(msg);
					return execute();
				}
				try{
					//System.out.println("UPDATE Task SET ensure="+ensure[i]+", unit='"+unit[i]+"', title='"+title[i]+"', template='"+template[i]+"' WHERE Oid="+Oid[i]);
					//df.exSql("UPDATE Task SET ensure="+ensure[i]+", unit='"+unit[i]+"', title='"+title[i]+"', template='"+template[i]+"' WHERE Oid="+Oid[i]);
					Task task=(Task)df.hqlGetListBy("FROM Task WHERE Oid="+Oid[i]).get(0);
					task.setEnsure(Integer.parseInt(ensure[i]));
					task.setTitle(title[i]);
					task.setTemplate(template[i]);
					df.update(task);
					msg.setSuccess("修改完成");
				}catch(Exception e){
					e.printStackTrace();
					msg.setError("修改失敗，請檢查內容");
				}
			}
		}		
		this.savMessage(msg);
		return execute();
	}
}