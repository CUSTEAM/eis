<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
			<select class="form-control" disabled>
				<option>所屬單位</option>
				<option>電子計算機中心</option>
			</select>
		</div>
		
		<div class="input-group">
      	<span class="input-group-addon">期間</span>
			<input class="form-control" type="text" placeholder="所有時間" value="" disabled autocomplete="off" data-provide="typeahead"/>
		</div>
		<button class="btn btn-danger" disabled>過濾</button>
	</div>
	<div class="table"></div>
	<c:if test="${empty que}"><br><p>&nbsp;&nbsp;沒有工作單可顯示, 請改變查詢條件或重新申請工作單</p></c:if>
	<c:if test="${!empty que}">  
<display:table name="${que}" id="row" class="table" sort="list" excludedParams="*" pagesize="30" requestURI="TaskDeal">
	<display:column title="工作單編號" property="Oid" sortable="true"/>
  	<display:column title="工作名稱" sortable="true"><a href="TaskDeal?Oid=${row.Oid}">${row.title}</a></display:column>
  	<display:column title="申請人" property="cname" sortable="true"/>  	
  	<display:column title="申請日期" sortable="true"><fmt:formatDate value="${row.sdate}" pattern="yyyy年M月d日 HH:mm" /></display:column>
  	<display:column title="流程處理人" property="next" sortable="true"/>
  	<display:column title="處理狀態" property="status" sortable="true" />
  	<display:column><button class="btn btn-danger" onClick="$('#Oid').val(${row.Oid})" name="method:edit">處理</button></display:column>
</display:table>
</c:if>
</div>



<c:if test="${!empty fin}">
<div class="panel panel-primary">
	<div class="panel-heading">已完成工作單</div>
	<div class="panel-body">
		<p>...</p>
	</div>
	<div class="table"></div>
<display:table name="${fin}" id="row" class="table" sort="list" excludedParams="*" pagesize="30" requestURI="TaskDeal">
	<display:column title="工作單編號" property="Oid" sortable="true"/>
  	<display:column title="工作名稱" sortable="true"><a href="TaskDeal?Oid=${row.Oid}">${row.title}</a></display:column>
  	<display:column title="申請人" property="cname" sortable="true"/>  	
  	<display:column title="申請日期" sortable="true"><fmt:formatDate value="${row.edate}" pattern="yyyy年M月d日 HH:mm" /></display:column>
  	<display:column title="流程處理人" property="next" sortable="true"/>
  	<display:column title="處理狀態" property="status" sortable="true" />
  	<display:column><button class="btn btn-info" onClick="$('#Oid').val(${row.Oid})" name="method:edit">檢視</button></display:column>
</display:table>
</div>
</c:if>
</form>
</body>
</html>