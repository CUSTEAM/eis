<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link href="/eis/inc/css/wizard-step.css" rel="stylesheet"/>
<link href="/eis/inc/bootstrap/plugin/silviomoreto-bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet"/>
<script src="/eis/inc/bootstrap/plugin/silviomoreto-bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>
</head>
<body>
   
<div id="dialog"></div>
<div class="alert alert alert-warning" role="alert">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<strong>選單管理及權限模擬</strong></div>

<form action="ModuleUnitManager" method="post" class="form-inline">


    
   
	<div class="panel panel-primary">
	  	<div class="panel-heading">
	    <h3 class="panel-title">單位模擬</h3>
	  	</div>
	  	<div class="panel-body">
	    
	    
	    
	    
	    
	    <select data-width="auto" name="unit" class="form-control selectpicker " data-size="auto">
		<c:forEach items="${CODE_UNIT}" var="u">
		<c:if test="${u.moduleCnt>0}">
		
		
		<option 
		data-content="${u.name} 
		<span class='label label-default'>${u.sname}</span> 
		<span class='label label-danger'>${u.moduleCnt}</span>" 
		<c:if test="${u.id eq unit}">selected</c:if> value="${u.id}">
		</option>
		
		</c:if>
		</c:forEach>
		</select>
	    <button class="form-control btn btn-danger" type="submit" name="method:changUnit">模擬</button>
	  	</div>
	</div>   
	
	<div class="panel panel-primary">
	  	<div class="panel-heading">
	    <h3 class="panel-title">選單管理</h3>
	  	</div>
	  	<div class="panel-body">
	    
	    
	  	</div>
	</div>   
<script>
$('.selectpicker').selectpicker({
    //style: 'btn-info',
    //size: 8
});
</script>
</form>
</body>
</html>