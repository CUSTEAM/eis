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
	$('.help').popover("show");
	setTimeout(function() {
		$('.help').popover("hide");
	}, 0);
	
	$("#type" ).change(function() {
		if($("#type").val()=="Gstmd"){
			$(".tcls" ).hide("hide");
		}else{
			$(".tcls" ).show("slow");
		}
		  
	});	
	
	$("select[name='week'], select[name='beginCls'], select[name='endCls']" ).change(function() {
		if($("#week").val()+$("#beginCls").val()+$("#endCls").val()!=""){
			$(".gtype, .tname, .tsex, .tide, .tadd, .tsch, .tsch, .tstds" ).hide("slow");
		}else{
			$(".gtype, .tname, .tsex, .tide, .tadd, .tsch, .tsch, .tstds" ).show("slow");
		}
		  
	});	
	
	$("#stds").keyup(function() {		
		if(this.value!=""){
			$(".tcls, .tsel,.tname, .tsex, .tide, .tadd, .tsch, .tsch" ).hide("slow");
		}else{
			$(".tcls, .tsel,.tname, .tsex, .tide, .tadd, .tsch, .tsch" ).show("slow");
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
</head>
<body>
<div class="bs-callout bs-callout-warning" id="callout-helper-pull-navbar">
學生查詢
</div>
<form action="StdSearch" method="post" class="form-inline" enctype="multipart/form-data">
<table class="table">
	<tr class="gtype">
		<td>
		<div class="input-group">
		<span class="input-group-addon">目標</span>
		<select class="form-control" name="type" id="type">
			<option <c:if test="${type eq'stmd'}">selected</c:if> value="stmd">在校生</option>
			<option <c:if test="${type eq'Gstmd'}">selected</c:if> value="Gstmd">離校生</option>
		</select>
		</div>
		</td>
	</tr>
	<tr class="tsel">
		<td><%@ include file="/inc/jsp-kit/classSelectorFull.jsp"%></td>
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
				<option <c:if test="${sex eq''}">selected</c:if> value="">全部</option>
				<option <c:if test="${sex eq'1'}">selected</c:if> value="1">男</option>
				<option <c:if test="${sex eq'2'}">selected</c:if> value="2">女</option>
			</select>
		</div>
		
		<div class="input-group">
			<span class="input-group-addon">身份別</span>
			<select class="form-control" name="ident">
				<option value="">全部</option>
				<c:forEach items="${CODE_STMD_IDENT}" var="i">
				<option <c:if test="${ident eq i.id}"></c:if> value="${i.id}">${i.name}</option>
				</c:forEach>
			</select>
		</div>
		</td>
	</tr>
	<tr class="tide">
		<td>		
		<div class="input-group">
			<span class="input-group-addon">生日範圍</span>
			<input class="form-control" type="text" id="beginDate" placeholder="點一下輸入日期" name="beginDate" value="${beginDate}"/>
		</div>
		
		<div class="input-group">
			<span class="input-group-addon">至</span>
			<input class="form-control" type="text" id="endDate" placeholder="點一下輸入日期" name="endDate" value="${endDate}"/>
		</div>
		<div rel="popover" title="說明" data-content="輸入開始日期則回傳自開始日期至今,輸入結束日期則回傳至結束日期,兩欄均輸入則回傳範圍內的學生名單" data-placement="right" class="help btn btn-warning">?</div>			
		
		</td>
	</tr>
	
	<tr class="tcls">
		<td>
		<div class="input-group">
			<span class="input-group-addon">到課日</span>
			<select class="form-control"name="week" id="week" class="form-control">
			<option value=""></option>			
			<option <c:if test="${week==1}">selected</c:if> value="1">星期一</option>
			<option <c:if test="${week==2}">selected</c:if> value="2">星期二</option>
			<option <c:if test="${week==3}">selected</c:if> value="3">星期三</option>
			<option <c:if test="${week==4}">selected</c:if> value="4">星期四</option>
			<option <c:if test="${week==5}">selected</c:if> value="5">星期五</option>
			<option <c:if test="${week==6}">selected</c:if> value="6">星期六</option>
			<option <c:if test="${week==7}">selected</c:if> value="7">星期日</option>
		</select>
		</div>
		
		
		<select class="form-control"name="beginCls" id="beginCls" class="form-control">
			<option value=""></option>
			<c:forEach begin="1" end="14" varStatus="i">
			<option <c:if test="${beginCls eq i.index}">selected</c:if> value="${i.index}">自第${i.index}節</option>
			</c:forEach>
		</select>		
		<select class="form-control"name="endCls" id="endCls" class="form-control">
			<option value=""></option>
			<c:forEach begin="1" end="14" varStatus="i">
			<option <c:if test="${endCls eq i.index}">selected</c:if> value="${i.index}">至第${i.index}節</option>
			</c:forEach>
		</select>	
		
		<div rel="popover" title="說明" data-content="輸入星期與節次,回傳學生名單並標記範圍內有課的節次" data-placement="right" class="help btn btn-warning">?</div>	
		</td>
	</tr>
	<tr class="tadd">
		<td>
		<div class="input-group">
			<span class="input-group-addon">現居住址</span>
			<input class="form-control" type="text" name="addr" value="${addr}"/>
		</div>
		<div rel="popover" title="說明" data-content="請以關鍵字查詢如：「台北」,「新北」,「南港」,「忠孝東路四段」查詢" data-placement="right" class="help btn btn-warning">?</div>
		</td>
	</tr>
	
	<tr class="tsch">
		<td>
		<div class="input-group">
			<span class="input-group-addon">畢業學校</span>
			<input class="form-control" type="text" name="sch" value="${sch}"/>
		</div>
		<div rel="popover" title="說明" data-content="請以關鍵字查詢如：「育達」,「協和」,「中華」,「大學」查詢" data-placement="right" class="help btn btn-warning">?</div>
		</td>
	</tr>
	<tr class="tstds">
		<td>
		<div class="input-group">
			<span class="input-group-addon">名單查詢</span>
			<textarea class="form-control" style="height:28px;" name="stds" id="stds">${stds}</textarea>
		</div>
		<div rel="popover" title="說明" data-content="連續輸入或貼上多個學生名單,可接受多餘的文字或符號,但不接受姓名中有缺少文字" data-placement="right" class="help btn btn-warning">?</div>
		</td>
	</tr>

	<tr>
		<td>
		<div class="btn-group">
		<button class="btn btn-danger" name="method:clSearch">查詢</button>
		<a href="StdSearch" class="btn btn-default">重新查詢</a>
		</div>
		
      <input style="margin:5px;" type="checkbox" checked disabled><small>遵守各項<a target="_blank" href="http://law.moj.gov.tw/">法律規定</a>並同意記錄查詢過程</small>
		</td>
	</tr>
</table>



</form>




<script>

$("input[name='beginDate'], input[name='endDate']" ).datepicker({
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