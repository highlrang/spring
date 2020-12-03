<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
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
	
	<form action="score2" method="post" style="margin-left: 5%;">
		<h2>성적 입력</h2>
		<h4>성적 입력을 위해 한 번 더 로그인 해주세요.</h4>
		
		<br>
		<div> 
			교수학번 : <input type="text" style="padding: 1%; width: 30%;" placeholder="교수 학번" name="prof_num" required/>
		</div>
		<div>
			비밀번호 : <input type="password" style="padding: 1%; width: 30%;" placeholder="비밀번호" name="prof_password" required/>
			<input type="submit" value="확인" style="margin: 3%"/>
		</div>
		<br>

		
	</form>
</body>
</html>