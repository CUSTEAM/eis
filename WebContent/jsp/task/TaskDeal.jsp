<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="/eis/inc/js/plugin/jquery-ui.js"></script>
	<script src="/eis/inc/js/plugin/jquery-ui-timepicker-addon.js"></script>
	<link href="/eis/inc/css/jquery-ui.css" rel="stylesheet"/>
	<title>工作單處理</title>
</head>
<body>
<div class="bs-callout bs-callout-info" id="callout-helper-pull-navbar">
    <strong>工作單處理</strong>    
</div>
<form action="TaskDeal" method="post" class="form-inline">
<input type="hidden" id="Oid" name="Oid" />

<div class="panel panel-primary">
	<div class="panel-heading">處理中工作單</div>
	<div class="panel-body">
		<div class="input-group">
      	<span class="input-group-addon">單位</span>
			<select class="form-control" name="unit">
				<option value="">所有單位</option>
				<c:forEach items="${CODE_UNIT}" var="c">
				<option <c:if test="${c.id eq unit}">selected</c:if> value="${c.id}">${c.name}</option>
				</c:forEach>
			</select>
		</div>
		
		<div class="input-group" style="width:20%;">
      	<span class="input-group-addon">期間</span>
			<input class="form-control date" name="begin" type="text" placeholder="所有時間" value="${begin}" autocomplete="off"/>
		</div>
		<div class="input-group" style="width:20%;">
      	<span class="input-group-addon">至</span>
			<input class="form-control date" type="text" placeholder="所有時間" name="end" value="${end}" autocomplete="off"/>
		</div>		
		<select class="form-control" name="stat">
			<option value="">所有狀態</option>
			<c:forEach items="${CODE_TASK_STATUS}" var="c">
				<option <c:if test="${c.id eq stat}">selected</c:if> value="${c.id}">${c.name}</option>
				</c:forEach>
		</select>
		<button class="btn btn-danger" name="method:search">查詢</button> <a href="TaskDeal">清除查詢</a>
		
	</div>
	<div class="table"></div>
	<c:if test="${empty que}"><br><p>&nbsp;&nbsp;沒有工作單可顯示, 請改變查詢條件或等待新申請</p></c:if>
	<c:if test="${!empty que}">  
<display:table name="${que}" id="row" class="table" sort="list" pagesize="30" requestURI="TaskDeal?method=search">
	<display:column title="工作單編號" property="Oid" sortable="true"/>
  	<display:column title="工作名稱" sortable="true"><a href="TaskDeal?Oid=${row.Oid}">${row.title}</a></display:column>
  	<display:column title="申請人" property="cname" sortable="true"/>  	
  	<display:column title="申請日期" sortable="true"><fmt:formatDate value="${row.sdate}" pattern="yyyy年M月d日 HH:mm" /></display:column>
  	<display:column title="流程處理人" property="next" sortable="true"/>
  	<!--display:column title="處理狀態" property="status" sortable="true" /-->
  	<display:column title="狀態" sortable="true">${row.statusName}</display:column>
  	<display:column>
  	<c:if test="${row.status eq'H'}"><button class="btn btn-success" onClick="$('#Oid').val(${row.Oid})" name="method:edit">審核</button></c:if>
  	<c:if test="${row.status ne'H'}"><button class="btn btn-danger" onClick="$('#Oid').val(${row.Oid})" name="method:edit">處理</button></c:if>
  	
  	
  	</display:column>
</display:table>
</c:if>
</div>



</form>
<script>
$(".date").datepicker({
	changeMonth: true,
	changeYear: true,
	//minDate: '@minDate'
	yearRange: "-100:+0"
	//showButtonPanel: true,
	//dateFormat: 'yy-MM-dd'
});
</script>
</body>
</html>