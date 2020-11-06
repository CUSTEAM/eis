package ajax;
 
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import action.BaseAction;
 
/**
 * 圖書館
 * TODO 即時訊息、即時警告、即時...
 * @author John
 */
public class OnlineSync4Lib extends BaseAction{
	
	private List readerInfo;	
	
	public String execute() {
		
		//JSON4LIB
		
		List<Map>JSON4LIB=df.sqlGet("SELECT host_runtime FROM SYS_HOST WHERE protocol='JSON4LIB'");
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		Boolean b=false;
		if (ipAddress == null || "".equals(ipAddress)) {

			ipAddress = request.getRemoteAddr();
			System.out.println(ipAddress);
			for(int i=0; i<JSON4LIB.size(); i++) {
				if(ipAddress.equals(String.valueOf(JSON4LIB.get(i).get("host_runtime")))) {
					b=true;
					break;
				}
			}
		}
		
		if(b) {
			setReaderInfo(df.sqlGet("select c.ClassNo, c.ClassName, c.DeptNO, c.SeqNo, c.SchoolNo, c.SchoolType, c.ShortName, c.SchNo,  `s`.`student_no` AS `student_no`,"
					+ "`s`.`depart_class` AS `depart_class`, `s`.`student_name` AS `student_name`,"
					+ " `s`.`idno` AS `idno`, `s`.`birthday` AS `birthday`,"
					+ " `s`.`telephone` AS `telephone`, `s`.`CellPhone` AS `CellPhone`,"
					+ " `s`.`curr_post` AS `curr_post`, `s`.`curr_addr` AS `curr_addr`,"
					+ " `s`.`perm_post` AS `perm_post`, `s`.`perm_addr` AS `perm_addr`,"
					+ " `s`.`sex` AS `sex`, `s`.`Email` AS `Email`, `s`.`entrance` AS `entrance`,"
					+ " `sc`.`card_num` AS `card_num`from (`stmd` `s` left join `StmdCardNum` `sc` "
					+ "on ((`s`.`student_no` = `sc`.`student_no`))), Class c WHERE c.ClassNo=s.depart_class LIMIT 10;"));
	        
		}else {
			Map m=new HashMap();
			m.put("atime", new Date());
			m.put("amsg", ipAddress+" 非指定IP位置");
			List<Map>list=new ArrayList();
			list.add(m);
			setReaderInfo(list);
		}
		
		
			
		
		
		return SUCCESS;
    }

	public List getReaderInfo() {
		return readerInfo;
	}

	public void setReaderInfo(List readerInfo) {
		this.readerInfo = readerInfo;
	}


	
	
	
}