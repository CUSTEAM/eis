<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>教室/實驗室借用記錄</title>
<link href="/eis/inc/css/jquery-ui.css" rel="stylesheet"/>
<link href="/eis/inc/bootstrap/plugin/nav-wizard.bootstrap.css" rel="stylesheet"/>
<link href="/eis/inc/bootstrap/plugin/silviomoreto-bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet"/>
<link href="/eis/inc/js/plugin/jquery-ui-timepicker-addon/jquery-ui-timepicker-addon.min.css" rel="stylesheet"/>


<script src="/eis/inc/js/plugin/jquery-ui.js"></script>
<script src="/eis/inc/js/plugin/jquery-ui-timepicker-addon/jquery-ui-timepicker-addon.min.js"></script>
<script src="/eis/inc/bootstrap/plugin/silviomoreto-bootstrap-select/js/bootstrap-select.min.js"></script>
<script src="/eis/inc/bootstrap/plugin/silviomoreto-bootstrap-select/js/i18n/defaults-zh_TW.min.js"></script>
<script src="/eis/inc/js/plugin/bootstrap-typeahead.js"></script>
<script src="/eis/inc/js/autoComplete.js"></script>
<script>
$(document).ready(function() {	
	/*$('.help').popover("show");
	setTimeout(function() {
		$('.help').popover("hide");
	}, 5000);*/
	
	
	/*$("input[name*='del']").click(function() {
		alert(this.id);
		$(".appDel").val(this.id);
	});*/
	
	$(".edit").click(function(){
		$("#Oid").val(this.id);
	})
	
	$(".check").click(function(){
		if($("#cls"+this.id).val()!=""){
			$("#cls"+this.id).val("");
		}else{
			$("#cls"+this.id).val(this.id);
		}
		
		
	})
});
</script>
</head>
<body>


<form action="NabbrBoroRegister" method="post" class="form-horizontal" onSubmit="$.blockUI({message:null});">

<c:if test="${empty cls}">
<div class="bs-callout bs-callout-warning" id="callout-helper-pull-navbar">
	<h4>建立教室/實驗室使用記錄</h4>
	點選 <button class="btn btn-danger btn-xs">建立使用記錄</button> 或 <button class="btn btn-default btn-xs">查詢使用記錄</button>
</div>
<ul class="nav nav-pills nav-wizard">
    <li class="active"><a href="#" data-toggle="tab">選擇研究計畫</a><div class="nav-arrow"></div></li>
    <li><div class="nav-wedge"></div><a href="" data-toggle="tab">選擇教室</a><div class="nav-arrow"></div></li>
    <li><div class="nav-wedge"></div><a href="" data-toggle="tab">指定設備</a><div class="nav-arrow"></div></li>
    <li><div class="nav-wedge"></div><a href="" data-toggle="tab">日期範圍</a><div class="nav-arrow"></div></li>
    <li><div class="nav-wedge"></div><a href="" data-toggle="tab">節次範圍</a><div class="nav-arrow"></div></li>
    <li><div class="nav-wedge"></div><a href="" data-toggle="tab">申請完成</a></li>
</ul>
<table class="table" >
	<tr>
		<td>		
			
			<div class="input-group">
				<div class="input-group-addon">選擇專案</div>
				<select class="selectpicker show-tick form-control" name="RcTableCode">
					<option value="">指定專案</option>
					<c:forEach items="${rclist}" var="r">
						<optgroup label="${r.name}">
						<c:forEach items="${r.rc}" var="c">
						<c:set var="x" value="${r.rc_table},${c.Oid}" />				
						<option <c:if test="${x eq RcTableCode}">selected</c:if> value="${r.id},${c.Oid}">${c.school_year}學年, ${c.title}, 已建立${c.cnt}時數</option>						
						</c:forEach>
						</optgroup>
					</c:forEach>    
				</select>
			</div>
		
		</td>
	</tr>
	
	<tr>
		<td>
		<div class="input-group">
			<div class="input-group-addon">使用人數</div>
			<input type="text" value="${users}" name="users" id="users" class="form-control" autocomplete="off" placeholder="人數"/>
		</div>
		</td>
	</tr>
	
	
	<tr>
		<td>		
		<div class="input-group">
			<div class="input-group-addon">開始日期</div>
			<input name="beginDate" value="${beginDate}" class="form-control timepick" autocomplete="off" type="text" />
			</div>
		</td>
	</tr>
	<tr>
		<td>
		<div class="input-group">
			<div class="input-group-addon">結束日期</div>
			<input type="text" value="${endDate}" name="endDate" class="form-control timepick" autocomplete="off" placeholder="1日內借用無需輸入"/>
		</div>
		</td>
	</tr>
	
	
	<tr>
		<td>
		
		<div class="input-group">
			<div class="input-group-addon">選擇教室</div>
			<input type="text" id="room_id" name="room_id" value="${room_id}" class="place form-control" autocomplete="off" onClick="this.value='';" data-provide="typeahead"/>
			</div>
		
		</td>
	</tr>
