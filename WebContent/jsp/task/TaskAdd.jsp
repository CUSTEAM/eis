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


<form action="TaskAdd" method="post" enctype="multipart/form-data">


		<div class="panel panel-primary" id="unitList">
			<div class="panel-heading">建立新工作單</div>
			<div class="panel-body">
		    <p>點選校區開啟行政單位與 <button class="btn btn-warning btn-xs" type="button">申請項目</button> 列表</p>
		    <p>或點選多個單位 <button type="button" onClick="$('.batch').show('slow')" class="btn btn-primary btn-xs">批次建立</button> 工作</p>
		  	</div>

			<div class="tree table">
			    <ul>
			        <c:forEach items="${aunit}" var="c">
			        <li>
			        	<span><i class="icon-folder-open"></i> ${c.id}, ${c.name}</span> <input onClick="selUnit('${c.id}', null)" class="batch class${c.id}" style="display:none;" type="checkbox"/>
			            <ul>                
			                <c:forEach items="${c.unit}" var="s">
			                <li class="after">
				                <span>
				                	<i class="icon-plus-sign"></i> ${s.id}, ${s.name}
				                	<c:if test="${s.cnt>0 }"><button onClick="getTasks('${s.id}')" class="btn btn-sm" type="button">申請服務</button></c:if>
				                </span> <input onClick="$('.class2').attr('checked');" class="batch class1${c.id}" style="display:none;" type="checkbox"/>
				                <ul>
			                    	<c:forEach items="${s.sub_unit}" var="ss">			                    	
			                    	<li>
				                    	<span>
				                    		<i class="icon-minus-sign"></i> ${ss.id},${ss.name}
				                    		<a href="javascript:getTasks('${ss.id}')">${ss.cnt}項申請</a>
				                    	</span> <input class="batch batch class1${c.id} class2${s.id}" name="unit" style="display:none;" type="checkbox"/>
			                    	</li>			                    	
			                    	</c:forEach>           
			                    </ul>
			                </li>			                
			                </c:forEach>		                
			            </ul>
			        </li>
			        </c:forEach>
			    </ul>
			</div>		
		</div>		
		<div id="infoList" style="display:none;">
			
			<div class="panel panel-primary">
		  
			<div class="panel-heading" id="infoListTitle"></div>
		  	<div class="panel-body">
		    <p>請選擇申請項目或 <a onClick="$('#appOne').fadeOut('slow'), $('#infoList').fadeOut('slow'),$('#unitList').fadeIn('slow')">返回單位列表</a></p>    
		  	</div>
			<table id="info" class="table"></table>
			</div>
			
		</div>
		
		<div id="appOne" style="display:none;">
			<div class="panel panel-primary">
		  
			<div class="panel-heading" id="infoFormTitle">?</div>
		  	<div class="panel-body">
		    <p>請填寫申請或 <a onClick="$('#appOne').fadeOut('slow'),$('#infoList').fadeOut('slow'),$('#infoList').fadeIn('slow')">返回申請單列表</a></p>    
		  	</div>
		  	<table class="table">
		  		<tr>
		  			<td>
		  			<label for="exampleInputEmail1">申請單說明</label>
		  			<div id="formInfo">
				
					</div>
		  			</td>
		  		</tr>
		  		<tr>
		  			<td >
		  			<label>回覆電子信箱</label>
		  			<input class="form-control" type="text" name="email" value="${email}" /></td>
		  		</tr>
		  		<tr>
		  			<td>
		  			<label>附件<small><abbr title="Phone">(按住Ctrl鍵可選擇多個檔案)</abbr></small></label>
		  			<!--input style="width:100%;" name="fileUpload" type="file" data-show-upload="false" id="upload" multiple class="file file-loading"/-->
		  			<!--input id="upload" name="fileUpload" type="file" multiple class="file-loading"-->
		  			<input id="upload" name="fileUpload" multiple type="file" class="file-loading">
		  			</td>
		  		</tr>
		  		<tr>
		  			<td ><button name='method:save' class='btn btn-danger'>送出工作單</button></td>
		  		</tr>
		  	</table>
			
			</div>
		</div>
		
		
		
		
