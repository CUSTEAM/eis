<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>中長程計劃管理</title>
<link href="/eis/inc/css/wizard-step.css" rel="stylesheet"/>
<link href="/eis/inc/bootstrap/plugin/bootstrap-fileinput/css/fileinput.min.css" rel="stylesheet">
<script src="/eis/inc/bootstrap/plugin/bootstrap-fileinput/js/fileinput.min.js"></script>
<script src="/eis/inc/bootstrap/plugin/bootstrap-fileinput/js/fileinput_locale_zh-TW.js"></script>
<script src="/eis/inc/js/plugin/jquery.chained.min.js"></script>
</head>
<body>
<div class="bs-callout bs-callout-warning" id="callout-helper-pull-navbar">
	<button type="button" class="close" data-dismiss="alert">&times;</button>
	中長程計畫管理
	
</div>

<div class="wizard-steps">
	<div><a href="#"><span>1</span> 下載格式</a></div>
	<div><a href="#"><span>2</span> 依格式編輯資料</a></div>
	<div><a href="#"><span>3</span> 匯入資料</a></div>
</div><br><br>
<form action="StrategsManager" method="post" enctype="multipart/form-data">
<div class="panel panel-primary" style="width: 100%; margin:0px auto;">
	<div class="panel-heading">
	  <h3 class="panel-title">資料上傳</h3>
	</div>
	<table class="table">
		<tr>
			<td>
				<table >
					<tr>
						<td style="padding:0px 5px 0px 0px;">
						<select name="codeUnit" class="selectpicker show-tick" data-size="10" data-header="請選擇填報單位" title="請選擇填報單位">
						  	<c:forEach items="${allUnit}" var="u">
						  		<option data-divider="true"></option>
						  		<option <c:if test="${codeUnit eq u.id}">selected</c:if> data-icon="glyphicon glyphicon-chevron-down" value="${u.id}">${u.name}</option>
						  		<c:forEach items="${u.units}" var="s">
						  		<option <c:if test="${codeUnit eq s.id}">selected</c:if> value="${s.id}" data-subtext=" ${u.name}">${s.name}</option>
						  		</c:forEach>
						  	</c:forEach>
						</select>
						</td>					
						<td style="padding:0px 5px 0px 0px;">						
						<select name="year" class="selectpicker show-tick" data-size="10" data-header="請選擇填報年度" title="請選擇填報年度">
							<c:forEach begin="${school_year}" end="${school_year+1}" varStatus="i">
							<option <c:if test="${year eq i.index}">selected</c:if> value="${i.index}">${i.index} 年度</option>
							</c:forEach>
						</select>						
						</td>
						<td style="padding:0px 5px 0px 0px;">
						<div class="btn-group" role="group" aria-label="...">
  							<button name="method:search" class="btn btn-primary"><span class="glyphicon glyphicon-search" aria-hidden="true"></span> 查詢</button>		
							<button name="method:print" class="btn btn-default"><span class="glyphicon glyphicon-print" aria-hidden="true"></span> 列印</button>
						</div>
						
						</td>
						<td style="padding:0px 5px 0px 0px;" width="300"><input id="upload" name="upload" multiple type="file" data-show-preview="false" class="file-loading"></div></td>
						<td style="padding:0px 5px 0px 0px;">
						<button name="method:upload" class="btn btn-danger"><span class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></span> 上傳檔案</button>		
						<a class="btn btn-default" href="jsp/task/tmp.xlsx">下載格式</a>
						</td>
						
					</tr>
				</table>				
			</td>
		</tr>
	</table>
</div>


<c:if test="${!empty prjs}">
<br>



<div class="panel panel-primary" style="width: 100%; margin:0px auto;">
	<div class="panel-heading">
	  <h3 class="panel-title">查詢結果</h3>
	</div>
	<display:table export="false" name="${prjs}" id="row" class="table table-condensed" sort="list"  requestURI="StrategsManager?method=search">
	  	
	  	<display:column style="width:250px; white-space:nowrap;" title="單位名稱" property="name" sortable="true"/>
	  	<display:column style="width:80px; white-space:nowrap;" title="年度" property="year" sortable="true" />
	  	
	  	<display:column style="width:100px; white-space:nowrap;" title="資料筆數" property="cnt" sortable="true"/>
	  	<display:column style="width:100px; white-space:nowrap;" title="建立者" property="username" sortable="true"/>	  	
	  	<display:column title="建立時間" property="editdate" sortable="true"/>
	  	
	</display:table>
</div>






</c:if>






</form>  
<script>
$(document).ready(function(){
	
	$("#upload").fileinput({
        showUpload: false,
        dropZoneEnabled: false,
		language: "zh-TW",
		layoutTemplates: {
		    main1: "{preview}\n" +
		    "<div class=\'input-group {class}\'>\n" +
		    "   <div class=\'input-group-btn\'>\n" +
		    "       {browse}\n" +
		    "       {remove}\n" +
		    "   </div>\n" +
		    "   {caption}\n" +
		    "</div>"
		}
		//,previewFileType: ["doc", "docx", "xls", "xlsx", "pdf", "jpg", "txt"]
		//,allowedFileExtensions: ["doc", "docx", "xls", "xlsx", "pdf", "jpg", "txt"]
	});
	
});	
</script>
</body>
</html>