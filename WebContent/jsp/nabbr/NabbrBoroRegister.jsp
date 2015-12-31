<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>教室/實驗室借用記錄</title>
<link href="/eis/inc/css/jquery-ui.css" rel="stylesheet"/>
<link href="/eis/inc/bootstrap/plugin/nav-wizard.bootstrap.css" rel="stylesheet"/>
<link href="/eis/inc/bootstrap/plugin/silviomoreto-bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet"/>
<link href="/eis/inc/js/plugin/jquery-ui-timepicker-addon/jquery-ui-timepicker-addon.min.css" rel="stylesheet"/>


<script src="/eis/inc/js/plugin/jquery-ui.js"></script>
<script src="/eis/inc/js/plugin/jquery-ui-timepicker-addon/jquery-ui-timepicker-addon.min.js"></script>
<script src="/eis/inc/bootstrap/plugin/silviomoreto-bootstrap-select/js/bootstrap-select.min.js"></script>
<script src="/eis/inc/bootstrap/plugin/silviomoreto-bootstrap-select/js/i18n/defaults-zh_TW.min.js"></script>
<script src="/eis/inc/js/plugin/bootstrap-typeahead.js"></script>
<script src="/eis/inc/js/autoComplete.js"></script>
<script>
$(document).ready(function() {	
	/*$('.help').popover("show");
	setTimeout(function() {
		$('.help').popover("hide");
	}, 5000);*/
	
	
	/*$("input[name*='del']").click(function() {
		alert(this.id);
		$(".appDel").val(this.id);
	});*/
	
	$(".edit").click(function(){
		$("#Oid").val(this.id);
	})
	
	$(".check").click(function(){
		if($("#cls"+this.id).val()!=""){
			$("#cls"+this.id).val("");
		}else{
			$("#cls"+this.id).val(this.id);
		}
		
		
	})
});
</script>
</head>
<body>


<form action="NabbrBoroRegister" method="post" class="form-horizontal" onSubmit="$.blockUI({message:null});">

<c:if test="${empty cls}">
<div class="bs-callout bs-callout-warning" id="callout-helper-pull-navbar">
	<h4>建立教室/實驗室使用記錄</h4>
	點選 <button class="btn btn-danger btn-xs">建立使用記錄</button> 或 <button class="btn btn-default btn-xs">查詢使用記錄</button>
</div>
<ul class="nav nav-pills nav-wizard">
    <li class="active"><a href="#" data-toggle="tab">選擇研究計畫</a><div class="nav-arrow"></div></li>
    <li><div class="nav-wedge"></div><a href="" data-toggle="tab">選擇教室</a><div class="nav-arrow"></div></li>
    <li><div class="nav-wedge"></div><a href="" data-toggle="tab">日期範圍</a><div class="nav-arrow"></div></li>
    <li><div class="nav-wedge"></div><a href="" data-toggle="tab">節次範圍</a><div class="nav-arrow"></div></li>
    <li><div class="nav-wedge"></div><a href="" data-toggle="tab">申請完成</a></li>
</ul>
<table class="table" >
	<tr>
		<td>		
			
			<div class="input-group">
				<div class="input-group-addon">選擇專案</div>
				<select class="selectpicker show-tick form-control" name="RcTableCode">
					<option value="">指定專案</option>
					<c:forEach items="${rclist}" var="r">
						<optgroup label="${r.name}">
						<c:forEach items="${r.rc}" var="c">
						<c:set var="x" value="${r.rc_table},${c.Oid}" />				
						<option <c:if test="${x eq RcTableCode}">selected</c:if> value="${r.id},${c.Oid}">${c.school_year}學年, ${c.title}</option>						
						</c:forEach>
						</optgroup>
					</c:forEach>    
				</select>
			</div>
		
		</td>
	</tr>
	
	<tr>
		<td>
		
		<div class="input-group">
			<div class="input-group-addon">選擇教室</div>
			<input type="text" name="room_id" value="${room_id}" class="place form-control" autocomplete="off" onClick="this.value='';" data-provide="typeahead"/>
			</div>
		
		</td>
	</tr>

	<tr>
		<td>		
		<div class="input-group">
			<div class="input-group-addon">開始日期</div>
			<input name="beginDate" value="${beginDate}" class="form-control timepick" autocomplete="off" type="text" />
			</div>
		</td>
	</tr>
	<tr>
		<td>
		<div class="input-group">
			<div class="input-group-addon">結束日期</div>
			<input type="text" value="${endDate}" name="endDate" class="form-control timepick" autocomplete="off" placeholder="1日內借用無需輸入"/>
		</div>
		</td>
	</tr>
	<tr>
		<td>
		<div class="input-group">
			<div class="input-group-addon">排除假日</div>
			<select name="holiday" class="form-control">
				<option value="*">排除周六、周日</option>
				<option value="">不排除</option>				
			</select>
		</div>
		</td>
	</tr>
	<tr>
		<td>
		<div class="input-group">
			<div class="input-group-addon">開始節次</div>
			<select class="selectpicker show-tick form-control" name="beginCls">
				<option value="">請選擇開始節次</option>					
				<optgroup label="上午">
				<c:forEach begin="1" end="4" varStatus="i">
				<option <c:if test="${i.index==beginCls}">selected</c:if> value="${i.index}">第${i.index}節</option>						
				</c:forEach>
				</optgroup>
				<optgroup label="下午">
				<c:forEach begin="5" end="10" varStatus="i">
				<option <c:if test="${i.index==beginCls}">selected</c:if> value="${i.index}">第${i.index}節</option>						
				</c:forEach>
				</optgroup>
				<optgroup label="晚上">
				<c:forEach begin="11" end="14" varStatus="i">
				<option <c:if test="${i.index==beginCls}">selected</c:if> value="${i.index}">第${i.index}節</option>						
				</c:forEach>
				</optgroup>				
			</select>
			</div>		
		</td>
	</tr>
	<tr>
		<td>	
			<div class="input-group">
			<div class="input-group-addon">結束節次</div>
			<select class="selectpicker show-tick form-control" name="endCls">
				<option value="">1節內借用無需輸入</option>					
				<optgroup label="上午">
				<c:forEach begin="1" end="4" varStatus="i">
				<option <c:if test="${i.index==endCls}">selected</c:if> value="${i.index}">第${i.index}節</option>						
				</c:forEach>
				</optgroup>
				<optgroup label="下午">
				<c:forEach begin="5" end="10" varStatus="i">
				<option <c:if test="${i.index==endCls}">selected</c:if> value="${i.index}">第${i.index}節</option>						
				</c:forEach>
				</optgroup>
				<optgroup label="晚上">
				<c:forEach begin="11" end="14" varStatus="i">
				<option <c:if test="${i.index==endCls}">selected</c:if> value="${i.index}">第${i.index}節</option>						
				</c:forEach>
				</optgroup>				
			</select>
			</div>		
		</td>
	</tr>
	<tr>
		<td>
		<div class="input-group">
			<div class="input-group-addon">使用人數</div>
			<input type="text" value="${users}" name="users" class="form-control" autocomplete="off" placeholder="人數"/>
		</div>
		</td>
	</tr>
	<tr>
		<td>
		<div class="btn-group" role="group" aria-label="...">
		<button name="method:create" class="btn btn-danger">建立使用記錄</button>
		<button name="method:search" class="btn btn-default">查詢使用記錄</button>
		</div>
		</td>
	</tr>
