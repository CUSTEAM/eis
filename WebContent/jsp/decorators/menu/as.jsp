<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ul class="nav">
	<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">系務系統 <b class="caret"></b></a>
		<ul class="dropdown-menu">		
			<li><a href="/CIS/DeptAssistant/StdSkill.do">學生證照管理</a></li>
			<li><a href="/CIS/Registration/CheckCsGroup.do">學程資格審查</a></li>
			<li><a href="http://ap168.cust.edu.tw/gradcheck/gradframe.php">畢業資格審查系統</a></li>
			<li class="divider"></li>			
			<li><a href="/csis/CourseManager?term=next"><i class="icon-share" style="margin-top: 5px;"></i> 課程規劃與管理</a></li>
			<li><a href="/CIS/Course/CsCoreManager.do">核心課程管理</a></li>							
			<li class="divider"></li>		
			<li><a href="/CIS/DeptAssistant/InvestigationSearch.do">應屆畢業生出路調查</a></li>
			<li><a href="/CIS/DeptAssistant/ReportPrint.do">報表列印</a></li>			
		</ul>
	</li>					
</ul>
<c:if test="${fn:indexOf(cookie['unit'].value, '130')>-1}">
<ul class="nav">
	<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">語言訓練中心 <b class="caret"></b></a>
		<ul class="dropdown-menu">		
			<li><a href="/csis/CourseManagerDemo"><i class="icon-check" style="margin-top: 5px;"></i> 課程管理</a></li>
			<li><a href="/csis/CourseManagerNext"><i class="icon-share" style="margin-top: 5px;"></i> 課程規劃</a></li>
			<li><a href="/tis/ExamsManager"><i class="icon-cog" style="margin-top: 5px;"></i> 考試管理</a></li>
		</ul>
	</li>					
</ul>
</c:if>