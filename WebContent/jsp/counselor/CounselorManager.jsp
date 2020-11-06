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
<c:if test="${!empty stds}">
<div class="panel panel-primary">
<div class="panel-heading">查詢結果</div>
<display:table name="${stds}" id="row" class="table table-striped table-bordered" sort="list" requestURI="CounSelorManager?method=searchStds">
	<display:column style="white-space:nowrap;" title="結果" sortable="true">同意</display:column>
  	<display:column style="white-space:nowrap;" title="連繫" sortable="true">6</display:column>
  	<display:column title="科系" property="DeptNo" sortable="true" />
	<display:column title="姓名" property="student_name" sortable="true" />
  	<display:column title="電話" property="cell_phone" sortable="true"/>  	
  	
  	
  	
  	<display:column title="細節">
  	<a href="CallStatusView?Oid=${row.Oid}" class="btn btn-default btn-sm">查看</a>
  	</display:column>
</display:table>
</div>
</c:if>


<div class="panel panel-primary">
<div class="panel-heading">系所人員管理</div>
<table class="table">	
	
	
	<tr>
		<td>
		<input type="text" class="form-control techid" id="techid" name="techid" onClick="this.value=''" 
		id="techids" value="${techid}" autocomplete="off" style="width:400px;"/>
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

<c:if test="${!empty members}">
<div class="panel panel-primary">
<div class="panel-heading">查詢結果</div>
<display:table name="${members}" id="row" class="table table-striped table-bordered" sort="list" requestURI="CounSelorManager?method=searchStds">
	
  	<display:column title="系所" property="DeptName" sortable="true" />
	<display:column title="姓名" property="cname" sortable="true" />
  	
  	
  	
  	
  	<display:column title="刪除">
  	<a href="CounselorManager?delMember=${row.Oid}" class="btn btn-danger">刪除</a>
  	</display:column>
</display:table>
</div>
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