package action.card;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Message;
import action.BaseAction;

/**
 * 匯入RFID內碼
 * @author shawn
 *
 */
public class CardManagerAction extends CardService{
	
	public File upload;
	public String username, inco;	
	
	public String saveTxtFile() throws IOException{
		Message msg=new Message();
		if(upload==null){
			msg.setError("請選擇檔案");
			this.savMessage(msg);
			return SUCCESS;
		}
		
		FileInputStream fis = new FileInputStream(upload);
		FileReader fr = new FileReader(fis.getFD());
		BufferedReader br = new BufferedReader(fr);
		String line; 
		//System.out.println(line);
		//df.exSql("INSERT INTO LC_exam_stmds (student_no) VALUES ('"+line+"')ON DUPLICATE KEY UPDATE student_no=VALUES(student_no);");
		//StringBuffer sb = new StringBuffer();
		//StringBuffer sb;
		String d[];
		//List<String[]>list=new ArrayList();
		List<Map<String,String>>w=df.sqlGet("SELECT username FROM wwpass");
		List fail=new ArrayList();
		List success=new ArrayList();
		int cnt=0;
		boolean b;
		StringBuilder inco;
		while ((line=br.readLine())!=null){
			cnt++;
			d=line.split(",");
			b=false;
			for(int i=0; i<w.size(); i++){
				if(w.get(i).get("username").equals(d[0])){
					try{
						//必須補滿10碼
						inco=new StringBuilder(d[1]);
						if(inco.length()<10) {
							for(int j=inco.length(); j<10; j++) {
								inco.insert(0, "0");
							}
						}
						
						df.exSql("UPDATE wwpass SET inco='"+inco+"' WHERE username='"+d[0]+"'");
					}catch(Exception e){
						e.printStackTrace();
						break;
					}
					success.add(d);
					w.remove(i);
					b=true;
					break;
				}
			}
			if(!b){
				fail.add(d);
			}			
		}		
		fis.close();
		fr.close();
		br.close();		
		
		request.setAttribute("fail", fail);
		request.setAttribute("success", success);
		
		
		if(fail.size()>0){
			msg.setInfo("讀取資料"+cnt+"行，無法建立"+fail.size()+"筆卡片資料");
		}else{
			msg.setInfo("已建立"+cnt+"筆卡片資料");
		}
		
		this.savMessage(msg);
		
		return SUCCESS;
	}

}
