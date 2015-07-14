function getDilgInfo(no, name, Oid){
	$("#stdNameNo").text(no+" - "+name);
	$.get("/eis/getStdDilg",{student_no:no, Dtime_oid:Oid},
		function(data){
		$("#info").html(data.info);				
	}, "json");	
}

function getStdContectInfo(no, name){
	$("#stdNameNo").text(no+" - "+name);
	$.get("/eis/getStdContectInfo",{student_no:no},
		function(data){
		$("#info").html(data.info);				
	}, "json");	
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