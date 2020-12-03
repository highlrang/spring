<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>     
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
	
	<div style="margin-left: 5%;">
	<div style="font-weight: bold;">${lec_sem} - ${maj_name} 강의</div>
	<br><br>
	
	<c:forEach var="i" items="${val.keySet()}">
	    <form action="register3" method="post">
		    <span style="margin: 3%;"> <span style="font-weight: bold;">${i}</span> &ensp;&ensp;
		    	<span><c:forEach var="j" items="${val.get(i)}">${j}&ensp;&ensp;&ensp;</c:forEach></span>
		    	<input type="hidden" value="${i}" name="course"/>
		    </span>
		    <input type="submit" value="수강신청"/>
			<input type="hidden" value="${stu_num}" name="stu_num"/>
			<input type="hidden" value="${maj_name}" name="maj_name"/>
			<input type="hidden" value="${lec_sem}" name="lec_sem"/>
	    </form>
	    <br>
	</c:forEach>
	</div>
</body>
</html>