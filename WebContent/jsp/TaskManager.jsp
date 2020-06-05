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
	<!-- script src="/eis/inc/bootstrap/plugin/bootstrap-multiselect/bootstrap-multiselect.js"></script>
	<link href="/eis/inc/bootstrap/plugin/bootstrap-multiselect/bootstrap-multiselect.css" rel="stylesheet"/-->
	<link href="/eis/inc/css/jquery-ui.css" rel="stylesheet"/>
	
	<script src="http://192.192.230.167/CIS/inc/js/plugin/ckeditor/ckeditor.js"></script>
	<link href="/eis/inc/bootstrap/plugin/bootstrap-fileinput/css/fileinput.min.css" rel="stylesheet">
	<script src="/eis/inc/bootstrap/plugin/bootstrap-fileinput/js/fileinput.min.js"></script>
	<script src="/eis/inc/bootstrap/plugin/bootstrap-fileinput/js/fileinput_locale_zh-TW.js"></script>
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
<form action="TaskManager" method="post" class="form-horizontal" enctype="multipart/form-data">
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
				</td>
			<td style="padding:5px;">
				<div class="input-group">
				  	<span class="input-group-addon" id="basic-addon1">至</span>
				  	<input type="text" class="form-control date" value="${end}" name="end" placeholder="結束日期" />
				</div>
			</td>
			<td style="padding:5px;">
				<div class="btn-group btn-default">
				<button class="btn btn-default" name="method:search"><span class="glyphicon glyphicon-search"></span> 依日期範圍查詢</button>
				<a class="btn btn-danger" href="#TaskAddModal" data-toggle="modal"><span class="glyphicon glyphicon-wrench"></span> 建立新工作</a>
				</div>
			</td>
		</tr>
	</table>		
	</div>	
</div>




<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
	
	<c:if test="${!empty tasks}"> 
	<input type="hidden" name="Task_oid" id="Task_oid"/>
	<div class="panel panel-primary">
	    <div class="panel-heading" role="tab" id="headingOne">
	      	<h4 class="panel-title">
	        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" 
	        aria-expanded="true" aria-controls="collapseOne">發出的意見</a>	        
	      	</h4>
	    </div>
	    <div class="panel-body" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" 
	        aria-expanded="true" aria-controls="collapseOne">
	    	請點選標題檢視各單位的回覆並個別結案<br><small>也可將工作單直接結案, 或是與其他相關單位合作</small>	    
	    </div>
    	<div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
	    <table class="table table-striped table-hover">			
			<tr>
				<th nowrap>#</th>
				<th nowrap>標題</th>
				<th nowrap>伙伴</th>
				<th nowrap>回覆</th>
				<th nowrap>完成</th>
				<th nowrap>開始日期</th>
				<th nowrap>完成日期</th>
			</tr>
			<c:forEach items="${tasks}" var="row">
			<tr>
				<td nowrap><button style="display:none;"id="editTask${row.Oid}" name="method:ediTask"></button>${row.Oid}</td>
				<td width="100%">
				<c:if test="${!empty row.edate}"><small><span class="label label-default">已結案</span></small></c:if>
				<c:if test="${empty row.edate}"><small><span class="label label-danger">未結案</span></small></c:if>
				<a href="javascript:" onClick="$('#Task_oid').val(${row.Oid}); $('#editTask${row.Oid}').click();">${row.title}</a></td>
				<td nowrap>${row.b+row.n}</td>
				<td nowrap>${row.b}</td>
				<td nowrap>${row.o}</td>
				<td nowrap><fmt:formatDate value="${row.sdate}" pattern="M月d日" /></td>
				<td nowrap>
				<c:if test="${empty row.edate}">尚未結案</c:if>
				<fmt:formatDate value="${row.edate}" pattern="M月d日" />
				</td>
			</tr>			
			</c:forEach>
		</table>
    	</div>
  	</div>
  	</c:if>
  	
  	<c:if test="${!empty myTasks}"> 
  	<div class="panel panel-primary">
	    <div class="panel-heading" role="tab" id="headingTwo">
	      	<h4 class="panel-title">
	        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" 
	        href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">接收到的意見</a>
	      	</h4>
	    </div>
	    <div class="panel-body" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" 
	        aria-expanded="true" aria-controls="collapseTwo">
	    	請點選標題回覆處理結果<br><small>或是與其他相關單位合作</small>	        
	    </div>
    	<div id="collapseTwo" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingTwo">			
			<table class="table table-striped table-hover">			
			<tr>
				<th style="width:1px;white-space:nowrap;">#</th>
				<th nowrap>標題</th>
				<th nowrap>伙伴</th>
				<th nowrap>回覆</th>
				<th nowrap>完成</th>
				<th nowrap>發佈者</th>
				<th nowrap>分送者</th>
				<th nowrap>接收日期</th>
				<th nowrap>回覆日期</th>
			</tr>
			<c:forEach items="${myTasks}" var="row">
			<tr>
				<td style="width:1px;white-space:nowrap;"><button style="display:none;"id="edit${row.Oid}" name="method:edit"></button>${row.Oid}</td>
				<td style="width:100%;">
				<c:if test="${row.open eq'0'}"><small><span class="label label-default">已結案</span></small></c:if>
				<c:if test="${row.open eq'1'}"><small><span class="label label-danger">未結案</span></small></c:if>
				<a href="javascript:" onClick="$('#histOid').val(${row.Oid}); $('#edit${row.Oid}').click();">${row.title}</a>
				</td>
				<td nowrap>${row.b+row.n}</td>
				<td nowrap>${row.b}</td>
				<td nowrap>${row.o}</td>
				<td style="width:100px; white-space:nowrap;">
				<c:if test="${!empty row.student_name}">				
				${row.student_name}
				<div class="btn-group btn-default">
					<button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown" aria-haspopup="true">學生</button>
					<ul class="dropdown-menu">
						<li><a href="#stdInfo" data-toggle="modal" onClick="getStudentTime('${row.userid}', '${row.student_name}')">本學期課表</a></li>
						<li><a href="#stdInfo" data-toggle="modal" onClick="getDilgInfo('${row.userid}', '${row.student_name}', '')">所有課程缺課記錄</a></li>
						<li><a href="#stdInfo" data-toggle="modal" onClick="getStdContectInfo('${row.userid}', '${row.student_name}')">連絡資訊</a></li>
						<li><a href="#stdInfo" data-toggle="modal" onClick="getStdScoreInfo('${row.userid}', '${row.student_name}')">歷年成績</a></li>
						<li><a href="/CIS/Portfolio/ListMyStudents.do" target="_blank">學習歷程檔案</a></li>									
					</ul>
				</div>
				</c:if>
				<c:if test="${!empty row.cname}">${row.cname}
			  	<div class="btn-group btn-default">
					<button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown" aria-haspopup="true">教職員</button>
					<ul class="dropdown-menu">						
						<li><a target="_blank">${row.unit}</a></li>									
					</ul>
				</div>			  	
			  	</c:if>				
				</td>
				<td style="width:100px; white-space:nowrap;">${row.pname}</td>
				<td style="width:100px; white-space:nowrap;"><fmt:formatDate value="${row.sdate}" pattern="M月d日" /></td>
				<td style="width:100px; white-space:nowrap;"><fmt:formatDate value="${row.edate}" pattern="M月d日" /></td>
			</tr>
			</c:forEach>
			</table>			
      	</div>
    </div>
    </c:if>
