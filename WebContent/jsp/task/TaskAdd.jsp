<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工作單申請</title>
<script src="/eis/inc/js/plugin/bootstrap-typeahead.js"></script>
<link href="/eis/inc/css/bootstrap-tree.css" rel="stylesheet"/>
<script src="http://192.192.230.167/CIS/inc/js/plugin/ckeditor/ckeditor.js"></script>

<link href="/eis/inc/bootstrap/plugin/bootstrap-fileinput/css/fileinput.min.css" rel="stylesheet">
<script src="/eis/inc/bootstrap/plugin/bootstrap-fileinput/js/fileinput.min.js"></script>
<script src="/eis/inc/bootstrap/plugin/bootstrap-fileinput/js/fileinput_locale_zh-TW.js"></script>

<script src="/eis/inc/js/plugin/jquery.chained.min.js"></script>
<link href="/eis/inc/css/wizard-step.css" rel="stylesheet"/>

<script src="/eis/inc/js/plugin/jquery-ui.js"></script>
<script src="/eis/inc/js/plugin/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
<link href="/eis/inc/css/jquery-ui.css" rel="stylesheet"/>
<script>

$(document).ready(function(){	
	
	$("#unitSearch").typeahead({
		//remote:"#agent",
		source : [],
		items : 10,
		updateSource:function(inputVal, callback){			
			$.ajax({
				url:"/eis/autoCompAnyCode",
			    dataType: 'jsonp',
			    jsonp:'back',          //jsonp請求方法
			    data:{
			    	bootstrap:"1",
			    	idCol:"id",
			    	nameCol:"name",
			    	table:"CODE_UNIT",
			    	value:inputVal			    
			    },
			    cache:false,
			    type:'POST',
			    success: function(d) {    			    	
			    	callback(d.list);
			    }
			});
		}		
	});
	
});

$(function () {
    $('.tree li:has(ul)').addClass('parent_li').find(' > span').attr('title', '關閉');
    $('.tree li.parent_li > span').on('click', function (e) {
        var children = $(this).parent('li.parent_li').find(' > ul > li');
        if (children.is(":visible")) {
            children.hide('fast');
            $(this).attr('title', '展開').find(' > i').addClass('icon-plus-sign').removeClass('icon-minus-sign');
        } else {
            children.show('fast');
            $(this).attr('title', 'Collapse this branch').find(' > i').addClass('icon-minus-sign').removeClass('icon-plus-sign');
        }
        e.stopPropagation();
    });
});
</script>

</head>
<body>

<div class="bs-callout bs-callout-info" id="callout-helper-pull-navbar">
    <strong>工作單申請</strong>    
</div>
<div class="wizard-steps" style="margin-bottom:50px;">
  	<div><a href="#"><span>1</span> 選擇校區</a></div>
  	<div><a href="#"><span>2</span> 選擇單位</a></div>
  	<div><a href="#"><span>3</span> 選擇服務項目</a></div>
  	<div><a href="#"><span>4</span> 填寫內容</a></div>
  	<div><a href="#"><span>5</span> 儲存</a></div>
</div>


<form action="TaskAdd" method="post" enctype="multipart/form-data" class="form-inline">

<p><button name="method:add" style="width:100%;" class="btn btn-danger btn-lg"><span class="glyphicon glyphicon-cloud-upload"></span> 申請新工作單</button></p>

