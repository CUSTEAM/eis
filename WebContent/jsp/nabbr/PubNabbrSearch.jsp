<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>教室/實驗室管理</title>
<link href="/eis/inc/css/jquery-ui.css" rel="stylesheet"/>
<link href="/eis/inc/bootstrap/plugin/nav-wizard.bootstrap.css" rel="stylesheet"/>
<link href="/eis/inc/bootstrap/plugin/silviomoreto-bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet"/>
<link href="/eis/inc/js/plugin/jquery-ui-timepicker-addon/jquery-ui-timepicker-addon.min.css" rel="stylesheet"/>


<script src="/eis/inc/js/plugin/jquery-ui.js"></script>
<script src="/eis/inc/js/plugin/jquery-ui-timepicker-addon/jquery-ui-timepicker-addon.min.js"></script>
<script src="/eis/inc/bootstrap/plugin/silviomoreto-bootstrap-select/js/bootstrap-select.min.js"></script>
<script src="/eis/inc/bootstrap/plugin/silviomoreto-bootstrap-select/js/i18n/defaults-zh_TW.min.js"></script>

<script src="/eis/inc/js/plugin/d3/d3.min.js"></script>
<style>

/*
.chart rect {
  fill: steelblue;
}
*/
.chart .legend {
  fill: black;
  font: 14px sans-serif;
  text-anchor: start;
  font-size: 12px;
}

.chart text {
  fill: white;
  font: 10px sans-serif;
  text-anchor: end;
}

.chart .label {
  fill: black;
  font: 14px sans-serif;
  text-anchor: end;
}

.bar:hover {
  fill: brown;
}

.axis path,
.axis line {
  fill: none;
  stroke: #000;
  shape-rendering: crispEdges;
}


</style>
</head>
<body>
<form action="PubNabbrSearch" method="post" class="form-inline" onSubmit="$.blockUI({message:null});">
<div class="bs-callout bs-callout-info" id="callout-helper-pull-navbar">
	<h4>教室/實驗室統計列表</h4>
	<p>點選 <button class="btn btn-default btn-xs">查詢</button> 顯示報表</p>
	
		
</div>
<div class="panel panel-primary" id="unitList">
			<div class="panel-heading">建立新工作單</div>
			<div class="panel-body">
		    <p>點選校區開啟行政單位與 <button class="btn btn-warning btn-xs" type="button">申請項目</button> 列表</p>
		    <p>或點選多個單位 <button type="button" onClick="$('.batch').show('slow')" class="btn btn-primary btn-xs">批次建立</button> 工作</p>
		  	</div>
<table class="table">
	<tr>
		<td>
		<select name="building" class="selectpicker show-tick" data-width="auto">
			<option value="">選擇大樓</option>
			<c:forEach items="${CODE_BUILDING}" var="c">
			<option <c:if test="${c.id eq building}">selected</c:if> value="${c.id}">${c.name}</option>
			</c:forEach>
		</select>
		
		<select name="floor" class="selectpicker show-tick" data-width="auto">
			<option value="">選擇樓層</option>
			<c:forEach begin="1" end="12" varStatus="c">
			<option <c:if test="${c.index eq floor}">selected</c:if> value="${c.index}">${c.index}</option>
			</c:forEach>
		</select>
		
		<select name="type" class="selectpicker show-tick" data-width="auto">
			<option <c:if test="${boro eq ''}">selected</c:if> value="">所有教室與實驗室</option>
			<option <c:if test="${boro eq 'Y'}">selected</c:if> value="Y">標記為納入統計(實驗室)</option>
			<option <c:if test="${boro eq 'N'}">selected</c:if> value="N">標計為不納入統計(教室或其他空間)</option>	
				
		</select>
		
		</td>
	</tr>
	<tr>
		<td>
		
		<select name="dept" class="selectpicker show-tick" data-width="auto">
			<option value="">選擇系所</option>
			<c:forEach items="${CODE_DEPT}" var="c">
			<option <c:if test="${c.id eq dept}">selected</c:if> value="${c.id}">${c.name}</option>
			</c:forEach>
		</select>
		
		<select name="unit" class="selectpicker show-tick" data-width="auto">
			<option value="">選擇單位</option>
			<c:forEach items="${CODE_UNIT}" var="c">
			<option <c:if test="${c.id eq unit}">selected</c:if> value="${c.id}">${c.name}</option>
			</c:forEach>
		</select>
		
		
		
		
		</td>
	</tr>
	<tr>
		<td>
		
		
		
		<div class="form-group">
		    <label class="sr-only" for="exampleInputAmount">Amount (in dollars)</label>
		    <div class="input-group">
		      <div class="input-group-addon">研究成果統計自</div>
		      <input type="text" name="begin" value="${school_term_begin}" class="form-control timepick" autocomplete="off" 
				placeholder="開始時間" data-provide="typeahead"/>
		    </div>
		</div>
		
		
		<div class="form-group">
		    <label class="sr-only" for="exampleInputAmount">Amount (in dollars)</label>
		    <div class="input-group">
		      <div class="input-group-addon">至</div>
		      <input type="text" name="end" value="${school_term_end}" class="form-control timepick" autocomplete="off" 
				placeholder="結束時間" data-provide="typeahead"/>
		    </div>
		</div>
		
		</td>
	<tr>
		<td>
		<button class="btn btn-default" name="method:print">績效統計表</button>
		<button class="btn btn-default" name="method:printMap">績效統計圖</button>
		<button class="btn btn-default" name="method:printMapOpt">項目統計圖</button>
		<!-- div class="btn-group">
		  <button class="btn btn-default" name="method:search">查詢</button>
		  
		  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		    <span class="caret"></span>
		    <span class="sr-only">Toggle Dropdown</span>
		  </button>
		  <ul class="dropdown-menu">
		    <li><a href="#">教室課表</a></li>
		    <li><a href="#">使用記錄</a></li>
		    <li><a href="#">研究成果</a></li>
		    <li role="separator" class="divider"></li>
		    <li><a href="#">教室簡介</a></li>
		  </ul>
		</div-->
		<a class="btn btn-default" href="PubNabbrSearch">重新查詢</a>
		
		
		
		</td>
	</tr>
