package ajax.ftp;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import action.BaseAction;

/**
 * 儲存系文件附件檔案
 * @author John
 */
public class putSysDocFile extends BaseAction {

	private File myFile;
	private String fileName;
	private String imageFileName;
	
	private Object files[];	

	public Object[] getFiles() {
		return files;
	}

	public void setFiles(Object[] files) {
		this.files = files;
	}
	
	public void setMyFileFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}

	public String getImageFileName() {
		return imageFileName;
	}	

	@Override
	public String execute() throws IOException {
		Map m=new HashMap();
        files=new Object[1];
		String extent=bio.getExtention(fileName);
		if(extent.indexOf("jpg")<0){			
	        m.put("name", null);
	        files[0]=m;	        
	        return SUCCESS;
		}
		
		String fullpath=getContext().getRealPath("/tmp" )+"/";
		
		//檔名為時間
		imageFileName = new Date().getTime()+extent;
		
        File imageFile = new File(fullpath+imageFileName);
        bio.copyFile(myFile, imageFile);//copy至server        
		
		//FTP
		String folder="img";
		
		
		try{
			String target="host_runtime";
			if(!df.testOnlineServer())target="host_debug";			
			Map<String, String>ftpinfo=df.sqlGetMap("SELECT "+target+" as host, username, password, path FROM SYS_HOST WHERE useid='SysDoc'");
			fullpath=fullpath.replace("\\", "/");			
			
			bio.putFTPFile(ftpinfo.get("host"), ftpinfo.get("username"), ftpinfo.get("password"), fullpath+"/", 
					ftpinfo.get("path")+"/"+folder+"/", imageFileName);
			
			df.exSql("INSERT INTO SYS_DOC_FILE(doc_oid,type,file_name)VALUES("+request.getParameter("docOid")+",'"+request.getParameter("xtype")+"', '"+imageFileName+"');");
						
		}catch(Exception e){
			e.printStackTrace();				
		}
        
        m.put("name", imageFileName);
        files[0]=m;        
        return SUCCESS;
	}
}