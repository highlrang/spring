<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
</head>
<body>
<jsp:include page="base.jsp"></jsp:include>
	
	<div style="margin-left: 5%;">
	
	<c:forEach var="k" items="${name_list.keySet()}">
		<h4> ${k} - 성적 입력</h4><br>
		<c:forEach var="i" items="${name_list.get(k)}">
			<form action="score3" method="post">
				<input type="hidden" value="${k}" name="course"/>
				<input type="hidden" value="${i}" name="student"/>
				<span> ${i}</span>&ensp;&ensp;&ensp;
				<span> ${num_list.get(i) }</span>&ensp;&ensp;&ensp;
				<span> ${maj_list.get(i) }</span>&ensp;&ensp;&ensp;
				
				<c:choose>
					<c:when test="${empty c_list }">
						<span style="margin: 5%;"><input type="submit" value="성적입력"/></span><br>
					</c:when>
					
					<c:when test="${not empty c_list.get(k) }">
						<c:if test="${c_list.get(k).contains(num_list.get(i)) }">
							<span style="margin: 5%; font-weight: bold;">성적 입력 완료</span><br>				
						</c:if>
						<c:if test="${nc_list.get(k).contains(num_list.get(i)) }">
							<span style="margin: 5%;"><input type="submit" value="성적입력"/></span><br>
						</c:if>
					</c:when>
					
				</c:choose>
			</form><br>
		</c:forEach><br><br>		
	</c:forEach>
	</div>
</body>
</html>