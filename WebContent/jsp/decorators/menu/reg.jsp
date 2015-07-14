<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ul class="nav">
	<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">學籍成績系統 <b class="caret"></b></a>
		<ul class="dropdown-menu">				
			
			<li><a href="/CIS/Registration/StudentDirectory.do"><i class="icon-user" style="margin-top: 5px;"></i> 學籍管理</a></li>
			
			<li class="dropdown-submenu"><a><i class="icon-folder-close" style="margin-top: 5px;"></i> 成績管理</a>
    			<ul class="dropdown-menu">
    				<li><a href="/sris/ScoreManager">成績管理</a></li>
    				<li><a href="/CIS/Score/ScoreNotUpload.do">本學期成績管理</a></li>
    				<li><a href="/CIS/Registration/ScoreManager.do">歷年成績管理</a></li>
    				<li><a href="/CIS/Score/ScoreHistAvg.do"> 歷年平均成績維護 </a></li>
    				<li><a href="/CIS/Summer/CheckOut.do"> 暑修成績管理 </a></li>
    			</ul>
    		</li>
			
			<li class="dropdown-submenu"><a><i class="icon-share-alt" style="margin-top: 5px;"></i> 批次作業</a>
    			<ul class="dropdown-menu">
    				<li><a href="/sris/DumpNewStmd">新生資料批次滙入</a></li>
    				<li><a href="/CIS/Regstration/Recruit/Config/EditStmdSchl.do">學生畢業學校維護</a></li>
    				<li><a href="/CIS/Course/UploadStdImage.do">學生照片批次匯入</a></li>
    				<li><a href="/CIS/Registration/EntranceDocNo.do">入學資格核准文號</a></li>    				
    				<li><a href="/CIS/Score/ScoreMaster.do">碩士班論文成績輸入</a></li>
    				<!-- li><a href="/sris/Upgrade">學生批次升級</a></li-->
        			<li><a href="/sris/ScoreFilder">期末成績整合併入歷年成績</a></li>
    			</ul>
    		</li>
    		
    		<li class="dropdown-submenu"><a><i class="icon-print" style="margin-top: 5px;"></i> 學籍列印</a>
    			<ul class="dropdown-menu">
    				<li><a href="/sris/ScorePrint">報表列印</a></li>
    				<li><a href="/CIS/Score/ReportPrint.do">成績列印 - 舊系統</a></li>
    				<li><a href="/CIS/Registration/ReportPrintList.do">學籍列印與照片預覽</a></li>
    				<li><a href="/csis/ForbidScoreList">1/3缺課名單</a></li>
    			</ul>
    		</li>
    		
			<li class="dropdown-submenu"><a><i class="icon-search" style="margin-top: 5px;"></i> 資料查核</a>
    			<ul class="dropdown-menu">
    				<li><a href="/sris/CheckStmdProfile">學生填報基本資料審查</a></li>
        			<li><a href="/CIS/Registration/CheckerDirectory.do">資格審查</a></li>
        			<li><a href="http://ap168.cust.edu.tw/gradcheck/gradframe.php">畢業資格審查</a></li>
        			<li><a href="/CIS/Registration/CheckCsGroup.do">學程資格審查</a></li>
        			<li><a href="/CIS/Registration/CheckeOut.do">學籍資料查核</a></li>
        			<li><a href="/CIS/Course/CheckOut.do">成績與選課資料查核</a></li>
        			<!--li><a href="CheckNoHist"> 學期轉換前查核</a></li-->
    			</ul>    			
    		</li>
    		
    		<li class="dropdown-submenu"><a><i class="icon-globe" style="margin-top: 5px;"></i> 線上服務</a>
    			<ul class="dropdown-menu">
        			<li><a href="/CIS/OnlineService/CaseView.do">服務狀態查詢</a></li>
        			<li><a href="/CIS/OnlineService/WorkingManager.do">服務設定</a></li>
        			<li><a href="/CIS/OnlineService/CaseManager.do">線上申請管理</a></li>
    			</ul>
    		</li>
    		
    		<li class="dropdown-submenu"><a><i class="icon-wrench" style="margin-top: 5px;"></i> 學籍設定</a>
    			<ul class="dropdown-menu">    				
    				<li><a href="/CIS/Registration/DeptNameManager.do">系所名稱設定</a></li>
    				<li><a href="/CIS/Course/ClassManager.do">班級管理</a></li>
    				<li><a href="/CIS/Registration/SetupClassGroup.do">設定班級組別</a></li>			
    				<li><a href="/eis/sa/CalendarManager?sys=sris"> 日期管理</a></li>    				
    			</ul>
    		</li>	
    		
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
			
			
    		
			<li class="divider"></li>
			<li><a href="/CIS/Registration/Register.do">註冊管理系統</a></li>
			<li><a href="/CIS/FEE/StdTransferAccount.do">學生轉帳帳號資料維護</a></li>
			<li><a href="/CIS/StudAffair/CounselingReport.do?idenType=A">輔導記錄列印</a></li>
		</ul>
	</li>					
</ul>