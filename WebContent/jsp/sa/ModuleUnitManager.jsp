<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>選單管理及權限模擬</title>

<link href="/eis/inc/css/wizard-step.css" rel="stylesheet"/>
<link href="/eis/inc/bootstrap/plugin/silviomoreto-bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet"/>
<script src="/eis/inc/bootstrap/plugin/silviomoreto-bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>
<script src="/eis/inc/js/plugin/bootstrap-typeahead.js"></script>
<script src="inc/js/autoComplete.js"></script>
</head>
<body>
   
<div id="dialog"></div>
<div class="alert alert alert-warning" role="alert">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<strong>選單管理及權限模擬</strong></div>

<form action="ModuleUnitManager" method="post" class="form-horizontal form-inline">    
   
	<div class="panel panel-primary">
	  	<div class="panel-heading">
	    <h3 class="panel-title">權限查詢與模擬</h3>
	  	</div>
	  	<div class="panel-body"></div>    
	    <table class="table">
	    	<tr>
	    		<td>
			    <select data-width="auto" name="unit" class="form-control selectpicker " data-size="auto">
					<option value=""></option>
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
			
			
				<select data-width="auto" name="module" class="form-control selectpicker " data-size="auto">
					<c:forEach items="${sysmenu}" var="u">					
					<option 
					data-content="${u.name} 
					<span class='label label-default'>${u.sname}</span> 
					<span class='label label-danger'>${u.moduleCnt}</span>" 
					<c:if test="${u.id eq unit}">selected</c:if> value="${u.id}">
					</option>					
					</c:forEach>
				</select>
			
				<input type="text" disabled placeholder="教師完整姓名或編號" class="form-control techid" name="emplOid" id="emplOid" value="${emplOid}" onClick="this.value='';" autocomplete="off" data-provide="typeahead"/>
			    <input type="text" disabled placeholder="學生完整姓名或學號" class="form-control techid" name="emplOid" id="emplOid" value="${emplOid}" onClick="this.value='';" autocomplete="off" data-provide="typeahead"/>
			    <div class="btn-group" role="group" aria-label="...">
			    <button class="form-control btn btn-danger" type="submit" name="method:chang">模擬</button>
			    <button class="form-control btn btn-default" type="submit" name="method:search">查詢</button>
			  	</div>
	  			</td>
	  		</tr>
	  		<tr>
	  			<td>
	  			
	  			
	  			</td>
	  		</tr>
	  	</table>
	</div> 
<c:if test="${!empty mods}">

<div class="panel panel-primary">

<div class="panel-heading">功能使用權限</div>

	<div class="panel-body">
    <p>...</p>
  	</div>
<table class="table">
<c:forEach items="${mods}" var="m">
	<tr>
		<td>${m.Oid}</td>
		<td>${m.name}</td>
	</tr>
</c:forEach>
</table>
</div>


</c:if>
	
	 
<script>
$('.selectpicker').selectpicker({
    //style: 'btn-info',
    //size: 8
});
</script>
</form>
</body>
</html>