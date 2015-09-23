<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ul class="nav">				
	<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">電算中心 <b class="caret"></b></a>
		<ul class="dropdown-menu">
		    <!-- 設定 -->
			<li class="dropdown-submenu"><a href="#" class="dropdown-toggle" data-toggle="dropdown">系統設定</a>
		        <ul class="dropdown-menu">		            
		            
		            <li><a href="/CIS/SysAdmin/UserPasswordReset.do"> 重設使用者密碼</a></li>
		            <li><a href="/CIS/SysAdmin/GroupMemberSetup.do"> 群組成員設定</a></li>
		            <li><a href="/CIS/SysAdmin/ClassInChargeSetup4TeachAffair.do"> 教務負責班級</a></li>
		            <li><a href="/CIS/SysAdmin/ClassInChargeSetup4StudAffair.do"> 學務負責班級</a></li>
		            <li><a href="CheckNoHist"> 學期轉換前查核</a></li>	
		            <li><a href="/eis/ChangeTerm"> 學期轉換</a></li>	
		            <!-- li><a href="/CIS/Course/DtimeReserverSync.do"> 課程規劃開課作業</a></li-->	
		            
		            <li><a href="/eis/sa/CalendarManager?sys=cisa"> 各項日期管理</a></li>	
		            <li><a href="/eis/sa/CardManager"> 卡片管理</a></li>		            
		            <li class="dropdown-submenu"><a><i class="icon-tasks" style="margin-top: 5px;"></i> 工作單管理</a>
						<ul class="dropdown-menu">
							<li><a href="/eis/TaskAdd">工作單申請與查詢</a></li>
							<li><a href="/eis/TaskDeal">工作單處理</a></li>
							<li><a href="/eis/TaskTemplateManager">單位工作項目管理</a></li>
						</ul>
					</li>
					
					
					<li class="dropdown-submenu"><a><i class="icon-book" style="margin-top: 5px;"></i> 系統文件管理</a>
						<ul class="dropdown-menu">							
							<li><a href="/eis/sa/QAManager"> 文件管理</a></li>
							<li><a href="/eis/sa/QAPGManager"> 程式管理</a></li>
						</ul>
					</li>
					
		        </ul>
		    </li>
		    
		    <!-- 教師 -->
			<li class="dropdown-submenu"><a href="#" class="dropdown-toggle" data-toggle="dropdown">教師系統</a>
				<ul class="dropdown-menu">
					<li class="divider"></li>
					<li><a href="/tis/RollCall"><i class="icon-check" style="margin-top: 3px;"></i> 線上點名</a></li>
										
					<li class="dropdown"><a href="/CIS/Teacher/ScoreManager.do"><i class="icon-th-list" style="margin-top: 3px;"></i> 成績輸入與列印</a></li>
					<li class="dropdown"><a href="/tis/ScoreManager"><i class="icon-th-list" style="margin-top: 3px;"></i> 成績輸入與列印</a></li>
					<li class="divider"></li>		
					<li class="dropdown"><a href="/CIS/Teacher/SummerRat.do"><i class="icon-edit" style="margin-top: 3px;"></i> 成績輸入(暑修課程)</a></li>
							
					<li><a href="/CIS/Teacher/CourseInfo.do"><i class="icon-book" style="margin-top: 3px;"></i> 課程簡介管理</a></li>
							
					<li class="dropdown-submenu"><a><i class="icon-th-list" style="margin-top: 3px;"></i> 導師相關作業</a>
						<ul class="dropdown-menu">
							<li><a href="/tis/DilgView">班級曠缺紀錄</a></li>
							<li><a href="/tis/DilgApp">學生假單審核</a></li>
							<li><a href="/sais/ConductCredits">操行成績管理</a></li>
							<li><a href="/CIS/Teacher/Tutor/LifeCounseling.do">生活輔導時間</a></li>
							<li><a href="/CIS/Teacher/Tutor/CounselingT.do">職涯與學習輔導記錄</a></li>
							<li><a href="/tis/CareForStudents">_職涯與學習輔導記錄</a></li>
							<li><a href="/CIS/Teacher/Tutor/GraduateInvestigation.do">應屆畢業生調查表</a></li>
							<li><a href="/CIS/Teacher/ClassCadre.do">班級幹部聯繫資料</a></li>
							<li><a href="/CIS/StudAffair/CounselingReport.do?idenType=T">輔導記錄查詢列印</a></li>
						</ul>
					</li>
							
					<li class="dropdown-submenu"><a><i class="icon-folder-open" style="margin-top: 3px;"></i> 研究成果資料</a>
						<ul class="dropdown-menu">
							<li class="nav-header"><i class="icon-pencil"></i> 資料輸入</li>
							<li><a href="/CIS/Teacher/Rcact.do">1-7 參加學術活動資料輸入</a></li>
							<li><a href="/CIS/Teacher/Rcproj.do">1-8 承接政府部門計劃與產學案資料輸入</a></li>
							<li><a href="/CIS/Teacher/Rcjour.do">1-9 期刊論文發表資料輸入</a></li>
							<li><a href="/CIS/Teacher/Rcconf.do">1-10 研討會論文發表資料輸入</a></li>
							<li><a href="/CIS/Teacher/Rcbook.do">1-11 專書(含篇章)資料輸入</a></li>
							<li><a href="/CIS/Teacher/Rcpet.do">1-12 專利資料輸入</a></li>
							<li><a href="/CIS/Teacher/Rchono.do">1-13 獲獎與榮譽資料輸入</a></li>
							<li class="nav-header"><i class="icon-search"></i> 資料查詢</li>
							<li><a href="/CIS/Teacher/Rcact_Query.do">1-7 參加學術活動資料查詢</a></li>
							<li><a href="/CIS/Teacher/Rcproj_Query.do">1-8 承接政府部門計劃與產學案查詢</a></li>
							<li><a href="/CIS/Teacher/Rcjour_Query.do">1-9 期刊論文發表資料查詢</a></li>
							<li><a href="/CIS/Teacher/Rcconf_Query.do">1-10 研討會論文發表資料查詢</a></li>
							<li><a href="/CIS/Teacher/Rcbook_Query.do">1-11專書(篇章)資料表查詢</a></li>
							<li><a href="/CIS/Teacher/Rcpet_Query.do">1-12 專利資料表查詢</a></li>
							<li><a href="/CIS/Teacher/Rchono_Query.do">1-13 獲獎與榮譽資料查詢</a></li>
						</ul>
					</li>
							
					<li class="dropdown-submenu"><a><i class="icon-home" style="margin-top: 5px;"></i> 教師教學歷程</a>
						<ul class="dropdown-menu">
							<li><a href="/CIS/Portfolio/REDirectory.do">數位歷程首頁</a></li>
							<li><a href="/CIS/Portfolio/SiteManager.do">網站管理</a></li>
							<li><a href="/CIS/Portfolio/PageManager.do">文章管理</a></li>
							<li><a href="/CIS/Portfolio/DownloadPortfolio">我的電子履歷Alpha</a></li>
							<li><a href="/CIS/Portfolio/EPortfolioManager.do">教學歷程管理</a></li>
							<li><a href="/CIS/Portfolio/ListMyStudents.do">授課學生列表</a></li>
							<li><a href="/CIS/Portfolio/MySkill.do">專長資料管理</a></li>
							<li><a href="/CIS/Portfolio/Report.do">統計資料</a></li>
							<li><a href="/CIS/Portfolio/UcanReport.do">UCAN統計資料</a></li>
						</ul>
					</li>
								
					<li class="divider"></li>
					<li class="nav-header"><i class="icon-th"></i> 應用程式</li>
					<li><a href="/CIS/Teacher/Coansw4Tech.do">教學評量</a></li>
					<li><a href="/tis/AddPubLeave">學生公假管理</a></li>
					<li><a href="/CIS/Teacher/StayTimeInput.do">個人留校時間設定</a></li>
					<li><a href="/CIS/Teacher/CounselingL.do">學生學習輔導記錄</a></li>							
					<li class="nav-header"><i class="icon-th-large"></i> 應用程式群組</li>							
					<li><a href="http://ap168.cust.edu.tw/Performance/Performance.php">教學績效系統</a></li>
					<li><a href="/CIS/LaboratoryManage.do">實驗室管理系統</a></li>							
					<li><a href="http://ap168.cust.edu.tw/HighCare/HighCare.php">高關懷學生輔導系統</a></li>							
					<li><a href="http://ap168.cust.edu.tw/physics/phyframe.php">運動績優輔導系統</a></li>
					
					<li class="divider"></li>							
					<li class="dropdown-submenu"><a href="#"><i class="icon-file" style="margin-top: 5px;"></i> 表單與文件下載</a>
						<ul class="dropdown-menu">
							<li><a href="/CIS/Secretary/File_Download.do">會議/法規-資料下載</a></li>
							<li><a href="/CIS/EmpEvaluate/EmpEvaluate4T.do">教師考核</a></li>
							<li><a href="/CIS/pages/downloads/TeacherTrainingAgressmentForm.pdf#pagemode=none&zoom=80">教師進修協議書</a></li>
							<li><a href="/CIS/pages/downloads/EmpEvaluate4TPaper1.pdf#pagemode=none&zoom=80">教師評鑑作業流程</a></li>
							<li><a href="/CIS/pages/downloads/EmpEvaluate4TPaper2.doc">評鑑總表與評鑑評量表</a></li>
							<li><a href="/CIS/pages/downloads/EmpEvaluate4Trule.pdf#pagemode=none&zoom=80">中華科技大學專任教師評鑑辦法</a></li>
							<li><a href="/CIS/pages/downloads/NewTeacherManual.pdf#pagemode=none&zoom=80">新進教師研習手冊資料</a></li>
							<li><a href="/CIS/pages/downloads/Retirement.doc">專任講師優退資遣辦法</a></li>
							<li><a href="/CIS/pages/downloads/RetirementPaper.doc">專任講師優退申請書</a></li>
						</ul>
					</li>													
				</ul>
			</li>
			
			<!-- 註冊 -->
			
			<li class="dropdown-submenu"><a href="#" class="dropdown-toggle" data-toggle="dropdown">學籍成績系統 <b class="caret"></b></a>
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
		    				<li><a href="/CIS/Registration/SetupClassGroup.do">設定班級組別</a></li>
		    				<li><a href="/CIS/Score/ScoreMaster.do">碩士班論文成績輸入</a></li>
		    				<li><a href="/sris/ScoreFilder">期末成績整合併入歷年成績</a></li>
		    				<li><a href="/sris//Upgrade">學生批次升級</a></li>
		    				<li class="divider"></li>
		        			<!--li><a href="/CIS/Score/JustToHistory.do">儲存歷年操行成績</a></li>
		        			<li><a href="/CIS/Score/ScoreAvg.do">儲存歷年平均成績</a></li>
		        			<li><a href="/CIS/Score/ScoreWarehouse.do">儲存歷年成績</a></li-->
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
		        			<li><a href="/CIS/Registration/CheckerDirectory.do">資格審查</a></li>
		        			<li><a href="/sris/CheckStmdProfile">學生填報基本資料查核</a></li>
		        			<li><a href="http://ap168.cust.edu.tw/gradcheck/gradframe.php">畢業資格審查</a></li>
		        			<li><a href="/CIS/Registration/CheckCsGroup.do">學程資格審查</a></li>
		        			<li><a href="/CIS/Registration/CheckeOut.do">學籍資料查核</a></li>
        					<li><a href="/CIS/Course/CheckOut.do">成績與選課查核</a></li>		        			
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
		    				
		    				<li><a href="/CIS/Registration/DeptNameManager.do">系所管理</a></li>
		    				<li><a href="/CIS/Course/ClassManager.do">班級管理</a></li>    				
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
					
				</ul>
			</li>
			
			<!-- 學務 -->
		    <li class="dropdown-submenu"><a href="#" class="dropdown-toggle" data-toggle="dropdown">學務管理系統 </a>
		        <ul class="dropdown-menu">        	
		            <li><a href="/sais/TimeOffManager">缺曠管理</a></li>
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
				    <li class="dropdown-submenu"><a href="#">獎懲查詢列印</a>
		                <ul class="dropdown-menu">
		                	<li><a href="/tis/DilgView">曠缺紀錄統計</a></li>
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
		            
		            <li class="divider"></li> 
		            <li><a href="/sais/Contest">生活教育競賽</a></li>
		            <li><a href="/sais/CallStatusView">點名狀況查詢</a></li>
		            <li><a href="/CIS/Student/ClinicService/AppointmentView.do">預約掛號管理</a></li>
		            <li><a href="/CIS/StudAffair/ReportPrint.do">兵役系統</a></li>		                                      
		        </ul>
		    </li>	

		    <!-- 課務 -->
		    <li class="dropdown-submenu"><a href="#" class="dropdown-toggle" data-toggle="dropdown">課程管理系統</a>
				<ul class="dropdown-menu">
					
					<li><a href="/csis/CourseManager?term=this">課程管理</a></li>
					<li><a href="/csis/CourseManager?term=next">課程規劃</a></li>
					<!--li><a href="/csis/CourseManager?term=demo">系務課程管理</a></li-->
					<li><a href="/CIS/Course/CourseHist.do">歷年課程資料</a></li>
					<li class="divider"></li>
					<li><a href="/csis/AddSeld"><i class="icon-ok-circle" style="margin-top: 5px;"></i> 第1學期選課管理</a></li>	
					<li><a href="/csis/AddSeldNext"><i class="icon-ok-circle" style="margin-top: 5px;"></i> 第2學期選課管理</a></li>
					<li><a href="/csis/MergeSeld"><i class="icon-ok-circle" style="margin-top: 5px;"></i> 併班選課管理</a></li>
							
					<li class="divider"></li>
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
					
					<!-- li class="dropdown-submenu"><a><i class="icon-book" style="margin-top: 5px;"></i> 課程規劃系統</a>
						<ul class="dropdown-menu">
							<li><a href="/CIS/Course/BatchStaticTableing.do">固定節次排課</a></li>
							<li><a href="/CIS/DepAssistant/DtimeReserveManager.do">課程規劃</a></li>
							<li><a href="/CIS/DepAssistant/DtimeManager.do">課程管理</a></li>
							<li><a href="/CIS/DepAssistant/DtimeReserveOption.do">時數規劃</a></li>
							<li><a href="/CIS/DepAssistant/Timetabling.do">課表排課管理</a></li>
							<li><a href="/CIS/DepAssistant/FestTimetabling.do">快速排課管理</a></li>
							<li><a href="/CIS/Course/GenTable.do">課表查詢列印</a></li>
							<li><a href="/CIS/Course/TeacherCourseSearch.do">教師課表查詢</a></li>
						</ul>
					</li-->
					
					<li class="dropdown-submenu"><a><i class="icon-th" style="margin-top: 5px;"></i> 設定管理</a>
						<ul class="dropdown-menu">
							<li><a href="/CIS/Course/CscodeManager.do">科目代碼管理</a></li>
							<li><a href="/CIS/Course/SetRoom.do">教室管理</a></li>							
							<li><a href="/CIS/Course/Seld3ScheduleManager.do">通識選課上限管理</a></li>
							<li><a href="/CIS/Course/GroupManager.do">學程管理</a></li>
							<li><a href="/csis/CoanswManager">教學評量管理</a></li>
							<li><a href="/CIS/Course/SetCsquestionary.do">教學評量管理*</a></li>
							<li><a href="/CIS/Course/SeldScheduleManager.do">網路選課管理*</a></li>
							<li><a href="/csis/ElectiveManager">網路選課管理</a></li>
							<li><a href="/CIS/Course/CsCoreManager.do">核心課程管理</a></li>
							<li><a href="/CIS/Course/LiteracyType.do">通識科目分類管理</a></li>							
							<li><a href="/CIS/Course/DtimestampManager.do">節次時間管理</a></li>
							<li><a href="/csis/ReSetDtime">重新設定前學期課程</a></li>	
							<li><a href="/eis/sa/CalendarManager?sys=csis"> 各項日期管理</a></li>
						</ul>
					</li>
					<li class="divider"></li>
					<li class="nav-header"><i class="icon-search"></i> 查核與列印</li>
					<li><a href="/csis/ForbidScoreList">1/3缺課名單</a></li>				
					<li><a href="/CIS/Course/CheckOut.do">課務查核</a></li>
					<li><a href="/CIS/Registration/CheckCsGroup.do">學程資格審查</a></li>					
					<li><a href="/CIS/Course/SelectFilter.do">學生選課篩選</a></li>
					<li><a href="/CIS/Course/BatchProcess.do">批次作業</a></li>
					<li><a href="/CIS/Course/ReportPrintList.do">課務列印</a></li>
				</ul>
			</li>		    
			
			<!-- 人事 -->
			<li class="dropdown-submenu"><a href="#" class="dropdown-toggle" data-toggle="dropdown">人事管理系統</a>
		        <ul class="dropdown-menu">
		            <li><a href="/CIS/Personnel/HRmanager.do">人事基本資料管理</a></li>
		            <li><a href="/CIS/Personnel/HRChecker.do">人事基本資料查核</a></li>
		            <li><a href="/hris/UnitManager">單位管理</a></li>
		            <li class="dropdown-submenu"><a href="#">教師資料設定</a>
		                <ul class="dropdown-menu">
		                    <li><a href="/CIS/Personnel/SkillStatistics.do">教師專長統計表*</a></li>
		                    <li><a href="/CIS/Personnel/TechlimitManager.do">設定授課時數*</a></li>
		                    <li><a href="/hris/EmplTechlimit">設定教師各項時數</a></li>
		                    
		                    <li><a href="/CIS/Personnel/StayTimeSetup.do">設定留校時間*</a></li>
		                    <li><a href="/CIS/Personnel/StayTimeSetup.do">設定留校時間(產學減免)*</a></li>
		                </ul>
		            </li>                                                       
		            <li class="dropdown-submenu"><a href="#">差勤系統設定</a>
		                <ul class="dropdown-menu">
		                    <li><a href="/CIS/AMS/DocExamine.do">審查申請單</a></li>
		                    <li><a href="/CIS/AMS/DocExamine.do?force=1">審查申請單(強制)</a></li>
		                    <li><a href="/CIS/AMS/DocMeetingExamine.do">審查重要集會假單</a></li>
		                    <li class="divider"></li>
		                    <li><a href="/CIS/AMS/EmplAmsInfo.do"><i class="icon-search" style="margin-top: 3px;"></i> 查詢全校差勤記錄</a></li>
		                    <li><a href="/CIS/AMS/CheckOvertime.do">超時工作查核</a></li>
		                    <li><a href="/CIS/AMS/LeaveDocManager.do">審核者管理</a></li> 
		                    <li><a href="/CIS/AMS/ArtificialInput.do">人工補登作業*</a></li>
		                    <li><a href="/hris/WorkDateManager">人工補登作業</a></li>		                    
		                    <li class="divider"></li>
		                    <li><a href="/CIS/AMS/AmsMeeting.do">重要集會維護</a></li> 
		                    <li><a href="/CIS/AMS/MeetingAttend.do">集會出席狀況維護</a></li>
		                    <li class="divider"></li> 
		                    <li><a href="/CIS/AMS/ReportPrint.do"><i class="icon-print" style="margin-top: 3px;"></i>報表列印</a></li> 
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
		            <li class="divider"></li>
		            <li><a href="/CIS/Personnel/ReportPrint.do"><i class="icon-print" style="margin-top: 3px;"></i>報表列印*</a></li>
		            <li><a href="/hris/Print"><i class="icon-print" style="margin-top: 3px;"></i>報表列印</a></li>                          
		        </ul>
		    </li>
			
			<!-- 會計 -->
			<li class="dropdown-submenu"><a href="#" class="dropdown-toggle" data-toggle="dropdown">會計室</a>
		        <ul class="dropdown-menu">
		            <li class="divider"></li>
		            <li><a href="http://ap168.cust.edu.tw/salytax/salytaxframe.php"><i class="icon-tasks" style="margin-top: 3px;"></i> 所得稅資訊系統</a></li>
		            <li><a href="/CIS/FEE/ReportPrint.do"><i class="icon-print" style="margin-top: 3px;"></i> 報表列印*</a></li>     
		            <li><a href="/CIS/FEE/FeeCode.do">收費代碼表維護</a></li>
					<li><a href="/CIS/FEE/ClassFee.do">班級代辦費資料維護</a></li>
					<li><a href="/CIS/FEE/ClassFee4C.do">班級學雜費資料維護</a></li>
					<li><a href="/CIS/FEE/BankFeePay.do">虛擬帳號維護</a></li>
					<li><a href="/CIS/FEE/StdTransferAccount.do">學生轉帳帳號資料維護</a></li>
					<li><a href="/CIS/FEE/StdTransferAccountCheck.do">學生轉帳資料查核</a></li>
					<li><a href="/CIS/FEE/BankFeeRegister.do">銀行繳費資料更新註冊檔</a></li>
					<li><a href="/CIS/FEE/ReliefVulnerableAmount.do">更新註冊檔(減免弱勢)</a></li>
					<li><a href="/CIS/FEE/ReportPrint.do">報表列印</a></li>
					<li><a href="/CIS/FEE/ReportPrintFee.do">報表列印</a></li>
					<li><a href="/CIS/FEE/ReportPrint4C.do">報表列印</a></li>
					<li><a href="/sris/TuitionManager">匯出收費檔</a></li>
				</ul>
			</li>
		    
		    <!-- 系助 -->
		    <li class="dropdown-submenu"><a href="#" class="dropdown-toggle" data-toggle="dropdown">系務系統</a>
				<ul class="dropdown-menu">			
					<li class="divider"></li>
					<li><a href="/CIS/DeptAssistant/DeptCourseInfo.do">系所開課狀況</a></li>
					<li><a href="/CIS/DeptAssistant/StdSkill.do">學生證照管理</a></li>
					<li><a href="/CIS/Registration/CheckCsGroup.do">學程資格審查</a></li>
					<li><a href="http://ap168.cust.edu.tw/gradcheck/gradframe.php">畢業資格審查系統</a></li>
					<li class="divider"></li>
					<li class="dropdown-submenu"><a><i class="icon-book" style="margin-top: 5px;"></i> 課程規劃系統</a>
						<ul class="dropdown-menu">
							<li><a href="/CIS/Course/BatchStaticTableing.do">固定節次排課</a></li>
							<li><a href="/CIS/DepAssistant/DtimeReserveManager.do">課程規劃</a></li>
							<li><a href="/CIS/DepAssistant/DtimeManager.do">課程管理</a></li>
							<li><a href="/CIS/DepAssistant/DtimeReserveOption.do">時數規劃</a></li>
							<li><a href="/CIS/DepAssistant/Timetabling.do">課表排課管理</a></li>
							<li><a href="/CIS/DepAssistant/FestTimetabling.do">快速排課管理</a></li>
							<li><a href="/CIS/Course/GenTable.do">課表查詢列印</a></li>
							<li><a href="/CIS/Course/TeacherCourseSearch.do">教師課表查詢</a></li>
						</ul>
					</li>
					<li class="divider"></li>					
					<li><a href="/CIS/DeptAssistant/InvestigationSearch.do">應屆畢業生出路調查</a></li>
					<li><a href="/CIS/DeptAssistant/ReportPrint.do">報表列印</a></li>			
				</ul>
			</li>
			
			<!-- 語言 -->
			<li class="dropdown-submenu"><a href="#" class="dropdown-toggle" data-toggle="dropdown">語言中心</a>			
				<ul class="dropdown-menu">		
					<li><a href="/tis/ExamsManager"><i class="icon-cog" style="margin-top: 5px;"></i> 考試管理</a></li>
				</ul>
			</li>
			
			<!-- 出納 -->
			<li class="dropdown-submenu"><a href="#" class="dropdown-toggle" data-toggle="dropdown">出納組</a>
		        <ul class="dropdown-menu">
		            <li><a href="http://ap168.cust.edu.tw/salypay/salypayframe.php"><i class="icon-tasks" style="margin-top: 3px;"></i> 薪資管理系統</a></li>
            		<li><a href="http://ap168.cust.edu.tw/yearfund/yearfundframe.php"><i class="icon-tasks" style="margin-top: 3px;"></i> 公保年金系統</a></li>
            		<li><a href="/CIS/FEE/StdTransferAccount.do">學生轉帳帳號資料維護</a></li>
            		<li><a href="/CIS/FEE/StdTransferAccountCheck.do"><i class="icon-check" style="margin-top: 3px;"></i> 學生轉帳資料查核</a></li>
            		<li><a href="/CIS/FEE/ReportPrint4C.do"><i class="icon-print" style="margin-top: 3px;"></i> 報表列印</a></li>
		        </ul>
		    </li>
		    		
			<!-- 遠距 -->
		    <li class="dropdown-submenu"><a href="#" class="dropdown-toggle" data-toggle="dropdown">遠距教學設定</a>
		        <ul class="dropdown-menu">
		            <li><a href="/CIS/Course/OpenCourse.do"> 課程管理</a></li>
            		<li><a href="/sais/UploadElearningDilg"><i class="icon-download-alt" style="margin-top: 3px;"></i> 遠距教學曠缺轉檔</a></li>
            		<li><a href="/csis/EleResManager"><i class="icon-ok-circle" style="margin-top: 3px;"></i> 課程規劃審核</a></li>
            		
		        </ul>
		    </li>
		    
		    <!-- 課指 -->
		    <li class="dropdown-submenu"><a href="#" class="dropdown-toggle" data-toggle="dropdown">學雜代辦費系統</a>
				<ul class="dropdown-menu">			
					<li class="divider"></li>
					<li><a href="/CIS/FEE/FeeCode.do">收費代碼表維護</a></li>
					<li><a href="/CIS/FEE/ClassFee.do">班級代辦費資料維護</a></li>
					<li><a href="/CIS/FEE/ClassFee4C.do">班級學雜費資料維護</a></li>
					<li><a href="/CIS/FEE/BankFeePay.do">虛擬帳號維護</a></li>
					<li><a href="/CIS/FEE/StdTransferAccount.do">學生轉帳帳號資料維護</a></li>
					<li><a href="/CIS/FEE/StdTransferAccountCheck.do">學生轉帳資料查核</a></li>
					<li><a href="/CIS/FEE/BankFeeRegister.do">銀行繳費資料更新註冊檔</a></li>
					<li><a href="/CIS/FEE/ReliefVulnerableAmount.do">更新註冊檔(減免弱勢)</a></li>
					<li><a href="/CIS/FEE/ReportPrint.do">報表列印</a></li>
					<li><a href="/CIS/FEE/ReportPrintFee.do">報表列印</a></li>
					<li><a href="/CIS/FEE/ReportPrint4C.do">報表列印</a></li>
				</ul>
			</li>
			
		    <!-- 研究 -->
		    <li class="dropdown-submenu"><a href="#" class="dropdown-toggle" data-toggle="dropdown">研究成果資料維護</a>
				<ul class="dropdown-menu">			
					<li class="divider"></li>
					<li><a href="/CIS/Teacher/Rcact_Manager.do">1-7 參加學術活動資料維護</a></li>
					<li><a href="/CIS/Teacher/Rcproj_Manager.do">1-8 承接政府部門計劃與產學案</a></li>
					<li><a href="/CIS/Teacher/Rcjour_Manager.do">1-9 期刊論文發表資料</a></li>
					<li><a href="/CIS/Teacher/Rcconf_Manager.do">1-10 研討會論文發表資料</a></li>
					<li><a href="/CIS/Teacher/Rcbook_Manager.do">1-11專書(篇章)資料表</a></li>
					<li><a href="/CIS/Teacher/Rcpet_Manager.do">1-12 專利資料表</a></li>
					<li><a href="/CIS/Teacher/Rchono_Manager.do">1-13 獲獎與榮譽資料</a></li>
					<li><a href="/CIS/Teacher/Article_Statistics.do">研究成果資料統計</a></li>		
				</ul>
			</li>
			
			<!-- 綜合 -->
    		<li class="dropdown-submenu"><a href="#" class="dropdown-toggle" data-toggle="dropdown">綜合業務組</a>
        		<ul class="dropdown-menu">
            		<li class="divider"></li>						
					<li class="nav-header"><i class="icon-th"></i> 招生資料維護</li>
					<li><a href="/CIS/Regstration/Recruit/Config/EditStmdSchl.do">學生畢業學校維護</a></li>
					<li><a href="/CIS/Regstration/Recruit/Config/SchoolManager.do">學校資料管理</a></li>
					<li><a href="/CIS/Regstration/Recruit/Config/ItemsManager.do">活動項目管理</a></li>  
					<li><a href="/CIS/Regstration/Recruit/Config/AllianceSet.do">聯招報名人數維護</a></li>
					<li><a href="/CIS/Regstration/Recruit/Config/DraftManager.do">各校系錄取率管理</a></li>
					<li><a href="/CIS/Regstration/Recruit/Config/MailListManager.do">電子郵件名單管理</a></li>                                 
        		</ul>
    		</li>
    		
    		<!-- 推廣 -->
    		<li class="dropdown-submenu"><a href="#" class="dropdown-toggle" data-toggle="dropdown">推廣教育中心</a>
        		<ul class="dropdown-menu">				
					<li><a href="/CIS/Registration/StudentManager.do">學生基本資料管理</a></li>
					<li><a href="/tis/AddPubLeave">學生公假管理</a></li>
					<li><a href="/CIS/Score/ReportPrint.do">報表列印</a></li>                                  
        		</ul>
    		</li> 

			<!-- 秘書室 -->
    		<li class="dropdown-submenu"><a href="#" class="dropdown-toggle" data-toggle="dropdown">秘書室 <b class="caret"></b></a>
        		<ul class="dropdown-menu">
	   	           <li><a href="/CIS/Calendar/TxtGroupPubManager.do">共用群組管理</a></li>
	   	           <li><a href="/eis/sa/CalendarManager?sys=sec"> 各項日期管理</a></li>
     		       <li><a href="/CIS//Secretary/ReportPrint.do">報表列印</a></li>
     		       <li><a href="/CIS/Secretary/File_Manager.do">會議/法規文件</a></li>
     		       <li><a href="/sais/CallStatusView">點名狀況查詢</a></li> 
     		       <li><a href="/sris/CountGstmd">離校生輔導記錄查詢</a></li>                                        
		        </ul>
    		</li>  

	    </ul>
    </li>          
</ul>