<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>工作單處理</title>
	<script src="http://192.192.230.167/CIS/inc/js/plugin/ckeditor/ckeditor.js"></script>
	<script src="/eis/inc/js/plugin/bootstrap-typeahead.js"></script>
	<script>
    $(document).ready(function(){    	
    	$("#next_empl").typeahead({
    		//remote:"#agent",
    		source : [],
    		items : 10,
    		updateSource:function(inputVal, callback){			
    			$.ajax({
    				url:"/eis/autoCompleteEmplOid",
    			    dataType: 'jsonp',
    			    jsonp:'back',          //jsonp請求方法
    			    data:{nameno:inputVal},
    			    cache:false,
    			    type:'POST',
    			    success: function(d) {
    			    	
    			    	callback(d.list);
    			    }
    			});
    		}		
    	});
    });
</script>
</head>
<body>
<div class="alert alert alert-warning" role="alert">
    <button type="button" class="close" data-dismiss="alert">&times;</button>
    <strong>工作單處理</strong>    
</div>

<form action="TaskDeal" method="post">
<table class="table control-group info">
	<tr>
		<td class="">
		<div class="input-prepend" >
		<span class="add-on">工單編號</span>
		<input class="span2" id="sign_end" readonly name="Oid" type="text" value="${task.Oid}"/>
		</div>
		
		<div class="input-prepend" >
		<span class="add-on">工單名稱</span>
		<input class="span6" readonly type="text" value="${task.title}"/>
		
		</td>
	</tr>
	<tr>
		<td nowrap>
		<div class="input-prepend" >
			<span class="add-on">申請人員</span>
			<input class="span2" id="sign_end" readonly type="text" value="${task.cname}"/>
		</div>
		<div class="input-prepend" >
			<span class="add-on">申請時間</span>
			<input class="span2" readonly type="text" value="${task.sdate}"/>
		</div>
		<div class="input-prepend" >
			<span class="add-on">完成時間</span>
			<input class="span2" readonly type="text" value="${task.edate}"/>
		</div>
		</td>
	</tr>
	<tr>
		<td nowrap>
		<div class="input-prepend" >
			<span class="add-on">處理人員</span>
			<input type="text" placeholder="教師完整姓名" class="span2" id="next_empl" name="next_empl" value="${task.emplOid}, ${task.emplName}" onClick="this.value='',$('#status').val('T')" autocomplete="off" data-provide="typeahead"/>
			
		</div>
		
		<div class="input-prepend" >
			<span class="add-on">處理狀態</span>
			<select name="status" id="status">
				<c:forEach items="${status}" var="s">
				<option <c:if test="${task.status eq s.id}">selected</c:if> value="${s.id}">${s.name}</option>
				</c:forEach>
			</select>
		</div>
		</td>
	</tr>
	<tr>
		<td nowrap>
		${task.note}
		</td>
	</tr>
	<tr>
		<td nowrap>
		<textarea id="reply" name="reply"></textarea>
		</td>
	</tr>
	<tr>
		<td nowrap>
		
		
		<button class="btn btn-danger" name="method:deal">立即處理</button>
		
		<a href="TaskAdd" class="btn">返回</a>
		</td>
	</tr>
	<tr>
		<td>		
		<div class="accordion" id="accordion">			
			<c:forEach items="${hist}" var="h">
			<div class="accordion-group">
				<div class="accordion-heading">
				<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapse${h.Oid}"><fmt:formatDate value="${h.edate}" pattern="yyyy年M月d日 HH:mm" />, ${h.cname}</a>
				</div>
				<div id="collapse${h.Oid}" class="accordion-body collapse">
					<div class="accordion-inner">${h.reply}</div>
				</div>
			</div>
			</c:forEach>			
		</div>		
		</td>
	</tr>
	
	
		
</table>

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