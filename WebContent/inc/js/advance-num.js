/**
 * 數字唯寫
 */
function checkNum(id){	
	if($("#"+id).value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{$("#"+id).value=this.value.replace(/\D/g,'')}
}