</table>
</div>





<c:if test="${!empty rooMap}">
<div class="panel panel-primary" id="unitList">
<div class="panel-heading">績效統計圖</div>
<div class="panel-body">
   <p>根據已申請的使用記錄 <button class="btn btn-warning btn-xs" type="button">申請項目</button> 列表</p>
</div>
<svg class="chart table"></svg>
</div>
<script>

var data = {
  labels: [
           <c:forEach items="${rooMap}" var="r">'${r.sname}',</c:forEach>
    
  ],
  series: [
    {
      label: '教室數',
      values: [<c:forEach items="${rooMap}" var="r">${r.rom},</c:forEach>]
    },
    {
      label: '研究成果數',
      values: [<c:forEach items="${rooMap}" var="r">${r.pro},</c:forEach>]
    },
    {
      label: '教室績效',
      values: [<c:forEach items="${rooMap}" var="r"><fmt:formatNumber type="number" pattern="#.##" value="${(r.pro/r.rom)*100}" />,</c:forEach>]
    },]
};

var chartWidth       = 800,
    barHeight        = 20,
    groupHeight      = barHeight * data.series.length,
    gapBetweenGroups = 10,
    spaceForLabels   = 150,
    spaceForLegend   = 150;

// Zip the series data together (first values, second values, etc.)
var zippedData = [];
for (var i=0; i<data.labels.length; i++) {
  for (var j=0; j<data.series.length; j++) {
    zippedData.push(data.series[j].values[i]);
  }
}

// Color scale
var color = d3.scale.category20();
var chartHeight = barHeight * zippedData.length + gapBetweenGroups * data.labels.length;

var x = d3.scale.linear()
    .domain([0, d3.max(zippedData)])
    .range([0, chartWidth]);

var y = d3.scale.linear()
    .range([chartHeight + gapBetweenGroups, 0]);

var yAxis = d3.svg.axis()
    .scale(y)
    .tickFormat('')
    .tickSize(0)
    .orient("left");

// Specify the chart area and dimensions
var chart = d3.select(".chart")
    .attr("width", spaceForLabels + chartWidth + spaceForLegend)
    .attr("height", chartHeight);

// Create bars
var bar = chart.selectAll("g")
    .data(zippedData)
    .enter().append("g")
    .attr("transform", function(d, i) {
      return "translate(" + spaceForLabels + "," + (i * barHeight + gapBetweenGroups * (0.5 + Math.floor(i/data.series.length))) + ")";
    });

// Create rectangles of the correct width
bar.append("rect")
    .attr("fill", function(d,i) { return color(i % data.series.length); })
    .attr("class", "bar")
    .attr("width", x)
    .attr("height", barHeight - 1);

// Add text label in bar
bar.append("text")
    .attr("x", function(d) { return x(d) - 3; })
    .attr("y", barHeight / 2)
    .attr("fill", "red")
    .attr("dy", ".35em")
    .text(function(d) { return d; });

// Draw labels
bar.append("text")
    .attr("class", "label")
    .attr("x", function(d) { return - 10; })
    .attr("y", groupHeight / 2)
    .attr("dy", ".35em")
    .text(function(d,i) {
      if (i % data.series.length === 0)
        return data.labels[Math.floor(i/data.series.length)];
      else
        return ""});

chart.append("g")
      .attr("class", "y axis")
      .attr("transform", "translate(" + spaceForLabels + ", " + -gapBetweenGroups/2 + ")")
      .call(yAxis);

// Draw legend
var legendRectSize = 18,
    legendSpacing  = 4;

var legend = chart.selectAll('.legend')
    .data(data.series)
    .enter()
    .append('g')
    .attr('transform', function (d, i) {
        var height = legendRectSize + legendSpacing;
        var offset = -gapBetweenGroups/2;
        var horz = spaceForLabels + chartWidth + 40 - legendRectSize;
        var vert = i * height - offset;
        return 'translate(' + horz + ',' + vert + ')';
    });

