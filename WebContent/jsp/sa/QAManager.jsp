<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<link href="/eis/inc/css/wizard-step.css" rel="stylesheet"/>
<script src="http://192.192.230.167/CIS/inc/js/plugin/ckeditor/ckeditor.js"></script>

<link rel="stylesheet" href="/eis/inc/js/plugin/jQuery-File-Upload/css/jquery.fileupload-ui.css">
<script src="/eis/inc/js/plugin/jQuery-File-Upload/js/vendor/jquery.ui.widget.js"></script>
<script src="/eis/inc/js/plugin/jQuery-File-Upload/js/jquery.iframe-transport.js"></script>
<script src="/eis/inc/js/plugin/jQuery-File-Upload/js/jquery.fileupload.js"></script>


<script>
$( document ).ready(function() {
	$("#sub_oid").chained("#parent_oid"); 	
	$("#parent_unit").chained("#campus");
	$("#sub_unit").chained("#parent_unit");
});

function checkSys(id){	
	if($("#sub_oid").val()!=""){
		$("#sys").val($('#sub_oid').val());
	}else{
		$("#sys").val($('#parent_oid').val());
	}
}

function checkUnit(id){
	
	if($("#sub_unit").val()!=""){
		$("#send_unit").val($('#sub_unit').val());		
		return;
	}
	
	if($("#parent_unit").val()!=""){
		$("#send_unit").val($('#parent_unit').val());
	}else{
		$("#send_unit").val($('#campus').val());
	}
}
</script>

</head>
<body>
   
<div id="dialog"></div>
<div class="bs-callout bs-callout-warning" id="callout-helper-pull-navbar">
<strong>系統文件管理</strong></div>


<form action="QAManager" method="post" class="form-horizontal">
<div class="wizard-steps">
  	<div><a href="#"><span>1</span> 建立</a></div>
  	<div><a href="#"><span>2</span> 編輯內容</a></div>
  	<div><a href="#"><span>3</span> 測試</a></div>
  	<div><a href="#"><span>4</span> 審查</a></div>
  	<div><a href="#"><span>5</span> 核定</a></div>
</div>
<c:if test="${empty doc}">
<table class="table">
	<tr>
		<td nowrap>主旨</td>
		<td width="100%" class="control-group error">
		<select name="type">
			<option value="q">系統問答</option>
			<option value="h">操作說明</option>
			<option value="d">系統文件</option>
		</select>
		<input type="text" class="span5" name="title" value="${title}" />
		
		</td>
	</tr>
	<tr>
		<td nowrap>所屬系統</td>
		<td width="100%" class="control-group error">
		<select id="parent_oid" name="parent_oid" onChange="checkSys();" name="parent_oid">
			<option value="">--</option>
			<c:forEach items="${allsys}" var="all">
			<option <c:if test="${parent_oid eq all.Oid}">selected</c:if> value="${all.Oid}">${all.name}</option>
			</c:forEach>
		</select>		
		<select id="sub_oid" name="sub_oid" onChange="checkSys();" name="sub_oid">	
			<option value="">--</option>
			<c:forEach items="${allsys}" var="all">
				<c:forEach items="${all.sub}" var="sub">
					<option <c:if test="${sub_oid eq sub.Oid}">selected</c:if> class="${sub.parent}" value="${sub.Oid}">${sub.name}</option>
				</c:forEach>
				
			</c:forEach>	
		</select>		
		<input type="hidden" name="sys" value="${sys}" id="sys" />
		<button class="btn btn-danger" id="subInfo" name="method:add" type="submit">新增</button>
				
		</td>
	</tr>
	<tr>
		<td>完整性</td>
		<td class="control-group error">
		<select name="per">
			<option value="">全部</option>
			<option <c:if test="${per eq 'tester'}">selected</c:if> value="tester">未測試</option>
			<option <c:if test="${per eq 'editor'}">selected</c:if> value="editor">未回覆</option>
			<option <c:if test="${per eq 'review'}">selected</c:if> value="review">未審核</option>
			<option <c:if test="${per eq 'review_final'}">selected</c:if> value="review_final">未核定</option>
			<option <c:if test="${per eq 'final'}">selected</c:if> value="final">完成</option>
		</select>
		<button class="btn" id="subInfo" name="method:list" type="submit">查詢</button>
		<button class="btn" id="subInfo" name="method:print" type="submit">列印報表</button>
		</td>
	</tr>
</table>


<c:if test="${!empty docs}">
<%@ include file="QAManager/list.jsp"%>
</c:if>
</c:if>


<c:if test="${!empty doc}">
<%@ include file="QAManager/edit.jsp"%>
</c:if>


</form>
<script>
CKEDITOR.replaceAll( function( textarea, config )
	{	//uiColor: '#14B8C4',
		config.toolbar= [
			[ 'Bold', 'Italic', '-', 'NumberedList', 'BulletedList', '-', 'Link', 'Unlink' ],
			[ 'FontSize', 'TextColor', 'BGColor']
		]			
	}
);	
</script>
</body>
</html>