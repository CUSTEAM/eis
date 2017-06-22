$.ajaxSetup({cache: false});
window.onbeforeunload=function(){$.unblockUI();};
$("#mainmenu").load("/eis/jsp/decorators/menu_3.jsp?r="+Math.floor(Math.random()*11));