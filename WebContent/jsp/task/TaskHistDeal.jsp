<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<!DOCTYPE html>
<html>
<head>
	<script src="http://192.192.230.167/CIS/inc/js/plugin/ckeditor/ckeditor.js"></script>
	<link href="/eis/inc/bootstrap/plugin/bootstrap-fileinput/css/fileinput.min.css" rel="stylesheet">
	<script src="/eis/inc/bootstrap/plugin/bootstrap-fileinput/js/fileinput.min.js"></script>
	<script src="/eis/inc/bootstrap/plugin/bootstrap-fileinput/js/fileinput_locale_zh-TW.js"></script>
	<script src="/eis/inc/js/plugin/multiselect.min.js"></script>
	<title>工作單處理..</title>
	<link rel="stylesheet" href="/eis/inc/css/wizard-step.css" />
</head>
<body>  


<div class="bs-callout bs-callout-danger">

<h4>${TaskInfo.title}</h4>
<p>${TaskInfo.cname} ${TaskInfo.student_no} ${TaskInfo.student_name} <small>${TaskInfo.sdate}</small></p>
<p>${TaskInfo.onote}</p>
<div class="btn-group" role="group" >
	<c:forEach items="${TaskFile}" var="f" varStatus="i">
  	<a href="/eis/getFtpFile?path=${f.path}&file=${f.file_name}" class="btn btn-default">附件${i.index+1}</a>  	
  	</c:forEach>  
</div>

<a href="TaskManager" class="btn btn-primary">返回列表</a>
</div>
<c:if test="${!empty TaskInfo.note}">
<div class="bs-callout bs-callout-warning">
<h4>${TaskInfo.fName}, ${TaskInfo.sdate}</h4>
<p>${TaskInfo.note}</p>
<div class="btn-group" role="group" >
	<c:forEach items="${histFile}" var="f" varStatus="i">
  	<a href="/eis/getFtpFile?path=${f.path}&file=${f.file_name}" class="btn btn-default">附件${i.index+1}</a>  	
  	</c:forEach>  
</div>
</div>
</c:if>


<form action="TaskManager" method="post" enctype="multipart/form-data">
<input type="hidden" name="Oid" value="${TaskInfo.Oid}" />
<input type="hidden" name="Task_oid" value="${TaskInfo.taskOid}" />
<input type="hidden" name="Task_hist_oid" value="${TaskInfo.Task_hist_oid}" />
<input type="hidden" id="close" name="close" />
<c:if test="${!empty TaskHistInfo}">
<div class="panel panel-primary">
	<div class="panel-heading">合作單位</div>
	<table class="table table-hover">
	<tr>
		<th></th>
		<th></th>
		<th>合作單位</th>
		<th>附件</th>
		<th>送出</th>
		<th nowrap>回覆</th>
	</tr>
    <c:forEach items="${TaskHistInfo}" var="h">
	<tr>
		<td nowrap width="80">		
		<c:if test="${h.open eq '0'}"><span class="label label-default">已結案</span></c:if>
		<c:if test="${h.open eq '1'}"><span class="label label-danger">未結案</span></c:if>
		
		</td>
		
		<td nowrap>
		
		<c:if test="${empty h.feedback}"><a disabled class="btn btn-default">檢視</a></c:if>
		<c:if test="${!empty h.feedback}"><a href="#taskAppInfo" onClick="getTaskHist(${h.Oid});" data-toggle="modal"  class="btn btn-primary">檢視</a></c:if>
		<button onClick="$('#close').val(${h.Oid}); return(confirm('確定結案?'));" name="method:close" class="btn btn-default">結案</button>
		
		</td>
		
		<td nowrap>${h.cname}<c:if test="${!empty h.unit}">【${h.unit}】</c:if></td>
		<td width="100%">
		<div class="btn-group" role="group" >
			<c:forEach items="${h.files}" var="f" varStatus="i">
		  	<a href="/eis/getFtpFile?path=${f.path}&file=${f.file_name}" class="btn btn-default">附件${i.index+1}</a>  	
		  	</c:forEach>  
		</div>
		<div class="btn-group" role="group" >
			<c:forEach items="${h.bFiles}" var="f" varStatus="i">
		  	<a href="/eis/getFtpFile?path=${f.path}&file=${f.file_name}" class="btn btn-primary">回件${i.index+1}</a>  	
		  	</c:forEach>  
		</div>		
		</td>
		<td width="100" nowrap>${fn:substring(h.sdate, 5, 10)}</td>
		<td width="100" nowrap>${fn:substring(h.edate, 5, 10)}</td>
		
		
	</tr>
	</c:forEach>
  	</table>
