package ajax.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
		System.out.println("work?");
		/*Map m=new HashMap();
        files=new Object[1];
		String extent=bio.getExtention(fileName);
		if(extent.indexOf("jpg")<0){			
	        m.put("name", null);
	        files[0]=m;	        
	        return SUCCESS;
		}*/
		
		//String fullpath=getContext().getRealPath("/tmp" )+"/";
		
		String path = getContext().getRealPath("/tmp" )+"/";
		
        File file = new File(path);
        if (!file.exists()) {  
            file.mkdir();  
        }  
        try {  
          if (this.file != null) {  
            File f = this.getFile();  
            String fileName = java.util.UUID.randomUUID().toString();   
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
            System.out.println(filePath);
  
          }  
        } catch (Exception e) {  
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