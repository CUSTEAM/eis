package action.nabbr;

import action.BaseAction;
import model.Message;

public class NabbrSetterAction extends BaseAction{
	
	public String dept;
	public String unit;
	public String building;
	public String floor;
	public String name2;
	public String Oid[];
	public String room_id;
	public String boro;
	public String seat;
	public String delRoom;
	public String d[], u[], b[], f[], r[], bo[];
	
	public String execute(){
		
		return SUCCESS;
	}
	
	public String search(){		
		StringBuilder sb=new StringBuilder("SELECT * FROM Nabbr n WHERE n.boro='"+boro+"'");
		if(!building.equals(""))sb.append("AND n.building='"+building+"'");
		if(!dept.equals(""))sb.append("AND n.dept='"+dept+"'");
		if(!room_id.equals(""))sb.append("AND n.room_id LIKE'"+room_id+"%'");
		if(!floor.equals(""))sb.append("AND n.floor='"+floor+"'");
		if(!unit.equals(""))sb.append("AND n.unit='"+unit+"'");		
		request.setAttribute("rooms", df.sqlGet(sb.toString()));
		
		return SUCCESS;
	}	
	
	public String del(){
		Message msg=new Message();
		if(df.sqlGetInt("SELECT COUNT(*)FROM Dtime_class WHERE place='"+delRoom+"'")>0){
			msg.setError("教室已排課");
			this.savMessage(msg);
			return search();
		}
		
		if(df.sqlGetInt("SELECT COUNT(*)FROM NabbrBorApp WHERE room_id='"+delRoom+"'")>0){
			msg.setError("教室已有使用登記");
			this.savMessage(msg);
			return search();
		}
		msg.setSuccess("已刪除");
		df.exSql("DELETE FROM Nabbr WHERE room_id='"+delRoom+"'");
		return search();
	}
	
	
	public String save(){
		
		for(int i=0; i<Oid.length; i++){
			
			if(!Oid[i].equals("")){
				df.exSql("UPDATE Nabbr SET building='"+b[i]+"', dept='"
				+d[i]+"', unit='"+u[i]+"',floor='"+f[i]
				+"',boro='"+bo[i]+"',room_id='"+r[i]+"'WHERE Oid="+Oid[i]);
			}
			
			
		}
		
		return search();
	}
	
	public String create(){
		Message msg=new Message();
		if(building.equals("")||room_id.equals("")||floor.equals("")||boro.equals("")){
			msg.setError("資料不完整");
			this.savMessage(msg);
			return SUCCESS;
		}
		try{
			df.exSql("INSERT INTO Nabbr(dept,unit,building,floor,room_id,boro)VALUES('"+dept+"','"+unit+"','"+building+"','"+floor+"','"+room_id+"','"+boro+"');");
		}catch(Exception e){
			msg.setError("新增失敗: "+e.getLocalizedMessage());
			this.savMessage(msg);
			return SUCCESS;
		}
		
		msg.setSuccess("已新增"+room_id);
		this.savMessage(msg);
		return search();
	}

}
