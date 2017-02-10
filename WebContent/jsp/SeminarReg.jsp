<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="now" class="java.util.Date"></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>活動報名</title>
<link href="/eis/inc/css/wizard-step.css" rel="stylesheet"/>
<script>  
$(document).ready(function() {		
	/*$('#funbtn').popover("show");
	setTimeout(function() {
		$('#funbtn').popover("hide");
	}, 3000);*/
});
</script>  
</head>
<body>
<div class="bs-callout bs-callout-info" id="callout-helper-pull-navbar">
    <h4>活動報名</h4>
    
</div>
<form action="SeminarReg" method="post" class="form-horizontal">
<input type="hidden" name="Oid" value="${exam.Oid}">


<c:if test="${empty exams}">
<div class="panel panel-default">
  <div class="panel-body">目前沒有開放報名的活動</div>
</div>
</c:if>


<c:if test="${!empty exams}">
<input type="hidden" id="level" name="level"/><input type="hidden" id="no" name="no">


<div class="panel panel-primary">
  <!-- Default panel contents -->
  <div class="panel-heading">活動列表</div>
  <!-- ul class="list-group">
	  <li class="list-group-item"><span class="label label-as-badge label-warning">1</span> 點選<div class="btn btn-xs btn-danger">報名</div>報名</li>
	  <li class="list-group-item"><span class="label label-as-badge label-danger">2</span> 點選「取消」取消報名</li>
	  
	</ul-->
<table class="table table-hover">
	<thead>
    	<tr>
    		<th nowrap>梯次</th>
	        <th nowrap>場次</th>
	        <th>場次資訊</th>
	        
	        <th nowrap>報名期間</th>
	        <th nowrap>活動期間</th>
	        
	       
	        <th nowrap>人數</th>
	        <th></th>
      	</tr>
    </thead>
    <tbody class="control-group info">    
    <c:set var="now"><fmt:formatDate value="${now}" pattern="yyyy-MM-dd HH:mm:ss" /></c:set>
	<c:forEach items="${exams}" var="e">
		<tr>
			<td nowrap><span class="label label-as-badge label-danger">${e.level}</span></td>
	        <td nowrap><span class="label label-as-badge label-warning">${e.no}</span></td>
	        <td>${e.note}</td>
	        
	        <c:set var="begin"><fmt:formatDate value="${e.sign_begin}" pattern="yyyy-MM-dd HH:mm:ss" /></c:set>
	        <c:set var="end"><fmt:formatDate value="${e.sign_end}" pattern="yyyy-MM-dd HH:mm:ss" /></c:set>
	        
	        <td nowrap class="text-info">
	        <span class="label label-as-badge label-primary">
	        <fmt:formatDate value="${e.sign_begin}" pattern="M月d日  HH:mm"/> 至 <fmt:formatDate value="${e.sign_end}" pattern="M月d日  HH:mm"/></span>
	        </td>	        
	        <td nowrap>
	        <span class="label label-as-badge label-primary">
	        <fmt:formatDate value="${e.exam_begin}" pattern="M月d日  HH:mm"/>開始</span>
	        </span>
	        <span class="label label-as-badge label-primary">
	        <c:if test="${(e.time/60)>=1}"><fmt:formatNumber value='${e.time/60}' pattern='########'/>小時</c:if>
	        <c:if test="${(e.time%60)>0}">${e.time%60}分鐘</c:if>
	        </span>
	        </td>
	        
	        
	        <td nowrap>${e.ecnt}/${e.empls}</td>
	        <td nowrap><input type="hidden" id="Oid${e.Oid}" name="Oids" />
	        <c:if test="${now>=begin&&now<end}">
		        <c:if test="${e.regdate==null}">
		        	<button class="btn btn-default btn-small" name="method:sign" onClick="$('#level').val('${e.level}'),$('#no').val('${e.no}')" type="submit">報名</button>
		        </c:if>		        
		        <c:if test="${e.regdate !=null}">
		        	<button class="btn btn-danger btn-small" name="method:cancel" onClick="$('#level').val('${e.level}'),$('#no').val('${e.no}')" type="submit">取消</button>
		        <span class="label label-as-badge label-default">${fn:substring(e.regdate, 5, 16)}</span>
		        </c:if>
	        </c:if>
	        <c:if test="${now<begin||now>end}">非報名期間</c:if>
	        </td>
      	</tr>     
	</c:forEach>
	</tbody>
</table>
</div>
</c:if>
</form>  

</body>
</html>