<div class="panel panel-primary">
	<div class="panel-heading"><span class="glyphicon glyphicon-th-list"></span> 申請單列表</div>
  	<div class="panel-body">
  	
    	<div class="input-group">
      	<span class="input-group-addon">單位</span>
			<select class="form-control" disabled>
				<option>所屬單位</option>
				<option>電子計算機中心</option>
			</select>
		
		</div>
		
		<div class="input-group">
      	<span class="input-group-addon">期間</span>
			<input class="form-control" type="text" placeholder="所有時間" value="" disabled autocomplete="off" data-provide="typeahead"/>
		</div>
		<button class="btn btn-danger" disabled>過濾</button>
  	</div>
	<display:table name="${myApps}" id="row" class="table" sort="list" excludedParams="*" pagesize="20" requestURI="TaskAdd">
		<display:column title="編號" property="Oid" sortable="true" style="white-space:nowrap;"/>
		<display:column title="名稱" property="title" sortable="true" style="white-space:nowrap;"/>
		<display:column title="單位" property="name" sortable="true" style="white-space:nowrap;"/>
		<display:column title="申請" sortable="true" style="white-space:nowrap;"><fmt:formatDate value="${row.sdate}" pattern="yyyy年M月d日 HH:mm" /></display:column>
		<display:column title="完成" sortable="true" style="white-space:nowrap;"><fmt:formatDate value="${row.edate}" pattern="yyyy年M月d日 HH:mm" /></display:column>
		<display:column title="狀態" property="status" sortable="true" style="white-space:nowrap;"/>
		<display:column title="人員" property="cname" sortable="true" style="white-space:nowrap;"/>
		<display:column title="細節">
		<a href="#taskAppInfo" class="btn btn-info" onClick="getTaskApp(${row.Oid})" data-toggle="modal">查看</a>
		</display:column>
	</display:table>
</div>
		
		
		
		

		  	
		  	
	
</form>
<!-- Modal -->
<div class="modal fade" id="taskAppInfo">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
			  <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			  <h4 class="modal-title" id="modelTitle"></h4>
			</div>
			<div class="modal-body" id="appInfo">
			  <p>Loading..</p>
			</div>
			<div class="modal-footer">
			  <button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
			  
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script>
function selUnit(no1, no2){
	if(no1!=null){
		if($('.class1'+no1).prop("checked")){
			$('.class1'+no1).attr("checked", false);
		}else{
			$('.class1'+no1).attr("checked", true);
		}
	}
	
}

$(".tree li:has(ul)").find( "li" ).hide('fast');

function getTaskApp(Oid){	
	$.get("/eis/getTaskAppInfo?Oid="+Oid+"&random="+Math.floor(Math.random()*999),
    function(d){ 
		$("#modelTitle").text(d.app.title);
		str="";
		str="<table class='table'>";
		$("#title").html(d.app.title);
		$("#appInfo").html("");
		
		str+="<tr><td nowrap>申請時間</td><td nowrap>"+d.app.sdate+"</td><td width='100%'></td></tr>";
		str+="<tr><td nowrap>完成時間</td><td nowrap>"+d.app.edate+"</td><td width='100%'></td></tr>";
		str+="<tr><td nowrap>申請人</td><td nowrap>"+d.app.cname+"</td><td width='100%'></td></tr>";
		
		if(d.app.taskFile.length>0){
			str+="<tr><td>附件 </td><td colspan='2'><div class='btn-group'>"
			for(i=0; i<d.app.taskFile.length; i++){
				str+="<a class='btn btn-default' href='/eis/getFtpFile?file="+d.app.taskFile[i].file_name+"&path="+d.app.taskFile[i].path+"'><span class='glyphicon glyphicon-floppy-save' aria-hidden='true'></span> 附件"+(i+1)+"</a>";
			}
			str+="</div></td></tr>"
			
		}
		
		var f;
		str+="<tr><td nowrap>申請單說明</td><td colspan='2'>"+d.app.note+"</td></tr>";
		if(d.app.hist.length>0){
			str+="<tr><td colspan='3'><b>已經過"+d.app.hist.length+"次處理</b></td></tr>";
			for(i=0; i<d.app.hist.length; i++){
				str+="<tr><td colspan='3'>"+d.app.hist[i].edate+" - "+d.app.hist[i].cname+"</td></tr>";
				str+="<tr><td colspan='3'>"+d.app.hist[i].reply+"<br>";
				f=d.app.hist[i].files;
				for(j=0; j<f.length; j++){
					str+="<a class='btn btn-default' href='/eis/getFtpFile?file="+f[j].file_name+"&path="+f[j].path+"'><span class='glyphicon glyphicon-floppy-save' aria-hidden='true'></span> 附件"+(j+1)+"</a> ";
				}
				str+="</td></tr>";
			}			
		}
		
		
		str+="</table>";
		$("#appInfo").append(str);
		
		
    }, "json");
	
}

$(".dtpick" ).datepicker();
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
//allowedFileExtensions: ["csv", "txt"]
});

</script>
</body>
</html>