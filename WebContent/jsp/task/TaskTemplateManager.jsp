<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="/eis/inc/js/plugin/bootstrap-typeahead.js"></script>
<script src="/eis/inc/js/plugin/jquery-ui.js"></script>
<script src="/eis/inc/js/plugin/bootstrap-tooltip.js"></script>
<script src="/eis/inc/js/plugin/jquery.chained.min.js"></script>
<!-- link href="/eis/inc/css/wizard-step.css" rel="stylesheet"/-->
<link href="/eis/inc/bootstrap/plugin/bootstrap-fileinput/css/fileinput.min.css" rel="stylesheet">
<script src="/eis/inc/bootstrap/plugin/bootstrap-fileinput/js/fileinput.min.js"></script>
<script src="/eis/inc/bootstrap/plugin/bootstrap-fileinput/js/fileinput_locale_zh-TW.js"></script>
<script
	src="http://192.192.230.167/CIS/inc/js/plugin/ckeditor/ckeditor.js"></script>
</head>
<body>
	<div id="dialog"></div>
	<div class="bs-callout bs-callout-info" id="callout-helper-pull-navbar">
		<strong>單位工作管理</strong>
	</div>
	<form action="TaskTemplateManager" method="post" class="form-horizontal" enctype="multipart/form-data">

		<div class="panel-group" id="accordion" role="tablist"
			aria-multiselectable="true">
			<div class="panel panel-primary">
				<div class="panel-heading" role="tab" id="headingOne">
					<h4 class="panel-title">
						<a role="button" data-toggle="collapse" data-parent="#accordion"
							href="#collapseOne" aria-expanded="true"
							aria-controls="collapseOne"> 建立新服務 </a>
					</h4>
				</div>
				<div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
					<div class="panel-body">
					
					<div class="accordion-inner">
						<input type="hidden" name="Oid" /> <input type="hidden" name="unit" value="${unit}" />
						<div class="input-group">
      						<div class="input-group-addon">工作名稱</div>
    						<input type="text" class="form-control" name="title" placeholder="例如：維修單申請、資料申請" />
						</div>
						<br>
						<div class="input-group">
      						<div class="input-group-addon">申請對象</div> 
      						<select name="open" class="form-control">
								<option value="e">教職員</option>
								<option value="s">學生</option>
							</select>
						</div>
						<br>
						<label>申請<small><abbr title="Ctrl+滑鼠點選">(按住Ctrl鍵可選擇多個檔案)</abbr></small></label>
			  			<input id="upload" name="fileUpload" multiple type="file" class="file-loading">
						<br>
						<label>申請說明</label>
						<textarea cols="80" name="template" rows="10">自訂申請書欄位例如:<br>狀況描述:<br>發生時間:<br>發生地點:</textarea>
						<br>
						<button class="btn btn-danger" name="method:add" type="submit">建立</button>
					</div>
					
					</div>
				</div>
			</div>
			<c:forEach items="${ot}" var="o">
			<div class="panel panel-primary">
				<div class="panel-heading" role="tab" id="headingTwo">
					<h4 class="panel-title">
						<a class="collapsed" role="button" data-toggle="collapse"
						data-parent="#accordion" href="#collapse${o.Oid}" onClick="$('#Oid${o.Oid}').val('${o.Oid}')"
						aria-expanded="false" aria-controls="collapseTwo">
						${o.title}
						</a>
					</h4>
				</div>
				<div id="collapse${o.Oid}" class="panel-collapse collapse"
					role="tabpanel" aria-labelledby="headingTwo">
					<div class="panel-body">
					<input type="hidden" name="Oid" id="Oid${o.Oid}" value="" /> 
					<input type="hidden" name="unit" value="${unit}" />							
					<div class="input-group">
     						<div class="input-group-addon">工作名稱</div>
   						<input type="text" class="form-control" name="title" value="${o.title}" />
					</div>
					<br>
					<div class="input-group">
     				<div class="input-group-addon">申請對象</div> 
     					<select name="open" class="form-control">
							<option <c:if test="${o.open eq'e'}">selected</c:if> value="e">教職員</option>
							<option <c:if test="${o.open eq's'}">selected</c:if> value="s">學生</option>
							<option <c:if test="${o.open eq'n'}">selected</c:if> value="n">暫停申請</option>
						</select>
					</div>
					<br>
					<div id="files${o.Oid}">
					<c:forEach items="${o.files}" var="f" varStatus="i">					
					<div class="btn-group">
						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">附件 ${i.index+1}</button>
					  
						<ul class="dropdown-menu">
					    <li><a href="/eis/getFtpFile?file=${f.file_name}&path=${f.path}"><span class="glyphicon glyphicon-floppy-save" aria-hidden="true"></span> 下載</a>
					    <li><a href="/eis/getFtpFile?file=${f.file_name}&path=${f.path}"><span class="glyphicon glyphicon-floppy-save" aria-hidden="true"></span> 檢視</a>
					    <li role="separator" class="divider"></li>
					    <li><a href="/eis/getFtpFile?file=${f.file_name}&path=${f.path}"><span class="glyphicon glyphicon-floppy-save" aria-hidden="true"></span> 刪除</a>
					  	</ul>
					</div>
					</c:forEach>
					<div class="btn-group">
					<input type="file" name="file" class="file-loading fs">
					</div>
					</div>
					
					
					<br>
							
					<label>申請說明</label>
					<textarea cols="80" name="template" rows="10">${o.template}</textarea>
					<br>
					<button class="btn btn-warning" name="method:edit" type="submit">修改</button>
					</div>
				</div>
			</div>
			</c:forEach>
		</div>




   


		
<script>
$(".fs").fileinput({
    uploadUrl: "putTaskFile", 
    uploadAsync: false,
    language: "zh-TW",
    //showUpload :false,
    showPreview: false,
    showCaption: false
    //maxFileCount: 5
}).on('filebatchuploadsuccess', function(event, data) {
    alert(d);
});
CKEDITOR.replaceAll(function(textarea, config) { //uiColor: '#14B8C4',
	config.toolbar = [
			[ 'Bold', 'Italic', '-', 'NumberedList','BulletedList', '-', 'Link', 'Unlink' ],
			[ 'FontSize', 'TextColor', 'BGColor' ] ]
});
	
$("#upload").fileinput({
	//uploadUrl: "#",
	multiple: true,
	uploadAsync: false,
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
	//allowedFileExtensions: ["csv", "txt"]
});

</script>
	</form>
</body>
</html>