</div>
</c:if>





<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
	<div class="panel panel-primary">
	    <div class="panel-heading" role="tab" id="headingOne">
	      	<h4 class="panel-title">
	        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" 
	        aria-expanded="true" aria-controls="collapseOne">直接回覆</a>	        
	      	</h4>
	    </div>
	    <div class="panel-body" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" 
	        aria-expanded="true" aria-controls="collapseOne">
	    	直接回覆此工作單處理結果，等待申請人結案。<br><small>若上層單位為學生申請將會直接結案</small>
	    	
	        
	    </div>
    	<div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
	      	<div class="panel-body">
	      	<textarea class="note" name="feedback" id="feedback" rows='1'/>${TaskInfo.feedback}</textarea>
	      	
			
			<c:if test="${!empty myFile}"><p>
			<div class="btn-group" role="group" >
				<c:forEach items="${myFile}" var="f" varStatus="i">
			  	<a href="/eis/getFtpFile?path=${f.path}&file=${f.file_name}" class="btn btn-default">附件${i.index+1}</a>  	
			  	</c:forEach>  
			</div>
			</p></c:if>
			
			<p>
	      	<label>附件</label>
			<small>點選「瀏覽檔案」後按住Ctrl鍵可選擇多個檔案</small>
			<input id="upload" name="fileUpload" multiple type="file" class="file-loading">
			</p>
			<button style="width:100%;font-size:20px;" class="btn btn-danger" name="method:back"><span class="glyphicon glyphicon-share-alt" aria-hidden="true"></span> 回覆</button>
	      	</div>
    	</div>
  	</div>
  	<div class="panel panel-primary">
	    <div class="panel-heading" role="tab" id="headingTwo">
	      	<h4 class="panel-title">
	        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" 
	        href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">尋找合作伙伴</a>
	      	</h4>
	    </div>
	    <div class="panel-body"role="button" data-toggle="collapse" data-parent="#accordion" 
	        href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">將工作單複製給其他單位，等待其他單位回覆後再進行處理<br><small>無主管的單位將不會顯示在列表中</small>
	    
	    </div>
    	<div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
      	<div class="panel-body">        
        	
	    <div class="row">
	        <div class="col-sm-5">
	        <select onChange="getUnit(this.value);"  id="optgroup" style="font-size:18px;"class="optgroup form-control" size="4" multiple="multiple">
                  <c:forEach items="${ulist}" var="u">					
				<option value="${u.idno}">【${u.name}】${u.cname}</option>
				</c:forEach>
	        </select>		
	        <select id="optgroup" onClick="document.getElementById('optgroup').selectedIndex = -1;" style="margin-top:5px;font-size:18px;" class="optgroup1 optgroup form-control"  multiple="multiple"></select>
	        </div>		        
	        <div class="col-sm-1">
	            <button type="button" id="optgroup_rightAll" disabled class="btn btn-block btn-primary"><i class="glyphicon glyphicon-forward"></i></button>
	            <button type="button" id="optgroup_rightSelected" class="btn btn-block btn-primary"><i class="glyphicon glyphicon-chevron-right"></i></button>
	            <button type="button" id="optgroup_leftSelected" class="btn btn-block btn-primary"><i class="glyphicon glyphicon-chevron-left"></i></button>
	            <button type="button" id="optgroup_leftAll" class="btn btn-block btn-primary"><i class="glyphicon glyphicon-backward"></i></button>
	        </div>
	        
	        <div class="col-sm-6">
	            <select name="units" id="optgroup_to" style="font-size:18px;"class="form-control" size="9"  multiple="multiple">
	                
	            </select>
	        </div>
	    </div>
		
		<label>附註</label>
		<small>處理過程中的文字記錄, 不會顯示在回覆內容中</small>
		<textarea class="note" name="subNote" id="subNote" rows='1'/></textarea>
		<p>
		<label>附件</label>
		<small>點選「瀏覽檔案」後按住Ctrl鍵可選擇多個檔案</small>
		<input id="upload1" name="fileUpload1" multiple type="file" class="file-loading">
		</p>
		
        <button style="width:100%;font-size:20px;" class="btn btn-danger" name="method:send" style="width:100%;"><span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span> 加入合作伙伴</button>
        </div>
        
      	</div>
    </div>
