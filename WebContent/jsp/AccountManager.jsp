<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="article" id="articlelink" content="/technology/jquerynews/20120901-jquery-plugin-complexify/" />
<title>個人帳號密碼管理</title>
<script src="inc/js/plugin/bootstrap-typeahead.js" type="text/javascript"></script>
<script src="inc/js/plugin/bootstrap-tooltip.js" type="text/javascript"></script>
<script src="inc/js/plugin/complexify/js/jquery.complexify.js" type="text/javascript"></script>
<script src="inc/js/plugin/complexify/js/jquery.placeholder.min.js"></script>
<script src="inc/js/checkInput.js"></script>
<link href="inc/js/plugin/complexify/css/style.css" rel="stylesheet">
<style type="text/css">
    input {ime-mode: disabled; }
</style>
<script>  
$(document).ready(function() {	
	$('#funbtn').popover("show");
	setTimeout(function() {
		$('#funbtn').popover("hide");
	}, 5000);
	
	$('#q1').popover("show");
	setTimeout(function() {
		$('#q1').popover("hide");
	}, -1);	
	$('#q2').popover("show");
	setTimeout(function() {
		$('#q2').popover("hide");
	}, 3000);	
	$('#q3').popover("show");
	setTimeout(function() {
		$('#q3').popover("hide");
	}, -1);
	$('#q4').popover("show");
	setTimeout(function() {
		$('#q4').popover("hide");
	}, -1);
	$('#q5').popover("show");
	setTimeout(function() {
		$('#q5').popover("hide");
	},-1);	
});

$(function(){
	//$('input[placeholder]').placeholder();
	$("#password").complexify({}, function(valid, complexity){
		if (!valid) {
			$('#complexity').animate({'width':complexity + '%'}).removeClass('valid').addClass('invalid');
		} else {
			$('#complexity').animate({'width':complexity + '%'}).removeClass('invalid').addClass('valid');
		}
		$('#complexity').html(Math.round(complexity) + '%');
	});
});

function getAjaxReturn(freename){
	
	if(freename.length<5){
		$("#msg").text("帳號過短");
		$("#subInfo").attr("disabled", true);
		return;
	}
	
	if(!fucPWDchk(freename)){
		$("#msg").text("此帳號不可使用");
		$("#subInfo").attr("disabled", true);
		return;
	}
	
	$.ajax({ 
		type:"POST", 
		url:"/eis/checkAccountUnique", 
		data:"freename="+freename, 
		success:function(date){ 
			if(date.pass=="1"){ 
				$("#msg").text("此帳號已被使用");
				$("#subInfo").attr("disabled", true);
			}else{ 
				$("#msg").text("此帳號可以使用"); 
				$("#subInfo").attr("disabled", false);
			} 
		} 
	}); 
}

function checkPwd(pwd){
	
	if(pwd.length<5){
		$("#msg1").text("密碼過短");
		$("#subInfo").attr("disabled", true);
		return;
	}else{
		$("#msg1").text("");
	}
	
	
	if(!fucPWDchk(pwd)){
		$("#msg1").text("此密碼包含符號可能不便於記憶");
		$("#subInfo").attr("disabled", false);
		return;
	}
	
	$("#subInfo").attr("disabled", false);
	
}

function showPass(str){
	$("#q4").html(str.substring(str.length-1, str.length));	
}
</script>

</head>
<body>
<div class="alert alert-success" role="alert">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<strong>個人帳號密碼管理</strong> 
<div id="funbtn" rel="popover" title="說明" data-content="請定期更換密碼或自訂帳號" data-placement="right" class="btn btn-warning">?</div>
</div>

<form action="AccountManager" method="post" class="form-inline">
<div class="form-group has-success">
<table class="table" >
	<tr>
		<td nowrap>永久帳號</td>
		<td nowrap>
			<input type="text" class="form-control" disabled value="${wwpass.username}"/>
			<div id="q1" rel="popover" title="說明" data-content="系統預設帳號,永不變更" data-placement="right" class="btn btn-warning">?</div>
		</td>
		<td width="100"></td>
		<td width="100%"></td>
	</tr>
	<tr>
		<td nowrap>自訂帳號</td>
		<td nowrap>
			<input type="text" class="form-control" name="freename" value="${wwpass.freename}" onClick="$('#q2').popover('hide');" autocomplete="off" onKeyup="getAjaxReturn(this.value);"/>
			<div id="q2" rel="popover" title="說明" data-content="自訂帳號與永內帳號均可登入系統,在公共場合登入時可利用自訂帳號登入" data-placement="right" class="btn btn-warning">?</div>
			</td>
		<td nowrap id="msg"></td>
		<td></td>
	</tr>
	<tr>
		<td nowrap>現在密碼</td>
		<td nowrap>
			<input type="password" class="form-control" disabled value="${wwpass.password}"/>
			<div id="q3" rel="popover" title="說明" data-content="目前的密碼${wwpass.password}" data-placement="right" class="btn btn-warning">?</div>
		</td>
		<td nowrap><c:if test="${!empty wwpass.updated}"> 上次變更 ${fn:substring(wwpass.updated, 0, 16)}</c:if></td>
		<td></td>
	</tr>
	<tr>
		<td nowrap>變更密碼</td>
		<td nowrap>
			<input type="password" class="form-control" onKeyUp="showPass(this.value), $('#strength').show(), checkPwd(this.value);" id="password" name="password" autocomplete="off"/>
			<div id="q4" rel="popover" title="說明" data-content="變更密碼" data-placement="right" class="btn btn-warning">?</div>
		</td>
		<td id="msg1" nowrap></td>
		<td></td>
	</tr>
	<tbody id="strength" style="display:none;">
	<tr>
		<td nowrap>密碼強度</td>
		<td nowrap>
			<div id="complexitywrap">
				<div id="complexity">0%</div>
			</div>
			
		</td>
		<td>
			<div id="q5" rel="popover" title="說明" data-content="密碼強度僅供參考,過強的密碼可能不利於記憶" data-placement="right" class="btn btn-warning">?</div>
		</td>
		<td></td>
	</tr>
	</tbody>
	<tbody id="confirmsub">
	<tr>
		<td nowrap></td>
		<td nowrap>
		<button class="btn btn-success" id="subInfo" disabled name="method:update" onClick="javascript: return(confirm('請確認您輸入的資料後,按下確定')); void('')" type="submit">變更帳號資料</button>
		</td>
		<td>
			
		</td>
		<td></td>
	</tr>
	</tbody>
</table>


</div>
</form>    
<script>
var e =document.getElementById("password"); 
var r =e.createTextRange(); 
e.focus(); 
r.moveStart('character',2); 
r.collapse(true); 
r.select(); 
</script>
</body>
</html>