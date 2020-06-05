<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>學生查詢</title>
<script src="/eis/inc/js/plugin/bootstrap-tooltip.js"></script>
<script src="/eis/inc/js/plugin/jquery-ui.js"></script>
<script src="/eis/inc/js/plugin/jquery-ui-timepicker-addon.js"></script>
<link href="/eis/inc/css/jquery-ui.css" rel="stylesheet"/>
<script>
$(document).ready(function() {	
	
	$(".grad").hide("slow");
	$(".filter").hide("slow");
	$('.help').popover("show");
	setTimeout(function() {
		$('.help').popover("hide");
	}, 0);
	
	$(".fall").click(function(){
		var checkboxes = $(this).closest('form').find('input[name="filter"]:checkbox');
		if($(this).is(':checked')) {
			checkboxes.prop('checked', 'checked');
		} else {
			checkboxes.removeAttr('checked');
		}		
	});
	
	$("#frev").click(function(){		
		var checkboxes = $(this).closest('form').find('input[name="filter"]:checkbox');
		for(i=0; i<checkboxes.length; i++){
			if($(checkboxes[i]).is(':checked')) {
				$(checkboxes[i]).removeAttr('checked');	
			} else {
				$(checkboxes[i]).prop('checked', 'checked');
			}
		}				
	});
	
	$("#opRes").click(function(){
		
		if($("#opRes").prop("checked")) {
			$(".filter").show("slow");
		}else{
			$(".filter").hide("slow");
		}
		
	});
	
	
	
	$("#type" ).change(function() {
		if($("#type").val()=="Gstmd"){
			$(".tcls" ).hide("slow");
			$(".grad" ).show("slow");			
		}else{
			$(".grad" ).hide("slow");
			$(".tcls" ).show("slow");
		}
		  
	});	
	
	$("select[name='week'], select[name='beginCls'], select[name='endCls']" ).change(function() {
		if($("#week").val()+$("#beginCls").val()+$("#endCls").val()!=""){
			$(".grad, .gtype, .tname, .tsex, .tide, .tadd, .tsch, .tsch, .tstds" ).hide("slow");
		}else{
			$(".grad, .gtype, .tname, .tsex, .tide, .tadd, .tsch, .tsch, .tstds" ).show("slow");
		}
		  
	});	
	
	$("#stds").keyup(function() {		
		if(this.value!=""){
			$(".grad, .tcls, .tsel,.tname, .tsex, .tide, .tadd, .tsch, .tsch" ).hide("slow");
		}else{
			$(".grad, .tcls, .tsel,.tname, .tsex, .tide, .tadd, .tsch, .tsch" ).show("slow");
		}
	});
		
	$("#stds").click(function(){
		if(this.style.width=='900px'){
			this.style.width='207px',this.style.height='20px';
		}else{
			this.style.width='900px',this.style.height='500px';
		}
		
	});
});
</script>
<style>
.material-switch > input[type="checkbox"] {
    display: none;   
}

.material-switch > label {
    cursor: pointer;
    height: 0px;
    position: relative; 
    width: 40px;  
}

.material-switch > label::before {
    background: rgb(0, 0, 0);
    box-shadow: inset 0px 0px 10px rgba(0, 0, 0, 0.5);
    border-radius: 8px;
    content: '';
    height: 16px;
    margin-top: -8px;
    position:absolute;
    opacity: 0.3;
    transition: all 0.4s ease-in-out;
    width: 40px;
}
.material-switch > label::after {
    background: rgb(255, 255, 255);
    border-radius: 16px;
    box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.3);
    content: '';
    height: 24px;
    left: -4px;
    margin-top: -8px;
    position: absolute;
    top: -4px;
    transition: all 0.3s ease-in-out;
    width: 24px;
}
.material-switch > input[type="checkbox"]:checked + label::before {
    background: inherit;
    opacity: 0.5;
}
.material-switch > input[type="checkbox"]:checked + label::after {
    background: inherit;
    left: 20px;
}
</style>
</head>
<body>
<div class="bs-callout bs-callout-warning" id="callout-helper-pull-navbar">
學生查詢
</div>
<form action="StdSearch" method="post" class="form-inline" enctype="multipart/form-data">

