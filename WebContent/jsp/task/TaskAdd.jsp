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
<script src="/eis/inc/js/plugin/jquery.chained.min.js"></script>
<link href="/eis/inc/css/wizard-step.css" rel="stylesheet"/>

<script src="/eis/inc/js/plugin/jquery-ui.js"></script>
<script src="/eis/inc/js/plugin/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
<link href="/eis/inc/css/jquery-ui.css" rel="stylesheet"/>
<style>
    body .modal {
    /* new custom width */
    width: 60%;
    /* must be half of the width, minus scrollbar on the left (30px) */
    margin-left: -30%;
}
</style>
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

<div class="alert">
    <button type="button" class="close" data-dismiss="alert">&times;</button>
    <strong>工作單申請</strong>    
</div>
<div class="wizard-steps" style="margin-bottom:50px;">
  	<div><a href="#"><span>1</span> 選擇校區</a></div>
  	<div><a href="#"><span>2</span> 選擇單位</a></div>
  	<div><a href="#"><span>3</span> 選擇服務項目</a></div>
  	<div><a href="#"><span>4</span> 填寫內容</a></div>
  	<div><a href="#"><span>5</span> 儲存</a></div>
</div>


<form action="TaskAdd" method="post">

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span4">		
		<div class="tree well">
		    <ul>
		        <c:forEach items="${aunit}" var="c">
		        <li>
		        	<span><i class="icon-folder-open"></i> ${c.id}, ${c.name}</span>		        	
		            <ul>                
		                <c:forEach items="${c.unit}" var="s">		                
		                <c:if test="${s.tal>0}">
		                <li class="after">
			                <span>
			                	<i class="icon-plus-sign"></i> ${s.id}, ${s.name}
			                	<c:if test="${s.cnt>0 }"><button onClick="getTasks('${s.id}')" class="btn btn-small" type="button">申請服務</button></c:if>
			                </span>
			                			                
		                    <ul>
		                    	<c:forEach items="${s.sub_unit}" var="ss">
		                    	<c:if test="${ss.cnt>0}">
		                    	<li>
			                    	<span>
			                    		<i class="icon-minus-sign"></i> ${ss.id},${ss.name}
			                    		<button onClick="getTasks('${ss.id}')" class="btn btn-warning btn-small" type="button">${ss.cnt}項申請</button>
			                    	</span>
		                    	</li>
		                    	</c:if>
		                    	</c:forEach>           
		                    </ul>
		                </li>
		                </c:if>
		                </c:forEach>		                
		            </ul>
		        </li>
		        </c:forEach>
		    </ul>
		</div>		
		
		</div>
		<div class="span4" id="infoList" style="display:none;">
			<div class="tree well">
				<div id="info"></div>
			
			</div>
		</div>
		
		<div class="span4" id="appOne" style="display:none;">
			<div class="tree well">			
			<div id="formInfo">
			
			</div>
			</div>
		</div>
	</div>
</div>



<table class="table">
	<tr>
		<td>
		<div class="input-prepend">
		<span class="add-on">申請單位</span>
		<input id="unitSearch" value="${unitSearch}" name="unitSearch" data-provide="typeahead" onClick="this.value=''" placeholder="單位名稱或代碼" autocomplete="off" type="text"/>
		</div>
		</td>
		<td>
		<div class="input-prepend">
		<span class="add-on">開始日期</span>
		<input type="text" name="begin" class="dtpick" value="${begin}" autocomplete="off"/>
		</div>
		</td>
		<td>
		<div class="input-prepend">
		<span class="add-on">結束日期</span>
		<input type="text" name="end" class="dtpick" value="${end}" autocomplete="off" />
		</div>
		</td>
		<td nowrap>
		<button class="btn btn-danger" name="method:search">查詢</button>
		</td> 
		<td width="100%"></td>
	</tr>
</table>


<c:if test="${!empty myApps}">
<input type="hidden" id="Oid" name="Oid" />
<display:table name="${myApps}" id="row" class="table" sort="list" excludedParams="*" pagesize="10" requestURI="TaskAdd">
	
  	<display:column title="工作單編號" property="Oid" sortable="true" />
  	<display:column title="工作單名稱" property="title" sortable="true" />
  	<display:column title="申請單位" property="name" sortable="true"/>
  	<display:column title="申請日期" sortable="true"><fmt:formatDate value="${row.sdate}" pattern="yyyy年M月d日 HH:mm" /></display:column>
  	<display:column title="完成日期" sortable="true"><fmt:formatDate value="${row.edate}" pattern="yyyy年M月d日 HH:mm" /></display:column>
  	<display:column title="狀態" property="status" sortable="true" />
  	<display:column title="處理人員" property="cname" sortable="true" />
  	<display:column title="細節">
  	<a href="#taskAppInfo" class="btn btn-info" onClick="getTaskApp(${row.Oid})" data-toggle="modal">查看</a>
  	</display:column>
</display:table>
</c:if>
</form>



<!-- Modal -->
<div id="taskAppInfo" class="modal hide fade" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">×</button>
		<h3 id="title"></h3>
	</div>
	<div class="modal-body" id="appInfo"></div>
	<div class="modal-footer">
		<button class="btn" data-dismiss="modal" aria-hidden="true">關閉</button>
	</div>
</div>


<script>
$(".tree li:has(ul)").find( "li" ).hide('fast');

function getTaskApp(Oid){
	
	$.get("/eis/getTaskAppInfo?Oid="+Oid+"&"+Math.floor(Math.random()*999),
		    function(d){ 
				str="";
				str="<table class='table'>";
				$("#title").html(d.app.title);
				$("#appInfo").html("");
				
				str+="<tr><td nowrap>申請時間</td><td nowrap>"+d.app.sdate+"</td><td width='100%'></td></tr>";
				str+="<tr><td nowrap>完成時間</td><td nowrap>"+d.app.edate+"</td><td width='100%'></td></tr>";
				str+="<tr><td nowrap>申請人</td><td nowrap>"+d.app.cname+"</td><td width='100%'></td></tr>";
				str+="<tr><td nowrap>問題描述</td><td colspan='2'>"+d.app.note+"</td></tr>";
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
	$("#infoList").fadeOut();
	$("#infoList").fadeIn();
	$.get("/eis/getTaskInfo?unit="+unit+"&"+Math.floor(Math.random()*999),
    function(d){    		
   		str="<table class='table table-bordered table-hover'>";    		
   		$("#info").html("");
   		if(d.list.length>0){
   			for(i=0; i<d.list.length; i++){				
   				str+="<tr><td nowrap>"+d.list[i].title+"</td><td><button onClick='getForm("+d.list[i].Oid+")' class='btn btn-danger' type='button'>申請</button></td>";
   				
   			}
   		}
   		str=str+"</table>";   		
   		//$("#title").text(stdNo);
   		$("#info").append(str);    		
    }, "json");	
}

function getForm(Oid){
	$("#appOne").fadeOut();
	$("#appOne").fadeIn();
	str="<input type='hidden' name='addOid' value='"+Oid+"' />";	
	$.get("/eis/getTaskInfo?Oid="+Oid+"&"+Math.floor(Math.random()*999),
    function(d){	
   		$("#formInfo").html("");   					
   		str+="<textarea cols='80' name='appInfo' id='appInfo' rows='10'>"+d.list[0].template+"</textarea><br><button name='method:save' class='btn btn-danger'>完成</button></td>";
   		$("#formInfo").html(str); 
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