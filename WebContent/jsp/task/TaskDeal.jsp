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
<div class="alert alert alert-warning" role="alert">
    <button type="button" class="close" data-dismiss="alert">&times;</button>
    <strong>工作單處理</strong>    
</div>
<form action="TaskDeal" method="post">
<input type="hidden" id="Oid" name="Oid" />
<c:if test="${!empty que}">  
<div class="tree well">  
<display:table name="${que}" id="row" class="table" sort="list" excludedParams="*" pagesize="10" requestURI="TaskDeal">
	<display:column title="工作單編號" property="Oid" sortable="true"/>
  	<display:column title="工作名稱" sortable="true"><a href="TaskDeal?Oid=${row.Oid}">${row.title}</a></display:column>
  	<display:column title="申請人" property="cname" sortable="true"/>  	
  	<display:column title="申請日期" sortable="true"><fmt:formatDate value="${row.sdate}" pattern="yyyy年M月d日 HH:mm" /></display:column>
  	<display:column title="流程處理人" property="next" sortable="true"/>
  	<display:column title="處理狀態" property="status" sortable="true" />
  	<display:column><button class="btn btn-danger" onClick="$('#Oid').val(${row.Oid})" name="method:edit">處理</button></display:column>
</display:table>
</div>
</c:if>


<c:if test="${!empty fin}">
<div class="tree well">
<display:table name="${fin}" id="row" class="table" sort="list" excludedParams="*" pagesize="10" requestURI="TaskDeal">
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