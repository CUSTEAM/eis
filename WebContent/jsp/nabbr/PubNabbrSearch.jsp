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


</head>
<body>
<form action="PubNabbrSearch" method="post" class="form-inline" onSubmit="$.blockUI({message:null});">
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
		
		<select name="type" class="selectpicker show-tick" data-width="auto">
			<option <c:if test="${boro eq ''}">selected</c:if> value="">所有教室與實驗室</option>
			<option <c:if test="${boro eq 'Y'}">selected</c:if> value="Y">標記為納入統計(實驗室)</option>
			<option <c:if test="${boro eq 'N'}">selected</c:if> value="N">標計為不納入統計(教室或其他空間)</option>	
				
		</select>
		
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
		
		
		
		
		</td>
	</tr>
	<tr>
		<td>
		
		
		
		<div class="form-group">
		    <label class="sr-only" for="exampleInputAmount">Amount (in dollars)</label>
		    <div class="input-group">
		      <div class="input-group-addon">研究成果統計自</div>
		      <input type="text" name="begin" value="${school_term_begin}" class="form-control timepick" autocomplete="off" 
				placeholder="開始時間" data-provide="typeahead"/>
		    </div>
		</div>
		
		
		<div class="form-group">
		    <label class="sr-only" for="exampleInputAmount">Amount (in dollars)</label>
		    <div class="input-group">
		      <div class="input-group-addon">至</div>
		      <input type="text" name="end" value="${school_term_end}" class="form-control timepick" autocomplete="off" 
				placeholder="結束時間" data-provide="typeahead"/>
		    </div>
		</div>
		
		</td>
	<tr>
		<td>
		<button class="btn btn-default" name="method:print">查詢</button>
		<!-- div class="btn-group">
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
		</div-->
		<a class="btn btn-default" href="PubNabbrSearch">重新查詢</a>
		
		
		
		</td>
	</tr>
</table>

<script>
$(".timepick" ).datepicker();
</script>
</form>
</body>
</html>