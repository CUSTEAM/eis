package action.sa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import action.BaseAction;
import model.Message;

public class BankMargeAction extends BaseAction{
	
	public String execute() {
		
		
		return SUCCESS;
	}
	
	public File upload;
	
	public String LandUpload() throws IOException {
		Message msg=new Message();
		if(upload==null){			
			msg.setError("未指定檔案");
			this.savMessage(msg);
			return SUCCESS;
		}
		
		FileInputStream fis = new FileInputStream(upload);
		XSSFSheet sheet;
		try{
			XSSFWorkbook xwb = new XSSFWorkbook(fis);
			sheet = xwb.getSheetAt(0);
		}catch(Exception e){
			msg.setError("請另存為xlsx檔案重新上傳");
			this.savMessage(msg);
			return SUCCESS;
		}
		
		XSSFRow row;	
		
		int check=0;
		
		String a;
		String b;
		String c;
		String d;
		String e;
		String f;
		String g;
		String h;
		SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sf1=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cl=Calendar.getInstance();
		Date date;
		
		for (int i = sheet.getFirstRowNum(); i <= sheet.getPhysicalNumberOfRows(); i++) {	
			
			if(sheet.getRow(i)==null)break;
			
			row = sheet.getRow(i);
			//if(row.getCell(0)==null)break;	
				if(i==0)
				try{//檢查標題
					
					Integer.parseInt(row.getCell(0).toString());
				}catch(Exception ex){
					msg.setError("發現標題欄位, 已跳過讀取<br>");
					continue;
				}
				
				
				
				
				
				try{
					a=readCellAsString(row.getCell(0));
					b=readCellAsString(row.getCell(1));
					c=readCellAsString(row.getCell(2));
					d=readCellAsString(row.getCell(3));
					e=readCellAsString(row.getCell(4));
					//f=readCellAsString(row.getCell(5));
					//g=readCellAsString(row.getCell(6));	
					//h=readCellAsString(row.getCell(7));					
									
					//df.exSql("UPDATE regmain SET  WHERE main_student_no='"+a+"'");					
					
					date=sf.parse("0"+e);
					cl.setTime(date);
					cl.add(Calendar.YEAR, 1911);
					e=sf1.format(cl.getTime());
					//System.out.println("INSERT INTO regmain (main_student_no, main_idno, main_virno_1, main_should_amt_1, main_real_amt_1, main_real_date_1) VALUES ('"+a+"', '"+df.sqlGetStr("SELECT idno FROM stmd WHERE student_no='"+a+"'")+"', '"+b+"', '"+c+"', '"+d+"', '"+sf1.format(cl.getTime())+"')ON DUPLICATE KEY UPDATE main_virno_1='"+b+"', main_should_amt_1='"+c+"', main_real_amt_1='"+d+"', main_real_date_1='"+e+"'");	
					
					try {
						System.out.println("INSERT INTO regmain (main_student_no, main_idno, main_virno_1, main_should_amt_1, main_real_amt_1, main_real_date_1) VALUES ('"+a+"', '"+df.sqlGetStr("SELECT idno FROM stmd WHERE student_no='"+a+"'")+"', '"+b+"', '"+c+"', '"+d+"', '"+sf1.format(cl.getTime())+"');");
						df.exSql("INSERT INTO regmain (main_student_no, main_idno, main_virno_1, main_should_amt_1, main_real_amt_1, main_real_date_1) VALUES ('"+a+"', '"+df.sqlGetStr("SELECT idno FROM stmd WHERE student_no='"+a+"'")+"', '"+b+"', '"+c+"', '"+d+"', '"+sf1.format(cl.getTime())+"');");
					}catch(Exception ex) {
						ex.printStackTrace();
						System.out.println("UPDATE regmain SET main_virno_1='"+b+"', main_should_amt_1='"+c+"', main_real_amt_1='"+d+"', main_real_date_1='"+e+"'WHERE main_student_no='"+a+"'");
						df.exSql("UPDATE regmain SET main_virno_1='"+b+"', main_should_amt_1='"+c+"', main_real_amt_1='"+d+"', main_real_date_1='"+e+"'WHERE main_student_no='"+a+"'");
					}
					
					
					
					
					check+=1;
					
					
				}catch(Exception ex){
					ex.printStackTrace();
					msg.addError("第"+(i+1)+"行");
					continue;
				}										
						
		}
		
		fis.close();
		//request.setAttribute("complete", complete);
		//request.setAttribute("fail", fail);
		msg.setSuccess("文件匯入完成, 共"+check+"筆資料");
		this.savMessage(msg);
		
		return execute();
	}
	
	public String MediumUpload() {
		
		
		return execute();
	}
	
	/**
	 * excel欄位強制轉文字 2003+
	 * @param cell
	 * @return
	 */
	private String readCellAsString(XSSFCell cell) {
		if (cell == null) {
			return null;
		}
		final DecimalFormat df = new DecimalFormat("####################0.##########");

		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_BLANK:
			return "";
		case HSSFCell.CELL_TYPE_BOOLEAN:
			return Boolean.valueOf(cell.getBooleanCellValue()).toString().trim();
		case HSSFCell.CELL_TYPE_NUMERIC:
			return df.format(cell.getNumericCellValue()).trim();
		case HSSFCell.CELL_TYPE_STRING:
			return cell.getStringCellValue().trim();
		case HSSFCell.CELL_TYPE_FORMULA:
			return cell.getCellFormula().trim();
		case HSSFCell.CELL_TYPE_ERROR:
			return Byte.toString(cell.getErrorCellValue());
		default:
			return "##POI## Unknown cell type";
		}
	}

}
