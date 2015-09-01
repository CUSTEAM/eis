<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<title>識別證卡號匯入</title>
<script src="/eis/inc/js/plugin/bootstrap-typeahead.js"></script>
<script src="/eis/inc/js/plugin/bootstrap-fileupload.js"></script>

<link href="/eis/inc/css/jquery-ui.css" rel="stylesheet"/>
<link href="/eis/inc/css/wizard-step.css" rel="stylesheet"/>
<link href="/eis/inc/css/bootstrap-fileupload.css" rel="stylesheet">
<script>  
$(document).ready(function() {
	$('.help').popover("show");
	
	
	setTimeout(function() {
		$('.help').popover("hide");
	}, 5000);
	
});


</script>  
</head>
<body>
<div class="alert">
	<button type="button" class="close" data-dismiss="alert">&times;</button>
	<strong>卡片匯入</strong> 請依格式建立匯入檔案
</div>
<form action="CardManager" method="post" class="form-horizontal" enctype="multipart/form-data">
<div class="wizard-steps">
	<div><a href="#"><span>1</span> 下載格式</a></div>
	<div><a href="#"><span>2</span> 依格式編輯資料</a></div>
	<div><a href="#"><span>3</span> 匯入資料</a></div>
</div>

<table class="table">
	
	<tr>
		<td>
		
		<div class="fileupload fileupload-new" data-provides="fileupload" style="float:left;">    
		<a class="btn" href="jsp/card/card.csv">下載格式</a>		    
			<div class="input-append">			
				<div class="uneditable-input">
					<i class="icon-file fileupload-exists"></i> 
					<span class="fileupload-preview"></span>
				</div>	
				<span class="btn btn-file btn-info">					
					<span class="fileupload-new">選擇檔案</span>
					<span class="fileupload-exists">重選檔案 </span>
					<input type="file" name="upload"/>
				</span>				
				<a href="#" class="btn fileupload-exists btn-info" data-dismiss="fileupload">取消</a>
			</div>
		</div>
		&nbsp;
		<button class="btn btn-danger" name="method:saveTxtFile" type="submit">匯入卡片對應資料</button>
		<div rel="popover" title="說明" data-content="依照格式欄位匯入資料後，學生基本資料及卡號於公用查詢→學生查詢依條件下載，教職員基本資料及卡號請洽人事單位。" data-placement="right" class="help btn btn-warning">?</div>
		</td>
	</tr>
</table>

<c:if test="${!empty fail || !empty success}">



<table class="table">
	<tr class="error">
		<td>無法建立</td>
	</tr>
	<c:forEach items="${fail}" var="f">
	<tr>
		<td>${f[0]} : ${f[1]}</td>
	</tr>
	</c:forEach>
	<tr class="success">
		<td>已建立</td>
	</tr>
	<c:forEach items="${success}" var="s">
	<tr>
		<td>${s[0]} : ${s[1]}</td>
	</tr>
	</c:forEach>
</table>
</c:if>








</form>

</body>
</html>