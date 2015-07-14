<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>  
$(document).ready(function() {
	
	$('#funbtn').popover("show");
	setTimeout(function() {
		$('#funbtn').popover("hide");
	}, 3000);
	
	$("input[name='beginDate']").change(function(){
		//alert($("#beginDate").val());
		$("#endDate").val($("#beginDate").val());
	});	
});

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
</script>
</head>
<body>    
<div class="alert">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<strong>歷年未轉檔查核</strong> 
</div>
<!-- Modal -->
<div id="stdInfo" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		<h3 id="stdNameNo"></h3>
	</div>
	<div class="modal-body" id="info"></div>
	<div class="modal-footer">
		<button class="btn" data-dismiss="modal" aria-hidden="true">關閉</button>
	</div>
</div>
<form action="CheckNoHist" method="post" class="form-horizontal" onSubmit="$.blockUI({message:null});">
<table class="table">
	<tr>
		<td class="text-info" nowrap>班級範圍</td>
		<td class="control-group info" nowrap>
			<%@ include file="/inc/jsp-kit/dhnSelector.jsp"%>
			
			<div class="input-append">
			<input type="text" class="span1" name="year" value="${year}" />
			<span class="add-on">學年</span>
			</div>
			<select name="term">
				<option <c:if test="${term eq '1'}">selected</c:if> value="1">第1學期</option>
				<option <c:if test="${term eq '2'}">selected</c:if> value="2">第2學期</option>
			</select>
			
			<select name="gno">
				<c:forEach begin="1" end="4" var="g">
				<option <c:if test="${g eq gno}">selected</c:if> value="${g}">目前就讀${g}年級</option>
				</c:forEach>
			</select>
			
			<select name="target">
				<option <c:if test="${target eq 'ScoreHist'}">selected</c:if> value="ScoreHist">歷年成績</option>
				<option <c:if test="${target eq 'Stavg'}">selected</c:if> value="Stavg">平均、排名</option>
				<option <c:if test="${target eq 'comb1'}">selected</c:if> value="comb1">軍訓、體育、評語</option>
				<option <c:if test="${target eq 'cond'}">selected</c:if> value="cond">操行、全勤</option>
				歷年操行
			</select>
			<button class="btn btn-danger" name="method:check" type="submit" 
			onClick="$.blockUI({message:null});">執行</button>
		</td>
		<td width="100%"></td>
	</tr>
	
</table>

<c:if test="${!empty stds}">
<display:table name="${stds}" id="row" class="table table-condensed" sort="list" excludedParams="*" >
  	<display:column title="班級代碼" property="ClassNo" sortable="true" />
  	<display:column title="班級名稱" property="ClassName" sortable="true"/>  	
  	<display:column title="學號" property="student_no" sortable="true" />
  	<display:column title="姓名" property="student_name" sortable="true"/> 
  	<display:column title="">  	
  	<div class="btn-group">
    <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#"><i class="icon-list" style="margin-top:1px;"></i></a>
    <ul class="dropdown-menu">
    	<li><a href="#stdInfo" data-toggle="modal" onClick="getDilgInfo('${row.student_no}', '${row.student_name}', '')">缺課記錄</a></li>
    	<li><a href="#stdInfo" data-toggle="modal" onClick="getStdContectInfo('${row.student_no}', '${row.student_name}')">連絡資訊</a></li>
    	<li><a href="#stdInfo" data-toggle="modal" onClick="getStdScoreInfo('${row.student_no}', '${row.student_name}')">歷年成績</a></li>
    </ul>
    </div>
  	</display:column>	
  	<display:column title="身份" property="name" sortable="true"/>
</display:table>
</c:if>

</form>
</body>
</html>