var obj;

function getClassTime(ClassNo, name){
	$.get("/eis/getTimeTable",{ClassNo:ClassNo},
		function(d){draw(d.list, name, "class", ClassNo);
		},"json");
}

function getTeacherTime(emplOid, name){
	$.get("/eis/getTimeTable",{emplOid:emplOid},
		function(d){draw(d.list, name, "teacher", emplOid);
		},"json");
}

function getRoomTime(nabbr, name){
	$.get("/eis/getTimeTable",{nabbr:nabbr},
		function(d){draw(d.list, name, "nabbr", nabbr);
		},"json");
}

function getStudentTime(student_no, name){
	$.get("/eis/getTimeTable",{student_no:student_no},
		function(d){draw(d.list, name, "student", student_no);
		},"json");
}

function draw(d, name, type, val){
	//$(".modal").css({"width": "90%"}); 
	$("#info").empty();
	if(type=='class'){$("#title").html(name+" <div class='btn-group'><a class='btn btn-danger' href='/csis/TimeTable?ClassNo="+val+"'>下載課表</a><a class='btn' data-dismiss='modal' aria-hidden='true'>關閉</a></div>");}
	if(type=='teacher'){$("#title").html(name+" <div class='btn-group'><a class='btn btn-danger' href='/csis/TimeTable?emplOid="+val+"'>下載課表</a><a class='btn' data-dismiss='modal' aria-hidden='true'>關閉</a></div>");}
	if(type=='nabbr'){$("#title").html(name+" <div class='btn-group'><a class='btn btn-danger' href='/csis/TimeTable?nabbr="+val+"'>下載課表</a><a class='btn' data-dismiss='modal' aria-hidden='true'>關閉</a></div>");}
	if(type=='student'){$("#title").html(name+" <div class='btn-group'><a class='btn btn-danger' href='/csis/TimeTable?student_no="+val+"'>下載課表</a><a class='btn' data-dismiss='modal' aria-hidden='true'>關閉</a></div>");}
	obj ="<table id='shitable' class='table table-condensed table-bordered table-hover'>";
	obj+="<tr>";
	for(j=0; j<=7; j++){
		if(j!=0){
			obj+="<td>"+getWeek(j)+"</td>";			
		}else{
			obj+="<td></td>";
		}
	}
	obj+="</tr>";
	for(i=1; i<=16; i++){
		obj+="<tr height='60'>";
		obj+="<td>第<br>"+i+"<br>節</td>";
		for(j=1; j<=7; j++){
			obj+="<td width='14%'>"
			for(k=0; k<d.length; k++){				
				if(d[k].week==j && (d[k].begin<=i && d[k].end>=i)){
					obj+="<small>"+d[k].chi_name+"<br>"+d[k].ClassName+"<br>"+d[k].cname+"老師, "+d[k].place+"教室</small>";
				}
			}
			obj+="</td>";
		}
		obj+="</tr>";
	}
	obj+="</table>";	
	$("#info").append(obj);
	$("#info").trigger("create");
}

function getWeek(w){
	//switch (new Date().getDay()) {
	switch (w) {
    case 7:
        return"星期日";
        break;
    case 1:
        return"星期一";
        break;
    case 2:
    	return"星期二";
        break;
    case 3:
    	return"星期三";
        break;
    case 4:
    	return"星期四";
        break;
    case 5:
    	return"星期五";
        break;
    case 6:
    	return"星期六";
        break;
	}
	
}