</div>
<!-- create Modal -->
<div class="modal fade" id="TaskAddModal" tabindex="-1" role="dialog" aria-labelledby="TaskAddModal">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
    <div class="modal-header"></div>
	<div class="modal-body">
		<p><input class="form-control" style="width:100%;" type="text" name="title" placeholder="工作單標題"/></p>
      	<p><textarea class="note" name="note" id="note" rows='1'/></textarea></p>
      	<p>		
		    <div class="row">
	        <div class="col-sm-5">
	        <select onChange="getUnit(this.value);"  id="optgroup" style="font-size:18px;"class="optgroup form-control" size="4" multiple="multiple">
                  <c:forEach items="${ulist}" var="u">					
				<option value="${u.idno}">【${u.name}】${u.cname}</option>
				</c:forEach>
	        </select>		
	        <select id="optgroup" onClick="document.getElementById('optgroup').selectedIndex = -1;" style="margin-top:5px;font-size:18px;" class="optgroup1 optgroup form-control"  multiple="multiple"></select>
	        </div>		        
	        <div class="col-sm-1">
	            <button type="button" id="optgroup_rightAll" disabled class="btn btn-block btn-primary"><i class="glyphicon glyphicon-forward"></i></button>
	            <button type="button" id="optgroup_rightSelected" class="btn btn-block btn-primary"><i class="glyphicon glyphicon-chevron-right"></i></button>
	            <button type="button" id="optgroup_leftSelected" class="btn btn-block btn-primary"><i class="glyphicon glyphicon-chevron-left"></i></button>
	            <button type="button" id="optgroup_leftAll" class="btn btn-block btn-primary"><i class="glyphicon glyphicon-backward"></i></button>
	        </div>
	        
	        <div class="col-sm-6">
	            <select name="units" id="optgroup_to" style="font-size:18px;"class="form-control" size="9"  multiple="multiple">
	                
	            </select>
	        </div>
	    </div>
		</p>
      	<p><label>附件</label>
		<small>點選「瀏覽檔案」後按住Ctrl鍵可選擇多個檔案</small>
		<input id="upload1" name="fileUpload1" multiple type="file" class="file-loading"></p>
      	<p><button name="method:addTask" class="btn btn-danger" style="width:100%;">建立</button></p>
    </div>
	<div class="modal-footer">
		<button class="btn btn-default" data-dismiss="modal" aria-hidden="true">取消並關閉</button>
	</div>
    </div>
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

function getUnit(lid){	
	$(".optgroup1").html("");
	for(i=0; i<units.length; i++){
		if(units[i].lid==lid&&units[i].cname!="")
		$(".optgroup1").append("<option value='"+units[i].idno+"'>"+"【"+units[i].name+"】"+units[i].cname+"</option>" );		
	}		
}
units=[<c:forEach items="${ulist}" var="l"><c:forEach items="${l.units}" var="u">{lid:"${u.lid}",pid:"${u.pid}", idno:"${u.idno}", name:"${u.name}", cname:"${u.cname}"},</c:forEach></c:forEach>]
</script>
</body>
</html>