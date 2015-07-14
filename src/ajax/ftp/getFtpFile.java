package ajax.ftp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import action.BaseAction;

public class getFtpFile extends BaseAction {
	private String filename;
	private String mimeType;
	private InputStream inStream;

	@Override
	public String execute() throws Exception {
		mimeType = getContext().getMimeType(filename);		
		return SUCCESS;
	}

	public InputStream getInStream() throws IOException {	
		
		String file=request.getParameter("file");
		String path=request.getParameter("path");
		
		Map map=(Map) getContext().getAttribute("DilgImage");
		
		
		try{			
			//TODO 進資料庫			
			String host="192.168.3.167";
			if(getContext().getAttribute("isServer").equals("0")){
				host="192.192.230.167";
			}
			
			inStream=bio.getFtpInputStream(host, "CIS", "cust!@#", path+"/", file);
			if (inStream == null) {
				inStream = new ByteArrayInputStream("Sorry,File not found !".getBytes());
			}			
		}catch(Exception e){
			e.printStackTrace();			
			
		}
		return inStream;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setFilename(String filename) {
		try {
			this.filename = new String(filename.getBytes("ISO8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getFilename() {
		try {
			return new String(filename.getBytes(),"ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return this.filename;
		}
	}
}