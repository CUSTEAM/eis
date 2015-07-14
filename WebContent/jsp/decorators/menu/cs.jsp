<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ul class="nav">
	<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">課程管理系統 <b class="caret"></b></a>
		<ul class="dropdown-menu">						
			<!-- li><a href="/csis/CourseManager"><i class="icon-check" style="margin-top: 5px;"></i> 課程管理</a></li>
			<li><a href="/csis/CourseManagerNext"><i class="icon-share" style="margin-top: 5px;"></i> 課程規劃</a></li>
			<li><a href="/CIS/Course/OpenCourse.do"><i class="icon-book" style="margin-top: 5px;"></i> 課程管理(舊系統)</a></li-->
			<li><a href="/csis/CourseManager?term=this"><i class="icon-check" style="margin-top: 5px;"></i> 課程管理</a></li>
			<!--li><a href="/csis/CourseManager?term=next"><i class="icon-share" style="margin-top: 5px;"></i> 課程規劃</a></li-->
			<li><a href="/csis/AddSeld"><i class="icon-ok-circle" style="margin-top: 5px;"></i> 第1學期選課管理</a></li>	
			<li><a href="/csis/AddSeldNext"><i class="icon-ok-circle" style="margin-top: 5px;"></i> 第2學期選課管理</a></li>
			<li><a href="/csis/MergeSeld"><i class="icon-ok-circle" style="margin-top: 5px;"></i> 併班選課管理</a></li>
			<li><a href="/CIS/Course/CourseHist.do"><i class="icon-time" style="margin-top: 5px;"></i> 歷年課程資料</a></li>
			<li class="dropdown-submenu"><a><i class=" icon-th-large" style="margin-top: 5px;"></i> 暑修相關作業</a>
				<ul class="dropdown-menu">
					<li><a href="/CIS/Summer/sOpenCourse.do">暑修課程管理</a></li>
					<li><a href="/CIS/Summer/SetSummerVaction.do">梯次/日期 設定</a></li>
					<li><a href="/CIS/Summer/inputTimeOff.do">暑修請假資料維護</a></li>
					<li><a href="/CIS/Summer/CheckOut.do">暑修批次處理</a></li>
					<li><a href="/CIS/Summer/OnlineClassSelection.do">暑修加退選作業</a></li>
					<li><a href="/CIS/Summer/ClassManager.do">暑修班級管理</a></li>
				</ul>
			</li>
			
			<li class="dropdown-submenu"><a><i class="icon-search"></i> 查核與列印</a>
				<ul class="dropdown-menu">
					<li><a href="/CIS/Course/CheckOut.do">課務查核</a></li>
					<li><a href="/csis/ForbidScoreList">1/3缺課名單</a></li>
					<li><a href="/CIS/Registration/CheckCsGroup.do">學程資格審查</a></li>					
					<li><a href="/CIS/Course/SelectFilter.do">學生選課篩選</a></li>
					<li class="nav-header"><i class="icon-share-alt"></i> 批次作業</li>
					<li><a href="/CIS/Course/BatchProcess.do">批次作業</a></li>
					<li><a href="/CIS/Course/ReportPrintList.do">課務列印</a></li>								
				</ul>
			</li>
						
			<li class="dropdown-submenu"><a><i class="icon-th" style="margin-top: 5px;"></i> 設定管理</a>
				<ul class="dropdown-menu">
					<li><a href="/CIS/Course/CscodeManager.do">科目代碼管理</a></li>
					<li><a href="/CIS/Course/SetRoom.do">教室管理</a></li>
					<li><a href="/CIS/Course/Seld3ScheduleManager.do">通識選課上限管理</a></li>
					<li><a href="/CIS/Course/GroupManager.do">學程管理</a></li>
					<li><a href="/csis/CoanswManager">教學評量管理</a></li>
					<li><a href="/csis/ElectiveManager">網路選課管理</a></li>					
					<li><a href="/CIS/Course/CsCoreManager.do">核心課程管理</a></li>
					<li><a href="/CIS/Course/LiteracyType.do">通識科目分類管理</a></li>					
					<li><a href="/CIS/Course/DtimestampManager.do">節次時間管理</a></li>
					<li><a href="/csis/ReSetDtime">重新設定前學期課程</a></li>	
					<li><a href="/eis/sa/CalendarManager?sys=csis"> 各項日期管理</a></li>				
				</ul>
			</li>
			
		</ul>
	</li>					
</ul>