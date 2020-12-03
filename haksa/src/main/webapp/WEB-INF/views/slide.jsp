<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
</head>


<body>
		<!-- 슬라이드 -->
	<div style="width: 400px; position: absolute; top: 1%; left: 30%;">
		<div id="carouselExampleSlidesOnly" class="carousel slide" data-ride="carousel">
		  <div class="carousel-inner">
		    <div class="carousel-item active">
		        <img src="${pageContext.request.contextPath}/img/spring.jpg" style="width: 100%; height: 150px;" alt="spring">
		    </div>
		    <div class="carousel-item">
		      <img src="${pageContext.request.contextPath}/img/oracle.png"  style="width: 100%; height: 150px;" alt="oracle">
		    </div>
		  </div>
		</div>
	</div>
	
	<!-- 메뉴한곳에 (대학소개, 공지사항, 수강신청 및 성적 확인) -->
	<div style="display: block; clear: both; text-align: center; border-top: 1px solid black; border-bottom: 1px solid black; margin-right: 3%;">
		<input type="button" value="대학소개" class="menu" onclick=""/>
		<input type="button" value="공지사항" class="menu" onclick=""/>
	</div>
	
	
	<script type="text/javascript">
	$(document).ready(function(){
		$('.carousel').carousel({
			  				interval: 2000
						})
	});
	
	</script>
</body>
</html>