legend.append('rect')
    .attr('width', legendRectSize)
    .attr('height', legendRectSize)
    .style('fill', function (d, i) { return color(i); })
    .style('stroke', function (d, i) { return color(i); });

legend.append('text')
    .attr('class', 'legend')
    .attr('x', legendRectSize + legendSpacing)
    .attr('y', legendRectSize - legendSpacing)
    .text(function (d) { return d.label; });

</script>
</c:if>

<c:if test="${!empty optMap}">
<div class="panel panel-primary" id="unitList">
<div class="panel-heading">項目統計圖</div>
<div class="panel-body">
   <p>根據已申請的使用記錄對應研究成果項目 <button class="btn btn-warning btn-xs" type="button">申請項目</button> 列表</p>
</div>
<svg class="chart table"></svg>
</div>
<script>

var data = {
  labels: [
<c:forEach items="${optMap}" var="o">'${o.sname}',</c:forEach>
  ],
  
  
  series: [
    
    <c:forEach items="${code}" var="c" >
    {
      label: '${c.name}',
      values: [
      <c:forEach items="${optMap}" var="o">
      	<c:forEach items="${o.codes}" var="cc">
      	<c:if test="${c.id eq cc.rcode && o.id eq cc.dept}">
      	${cc.pro},
      	</c:if>
      	</c:forEach>
      </c:forEach>
               
               ]
    },
    
    
    </c:forEach>
    
    ]
    
    
    
};

var chartWidth       = 800,
    barHeight        = 20,
    groupHeight      = barHeight * data.series.length,
    gapBetweenGroups = 10,
    spaceForLabels   = 150,
    spaceForLegend   = 150;

// Zip the series data together (first values, second values, etc.)
var zippedData = [];
for (var i=0; i<data.labels.length; i++) {
  for (var j=0; j<data.series.length; j++) {
    zippedData.push(data.series[j].values[i]);
  }
}

// Color scale
var color = d3.scale.category20();
var chartHeight = barHeight * zippedData.length + gapBetweenGroups * data.labels.length;

var x = d3.scale.linear()
    .domain([0, d3.max(zippedData)])
    .range([0, chartWidth]);

var y = d3.scale.linear()
    .range([chartHeight + gapBetweenGroups, 0]);

var yAxis = d3.svg.axis()
    .scale(y)
    .tickFormat('')
    .tickSize(0)
    .orient("left");

// Specify the chart area and dimensions
var chart = d3.select(".chart")
    .attr("width", spaceForLabels + chartWidth + spaceForLegend)
    .attr("height", chartHeight);

// Create bars
var bar = chart.selectAll("g")
    .data(zippedData)
    .enter().append("g")
    .attr("transform", function(d, i) {
      return "translate(" + spaceForLabels + "," + (i * barHeight + gapBetweenGroups * (0.5 + Math.floor(i/data.series.length))) + ")";
    });

// Create rectangles of the correct width
bar.append("rect")
    .attr("fill", function(d,i) { return color(i % data.series.length); })
    .attr("class", "bar")
    .attr("width", x)
    .attr("height", barHeight - 1);

// Add text label in bar
bar.append("text")
    .attr("x", function(d) { return x(d) - 3; })
    .attr("y", barHeight / 2)
    .attr("fill", "red")
    .attr("dy", ".35em")
    .text(function(d) { return d; });

// Draw labels
bar.append("text")
    .attr("class", "label")
    .attr("x", function(d) { return - 10; })
    .attr("y", groupHeight / 2)
    .attr("dy", ".35em")
    .text(function(d,i) {
      if (i % data.series.length === 0)
        return data.labels[Math.floor(i/data.series.length)];
      else
        return ""});

chart.append("g")
      .attr("class", "y axis")
      .attr("transform", "translate(" + spaceForLabels + ", " + -gapBetweenGroups/2 + ")")
      .call(yAxis);

// Draw legend
var legendRectSize = 18,
    legendSpacing  = 4;

var legend = chart.selectAll('.legend')
    .data(data.series)
    .enter()
    .append('g')
    .attr('transform', function (d, i) {
        var height = legendRectSize + legendSpacing;
        var offset = -gapBetweenGroups/2;
        var horz = spaceForLabels + chartWidth + 40 - legendRectSize;
        var vert = i * height - offset;
        return 'translate(' + horz + ',' + vert + ')';
    });

legend.append('rect')
    .attr('width', legendRectSize)
    .attr('height', legendRectSize)
    .style('fill', function (d, i) { return color(i); })
    .style('stroke', function (d, i) { return color(i); });

legend.append('text')
    .attr('class', 'legend')
    .attr('x', legendRectSize + legendSpacing)
    .attr('y', legendRectSize - legendSpacing)
    .text(function (d) { return d.label; });

</script>


</c:if>






<script>
$(".timepick" ).datepicker();
</script>
</form>
</body>
</html>