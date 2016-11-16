package ajax.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import service.tools.ImageHandler;
import action.BaseAction;

/**
 * 儲存工作單附件檔案
 * @author John
 */
public class putTaskFile extends BaseAction {
	
	private File file;            //文件  
    private String fileFileName;  //文件名   
    private String filePath;      //文件路径  
    private InputStream inputStream;
    
    private Map info;

	
	public Map getInfo() {
		return info;
	}


	public void setInfo(Map info) {
		this.info = info;
	}


	public File getFile() {
		return file;
	}


	public void setFile(File file) {
		this.file = file;
	}


	public String getFileFileName() {
		return fileFileName;
	}


	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}


	public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	public InputStream getInputStream() {
		return inputStream;
	}


	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}


	@Override
	public String execute() throws IOException {
		//System.out.println("work?");
		/*Map m=new HashMap();
        files=new Object[1];
		String extent=bio.getExtention(fileName);
		if(extent.indexOf("jpg")<0){			
	        m.put("name", null);
	        files[0]=m;	        
	        return SUCCESS;
		}*/
		
		//String fullpath=getContext().getRealPath("/tmp" )+"/";
		
		
		
        
        Map m=new HashMap();
        try {  
        	String path = getContext().getRealPath("/tmp" )+"/";
        	File file = new File(path);
            if (!file.exists()) {  
                file.mkdir();  
            }  
        	
          if (this.file != null) {  
        	  
        	/*Map ftpinfo=df.sqlGetMap("SELECT "+target+" as host, username, password, path FROM SYS_HOST WHERE useid='TaskFile'");
            File f = this.getFile();  
            String fileName = String.valueOf(new Date().getTime());   
            String name = fileName+ fileFileName.substring(fileFileName.lastIndexOf(".")); 
  
            FileInputStream inputStream = new FileInputStream(f);  
            FileOutputStream outputStream = new FileOutputStream(path+ "\\" + name);  
            byte[] buf = new byte[1024];  
            int length = 0;  
            while ((length = inputStream.read(buf)) != -1) {  
                outputStream.write(buf, 0, length);  
            }  
            inputStream.close();  
            outputStream.flush();  
            
            filePath = path+"\\"+name;  
            System.out.println(filePath);*/
        	  
        	Date now=new Date();	
  			String fileName;		
  			String filePath;
  			String tmp_path=getContext().getRealPath("/tmp");//本機目錄
  			String target="host_runtime";
  			File dst;
  			Map<String, String>ftpinfo;
  			File uploadedFile;
  			
            uploadedFile = this.getFile();  
            fileName=now.getTime()+bio.getExtention(getFileFileName());//置換檔名            
            filePath=getContext().getRealPath("/tmp" )+"/"+fileName;            
            if(!df.testOnlineServer()){//測試的情況
    			target="host_debug";
    			filePath=filePath.replace("\\", "/");
    			tmp_path=tmp_path.replace("\\", "/");
    		}
            dst=new File(tmp_path);//暫存資料夾			
			if(!dst.exists())dst.mkdir();
			bio.copyFile(uploadedFile, new File(filePath));
			ftpinfo=df.sqlGetMap("SELECT "+target+" as host, username, password, path FROM SYS_HOST WHERE useid='TaskFile'");
			bio.putFTPFile(ftpinfo.get("host"), ftpinfo.get("username"), ftpinfo.get("password"), tmp_path+"/", ftpinfo.get("path")+"/", fileName);
			df.exSql("INSERT INTO Task_file(Task_oid, path, file_name)VALUES("+request.getParameter("Oid")+", 'task/', '"+fileName+"');");		            
  	        
			
			m.put("file", fileName);
			m.put("path", "task/");
			this.setInfo(m);
          }  
        } catch (Exception e) { 
        	m.put("file", "");
			m.put("path", "task/");
			this.setInfo(m);
            e.printStackTrace();  
        }  
        return SUCCESS;
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	/*
	private boolean uploadImage2FTPServer(String FTPHost, String username, String password, String ServerDir, String LocalDir, String fileName){		
		try{
			FtpClient ftp=new FtpClient(FTPHost, username, password, "", "");			
			ftp.connect();
			ftp.setLocalDir(LocalDir+"/");
			ftp.setServerDir(ServerDir+"/");						
			ftp.setBinaryTransfer(true);
			ftp.put(fileName, true);
			ftp.disconnect();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			//ftp.disconnect();
			return false;
		}		
	}
	*/
}