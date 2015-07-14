<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<script src="/eis/inc/js/plugin/jquery.tinyMap.min.js"></script>

<script>
$(document).ready(function() {

	$('#map').tinyMap({
	    'center': ['25.039065815333753', '121.56097412109375'],
	    //'center': ['23.583234', '120.5825975'],
	    'zoom': 11,
	    'markerCluster': true,
	    'marker': [
	        <c:forEach items="${schools}" var="s">
	        {'addr': ['${s.lat}', '${s.lng}'], 'text': '<strong>${s.name}</strong>'},
	        </c:forEach>
	    ]
	});	
	
	<c:forEach items="${stdscol}" var="s">
	$('#map${s.id}').tinyMap({
	    'center': ['25.039065815333753', '121.56097412109375'],
	    //'center': ['23.583234', '120.5825975'],
	    'zoom': 11,
	    'markerCluster': true,
	    'marker': [
	        <c:forEach items="${s.stds}" var="ss">
	        {'addr': ['${ss.lat}', '${ss.lng}'], 'text': '<strong>${ss.name}</strong>'},
	        </c:forEach>
	    ]
	});	
	</c:forEach>
	
	
	
	
	<c:forEach items="${stdsdep}" var="s">
	$('#map${s.id}${s.id}').tinyMap({
	    'center': ['25.039065815333753', '121.56097412109375'],
	    //'center': ['23.583234', '120.5825975'],
	    'zoom': 11,
	    'markerCluster': true,
	    'marker': [
	        <c:forEach items="${s.stds}" var="ss">
	        {'addr': ['${ss.lat}', '${ss.lng}'], 'text': '<strong>${ss.name}</strong>'},
	        </c:forEach>
	    ]
	});	
	</c:forEach>
	
});
</script>
</head>
<body>
<div class="alert"><b>學生來源</b> 學生合計: ${stdsall}名, 學歷不詳: ${stdsall-stdsnoschl}名<br>滑鼠拖放或滾動檢視細部資訊</div>





<div class="accordion" id="accordion2">
  <div class="accordion-group">
    <div class="accordion-heading"> 
      <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseOne">全校</a>
    </div>
    <div id="collapseOne" class="accordion-body collapse in">
      <div class="accordion-inner"><div id="map" style="height:400px; width:100%;"></div></div>
    </div>
  </div>
  
  <c:forEach items="${stdscol}" var="s">
  <div class="accordion-group">
    <div class="accordion-heading"> 
      <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapse${s.id}">${s.name}</a>
    </div>
    <div id="collapse${s.id}" class="accordion-body collapse">
      <div class="accordion-inner"><div id="map${s.id}" style="height:400px; width:100%;"></div></div>
    </div>
  </div>
  </c:forEach>
  
  <c:forEach items="${stdsdep}" var="s">
  <div class="accordion-group">
    <div class="accordion-heading"> 
      <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapse${s.id}${s.id}">${s.name}</a>
    </div>
    <div id="collapse${s.id}${s.id}" class="accordion-body collapse">
      <div class="accordion-inner"><div id="map${s.id}${s.id}" style="height:400px; width:100%;"></div></div>
    </div>
  </div>
  </c:forEach>
  
</div>
</body>
</html>