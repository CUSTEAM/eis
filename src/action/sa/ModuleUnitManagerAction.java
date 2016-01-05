package action.sa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import action.BaseAction;

/**
 * 權限管理
 * @author shawn
 *
 */
public class ModuleUnitManagerAction extends BaseAction{
	
	public String module, unit, emplOid, studentNo, Oid[];
	
	
	public String execute(){
		
		List<Map>list=(List<Map>) getContext().getAttribute("CODE_UNIT");
		for(int i=0; i<list.size(); i++){
			list.get(i).put("moduleCnt", df.sqlGetInt("SELECT COUNT(*)FROM SYS_MODULE_UNIT WHERE unit_id='"+list.get(i).get("id")+"'"));
		}
		getContext().setAttribute("CODE_UNIT", list);
		return SUCCESS;
	}
	
	/**
	 * 模擬
	 * @return
	 */
	public String chang(){
		
		if(!unit.equals("")){
			changUnit();
		}
		
		return SUCCESS;
	}
	
	/**
	 * 查詢
	 * @return
	 */
	public String search(){
		
		if(!unit.equals("")){
			request.setAttribute("mods", df.sqlGet("SELECT m.Oid, m.name FROM SYS_MODULE m, SYS_MODULE_UNIT u WHERE u.module_oid=m.Oid AND m.parent='0' AND u.unit_id='"+unit+"'"));
		}
		
		return SUCCESS;
	}
	
	/**
	 * 單位模擬
	 */
	private void changUnit(){		
		Cookie c[]=request.getCookies();
		for(int i=0; i<c.length; i++){
			if(c[i].getName().equals("unit")){
				c[i].setValue(c[i].getValue()+","+unit);
			}
			c[i].setMaxAge(60*60*24*365); // 瀏覽器關閉失效   	
			c[i].setDomain(".cust.edu.tw");
			c[i].setPath("/");
	    	response.addCookie(c[i]);
		}
	}
	
	/**
	 * 教職員模擬
	 */
	private void changEmpl(){
		
	}
	
	/**
	 * 學生模擬
	 */
	private void changStmd(){
	
	}
	
	
	
	/*public String changUser() throws Exception{
		
		sendLogout("Logout");
		
		
		String userid=emplOid.substring(0, emplOid.indexOf(","));
		
		
		//df.exSql("INSERT SYS_LOG_ACCESS(userid,action,accip)VALUES('"+username+"','"+request.getServletPath()+"','"+ip+"')");
		//寫身份cookie
		Cookie cookie = new Cookie("userid", request.getSession().getId()+userid.hashCode());	    	
		cookie.setMaxAge(60*60*24*365); // 瀏覽器關閉失效   	
    	cookie.setDomain(".cust.edu.tw");
    	cookie.setPath("/");
    	response.addCookie(cookie);
    	
    	//寫session cookie id	    	
    	dm.exSql("UPDATE wwpass SET sessionid='"+
    	request.getSession().getId()+userid.hashCode()+
    	"' WHERE username='"+userid+"'"); 
		
		response.sendRedirect("/eis/Calendar");//轉送至eis
		
		
		return SUCCESS;
	}
	
	private void sendLogout(String url) throws Exception{	
		
		//response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
		//response.setHeader("Location", url);
		
		
		System.out.println(request.getLocalName());;
		
		URL obj = new URL("http://"+request.getLocalName()+request.getContextPath()+"/"+url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());		
	}
	*/

}
