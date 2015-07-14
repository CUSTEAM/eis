<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
  	<head>
	<script src="inc/js/plugin/scheduler/dhtmlxscheduler.js"></script>
	<script src="inc/js/plugin/scheduler/ext/dhtmlxscheduler_recurring.js"></script>
	<script src="inc/js/plugin/scheduler/ext/dhtmlxscheduler_editors.js"></script>
	<script src="inc/js/plugin/scheduler/ext/dhtmlxscheduler_minical.js"></script>
	<script src="inc/js/plugin/scheduler/ext/dhtmlxscheduler_dhx_terrace.js"></script>
	<script src="inc/js/plugin/scheduler/ext/dhtmlxscheduler_limit.js"></script>	
	<script src="inc/js/plugin/scheduler/access.js"></script>	
	<link rel="stylesheet" href="inc/js/plugin/scheduler/dhtmlxscheduler_dhx_terrace.css">		
	
	<style type="text/css" media="screen">
	html, body {margin:0px !important; padding:0px !important; height: 100%; overflow: hidden;}	
	.dhx_cal_event.past_event div{background-color:#AAAAAA !important;color:#FFFFFF !important;}		
	.dhx_cal_event_line.past_event{background-color:#AAAAAA !important;color:white !important;}		
	.dhx_cal_event_clear.past_event{color:#AAAAAA !important;}
	.dhx_title{height:18px !important;}	
	@media screen and (min-width:840px){.dhx_now_time{left:-30px !important;}}
	</style>
	
	<script>
	$.ajaxSetup ({ 
		cache: false 
	});
	
	$.ajax({
		  url: "initCheck",
		  type: "GET",
		  dataType: "json",
		  success: function(data) {			
			if(data.aq<3){
				$.blockUI({ 
		            message: '<Iframe style="border-radius:15px;" id="aqansw" src="Aqansw"; width="100%" height="100%" frameborder="0"></iframe>', 
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
			}
		  },
		  
		  error: function() {
		    alert("ERROR!!!");
		  }
		});
	</script>
	<script type="text/javascript" charset="utf-8">
		function init() {
			scheduler.config.multi_day = true;
			scheduler.config.fix_tab_position = false;
			scheduler.config.minicalendar.mark_events = false;
			scheduler.config.full_day = true;
			scheduler.config.xml_date = "%Y-%m-%d %H:%i";
			scheduler.init('myCalendar', new Date(), "week");			
			scheduler.config.cascade_event_display = true;			
			scheduler.attachEvent("onEventAdded", addEvent);
        	scheduler.attachEvent("onEventChanged", changeEvent);
        	scheduler.attachEvent("onEventDeleted", deleteEvent);        	
        	scheduler.config.prevent_cache = true;    		
    		scheduler.config.details_on_create=true;
    		scheduler.config.details_on_dblclick=true;	
    		
			//工具欄位			
			scheduler.config.lightbox.sections=[	
      			{name:"description", height:43, map_to:"text", type:"textarea" , focus:true},
      			{name:"detail", height:130, type:"textarea", map_to:"details" },
      			{name:"members", height:86, type:"textarea", map_to:"members" },
      			//{name:"recurring", type: "recurring", map_to: "rec_type", button: "recurring"},    			
      			{name:"time", height:72, type:"time", map_to:"auto"}
      		];			
      		scheduler.locale.labels.section_detail="細節";
      		scheduler.locale.labels.section_members="參與者";
      		scheduler.locale.labels.reccuring="週期";			
			scheduler.load("getMySchedule?random="+Math.floor(Math.random()*999));	
			
			//過期過濾器
			scheduler.templates.event_class=function(start,end,event){
	 		   if (start < (new Date())){
	 				//若過期反灰
	        		return "past_event"; 
	 		   }	 		   
			};
			
			//假日過濾器
			scheduler.filter_day = function(start,end,event){
				if (start < (new Date())){
	 				//若過期反灰
	        		return true; 
	 		   }
			}
			
			//過濾器
			scheduler.templates.event_text=function(start, end, event){
				if(event.status_no=='1'){
					return "<a href='/tis/RollCall'><font color='#FFFFFF'>"+event.text+"<br>點名 </font></a>-<a href='/tis/ScoreManager'><font color='#FFFFFF'>評分</font></a>";
				}else{
					return event.text;
				}
	    	};			
			
			//排課不允許拖
			scheduler.attachEvent("onBeforeDrag", function (event_id, mode, native_event_object){
				if(scheduler.getEvent(event_id)!=null&&scheduler.getEvent(event_id).status_no=='1'){
					return false;
				}				     
				return true;
			});
			//排課不允許按
			scheduler.attachEvent("onClick", function (event_id, mode, native_event_object){				
				if(scheduler.getEvent(event_id)!=null&&scheduler.getEvent(event_id).status_no=='1'){
					return false;
				}				     
				return true;
			});
			//排課不允許編輯
			scheduler.attachEvent("onDblClick", function (event_id, native_event_object){
				if(scheduler.getEvent(event_id)!=null&&scheduler.getEvent(event_id).status_no=='1'){
					return false;
				}				     
				return true;
			 });
			
			//scheduler.locale.labels.workweek_tab = "課表"
			
		}		
	</script>
</head>
<body onload="init();">
<div id="myCalendar" class="dhx_cal_container" style='width:100%; height:100%;'>
	<div class="dhx_cal_navline">
		<div class="dhx_cal_prev_button">&nbsp;</div>
		<div class="dhx_cal_next_button">&nbsp;</div>
		<div class="dhx_cal_today_button"></div>
		<div class="dhx_cal_date"></div>
		<div class="dhx_cal_tab dhx_cal_tab_first" name="day_tab" style="left:14px;"></div>
		<div class="dhx_cal_tab" name="week_tab" style="left:75px;"></div>
		<div class="dhx_cal_tab dhx_cal_tab_last" name="month_tab" style="left:136px;"></div>
		<div class="dhx_cal_tab" style="border-radius:5px; left:210px;"><a href="/csis/TimeTable?nonStay=1&emplOid=${emplOid}" style="color:#777777;">課表</a></div>
		<div class="dhx_cal_tab" style="border-radius:5px; left:280px;"><a href="/csis/TimeTable?emplOid=${emplOid}" style="color:#777777;">留校時間</a></div>	
	</div>	
	<div class="dhx_cal_header"></div>
	<div class="dhx_cal_data"></div>
</div>
<!-- div style="position:absolute; bottom:20px; right:14px; padding:5px; color:#444444;">
	<div style="float:left; backGround:#444444; width:16px; height:16px; margin:5px;"></div>
	<div style="float:left; margin:2px;">循環</div>
	<div style="float:left; backGround:#AAAAAA; width:16px; height:16px; margin:5px;"></div>
	<div style="float:left; margin:2px;">過期</div>
	<div style="float:left; backGround:#FFAD46; width:16px; height:16px; margin:5px;"></div>
	<div style="float:left; margin:2px;">受邀</div>
	<div style="float:left; backGround:#5484ED; width:16px; height:16px; margin:5px;"></div>
	<div style="float:left; margin:2px;">個人</div>
</div>

<div style="position:absolute; bottom:20px; left:50px; padding:5px; color:#444444;">	
	<img src="/CIS/pages/images/icon_info.gif"/>滑鼠點擊時間欄點2下，或滑鼠拖拉時間欄，即可編輯
</div-->
</body>
</html>