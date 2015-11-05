<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="/eis/inc/js/plugin/bootstrap-typeahead.js"></script>
<script src="/eis/inc/js/plugin/json2.js"></script>
<script src="/eis/inc/js/plugin/jquery-ui.js"></script>
<script src="/eis/inc/js/plugin/bootstrap-tooltip.js"></script>
<script src="/eis/inc/js/plugin/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
<link href="/eis/inc/css/jquery-ui.css" rel="stylesheet"/>



</head>
<body>
   
<div id="dialog"></div>
<div class="alert alert alert-warning" role="alert">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<strong>各項日期管理</strong></div>
<form action="CalendarManager" method="post" class="form-inline">
<table class="table control-group info">
	<tr>
		<td nowrap>日期</td>
		<td nowrap>名稱</td>
		
		<td nowrap>修改日期</td>
		<td width="100%">編輯者</td>
	</tr>
	<c:forEach items="${cals}" var="c">
	<tr>
		<td>
		
		<input class="dtpick form-control" type="text" id="${c.name}" placeholder="點一下輸入日期" name="cdate" value="${c.date}"/>
		</td>
		<td nowrap>${c.note}</td>
		<td nowrap>${c.edate}</td>
		<td width="100%">${c.editor}
		<input type="text" id="name${c.name}" name="name" value="">
		<input type="text" value="${c.sys}">
		</td>
	</tr>
	</c:forEach>
</table>
<button class="btn btn-success" id="subInfo" name="method:update" onClick="javascript: return(confirm('請確認您輸入的資料後,按下確定')); void('')" type="submit">變更日期設定</button>
</form>
    
<script>
$(".dtpick" ).datetimepicker({
		onSelect:function(){
			//alert(this.id);
			$("#name"+this.id).val(this.id);
		}
});

</script>
</body>

</html>