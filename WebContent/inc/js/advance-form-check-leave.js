/**
 * 所有表單提示未儲存離開
 */
var isChange = false;
$(function () {
    //捕捉滑鼠切換
	$("input,textarea,select").change(function () {
        isChange = true;
        $(this).addClass("editing");
    });
	//捕捉鍵盤輸入
	$("input,textarea,select").keydown(function () {
        isChange = true;
        $(this).addClass("editing");
    });

    $(window).bind('beforeunload', function (e) {
        if (isChange || $(".editing").get().length > 0) {
            return '您的操作可能尚未完成，確定要離開？';
        }
    })
    
    $("button:submit").click(function() {
        $(window).unbind('beforeunload');
    });
});

$.ajaxSetup ({ 
	cache: false 
});

window.onbeforeunload = function() {
	$.unblockUI();
};