<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
	<div class="panel panel-primary">
    	<div class="panel-heading" role="tab" id="headingOne">
      	<h4 class="panel-title">
        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">進行中的工作單</a>
      	</h4>
    	</div>
    <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
		<div class="panel-body">
			<!-- div class="input-group">
	      	<div class="input-group-addon">申請單位</div>
			<input class="form-control" id="unitSearch" value="${unitSearch}" name="unitSearch" data-provide="typeahead" onClick="this.value=''" placeholder="單位名稱或代碼" autocomplete="off" type="text"/>
			</div>
			
			<div class="input-group">
	      	<div class="input-group-addon">開始日期</div>
			<input type="text" name="begin" class="dtpick form-control" value="${begin}" autocomplete="off"/>
			</div>
			
			<div class="input-group">
	      	<div class="input-group-addon">結束日期</div>
			<input type="text" name="end" class="dtpick form-control" value="${end}" autocomplete="off" />
			</div>				
			<button class="btn btn-danger" name="method:search">查詢</button-->
		</div>
		<display:table name="${myApps}" id="row" class="table" sort="list" excludedParams="*" pagesize="5" requestURI="TaskAdd">
		<display:column title="編號" property="Oid" sortable="true" />
		<display:column title="名稱" property="title" sortable="true" />
		<display:column title="單位" property="name" sortable="true"/>
		<display:column title="申請" sortable="true"><fmt:formatDate value="${row.sdate}" pattern="yyyy年M月d日 HH:mm" /></display:column>
		<display:column title="完成" sortable="true"><fmt:formatDate value="${row.edate}" pattern="yyyy年M月d日 HH:mm" /></display:column>
		<display:column title="狀態" property="status" sortable="true" />
		<display:column title="人員" property="cname" sortable="true" />
		<display:column title="細節">
		<a href="#taskAppInfo" class="btn btn-info" onClick="getTaskApp(${row.Oid})" data-toggle="modal">查看</a>
		</display:column>
		</display:table>
    </div>
  	</div>
	<div class="panel panel-primary">
	<div class="panel-heading" role="tab" id="headingTwo">
      	<h4 class="panel-title">
        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">已結案的工作單</a>
      	</h4>
    </div>
    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
      	<div class="panel-body">
			...
		</div>
		<display:table name="${myFin}" id="row" class="table" sort="list" excludedParams="*" pagesize="10" requestURI="TaskAdd">
		<display:column title="編號" property="Oid" sortable="true" />
		<display:column title="名稱" property="title" sortable="true" />
		<display:column title="單位" property="name" sortable="true"/>
		<display:column title="申請" sortable="true"><fmt:formatDate value="${row.sdate}" pattern="yyyy年M月d日 HH:mm" /></display:column>
		<display:column title="完成" sortable="true"><fmt:formatDate value="${row.edate}" pattern="yyyy年M月d日 HH:mm" /></display:column>
		<display:column title="狀態" property="status" sortable="true" />
		<display:column title="人員" property="cname" sortable="true" />
		<display:column title="細節">
		<a href="#taskAppInfo" class="btn btn-info" onClick="getTaskApp(${row.Oid})" data-toggle="modal">查看</a>
		</display:column>
		</display:table>
    </div>
</div>
  
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
		
		
		str+="<tr><td nowrap>申請單說明</td><td colspan='2'>"+d.app.note+"</td></tr>";
		str+="<tr><td colspan='3'><b>處理過程</b></td></tr>";
		for(i=0; i<d.app.hist.length; i++){
			str+="<tr><td colspan='3'>"+d.app.hist[i].edate+" - "+d.app.hist[i].cname+"</td></tr>";
			str+="<tr><td colspan='3'>"+d.app.hist[i].reply+"</td></tr>";
		}
		
		str+="</table>";
		$("#appInfo").append(str);
		
		
    }, "json");
	
}

function getTasks(unit){
	$("#unitList").fadeOut();	
	$("#infoList").fadeOut();
	$("#infoList").fadeIn();
	$.get("/eis/getTaskInfo?unit="+unit+"&random="+Math.floor(Math.random()*999),
    function(d){    		
   		//str="<table class='table table-bordered table-hover'>";    		
   		str="";
   		$("#info").html("");
   		if(d.list.length>0){
   			for(i=0; i<d.list.length; i++){				
   				str+="<tr><td><a href='javascript:getForm("+d.list[i].Oid+")'><span class='glyphicon glyphicon-menu-right' aria-hidden='true'></span> "+d.list[i].title+"</a> <span class='badge'>"+d.list[i].cnt+"</span></td>";
   				
   			}
   		}
   		//str=str+"</table>";  
   		$("#infoListTitle").text(d.unitName);
   		$("#info").append(str);    		
    }, "json");	
}

function getForm(Oid){
	
	$("#infoList").fadeOut();
	$("#appOne").fadeOut();
	$("#appOne").fadeIn();
	str="<input type='hidden' name='addOid' value='"+Oid+"' />";	
	$.get("/eis/getTaskInfo?Oid="+Oid+"&random="+Math.floor(Math.random()*999),
    function(d){	
   		$("#formInfo").html("");   					
   		str+="<textarea cols='80' name='appInfo' id='appInfo' rows='10'>"+d.list[0].template+"</textarea></td>";
   		$("#formInfo").html(str); 
   		$("#infoFormTitle").text(d.list[0].title);
   		CKEDITOR.replace('appInfo', {
   			toolbar: [
   				['Bold','Italic','-','NumberedList','BulletedList','-','Link','Unlink' ],
   				['FontSize','TextColor','BGColor']
   			]
   		});
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