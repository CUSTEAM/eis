function getDilgInfo(no, name, Oid){
	$("#stdNameNo").text(no+" - "+name);
	$.get("/eis/getStdDilg",{student_no:no, Dtime_oid:Oid},
		function(data){
		$("#info").html(data.info);				
	}, "json");	
}

var lookout=false;
function getStdContectInfo(no, name){
	$("#stdNameNo").text(no+" - "+name);
	$.get("/eis/getStdContectInfo",{student_no:no},
			function(data){
			$("#info").html(data.info);				
		}, "json");	
	$.fn.tinyMapConfigure({
	    'api': 'https://maps.googleapis.com/maps/api/js',
	    'v': '3.3',
	    'language': 'zh‐TW'
	});
	
	
	setTimeout(function() {			
			$.ajax({
				type:"POST",
				url:"/eis/getStdsgeo",
				dataType:"json",
				data:{stdNo:no},
				success:function(d){
					if(!lookout){
						lookout=true;
						obj = jQuery.parseJSON(d.list[0].geocode);
						$("#stdMap").tinyMap({
							direction: [{
							                id: no,
							                from: [obj.lat, obj.lng],
							                // 起點的替代顯示文字
							                fromText: 'no',
							                to: ['25.032', '121.609'],
							                toText: '中華科大',	
							                optimize: true,
							                travel: 'driving',
							                autoViewport: true,
							            }]
						});
					}else{
						obj = jQuery.parseJSON(d.list[0].geocode);
						$("#stdMap").tinyMap('clear');
						$("#stdMap").tinyMap('modify',{
							direction: [{
							                id: no,
							                from: [obj.lat, obj.lng],
							                // 起點的替代顯示文字
							                fromText: 'no',
							                to: ['25.032', '121.609'],
							                toText: '中華科大',	
							                optimize: true,
							                travel: 'driving',
							                autoViewport: true,
							            }]
						});
					}
				}
				
			});
		
		
	}, 500);
	
		
}

function getStdScoreInfo(no, name){
	$("#stdNameNo").text(no+" - "+name);
	$.get("/eis/getStdScoreInfo",{student_no:no},
		function(data){
		$("#info").html(data.info);				
	}, "json");	
}

function getSeldTable(no, name){
	$("#stdNameNo").text(no+" - "+name);
	$.get("/eis/getTimeTable",{student_no:no},
		function(d){
		alert(d.list);
		$("#info").html(d.list);				
	}, "json");	
}