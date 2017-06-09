<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>校務研究公開資料</title>
</head>
<body>

<div class="bs-callout bs-callout-danger">

  
 <b>校務研究公開資料</b>
 缺少或不足的部份請洽相關單位 <span class="glyphicon glyphicon-phone-alt" aria-hidden="true"></span>
</div>












<div class="panel panel-primary" style="width: 100%; margin:0px auto;">
		<div class="panel-heading">
		  <h3 class="panel-title">公開資料</h3>
		</div>
		<div class="panel-body">
<div align="center">
    <div class="row">
      <div class="col-sm-6 col-md-3">
        <div class="thumbnail">
          <img src="http://140.117.104.212/images/opendb/stu.jpg" alt="學生面" width="242" height="200">
          <div class="caption">
            <h3>學生面</h3>
			<div class="list-group" style="height:210px;overflow-x:hidden;overflow-y:auto;">
			<a href="opendb.php?type=stun_edu"class="list-group-item">各年學生數(校)</a>
			<a href="opendb.php?type=stun_dpt"class="list-group-item">各年學生數(系)</a>
			<a href="opendb.php?type=stun_mf_edu"class="list-group-item">男生女生比(校)</a>
			<a href="opendb.php?type=stun_mf_dpt"class="list-group-item">男生女生比(系)</a>
			<a href="opendb.php?type=st_r"class="list-group-item">生師比</a>
			<a href="opendb.php?type=foreign_edu"class="list-group-item">外籍生數(校)</a>
			<a href="opendb.php?type=foreign_dpt"class="list-group-item">外籍生數(系)</a>
			<a href="opendb.php?type=overseas_edu"class="list-group-item">僑生、港澳生數(校)</a>
			<a href="opendb.php?type=overseas_dpt"class="list-group-item">僑生、港澳生數(系)</a>
			<a href="opendb.php?type=in_sch"class="list-group-item">就讀本校大學部之高中生來源</a>
			<a href="opendb.php?type=stu_all"class="list-group-item">在籍學生人數</a>
			</div>
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-md-3">
        <div class="thumbnail">
          <img src="http://140.117.104.212/images/opendb/oa.jpg" alt="教務面" width="242" height="200">
          <div class="caption">
            <h3>教務面</h3>
			<div class="list-group" style="height:210px;overflow-x:hidden;overflow-y:auto;">
			<a href="opendb.php?type=course"class="list-group-item">開設課程總數</a>
			<a href="opendb.php?type=c_general"class="list-group-item">通識教育課程數</a>
			</div>
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-md-3">
        <div class="thumbnail">
          <img src="http://140.117.104.212/images/opendb/sa.jpg" alt="學務面" width="242" height="200">
          <div class="caption">
            <h3>學務面</h3>
			<div class="list-group" style="height:210px;overflow-x:hidden;overflow-y:auto;">
			<a href="opendb.php?type=ded_p"class="list-group-item">弱勢生數</a>
			<a href="opendb.php?type=disability"class="list-group-item">身障生數</a>
			<a href="opendb.php?type=athlete"class="list-group-item">體育生數</a>
			<a href="opendb.php?type=ti_r_sem"class="list-group-item">學雜費減免人數</a>
			<a href="opendb.php?type=ti_r_yr"class="list-group-item">學雜費減免人數(弱勢學生助學計畫)</a>
			<a href="opendb.php?type=ded_mean"class="list-group-item">平均每位弱勢生補助</a>
			<a href="opendb.php?type=loans"class="list-group-item">申請就學貸款數</a>
			</div>
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-md-3">
        <div class="thumbnail">
          <img src="http://140.117.104.212/images/opendb/teacher.jpg" alt="教師人事面" width="242" height="200">
          <div class="caption">
            <h3>教師人事面</h3>
			<div class="list-group" style="height:210px;overflow-x:hidden;overflow-y:auto;">
			<a href="opendb.php?type=yr_teacher_edu"class="list-group-item">各級教師數(校)</a>
			<a href="opendb.php?type=yr_teacher_dpt"class="list-group-item">各級教師數(系)</a>
			<a class="list-group-item disabled">各級教師比</a>
			<a href="opendb.php?type=yr_teacher_mf_edu"class="list-group-item">各級教師男女比例(校)</a>
			<a href="opendb.php?type=yr_teacher_mf_dpt"class="list-group-item">各級教師男女比例(系)</a>
			<a class="list-group-item disabled">專任職員數</a>
			<a class="list-group-item disabled">約聘職員數</a>
			</div>
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-md-3">
        <div class="thumbnail">
          <img src="http://140.117.104.212/images/opendb/study.jpg" alt="研究面" width="242" height="200">
          <div class="caption">
            <h3>研究面</h3>
			<div class="list-group" style="height:210px;overflow-x:hidden;overflow-y:auto;">
			<a href="opendb.php?type=cedu_income"class="list-group-item">建教合作收入</a>
			<a class="list-group-item disabled">建教合作件數</a>
			<a class="list-group-item disabled">平均每位教師收入</a>
			<a href="opendb.php?type=study_check"class="list-group-item">科技部核定件數</a>
			<a href="opendb.php?type=study_pass_rate"class="list-group-item">通過比率</a>
			<a href="opendb.php?type=study_check_fund"class="list-group-item">核定金額</a>
			<a href="opendb.php?type=study_mean_fund"class="list-group-item">平均每人金額</a>
			<a class="list-group-item disabled">論文數</a>
			<a class="list-group-item disabled">論文引用數</a>
			<a class="list-group-item disabled">H指數</a>
			<a class="list-group-item disabled">技轉件數</a>
			<a class="list-group-item disabled">專利件數</a>
			</div>
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-md-3">
        <div class="thumbnail">
          <img src="http://140.117.104.212/images/opendb/finance.jpg" alt="財務面" width="242" height="200">
          <div class="caption">
            <h3>財務面</h3>
			<div class="list-group" style="height:210px;overflow-x:hidden;overflow-y:auto;">
			<a href="opendb.php?type=ys_fund"class="list-group-item">校務基金使用狀況</a>
			<a href="opendb.php?type=accounts"class="list-group-item">各年會計決算數</a>
			<a href="opendb.php?type=accounts_rage"class="list-group-item">平均每位學生資源</a>
			<a class="list-group-item disabled">平均每位教師資源</a>
			</div>
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-md-3">
        <div class="thumbnail">
          <img src="http://140.117.104.212/images/opendb/construction.jpg" alt="基礎建設面" width="242" height="200">
          <div class="caption">
            <h3>基礎建設面</h3>
			<div class="list-group" style="height:210px;overflow-x:hidden;overflow-y:auto;">
			<a href="opendb.php?type=dorm"class="list-group-item">男女宿舍床位</a>
			<a href="opendb.php?type=domain_sd"class="list-group-item">校地面積</a>
			<a href="opendb.php?type=domain_floor"class="list-group-item">樓板面積</a>
			<a href="opendb.php?type=domain_mean"class="list-group-item">每位學生平均使用面積</a>
			<a href="opendb.php?type=water"class="list-group-item">用電量(節電)</a>
			<a href="opendb.php?type=electricity"class="list-group-item">用水量(節水)</a>
			</div>
          </div>
        </div>
      </div>
    </div>
		
</div>


</div>
</div>




<script>
//$( ".list-group-item" ).addClass( "disabled" );
var id;
$(".list-group-item").removeClass("disabled").delay(3000).queue(function(){
	
	setTimeout(function(){
		//alert($(this));
		$(this).addClass("disabled");
	}, 1000);
   
});



</script>


</body>
</html>