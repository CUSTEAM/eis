<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>學期轉換/資料清除</title>
<link href="/eis/inc/css/wizard-step.css" rel="stylesheet"/>


<script src="/eis/inc/js/plugin/jquery-ui.js"></script>
<script src="/eis/inc/js/plugin/jquery-ui-timepicker-addon.js"></script>
<link href="/eis/inc/css/jquery-ui.css" rel="stylesheet"/>


<script>
$(document).ready(function() {	
	$('.help').popover("show");
	setTimeout(function() {
		$('.help').popover("hide");
	}, 5000);
	
});
</script>
</head>
<body>
<div class="bs-callout bs-callout-warning" id="callout-helper-pull-navbar">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<strong>${school_year}學年第${school_term}學期轉換新學期</strong>
</div>
<c:if test="${school_term eq '2'}">
<div class="alert alert-danger">本學期包含升級作業</div>	
</c:if>
<div class="wizard-steps row-fluid">
  	<div><a href="#"><span>1</span> 確認歷年資料量</a></div>
  	<div><a href="#"><span>2</span> 確認清除資料表名稱</a></div>
  	<div><a href="#"><span>3</span> 轉換學期</a></div>
	</div><br><br>
<form action="ChangeTerm" method="post" class="form-horizontal" onSubmit="$.blockUI({message:null});">
<div class="row">
	<div class="col-md-6">  
  	<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">  
		<div class="panel panel-primary">	    
		    <div class="panel-heading " role="tab" id="heading">
		      <h4 class="panel-title">訂單內容</h4>
		    </div>
			<table class="table">
				<tr>
					<td colspan="3"><h2>學期轉換工作事項</h2>
					第1學期執行時間為1、2月, 第2學期執行時間為7、8月</td>
				</tr>
				<tr>
					<td>事項</td>
					<td>表格</td>
					<td>動作</td>
				</tr>			
				<tr>
					<td>開課資料</td>
					<td>Dtime</td>
					<td>寫入Savedtime</td>
				</tr>			
				<tr>
					<td>開課資料</td>
					<td>Dtime</td>
					<td>清除當學期欄位</td>
				</tr>
				<tr>
					<td>排課資料</td>
					<td>Dtime_class</td>
					<td>清除當學期資料</td>
				</tr>			
				<tr>
					<td>選課資料</td>
					<td>Seld</td>
					<td>清除當學期</td>
				</tr>				
				<c:if test="${school_term eq '2'}">						
				<tr>
					<td>升級班查核 <span class="label label-info">升級工作</span></td>
					<td>Class</td>
					<td>建立升級班</td>
				</tr>			
				<tr>
					<td>實體班級升級 <span class="label label-info">升級工作</span></td>
					<td>stmd</td>
					<td>置換depart_class</td>
				</tr>
				</c:if>				
				<tr>
					<td colspan="3">
					<div class="input-group">
						  	<span class="input-group-addon" id="basic-addon1">學期開始</span>
						<input id="begin" class="form-control" name="begin" type="text" value="${school_term_begin}"/>
					</div>	
					</td>
				</tr>
				<tr>
					<td colspan="3">
					<div class="input-group">
						  	<span class="input-group-addon" id="basic-addon1">學期結束</span>
						<input id="end" class="form-control" name="end" type="text" value="${school_term_end}"/>
						</div>			
						</td>
					</tr>
					<tr>
						<td colspan="3">
						<button name="method:termChange" onclick="return confirm('確定轉換?');" class="btn btn-danger">執行學期轉換</button>				
						</td>
					</tr>
				</table>	
	  		</div>
  		</div>
  	</div>
	<div class="col-md-6">
  	<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">  
		<div class="panel panel-primary">	    
	    <div class="panel-heading " role="tab" id="heading">
	      <h4 class="panel-title">資料表</h4>
	    </div>
  		<table class="table">
			<tr>
				<td colspan="3"><h2>匯入歷年資料表</h2></td>
			</tr>
			<tr>
				<td>表格</td>
				<td>資料量</td>
				<td>備註</td>
			</tr>
			<c:forEach items="${stor}" var="s">
			<tr>
				<td>${s.table_name}</td>
				<td>${s.cnt}</td>
				<td>${s.note}</td>
			</tr>			
			</c:forEach>
		</table>
		<div class="panel-body">
    	<h2>清除資料表</h2>
  		</div>
		<table class="table">
			<tr>
				<td>表格</td>
				<td>資料量</td>
				<td>備註</td>
				<td>動作</td>
			</tr>
			<c:forEach items="${reuse}" var="r">
			<tr>
				<td>${r.table_name}</td>
				<td>${r.cnt}</td>
				<td>${r.note}</td>
				<td>
				<c:if test="${r.auto eq '1'}"><button class="btn btn-primary"disabled>自動</button></c:if>
				<c:if test="${r.auto eq '0'}"><button name="method:manuClear" onClick="$('#${r.table_name}').val('${r.table_name}')" class="btn btn-primary">手動</button></c:if>
				<input type="hidden" name="ctable" id="${r.table_name}" />
				</td>
			</tr>			
			</c:forEach>
		</table>
  </div>
</div>






</form>

<script>
$("input[name='begin'], input[name='end']" ).datetimepicker({
	changeMonth: true,
	changeYear: true,});

</script>


</body>
</html>