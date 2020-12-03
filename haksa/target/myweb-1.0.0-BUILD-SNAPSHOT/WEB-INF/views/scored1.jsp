<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script>
	$(function(){
		var msg = "${msg}";
		if(msg != ""){
			alert(msg);
		}
	});
</script>
</head>
<body>
<jsp:include page="base.jsp"></jsp:include>
	<div style="margin-left: 5%">
	<h2>성적 조회</h2>
	<h4>성적 조회를 위하여 한 번 더 로그인 해주세요.</h4>
	<br>
	
	<form action="scored2" method="post">
		<input type="text" style="padding: 1%; width: 30%;" placeholder="학생 학번" name="stu_number" required/>
		<br>
		<input type="password" style="padding: 1%; width: 30%;" placeholder="비밀번호" name="stu_password" required/><input type="submit" value="확인" style="margin:2%"/>
		<br>

	</form>
	</div>
</body>
</html>