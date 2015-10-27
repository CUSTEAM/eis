<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div id="menu" class="navbar navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container-fluid">
			<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
			<span class="icon-bar"></span>
			<span class="icon-bar"></span> 
			<span class="icon-bar"> </span></a>
			<a class="brand" href="/eis/Calendar">中華科技大學</a>			
			<div class="nav-collapse collapse navbar-inverse-collapse">	
			<!-- 未登入 -->
			
				
				
				
			<ul class="nav">									
				
				
				
				
				
				
				
				
				
				
				
				<c:forEach items="${sysmenu}" var="m">
				<c:forEach items="${m.rule}" var="r">				
				<c:if test="${fn:indexOf(cookie['unit'].value, r.unit_id)>-1}">				
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">${m.name} <b class="caret"></b></a>
					<ul class="dropdown-menu">					
					
					<c:forEach items="${m.menu}" var="mm">
					<c:if test="${fn:length(mm.menu)<1}">						
						<li><a href="${mm.path}"> ${mm.name}</a></li>
					</c:if>
					
					<c:if test="${fn:length(mm.menu)>0}">					
					
					<li class="dropdown-submenu"><a> ${mm.name}</a>					
						<ul class="dropdown-menu">
							<c:forEach items="${mm.menu}" var="mmm">							
							<c:if test="${fn:length(mmm.menu)<1}">
							<li><a href="${mmm.path}">${mmm.name}</a></li>
							</c:if>
							
							<c:if test="${fn:length(mmm.menu)>0}">
							<li class="dropdown-submenu">
								<a tabindex="0" data-toggle="dropdown" data-submenu>${m.name}</a>
								<ul class="dropdown-menu">
								<c:forEach items="${mmm.menu}" var="mmmm">
									<li><a href="${mmmm.path}">${mmmm.name}</a></li>
								</c:forEach>
								</ul>
							</li>
							</c:if>						
							</c:forEach>
						</ul>
					</li>					
					</c:if>										
					</c:forEach>					
					</ul>				
				</li>				
				</c:if>				
				</c:forEach>			
			</c:forEach>	
			</ul>							
			</div>	
			
		</div>
	</div>
</div>
<script>
function getHelpPage(){
	$.blockUI({ 
		
	    message: '<Iframe style="border-radius:15px;" id="coansw" src="/pis/SysDoc"; width="100%" height="100%" frameborder="0"></iframe>', 
	    css: { 
	    	width: '60%', 
	        height:'80%',
	        top: '10%', 
	        left: '20%', 
	        right: '20%', 
	        border: 'none',
	        'border-radius':'15px',
			'box-shadow': '10px 10px 20px #000'
	    }
	});
	$('.blockOverlay').attr('title','Click to unblock').click($.unblockUI);
}
</script>