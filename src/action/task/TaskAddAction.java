package action.task;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import action.BaseAction;
import model.Message;
import model.Task;

/**
 * 工作單建立
 * @author shawn
 */
public class TaskAddAction extends BaseAction{
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
 
    
	
	public String Oid, addOid;
	public String appInfo;//, email;
	//public String unitSearch, begin, end;
	public String checker;
	
	public String execute(){
		
		
		
		
		
		
		
		
		return SUCCESS;
	}
	
	public String add(){
		
		
		return "add";
	}

	public String save(){		
		
		//處理附加檔案
		if(fileUpload!=null){
			Date now=new Date();	
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
				//String filePath=ftpinfo.get("path")+"/"+t.getOid()+"/", file_name;
				//bio.putFTPFile(ftpinfo.get("host"), ftpinfo.get("username"), ftpinfo.get("password"), tmp_path+"/", ftpinfo.get("path")+"/"+t.getOid()+"/", fileName);
				//df.exSql("INSERT INTO Task_file(Task_app_oid, path, file_name)VALUES("+t.getOid()+",'task/"+t.getOid()+"/', '"+fileName+"');");		            
	        }
			
		}		
		return execute();
	}
	
	
	
	
	public String unit,begin,end,stat;
	
	public String search(){
		//SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		StringBuilder sql=new StringBuilder("SELECT cts.name as status, e.cname, ta.Oid, cu.name, t.title, ta.edate, ta.sdate FROM "
		+ "CODE_TASK_STATUS cts,Task t, Task_apply ta LEFT OUTER JOIN empl e ON e.Oid=ta.next_empl, CODE_UNIT cu WHERE "
		+ "cts.id=ta.status AND t.unit=cu.id AND t.Oid=ta.Task ");
		if(!unit.equals(""))sql.append("AND t.unit='"+unit+"'");
		if(!begin.equals(""))sql.append("AND ta.sdate>='"+begin+"'");
		if(!end.equals(""))sql.append("AND ta.sdate<='"+end+"'");
		if(!stat.equals(""))sql.append("AND ta.status='"+stat+"'");
		sql.append("ORDER BY ta.Oid DESC");
		//System.out.println(sql);
		request.setAttribute("myApps", df.sqlGet(sql.toString()));
		return SUCCESS;
	}
	
	private List getUnit(){
		//單位
		List<Map>campus=dm.sqlGet("SELECT (SELECT COUNT(*)FROM Task WHERE unit=c.id)as cnt,id, name FROM CODE_CAMPUS c");
		List<Map>tmp;		
		
		for(int i=0; i<campus.size(); i++){
			tmp=dm.sqlGet("SELECT (SELECT COUNT(*) FROM Task WHERE unit IN(SELECT id FROM CODE_UNIT WHERE pid=c.id))as tal, (SELECT COUNT(*)FROM Task WHERE unit=c.id)as cnt, c.id, c.name "
			+ "FROM CODE_UNIT c WHERE (pid='0'AND id!='0') AND campus='"+campus.get(i).get("id")+"'");			
			for(int j=0; j<tmp.size(); j++){				
				tmp.get(j).put("sub_unit", dm.sqlGet("SELECT (SELECT COUNT(*)FROM Task WHERE unit=c.id)as cnt, c.id, c.name FROM CODE_UNIT c WHERE pid='"+tmp.get(j).get("id")+"'"));
				}			
			campus.get(i).put("unit", tmp);
		}
		return campus;
	}
	
	private String getId(String str){
		try{
			return str.substring(0, str.indexOf(","));
		}catch(Exception e){
			return "";
		}
	}
	

}
