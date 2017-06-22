<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>問卷管理</title>
<link href="/eis/inc/css/jquery-ui.css" rel="stylesheet"/>
<link href="/eis/inc/css/wizard-step.css" rel="stylesheet"/>
<link href="/eis/inc/bootstrap/plugin/bootstrap-fileinput/css/fileinput.min.css" rel="stylesheet">

<script src="/eis/inc/js/plugin/bootstrap-tooltip.js"></script>
<script src="/eis/inc/js/plugin/jquery-ui.js"></script>
<script src="/eis/inc/js/plugin/jquery-ui-timepicker-addon.js"></script>

<script src="/eis/inc/bootstrap/plugin/bootstrap-fileinput/js/fileinput.min.js"></script>
<script src="/eis/inc/bootstrap/plugin/bootstrap-fileinput/js/fileinput_locale_zh-TW.js"></script>
</head>
<body>
<div class="bs-callout bs-callout-warning" id="callout-helper-pull-navbar">
問卷管理
</div>
<form action="STDQAManager" method="post" class="form-inline" enctype="multipart/form-data">
<c:if test="${empty quest}">
<div class="panel panel-primary">
	<div class="panel-heading">新增或查詢問卷</div>
	
	<table class="table">
		<tr>
			<td>
			<div class="input-group">
			<span class="input-group-addon">問卷標題</span>
			<input style="width:400px;" value="${title}" class="form-control" type="text" placeholder="簡短、明確，可分辨的標題" name="title"/>
		</div>
			</td>
		</tr>
		<tr>
			<td>		
			<div class="input-group">
				<span class="input-group-addon">日期範圍</span>
				<input class="form-control date" value="${beginDate}" type="text" placeholder="點一下輸入日期" name="beginDate"/>
			</div>
			
			<div class="input-group">
				<span class="input-group-addon">至</span>
				<input class="form-control date" value="${enDate}" type="text" id="endDate" placeholder="點一下輸入日期" name="enDate"/>
			</div>
			
			</td>
		</tr>
	</table>
<div class="panel-body">
	
		<button class="btn btn-danger" name="method:create">建立新問卷</button>
		<button class="btn btn-default" name="method:search">查詢</button>
	
</div>
</div>
</c:if>

<c:if test="${!empty quests}">
<c:set var="now" value="<%=new Date()%>"/>
<input type="hidden" id="Oid" name="Oid" />
<div class="panel panel-primary">
	<div class="panel-heading">
	<c:if test="${empty inity}">問卷查詢列表</c:if>
	<c:if test="${!empty inity}">進行中的問卷</c:if>
	</div>
<display:table name="${quests}" id="row" class="table table-condensed" sort="list" excludedParams="*" >
 	<display:column style="white-space:nowrap;width:120px;">
 		<fmt:parseDate var="bdate" value="${row.beginDate}" type="DATE" pattern="yyyy-MM-dd"/> 		
 		
 		<button <c:if test="${bdate.getTime()<=now.getTime()}">disabled</c:if> type="button" onClick="try{winid1.close()}catch(e){};winid1=window.open('STDQAManager?method=edit&method:edit&Oid=${row.Oid}','_blank', 'directories=no,titlebar=no,toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no,width=1024, height=800,top=0,left=0');" class="btn btn-danger edit">編輯問卷</button>
		<button onclick="return confirm('請問是否刪除?包含:問卷、題目、選項，以及所有樣本');" class="btn btn-default" name="method:delete" onMouseOver="$('#Oid').val('${row.Oid}')">刪除</button>
		<button class="btn btn-default" name="method:print" onMouseOver="$('#Oid').val('${row.Oid}')">閱卷</button>
	</display:column>
 	<display:column style="white-space:nowrap;" title="標題" property="title" sortable="true" />
 	<display:column style="white-space:nowrap;" title="題數" property="qs" sortable="true"/>
 	<display:column style="white-space:nowrap;" title="開始日期" property="beginDate" sortable="true"/>
 	<display:column style="white-space:nowrap;" title="結束日期" property="enDate" sortable="true"/>
 	<display:column style="white-space:nowrap;" title="應收" property="cnt" sortable="true"/>
 	<display:column style="white-space:nowrap;" title="實收" property="rel" sortable="true"/> 	
 	<display:column style="white-space:nowrap;" title="建立" property="cname" sortable="true"/> 
</display:table>
</div>
</c:if>

<c:if test="${!empty quest}">
<input type="hidden" name="Oid" value="${quest.Oid}" />
<div class="panel panel-primary">
	<div class="panel-heading">標題與日期</div>
	<table class="table">
		<tr>
			<td>
			<div class="input-group">
			<span class="input-group-addon">問卷標題</span>
			<input style="width:400px;" value="${quest.title}" class="form-control" type="text" placeholder="簡短、明確，可分辨的標題" name="title"/>
		</div>
			</td>
		</tr>
		<tr>
			<td>		
			<div class="input-group">
				<span class="input-group-addon">日期範圍</span>
				<input class="form-control date" value="${quest.beginDate}" type="text" placeholder="點一下輸入日期" name="beginDate"/>
			</div>
			
			<div class="input-group">
				<span class="input-group-addon">至</span>
				<input class="form-control date" value="${quest.enDate}" type="text" id="endDate" placeholder="點一下輸入日期" name="enDate"/>
			</div>
			
			</td>
		</tr>
	</table>

