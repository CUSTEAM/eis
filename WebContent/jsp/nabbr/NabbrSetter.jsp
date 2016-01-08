<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>教室/實驗室設置</title>
<link href="/eis/inc/css/jquery-ui.css" rel="stylesheet"/>
<link href="/eis/inc/bootstrap/plugin/nav-wizard.bootstrap.css" rel="stylesheet"/>
<link href="/eis/inc/bootstrap/plugin/silviomoreto-bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet"/>
<link href="/eis/inc/js/plugin/jquery-ui-timepicker-addon/jquery-ui-timepicker-addon.min.css" rel="stylesheet"/>


<script src="/eis/inc/js/plugin/jquery-ui.js"></script>
<script src="/eis/inc/js/plugin/jquery-ui-timepicker-addon/jquery-ui-timepicker-addon.min.js"></script>
<script src="/eis/inc/bootstrap/plugin/silviomoreto-bootstrap-select/js/bootstrap-select.min.js"></script>
<script src="/eis/inc/bootstrap/plugin/silviomoreto-bootstrap-select/js/i18n/defaults-zh_TW.min.js"></script>

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
<form action="NabbrSetter" method="post" class="form-horizontal form-inline" onSubmit="$.blockUI({message:null});">
<div class="bs-callout bs-callout-info" id="callout-helper-pull-navbar">
	<h4>教室/實驗室設置</h4>
	<p>點選 <button class="btn btn-success btn-xs">新增</button> 建立新教室/實驗室, 點選 <button class="btn btn-default btn-xs">查詢</button> 顯示或列印各項報表</p>
	
		
</div>
<table class="table">
	<tr>
		<td>
		<select name="building" class="selectpicker show-tick" data-width="auto">
			<option value="">選擇大樓</option>
			<c:forEach items="${CODE_BUILDING}" var="c">
			<option <c:if test="${c.id eq building}">selected</c:if> value="${c.id}">${c.name}</option>
			</c:forEach>
		</select>
		
		<select name="floor" class="selectpicker show-tick" data-width="auto">
			<option value="">選擇樓層</option>
			<c:forEach begin="1" end="12" varStatus="c">
			<option <c:if test="${c.index eq floor}">selected</c:if> value="${c.index}">${c.index}</option>
			</c:forEach>
		</select>
		<input type="text" name="room_id" value="${room_id}" class="form-control" autocomplete="off" 
		placeholder="教室編號" data-provide="typeahead"/>
		<input type="text" name="name2" value="${name2}" class="form-control" autocomplete="off" 
		placeholder="教室名稱" data-provide="typeahead"/>
		
		</td>
	</tr>
	<tr>
		<td>
		
		<select name="dept" class="selectpicker show-tick" data-width="auto">
			<option value="">選擇系所</option>
			<c:forEach items="${CODE_DEPT}" var="c">
			<option <c:if test="${c.id eq dept}">selected</c:if> value="${c.id}">${c.name}</option>
			</c:forEach>
		</select>
		
		<select name="unit" class="selectpicker show-tick" data-width="auto">
			<option value="">選擇單位</option>
			<c:forEach items="${CODE_UNIT}" var="c">
			<option <c:if test="${c.id eq unit}">selected</c:if> value="${c.id}">${c.name}</option>
			</c:forEach>
		</select>
		
		
		
		<select name="boro" class="selectpicker show-tick" data-width="auto">
			<option <c:if test="${boro eq 'N'}">selected</c:if> value="N">不納入統計</option>	
			<option <c:if test="${boro eq 'Y'}">selected</c:if> value="Y">納入統計</option>
	
		</select>
		</td>
	</tr>
	<tr>
		<td>
		<button class="btn btn-success" name="method:create">新增</button>
		<div class="btn-group">
		  <button class="btn btn-default" name="method:search">查詢</button>
		  
		  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		    <span class="caret"></span>
		    <span class="sr-only">Toggle Dropdown</span>
		  </button>
		  <ul class="dropdown-menu">
		    <li><a href="#">教室課表</a></li>
		    <li><a href="#">使用記錄</a></li>
		    <li><a href="#">研究成果</a></li>
		    <li role="separator" class="divider"></li>
		    <li><a href="#">教室簡介</a></li>
		  </ul>
		</div>
		<a class="btn btn-default" href="NabbrSetter">重新查詢</a>
		
		
		
		</td>
	</tr>
