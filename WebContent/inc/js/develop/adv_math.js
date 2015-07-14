//數學公用元件
/**
 * 偵測數字欄位
 * @param e
 * @param pnumber
 * @returns {Boolean}
 */
function ValidateNumber(e, pnumber){
	if (!/^\d+$/.test(pnumber)){
		$(e).val(/^\d+/.exec($(e).val()));
	}
	return false;
}