</table>




<div id="prop" style="display:none;" onClick="check('prop')">
	
</div>

<div id="clsTable" style="display:none;">
<div class="panel panel-primary">
  	<!-- Default panel contents -->
	<div class="panel-heading">每週使用頻率</div>
	<div class="panel-body">
	  <p>根據時間範圍條件，比對下表中勾選的星期節次建立使用記錄</p>
	</div>

	<table class="table table-bordered" onClick="check('cls')">
		<tr>
			<c:forEach begin="0" end="7" varStatus="w">
			<c:if test="${w.index==0}">
			<td width="100">節次/週</td>
			</c:if>
			<c:if test="${w.index!=0}">
			<td><input type="checkbox" class="week" value="${w.index}"/> 星期${w.index} </td>
			</c:if>
			</c:forEach>
		</tr>
		<c:forEach begin="1" end="14" varStatus="c">
		
		<tr>
			<c:forEach begin="0" end="7" varStatus="w">
			<c:if test="${w.index==0}">
			<td>
			<input type="checkbox" class="cls" value="${c.index}"/>
			第${c.index}節
			</td>
			</c:if>
			
			<c:if test="${w.index!=0}">
			<td>
			<input type="checkbox" class="w${w.index} c${c.index}" name="workday" value="${w.index}-${c.index}"/>
			</td>
			</c:if>
			</c:forEach>
		</tr>
		</c:forEach>			
	</table>
  
</div>




</div>



<p>
<div class="btn-group" role="group" aria-label="...">
<button name="method:create" id="create" disabled class="btn btn-danger">建立使用記錄</button>
<button name="method:search" class="btn btn-default">查詢使用記錄</button>
</div>
</p>		

</c:if>
		
<input type="hidden" name="Oid" id="Oid" value="${Oid}"/>
<c:if test="${!empty boros}">
<div class = "panel panel-primary">
   <div class = "panel-heading"><h3 class = "panel-title">使用記錄</h3></div>
	<display:table export="false" name="${boros}" id="row" class="table table-condensed" sort="list"  requestURI="NabbrBoroRegister?method=search">
	  	<display:column style="white-space: nowrap">
	  	
	  		<button name="method:edit" id="${row.Oid}" class="btn btn-default btn-sm edit">修改</button>
			<button name="method:delApp" id="${row.Oid}" class="btn btn-danger btn-sm edit">刪除</button>
		
	  	</display:column>
	  	<display:column style="width:5%; white-space:nowrap;" title="編號" property="Oid" sortable="true" />
	  	<display:column style="width:10%; white-space:nowrap;" title="名稱" property="name" sortable="true"/>
	  	<display:column style="width:10%; white-space:nowrap;" title="申請日期" property="date" sortable="true"/>
	  	<display:column style="width:5%; white-space:nowrap;" title="教室" property="room_id" sortable="true"/>
	  	
	  	
	  	<display:column style="width:5%; white-space:nowrap;" title="時數" property="cnt" sortable="true"/>
	  	<display:column style="width:5%; white-space:nowrap;" title="人數" property="users" sortable="true"/>
	  	<display:column style="width:5%; white-space:nowrap;" title="狀態" property="lender" sortable="true"/>
	  	<display:column style="width:100%;" title="審核" property="result" sortable="true"/>	  	
	  	
	</display:table>
</div>
</c:if>
		
