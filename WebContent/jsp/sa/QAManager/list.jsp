<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<input type="hidden" id="Oid" name="Oid"/>
<display:table name="${docs}" id="row" class="table" sort="list" excludedParams="*" >
  	<display:column title="所屬系統" property="parentName" sortable="true" />
  	<display:column title="程式名稱" property="name" sortable="true"/>  	
  	<display:column title="標題" property="title" sortable="true"/>
  	<display:column title="完整性">
	    
	    	<c:choose>			    
			    <c:when test="${empty row.tester}">
			    	<div class="progress progress-danger progress-striped">
			       		<div class="bar" style="width: 20%;">未測試</div>
			       	</div>
			    </c:when>
			    
			    <c:when test="${empty row.editor}">
			       	<div class="progress progress-danger progress-striped">
			       		<div class="bar" style="width: 30%;">未回覆</div>
			       	</div>
			    </c:when>
			    
			    <c:when test="${empty row.review}">
			       	<div class="progress progress-warning progress-striped">
			       		<div class="bar" style="width: 50%;">未審核</div>
			       	</div>
			    </c:when>
			    
			    <c:when test="${empty row.review_final}">
				    <div class="progress progress-info progress-striped">
			       		<div class="bar" style="width: 75%;">未核定</div>
			       	</div>
			    </c:when>
			    
			    <c:otherwise>
			    	<div class="progress progress-success progress-striped">
			        	<div class="bar" style="width: 100%;">完成</div>
			        </div>
			    </c:otherwise>
			</c:choose>
	    	
	    
	</display:column>
  	<display:column title="編輯">
  		<a class="btn btn-small" href="QAManager?Oid=${row.Oid}">修改</a>
  		<button class="btn btn-small btn-danger" onMouseOver="$('#Oid').val(${row.Oid});" onClick="return(confirm('確定結算?')); void('')" name="method:delete" type="submit">刪除</button>
  	</display:column>
</display:table>