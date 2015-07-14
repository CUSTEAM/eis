<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ul class="nav">
	<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">統計圖表 <b class="caret"></b></a>
		<ul class="dropdown-menu">
			<!-- li class="dropdown-submenu"><a><i class="icon-share" style="margin-top: 3px;"></i> 教務</a>
				<ul class="dropdown-menu">
					<li><a href="/tis/DilgView">000</a></li>
				</ul>
			</li-->				
			<!--li class="nav-header"><i class="icon-chevron-down"></i> 學務</li-->	
			
			<li><a href="/eis/Outlook?view=dilg"> 缺曠趨勢</a></li>	
			<li><a href="/eis/Outlook?view=stdaddr"> 學生居住地統計</a></li>
			<li><a href="/eis/Outlook?view=stdfrom"> 畢業學校統計</a></li>
			
			
			<!-- li class="divider"></li> 
			
			
			<li class="divider"></li> 
			<li><a href="/eis/Outlook?view=desd"> 証照統計</a></li>
			<li><a href="/eis/Outlook?view=desd"> 期中考二一</a></li>
			<li><a href="/eis/Outlook?view=stdlost"> 流失統計</a></li>
			
			
			<li><a href="/eis/Outlook?view=desd"> 併班統計</a></li>
			<li class="divider"></li>
		
			<li class="nav-header"><i class="icon-search"></i> 主管資訊圖表</li-->
			
			<c:if test="${fn:indexOf(cookie['unit'].value, 'C')>-1}">
			<li class="divider"></li> 
			<li><a href="/eis/Outlook?view=merge"> 併班統計</a></li>
				
			</c:if>
			
			
		</ul>
	</li>
</ul>