</div>
<c:if test="${!empty myReaction}">
<div class="panel panel-primary">
	<div class="panel-heading">意見反應列表</div>
<table class="table">
	<tr>
		<th>申請日期</th>
		<th nowrap>意見反應主題</th>
		<th>回覆日期</th>
	</tr>
	<c:forEach items="${myReaction}" var="a">
	<tr>
		<td nowrap>${a.sdate}</td>
		<td nowrap>${a.title}</td>
		<td width="100%">${a.edate}</td>
	</tr>
	</c:forEach>
</table>
</div>
</c:if>
</form>

<!-- Modal -->
<div class="modal fade" id="taskAppInfo">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
			  <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			  <h4 class="modal-title" id="modelTitle"></h4>
			</div>
			<div class="modal-body" id="appInfo"></div>
			<div class="modal-footer">
			  <button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
			  
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<script>
CKEDITOR.config.toolbar =[
	['Bold','Italic','-','NumberedList','BulletedList','-','Link','Unlink' ],
	['FontSize','TextColor','BGColor']
];
CKEDITOR.replaceAll("note");
function getTaskHist(Oid){
	//var files;
	$.ajax({
		  url: "/eis/getTaskInfo",
		  type: "get", //send it through get method
		  data:{ 		    
		    histOid: Oid, 		    
		  },
		  success: function(data) {
			  d=data.list[0];
			  $("#appInfo").text("");
			  $("#modelTitle").text("#"+Oid+" 內容");
			  info="<p>";
			  if(d.files!=null){
				  info+="<div class='btn-group' role='group'>"
				  for(i=0; i<d.files.length; i++){
					  if(d.files[i]!=null){
						  info+="<a href='/eis/getFtpFile?path="+d.files[i].path+"&file="+d.files[i].file_name+"' class='btn btn-default btn-sm'>附件"+(i+1)+"</a>";
					  }					  
				  }	
				  info+="</div>"
			  }
			  /*if(d.bFiles!=null){
				  info+="&nbsp;<div class='btn-group' role='group'>"
				  for(i=0; i<d.bFiles.length; i++){
					  if(d.bFiles[i]!=null){
						  info+="<a href='/eis/getFtpFile?path="+d.bFiles[i].path+"&file="+d.bFiles[i].file_name+"' class='btn btn-primary'>回件"+(i+1)+"</a>";
					  }					  
				  }	
				  info+="</div>"
			  }*/
			  info+="</p>";
			  $("#appInfo").append("<blockquote><p>"+d.sdate+"申請</p><footer>附註: <cite title='Source Title'>"+d.note+"</cite></footer></blockquote>");	
			  $("#appInfo").append(info);	
			  
			  $("#appInfo").append("<blockquote><p>"+d.cname+" 於"+d.edate+"回覆</p>"+d.feedback+"</blockquote>");	
			  if(d.bFiles!=null){
				  info="&nbsp;<div class='btn-group' role='group'>"
				  for(i=0; i<d.bFiles.length; i++){
					  if(d.bFiles[i]!=null){
						  info+="<a href='/eis/getFtpFile?path="+d.bFiles[i].path+"&file="+d.bFiles[i].file_name+"' class='btn btn-sm btn-primary'>回件"+(i+1)+"</a>";
					  }					  
				  }	
				  info+="</div>"
			  }
			  $("#appInfo").append(info);
		  },
		  error: function(xhr) {
		    //Do Something to handle error
		  }
	});
}

$(document).ready(function(){		
	$(".optgroup").multiselect();
	$(".file-loading").fileinput({
		//uploadUrl: "#",
		multiple: true,
		uploadAsync: false,
		maxFileCount: 10,
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
	});	
});

function getUnit(lid){	
	
	$(".optgroup1").html("");
	for(i=0; i<units.length; i++){
		if(units[i].lid==lid&&units[i].cname!="")
		$(".optgroup1").append("<option value='"+units[i].idno+"'>"+"【"+units[i].name+"】"+units[i].cname+"</option>" );
		
	}		
}
units=[<c:forEach items="${ulist}" var="l"><c:forEach items="${l.units}" var="u">{lid:"${u.lid}",pid:"${u.pid}", idno:"${u.idno}", name:"${u.name}", cname:"${u.cname}"},</c:forEach></c:forEach>]
</script>
</body>
</html>