<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<script src="https://maps.googleapis.com/maps/api/js"></script>
<script src="/eis/inc/js/plugin/tinyMap/markerclusterer.js"></script>
<script src="/eis/inc/js/plugin/tinyMap/markerwithlabel.js"></script>
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
	
	
});

function getMap(mapId, cid, did){
	$.ajax({
		type:"POST",
		url:"/eis/getStdsFromgeo",
		dataType:"json",
		data:{cid:cid, did:did},
		success:function(d){
			obj = null;
			out=[];
			for(var i=0;i<d.list.length;i++){
			//for(var i=0;i<2;i++){
				obj = jQuery.parseJSON(d.list[i].geocode);
				out.push({
				'addr': [obj.lat, obj.lng]
				//'text': Jdata[x]['Addr']
				});
			}
			
			$("#"+mapId).tinyMap({
			    'center': ['25.039065815333753', '121.56097412109375'],
			    //'center': ['23.583234', '120.5825975'],
			    'zoom': 10,
			    'markerCluster':true,
			    'marker':out
			});
			
		}
	});
}
</script>
</head>
<body>
<div class="bs-callout bs-callout-info" id="callout-helper-pull-navbar"><b>學生來源</b> 學生合計: ${stdsall}名, 學歷不詳: ${stdsall-stdsnoschl}名<br>滑鼠拖放或滾動檢視細部資訊</div>





<div class="panel-group" id="accordion">
  <div class="panel panel-primary">
    <div class="panel-heading"> 
      <a class="panel-title" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">全校</a>
    </div>
    <div id="collapseOne" class="panel-collapse collapse in">
      <div class="accordion-inner"><div id="map" style="height:600px; width:100%;"></div></div>
    </div>
  </div>
  
  <c:forEach items="${stdscol}" var="s">
  <div class="panel panel-primary">
    <div class="panel-heading"> 
      <a class="panel-title" onClick="getMap('map${s.id}', '${s.id}', null);" data-toggle="collapse" data-parent="#accordion" href="#collapse${s.id}">${s.name}${s.id}</a>
    </div>
    <div id="collapse${s.id}" class="panel-collapse collapse">
      <div class="accordion-inner"><div id="map${s.id}" style="height:400px; width:100%;"></div></div>
    </div>
  </div>
  </c:forEach>
  
  <c:forEach items="${stdsdep}" var="s">
  <div class="panel panel-default">
    <div class="panel-heading"> 
      <a class="panel-title" onClick="getMap('map${s.id}${s.id}', null, '${s.id}');" data-toggle="collapse" data-parent="#accordion" href="#collapse${s.id}${s.id}">${s.name}</a>
    </div>
    <div id="collapse${s.id}${s.id}" class="panel-collapse collapse">
      <div class="accordion-inner"><div id="map${s.id}${s.id}" style="height:400px; width:100%;"></div></div>
    </div>
  </div>
  </c:forEach>
  
</div>
</body>
</html>