</table>
</c:if>
		
<input type="hidden" name="Oid" id="Oid" value="${Oid}"/>
<c:if test="${!empty boros}">
<div class = "panel panel-primary">
   <div class = "panel-heading"><h3 class = "panel-title">使用記錄</h3></div>
	<display:table name="${boros}" id="row" class="table table-condensed" sort="list"  requestURI="NabbrBoroRegister?method=search">
	  	<display:column style="white-space: nowrap">
	  	
	  		<button name="method:edit" id="${row.Oid}" class="btn btn-default btn-sm edit">修改</button>
			<button name="method:delApp" id="${row.Oid}" class="btn btn-danger btn-sm edit">刪除</button>
		
	  	</display:column>
	  	<display:column style="width:5%; white-space:nowrap;" title="編號" property="Oid" sortable="true" />
	  	<display:column style="width:10%; white-space:nowrap;" title="申請日期" property="date" sortable="true"/>
	  	<display:column style="width:5%; white-space:nowrap;" title="教室" property="room_id" sortable="true"/>
	  	<display:column style="width:10%; white-space:nowrap;" title="開始日期" property="begin" sortable="true"/>
	  	<display:column style="width:10%; white-space:nowrap;" title="結束日期" property="end" sortable="true"/>
	  	<display:column style="width:5%; white-space:nowrap;" title="時數" property="cnt" sortable="true"/>
	  	<display:column style="width:5%; white-space:nowrap;" title="人數" property="users" sortable="true"/>
	  	<display:column style="width:5%; white-space:nowrap;" title="狀態" property="lender" sortable="true"/>
	  	<display:column style="width:100%;" title="審核" property="result" sortable="true"/>	  	
	  	
	</display:table>
</div>
</c:if>
		
<c:if test="${!empty cls}">
<div class="bs-callout bs-callout-warning" id="callout-helper-pull-navbar">
	<h4>建立教室/實驗室使用記錄</h4>
	勾選記錄後點選表格頂端或尾端的 <button type="button" class="btn btn-danger btn-xs">刪除勾選</button><br>
	結束請點選 <a href="NabbrBoroRegister" class="btn btn-default btn-xs">返回</a>
</div>

   
   
<div class = "panel panel-primary">
	<div class = "panel-heading"><h3 class = "panel-title">所有欄位均可排序</h3></div>
	<display:table name="${cls}" id="row" class="table table-condensed" sort="list" requestURI="NabbrBoroRegister?method=edit">
		<display:column title="<button name='method:delCls' id='${row.Oid}' class='btn btn-danger btn-sm check'>刪除勾選</button>" style="width:5%; text-align:center;">
		<input type="checkbox" class="check" id="${row.Oid}"/>
		</display:column>
	  	<display:column style="white-space:nowrap;" title="日期" property="boro_date" sortable="true"/>
	  	<display:column style="width:5%; text-align:center; white-space:nowrap;" title="星期" property="week" sortable="true"/>
	  	<display:column style="width:5%; text-align:center; white-space:nowrap;" title="節次" property="cls" sortable="true"/>  	
	  	<display:column style="width:100%;">
	  	<input type="hidden" name="cls" id="cls${row.Oid}" />
	  	<!--button name="method:delCls" id="${row.Oid}" class="btn btn-danger btn-sm check">刪除</button-->
	  	</display:column>
	</display:table>
	<div class="panel-footer">
	<div class="btn-group" role="group" aria-label="...">
		<button name="method:delCls" class="btn btn-danger chedk" id="${c.Oid}">刪除勾選</button>
		<a href="NabbrBoroRegister" class="btn btn-default">返回</a>
	</div>
	</div>
</div>
</c:if>




</form>

<script>
$(".timepick" ).datepicker();
</script>


</body>
</html>