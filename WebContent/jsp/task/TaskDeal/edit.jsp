<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>工作單處理</title>
	<script src="http://192.192.230.167/CIS/inc/js/plugin/ckeditor/ckeditor.js"></script>
	<script src="/eis/inc/js/plugin/bootstrap-typeahead.js"></script>	
	<link href="/eis/inc/bootstrap/plugin/bootstrap-fileinput/css/fileinput.min.css" rel="stylesheet">
	<script src="/eis/inc/bootstrap/plugin/bootstrap-fileinput/js/fileinput.min.js"></script>
	<script src="/eis/inc/bootstrap/plugin/bootstrap-fileinput/js/fileinput_locale_zh-TW.js"></script>
	
	<script>
    $(document).ready(function(){   
    	//$('.collapse').collapse();
    	$("#next_empl").typeahead({
    		//remote:"#agent",
    		source : [],
    		items : 10,
    		updateSource:function(inputVal, callback){			
    			$.ajax({
    				url:"/eis/autoCompleteEmplOid",
    			    dataType: 'jsonp',
    			    jsonp:'back',          //jsonp請求方法
    			    data:{nameno:inputVal},
    			    cache:false,
    			    type:'POST',
    			    success: function(d) {
    			    	
    			    	callback(d.list);
    			    }
    			});
    		}		
    	});
    });
</script>
</head>
<body>
<div class="bs-callout bs-callout-danger" id="callout-helper-pull-navbar">

<h1 class="page-header">${task.Oid}</h1> 
<h2>${task.title}</h2> 
<p>申請時間: ${task.sdate}</p>
<p>申請人: ${task.cname}</p> 

</div>

<form action="TaskDeal" method="post" enctype="multipart/form-data">
<input name="Oid" type="hidden" value="${task.Oid}"/>


<c:if test="${task.note !=''}">
<div class="bs-callout bs-callout-warning" id=callout-overview-not-both>
<h4>申請內容說明</h4> 
<p>${task.note}</p>
</div>
</c:if>

<c:if test="${!empty appFiles}">
<div class="bs-callout bs-callout-info" id=callout-overview-dependencies>
<h4>附件</h4> 
<p><c:forEach items="${appFiles}" var="f" varStatus="i"><a class="btn btn-default" href="/eis/getFtpFile?file=${f.file_name}&path=${f.path}"><span class="glyphicon glyphicon-floppy-disk"></span> 附件${i.index+1}</a> </c:forEach></p>
</div>
</c:if>

<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
	<c:forEach items="${hist}" var="h" varStatus="i">
		<div class="panel panel-primary">
	    <div class="panel-heading" role="tab" id="headingOne">
	      <h4 class="panel-title">
	        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse${h.Oid}" aria-expanded="true" aria-controls="collapse${h.Oid}">
	          <fmt:formatDate value="${h.edate}" pattern="yyyy年M月d日 HH:mm" />, ${h.cname}</a>
	        </a>
	      </h4>
	    </div>
	    <div id="collapse${h.Oid}" class="panel-collapse collapse <c:if test='${i.index==0}'>in</c:if>" role="tabpanel" aria-labelledby="heading${h.Oid}">
	      <div class="panel-body">
	      ${h.reply}	      
	      <c:forEach items="${h.files}" var="f">	      
	      <a class="btn btn-default" href="/eis/getFtpFile?file=${f.file_name}&path=${f.path}"><span class="glyphicon glyphicon-floppy-disk"></span> 附件${i.index+1}</a>
	      </c:forEach>
	      </div>
	    </div>
	  </div>
	</c:forEach>
</div>


<table class="table control-group info">
	<c:if test="${task.status ne'H'}">
	<tr>
		<td>
		<div class="input-group">
      	<span class="input-group-addon">次階段處理人員</span>
			<input class="form-control" type="text" placeholder="教師完整姓名" class="span2" id="next_empl" name="next_empl" value="${task.emplOid}, ${task.emplName}" onClick="this.value='',$('#status').val('T')" autocomplete="off" data-provide="typeahead"/>
		</div>
		</td>
	</tr>
	<tr>
		<td>
		<div class="input-group">
      		<span class="input-group-addon">處理狀態</span>
			<select class="form-control" name="status" id="status">
				<c:forEach items="${CODE_TASK_STATUS}" var="s">
				<option <c:if test="${task.status eq s.id}">selected</c:if> value="${s.id}">${s.name}</option>
				</c:forEach>
			</select>
		</div>
		</td>
	</tr>
	</c:if>
	<tr>
		<td nowrap>
		<textarea id="reply" name="reply"></textarea>
		</td>
	</tr>
	<tr>
		<td>
		<label>附件</label>
		<small>點選「瀏覽檔案」後按住Ctrl鍵可選擇多個檔案</small>
		<input id="upload" name="fileUpload" multiple type="file" class="file-loading">
		</td>
	</tr>
	<c:if test="${task.status ne'H'}">
	<tr>
		<td nowrap>		
		<button class="btn btn-danger" name="method:deal">立即處理</button>		
		<a href="TaskDeal" class="btn">返回</a>
		</td>
	</tr>
	</c:if>
	<c:if test="${task.status eq'H'}">
	<tr>
		<td nowrap>	
		<select class="selectpicker" name="chk">
			<option>請選擇審核結果</option>
		  <optgroup label="確認工作單內容符合程序">
		    <option value="N">送出工作單</option>
		  </optgroup>
		  <optgroup label="工作單內容不符合程序">
		    <option value="R">退回工作單</option>
		  </optgroup>
		</select>

		<button class="btn btn-danger" name="method:check">處理</button>		
		<a href="TaskDeal" class="btn">返回</a>
		</td>
	</tr>
	</c:if>	
</table>
</form>

<script>
CKEDITOR.replaceAll( function( textarea, config )
	{	//uiColor: '#14B8C4',
		config.toolbar= [
			[ 'Bold', 'Italic', '-', 'NumberedList', 'BulletedList', '-', 'Link', 'Unlink' ],
			[ 'FontSize', 'TextColor', 'BGColor']
		]			
	}
);	

$("#upload").fileinput({
	//uploadUrl: "#",
	multiple: true,
	uploadAsync: false,
	//previewFileIcon: '<i class="fa fa-file"></i>',
	allowedPreviewTypes: null,
	language: "zh-TW",
	layoutTemplates: {
	    main1: "{preview}\n" +
	    "<div class=\'input-group {class}\'>\n" +
	    "   <div class=\'input-group-btn\'>\n" +
	    "       {browse}\n" +
	    //"       {upload}\n" +
	    "       {remove}\n" +
	    "   </div>\n" +
	    "   {caption}\n" +
	    "</div>"
	}
	,allowedFileExtensions: ["doc", "docx", "xls", "xlsx", "pdf", "jpg", "txt"]
});
</script>
</body>
</html>