<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

</head>
<body>    
<div class="bs-callout bs-callout-warning" id="callout-helper-pull-navbar">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<strong>刪除前學期資料</strong> 注意！停用！
</div>

<form action="DeLasTermData" method="post" class="form-horizontal" onSubmit="$.blockUI({message:null});">
<table class="table">
	<tr>
		<td>目標學期</td>
		<td>資料表</td>
		<td>資料數</td>
		<td>清除動作</td>
		<td>備註</td>
	</tr>
	
	<c:forEach items="${tables}" var="t">
	<tr>
		<td>${t.table_name}</td>
		<td>${t.table_name}</td>
		<td>${t.cnt}</td>
		<td>
		<c:if test="${t.clear eq'1'}">自動</c:if>
		<c:if test="${t.clear eq'0'}">手動</c:if>
		</td>
		<td>${t.note}</td>
	</tr>	
	</c:forEach>		
	<tr>	
		<td colspan="4">
		<button class="btn" type="button" onClick="alert('增加SYS_REUSE_TABLE資料')">新增</button>
		<button class="btn btn-danger" name="method:confirm" type="submit" 
			onClick="$.blockUI({message:null});">執行</button>
		
		</td>
	</tr>	
</table>



</form>
</body>
</html>