<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<script src="/eis/inc/js/plugin/ChartJS/ChartNew.js"></script>
<script src="/eis/inc/js/plugin/ChartJS/ChartColor.js"></script>
</head>
<body>
<div class="alert"><b>併班次數統計</b> 課程合併的次數，以被合併的課程或班級為單位累計</div>

<canvas id="chart" width="1000" height="400"></canvas>


<script>
var op={
		
		inGraphDataShow : true,
		datasetFill : true,
		/*
		scaleLabel: "smart",
		scaleTickSizeRight : 5,
		scaleTickSizeLeft : 5,
		scaleTickSizeBottom : 5,
		scaleTickSizeTop : 5,
		scaleFontSize : 16,
		canvasBorders : true,
		canvasBordersWidth : 3,
		canvasBordersColor : "black",
			
		footNote : "Footnote for the graph",
		footNoteFontFamily : "'Microsoft JhengHei'",
		footNoteFontSize : 10,
		footNoteFontStyle : "bold",
		footNoteFontColor : "#666",
		legendBordersWidth : 0,
	    legendBordersColors : "#666",
	    yAxisLeft : true,
	    yAxisRight : false,
	    xAxisBottom : true,
	    xAxisTop : false,
	    yAxisLabel : "Y Axis Label",
	    yAxisFontFamily : "'Arial'",
	    yAxisFontSize : 16,
	    yAxisFontStyle : "normal",
	    yAxisFontColor : "#666",
	    xAxisLabel : "pX Axis Label",
	    xAxisFontFamily : "'Arial'",
	    xAxisFontSize : 16,
	    xAxisFontStyle : "normal",
	    xAxisFontColor : "#666",
	    yAxisUnit : "Y Unit",
	    yAxisUnitFontFamily : "'Arial'",
	    yAxisUnitFontSize : 8,
	    yAxisUnitFontStyle : "normal",
	    yAxisUnitFontColor : "#666",
	    showYAxisMin : false,
		*/	
		graphTitle : "${school_year}學年 第 ${school_term}學期",
		graphTitleFontFamily : "'Microsoft JhengHei'",
		graphTitleFontSize : 24,
		graphTitleFontStyle : "bold",
		graphTitleFontColor : "#666",
		graphSubTitle : "併班次數統計",
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
var data = {
		labels : [<c:forEach items="${merge}" var="m">"${m.name}",</c:forEach>],
		datasets : [
		    {
		        fillColor : [<c:forEach items="${merge}" var="m" varStatus="i">ChartColors[${i.index}].fillColor,</c:forEach>],
		        //strokeColor : [<c:forEach items="${merge}" var="m" varStatus="i">ChartColors[${i.index}].fillColor,</c:forEach>],
		        data : [<c:forEach items="${merge}" var="m">"${m.cnt}",</c:forEach>]
		        //title : "${school_year}學年 第 ${school_term}學期"
		    }
		]
		}
		
var ctx = document.getElementById("chart").getContext("2d");
new Chart(ctx).Bar(data, op);

</script>
</body>
</html>