<c:if test="${!empty cls}">
<div class="bs-callout bs-callout-warning" id="callout-helper-pull-navbar">
	<h4>建立教室/實驗室使用記錄</h4>
	勾選記錄後點選表格頂端或尾端的 <button type="button" class="btn btn-danger btn-xs">刪除勾選</button><br>
	結束請點選 <a href="NabbrBoroRegister" class="btn btn-default btn-xs">返回</a>
</div>

   
   
<div class = "panel panel-primary">
	<div class = "panel-heading"><h3 class = "panel-title">所有欄位均可排序</h3></div>
	<display:table name="${cls}" id="row" class="table table-condensed" sort="list" requestURI="NabbrBoroRegister?method=edit">
		<display:column title="<button name='method:delCls' id='${row.Oid}' class='btn btn-danger btn-sm check'>刪除勾選</button>" style="width:5%; text-align:center;">
		<input type="checkbox" class="check" id="${row.Oid}"/>
		</display:column>
	  	<display:column style="white-space:nowrap;" title="日期" property="boro_date" sortable="true"/>
	  	<display:column style="width:5%; text-align:center; white-space:nowrap;" title="星期" property="week" sortable="true"/>
	  	<display:column style="width:5%; text-align:center; white-space:nowrap;" title="節次" property="cls" sortable="true"/>  	
	  	<display:column style="width:100%;">
	  	<input type="hidden" name="cls" id="cls${row.Oid}" />
	  	<!--button name="method:delCls" id="${row.Oid}" class="btn btn-danger btn-sm check">刪除</button-->
	  	</display:column>
	</display:table>
	<div class="panel-footer">
	<div class="btn-group" role="group" aria-label="...">
		<button name="method:delCls" class="btn btn-danger chedk" id="${c.Oid}">刪除勾選</button>
		<a href="NabbrBoroRegister" class="btn btn-default">返回</a>
	</div>
	</div>
</div>
</c:if>




</form>

<script>
$(".timepick" ).datepicker({
	changeMonth: true,
	changeYear: true,
});

var chProp=false;
var chCls=false;
function check(type){
	if(type=="prop"){
		chProp=true;
		$("#clsTable").show("slow");
	}
	if(type=="cls")chCls=true;
	if(chCls && chProp)$("#create").prop("disabled", false );
}

$(".week").click(function(){	
	if(this.checked){
		$(".w"+this.value).prop("checked",true);
		
	}else{
		$(".w"+this.value).prop("checked",false);
	}	
})

$(".cls").click(function(){	
	if(this.checked){
		$(".c"+this.value).prop("checked",true);
		
	}else{
		$(".c"+this.value).prop("checked",false);
	}	
})


$("#room_id").change(function(){
	if(this.value.indexOf(",")>0){
		$.ajax({
			url:"/eis/getPropByNabbr",
		    dataType: 'jsonp',
		    jsonp:'back',          //jsonp請求方法
		    data:{roomid:this.value.substr(0, this.value.indexOf(","))},
		    cache:false,
		    type:'POST',
		    success: function(d) {    			    	
		    	createProps(d.list);
		    }
		});		
	}
})


function createProps(d){
	$("#prop").hide("slow");
	$("#prop").html("");	
	str="<div class='list-group'><a class='list-group-item active'>設備列表</a>";
	if(d.length<1){
		str+="<a class='list-group-item'>無設備建置於此空間, 可直接 <button type='button' class='btn btn-sm btn-primary'>選選擇節次</button> 或洽設備電子化建檔單位</a>";
	}else{
		for(i=0; i<d.length; i++){
			try{
				p=($("#users").val()/d[i].cnt)*100;
				if(p>100)p=100;
				str+="<a class='list-group-item'><input type='checkbox' name='propname' value='"+
				d[i].propname+"' /> "+d[i].propname+", 數量: "+d[i].cnt+"<div style='float:right; width:20%;' class='progress'><div class='progress-bar progress-bar-striped active'role='progressbar' aria-valuenow='"+p+"'aria-valuemin='0'aria-valuemax='100'style='width:"+p+"%;'>"+p+"%</div></div></a>";
			}catch(e){
				str+="<a class='list-group-item'><input type='checkbox' name='propname' value='"+d[i].propname+"' /> "+d[i].propname+", 數量 : "+d[i].cnt+"</a>";
			}
		}
	}
	
	str+="</div>";
	$("#prop").html(str);
	$("#prop").show("slow");
}
</script>


</body>
</html>