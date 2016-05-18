<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>

<script src="/eis/inc/js/plugin/ChartJS/ChartNew.js"></script>
<script src="/eis/inc/js/plugin/ChartJS/ChartColor.js"></script>

<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">  

</script>
</head>
<body>
<div class="bs-callout bs-callout-info" id="callout-helper-pull-navbar">
<b>缺曠趨勢</b> 請點選院或系的名稱檢視細部資訊</div>
<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
  
  
  <div class="panel panel-primary">
    <div class="panel-heading" role="tab" id="headingOne">
      <h4 class="panel-title">
        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
          	全校趨勢
        </a>
      </h4>
    </div>
    <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
      <div class="panel-body">
      <canvas id="linechart_sch" width="1000" height="300"></canvas>
      </div>
    </div>
  </div>
  
  
  
  
  
	<div class="panel panel-primary">
	    <div class="panel-heading" role="tab" id="headingTwo">
	      <h4 class="panel-title">
	        <a class="collapsed" role="button" 
	        data-toggle="collapse" 
	        data-parent="#accordion" 
	        href="#collapseTwo" 
	        aria-expanded="false" 
	        aria-controls="collapseTwo">院趨勢</a>
	      </h4>
	    </div>
	    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
	      <div class="panel-body">
	      <canvas id="linechart_col" width="1000" height="600"></canvas>
			<div id="calendar_sch"></div>
	      </div>
	    </div>
  	</div>  
  
	<c:forEach items="${col}" var="c" varStatus="i">
	<div class="panel panel panel-primary">
		<div class="panel-heading" role="tab" id="Eheading${c.id}" onClick="getChart${c.id}${c.id}()">
	    <h4 class="panel-title">
	        <a class="collapsed" 
	        role="button" 
	        data-toggle="collapse" 
	        data-parent="#accordion" 
	        href="#Ecollapse${c.id}" 
	        aria-expanded="false" 
	        aria-controls="Ecollapse${c.id}">
	        <b>${c.name}</b></a>
	    </h4>
	    </div>
	    <div id="Ecollapse${c.id}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="Eheading${c.id}">
	      <div class="panel-body">
	      <canvas  id="linechart_col${i.index}" width="1000" height="600"></canvas >
			<div id="Ecollapse${c.id}"></div>
	      </div>
	    </div>
    </div>
	
	<c:forEach items="${dep}" var="d">					
	<c:if test="${d.college eq c.id }">					
	<div class="panel panel-default">
	<div class="panel-heading" role="tab" id="heading${d.id}" onClick="getChart${d.id}()">
    <h4 class="panel-title">
	    <a class="collapsed" 
	    role="button" 
	    data-toggle="collapse" 
	    data-parent="#accordion" 
	    href="#collapse${d.id}" 
	    aria-expanded="false" 
	    aria-controls="collapse${d.id}">
	    ${d.name}</a>
    </h4>
    </div>
    <div id="collapse${d.id}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading${d.id}">
      <div class="panel-body">
      <canvas  id="linechart_dep${d.id}" width="1000" height="300"></canvas >
		<div id="calendar_col${d.id}"></div>
      </div>
    </div>
    </div>
	</c:if>								
	</c:forEach>		
	</c:forEach>
</div>
<canvas style="display:none;" id="linechart_depx" width="1000" height="300"></canvas>

<script>



var op={
		
	inGraphDataShow : true,
	datasetFill : true,	
	graphTitleFontFamily : "'Microsoft JhengHei'",
	graphTitleFontSize : 24,
	graphTitleFontStyle : "bold",
	graphTitleFontColor : "#666",
	//graphSubTitle : "每週缺曠加總",
	graphSubTitleFontFamily : "'Microsoft JhengHei'",
	graphSubTitleFontSize : 18,
	graphSubTitleFontStyle : "normal",
	graphSubTitleFontColor : "#666",	
	legend : true,
	legendFontFamily : "'Microsoft JhengHei'",
	legendFontSize : 12,
	legendFontStyle : "normal",
	legendFontColor : "#666",
    legendBlockSize : 15,
    legendBorders : false,		
    annotateDisplay : true, 
    spaceTop : 0,
    spaceBottom : 0,
    spaceLeft : 0,
    spaceRight : 0,
    logarithmic: false,	      
    rotateLabels : "smart",
    xAxisSpaceOver : 0,
    xAxisSpaceUnder : 0,
    xAxisLabelSpaceAfter : 0,
    xAxisLabelSpaceBefore : 0,
    legendBordersSpaceBefore : 0,
    legendBordersSpaceAfter : 0,
    footNoteSpaceBefore : 0,
    footNoteSpaceAfter : 0, 
    startAngle : 0
    //dynamicDisplay : true
}

