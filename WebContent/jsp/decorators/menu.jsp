<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div id="menu" class="navbar navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container-fluid">
			<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
			<span class="icon-bar"></span>
			<span class="icon-bar"></span> 
			<span class="icon-bar"> </span></a>
			<a class="brand" href="/eis/Calendar">中華科技大學</a>			
			<div class="nav-collapse collapse navbar-inverse-collapse">	
			<!-- 未登入 -->
			<c:if test="${empty cookie['unit']}">
				<ul class="nav">									
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">公用程式 <b class="caret"></b></a>
						<ul class="dropdown-menu">							
							<li class="dropdown-submenu"><a><i class="icon-search" style="margin-top: 5px;"></i> 資料查詢</a>
								<ul class="dropdown-menu">
									<!--li><a href="/CIS/Personnel/ListStudent.do">班級查詢</a></li-->
									<!--li><a href="/CIS/Personnel/ListCourse.do">課程查詢*</a></li-->
									<li><a href="/pis/PubCsSearch">課程查詢</a></li>
									<li><a href="/pis/PubCsSearch">教師留校時間查詢</a></li>
									<!--li><a href="/CIS/Teacher/TeacherStayTimeSearch.do">教師留校時間查詢</a></li-->
								</ul>
							</li>							
							<!--li><a onClick="getHelpPage();"><i class="icon-question-sign" style="margin-top: 5px;"></i> 系統說明</a></li-->							
						</ul>
					</li>
					
				</ul>
			</c:if>
						
			<c:if test="${fn:indexOf(cookie['unit'].value, 'A')>-1}">
			<!-- 教職員公用 -->
			
				<ul class="nav">
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">教職員公用系統 <b class="caret"></b></a>
						<ul class="dropdown-menu">	
												
							<li class="dropdown-submenu"><a><i class="icon-calendar" style="margin-top: 5px;"></i> 行事曆</a>
								<ul class="dropdown-menu">
									<li><a href="/eis/Calendar">個人行事曆</a></li>
									<li><a href="/CIS/Calendar/PriManager.do">會議行事曆</a></li>
									<li><a href="/CIS/Calendar/TxtGroupManager.do">群組管理</a></li>
								</ul>
							</li>
																		
							<li class="dropdown-submenu"><a><i class="icon-time" style="margin-top: 3px;"></i> 差勤系統</a>
								<ul class="dropdown-menu">
									<li><a href="/CIS/AMS/AmsInfo.do">差勤紀錄</a></li>									
									<li><a href="/CIS/AMS/DocManager.do?docType=1">請假單管理</a></li>
									<li><a href="/eis/ExcuseManager">加班與補休申請</a></li>
									<li><a href="/CIS/AMS/DocManager.do?docType=4">銷假單管理</a></li>
									<li><a href="/CIS/AMS/MeetingAskLeave.do">重要集會請假單</a></li>
								</ul>
							</li>							
							<li><a href="http://ap168.cust.edu.tw/salypay/salysearch.php"><i class="icon-list-alt" style="margin-top: 3px;"></i> 薪給資料查詢</a></li>
							<li><a href="/CIS/Personnel/MailManager.do"><i class="icon-envelope" style="margin-top: 3px;"></i> 群組郵件</a></li>
							<li class="divider"></li>
							<li class="dropdown-submenu"><a><i class="icon-search" style="margin-top: 3px;"></i> 公用查詢</a>
								<ul class="dropdown-menu">									
									<li><a href="/CIS/Teacher/StdinfoSh.do">學生查詢</a></li>
									<li><a href="/CIS/Personnel/ListStudent.do">班級查詢</a></li>
									<li><a href="/pis/PubCsSearch">課程查詢</a></li>
									<li><a href="/pis/PubCsSearch">教師留校時間查詢</a></li>									
									<!-- li><a href="/CIS/Teacher/TeacherStayTimeSearch.do">教師留校時間查詢</a></li-->
									<!-- li><a onClick="getHelpPage();"><i class="icon-question-sign" style="margin-top: 5px;"></i> 系統說明</a></li-->	
									<!--li class="dropdown-submenu"><a href="/Regstration/Recruit/Directory.do"><i class="icon-briefcase" style="margin-top: 3px;"></i> 招生規劃系統</a>							
										<ul class="dropdown-menu">
											<li><a href="/CIS/Regstration/Recruit/ScheduleManager.do">招生工作日程管理</a></li>	
											<li><a href="/CIS/Regstration/Recruit/RecruitQuery.do">各系生源資料查詢</a></li>
											<li><a href="/CIS/Regstration/Recruit/ScheduleCheck.do">招生工作管考</a></li>							
										</ul>
									</li-->
								</ul>					
							</li>
							<li class="dropdown-submenu"><a href="/CIS/EmpTraining/Training.do"><i class="icon-file" style="margin-top: 3px;"></i> 表單與文件下載</a>
								<ul class="dropdown-menu">
									<li><a href="/CIS/Secretary/File_Download.do">會議/法規-資料下載</a></li>
									<li><a href="/CIS/EmpEvaluate/EmpEvaluate4E.do">教職員工考核</a></li>
									<li><a href="/CIS/pages/downloads/EmpTrainingForm.pdf#pagemode=none&zoom=80">職員進修申請書</a></li>										
									<li><a href="/CIS/pages/downloads/EmpTrainingAgressmentForm.pdf#pagemode=none&zoom=80">職員進修協議書</a></li>													
									<li><a href="/CIS/pages/downloads/EmpRatingScale.pdf#pagemode=none&zoom=80">職員工績效評量表</a></li>																	
								</ul>							
							</li>
							<li><a href="http://alumni.cust.edu.tw/info/index.php">資訊能力報名系統</a></li>
							
							<li class="divider"></li>
							<li class="dropdown-submenu"><a><i class="icon-pencil" style="margin-top: 3px;"></i> 學生輔導</a>
								<ul class="dropdown-menu">
									<li><a href="http://ap168.cust.edu.tw/bank/processframe.php">註冊繳費系統</a></li>
									<li><a href="http://ap168.cust.edu.tw/HighCare/HighCare.php">高關懷學生輔導系統</a></li>							
									<li><a href="http://ap168.cust.edu.tw/physics/phyframe.php">運動績優輔導系統</a></li>
								</ul>
							</li>														
							<li><a href="http://ap168.cust.edu.tw/Performance/Performance.php">教學績效系統</a></li>
							<li><a href="/CIS/LaboratoryManage.do">實驗室管理系統</a></li>							
							<li><a href="/tis/AddPubLeave">學生公假管理</a></li>
							<li><a href="http://ap168.cust.edu.tw/laborsaly/laborsalyframe.php">工讀生勞健保與預算系統</a></li>				
							
							<li class="dropdown-submenu"><a><i class="icon-folder-open" style="margin-top: 3px;"></i> 研究成果資料輸入</a>
								<ul class="dropdown-menu">
									<li><a href="/CIS/Teacher/Rcact.do">1-7 參加學術活動資料輸入</a></li>
									<li><a href="/CIS/Teacher/Rcproj.do">1-8 承接政府部門計劃與產學案資料輸入</a></li>
									<li><a href="/CIS/Teacher/Rcjour.do">1-9 期刊論文發表資料輸入</a></li>
									<li><a href="/CIS/Teacher/Rcconf.do">1-10 研討會論文發表資料輸入</a></li>
									<li><a href="/CIS/Teacher/Rcbook.do">1-11 專書(含篇章)資料輸入</a></li>
									<li><a href="/CIS/Teacher/Rcpet.do">1-12 專利資料輸入</a></li>
									<li><a href="/CIS/Teacher/Rchono.do">1-13 獲獎與榮譽資料輸入</a></li>
								</ul>
							</li>
							
							<li class="dropdown-submenu"><a><i class="icon-folder-open" style="margin-top: 3px;"></i> 研究成果資料查詢</a>
								<ul class="dropdown-menu">
									<li><a href="/CIS/Teacher/Rcact_Query.do">1-7 參加學術活動資料查詢</a></li>
									<li><a href="/CIS/Teacher/Rcproj_Query.do">1-8 承接政府部門計劃與產學案查詢</a></li>
									<li><a href="/CIS/Teacher/Rcjour_Query.do">1-9 期刊論文發表資料查詢</a></li>
									<li><a href="/CIS/Teacher/Rcconf_Query.do">1-10 研討會論文發表資料查詢</a></li>
									<li><a href="/CIS/Teacher/Rcbook_Query.do">1-11專書(篇章)資料表查詢</a></li>
									<li><a href="/CIS/Teacher/Rcpet_Query.do">1-12 專利資料表查詢</a></li>
									<li><a href="/CIS/Teacher/Rchono_Query.do">1-13 獲獎與榮譽資料查詢</a></li>
								</ul>
							</li>
							
							<li class="divider"></li>													
							<li class="dropdown-submenu"><a href="/CIS/Individual/Directory.do"><i class="icon-user" style="margin-top: 3px;"></i> 個人資料</a>							
								<ul class="dropdown-menu">
									<li><a href="/eis/AccountManager">變更密碼</a></li>	
									<li><a href="/CIS/Teacher/PhoneAndAddress.do">檢視聯絡資料</a></li>
									<li><a href="/CIS/Personnel/AssessPaper.do">服務滿意度調查表列印</a></li>							
								</ul>
							</li>
							<li><a href="/CIS/Student/ClinicService/Appointment.do">健康中心預約掛號</a></li>	
							<li><a href="/CIS/Personnel/AssessPaperReply.do"> 服務滿意度調查</a></li>
							<li><a href="/CIS/OpinionSuggestion.do">意見反映</a></li>								
																	
						</ul>
					</li>					
				</ul>
				</c:if>		
				
				<!-- 圖表資訊 -->
				<%@ include file="/jsp/decorators/menu/eis.jsp"%>
				
				<c:if test="${fn:indexOf(cookie['unit'].value, 'T')>-1||fn:indexOf(cookie['unit'].value, '117')>-1}">
				<!-- 教師系統 -->
				<ul class="nav">
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">教師系統 <b class="caret"></b></a>
						<ul class="dropdown-menu">							
							<li><a href="/tis/RollCall"><i class="icon-check" style="margin-top: 3px;"></i> 線上點名</a></li>							
							<li class="dropdown"><a href="/tis/ScoreManager"><i class="icon-edit" style="margin-top: 3px;"></i> 成績輸入與列印</a></li>
							<li class="dropdown"><a href="/tis/CareForStudents"><i class="icon-globe" style="margin-top: 3px;"></i> 學生動態</a></li>
							<li class="divider"></li>
							<li class="dropdown"><a href="/CIS/Teacher/SummerRat.do"><i class="icon-edit" style="margin-top: 3px;"></i> 成績輸入(暑修課程)</a></li>
							<li><a href="/CIS/Teacher/CourseInfo.do"><i class="icon-book" style="margin-top: 5px;"></i> 課程簡介管理</a></li>
							<li><a href="/tis/ElearningReserve"><i class="icon-film" style="margin-top: 3px;"></i> 數位教材規劃</a></li>
							<li class="dropdown-submenu"><a><i class="icon-share" style="margin-top: 3px;"></i> 導師相關作業</a>
								<ul class="dropdown-menu">
									<li><a href="/tis/DilgView">班級曠缺紀錄</a></li>
									<li><a href="/tis/OneThirdView">課程曠缺預警</a></li>
									<li><a href="/tis/DilgApp">學生假單審核</a></li>
									<li><a href="/sais/ConductCredits">操行成績及評語管理</a></li>
									<li><a href="/CIS/Teacher/Tutor/LifeCounseling.do">生活輔導時間</a></li>
									<li><a href="/CIS/Teacher/Tutor/CounselingT.do">職涯與學習輔導記錄</a></li>							
									<li><a href="/CIS/Teacher/Tutor/GraduateInvestigation.do">應屆畢業生調查表</a></li>									
									<li><a href="/CIS/Teacher/ClassCadre.do">班級幹部聯繫資料</a></li>
									<li><a href="/CIS/StudAffair/CounselingReport.do?idenType=T">輔導記錄查詢列印</a></li>
								</ul>
							</li>
							
							<li class="dropdown-submenu"><a><i class="icon-folder-open" style="margin-top: 3px;"></i> 研究成果資料輸入</a>
								<ul class="dropdown-menu">
									<li><a href="/CIS/Teacher/Rcact.do">1-7 參加學術活動資料輸入</a></li>
									<li><a href="/CIS/Teacher/Rcproj.do">1-8 承接政府部門計劃與產學案資料輸入</a></li>
									<li><a href="/CIS/Teacher/Rcjour.do">1-9 期刊論文發表資料輸入</a></li>
									<li><a href="/CIS/Teacher/Rcconf.do">1-10 研討會論文發表資料輸入</a></li>
									<li><a href="/CIS/Teacher/Rcbook.do">1-11 專書(含篇章)資料輸入</a></li>
									<li><a href="/CIS/Teacher/Rcpet.do">1-12 專利資料輸入</a></li>
									<li><a href="/CIS/Teacher/Rchono.do">1-13 獲獎與榮譽資料輸入</a></li>
								</ul>
							</li>
							
							<li class="dropdown-submenu"><a><i class="icon-folder-open" style="margin-top: 3px;"></i> 研究成果資料查詢</a>
								<ul class="dropdown-menu">
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
							<li><a href="/tis/CoanswView">教學評量</a></li>
							<li><a href="/tis/AddPubLeave">學生公假管理</a></li>
							<!-- li><a href="/CIS/Teacher/StayTimeInput.do">個人留校時間設定</a></li-->							
							<li><a href="/tis/StayTimeManager">留校時間管理</a></li>
							<li><a href="/CIS/Teacher/CounselingL.do">學生學習輔導記錄</a></li>				
							
							<li class="divider"></li>												
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
				</ul>
				</c:if>
				
				<c:if test="${fn:indexOf(cookie['unit'].value, 'S')>-1||
				fn:indexOf(cookie['unit'].value, '121')>-1||
				fn:indexOf(cookie['unit'].value, '130')>-1}">
				<!-- 系務 -->
				<%@ include file="/jsp/decorators/menu/as.jsp"%>
				</c:if>
				
				<c:if test="${fn:indexOf(cookie['unit'].value, '116')>-1}">
				<!-- 會計 -->
				<%@ include file="/jsp/decorators/menu/acc.jsp"%>
				</c:if>				
				
				<c:if test="${fn:indexOf(cookie['unit'].value, '139')>-1||
				fn:indexOf(cookie['unit'].value, '148')>-1||
				fn:indexOf(cookie['unit'].value, '150')>-1}">
				<!-- 出納 -->
				<%@ include file="/jsp/decorators/menu/cash.jsp"%>
				</c:if>
				
				<c:if test="${fn:indexOf(cookie['unit'].value, '128')>-1||
				fn:indexOf(cookie['unit'].value, '128')>-1||
				fn:indexOf(cookie['unit'].value, '146')>-1||
				fn:indexOf(cookie['unit'].value, '154')>-1||
				fn:indexOf(cookie['unit'].value, '157')>-1||
				fn:indexOf(cookie['unit'].value, '160')>-1}">
				<!-- 課程 -->
				<%@ include file="/jsp/decorators/menu/cs.jsp"%>
				</c:if>	
				
				<c:if test="${fn:indexOf(cookie['unit'].value, '131')>-1}">
				<!-- 遠距 -->
				<%@ include file="/jsp/decorators/menu/eln.jsp"%>
				</c:if>
				
				<c:if test="${fn:indexOf(cookie['unit'].value, '133')>-1||
				fn:indexOf(cookie['unit'].value, '145')>-1||
				fn:indexOf(cookie['unit'].value, '147')>-1||
				fn:indexOf(cookie['unit'].value, '155')>-1||
				fn:indexOf(cookie['unit'].value, '158')>-1||
				fn:indexOf(cookie['unit'].value, '161')>-1}">
				<!-- 學雜 -->
				<%@ include file="/jsp/decorators/menu/fee.jsp"%>
				</c:if>
				
				<c:if test="${fn:indexOf(cookie['unit'].value, '115')>-1}">
				<!-- 人事 -->
				<%@ include file="/jsp/decorators/menu/hr.jsp"%>
				</c:if>
				
				<c:if test="${fn:indexOf(cookie['unit'].value, '142')>-1||
				fn:indexOf(cookie['unit'].value, '143')>-1||
				fn:indexOf(cookie['unit'].value, '144')>-1}">
				<!-- 研發 -->
				<%@ include file="/jsp/decorators/menu/rc.jsp"%>
				</c:if>
				
				<c:if test="${fn:indexOf(cookie['unit'].value, '127')>-1||
				fn:indexOf(cookie['unit'].value, '146')>-1||
				fn:indexOf(cookie['unit'].value, '154')>-1||
				fn:indexOf(cookie['unit'].value, '157')>-1||
				fn:indexOf(cookie['unit'].value, '160')>-1}">
				<!-- 註冊成績 -->
				<%@ include file="/jsp/decorators/menu/reg.jsp"%>
				</c:if>
				
				<c:if test="${fn:indexOf(cookie['unit'].value, '134')>-1||
				fn:indexOf(cookie['unit'].value, '106')>-1||
				fn:indexOf(cookie['unit'].value, '147')>-1||
				fn:indexOf(cookie['unit'].value, '148')>-1||
				fn:indexOf(cookie['unit'].value, '155')>-1||
				fn:indexOf(cookie['unit'].value, '158')>-1||
				fn:indexOf(cookie['unit'].value, '161')>-1||
				fn:indexOf(cookie['unit'].value, '117')>-1}">
				<!-- 學務 -->
				<%@ include file="/jsp/decorators/menu/saf.jsp"%>
				</c:if>
				
				<c:if test="${fn:indexOf(cookie['unit'].value, '132')>-1}">
				<!-- 綜合 -->
				<%@ include file="/jsp/decorators/menu/ga.jsp"%>
				</c:if>
				
				<c:if test="${fn:indexOf(cookie['unit'].value, '172')>-1}">
				<!-- 推廣 -->
				<%@ include file="/jsp/decorators/menu/eec.jsp"%>
				</c:if>
				
				<c:if test="${fn:indexOf(cookie['unit'].value, '114')>-1}">
				<!-- 秘書室 -->
				<%@ include file="/jsp/decorators/menu/sec.jsp"%>
				</c:if>
				
				<c:if test="${fn:indexOf(cookie['unit'].value, '101')>=0||
				fn:indexOf(cookie['unit'].value, '102')>=0||
				fn:indexOf(cookie['unit'].value, '103')>=0}">
				<!-- 系統 -->
				<%@ include file="/jsp/decorators/menu/sa.jsp"%>
				</c:if>
				
				<c:if test="${!empty cookie['unit']}">
				<ul id="xLogout" class="nav pull-right">					
					<li class="divider-vertical"></li>			
					<li><a href="/eis/Logout">登出</a></li>
				</ul>
				</c:if>
								
			</div>	
			
		</div>
	</div>
</div>
<script>
function getHelpPage(){
	$.blockUI({ 
		
	    message: '<Iframe style="border-radius:15px;" id="coansw" src="/pis/SysDoc"; width="100%" height="100%" frameborder="0"></iframe>', 
	    css: { 
	    	width: '60%', 
	        height:'80%',
	        top: '10%', 
	        left: '20%', 
	        right: '20%', 
	        border: 'none',
	        'border-radius':'15px',
			'box-shadow': '10px 10px 20px #000'
	    }
	});
	$('.blockOverlay').attr('title','Click to unblock').click($.unblockUI);
}
</script>