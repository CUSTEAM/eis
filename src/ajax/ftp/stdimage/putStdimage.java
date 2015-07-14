package ajax.ftp.stdimage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import service.tools.ImageHandler;
import action.BaseAction;

/**
 * 儲存學生照片檔案
 * @author John
 */
public class putStdimage extends BaseAction {
	
	//private static final long serialVersionUID = 572146812454l;
	//private static final int BUFFER_SIZE = 16 * 1024;

	private File myFile;
	//private String contentType;
	private String fileName;
	private String imageFileName;
	//private String caption;
	public String myStdNo;
	
	private Object files[];	

	public Object[] getFiles() {
		return files;
	}

	public void setFiles(Object[] files) {
		this.files = files;
	}
	/*
	public void setMyFileContentType(String contentType) {
		this.contentType = contentType;
	}
	*/
	public void setMyFileFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}

	public String getImageFileName() {
		return imageFileName;
	}
	/*
	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}
	*/

	

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
		
		//來自學生自行上傳
		if(myStdNo!=null){	
			imageFileName = myStdNo + extent;
		}else{
			myStdNo=bio.getFileName(fileName);
			imageFileName = fileName;
		}
		
        File imageFile = new File(fullpath+imageFileName);
        bio.copyFile(myFile, imageFile);//copy至server
        
        ImageHandler img=new ImageHandler(fullpath+imageFile.getName());		
		float height=img.getHeight();
		float width=img.getWidth();
		
		if(width>140){
			float ratio=width/140;
			img.reduceImg(fullpath+imageFile.getName(), Math.round(width/ratio), Math.round(height/ratio));			
		}
		
		//FTP
		String folder;
		if(myStdNo.indexOf("10")==0){
			folder=myStdNo.substring(0, 3);
		}else{
			folder=myStdNo.substring(0, 2);
		}
		
		try{
			String target="host_runtime";
			if(!df.testOnlineServer())target="host_debug";			
			Map<String, String>ftpinfo=df.sqlGetMap("SELECT "+target+" as host, username, password, path FROM SYS_HOST WHERE useid='StdImage'");
			fullpath=fullpath.replace("\\", "/");
			
			/*
			FtpClient ftp=new FtpClient(ftpinfo.get("host"), ftpinfo.get("username"), ftpinfo.get("password"), null, null);				
			ftp.connect();				
			ftp.setLocalDir(fullpath+"/");
			ftp.setServerDir(ftpinfo.get("path")+"/"+folder+"/");
			ftp.setBinaryTransfer(true);
			ftp.put(imageFileName, true);
			*/
			
			bio.putFTPFile(ftpinfo.get("host"), ftpinfo.get("username"), ftpinfo.get("password"), fullpath+"/", 
					ftpinfo.get("path")+"/"+folder+"/", imageFileName);			
						
		}catch(Exception e){
			e.printStackTrace();				
		}
        
        m.put("name", imageFileName);
        files[0]=m;        
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