</table>
<c:if test="${!empty rooms}">
<input type="hidden" name="delRoom" id="delRoom" />
<div class = "panel panel-primary">
   <div class = "panel-heading">教室列表</div>
	<display:table name="${rooms}" id="row" class="table table-hover" sort="list" 
	 pagesize="20" requestURI="NabbrSetter">
	  	
	  	<display:column style="white-space: nowrap">
	  		<button name="method:del" id="${row.room_id}" class="btn btn-default btn-sm del">刪除</button>
	  		<!-- button name="method:save" id="${row.room_id}" class="btn btn-danger">修改</button -->
		</display:column>	  	
	  	
	  	<display:column title="大樓" style="white-space: nowrap" sortable="true">
	  	<input type="hidden" id="Oid${row.Oid}" name="Oid" />
	  	<select name="b" class="form-control" onChange="check('${row.Oid}')">
			<c:forEach items="${CODE_BUILDING}" var="c">
			<option <c:if test="${c.id eq row.building}">selected</c:if> value="${c.id}">${c.name}</option>
			</c:forEach>
		</select>
	  	</display:column>	
	  	  	
	  	<display:column title="樓層" style="white-space: nowrap" sortable="true">
	  	<select name="f" class="form-control" onChange="check('${row.Oid}')">
			<c:forEach begin="1" end="12" varStatus="c">
			<option <c:if test="${c.index eq row.floor}">selected</c:if> value="${c.index}">${c.index}</option>
			</c:forEach>
		</select>
		</display:column>
		<display:column title="教室編號" style="white-space: nowrap" sortable="true">
		<input type="text" class="form-control" name="r" value="${row.room_id}" onKeyDown="check('${row.Oid}')"/>
		</display:column>
		<display:column title="教室名稱" style="white-space: nowrap" sortable="true">
		<input type="text" class="form-control" name="n" value="${row.name2}" onKeyDown="check('${row.Oid}')"/>
		</display:column>
	  	<!-- display:column style="width:10%; white-space:nowrap;" title="教室名稱" property="name2" sortable="true"/ -->
	  	<display:column style="width:10%; white-space:nowrap;" title="座位" property="seat" sortable="true"/>
	  	
	  	
	  	<display:column title="學術單位" style="white-space: nowrap" sortable="true">
	  	<select name="d" class="form-control" onChange="check('${row.Oid}')">
			<option value="">選擇系所</option>
			<c:forEach items="${CODE_DEPT}" var="c">
			<option <c:if test="${c.id eq row.dept}">selected</c:if> value="${c.id}">${c.name}</option>
			</c:forEach>
		</select>		
	  	</display:column>
	  	
	  	<display:column title="行政單位" style="white-space: nowrap" sortable="true">
	  	<select name="u" class="form-control" onChange="check('${row.Oid}')">
			<option value="">選擇單位</option>
			<c:forEach items="${CODE_UNIT}" var="c">
			<option <c:if test="${c.id eq row.unit}">selected</c:if> value="${c.id}">${c.name}</option>
			</c:forEach>
		</select>
	  	</display:column>
	  	<display:column title="納入統計" style="width:100%; white-space: nowrap" sortable="true">
	  	<select name="bo" class="form-control" onChange="check('${row.Oid}')">	
			<option <c:if test="${row.boro eq 'Y'}">selected</c:if> value="Y">是</option>
			<option <c:if test="${row.boro eq 'N'}">selected</c:if> value="N">否</option>
		</select>
	  	</display:column>	  	
	</display:table>
		
	<div class="panel-footer text-center">	
		<div class="btn-group" role="group" aria-label="...">		
		<button class="btn btn-danger" name="method:save">儲存修改</button>
		<a href="NabbrSetter" class="btn btn-default" >重新查詢</a>		
		</div>
	</div>
</div>


</c:if>
</form>
<script>
$(".timepick" ).datepicker();
function check(Oid){
	$("#Oid"+Oid).val(Oid);
}

$(".del").click(function(){	
	$("#delRoom").val(this.id);
	return confirm('確定刪除教室?');
})
</script>
</body>
</html>