<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<title>識別證卡號匯入</title>
<link href="/eis/inc/css/jquery-ui.css" rel="stylesheet"/>
<link href="/eis/inc/css/wizard-step.css" rel="stylesheet"/>
<link href="/eis/inc/bootstrap/plugin/bootstrap-fileinput/css/fileinput.min.css" rel="stylesheet">

<script src="/eis/inc/js/plugin/bootstrap-typeahead.js"></script>
<script src="/eis/inc/bootstrap/plugin/bootstrap-fileinput/js/fileinput.min.js"></script>
<script src="/eis/inc/bootstrap/plugin/bootstrap-fileinput/js/fileinput_locale_zh-TW.js"></script>

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
<div class="alert alert alert-warning" role="alert">
	<button type="button" class="close" data-dismiss="alert">&times;</button>
	<strong>卡片匯入</strong> <div rel="popover" title="說明" data-content="依照格式欄位匯入資料後，學生基本資料及卡號於公用查詢→學生查詢依條件下載，教職員基本資料及卡號請洽人事單位。" data-placement="right" class="help btn btn-warning">?</div>
</div>
<form action="CardManager" method="post" enctype="multipart/form-data">
<div class="wizard-steps">
	<div><a href="#"><span>1</span> 下載格式</a></div>
	<div><a href="#"><span>2</span> 依格式編輯資料</a></div>
	<div><a href="#"><span>3</span> 匯入資料</a></div>
</div>

<table class="table">
	<tr>
		<td>
		<a class="btn btn-primary btn-lg btn-block" href="jsp/card/card.csv"><span class="glyphicon glyphicon-cloud-download" aria-hidden="true"></span>下載格式</a>	<br>			    
		<input id="upload" name="upload" multiple type="file" class="file file-loading " data-allowed-file-extensions='["csv"]'><br>
		<script>
		$("#upload").fileinput({
		    language: "zh-TW",
		    uploadUrl: "/file-upload-batch/2",
		    allowedFileExtensions: ["jpg", "png", "gif"]
		});
		</script>
					
				
				
		<button class="btn btn-primary btn-lg btn-block" name="method:saveTxtFile" type="submit"><span class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></span>匯入卡片對應資料</button> 
				
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