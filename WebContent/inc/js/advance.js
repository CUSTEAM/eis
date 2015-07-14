

$(document).ready(function() {
	$("#ActLink").change(function() {
		$.blockUI({
			message : null,
			onOverlayClick : $.unblockUI
		});
	});
	
	$("#mainmenu").load("/eis/jsp/decorators/menu.jsp?r="+Math.floor(Math.random()*11));
});