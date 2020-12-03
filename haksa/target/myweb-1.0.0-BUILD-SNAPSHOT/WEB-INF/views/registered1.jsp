<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script>
    $(function(){
        var msg = "<c:out value="${msg}" />"
        if(msg != ""){
            alert(msg);
        }
    }) 
</script>
</head>
<body>
<jsp:include page="base.jsp"></jsp:include>
	
	<div style="margin-left: 3%">
	
		<h3>수강신청 내역</h3><br>
		
		
			<c:forEach var="i" items="${majList.keySet()}"><!-- 강의 -->
				<span style="display: inline-block; width: 800px;">
					<span style="display: inline-block; width: 150px; font-weight: bold;">${i}</span>
					
					<c:forEach var="j" items="${majList.get(i)}"><!-- 전공 -->
						<span style="display: inline-block; width: 150px;">${j}</span>
					</c:forEach>
					
					<c:forEach var="v" items="${semList.get(i)}"><!-- 학기 -->
						<span style="display: inline-block; width: 150px;">${v}</span>
					</c:forEach>
				
					<c:forEach var="l" items="${starList.get(i)}">
						<c:choose>
							<c:when test="${l eq 'null'}">
								<span style="display: inline-block; width: 150px;">
									<form action="writeReview" method="post">
										<input type="submit" value="강의평가하기"/>
										<input type="hidden" value="${i}" name="lec_name"/>
									</form>
								</span>
							</c:when>
							<c:otherwise>
								<span style="display: inline-block; width: 150px;" >
									강의 평가 완료
								</span>	
							</c:otherwise>
						</c:choose>
						<br><br>
					</c:forEach>
				</span>	
			</c:forEach>
	</div>
	
	
</body>
</html>