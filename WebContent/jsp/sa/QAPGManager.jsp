<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="/eis/inc/js/plugin/bootstrap-typeahead.js"></script>
<link href="/eis/inc/css/bootstrap-tree.css" rel="stylesheet"/>
<script src="http://192.192.230.167/CIS/inc/js/plugin/ckeditor/ckeditor.js"></script>
<script src="/eis/inc/js/plugin/jquery.chained.min.js"></script>
<script>  
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
   
<div id="dialog"></div>
<div class="bs-callout bs-callout-warning" id="callout-helper-pull-navbar">
<strong>程式管理</strong></div>


<form action="QAPGManager" method="post" class="form-horizontal">


<div class="container-fluid">
	<div class="row-fluid">
		<div class="span6">		
		<div class="tree well">
		    <ul>
		        <c:forEach items="${sys}" var="s">
		        <li>
		        	<span><i class="icon-folder-open"></i> ${s.Oid}, ${s.name}</span>		        	
		            <ul>                
		                <c:forEach items="${s.pg}" var="p">
		                <li class="after">
			                <span><i class="icon-plus-sign"></i> ${p.Oid}, ${p.name}</span>			                			                
		                    <ul>
		                    	<c:forEach items="${p.sub}" var="ps">
		                    	<li>
			                    	<span><i class="icon-minus-sign"></i> ${ps.Oid},${ps.name}</span>
		                    	</li>
		                    	</c:forEach>
		                    	<li>
						        	<span onClick="setForm(${p.Oid}, '${p.name}')" class="text-success" data-toggle="modal" data-target="#myModal">
		        					<i class="icon-folder-open"></i> 建立附屬子系統
		        					</span>
						        </li>           
		                    </ul>
		                </li>
		                </c:forEach>
		                <li>
				        	<span onClick="setForm(${s.Oid}, '${s.name}')" class="text-success" data-toggle="modal" data-target="#myModal">
		        			<i class="icon-folder-open"></i> 建立新子系統
		        			</span>
				        </li>		                
		            </ul>
		        </li>
		        </c:forEach>
		        <li>
		        	<span onClick="setForm(0, '新系統')" class="text-success" data-toggle="modal" data-target="#myModal">
		        	<i class="icon-folder-open"></i> 建立新系統
		        	</span> 
		        </li>
		    </ul>
		</div>		
		
		</div>
		
		<div class="span6">
			
		</div>
		
	</div>
</div>


<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		<h3 id="myModalLabel">Modal header</h3>
	</div>
	<div class="modal-body">
		<input type="hidden" id="parent" name="parent" />
		<table class="table">
				<tr>
					<td>
					<div class="input-prepend">
					<span class="add-on">功能名稱</span>
					<input type="text" id="name" placeholder="功能中文名稱" name="name"/>
					</div>
					</td>
				</tr>
				
				<tr>
					<td>
					<div class="input-prepend">
					<span class="add-on">方法路徑</span>
					<input type="text" id="action" placeholder="路徑不含專含路徑" name="action"/>
					</div>
					</td>
				</tr>
			</table>
	</div>
	<div class="modal-footer">
		<button class="btn" data-dismiss="modal" aria-hidden="true">關閉</button>
		<button type="submit" name="method:save" class="btn btn-primary">儲存</button>
	</div>
</div>

</form>

<script>
$(".tree li:has(ul)").find( "li" ).hide('fast');
function setForm(parent, name){
	//$("#Oid").val(Oid);
	$("#parent").val(parent);
	$("#myModalLabel").text(name);	
}

</script>

</body>
</html>