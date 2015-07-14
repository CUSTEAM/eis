<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ul class="nav">
    <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">學務管理系統 <b class="caret"></b></a>
        <ul class="dropdown-menu">        	
            <li><a href="/sais/TimeOffManager">缺曠管理</a></li>
            <li><a href="/tis/DilgView">班級曠缺紀錄</a></li>
            <li><a href="/tis/DilgApp">假單審核</a></li>
            <li class="divider"></li> 
            <li class="dropdown-submenu"><a href="#">操行獎懲管理</a>
                <ul class="dropdown-menu">
                	<li class="nav-header"><i class="icon-user"></i> 操行管理</li>
                	<li><a href="/sais/JustFilder">操行成績結算</a></li>
                	<li><a href="/sais/JustManager">操行結算後修正管理</a></li>                	
                	<li class="divider"></li>   
                	<li class="nav-header"><i class="icon-user"></i> 獎懲管理</li>                                 
                    <li><a href="/sais/DesdManager">獎懲資料管理</a></li>
                    <li><a href="/CIS/StudAffair/StudHistConduct.do">歷年操行成績資料維護</a></li>
                    <li><a href="/CIS/StudAffair/StudInspected.do">定察學生資料維護</a></li>
                    <li><a href="/sris/CountGstmd">流失學生輔導記錄查詢</a></li> 
                </ul>
            </li>
                      
            <li class="divider"></li>
              
            <li class="nav-header"><i class="icon-print"></i> 學務列印</li>
            <li><a href="/sais/TimeOffSearch">缺曠查詢列印</a></li>
            <li><a href="/tis/DilgView">曠缺紀錄統計</a></li>
		    <li class="dropdown-submenu"><a href="#">獎懲查詢列印</a>
                <ul class="dropdown-menu">                 
                    <li><a href="/CIS/StudAffair/KeepReports.do">定察學生相關名冊</a></li>                    
                    <li><a href="/CIS/StudAffair/GraduateJustSort.do">畢業生歷年操行成績排名及全勤</a></li>
                    <li><a href="/CIS/StudAffair/StudentCompositData.do">學生綜合資料表</a></li>                    
                </ul>
            </li>
            <li><a href="/CIS/StudAffair/CounselingReport.do?idenType=A">輔導記錄列印</a></li>
            <li><a href="/CIS/StudAffair/StayTimePrint.do">導師留校時間列印</a></li>
            <li class="divider"></li>              
            <li class="nav-header"><i class="icon-wrench"></i> 學務設定</li>
            <li><a href="/CIS/StudAffair/TutorManager.do">班級導師設定</a></li>
            <li class="dropdown-submenu"><a><i class=" icon-th-large" style="margin-top: 5px;"></i> 暑修相關作業</a>
				<ul class="dropdown-menu">
					<li><a href="/CIS/Summer/sOpenCourse.do">暑修查詢列印</a></li>
					<li><a href="/CIS/Summer/SetSummerVaction.do">梯次/日期 設定</a></li>
					<li><a href="/CIS/Summer/inputTimeOff.do">暑修請假資料維護</a></li>
				</ul>
			</li>
            <li class="dropdown-submenu"><a href="/CIS/Individual/Directory.do">操作權限及代碼設定</a>                         
                <ul class="dropdown-menu">
                    <li><a href="/CIS/StudAffair/ConductMarkCode.do">操行評語代碼維護</a></li>
                    <li><a href="/CIS/StudAffair/BonusPenaltyReasonCode.do">獎懲原因代碼維護</a></li>
                    <li><a href="/CIS/StudAffair/BonusPenaltyMarkCode.do">獎懲代碼及加減分設定</a></li>
                    <li><a href="/CIS/StudAffair/StudFeeCode.do">收費代碼資料維護</a></li>
                    <li><a href="/CIS/StudAffair/ConductInputTime.do">操行加減分輸入時間設定</a></li>             
                </ul>
            </li>
            <li><a href="/eis/sa/CalendarManager?sys=sais"> 各項日期管理</a></li>
            <li><a href="CheckNoHist"> 學期轉換前查核</a></li>
            <li class="divider"></li> 
            <li><a href="/sais/Contest">生活教育競賽</a></li>
            <li><a href="/sais/CallStatusView">點名狀況查詢</a></li>
            <li><a href="/CIS/Student/ClinicService/AppointmentView.do">預約掛號管理</a></li>
            <li><a href="/CIS/StudAffair/ReportPrint.do">兵役系統</a></li>                                      
        </ul>
    </li>                   
</ul>