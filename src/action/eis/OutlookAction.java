package action.eis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONString;

import action.BaseAction;

/**
 * 圖表展示
 * @author shawn
 * @category TODO: 核心能力指標
 * TODO 消耗過多客戶端效能，需修改為單一項目檢視
 * 
 */
public class OutlookAction extends BaseAction{
	
	public String execute(){
		
		//併班
		if(request.getParameter("view").equals("merge")){			
			merge();
			return "merge";
		}
		
		//缺曠
		if(request.getParameter("view").equals("dilg")){			
			dilg();
			return "dilg";
		}
		
		//居住地
		if(request.getParameter("view").equals("stdaddr")){
			stdaddr();			
			return "stdaddr";
		}
		
		//生源
		if(request.getParameter("view").equals("stdfrom")){
			stdfrom();			
			return "stdfrom";
		}
		
		//流失
		if(request.getParameter("view").equals("stdlost")){
			stdlost();			
			return "stdlost";
		}
		
		if(request.getParameter("view").equals("desd"))return "desd";
		
		
		return SUCCESS;
	}
	
	//併班
	private void merge(){
		
		request.setAttribute("merge", df.sqlGet("SELECT cd.name, SUM(d.pid)as cnt FROM CODE_DEPT cd, Class c, Dtime d WHERE d.depart_class=c.ClassNo AND c.DeptNo=cd.id AND d.pid IS NOT NULL GROUP BY cd.id"));
		
	}
	
	//流失
	private void stdlost(){
		
	}
	
