<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ul class="nav">
    <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">人事管理系統 <b class="caret"></b></a>
        <ul class="dropdown-menu">
            <li><a href="/CIS/Personnel/HRmanager.do">人事基本資料管理</a></li>
            <li><a href="/CIS/Personnel/HRChecker.do">人事基本資料查核</a></li>
            <li><a href="/hris/UnitManager">單位管理</a></li>
            <li><a href="/eis/sa/CardManager"> 識別證管理</a></li>
            <!--li><a href="/hris/UnitManager">單位管理</a></li-->
            <!-- li class="dropdown-submenu"><a href="#">教師資料設定</a>
                <ul class="dropdown-menu">
                    <li><a href="/CIS/Personnel/SkillStatistics.do">教師專長統計表</a></li>
                    <li><a href="/CIS/Personnel/TechlimitManager.do">設定授課時數*</a></li>
                    <li><a href="/hris/EmplTechlimit">教師各項時數</a></li>
                    <li><a href="/CIS/Personnel/StayTimeSetup.do">設定留校時間</a></li>
                    <li><a href="/CIS/Personnel/StayTimeSetup.do">設定留校時間(產學減免)</a></li>
                </ul>
            </li-->
            <li><a href="/hris/EmplTechlimit">教師各項時數</a></li>                                                    
            <li class="dropdown-submenu"><a href="#">差勤系統設定</a>
                <ul class="dropdown-menu">
                    <li><a href="/CIS/AMS/DocExamine.do">審查申請單</a></li>
                    <li><a href="/CIS/AMS/DocExamine.do?force=1">審查申請單(強制)</a></li>
                    <li><a href="/CIS/AMS/DocMeetingExamine.do">審查重要集會假單</a></li>
                    <li class="divider"></li>
                    <li><a href="/CIS/AMS/EmplAmsInfo.do"><i class="icon-search" style="margin-top: 3px;"></i> 查詢全校差勤記錄</a></li>
                    <li><a href="/CIS/AMS/CheckOvertime.do">超時工作查核</a></li>
                    <li><a href="/CIS/AMS/LeaveDocManager.do">審核者管理</a></li> 
                    <li><a href="/CIS/AMS/ArtificialInput.do">人工補登作業</a></li>
                    <!-- li><a href="/hris/WorkDateManager">人工補登作業</a></li-->
                    <li class="divider"></li>
                    <li><a href="/CIS/AMS/AmsMeeting.do">重要集會維護</a></li> 
                    <li><a href="/CIS/AMS/MeetingAttend.do">集會出席狀況維護</a></li>
                    <li class="divider"></li> 
                    <li><a href="/CIS/AMS/ReportPrint.do"><i class="icon-print" style="margin-top: 3px;"></i>報表列印*</a></li>
                     
                    <li class="divider"></li>
                    <li><a href="/CIS/AMS/Holiday.do">設定例假日期</a></li> 
                    <li><a href="/CIS/AMS/ShiftManager.do">設定應上應下班別時間</a></li> 
                    <li><a href="/CIS/AMS/EmplShiftManager.do">人員個人排班管理</a></li> 
                    <li><a href="/CIS/AMS/EmplStaticShiftManager.do">人員常態排班管理</a></li> 
                    <li><a href="/CIS/AMS/EmplStaticWorkManager.do">人員固定排班管理</a></li> 
                    <li><a href="/CIS/AMS/EmplHolidayManager.do">休假日數管理</a></li> 
                    <li><a href="/CIS/AMS/Empl7ShiftManager.do">每週個人排班管理</a></li>
                    <li><a href="/CIS/AMS/AskLeave.do">假別代碼設定</a></li>
                </ul>
            </li>
            <li><a href="http://ap168.cust.edu.tw/salytax/healthframe.php" target="_blank">二代健保系統</a></li>
            <li><a href="http://ap168.cust.edu.tw/yearfund/yearfundframe.php" target="_blank">公保年金系統</a></li>
            <li><a href="/eis/sa/CalendarManager?sys=hris"> 各項日期管理</a></li>
            <li class="divider"></li>
            <!--li><a href="/CIS/Personnel/ReportPrint.do"><i class="icon-print" style="margin-top: 3px;"></i>報表列印*</a></li-->
            <li><a href="/hris/Print"><i class="icon-print" style="margin-top: 3px;"></i>報表列印</a></li>                       
        </ul>
    </li>                   
</ul>
