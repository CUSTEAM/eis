<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<!DOCTYPE html>
<html>
<head>
	<script src="http://192.192.230.167/CIS/inc/js/plugin/ckeditor/ckeditor.js"></script>
	<link href="/eis/inc/bootstrap/plugin/bootstrap-fileinput/css/fileinput.min.css" rel="stylesheet">
	<script src="/eis/inc/bootstrap/plugin/bootstrap-fileinput/js/fileinput.min.js"></script>
	<script src="/eis/inc/bootstrap/plugin/bootstrap-fileinput/js/fileinput_locale_zh-TW.js"></script>
	<script src="/eis/inc/js/plugin/multiselect.min.js"></script>
	<title>工作單處理</title>
	<link rel="stylesheet" href="/eis/inc/css/wizard-step.css" />
</head>
<body>  

<form action="TaskManager" method="post" enctype="multipart/form-data">
<div class="bs-callout bs-callout-danger">
<c:if test="${!empty TaskInfo.edate}"><p><span class="label label-default">已結案</span></p></c:if>
<h4>${TaskInfo.title}</h4>
<p>${TaskInfo.cname} ${TaskInfo.student_no} ${TaskInfo.student_name} <small>${TaskInfo.sdate}</small></p>
<p>${TaskInfo.onote}</p>
<div class="btn-group" role="group" >
	<c:forEach items="${TaskFile}" var="f" varStatus="i">
  	<a href="/eis/getFtpFile?path=${f.path}&file=${f.file_name}" class="btn btn-default">附件${i.index+1}</a>  	
  	</c:forEach>  
</div>
<button onClick="confirm('確定結案?');" <c:if test="${!empty TaskInfo.edate}">disabled</c:if> class="btn btn-danger" name="method:closeTask">結案</button>
<a href="TaskManager" class="btn btn-primary">返回列表</a>

</div>
<c:if test="${!empty TaskInfo.note}">
<div class="bs-callout bs-callout-warning">
<h4>${TaskInfo.fName}, ${TaskInfo.sdate}</h4>
<p>${TaskInfo.note}</p>
<div class="btn-group" role="group" >
	<c:forEach items="${histFile}" var="f" varStatus="i">
  	<a href="/eis/getFtpFile?path=${f.path}&file=${f.file_name}" class="btn btn-default">附件${i.index+1}</a>  	
  	</c:forEach>  
</div>
</div>
</c:if>



<input type="hidden" name="Oid" value="${TaskInfo.Oid}" />
<input type="hidden" name="Task_oid" value="${TaskInfo.taskOid}" />
<input type="hidden" name="Task_hist_oid" value="${TaskInfo.Task_hist_oid}" />
<input type="hidden" id="close" name="close" />
<c:if test="${!empty TaskHistInfo}">
<div class="panel panel-primary">
	<div class="panel-heading">單位</div>
	<table class="table table-hover">
	<tr>
		<th></th>
		<th></th>
		<th>合作單位</th>
		<th>附件</th>
		<th>送出</th>
		<th nowrap>回覆</th>
	</tr>
    <c:forEach items="${TaskHistInfo}" var="h">
	<tr>
		<td nowrap width="80">		
		<c:if test="${h.open eq '0'}"><span class="label label-default">已結案</span></c:if>
		<c:if test="${h.open eq '1'}"><span class="label label-danger">未結案</span></c:if>		
		</td>		
		<td nowrap>
			
		<c:if test="${empty h.feedback}"><a disabled class="btn btn-default">檢視</a></c:if>
		<c:if test="${!empty h.feedback}"><a href="#taskAppInfo" onClick="getTaskHist(${h.Oid});" data-toggle="modal"  class="btn btn-primary">檢視</a></c:if>
		<button onClick="$('#close').val(${h.Oid});return(confirm('確定結案?'));" name="method:close" class="btn btn-default">結案</button>
		
		</td>
		
		<td nowrap>${h.cname}<c:if test="${!empty h.unit}">【${h.unit}】</c:if></td>
		<td width="100%">
		<div class="btn-group" role="group" >
			<c:forEach items="${h.files}" var="f" varStatus="i">
		  	<a href="/eis/getFtpFile?path=${f.path}&file=${f.file_name}" class="btn btn-default">附件${i.index+1}</a>  	
		  	</c:forEach>  
		</div>
		<div class="btn-group" role="group" >
			<c:forEach items="${h.bFiles}" var="f" varStatus="i">
		  	<a href="/eis/getFtpFile?path=${f.path}&file=${f.file_name}" class="btn btn-primary">回件${i.index+1}</a>  	
		  	</c:forEach>  
		</div>		
		</td>
		<td width="80" nowrap>${fn:substring(h.sdate, 5, 10)}</td>
		<td width="80" nowrap>${fn:substring(h.edate, 5, 10)}</td>		
	</tr>
	</c:forEach>
  	</table>
</div>
</c:if>
</form>


<!-- Modal -->
<div class="modal fade" id="taskAppInfo">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
			  <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			  <h4 class="modal-title" id="modelTitle"></h4>
			</div>
			<div class="modal-body" id="appInfo">
			  <p>Loading..</p>
			</div>
			<div class="modal-footer">
			  <button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
			  
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<script>


CKEDITOR.config.toolbar =[
	['Bold','Italic','-','NumberedList','BulletedList','-','Link','Unlink' ],
	['FontSize','TextColor','BGColor']
];

CKEDITOR.replaceAll("note");

function getTaskHist(Oid){
	//var files;
	$.ajax({
		  url: "/eis/getTaskInfo",
		  type: "get", //send it through get method
		  data:{ 		    
		    histOid: Oid, 		    
		  },
		  success: function(data) {
			  d=data.list[0];
			  
			  $("#modelTitle").text(d.cname+", "+d.edate);
			  info="<p>";
			  if(d.files!=null){
				  info+="<div class='btn-group' role='group'>"
				  for(i=0; i<d.files.length; i++){
					  if(d.files[i]!=null){
						  info+="<a href='/eis/getFtpFile?path="+d.files[i].path+"&file="+d.files[i].file_name+"' class='btn btn-default'>附件"+(i+1)+"</a>";
					  }					  
				  }	
				  info+="</div>"
			  }
			  if(d.bFiles!=null){
				  info+="&nbsp;<div class='btn-group' role='group'>"
				  for(i=0; i<d.bFiles.length; i++){
					  if(d.bFiles[i]!=null){
						  info+="<a href='/eis/getFtpFile?path="+d.bFiles[i].path+"&file="+d.bFiles[i].file_name+"' class='btn btn-primary'>回件"+(i+1)+"</a>";
					  }					  
				  }	
				  info+="</div>"
			  }
			  info+="</p>";
			  
			  $("#appInfo").html(info);	
			  $("#appInfo").append(d.feedback);	
		  },
		  error: function(xhr) {
		    //Do Something to handle error
		  }
	});
}

$(document).ready(function(){		
	$('#multiselect').multiselect();
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
</script>
</body>
</html>