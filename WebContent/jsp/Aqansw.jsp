<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/eis/inc/js/jquery.js"></script>
<link rel="stylesheet" href="/eis/inc/css/bootstrap.css" />
<title>Insert title here</title>
</head>
<body>
<form action="Aqansw" method="post" class="form-horizontal">


    <div class="alert">
    <button type="button" class="close" onClick="window.parent.$.unblockUI()">&times;</button>
    <strong>中華科技大學「行政與教學單位服務品質之滿意度調查」</strong> 
    </div>

<table>
	<tr>
		<td>
		

		<table class="table">
			<tr>
				<td>
				親愛的老師，您好：此份問卷是調查本校「行政及教學單位服務品質的滿意度」，
				目的在瞭解學校提供教師們的服務是否滿意。您寶貴的意見將對本校提供教師服務有很大的幫助，
				調查結果將作為未來提升服務品質之參考。您所回答的內容將僅作為統計分析之用途，不對外公開，
				謝謝您的合作與支持。中華科技大學秘書室   啟
				
				</td>
			</tr>
			<tr>
				<td>
				<p class="text-right">
				<button class="btn btn-primary" onClick="window.parent.$.unblockUI()" type="button">略過</button>
				<button class="btn" onClick="window.parent.$.unblockUI()">不再提示</button>
				</p>
				</td>
			</tr>
		</table>
		
		
		
		
		
		<c:if test="${empty qform}">
		<%@ include file="Qform/LoginDirect.jsp" %>
		</c:if>	
	
		<c:if test="${!empty qform}">
		<%@ include file="Qform/LoginQform.jsp" %>
		</c:if>
		
		</td>
	</tr>
	
</table>

</form>

</body>
</html>