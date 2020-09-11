<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>繳費資料上傳</title>
<link href="/eis/inc/css/jquery-ui.css" rel="stylesheet"/>
<link href="/eis/inc/bootstrap/plugin/bootstrap-fileinput/css/fileinput.min.css" rel="stylesheet">

<script src="/eis/inc/js/plugin/bootstrap-typeahead.js"></script>
<script src="/eis/inc/bootstrap/plugin/bootstrap-fileinput/js/fileinput.min.js"></script>
<script src="/eis/inc/bootstrap/plugin/bootstrap-fileinput/js/fileinput_locale_zh-TW.js"></script>
<link href="/eis/inc/bootstrap/plugin/silviomoreto-bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet"/>
<script src="/eis/inc/bootstrap/plugin/silviomoreto-bootstrap-select/js/bootstrap-select.min.js"></script>
<script src="/eis/inc/bootstrap/plugin/silviomoreto-bootstrap-select/js/i18n/defaults-zh_TW.min.js"></script>
<style>
.file-caption-name{width:250px;}
}
</style>
</head>
<body>    
<div class="bs-callout bs-callout-info">
<h4>繳費資料上傳</h4> <small>批次匯入請先下載最新格式，依欄位貼上資料，並按下匯入資料</small>
</div>
<form action="BankMarge" method="post" class="form-inline" enctype="multipart/form-data">
<div class="panel panel-primary">
<div class="panel-heading">新增或查詢</div>
<table class="table">	
	<tr>
		<td>
		<table>
			<tr>
				<td>
				<a class="btn btn-primary" href="jsp/sa/bankMarge/sample.xlsx"><span class="glyphicon glyphicon-cloud-download" aria-hidden="true"></span>下載格式</a>
				</td>
				<td style="padding:0px 5px 0px 5px;">
				<input name="upload" type="file" 
				data-show-preview="false" 
				data-show-upload="false" 
				id="upload" 
				class="file"/>
				<script>
				$("#upload").fileinput({
					multiple: false,			        
				    language: "zh-TW",
				    uploadUrl: "",
				    allowedFileExtensions: ["xls", "xlsx"]
				});
				</script>
				</td>			
				<td>
				<button class="btn btn-primary" id="saveTxtFile" name="method:LandUpload" type="submit"><span class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></span>匯入土銀</button>
				
				<button class="btn btn-primary" id="saveTxtFile" name="method:MediumUpload" type="submit"><span class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></span>匯入竹企</button>
				</td>
			</tr>
		</table>		
		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>