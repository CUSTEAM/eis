<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="/eis/inc/js/plugin/jquery.chained.min.js"></script>
<script>
function ImgError(source){  
    source.src = "/eis/img/icon/User-Black-Folder-icon.png";  
    source.onerror = "";  
    return true;  
} 

function upload(){
	//文件上傳地址
	//alert($("#fileType").val());
	    var url = "/eis/putSysDocFile?xtype="+$("#fileType").val()+"&docOid=${doc.getOid()}&"+Math.floor(Math.random()*999);
	    //$("input[id='qFile'], input[id='nFile'], input[id='rFile']" ).fileupload({
	    $('#myFile').fileupload({	    	
	        autoUpload: true,//自動上傳true
	        url: url,
	        dataType: 'json',	        
	        done: function (e, data) {
	            $.each(data.result.files, function (index, file) {//
	                if(file.name!=null){	                	
	                	$("#"+$("#fileType").val()).append("<img width='120' class='img-polaroid' src='/eis/getFtpFile?path=SysDoc/img&file="+file.name+"&"+Math.floor(Math.random()*999)+"'>&nbsp;");
	                }else{
	                	$("#files").html("必需是.jpg檔案");
	                }	            		            
	            });
	            $.unblockUI();
	        },
	        progressall: function (e, data) {
	        	$.blockUI({ 
	                theme:     true, 
	                title:    "影像處理中", 
	                message:  "<div id='progress' class='progress progress-success progress-striped'><div class='bar'></div></div>"
	            }); 
	            var progress = parseInt(data.loaded / data.total * 100, 10);
	            $('#progress .bar').css('width',progress+'%');
	        }
	    });
	};

	function getImage(src){
		$.blockUI({ 
            message: $('#selectImg'), 
            css: { 
                //top:  ($(window).height() - 600) /2 + 'px', 
                top:  '100px', 
                left: ($(window).width() - 600) /2 + 'px', 
                width: '600px' 
            } 
        }); 
		$('.blockOverlay').attr('title','Click to unblock').click($.unblockUI);
		$("#selectImg").html("<img width='600px' src='"+src+"'/>");
	}
