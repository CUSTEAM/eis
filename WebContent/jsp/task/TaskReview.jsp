<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="/eis/inc/js/plugin/jquery-ui.js"></script>
	<script src="/eis/inc/js/plugin/jquery-ui-timepicker-addon.js"></script>
	<script src="/eis/inc/js/develop/stdinfo.js"></script>
	<script src="/eis/inc/js/develop/timeInfo.js"></script>
	<script src="/eis/inc/js/plugin/jquery.tinyMap.min.js"></script>
	
	<link href="/eis/inc/css/jquery-ui.css" rel="stylesheet"/>
	
	
	<script src="/eis/inc/js/plugin/multiselect.min.js"></script>
	<title>意見反應單管理</title>
	<style>
    .modal-lg {
    /* new custom width*/
    width: 90%;
     /*must be half of the width, minus scrollbar on the left (30px)*/
    /*margin-right: 45%;
    margin-left: -45%;*/
}
</style>
</head>
<body>
<div class="bs-callout bs-callout-info" id="callout-helper-pull-navbar">
    <strong>意見反應單管理</strong>    
</div>
<form action="TaskReview" method="post" class="form-inline" enctype="multipart/form-data">
<input type="hidden" id="histOid" name="Oid" />

<div class="panel panel-primary">
	<div class="panel-heading">查詢或新增意見反應</div>
	<div class="panel-body">
	
	<table>
		<tr>
			<td style="padding:5px;">
				<div class="input-group">
			  	<span class="input-group-addon" id="basic-addon1">自</span>
			  	<input type="text" class="form-control date" value="${begin}" name="begin" placeholder="開始日期" />
				</div>
				<div class="input-group">
				  	<span class="input-group-addon" id="basic-addon1">至</span>
				  	<input type="text" class="form-control date" value="${end}" name="end" placeholder="結束日期" />
				</div>
			</td>
			<td style="padding:5px;">
				
				<button class="btn btn-default" name="method:search"><span class="glyphicon glyphicon-search"></span> 依日期範圍查詢</button>
				
				
			</td>
		</tr>
	</table>		
	</div>	
</div>







<!-- Modal -->
<div class="modal fade" id="stdInfo" tabindex="-2" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">×</button>
		<h3 id="stdNameNo"></h3>
      </div>
      <div class="modal-body" id="info"></div>
      <center><div class="modal-body" style="width:80%; height:400px;" id="stdMap"></div></center>
	<div class="modal-footer">
		<button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">關閉</button>
	</div>
    </div>
  </div>
</div>

</form>
<script>
CKEDITOR.config.toolbar =[
	['Bold','Italic','-','NumberedList','BulletedList','-','Link','Unlink' ],
	['FontSize','TextColor','BGColor']
];

$(document).ready(function(){	
	$(".date").datepicker({
		changeMonth: true,
		changeYear: true,
		//minDate: '@minDate'
		yearRange: "-100:+0"
		//showButtonPanel: true,
		//dateFormat: 'yy-MM-dd'
	});
	CKEDITOR.replaceAll("note");
	
	$(".optgroup").multiselect();
	$(".file-loading").fileinput({
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

function getUnit(pid){	
	$(".optgroup1").html("");
	for(i=0; i<units.length; i++){
		if(units[i].pid==pid&&units[i].cname!="")
		$(".optgroup1").append("<option value='"+units[i].idno+"'>"+"【"+units[i].name+"】"+units[i].cname+"</option>" );
		
	}		
}
units=[<c:forEach items="${ulist}" var="l"><c:forEach items="${l.units}" var="u">{pid:"${u.pid}", idno:"${u.idno}", name:"${u.name}", cname:"${u.cname}"},</c:forEach></c:forEach>]
</script>
</body>
</html>