var dt = {
    labels: [<c:forEach begin="1" end="18" varStatus="i">"第${i.index}週",</c:forEach>],
    datasets: [
        {
        	
         //label: "My First dataset",
        fillColor: ChartColors[0].fillColor,
		strokeColor: ChartColors[0].strokeColor,
		pointColor: ChartColors[0].pointColor,
	    
            data: [<c:forEach items="${schcnt}" var="c"><c:forEach items="${c.cnt}" var="n">${n},</c:forEach></c:forEach>],
            title : "全校"
        }
    ]
};
op.graphTitle="全校18週缺曠趨勢";
op.graphSubTitle="每週缺曠加總";
var ctx = document.getElementById("linechart_sch").getContext("2d");
var myLineChart = new Chart(ctx).Line(dt, op);


op.graphTitle="各院18週缺曠趨勢";
op.graphSubTitle="平均每人每週缺曠節次";
dt = {
	labels: [<c:forEach begin="1" end="18" varStatus="i">"第${i.index}週",</c:forEach>],
	datasets: [
	<c:forEach items="${col}" var="c" varStatus="i">
	{
		label: "${c.name}",
		fillColor: ChartColors[${i.index}].fillColor,
		strokeColor: ChartColors[${i.index}].strokeColor,
		pointColor: ChartColors[${i.index}].pointColor,
	    data: [<c:forEach items="${c.cnt}" var="cc"><fmt:formatNumber type="number" maxFractionDigits="2" value="${cc/c.stds}" />,</c:forEach>],
	    title : "${c.name}"
	},
	</c:forEach>
	]
};
ctx = document.getElementById("linechart_col").getContext("2d");
myLineChart = new Chart(ctx).Line(dt, op);

//院
<c:forEach items="${col}" var="c" varStatus="a">
function getChart${c.id}${c.id}(){
	op.graphTitle="${c.name}18週缺曠趨勢";
	op.graphSubTitle="平均每人每週缺曠節次";
	dt = {
		labels: [<c:forEach begin="1" end="18" varStatus="i">"第${i.index}週",</c:forEach>],
		datasets: [		
		<c:forEach items="${dep}" var="d" varStatus="dd">
		<c:if test="${d.college eq c.id}">
		{
			label: "${d.name}",
			fillColor: ChartColors[${dd.index}].fillColor,
			strokeColor: ChartColors[${dd.index}].strokeColor,
			pointColor: ChartColors[${dd.index}].pointColor,
		    data: [<c:forEach items="${d.cnt}" var="cc"><fmt:formatNumber type="number" maxFractionDigits="2" value="${cc/d.stds}" />,</c:forEach>],
		    title : "${d.name}"
		},
		</c:if>
		</c:forEach>		
		]
	};
	ctx = document.getElementById("linechart_col${a.index}").getContext("2d");
	myLineChart = new Chart(ctx).Line(dt, op);
}
</c:forEach>





<c:forEach items="${dep}" var="d" varStatus="a">
<c:if test="${d.stds>0}">
function getChart${d.id}(){
	op.graphTitle="${d.name}18週缺曠趨勢";
	op.graphSubTitle="每週缺曠加總";
	dt = {
		labels: [<c:forEach begin="1" end="18" varStatus="i">"第${i.index}週",</c:forEach>],
		datasets: [	
		{
			label: "${d.name}",
			fillColor: ChartColors[${a.index}].fillColor,
			strokeColor: ChartColors[${a.index}].strokeColor,
			pointColor: ChartColors[${a.index}].pointColor,
			pointHighlightStroke: "#fff",
		    //pointStrokeColor: "#fff",
		    // pointHighlightFill: "#fff",
		    // pointHighlightStroke: "rgba(220,220,220,1)",
		    data: [<c:forEach items="${d.cnt}" var="dc">${dc},</c:forEach>],
		    title : "${d.name}"
		},
		
		
		
		
		]
	};
	try{
		ctx = document.getElementById("linechart_dep${d.id}").getContext("2d");
	}catch(e){
		ctx = document.getElementById("linechart_depx").getContext("2d");
	}

	myLineChart = new Chart(ctx).Line(dt, op);
}
</c:if>
</c:forEach>


















</script>
	
</body>
</html>