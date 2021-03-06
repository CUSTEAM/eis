<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="/eis/inc/css/jquery-ui.css" rel="stylesheet"/>
<link href="/eis/inc/css/bootstrap-tree.css" rel="stylesheet"/>
<link href="/eis/inc/css/advance/autoscale_1152.css" rel="stylesheet"/>
<link href="/eis/inc/css/wizard-step.css" rel="stylesheet"/>
<link href="/eis/inc/js/plugin/jquery-ui-timepicker-addon/jquery-ui-timepicker-addon.min.css" rel="stylesheet"/>

<script src="/eis/inc/js/plugin/jquery-ui.js"></script>
<script src="/eis/inc/js/plugin/jquery-ui-timepicker-addon/jquery-ui-timepicker-addon.min.js"></script>
<script src="/eis/inc/bootstrap/plugin/bootstrap-typeahead.js"></script>
<script src="/eis/inc/js/plugin/jquery.knob.js"></script>




<title>加班補休申請</title>
<script>  
$(document).ready(function() {
	
	$("#asshole").typeahead({
		remote:"#agent",
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
<form action="ExcuseManager" method="post" class="form-inline">

<div class="bs-callout bs-callout-warning" id="callout-helper-pull-navbar">
<button type="button" class="close" onClick="window.parent.$.unblockUI()">&times;</button>
<strong>加班補休申請</strong> 
</div>

<div class="row">
	
	<div class="col-md-3">
		<div class="panel panel-primary">
			<div class="panel-heading"><h3 class="panel-title">狀態</h3></div>
			<div class="panel-body">
			<table>
				<tr>
					<td nowrap>
					<input readonly class="knob" data-width="200" 
					data-displayInput="true" data-step="1" data-min="0" data-max="${total}" value="${used}">
					</td>
					<td nowrap style="padding:10px;">
						<h1 class="muted">加班:${total}</h1>					
						<h1 class="muted">已休:${used}</h1>	
					</td>
				</tr>
			</table>
			</div>
		</div>	
	</div>
	
	<div class="col-md-4">
	<div class="panel panel-primary">
		<div class="panel-heading"><h3 class="panel-title">加班申請</h3></div>
		<div class="panel-body">
		<table class="table control-group success">				
				<tr>
					<td width="100%">
					<div class="input-group">
      				<div class="input-group-addon">加班開始</div>
					<input class="form-control timepick" type="text" name="startDate1" id="startDate1" />
					</div>
					</td>
				</tr>
				<tr>
					<td>
					<div class="input-group">
      				<div class="input-group-addon">加班結束</div>
					<input type="text" class="form-control timepick" name="endDate1" id="endDate1" />
					</div>
					</td>
				</tr>
				<tr>
					<td>
					<div class="input-group">
      				<div class="input-group-addon">加班原因</div>
					<input type="text" class="form-control" name="reason1"/>
					</div>
					</td>
				</tr>
				<tr>
					<td><button class="btn btn-success" name="method:add1" type="submit">申請加班</button></td>
				</tr>
			</table>
		</div>
	</div>
	</div>
	
	<div class="col-md-5">	
	<div class="panel panel-primary">
		<div class="panel-heading"><h3 class="panel-title">補休申請</h3></div>
			<div class="panel-body">
			<table class="table control-group error">
				<tr>
					<td width="100%">
					<div class="input-group">
      				<div class="input-group-addon">補休開始</div>
					<input class="form-control timepick" type="text" name="startDate" id="startDate" />
					</div>
					</td>
				</tr>
				<tr>
					<td>
					<div class="input-group">
      				<div class="input-group-addon">補休結束</div>
					<input type="text" class="form-control timepick" name="endDate" id="endDate" />
					</div>
					</td>
				</tr>
				<tr>
					<td>
					<div class="input-group">
      				<div class="input-group-addon">補休原因</div>
					<input type="text" class="form-control" name="reason"/>
					</div>
					</td>
				</tr>
				<tr>
					<td>
					<div class="input-group">
      				<div class="input-group-addon">代理人 ★</div>
					<input type="text" name="asshole" class="form-control" onClick="this.value='', $('#agent').val('');" id="asshole" data-provide="typeahead"/>
					</div>
					<input type="hidden" name="agent" id="agent" />
					</td>
				</tr>
				<tr>
					<td><button class="btn btn-danger" name="method:add" type="submit">申請補休</button></td>
				</tr>
			</table>
			</div>
		</div>	
	</div>
</div>
	
<div class="row">
	<div class="col-md-6">
		<div class="bs-callout bs-callout-info" id="callout-helper-pull-navbar">加班申請列表</div>
		<display:table name="${docs2}" id="row" class="table table-condensed" sort="list" excludedParams="*" >
		  	<display:column title="開始" property="startDate" sortable="true" />
		  	<display:column title="結束" property="endDate" sortable="true"/>
		  	<display:column title="加班原因" property="reason" sortable="true"/>
		  	<display:column title="時數" property="hours" sortable="true"/>
		  	<display:column title="狀態" 	sortable="true" >
		  	<c:if test="${row.status eq '2'}">不核准</c:if>
		  	<c:if test="${empty row.status}">審核中</c:if>
		  	</display:column> 
		  	<display:column>
	        	<c:if test="${empty row.status}"><a onclick="return confirm('請問是否刪除');" href="ExcuseManager?dOid=${row.Oid}">刪除</a></c:if>
	        </display:column>
		  	<display:column>
		  		<c:if test="${empty row.status}">
	        	<script>document.write("<a target='_blank' href=/CIS/AjaxGlobal?method=DocReport&Oid=${row.Oid}&sessionNumber=" + new Date().getTime() + Math.floor(Math.random()*999)+">列印</a>")</script>
	        	</c:if>
	        </display:column>
		</display:table>
	</div>
		
	<div class="col-md-6">
		<div class="bs-callout bs-callout-warning" id="callout-helper-pull-navbar">補休申請列表</div>
		<display:table name="${docs09}" id="row" class="table table-condensed" sort="list" excludedParams="*" >
		  	<display:column title="開始" property="startDate" sortable="true" />
		  	<display:column title="結束" property="endDate" sortable="true"/>
		  	<display:column title="補休原因" property="reason" sortable="true"/> 
		  	<display:column title="時數" property="hours" sortable="true"/> 
		  	<display:column title="狀態" 	sortable="true" >
		  	<c:if test="${row.status eq '2'}">不核准</c:if>
		  	<c:if test="${empty row.status}">審核中</c:if>
		  	</display:column>
		  	<display:column>
	        	<c:if test="${empty row.status}"><a onclick="return confirm('請問是否刪除');" href="ExcuseManager?dOid=${row.Oid}">刪除</a></c:if>
	        </display:column>
		  	<display:column>
		  		<c:if test="${empty row.status}">
	        	<script>document.write("<a target='_blank' href=/CIS/AjaxGlobal?method=DocReport&Oid=${row.Oid}&sessionNumber=" + new Date().getTime() + Math.floor(Math.random()*999)+">列印</a>")</script>
	        	</c:if>
	        </display:column>
		</display:table>
	</div>
</div>	


</form>
<script>
    $(".knob").knob({'change' : function (v) { console.log(v); }});
</script>

</form>
<script>
$('.timepick').datetimepicker({
	controlType: 'select',
	//addSliderAccess: true,
	changeMonth: true,
	changeYear: true,
	sliderAccessArgs: { touchonly: false },
	stepHour: 1,
	stepMinute: 30,
	hourMin: 7,
	hourMax: 23
});
</script>
</body>


</html>