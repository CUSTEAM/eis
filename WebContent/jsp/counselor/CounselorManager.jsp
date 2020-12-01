<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>招生咨詢管理</title>
<script src="/eis/inc/js/plugin/bootstrap-typeahead.js"></script>
<script src="/eis/inc/js/plugin/jquery-ui.js"></script>
<script src="/eis/inc/js/plugin/bootstrap-tooltip.js"></script>
<script src="/eis/inc/js/plugin/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
<link href="/eis/inc/css/jquery-ui.css" rel="stylesheet"/>
<script src="inc/js/autoComplete.js"></script>
</head>
<body>

<div class="bs-callout bs-callout-danger">招生咨詢管理 <a href="/pis/Counselor">檢視咨詢網頁</a></div>

<form action="CounselorManager" method="post" class="form-inline" enctype="multipart/form-data">



<div class="row">
<div class="col-md-6">
<div class="panel panel-primary">
<div class="panel-heading">查詢申請</div>
<table class="table">	
	
	<tr>
		<td>
		<div class="input-group">
	  		<span class="input-group-addon">開始日期</span>
	  		<input class="form-control" type="text" id="beginDate" autocomplete="off" placeholder="點一下輸入日期" name="beginDate" value="${beginDate}"/>
		</div>
		<div class="input-group">
	  		<span class="input-group-addon">結束日期</span>
	  		<input class="form-control" type="text" id="endDate" autocomplete="off" placeholder="點一下輸入日期" name="endDate" value="${endDate}"/>
		</div>
		</td>	
	</tr>
	<tr>
		<td>
		
		<select name="DeptStd" class="selectpicker">
			<option value="">全部系所</option>
			<c:forEach items="${allDept}" var="d">
			<option <c:if test='${d.id eq  DeptStd}'>selected</c:if> value="${d.id}">${d.name}</option>
			</c:forEach>
		</select>
		<button class="btn btn-primary" id="saveTxtFile" name="method:searchStds" type="submit"><span class="glyphicon glyphicon-search" aria-hidden="true"></span> 查詢申請狀況</button>
		</td>
	</tr>
	
</table>
</div>
</div>
<div class="col-md-6">
<div class="panel panel-primary">
<div class="panel-heading">系所人員管理</div>
<table class="table">	
	
	
	<tr>
		<td>
		<input type="text" class="form-control techid" id="techid" name="techid" onClick="this.value=''" 
		placeholder="教職員姓名.." id="techids" value="${techid}" autocomplete="off" style="width:100%;"/>		
		</td>
	</tr>
	<tr>
		<td>
		<select name="DeptMamber" class="selectpicker">
			<option value="">全部系所</option>
			<c:forEach items="${allDept}" var="d">
			<option <c:if test='${d.id eq  DeptMamber}'>selected</c:if> value="${d.id}">${d.name}</option>
			</c:forEach>
		</select>
		<button class="btn btn-primary" id="saveTxtFile" name="method:searchMambers" type="submit"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> 查詢權限</button>
		<button class="btn btn-danger" id="saveTxtFile" name="method:addMambers" type="submit"><span class="glyphicon glyphicon-king" aria-hidden="true"></span> 建立權限</button>
		</td>
	</tr>
</table>
</div>
</div>
</div>






<input type="hidden" name="Oid" id="Oid" value="${Oid }" />
<c:if test="${!empty stds}">
<div class="panel panel-primary">
<div class="panel-heading">查詢結果</div>
<display:table name="${stds}" id="row" class="table table-striped table-bordered" sort="list" requestURI="CounSelorManager?method=searchStds">
	<display:column style="text-align:left; width:1px;" title="結果">
	<select class="selectpicker"  
	<c:if test="${row.reply eq'Y'}">data-style="btn-info"</c:if>
	<c:if test="${row.reply eq'N'}">data-style="btn-danger"</c:if>
	<c:if test="${row.reply ne'Y' && row.reply ne'N'}">data-style="btn-warning"</c:if>
	disabled>
		<option>未決定</option>
		<option <c:if test="${row.reply eq'Y'}">selected</c:if>>有意願</option>
		<option <c:if test="${row.reply eq'N'}">selected</c:if>>無意願</option>		
	</select>	
	</display:column>
	<display:column title="日期" style="white-space:nowrap; width:100px;" property="add_time" sortable="true"/>  	
  	<display:column style="white-space:nowrap; width:100px;" title="連繫" sortable="true">${row.cnt}</display:column>
  	<display:column title="科系" style="white-space:nowrap; width:100px;" property="DeptName" sortable="true" />
  	<display:column title="學制" style="white-space:nowrap; width:100px;">
  	<c:choose>
	    <c:when test="${row.SchoolNo eq '2'}">二專</c:when>
	    <c:when test="${row.SchoolNo eq 'B2'}">四技</c:when>
	    <c:when test="${row.SchoolNo eq 'C'}">二技</c:when>
	    <c:when test="${row.SchoolNo eq 'M'}">碩士</c:when>
	    <c:otherwise></c:otherwise>
	</c:choose>
  	</display:column>
	<display:column title="姓名" style="white-space:nowrap; width:100px;" property="student_name" sortable="true" />
  	<display:column title="電話" style="white-space:nowrap; width:100px;" property="cell_phone" sortable="true"/>  	
  	<display:column title="細節" style="width:50%;">
  	<button name="method:edit" onClick="$('#Oid').val(${row.Oid})" class="btn btn-primary">查看</button>
  	<button name="method:del" onClick="$('#Oid').val(${row.Oid})" class="btn btn-default">刪除</button>
  	</display:column>
