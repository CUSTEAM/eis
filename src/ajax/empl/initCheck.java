package ajax.empl;
 
import java.util.Date;
import java.util.List;
import java.util.Map;
import action.BaseAction;
 
/**
 * 取行政評量
 * @author John
 */
public class initCheck extends BaseAction{
	
	private int aq;
	
	

	public int getAq() {
		return aq;
	}



	public void setAq(int aq) {
		this.aq = aq;
	}



	public String execute() {
		//行政評量期間判斷
		
		Date coansw_begin=(Date)getContext().getAttribute("date_aqansw_begin");
		Date coansw_end=(Date)getContext().getAttribute("date_aqansw_end");		
		Date now=new Date();
		if(now.getTime()>=coansw_begin.getTime() && now.getTime()<coansw_end.getTime()){
			if(df.sqlGetInt("SELECT COUNT(*)FROM Dtime WHERE techid='"+getSession().getAttribute("userid")+"'")>0){
				setAq(df.sqlGetInt("SELECT COUNT(*)FROM AQ_anser a WHERE a.idno='"+getSession().getAttribute("userid")+"'"));
			}else{
				setAq(999);
			}			
		}else{
			setAq(999);
		}
        return SUCCESS;
    } 
}