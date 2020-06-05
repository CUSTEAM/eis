<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>


<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB9Y8oHl3dtYJUiOG0ZFzxzurFdd57-DVY&callback=initMap" type="text/javascript"></script>
<script src="/eis/inc/js/plugin/tinyMap/markerclusterer.js"></script>
<script src="/eis/inc/js/plugin/tinyMap/markerwithlabel.js"></script>
<script src="/eis/inc/js/plugin/jquery.tinyMap.min.js"></script>


<script>




$(document).ready(function() {
	
	$.fn.tinyMapConfigure({
	    // Google Maps API URL
	    'api': 'https://maps.googleapis.com/maps/api/js',
	    // Google Maps API Version
	    'v': '3.3',
	    // Google Maps API Key，預設 null
	    'key': 'AIzaSyB9Y8oHl3dtYJUiOG0ZFzxzurFdd57-DVY',
	    // 使用的地圖語言
	    'language': 'zh‐TW'
	    // 載入的函式庫名稱，預設 null
	    //'libraries': 'adsense,drawing,geometry...',
	    // 使用個人化的地圖，預設 false
	    //'signed_in': true|false,
	    // MarkerClustererPlus.js 路徑
	    // 預設 '//google‐maps‐utility‐library‐v3.googlecode.com/svn/trunk/markerclustererplus/src/markerclusterer_packed.js'
	    // 建議下載至自有主機，避免讀取延遲造成無法使用。
	    //'clusterer': '/eis/inc/js/plugin/tinyMap/markerclusterer.js',
	    // MarkerWithLabel.js 路徑
	    // 預設 '//google‐maps‐utility‐library‐v3.googlecode.com/svn/trunk/markerwithlabel/src/markerwithlabel_packed.js'
	    // 建議下載至自有主機，避免讀取延遲造成無法使用。
	    //'withLabel': '/eis/inc/js/plugin/tinyMap/markerwithlabel.js'
	});
	
	$('#map').tinyMap({
	    'center': ['25.039065815333753', '121.56097412109375'],
	    //'center': ['23.583234', '120.5825975'],
	    'zoom': 11,
	    'markerCluster':true,
	    'marker': [	
			<c:forEach items="${stdsgeo}" var="s">{'addr': ['${s.lat}', '${s.lng}']},</c:forEach>
		]
	});
	
	setTimeout(function() {
		
		$('#map').tinyMap(
				'modify', {
			    'marker': [
			        {
			            'addr': ['25.032', '121.609'],
			            'icon': {
					    	'url': '/eis/inc/js/plugin/tinyMap/cust.png',
					        'scaledSize': [24, 24]
					    },
					    'animation': 'DROP',
			            'draggable': true
			        }
			    ]
		});
   }, 1000);
	
});



function getMap(mapId, cid, did){
	<c:forEach items="${stdscol}" var="s">
	$("#map${s.id}").tinyMap('close'); 
	</c:forEach>
	<c:forEach items="${stdsdep}" var="s">
	$("#map${s.id}${s.id}").tinyMap('close'); 
	</c:forEach>
	
	$.ajax({
		type:"POST",
		url:"/eis/getStdsgeo",
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
	
	setTimeout(function() {
		
		$("#"+mapId).tinyMap(
				'modify', {
			    'marker': [
			        {
			            'addr': ['25.032', '121.609'],
			            'icon': {
					    	'url': '/eis/inc/js/plugin/tinyMap/cust.png',
					        'scaledSize': [24, 24]
					    },
					    'animation': 'DROP',
			            'draggable': true
			        }
			    ]
		});
   }, 1000);
}


</script>

</head>
<body>
<div class="bs-callout bs-callout-info" id="callout-helper-pull-navbar">
<b>學生來源</b> 學生合計: ${stdsall}名, 地址不詳: ${stdsall-fn:length(stdsgeo)}名<br>滑鼠拖放或滾動檢視細部資訊</div>
<div class="panel-group" id="accordion">
  <div class="panel panel-primary">
    <div class="panel-heading"> 
      <a class="panel-title" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">全校</a>
    </div>
    <div id="collapseOne" class="accordion-body collapse in">
      <div class="accordion-inner"><div id="map" style="height:600px; width:100%;"></div></div>
    </div>
  </div>
  
  
  
  <c:forEach items="${stdscol}" var="s">
  <div class="panel panel-primary">
    <div class="panel-heading" onClick="getMap('map${s.id}', '${s.id}', null)"> 
      <a class="panel-title" data-toggle="collapse" data-parent="#accordion" href="#collapse${s.id}">${s.name}</a>
    </div>
    <div id="collapse${s.id}" class="accordion-body collapse">
      <div class="accordion-inner"><div id="map${s.id}" style="height:400px; width:100%;">map${s.id}</div></div>
    </div>
  </div>
  </c:forEach>
  
  <c:forEach items="${stdsdep}" var="s">
  <div class="panel panel-default">
    <div class="panel-heading" onClick="getMap('map${s.id}${s.id}', null, '${s.id}')"> 
      <a class="panel-title" data-toggle="collapse" data-parent="#accordion" href="#collapse${s.id}${s.id}">${s.name}</a>
    </div>
    <div id="collapse${s.id}${s.id}" class="accordion-body collapse">
      <div class="accordion-inner"><div id="map${s.id}${s.id}" style="height:400px; width:100%;"></div></div>
    </div>
  </div>
  </c:forEach>
  
  
</div>

</body>
</html>