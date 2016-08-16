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
	
	$("#upload").fileinput({
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
		//,previewFileType: ["doc", "docx", "xls", "xlsx", "pdf", "jpg", "txt"]
		//,allowedFileExtensions: ["doc", "docx", "xls", "xlsx", "pdf", "jpg", "txt"]
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
		    <p>點選校區開啟各行政單提供的服務位列表或 <a href="http://john.cust.edu.tw/eis/TaskAdd">返回申請單列表</a></p>
		    <p>點選 <button type="button" onClick="$('.batch').show('slow')" class="btn btn-primary btn-xs">批次建立</button> 多個單位的工作單</p>
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
			<div id="info"></div>
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
		  			<label>申請單說明</label>
		  			<div id="fileInfo"></div>
		  			<div id="formInfo"></div>
		  			</td>
		  		</tr>		  		
		  		<tr>
		  			<td>
		  			<label>附件</label>
		  			<small>點選「瀏覽檔案」後按住Ctrl鍵可選擇多個檔案</small>
		  			<!--input style="width:100%;" name="fileUpload" type="file" data-show-upload="false" id="upload" multiple class="file file-loading"/-->
		  			<!--input id="upload" name="fileUpload" type="file" multiple class="file-loading"-->
		  			<input id="upload" name="fileUpload" multiple type="file" class="file-loading">
		  			</td>
		  		</tr>
		  		<!--tr>
		  			<td >
		  			<label>回覆電子信箱</label>
		  			<input class="form-control" type="text" name="email" value="${email}" /></td>
		  		</tr-->
		  		<tr>
		  			<td ><button name='method:save' class='btn btn-danger'>送出工作單</button></td>
		  		</tr>
		  	</table>
			
			</div>
		</div>	
</form>


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

function getTasks(unit){
	$("#unitList").fadeOut();	
	$("#infoList").fadeOut();
	$("#infoList").fadeIn();
	$.get("/eis/getTaskInfo?unit="+unit+"&random="+Math.floor(Math.random()*999),
    function(d){    		
   		  		
   		str="";
   		$("#info").html("");
   		if(d.list.length>0){
   			str="<table class='table table-bordered table-hover'>";  
   			str+="<tr><td>工作單名稱</td><td width='1' nowrap>佇列</td><td>申請書</td></tr>";
   			for(i=0; i<d.list.length; i++){				
   				str+="<tr><td><a href='javascript:getForm("+d.list[i].Oid+")'><span class='glyphicon glyphicon-menu-right' aria-hidden='true'></span> "+d.list[i].title+"</a></td><td><span class='label label-as-badge label-danger'>"+d.list[i].cnt+"</span></td><td>";
   				for(j=0; j<d.files.length; j++){
   					str+="&nbsp;<a class='btn btn-default' href='/eis/getFtpFile?file="+d.files[j].file_name+"&path="+d.files[j].path+"'><span class='glyphicon glyphicon-floppy-disk'></span> 文件"+(j+1)+"</a> ";
   					
   				}
   				str+="</td>";
   			}
   			str=str+"</table>";  
   		}
   		
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
   		$("#fileInfo").html("");
   		str+="<textarea cols='80' name='appInfo' id='appInfo' rows='10'>"+d.list[0].template+"</textarea></td>";
   		$("#formInfo").html(str); 
   		$("#infoFormTitle").text(d.list[0].title);
   		str="";
   		if(d.files.length>0){
   			str+="<div class='alert alert-danger'>此項工作需附帶標準格式工作單，請依格式描述工作主旨，並在附件中加入填妥的檔案 ";
   			for(i=0; i<d.files.length; i++){
   				str+="&nbsp;<a href='/eis/getFtpFile?file="+d.files[i].file_name+"&path="+d.files[i].path+"'>申請文件"+(i+1)+"</a>, ";
   	   		}
   			str+="</div>";
   		}else{
   			str+="<div class='alert alert-danger'>此項工作無標準格式工作單，請依格式描述工作內容。</div>";
   		}
   		
   		$("#fileInfo").html(str);
   		CKEDITOR.replace('appInfo', {
   			toolbar: [
   				['Bold','Italic','-','NumberedList','BulletedList','-','Link','Unlink' ],
   				['FontSize','TextColor','BGColor']
   			]
   		});
    }, "json");		
	
}
$(".dtpick" ).datepicker();


</script>
</body>
</html>