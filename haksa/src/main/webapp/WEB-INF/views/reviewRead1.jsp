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
<script>
	$(function(){
		var msg = "${msg}";
		if(msg != ""){
			alert(msg);
		}
	})
</script>
</head>
<body>
	<jsp:include page="base.jsp"></jsp:include>
	
	<div style="margin-left: 3%; text-align: center;">
		<form action="readReview2" method="post">
			<div> 검색 : <input type="text" name="lec_name" style="width: 30%;" placeholder="강의 이름" required/> <input type="submit" value="확인"/> </div>
		
		</form>
		<br><br>
		
		<div style="border-top: 1px solid black; border-bottom: 1px solid black; padding:1%;">나의 강의평가</div>
		<c:forEach var="i" items="${myReview.keySet()}"><br>
		<div style="padding: 1%; border-radius: 5px; margin-right: 5%;" class="all">
			 
			<div> 
				<a class="info"> ${i} </a><br><br>
				<form action="" method="post">	 
					<input type="hidden" value="${i}" name="my_lec"/>
					<input type="submit" value="수정" formaction="updateReview"/>
					<input type="submit" value="삭제" formaction="deleteReview"/> 
				</form>
			</div>

			<div style="display: none;" class="other">
				<br>
				<div style="background-color:white">
					<span style="color: gray;">리뷰 키워드&ensp;&ensp;&ensp;</span>

					<c:forEach var="n" items="${myKeyword.get(i) }">
						<c:choose>
							<c:when test="${myCount.get(n) <= 3 }">
								<span style="font-size: 16px;">${n }</span>
							</c:when>
							<c:when test="${myCount.get(n) > 3}">
								<span style="font-size: 20px;">${n }</span>
							</c:when>
						</c:choose>	
					</c:forEach>
				</div>
				<br>
				<c:forEach var="l" items="${myStar.get(i) }">
					<div id="star"><c:choose>
						<c:when test="${l eq '5'}">
							<a>★</a>
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
					</c:choose></div>
					<br>
					<c:forEach var="j" items="${myReview.get(i)}">
						<div>
							<textarea cols="60" rows="20" name="rev_content" wrap="hard" style="border-radius: 10px; padding:1%;" disabled>${j}</textarea>
						</div>
						<br>
						<c:forEach var="m" items="${myPublic.get(i) }">
							<div><c:choose>
								<c:when test="${m eq '1'}">
									공개
								</c:when>
								<c:otherwise>
									비공개
								</c:otherwise>
							</c:choose></div>
						</c:forEach>
						
					</c:forEach>
						
				</c:forEach>
				
				
			</div>
		</div>	
		</c:forEach>
	</div>
	<br><br><br>
	
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
					$(this).parent("div").parent().css({"background-color": "white"});
					$(this).parent("div").next().css({"display": "none"});
				}
			});
		});
		

	
	</script>
</body>
</html>