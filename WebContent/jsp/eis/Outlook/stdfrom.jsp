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
<style>
.labels {
    background-color: rgba(0, 0, 0, 0.5);
    border-radius: 4px;
    color: white;
    padding: 4px
}
</style>
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
	
	//getMap();
	$('#map').tinyMap({
	    'center': ['25.039065815333753', '121.56097412109375'],
	    //'center': ['23.583234', '120.5825975'],
	    'zoom': 14,
	    //'markerCluster': true,
	    'marker': [
	        <c:forEach items="${schools}" var="s">
	        {'addr':['${s.lat}','${s.lng}'],'text':'<strong>${s.name}</strong>','newLabel':'${s.name}, ${s.cnt}人','newLabelCSS':'labels'},
	        </c:forEach>
	    ]
	});	
	
});


function getMapNew(){
	$("#map").tinyMap('destroy');
	$.ajax({
		//type:"GET",
		url:"/eis/getStdsFromgeo",
		dataType:"json",
		data:{cno:$("#cno").val(), stno:$("#stno").val(), sno:$("#sno").val(), cono:$("#cono").val(), dno:$("#dno").val(), gno:$("#gno").val(), zno:$("#zno").val()},
		success:function(d){
			obj = null;
			out=[];
			for(var i=0;i<d.list.length;i++){								
				out.push({
					'addr':[d.list[i].lat, d.list[i].lng],'newLabel':d.list[i].name+','+d.list[i].cnt,'newLabelCSS':'labels'
				});
			}
			
			$("#map").tinyMap({
			    'center': ['25.039065815333753', '121.56097412109375'],
			    //'center': ['23.583234', '120.5825975'],
			    'zoom': 14,
			    //'markerCluster':true,
			    'marker':out
			});
			
		}
	});
}





function getMap(){
	$("#map").tinyMap('destroy');
	$.ajax({
		type:"POST",
		url:"/eis/getStdsFromgeo",
		dataType:"json",
		data:{cluster:true,cno:$("#cno").val(), stno:$("#stno").val(), sno:$("#sno").val(), cono:$("#cono").val(), dno:$("#dno").val(), gno:$("#gno").val(), zno:$("#zno").val()},
		success:function(d){
			obj = null;
			out=[];
			for(var i=0;i<d.list.length;i++){								
				out.push({
					'addr': [d.list[i].lat, d.list[i].lng]
				});
			}
			
			$("#map").tinyMap({
			    'center': ['25.039065815333753', '121.56097412109375'],
			    //'center': ['23.583234', '120.5825975'],
			    'zoom': 11,
			    'markerCluster':true,
			    'marker':out
			});
			
		}
	});
}





</script>
</head>
<body>
<div class="bs-callout bs-callout-info" id="callout-helper-pull-navbar"><b>學生來源</b> 學生合計: ${stdsall}名, 學歷不詳: ${stdsall-stdsnoschl}名</div>


<div class="panel panel-primary">
  	<div class="panel-heading">自訂查詢範圍</div>
  	<div class="panel-body">
    <form class="form-inline">
	<%@ include file="/inc/jsp-kit/classSelectorFullAll.jsp"%><br><br>
	<button class="btn btn-danger" type="button" onClick="getMapNew()">生源學校個別顯示</button>
	<button class="btn btn-default" type="button" onClick="getMap()">生源區學校區域顯示</button>
	<button class="btn btn-default" name="method:stdFromPrint">建立文字報表</button>
	</form>
  	</div>
  	
  	<table class="table"><tr><td>
    <div id="map" style="height:600px; width:100%;"></div>
    </td></tr></table>
  	
</div>


</body>
</html>