</display:table>
</div>
</c:if>




<c:if test="${!empty members}">
<div class="panel panel-primary">
<div class="panel-heading">權限列表</div>
<display:table name="${members}" id="row" class="table table-striped table-bordered" sort="list" requestURI="CounSelorManager?method=searchStds">
	
  	<display:column title="系所" property="DeptName" sortable="true" />
	<display:column title="姓名" property="cname" sortable="true" />
  	
  	
  	
  	
  	<display:column title="刪除">
  	<a href="CounselorManager?delMember=${row.Oid}" class="btn btn-danger">刪除</a>
  	</display:column>
</display:table>
</div>
</c:if>








<c:if test="${!empty info}">

<div class="panel panel-primary">
<div class="panel-heading">連絡記錄</div>
<table class="table">
		<tr>
			<td width="100">姓名</td>
			<td>${info.student_name}, 於 ${info.add_time}預約</td>
		</tr>
		<tr>
			<td width="100">電話</td>
			<td>${info.cell_phone}</td>
		</tr>
		<tr>
			<td width="100">學制</td>
			<td>			
				<c:choose>
			    <c:when test="${info.SchoolName eq 'M'}">碩士班</c:when>
			    <c:when test="${info.SchoolName eq '2'}">二專</c:when>
			    <c:when test="${info.SchoolName eq 'B2'}">四技</c:when>    
			    <c:otherwise>二技</c:otherwise>
				</c:choose>			
			</td>
		</tr>
		<tr>
			<td width="100">科系</td>
			<td>${info.deptName}</td>
		</tr>
		<tr>
			<td width="100">目前意願</td>
			<td>
			<select name="reply" class="selectpicker">
				<option value="">未決定</option>
				<option <c:if test="${info.reply eq'Y'}">selected</c:if> value="Y">有意願</option>
				<option <c:if test="${info.reply eq'N'}">selected</c:if> value="N">無意願</option>
			</select>
			
			</td>
		</tr>
		<tr>
			<td width="100">預約備註</td>
			<td>${info.note}</td>
		</tr>
		
	<tr>
			<td width="100">連繫結果</td>
			<td>			
			<script src="https://cdn.ckeditor.com/4.15.1/standard/ckeditor.js"></script>
			<textarea name="note" rows="8" style="height:200px; width:100%;"></textarea>
                <script>
                config.width = 850;     // 850 pixels wide.
                config.width = '75%';   // CSS unit.
                        CKEDITOR.inline( 'note', {
                        	toolbar: [
                        		
                        		
                        		{ name: 'document', items: [ 'Templates' ] },	// Defines toolbar group with name (used to create voice label) and items in 3 subgroups.
                        		{ name: 'basicstyles', items: [ 'Bold', 'Italic' ] },
                        		[ 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo' ],			// Defines toolbar group without name.
                        		{ name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ], items: [ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote', 'CreateDiv', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock', '-', 'BidiLtr', 'BidiRtl', 'Language' ] },																				// Line break - next group will be placed in new line.
                        		
                        	
                        		
                        		
                        		
                        		]
                        });
                </script>
			</td>
		</tr>
</table>
<div class="panel-footer"><button class="btn btn-default" id="save" name="method:save" type="submit"><span class=" glyphicon glyphicon-floppy-disk " aria-hidden="true"></span> 儲存</button>
<button name="method:searchStds" class="btn btn-default">離開</a></div>

</div>

<c:if test="${!empty ms}">
<div class="panel panel-primary">
<div class="panel-heading">查詢結果</div>
<table class="table">
	<c:forEach items="${ms}" var="m">
		<tr>
			<td nowrap>${m.name}, ${m.editDate}: ${m.note}</td>
		</tr>
		</c:forEach>
	</table>
</div>

</c:if>

</c:if>









</form>


<script>
$("input[name='beginDate'], input[name='endDate']").datepicker({
	changeMonth: true,
	changeYear: true,
	//minDate: '@minDate'
	yearRange: "-100:+0"
	//showButtonPanel: true,
	//dateFormat: 'yy-MM-dd'
});
</script>

</body>
</html>