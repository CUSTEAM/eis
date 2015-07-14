<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/eis/inc/js/jquery.js"></script>
<script src="/eis/inc/js/plugin/json2.js"></script>
<title>Insert title here</title>









<script>
    $(document).ready(function(){
  
        $("#useJSONP").click(function(){
            $.ajax({
                url: 'http://ap.cust.edu.tw/eis/getStdScoreJsonp?stdNo=10114D010',
                data: {format: 'json'},
                dataType: 'jsonp',
                success: callback
            });
        });
  
    });
      
    function callback(data){
    	
    	for(i=0; i<data.list.length; i++){
    		$('#jsonpResult').append(data.list[i].school_year+"<br>");
    		
    		for(j=0; j<data.list[i].score.length; j++){
    			$('#jsonpResult').append(data.list[i].score[j].chi_name+"<br>");
    		}
    		
    	}
    	//$('#jsonpResult').append(data.list.length);
    	
    	
    }
    </script>
</head>
<body>
<div id="jsonpResult">


</div>
<button id="useJSONP">取得</button>
<button onClick="window.parent.$.unblockUI()">關閉</button>
</body>
</html>