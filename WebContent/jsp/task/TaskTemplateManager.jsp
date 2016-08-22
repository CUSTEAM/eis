<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工作單項目管理</title>
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
		<strong>工作單項目管理</strong><input type="hidden" name="upOid" id="upOid" value="100" />
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
				
				<div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
					<div class="panel-body">
					
					<div class="accordion-inner">
						<input type="hidden" name="Oid" id="Oid" /> 
						<input type="hidden" name="unit" value="${unit}" />
						<div class="input-group">
      						<div class="input-group-addon">工作名稱</div>
    						<input type="text" class="form-control" name="title" placeholder="例如：維修單申請、資料申請" />
						</div>
						<br>
						<div class="input-group">
      						<div class="input-group-addon">申請單位主管確認</div> 
      						<select name="ensure" class="form-control">
								<option value="0">不需要</option>
								<option value="1">需要</option>
							</select>
						</div>
						<br>
						<label>申請書範本 <small><abbr title="Ctrl+滑鼠點選">(按住Ctrl鍵可選擇多個檔案)</abbr></small></label>
			  			<input id="upload" name="fileUpload" multiple type="file" class="file-loading">
						<br>
						<label>申請說明<small>(請建立說明格式供申請者參考)</small></label>
						<textarea cols="80" name="template" rows="10"></textarea>
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
				<div id="collapse${o.Oid}" class="panel-collapse collapse <c:if test="${param.Oid eq o.Oid}">in</c:if>"
					role="tabpanel" aria-labelledby="headingTwo">
					<div class="panel-body">
					<input type="hidden" name="Oid" id="Oid${o.Oid}" value="" /> 
					<input type="hidden" name="unit" value="${unit}" />							
					<div class="input-group">
     						<div class="input-group-addon"><A name="${o.Oid}"></A>工作名稱</div>
   						<input type="text" class="form-control" name="title" value="${o.title}" />
					</div>
					<br>
					<div class="input-group">
      					<div class="input-group-addon">申請單位主管確認</div> 
      						<select name="ensure" class="form-control">
								<option <c:if test="${o.ensure==0}">selected</c:if> value="0">不需要</option>
								<option <c:if test="${o.ensure==1}">selected</c:if> value="1">需要</option>
							</select>
						</div>
					<br>
					<div id="files${o.Oid}">
					<c:forEach items="${o.files}" var="f" varStatus="i">					
					<div class="btn-group">
						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">範本 ${i.index+1}</button>
					  
						<ul class="dropdown-menu">
					    <li><a href="/eis/getFtpFile?file=${f.file_name}&path=${f.path}"><span class="glyphicon glyphicon-floppy-save" aria-hidden="true"></span> 下載</a>
					    <li><a href="/eis/getFtpFile?file=${f.file_name}&path=${f.path}"><span class="glyphicon glyphicon-floppy-save" aria-hidden="true"></span> 檢視</a>
					    <li role="separator" class="divider"></li>
					    <li><a href="/eis/getFtpFile?file=${f.file_name}&path=${f.path}"><span class="glyphicon glyphicon-floppy-save" aria-hidden="true"></span> 刪除</a>
					  	</ul>
					</div>
					</c:forEach>
					<div class="btn-group" onMouseOver="$('#upOid').val(${o.Oid})">
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
function callback(d){	
	alert(d.info.path)
}

var Oid;	
$(".fs").fileinput({
    uploadUrl: "putTaskFile", 
    uploadExtraData: function () {
    	Oid=$("#upOid").val();
        return {
        	Oid:$("#upOid").val()
        }
    },
    language: "zh-TW",
    showPreview: false,
    showCaption: false,
    showUploadedThumbs:true
    //maxFileCount: 5
	})
	
	.on('filebatchpreupload', function(event, data) {
		window.location.href = "TaskTemplateManager?Oid="+Oid;
		//window.location.replace("TaskTemplateManager?Oid="+Oid+"#"+Oid);
});


/*CKEDITOR.on('instanceReady', function (e) {
	  $('.cke_top').css('background','#428bca');
	  $('.cke_inner').css('background','transparent');
});*/

CKEDITOR.replaceAll(function(textarea, config) { 
	//config.uiColor = "#428bca",
	//config.uiColor = "#428b00",
	//config.skin = 'office2013',
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

$( document ).ready(function() {
	$('#collapseOne').collapse();
});


</script>
	</form>
</body>
</html>