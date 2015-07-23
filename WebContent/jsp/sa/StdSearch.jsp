<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="/eis/inc/js/plugin/bootstrap-typeahead.js"></script>
<script src="/eis/inc/js/plugin/jquery-ui.js"></script>
<script src="/eis/inc/js/plugin/jquery-ui-timepicker-addon.js"></script>
<link href="/eis/inc/css/jquery-ui.css" rel="stylesheet"/>
<script>  




$(document).ready(function() {
	
	
	
	$("select[name='week'], select[name='beginCls'], select[name='endCls']" ).change(function() {
		if($("#week").val()+$("#beginCls").val()+$("#endCls").val()!=""){
			$(".tname, .tsex, .tadd, .tsch, .tsch, .tstds" ).hide("slow");
		}else{
			$(".tname, .tsex, .tadd, .tsch, .tsch, .tstds" ).show("slow");
		}
		  
	});
	
	
	
	$("#stds").keyup(function() {		
		if(this.value!=""){
			$(".tcls, .tsel,.tname, .tsex, .tadd, .tsch, .tsch" ).hide("slow");
		}else{
			$(".tcls, .tsel,.tname, .tsex, .tadd, .tsch, .tsch" ).show("slow");
		}
	});
		
	$("#stds").click(function(){
		if(this.style.width=='900px'){
			this.style.width='207px',this.style.height='20px';
		}else{
			this.style.width='900px',this.style.height='500px';
		}
		
	});
	
	
	
	$("input[id='stdName']").typeahead({
		remote:"#stdNo",
		source : [],
		items : 30,
		updateSource:function(inputVal, callback){			
			$.ajax({
				type:"POST",
				url:"/eis/autoCompleteStmd",
				dataType:"json",
				data:{length:10, nameno:inputVal},
				success:function(d){
					callback(d.list);
				}
			});
		}		
	});	
	
});
</script>

</head>
<body>


<form action="StdSearch" method="post" class="form-horizontal" enctype="multipart/form-data">
<table class="table">
	<tr class="tsel">
		<td><%@ include file="/inc/jsp-kit/classSelectorFull.jsp"%></td>
	</tr>
	<tr class="tname">
		<td>
		<div class="input-prepend">
			<span class="add-on">學號或姓名</span>
			<input class="span4" onClick="$('#stdName').val(''), $('#stdNo').val('');" autocomplete="off" type="text" id="stdName" value="${stdName}" name="stdName"
			 data-provide="typeahead" onClick="addStd()" placeholder="學號或姓名片段" />
		</div>		
		</td>
	</tr>
	
	<tr class="tsex">
		<td>
		<div class="input-prepend">
			<span class="add-on">性別</span>
			<select name="sex">
				<option <c:if test="${sex eq''}">selected</c:if> value="">全部</option>
				<option <c:if test="${sex eq'1'}">selected</c:if> value="1">男</option>
				<option <c:if test="${sex eq'2'}">selected</c:if> value="2">女</option>
			</select>
		</div>
		
		<div class="input-prepend">
			<span class="add-on">生日範圍</span>
			<input type="text" id="beginDate" placeholder="點一下輸入日期" name="beginDate" value="${beginDate}"/>
		</div>
		
		<div class="input-prepend">
			<span class="add-on">至</span>
			<input type="text" id="endDate" placeholder="點一下輸入日期" name="endDate" value="${endDate}"/>
		</div>			
		</td>
	</tr>
	<tr class="tcls">
		<td>
		<div class="input-prepend">
			<span class="add-on">到課日</span>
			<select name="week" id="week">
			<option value=""></option>			
			<option <c:if test="${week==1}">selected</c:if> value="1">星期一</option>
			<option <c:if test="${week==2}">selected</c:if> value="2">星期二</option>
			<option <c:if test="${week==3}">selected</c:if> value="3">星期三</option>
			<option <c:if test="${week==4}">selected</c:if> value="4">星期四</option>
			<option <c:if test="${week==5}">selected</c:if> value="5">星期五</option>
			<option <c:if test="${week==6}">selected</c:if> value="6">星期六</option>
			<option <c:if test="${week==7}">selected</c:if> value="7">星期日</option>
		</select>
		</div>
		
		
		<select name="beginCls" id="beginCls">
			<option value=""></option>
			<c:forEach begin="1" end="14" varStatus="i">
			<option <c:if test="${beginCls eq i.index}">selected</c:if> value="${i.index}">自第${i.index}節</option>
			</c:forEach>
		</select>		
		<select name="endCls" id="endCls">
			<option value=""></option>
			<c:forEach begin="1" end="14" varStatus="i">
			<option <c:if test="${endCls eq i.index}">selected</c:if> value="${i.index}">至第${i.index}節</option>
			</c:forEach>
		</select>		
		</td>
	</tr>
	<tr class="tadd">
		<td>
		<div class="input-prepend">
			<span class="add-on">現居住址</span>
			<input type="text" name="addr" value="${addr}"/>
		</div>
		<button class="btn" type="button">?</button>
		</td>
	</tr>
	
	<tr class="tsch">
		<td>
		<div class="input-prepend">
			<span class="add-on">畢業學校</span>
			<input type="text" name="sch" value="${sch}"/>
		</div>
		<button class="btn" type="button">?</button>
		</td>
	</tr>
	<tr class="tstds">
		<td>
		<div class="input-prepend">
			<span class="add-on">名單查詢</span>
			<textarea style="height:20px;" name="stds" id="stds">${stds}</textarea>
		</div>
		<button class="btn" type="button">?</button>
		</td>
	</tr>

	<tr>
		<td>
		<div class="btn-group">
		<button class="btn btn-danger" name="method:clSearch">查詢</button>
		<a href="StdSearch" class="btn">重新查詢</a>
		</div>
		</td>
	</tr>
</table>



</form>




<script>

$("input[name='beginDate'], input[name='endDate']" ).datepicker({
	changeMonth: true,
	changeYear: true,
	//minDate: '@minDate'
	yearRange: "-100:+0"
	//showButtonPanel: true,
	//dateFormat: 'yy-MM-dd'
});

//$("input[name='beginDate'], input[name='endDate']" ).datepicker();



</script>
</body>
</html>