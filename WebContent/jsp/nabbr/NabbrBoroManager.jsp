<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>教室/實驗室借用管理</title>
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
<div class="alert alert alert-warning" role="alert">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<strong>教室/實驗室借用管理</strong>
<div  class="btn btn-warning help" data-toggle="popover" title="說明" data-content="第1學期執行時間為1、2月, 第2學期執行時間為7、8月。" 
    data-placement="right" >?</div>
</div>

<form action="ChangeTerm" method="post" class="form-horizontal" onSubmit="$.blockUI({message:null});">

	<div class="wizard-steps row-fluid">
  	<div><a href="#"><span>1</span> 確認歷年資料量</a></div>
  	<div><a href="#"><span>2</span> 確認清除資料表名稱</a></div>
  	<div><a href="#"><span>3</span> 轉換學期</a></div>
	</div>







</form>

<script>
$("input[name='begin'], input[name='end']" ).datetimepicker();

</script>


</body>
</html>