<div class="panel panel-primary">
<div class="panel-heading">查詢條件</div>
<!--div class="panel-body">
<p>...</p>
</div-->
<table class="table">
	<tr class="gtype">
		<td>
		<div class="input-group">
		<span class="input-group-addon">目標</span>
		<select class="form-control" name="type" id="type">
			<option value="stmd">在校生</option>
			<option value="Gstmd">離校生</option>
		</select>
		</div>
		</td>
	</tr>
	<tr class="tsel">
		<td>
		<%@ include file="/inc/jsp-kit/classSelectorFullCampus.jsp"%>
		
		</td>
	</tr>
	
	<tr class="tname">
		<td>
		<div class="input-group">
			<span class="input-group-addon">學號或姓名</span>
			<input class="form-control" class="span4" onClick="$('#stdName').val(''), $('#stdNo').val('');" autocomplete="off"  class="form-control" type="text" id="stdName" value="${stdName}" name="stdName"
			 data-provide="typeahead" onClick="addStd()" placeholder="學號、姓名或身分證字號片段" />
			 
		</div>	
		<div rel="popover" title="說明" data-content="以姓名片段查詢如:「陳怡君」,「陳」,「陳怡」,「怡君」,「陳_君」,中間字不詳請輸入下底線「_」" data-placement="right" class="help btn btn-warning">?</div>	
		</td>
	</tr>
	
	<tr class="tsex">
		<td>
		<div class="input-group">
			<span class="input-group-addon">性別</span>
			<select class="form-control" name="sex">
				<option value="">全部</option>
				<option value="1">男</option>
				<option value="2">女</option>
			</select>
		</div>
		
		<div class="input-group">
			<span class="input-group-addon">身份別</span>
			<select class="form-control" name="ident">
				<option value="">全部</option>
				<c:forEach items="${CODE_STMD_IDENT}" var="i">
				<option value="${i.id}">${i.name}</option>
				</c:forEach>
			</select>
		</div>
		
		<div class="input-group">
			<span class="input-group-addon">畢業班</span>
			<select class="form-control" name=graduate>
				<option value="">不限畢業資格</option>
				<option value="1">應屆畢業班</option>
				<option value="0">非畢業班</option>
			</select>
			 
		</div>
		</td>
	</tr>
	<tr class="tide">
		<td>		
		<div class="input-group">
			<span class="input-group-addon">生日範圍</span>
			<input class="form-control date" type="text" placeholder="點一下輸入日期" name="beginDate"/>
		</div>
		
		<div class="input-group">
			<span class="input-group-addon">至</span>
			<input class="form-control date" type="text" id="endDate" placeholder="點一下輸入日期" name="endDate"/>
		</div>
		<div rel="popover" title="說明" data-content="輸入開始日期則回傳自開始日期至今,輸入結束日期則回傳至結束日期,兩欄均輸入則回傳範圍內的學生名單" data-placement="right" class="help btn btn-warning">?</div>			
		
		</td>
	</tr>
	<tr class="grad">
		<td>
		<div class="input-group">
			<span class="input-group-addon">學籍狀態</span>
			<select class="form-control" name="occur_status">
				<option value="">所有狀態</option>
				<c:forEach items="${CODE_STMD_STATUS}" var="c">
				<option value="${c.id}">${c.name}</option>
				</c:forEach>
			</select>
		</div>
		</td>
	</tr>
	<tr class="grad">
		<td>
		<div class="input-group">
			<span class="input-group-addon">變更學年</span>
			<input class="form-control" type="text" placeholder="開始學年" name="occur_year_begin"/>
		</div>
		<div class="input-group">
			<span class="input-group-addon">至</span>
			<input class="form-control" type="text" placeholder="結束學年" name="occur_year_end"/>
		</div>		
		</td>
	</tr>
	<tr class="grad">
		<td>
		<div class="input-group">
			<span class="input-group-addon">變更日期</span>
			<input class="form-control date" type="text" placeholder="開始日期" name="occur_date_begin"/>
		</div>
		<div class="input-group">
			<span class="input-group-addon">至</span>
			<input class="form-control date" type="text" placeholder="結束日期" name="occur_date_end"/>
		</div>
		
		
		</td>
	</tr>
	<tr class="tcls">
		<td>
		<div class="input-group">
			<span class="input-group-addon">到課日</span>
			<select class="form-control"name="week" id="week" class="form-control">
			<option value=""></option>			
			<option value="1">星期一</option>
			<option value="2">星期二</option>
			<option value="3">星期三</option>
			<option value="4">星期四</option>
			<option value="5">星期五</option>
			<option value="6">星期六</option>
			<option value="7">星期日</option>
		</select>
		</div>
		
		
		<select class="form-control"name="beginCls" id="beginCls" class="form-control">
			<option value=""></option>
			<c:forEach begin="1" end="14" varStatus="i">
			<option value="${i.index}">自第${i.index}節</option>
			</c:forEach>
		</select>		
		<select class="form-control"name="endCls" id="endCls" class="form-control">
			<option value=""></option>
			<c:forEach begin="1" end="14" varStatus="i">
			<option value="${i.index}">至第${i.index}節</option>
			</c:forEach>
		</select>	
		
		<div rel="popover" title="說明" data-content="輸入星期與節次,回傳學生名單並標記範圍內有課的節次" data-placement="right" class="help btn btn-warning">?</div>	
		</td>
	</tr>
	<tr class="tadd">
		<td>
		<div class="input-group">
			<span class="input-group-addon">現居住址</span>
			<input class="form-control" type="text" name="addr" />
		</div>
		<div rel="popover" title="說明" data-content="請以關鍵字查詢如：「台北」,「新北」,「南港」,「忠孝東路四段」查詢" data-placement="right" class="help btn btn-warning">?</div>
		</td>
	</tr>
	
	<tr class="tsch">
		<td>
		<div class="input-group">
			<span class="input-group-addon">畢業學校</span>
			<input class="form-control" type="text" name="sch"/>
		</div>
		<div rel="popover" title="說明" data-content="請以關鍵字查詢如：「育達」,「協和」,「中華」,「大學」查詢" data-placement="right" class="help btn btn-warning">?</div>
		</td>
	</tr>
	<tr class="tstds">
		<td>
		<div class="input-group">
			<span class="input-group-addon">名單查詢</span>
			<textarea class="form-control" style="height:28px;" name="stds" id="stds"></textarea>
		</div>
		<div rel="popover" title="說明" data-content="連續輸入或貼上多個學生名單,可接受多餘的文字或符號,但不接受姓名中有缺少文字" data-placement="right" class="help btn btn-warning">?</div>
		</td>
	</tr>
	
	<tr>
		<td>		
		<div class="material-switch">
		<span class="glyphicon glyphicon-filter" aria-hidden="true"></span>過濾查詢結果&nbsp; 
	        <input id="opRes" name="opRes" value="" type="checkbox"/>
	        <label for="opRes" class="label-primary"></label>
	    </div>
		</td>
	</tr>
	<tr class="filter">
		<td>
		<div class="row">
		  	
		  	<div class="col-md-3">
		  	<ul class="list-group">                    
	            <li class="list-group-item">系所
	                <div class="material-switch pull-right">
	                    <input id="cDeptName" value="0" name="filter" type="checkbox" checked/>
	                    <label for="cDeptName" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">班級
	                <div class="material-switch pull-right">
	                    <input id="cClassNo" value="1" name="filter" type="checkbox" checked/>
	                    <label for="cClassNo" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">姓名
	                <div class="material-switch pull-right">
	                    <input id="cstudent_name" name="filter" value="2" type="checkbox" checked/>
	                    <label for="cstudent_name" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">學號
	                <div class="material-switch pull-right">
	                    <input id="cstudent_no" name="filter" value="3" type="checkbox" checked/>
	                    <label for="cstudent_no" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">性別
	                <div class="material-switch pull-right">
	                    <input id="csex" name="filter" value="4" type="checkbox" checked/>
	                    <label for="csex" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">身分證號
	                <div class="material-switch pull-right">
	                    <input id="cidno" name="filter" value="5" type="checkbox" checked/>
	                    <label for="cidno" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">出生日期
	                <div class="material-switch pull-right">
	                    <input id="bdate" name="filter" value="6" type="checkbox" checked/>
	                    <label for="bdate" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">入學年月
	                <div class="material-switch pull-right">
	                    <input id="f7" name="filter" value="7" type="checkbox" checked/>
	                    <label for="f7" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">前學程畢業
	                <div class="material-switch pull-right">
	                    <input id="f8" name="filter" value="8" type="checkbox" checked/>
	                    <label for="f8" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">身份別
	                <div class="material-switch pull-right">
	                    <input id="f9" name="filter" value="9" type="checkbox" checked/>
	                    <label for="f9" class="label-primary"></label>
	                </div>
	            </li>                    
            </ul>
		  	</div>
		  	<div class="col-md-3">
		  	<ul class="list-group">                    
	            <li class="list-group-item">組別
	                <div class="material-switch pull-right">
	                    <input id="f10" name="filter" value="10" type="checkbox" checked/>
	                    <label for="f10" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">出生省縣
	                <div class="material-switch pull-right">
	                    <input id="f11" name="filter" value="11" type="checkbox" checked/>
	                    <label for="f11" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">出生鄉鎮市
	                <div class="material-switch pull-right">
	                    <input id="f12" name="filter" value="12" type="checkbox" checked/>
	                    <label for="f12" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">電話
	                <div class="material-switch pull-right">
	                    <input id="f13" name="filter" value="13" type="checkbox" checked/>
	                    <label for="f13" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">行動電話
	                <div class="material-switch pull-right">
	                    <input id="f14" name="filter" value="14" type="checkbox" checked/>
	                    <label for="f14" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">現居郵編
	                <div class="material-switch pull-right">
	                    <input id="f15" name="filter" value="15" type="checkbox" checked/>
	                    <label for="f15" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">現居地址
	                <div class="material-switch pull-right">
	                    <input id="f16" name="filter" value="16" type="checkbox" checked/>
	                    <label for="f16" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">戶籍郵編
	                <div class="material-switch pull-right">
	                    <input id="f17" name="filter" value="17" type="checkbox" checked/>
	                    <label for="f17" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">戶籍地址
	                <div class="material-switch pull-right">
	                    <input id="f18" name="filter" value="18" type="checkbox" checked/>
	                    <label for="f18" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">戶籍里
	                <div class="material-switch pull-right">
	                    <input id="f19" name="filter" value="19" type="checkbox" checked/>
	                    <label for="f19" class="label-primary"></label>
	                </div>
	            </li>                    
            </ul>
		  	</div>
		  	<div class="col-md-3">
		  	<ul class="list-group">                    
	            <li class="list-group-item">畢業校碼
	                <div class="material-switch pull-right">
	                    <input id="f20" name="filter" value="20" type="checkbox" checked/>
	                    <label for="f20" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">畢業校名
	                <div class="material-switch pull-right">
	                    <input id="f21" name="filter" value="21" type="checkbox" checked/>
	                    <label for="f21" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">畢業科系
	                <div class="material-switch pull-right">
	                    <input id="f22" name="filter" value="22" type="checkbox" checked/>
	                    <label for="f22" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">畢業狀態
	                <div class="material-switch pull-right">
	                    <input id="f23" name="filter" value="23" type="checkbox"checked/>
	                    <label for="f23" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">家長姓名
	                <div class="material-switch pull-right">
	                    <input id="f24" name="filter" value="24" type="checkbox" checked/>
	                    <label for="f24" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">變更學年
	                <div class="material-switch pull-right">
	                    <input id="f25" name="filter" value="25" type="checkbox" checked/>
	                    <label for="f25" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">變更學期
	                <div class="material-switch pull-right">
	                    <input id="f26" name="filter" value="26" type="checkbox" checked/>
	                    <label for="f26" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">變更狀態
	                <div class="material-switch pull-right">
	                    <input id="f27" name="filter" value="27" type="checkbox" checked/>
	                    <label for="f27" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">變更原因
	                <div class="material-switch pull-right">
	                    <input id="f28" name="filter" value="28" type="checkbox" checked/>
	                    <label for="f28" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">變更日期
	                <div class="material-switch pull-right">
	                    <input id="f29" name="filter" value="29" type="checkbox" checked/>
	                    <label for="f29" class="label-primary"></label>
	                </div>
	            </li>                    
            </ul>
            
            
            
		  	</div>
		  	
		  	
		  	<div class="col-md-3">
		  	
		  	<ul class="list-group">                    
	            <li class="list-group-item">變更文號
	                <div class="material-switch pull-right">
	                    <input id="f30" name="filter" value="30" type="checkbox" checked/>
	                    <label for="f30" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">畢業號
	                <div class="material-switch pull-right">
	                    <input id="f31" name="filter" value="31" type="checkbox" checked/>
	                    <label for="f31" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">輔/雙主修系
	                <div class="material-switch pull-right">
	                    <input id="f32" name="filter" value="32" type="checkbox" checked/>
	                    <label for="f32" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">電子郵件
	                <div class="material-switch pull-right">
	                    <input id="f33" name="filter" value="33" type="checkbox" checked/>
	                    <label for="f33" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">身份備註
	                <div class="material-switch pull-right">
	                    <input id="f34" name="filter" value="34" type="checkbox" checked/>
	                    <label for="f34" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">英譯姓名
	                <div class="material-switch pull-right">
	                    <input id="f35" name="filter" value="35" type="checkbox" checked/>
	                    <label for="f35" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">卡號
	                <div class="material-switch pull-right">
	                    <input id="f36" name="filter" value="36" type="checkbox" checked/>
	                    <label for="f36" class="label-primary"></label>
	                </div>
	            </li>
	            <li class="list-group-item">全部選擇
	                <div class="material-switch pull-right">
	                    <input id="fall" class="fall" type="checkbox"/>
	                    <label for="fall" class="label-success"></label>
	                </div>
	            </li>
	            <li class="list-group-item">反向選擇
	                <div class="material-switch pull-right">
	                    <input id="frev" type="checkbox"/>
	                    <label for="frev" class="label-warning"></label>
	                </div>
	            </li>
	            <li class="list-group-item">全部清除
	                <div class="material-switch pull-right">
	                    <input id="fnon" class="fall" name="chkCol" type="checkbox"/>
	                    <label for="fnon" class="label-danger"></label>
	                </div>
	            </li>                    
            </ul>
		  	
		  	</div>
		  	
		  	
		  	
		</div>
		
		
                
		
		
		
		
		


		
		
		
		
		
		</td>
	</tr>
</table>
<div class="panel-body">
<div class="btn-group">
		<button class="btn btn-danger" name="method:clSearch">查詢</button>
		<a href="StdSearch" class="btn btn-default">重新查詢</a>
		</div>
		
      	<input style="margin:5px;" type="checkbox" checked disabled><small>遵守各項<a target="_blank" href="http://law.moj.gov.tw/">法律規定</a>並同意記錄查詢過程</small>
</div>
</div>


</form>




<script>

$(".date" ).datepicker({
	changeMonth: true,
	changeYear: true,
	//minDate: '@minDate'
	yearRange: "-100:+0"
	//showButtonPanel: true,
	//dateFormat: 'yy-MM-dd'
});

//$("input[name='beginDate'], input[name='endDate']" ).datepicker();



</script>
</body>
</html>