	//居住地
	private void stdaddr(){
		List<Map>tmp;
		JSONObject json;
		List<Map>col=df.sqlGet("SELECT id, name FROM CODE_COLLEGE");
		for(int i=0; i<col.size(); i++){
			
			tmp=df.sqlGet("SELECT s.geocode FROM stmd s, Class c WHERE s.depart_class=c.ClassNo AND s.geocode LIKE '%lat%' AND c.InstNo='"+col.get(i).get("id")+"'");
			for(int j=0; j<tmp.size(); j++){
				json=new JSONObject(tmp.get(j).get("geocode").toString());
				try{
					tmp.get(j).put("lat", json.get("lat"));
					tmp.get(j).put("lng", json.get("lng"));
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}			
			col.get(i).put("stds", tmp);			
		}
		
		
		List<Map>dep=df.sqlGet("SELECT id, name FROM stmd s, Class c, CODE_DEPT cd WHERE s.depart_class=c.ClassNo AND c.DeptNo=cd.id AND s.geocode LIKE '%lat%' GROUP BY cd.id");
		for(int i=0; i<dep.size(); i++){
			tmp=df.sqlGet("SELECT s.geocode FROM stmd s, Class c WHERE s.depart_class=c.ClassNo AND s.geocode LIKE '%lat%' AND c.DeptNo='"+dep.get(i).get("id")+"'");
			for(int j=0; j<tmp.size(); j++){
				json=new JSONObject(tmp.get(j).get("geocode").toString());
				try{
					tmp.get(j).put("lat", json.get("lat"));
					tmp.get(j).put("lng", json.get("lng"));
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}			
			dep.get(i).put("stds", tmp);
		}
		
		
		request.setAttribute("stdscol", col);
		request.setAttribute("stdsdep", dep);
		
		
		
		
		
		
		
		
		
		
		
		request.setAttribute("stdsall", df.sqlGetInt("SELECT COUNT(*)FROM stmd"));
		request.setAttribute("stdsnoschl", df.sqlGetInt("SELECT COUNT(*) FROM stmd s WHERE geocode IS NULL"));
		
		List<Map>stdsgeo=df.sqlGet("SELECT geocode FROM stmd s WHERE geocode IS NOT NULL AND geocode LIKE'%lat%'");
		
		for(int i=0; i<stdsgeo.size(); i++){
			
			json=new JSONObject(stdsgeo.get(i).get("geocode").toString());
			try{
				stdsgeo.get(i).put("lat", json.get("lat"));
				stdsgeo.get(i).put("lng", json.get("lng"));
				//System.out.println(json.get("lat")+", "+json.get("lng"));
			}catch(Exception e){
				//System.out.println(json);
			}
			
		}
		request.setAttribute("stdsgeo", stdsgeo);
		
		
	}
	
	//來源學校
	private void stdfrom(){		
		
		List<Map>schools=df.sqlGet("SELECT r.* FROM stmd s, Recruit_school r WHERE r.lat IS NOT NULL AND s.schl_code=r.no");
		List<Map>col=df.sqlGet("SELECT id, name FROM CODE_COLLEGE");
		for(int i=0; i<col.size(); i++){
			col.get(i).put("stds", df.sqlGet("SELECT r.* FROM stmd s, Recruit_school r, Class c, CODE_COLLEGE cs WHERE "
			+ "r.lat IS NOT NULL AND s.schl_code=r.no AND s.depart_class=c.ClassNo AND c.InstNo=cs.id AND cs.id='"+col.get(i).get("id")+"'"));
			
		}
		List<Map>dep=df.sqlGet("SELECT id, name FROM stmd s, Class c, CODE_DEPT cd WHERE s.depart_class=c.ClassNo AND c.DeptNo=cd.id AND s.geocode LIKE '%lat%' GROUP BY cd.id");
		for(int i=0; i<dep.size(); i++){
			dep.get(i).put("stds", df.sqlGet("SELECT r.* FROM stmd s, Recruit_school r, Class c, CODE_DEPT cs WHERE "
			+ "r.lat IS NOT NULL AND s.schl_code=r.no AND s.depart_class=c.ClassNo AND c.DeptNo=cs.id AND cs.id='"+dep.get(i).get("id")+"'"));
		}
		request.setAttribute("stdscol", col);
		request.setAttribute("stdsdep", dep);		
		
		request.setAttribute("stdsall", df.sqlGetInt("SELECT COUNT(*)FROM stmd"));
		request.setAttribute("stdsnoschl", df.sqlGetInt("SELECT COUNT(*)FROM stmd s, Recruit_school r WHERE s.schl_code=r.no"));
		
		request.setAttribute("schools", df.sqlGet("SELECT r.* FROM stmd s, Recruit_school r WHERE r.lat IS NOT NULL AND s.schl_code=r.no"));
	}
	
	//缺曠
	private void dilg(){
		List<Map>all=df.sqlGet("SELECT c.InstNo, c.DeptNo, c.SchoolType, c.Grade, d.* FROM BATCH_DILG_CLASS d, Class c WHERE d.dilgs>0 AND c.InstNo IS NOT NULL AND d.ClassNo=c.ClassNo");
		Map map;
		List<Map>tmp;
		
		int cnt[];		
		//校日曆圖
		request.setAttribute("days", df.sqlGet("SELECT COUNT(*)as cnt, "
		+ "DATE_FORMAT(date,'%Y')as y, DATE_FORMAT(date,'%c')as m, DATE_FORMAT(date,'%e')as d FROM Dilg WHERE abs<5 GROUP BY date"));
		//校18週線圖
		tmp=new ArrayList();
		
		for(int i=1; i<19; i++){
			cnt=new int[1];
			map=new HashMap();
			map.put("week", i);
			for(int j=0; j<all.size(); j++)
			if(  
				all.get(j).get("week").toString().equals(String.valueOf(i))					
			){						
				cnt[0]+=Integer.parseInt(all.get(j).get("dilgs").toString());						
			}
			map.put("cnt", cnt);
			tmp.add(map);
		}
		request.setAttribute("schcnt", tmp);		
		List<Map>col=df.sqlGet("SELECT id, name, (SELECT COUNT(*)FROM stmd s, Class c WHERE s.depart_class=c.ClassNo AND c.InstNo=CODE_COLLEGE.id)as stds FROM CODE_COLLEGE");
		
		//院18週日圖
		for(int i=0; i<col.size(); i++){
			col.get(i).put("days", df.sqlGet("SELECT COUNT(*)as cnt,DATE_FORMAT(d.date,'%Y')as y, "
					+ "DATE_FORMAT(d.date,'%c')as m,DATE_FORMAT(d.date,'%e')as d "
					+ "FROM Dilg d, Class c, stmd s WHERE d.abs<5 AND d.student_no=s.student_no "
					+ "AND s.depart_class=c.ClassNo AND c.InstNo='"+col.get(i).get("id")+"'GROUP BY date"));
		}
			
		//院18週線圖		
		for(int i=0; i<col.size(); i++){
			
			cnt=new int[18];
			for(int j=0; j<18; j++){
				
				for(int k=0; k<all.size(); k++){
					
					if(  
						all.get(k).get("InstNo").toString().equals(col.get(i).get("id").toString()) &&
						all.get(k).get("week").toString().equals(String.valueOf(j+1))
							
					){		
						cnt[j]+=Integer.parseInt(all.get(k).get("dilgs").toString());
					}
					
				}
				
			}
			col.get(i).put("cnt", cnt);
		}		
		request.setAttribute("col", col);		
		
		//系18週線圖
		List<Map>dep=df.sqlGet("SELECT id, college, sname, name,(SELECT COUNT(*)FROM stmd s, Class c WHERE s.depart_class=c.ClassNo AND c.DeptNo=CODE_DEPT.id)as stds FROM CODE_DEPT WHERE id!='0' AND college IS NOT NULL");		
		for(int i=0; i<dep.size(); i++){
			cnt=new int[18];
			for(int j=0; j<18; j++){
				
				for(int k=0; k<all.size(); k++){					
					if(  
						all.get(k).get("DeptNo").toString().equals(dep.get(i).get("id").toString()) &&
						all.get(k).get("week").toString().equals(String.valueOf(j+1))
							
					){		
						cnt[j]+=Integer.parseInt(all.get(k).get("dilgs").toString());
					}
				}				
			}			
			dep.get(i).put("cnt", cnt);
			dep.get(i).put("days", df.sqlGet("SELECT COUNT(*)as cnt,DATE_FORMAT(d.date,'%Y')as y, "
					+ "DATE_FORMAT(d.date,'%c')as m,DATE_FORMAT(d.date,'%e')as d "
					+ "FROM Dilg d, Class c, stmd s WHERE d.abs<5 AND d.student_no=s.student_no "
					+ "AND s.depart_class=c.ClassNo AND c.DeptNo='"+dep.get(i).get("id")+"'GROUP BY date"));
		}		
		request.setAttribute("dep", dep);		
	}

}