</script>
<div id="selectImg" style="display:none"></div>
<input type="hidden" name="Oid" value="${doc.getOid()}"/>
<table class="table">
	<tr>
		<td nowrap>主旨</td>
		<td width="100%" class="control-group success">
		<select name="type">
			<option <c:if test="${type eq 'q'}">selected</c:if> value="q">系統問答</option>
			<option <c:if test="${type eq 'h'}">selected</c:if> value="h">操作說明</option>
			<option <c:if test="${type eq 'd'}">selected</c:if> value="d">系統文件</option>
		</select>
		<input type="text" class="span5" value="${doc.getTitle()}" name="title" value="" />
		</td>
	</tr>
	<tr>
		<td nowrap>所屬系統</td>
		<td width="100%" class="control-group success">
		<select id="parent_oid" onMouseup="checkSys();" name="parent_oid">
			<option value="">--</option>
			<c:forEach items="${allsys}" var="all">
			<option <c:if test="${parent_oid eq all.Oid}">selected</c:if> value="${all.Oid}">${all.name}</option>
			</c:forEach>
		</select>		
		<select id="sub_oid" onMouseup="checkSys();" name="sub_oid">	
			<option value="">--</option>
			<c:forEach items="${allsys}" var="all">
				<c:forEach items="${all.sub}" var="sub">
					<option <c:if test="${sub_oid eq sub.Oid}">selected</c:if> class="${sub.parent}" value="${sub.Oid}">${sub.name}</option>
				</c:forEach>
				
			</c:forEach>	
		</select>		
		<input type="hidden" name="sys" value="${doc.getSys()}" id="sys" />		
		</td>
	</tr>	
	<tr>
		<td nowrap>提出單位</td>
		<td width="100%" class="control-group <c:if test="${!empty doc.getSend_unit()}">success</c:if> <c:if test="${empty doc.getSend_unit()}">error</c:if>">
		
		<select id="campus" onMouseup="checkUnit();" name="campus">
			<option value="">--</option>			
			<c:forEach items="${allUnit}" var="c">
			<option <c:if test="${campus eq c.id}">selected</c:if> value="${c.id}">${c.name}</option>
			</c:forEach>
		</select>
		
		<select id="parent_unit" onMouseup="checkUnit();" name="parent_unit">
			<option value="">--</option>
			<c:forEach items="${allUnit}" var="c">
				<c:forEach items="${c.unit}" var="u">
				<option <c:if test="${parent_unit eq u.id}">selected</c:if> value="${u.id}" class="${c.id}">${u.name}</option>
				</c:forEach>
			</c:forEach>
		
		</select>
		
		<select id="sub_unit" onMouseup="checkUnit();" name="sub_unit">
			<option value="">--</option>
			<c:forEach items="${allUnit}" var="c">
				<c:forEach items="${c.unit}" var="u">
					<c:forEach items="${u.sub_unit}" var="s">
					<option <c:if test="${sub_unit eq s.id}">selected</c:if> value="${s.id}" class="${c.id} ${u.id}">${s.name}</option>
					</c:forEach>
				</c:forEach>
			</c:forEach>
		</select>
		
		<input type="hidden" name="send_unit" value="${doc.getSend_unit()}" id="send_unit" />
		
		</td>
	</tr>		
	</tr>
		<td colspan="2" class="control-group 
		<c:if test="${!empty doc.getSender()}">success</c:if> 
		<c:if test="${empty doc.getSender()}">error</c:if>">問題內容/回覆對象 ?
		<input type="text" class="span2" name="sender" value="${doc.getSender()}"/></td>		
	</tr>	
	</tr>
		<td colspan="2">
		<textarea name="question" class="jqte-test">${doc.getQuestion()}</textarea>
			
		<div id="q">
		<c:forEach items="${qFile}" var="q">					
			<img width="140" class='img-polaroid' onClick="getImage('/eis/getFtpFile?path=SysDoc/img&file=${q.file_name}')" onerror="ImgError(this);" src='/eis/getFtpFile?path=SysDoc/img&file=${q.file_name}&"+Math.floor(Math.random()*999)+"'>
			&nbsp;
			</c:forEach>
		</div>
		<br>
		<div onMouseDown="$('#fileType').val('q');">
		<input type="hidden" id="fileType"/>
		
		<span class="btn fileinput-button">		        
	        <span>上傳圖片</span>
	        <input id="myFile" name="myFile" type="file" onClick="upload();" onClick="$('#progress .bar').css('width','0%'),$('#files').html('');" multiple/>
	    </span>	    
	    <button class="btn" id="subInfo" name="method:save" type="submit">儲存</button>
	    <button class="btn btn-danger" id="subInfo" name="method:leave" type="submit" onClick="return(confirm('確定離開?')); void('')">離開</button>
		</div>		
		</td>
	</tr>	
	</tr>
		<td colspan="2" class="control-group <c:if test="${!empty doc.getTester()}">success</c:if> 
		<c:if test="${empty doc.getTester()}">error</c:if>">
		測試過程/人員 <input type="text" class="span2" value="${doc.getTester()}" name="tester" />
		<textarea name="note" class="jqte-test">${doc.getNote()}</textarea>		
		<div id="n">
		
			<c:forEach items="${nFile}" var="q">					
			<img width="140" class='img-polaroid' onClick="getImage('/eis/getFtpFile?path=SysDoc/img&file=${q.file_name}')" onerror="ImgError(this);" src='/eis/getFtpFile?path=SysDoc/img&file=${q.file_name}&"+Math.floor(Math.random()*999)+"'>
			&nbsp;
			</c:forEach>
		</div>
		<br>
		<button class="btn" type="button" onMouseDown="$('#fileType').val('n')" onClick="$('#myFile').trigger('click')">上傳圖片</button>
		<button class="btn" id="subInfo" name="method:save" type="submit">儲存</button>
		<button class="btn btn-danger" id="subInfo" name="method:leave" type="submit" onClick="return(confirm('確定離開?')); void('')">離開</button>
		</td>
	</tr>
		
	</tr>
		<td colspan="2" class="control-group <c:if test="${!empty doc.getEditor()}">success</c:if> 
		<c:if test="${empty doc.getEditor()}">error</c:if>">問題回覆/人員 <input type="text" class="span2" value="${doc.getEditor()}" name="editor" />
		<textarea name="reply" class="jqte-test">${doc.getReply()}</textarea>
		<div id="r">
			
			<c:forEach items="${rFile}" var="q">					
			<img width="140" class='img-polaroid' onClick="getImage('/eis/getFtpFile?path=SysDoc/img&file=${q.file_name}')" onerror="ImgError(this);" src='/eis/getFtpFile?path=SysDoc/img&file=${q.file_name}&"+Math.floor(Math.random()*999)+"'>
			&nbsp;
			</c:forEach>
		</div>	
		<br>
		<button class="btn" type="button" onMouseDown="$('#fileType').val('r')" onClick="$('#myFile').trigger('click')">上傳圖片</button>
		<button class="btn" id="subInfo" name="method:save" type="submit">儲存</button>
		<button class="btn btn-danger" id="subInfo" name="method:leave" type="submit" onClick="return(confirm('確定離開?')); void('')">離開</button>
		</td>
	</tr>	
	</tr>
		<td nowrap>審查人員</td>
		<td width="100%" class="control-group <c:if test="${!empty doc.getReview()}">success</c:if> 
		<c:if test="${empty doc.getReview()}">error</c:if>">
		<input type="text" class="span2" value="${doc.getReview()}" name="review" />
		</td>
	</tr>
	</tr>
		<td nowrap>核定人員</td>
		<td width="100%" class="control-group <c:if test="${!empty doc.getReview_final()}">success</c:if> 
		<c:if test="${empty doc.getReview_final()}">error</c:if>">
		<input type="text" class="span2" value="${doc.getReview_final()}" name="review_final" />
		</td>
	</tr>
</table>

<button class="btn" id="subInfo" name="method:save" type="submit">儲存</button>
<button class="btn btn-danger" id="subInfo" name="method:leave" type="submit" onClick="return(confirm('確定離開?')); void('')">離開</button>