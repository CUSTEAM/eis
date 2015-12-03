<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>教室/實驗室管理</title>
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
	$('.help').popover("show");
	setTimeout(function() {
		$('.help').popover("hide");
	}, 5000);
	
	
	/*$("input[name*='del']").click(function() {
		alert(this.id);
		$(".appDel").val(this.id);
	});*/
	
	$(".edit").click(function(){
		$("#Oid").val(this.id);
	})
	
	/*$(".check").click(function(){
		if($("#cls"+this.id).val()!=""){
			$("#cls"+this.id).val("");
		}else{
			$("#cls"+this.id).val(this.id);
		}		
	})*/
});
</script>
</head>
<body>
<form action="NabbrManager" method="post" class="form-horizontal" onSubmit="$.blockUI({message:null});">
<input type="hidden" name="Oid" id="Oid" value="${Oid}"/>
<c:if test="${!empty rooms}">
<div class="bs-callout bs-callout-info" id="callout-helper-pull-navbar">
	<h4>教室/實驗室管理</h4>
	<p>點選 <button class="btn btn-default btn-xs">教室管理</button> 
	或 <button class="btn btn-danger btn-xs">列印</button> 目前使用記錄</p>
	
	<div class="row">
  		<div class="col-xs-2">
			<div class="input-group has-info">
				<div class="input-group-addon">列印範圍</div>
				<input class="form-control timepick" type="text" id="begin" name="begin" value="<c:choose><c:when test="${begin==null}">${school_term_begin}</c:when><c:otherwise>${begin}</c:otherwise></c:choose>"/>
			</div>
		</div>
		<div class="col-xs-2">
			<div class="input-group has-info">
				<div class="input-group-addon">至</div>
				<input class="form-control timepick" type="text" id="end" name="end" value="<c:choose><c:when test="${end==null}">${school_term_end}</c:when><c:otherwise>${end}</c:otherwise></c:choose>"/>
			</div>
		</div>
		<div class="btn-group">
				<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" 
				aria-haspopup="true" aria-expanded="false">
			    	教室列印 <span class="glyphicon glyphicon-print" aria-hidden="true"></span>
			  	</button>
			  	<ul class="dropdown-menu">
				    <li><a href="#">教室課表</a></li>
				    <li><a href="UseDetail?room_id=${row.room_id}">使用記錄</a></li>
				    <li><a href="#">研究成果</a></li>
				    <li role="separator" class="divider"></li>
				    <li><a href="#">教室簡介</a></li>
			  	</ul>
			</div>
		
	</div>	
</div>
<div class = "panel panel-primary">
   <div class = "panel-heading"><h3 class = "panel-title">教室列表</h3></div>
	<display:table name="${rooms}" id="row" class="table table-condensed" sort="list"  requestURI="NabbrManager">
	  	<display:column style="white-space: nowrap">
	  		<button name="method:edit" id="${row.Oid}" class="btn btn-danger btn-sm edit">教室管理</button>	  		
	  		<div class="btn-group">
				<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" 
				aria-haspopup="true" aria-expanded="false">
			    	教室列印 <span class="glyphicon glyphicon-print" aria-hidden="true"></span>
			  	</button>
			  	<ul class="dropdown-menu">
				    <li><a href="#">教室課表</a></li>
				    <li><a href="UseDetail?room_id=${row.room_id}">使用記錄</a></li>
				    <li><a href="#">研究成果</a></li>
				    <li role="separator" class="divider"></li>
				    <li><a href="#">教室簡介</a></li>
			  	</ul>
			</div>
		</display:column>
	  	<display:column style="width:10%; white-space:nowrap;" title="大樓" property="building" sortable="true" />
	  	<display:column style="width:10%; white-space:nowrap;" title="樓層" property="floor" sortable="true"/>
	  	<display:column style="width:10%; white-space:nowrap;" title="教室編號" property="room_id" sortable="true"/>
	  	<display:column style="width:10%; white-space:nowrap;" title="教室名稱" property="name2" sortable="true"/>
	  	<display:column style="width:10%; white-space:nowrap;" title="座位" property="seat" sortable="true"/>
	  	<display:column style="width:100%; white-space:nowrap;" title="納入統計" property="boro" sortable="true"/>
	</display:table>