</div>


<div class="panel panel-primary">
	<div class="panel-heading">問卷題目</div>
	<table class="table">
		<tr>
			<td style="vertical-align:bottom;"><a class="btn btn-success" href="jsp/sa/STDQAManager.xlsx">下載格式</a></td>
			<td nowrap>
			
				
					<input 
					name="upload" 
					type="file" 					
					id="upload" 
					class="file-loading"/>
					<script>
					$("#upload").fileinput({
						multiple: false,
						showCaption: false,
						showUpload: true,
				        browseLabel: "選擇上傳檔案",
				        browseIcon: "<i class=\"glyphicon glyphicon-level-up\"></i> ",
						
					    language: "zh-TW",
					    uploadUrl: "",
					    uploadClass:"upl btn btn-danger",
					    allowedFileExtensions: ["xlsx"]
					});		
					
					$('.upl').attr('name', 'method:upload');
					</script>			
			 		
					<!-- button class="btn btn-primary" style="width:100%;" id="saveTxtFile" 
					name="method:saveTxtFile" type="submit">
					
					
					<span class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></span>匯入問卷題目</button-->
					
		
			</td>
			<td nowrap style="vertical-align:bottom; width:100%;">
			
			<a href="STDQAManager?method=preview&method:preview&Oid=${Oid}" class="btn btn-primary"><span class="glyphicon glyphicon-sunglasses" aria-hidden="true"></span> 預覽問卷</a></td>
		</tr>
	</table>
</div>

<div class="panel panel-primary">
<div class="panel-heading">限制範圍</div>
<!--div class="panel-body">
<p>...</p>
</div-->
<table class="table">
	
	<tr class="tsel">
		<td><%@ include file="/inc/jsp-kit/classSelectorFullAll.jsp"%></td>
	</tr>	
	
	<tr class="tsex">
		<td>
		<div class="input-group">
			<span class="input-group-addon">性別</span>
			<select disabled class="form-control" name="sex">
				<option value="">全部</option>
				<option value="1">男</option>
				<option value="2">女</option>
			</select>
		</div>
		
		<div class="input-group">
			<span class="input-group-addon">身份別</span>
			<select disabled class="form-control" name="ident">
				<option value="">全部</option>
				<c:forEach items="${CODE_STMD_IDENT}" var="i">
				<option value="${i.id}">${i.name}</option>
				</c:forEach>
			</select>
		</div>
		</td>
	</tr>
	<tr class="tide">
		<td>		
		<div class="input-group">
			<span class="input-group-addon">生日範圍</span>
			<input disabled class="form-control date" type="text" placeholder="點一下輸入日期" name="beginDate"/>
		</div>
		
		<div class="input-group">
			<span class="input-group-addon">至</span>
			<input disabled class="form-control date" type="text" id="endDate" placeholder="點一下輸入日期" name="endDate"/>
		</div>
		
		</td>
	</tr>
	
	
	<tr class="tcls">
		<td>
		<div class="input-group">
			<span class="input-group-addon">到課日</span>
			<select disabled class="form-control"name="week" id="week" class="form-control">
			<option value=""></option>			
			<option value="1">星期一</option>
			<option value="2">星期二</option>
			<option value="3">星期三</option>
			<option value="4">星期四</option>
			<option value="5">星期五</option>
			<option value="6">星期六</option>
			<option value="7">星期日</option>
		</select>
		</div>
		
		
		<select disabled class="form-control"name="beginCls" id="beginCls" class="form-control">
			<option value=""></option>
			<c:forEach begin="1" end="14" varStatus="i">
			<option value="${i.index}">自第${i.index}節</option>
			</c:forEach>
		</select>		
		<select disabled class="form-control"name="endCls" id="endCls" class="form-control">
			<option value=""></option>
			<c:forEach begin="1" end="14" varStatus="i">
			<option value="${i.index}">至第${i.index}節</option>
			</c:forEach>
		</select>	
		
		<div rel="popover" title="說明" data-content="輸入星期與節次,回傳學生名單並標記範圍內有課的節次" data-placement="right" class="help btn btn-warning">?</div>	
		</td>
	</tr>
	<tr class="tadd">
		<td>
		<div class="input-group">
			<span class="input-group-addon">現居住址</span>
			<input disabled class="form-control" type="text" name="addr" />
		</div>
		<div rel="popover" title="說明" data-content="請以關鍵字查詢如：「台北」,「新北」,「南港」,「忠孝東路四段」查詢" data-placement="right" class="help btn btn-warning">?</div>
		</td>
	</tr>
	
	<tr class="tsch">
		<td>
		<div class="input-group">
			<span class="input-group-addon">畢業學校</span>
			<input disabled class="form-control" type="text" name="sch"/>
		</div>
		<div rel="popover" title="說明" data-content="請以關鍵字查詢如：「育達」,「協和」,「中華」,「大學」查詢" data-placement="right" class="help btn btn-warning">?</div>
		</td>
	</tr>	
	
	
</table>





<div class="panel-body">
<div class="btn-group">
		<button class="btn btn-danger" name="method:save">儲存</button>
		<button class="btn btn-default" type="button" onClick=" window.close();">關閉</button>
		</div>
</div>
</div>
</c:if>

</form>




<script>

$(".date" ).datepicker({
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