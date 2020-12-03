<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
<style>
	#star a {
		text-decoration: none;
		color: black;
	}
</style>
</head>
<body>
	<jsp:include page="base.jsp"></jsp:include>
	
	<div style="margin-left: 3%; text-align: center;">
		<div style="border-top: 1px solid black; border-bottom: 1px solid black; padding: 1%;"><span style="font-weight: bold;">${lec_name }</span> 검색 결과 -- 전체 강의 평가</div>
		<br><br>
		<c:choose>
			<c:when test="${allReview.isEmpty() eq true}">
				<p> <span style="font-weight: bold">${lec_name}</span>의 강의 평가가 없습니다. </p>
			</c:when>
		</c:choose>
		
		<c:forEach var="i" items="${allReview.keySet()}">
		<div class="all" style="border-radius: 5px; padding: 1%; margin-right: 5%;">
			
			<div> 
				<a class="info"> ${i } </a>
			</div>
			
			<div style="display: none" class="other">
			<br>
				<c:forEach var="l" items="${allStar.get(i) }">
					<div id="star">
						<c:choose>
							<c:when test="${l eq '5'}">
								<a href="#">★</a>
								<a href="#">★</a>
								<a href="#">★</a>
								<a href="#">★</a>
								<a href="#">★</a>
							</c:when>
							<c:when test="${l eq '4'}">
								<a href="#">★</a>
								<a href="#">★</a>
								<a href="#">★</a>
								<a href="#">★</a>
								<a href="#" style="color: gray">★</a>
							</c:when>
							<c:when test="${l eq '3'}">
								<a href="#">★</a>
								<a href="#">★</a>
								<a href="#">★</a>
								<a href="#" style="color: gray">★</a>
								<a href="#" style="color: gray">★</a>
							</c:when>
							<c:when test="${l eq '2'}">
								<a href="#">★</a>
								<a href="#">★</a>
								<a href="#" style="color: gray">★</a>
								<a href="#" style="color: gray">★</a>
								<a href="#" style="color: gray">★</a>
							</c:when>
							<c:when test="${l eq '1'}">
								<a href="#">★</a>
								<a href="#" style="color: gray">★</a>
								<a href="#" style="color: gray">★</a>
								<a href="#" style="color: gray">★</a>
								<a href="#" style="color: gray">★</a>
							</c:when>
						</c:choose>
					</div>
					<br>
					<c:forEach var="j" items="${allReview.get(i)}">
						<div><textarea  cols="60" rows="20" wrap="hard" style="border-radius: 10px; padding: 1%;" disabled> ${j}</textarea> </div>
					</c:forEach>
					<br>	
				</c:forEach>
			</div>
		</div>		
		</c:forEach>	
	</div>
	
	<script>
		$(function(){
			$(".info").click(function(){
				var info = $(this).parent("div");
				
				if(info.next().css("display") == "none"){
					$(".all").css({"background-color": "white"});
					info.parent().css({"background-color": "beige"});
					
					$(".other").css({"display": "none"});
					info.next().css({"display": "block"});
				}else{
					info.parent().css({"background-color": "white"});
					info.next().css({"display": "none"});
					
				}
			});
			
		});
	</script>
</body>
</html>