</div>
</c:if>

<c:if test="${!empty room}">
<div class="bs-callout bs-callout-info" id="callout-helper-pull-navbar">
	<h4>教室/實驗室分配管理</h4>	
	<p>點選 <button class="btn btn-danger btn-xs">儲存</button> 或 <button class="btn btn-default btn-xs">返回</button></p>
	<p>可指定多位 <span class="label label-success">負責人員</span> , 每次儲存增加1人為限 </p>
</div>
<table class="table" >
	<tr>
		<td>
		<div class="input-group">
			<div class="input-group-addon">教室編號</div>
			<input readonly class="form-control" type="text" id="room_id" name="room_id" value="${room.room_id}"/>
		</div>
		</td>
	</tr>
	
	<tr>
		<td>		
		<div class="input-group">
			<div class="input-group-addon">大樓位置</div>
			<select disabled class="selectpicker show-tick form-control">
			<optgroup label="...">
				<c:forEach items="${CODE_BUILDING}" var="b">						
					<option <c:if test="${b.id eq room.building}">selected</c:if> value="${b.id}">${b.name}</option>						
					</c:forEach>   
				</optgroup> 
			</select>
		</div>		
		</td>
	</tr>
	<tr>
		<td>
		<div class="input-group">
			<div class="input-group-addon">樓層位置</div>
			<select disabled class="selectpicker show-tick form-control">
			<optgroup label="...">
				<c:forEach begin="1" end="12" varStatus="i">						
					<option <c:if test="${i.index eq room.floor}">selected</c:if> value="${i.index}">${i.index}樓</option>						
				</c:forEach>   
				</optgroup> 
			</select>
		</div>		
		</td>
	</tr>
	<tr>
		<td>
		<div class="input-group">
			<div class="input-group-addon">所屬單位</div>
			<input type="text" class="form-control" value="${room.unit}" />
		</div>
		</td>
	</tr>
	
	<tr>
		<td>
		<div class="input-group">
			<div class="input-group-addon">所屬系所</div>
			<input disabled type="text" class="form-control" value="${room.dept}" />
		</div>
		</td>
	</tr>
	<tr>
		<td>
		<div class="input-group has-success">
			<div class="input-group-addon">教室名稱</div>
			<input class="form-control" type="text" name="name2" value="${room.name2}" />
		</div>
		</td>
	</tr>
	<tr>
		<td>
		<div class="input-group has-success">
			<div class="input-group-addon">納入統計</div>
			<select class="form-control" name="boro">
				<option value="Y" <c:if test="${room.boro eq 'Y'}">selected</c:if>>是</option>
				<option value="N" <c:if test="${room.boro eq 'N'}">selected</c:if>>否</option>
			</select>
		</div>
		</td>
	</tr>
	<tr>
		<td>
		<div class="input-group has-success">
			<div class="input-group-addon">座位數量</div>
			<input type="text" class="form-control" name="seat" value="${room.seat}" />
		</div>
		</td>
	</tr>
	
	<tr>
		<td>
		<div class="input-group has-success">
			<div class="input-group-addon">負責人員</div>
			<input type="text" class="form-control techid" name="charge" placeholder="指定新人員" value="" />
		</div>
		</td>
	</tr>
	<c:forEach items="${room.charge}" var="c">
	<tr>
		<td>
		<div class="input-group has-success">
			<div class="input-group-addon">負責人員</div>
			<input type="text" class="form-control" name="charge" onClick="this.value=''" value="${c.Oid},${c.cname}" />
		</div>
		</td>
	</tr>
	</c:forEach>	
	<tr>
		<td>
		<div class="input-group has-success">
			<div class="input-group-addon">使用需知</div>
			<textarea class="form-control" rows="1" name="remark">${room.remark}</textarea>
		</div>
		</td>
	</tr>
	
	<tr>
		<td>
		<button class="btn btn-danger" name="method:save">儲存</button>
		<a class="btn btn-default" href="NabbrManager">返回</a>
		</td>
	</tr>
	
	
</table>

</c:if>

</form>
<script>
$(".timepick" ).datepicker();
</script>
</body>
</html>