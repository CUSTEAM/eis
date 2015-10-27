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
<!-- link href="/eis/inc/css/wizard-step.css" rel="stylesheet"/-->
<script src="http://192.192.230.167/CIS/inc/js/plugin/ckeditor/ckeditor.js"></script>
</head>
<body>   
<div id="dialog"></div>
<div class="alert alert alert-warning" role="alert">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<strong>單位工作管理</strong></div>
<form action="TaskTemplateManager" method="post" class="form-horizontal">
<div class="accordion" id="accordion2">
	<div class="accordion-group">
		<div class="accordion-heading">
		<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseOne">建立新服務</a>
		</div>
		<div id="collapseOne" class="accordion-body collapse">
			<div class="accordion-inner">
			<input type="hidden" name="Oid"/>
			<input type="hidden" name="unit" value="${unit}"/>	
			<div class="input-prepend">
				<span class="add-on">服務名稱</span>						
				<input type="text" class="span4" name="title" placeholder="例如：維修單申請、資料申請"/>
			</div>
			
			<div class="input-prepend">
				<span class="add-on">服務對象</span>
				<select name="open">
					<option value="e">教職員</option>
					<option value="s">學生</option>
				</select>
			</div>
			<br><br>
			<textarea cols="80" name="template" rows="10">自訂申請書欄位例如:<br>狀況描述:<br>發生時間:<br>發生地點:</textarea>
			<br>
			<button class="btn btn-danger" name="method:add" type="submit">建立</button>
			</div>
		</div>
	</div>	
	<c:forEach items="${ot}" var="o">
	<div class="accordion-group" onClick="$('#Oid${o.Oid}').val('${o.Oid}')">
		<div class="accordion-heading">
		<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapse${o.Oid}">${o.title}</a>
		</div>
		<div id="collapse${o.Oid}" class="accordion-body collapse">
			<div class="accordion-inner">
			<input type="hidden" name="Oid" id="Oid${o.Oid}" value=""/>
			<input type="hidden" name="unit" value="${unit}"/>
			<div class="input-prepend">
				<span class="add-on">服務名稱</span>
				<input type="text" class="span4" name="title" value="${o.title}"/>
			</div>
			<div class="input-prepend">
				<span class="add-on">服務對象</span>
				<select name="open">
					<option <c:if test="${o.open eq'e'}">selected</c:if> value="e">教職員</option>
					<option <c:if test="${o.open eq's'}">selected</c:if> value="s">學生</option>
					<option <c:if test="${o.open eq'n'}">selected</c:if> value="n">暫停申請</option>
				</select>
			</div>
			<br><br>
			<textarea cols="80" name="template" rows="10">${o.template}</textarea>
			<br>
			<button class="btn btn-warning" name="method:edit" type="submit">修改</button>
			
			</div>
		</div>
	</div>
	</c:forEach>	
</div>
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
</form>
</body>
</html>