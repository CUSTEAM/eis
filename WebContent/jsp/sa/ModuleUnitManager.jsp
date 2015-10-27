<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link href="/eis/inc/css/wizard-step.css" rel="stylesheet"/>

</head>
<body>
   
<div id="dialog"></div>
<div class="alert alert alert-warning" role="alert">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<strong>選單管理及權限模擬</strong></div>

<form action="ModuleUnitManager" method="post" class="form-inline">

<div class="form-group">
    
    
	<div class="panel panel-default">
	  	<div class="panel-heading">
	    <h3 class="panel-title">單位模擬</h3>
	  	</div>
	  	<div class="panel-body">
	    
	    <select name="unit" class="form-control">
		<c:forEach items="${CODE_UNIT}" var="u">
		<option <c:if test="${u.id eq unit}">selected</c:if> value="${u.id}">${u.name}</option>
		</c:forEach>
		</select>
	    <button class="btn btn-default" type="submit" name="method:changUnit">Go!</button>
	  	</div>
	</div>   
	
</div>
  
</form>
</body>
</html>