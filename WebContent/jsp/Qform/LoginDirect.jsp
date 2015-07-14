<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>






	<c:if test="${!empty qlist}">
		
		<table class="table">
			<tr>
				<td>已完成的問卷</td>
			</tr>
			<c:forEach items="${qlist}" var="q">		
			<tr>
				<td>
				<input type="button" value="刪除" class="btn btn-danger btn-small" 
				onClick="window.location.replace('Aqansw?doid=${q.Oid}')" />
				${q.name}: ${q.anser}</td>
			</tr>
			</c:forEach>
		</table>
		
	</c:if>
	
	<c:if test="${!empty mydept}">
	
		
		
		
	<table class="table">				
		<tr>
			<td class="control-group error" nowrap>						
			
			<button class="btn" name="method:myDept" type="submit">開啟所屬科系問卷</button>
			<select name="dept">
				<c:forEach items="${mydept}" var="u">
				<option <c:if test="${dept==u.id2}">selected</c:if> value="${u.id2}">${u.name}</option>
				</c:forEach>
			</select>
			<span class="label label-important">若非您所屬的系，欄位可自由調整</span>
			
			</td>
		</tr>		
		
	</table>
		
		
	</c:if>
	
	
		
		<table class="table" >
			<tr>
				<td>
				<button class="btn" type="button" onClick="$('#other').show()">開啟其它科系問卷</button>
				</td>
			</tr>
		</table>
		
		<table class="table"  id="other" style="display:none;">
			<tr>
				<td class="hairLineTdF"></td>
				<td class="hairLineTdF">問卷名稱</td>
			</tr>
		<c:forEach items="${other}" var="u">		
		<tr>
			<td class="hairLineTdF" width="1" nowrap>
			<a class="btn btn-warning btn-small" href="Aqansw?uid=${u.id}&id2=${u.id2}">填寫</a>
			</td>
			<td class="hairLineTdF">${u.name }</td>
		</tr>		
		</c:forEach>
		</table>
		
		
		
		<table class="table">
			<tr>
				<td>
				
				<button class="btn" type="button" onClick="$('#units').show()">開啟行政單位問卷</button>
				 
				
				</td>
			</tr>
		</table>
		
		
		<table class="table" id="units" style="display:none;">
			<tr>
				<td class="hairLineTdF"></td>
				<td class="hairLineTdF">問卷名稱</td>
			</tr>
		<c:forEach items="${allunit}" var="u">		
		<tr>
			<td class="hairLineTdF" width="1" nowrap>
			<a class="btn btn-warning btn-small" href="Aqansw?uid=${u.id}&uid=${u.id}">填寫</a>
			
			
			</td>
			<td class="hairLineTdF">${u.name }</td>
		</tr>		
		</c:forEach>
		</table>
		
		