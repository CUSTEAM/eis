<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>成績查詢</title>
<script src="/eis/inc/js/plugin/bootstrap-typeahead.js"></script>
<script src="http://192.192.230.167/CIS/inc/js/plugin/ckeditor/ckeditor.js"></script>
<link href="/eis/inc/bootstrap/plugin/bootstrap-fileinput/css/fileinput.min.css" rel="stylesheet">
<script src="/eis/inc/bootstrap/plugin/bootstrap-fileinput/js/fileinput.min.js"></script>
<script src="/eis/inc/bootstrap/plugin/bootstrap-fileinput/js/fileinput_locale_zh-TW.js"></script>
<script src="/eis/inc/js/plugin/jquery.chained.min.js"></script>
</head>
<body>
<div class="bs-callout bs-callout-danger">
<b>成績查詢</b>
資訊系統經相關單位授權提供資料，缺少或不足的部份請洽相關單位 <span class="glyphicon glyphicon-phone-alt" aria-hidden="true"></span>
</div>
<form action="StdScoreViewer" method="post" enctype="multipart/form-data">
<div class="panel panel-primary" style="width: 100%; margin:0px auto;">
	<div class="panel-heading">
	  <h3 class="panel-title">成績查詢</h3>
	</div>
	<table class="table">
		<tr>
			<td>
			<label>單一學生查詢</label>
			<small>請輸入學號或姓名</small>
			<div class="input-group">
			<span class="input-group-addon">學號</span>
			<input class="form-control" type="text" id="student_no" name="student_no" autocomplete="off" value="${student_no}"/>
			</div>
			</td>
		</tr>
		<tr>
			<td>				
			<label>批次學生查詢</label>
			<small>請先下載「<a href="/eis/jsp/eis/sample.xlsx"><span class="glyphicon glyphicon-cloud-download" aria-hidden="true"></span> 查詢格式</a>」編輯資料後點選瀏覽..</small>
			<input id="upload" name="fileUpload" multiple type="file" class="file-loading">
			</td>
		</tr>
		<tr>
			<td>
			<button name="method:print" class="btn btn-danger btn-lg">歷年成績查詢</button>
			</td>
		</tr>	
	</table>
</div>
</form>
<script>
$(document).ready(function(){
	
	$("#upload").fileinput({
		//uploadUrl: "#",
		multiple: true,
		uploadAsync: false,
		maxFileCount: 10,
		//previewFileIcon: '<i class="fa fa-file"></i>',
		allowedPreviewTypes: null,
		language: "zh-TW",
		layoutTemplates: {
		    main1: "{preview}\n" +
		    "<div class=\'input-group {class}\'>\n" +
		    "   <div class=\'input-group-btn\'>\n" +
		    "       {browse}\n" +
		    //"       {upload}\n" +
		    "       {remove}\n" +
		    "   </div>\n" +
		    "   {caption}\n" +
		    "</div>"
		}
		//,previewFileType: ["doc", "docx", "xls", "xlsx", "pdf", "jpg", "txt"]
		//,allowedFileExtensions: ["doc", "docx", "xls", "xlsx", "pdf", "jpg", "txt"]
	});
	
});	
	
$("#student_no").typeahead({
	//remote:"#student_no",
	source : [],
	items : 10,
	updateSource:function(inputVal, callback){			
		$.ajax({
			type:"POST",
			url:"/eis/autoCompleteStmd",
			dataType:"json",
			data:{length:10, nameno:inputVal},
			success:function(d){
				callback(d.list);
			}
		});
	}		
});
</script>
</body>
</html>