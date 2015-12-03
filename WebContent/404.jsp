<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%String path = request.getContextPath();String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<head>
<base href="<%=basePath%>">
<meta name="JOHN HSIAO" content="http://blog.xuite.net/hsiao/blog" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta content='text/html; charset=UTF-8' http-equiv='Content-Type'/>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
<meta HTTP-EQUIV="expires" CONTENT="-1">
<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
<meta name="ROBOTS" content="none">
<title>sss</title>
<link rel="stylesheet" href="inc/bootstrap/css/bootstrap.css" />
<link rel="stylesheet" href="inc/css/advance.css" />
<link href="/eis/inc/bootstrap/css/docs.min.css" rel="stylesheet"/>
<script src="inc/js/jquery.js"></script>
<script src="inc/bootstrap/js/bootstrap.js"></script>
<script src="inc/js/plugin/jquery.blockUI.js"></script>
<link rel="stylesheet" href="inc/bootstrap/plugin/bootstrap-submenu/css/bootstrap-submenu.min.css">
<script src="inc/bootstrap/plugin/bootstrap-submenu/js/bootstrap-submenu.min.js" defer></script>
<script>
$.ajaxSetup ({ 
	cache: false 
});
window.onbeforeunload = function() {
	$.unblockUI();
};
</script>
</head>
<body>
<%@ include file="/jsp/decorators/menu_3.jsp"%>
<div class="content-page-3">
<div class="bs-callout bs-callout-info" id="callout-helper-pull-navbar">
	<h4>The application has malfunctioned.</h4>
	<p>Please contact technical support with the following information:</p> 
	<p>Exception Name: <span class="label label-danger">404</span></p>
</div>
<h4>Exception Details: </h4>
<textarea class="form-control" rows="3">        <%=request.getHeader("REFERER") %>Oops! A 404 error happened because the resource could not be found.</textarea>
<span id="helpBlock" class="help-block">Give us your error logs and you can influence the way millions of people use the application.</span>
<button type="submit" class="btn btn-primary">send</button>  
   
  
  
</div>
</body>
</html>