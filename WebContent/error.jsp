<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="bs-callout bs-callout-info" id="callout-helper-pull-navbar">
	<h4>The application has malfunctioned.</h4>
	<p>Please contact technical support with the following information:</p> 
	<p>Exception Name: <span class="label label-danger"><s:property value="exception" /></span></p>
</div>
<h4>Exception Details: </h4>
<textarea class="form-control" rows="3">        <s:property value="exceptionStack" /></textarea>
<span id="helpBlock" class="help-block">Give us your error logs and you can influence the way millions of people use the application.</span>
<button type="submit" class="btn btn-primary">send</button>  
   
  
  

</body>
</html>