<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home2</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
</head>
<body>

<table>
<tr>
	<jsp:include page="base.jsp"></jsp:include>
</tr>


<br>
<tr>
<td width="100%" style="position: relative;">
		<jsp:include page="slide.jsp"></jsp:include>	
</td>
<td height="1000" style="float: right; margin-right: 5%;">
<form action="logout" method="post" style="height: 100%">
	<div style="border: 1px solid Khaki; border-radius: 10px; padding: 5%; width: 300px; height: 100%; text-align:center;">
	
	<c:choose>
		<c:when test="${not empty stu_num}">
			<div style="border-bottom: 1px solid black; padding: 1%;"> 학번 : ${stu_num}</div>
			<br>
			<div id="stu_info" style="display:block; background-color: beige; padding: 3%; text-align: left;"><!--stu_info();-->
				<div>이름 : ${stu_name}</div>
				<div>전공 : ${stu_major}</div>
				<div>복수전공 : ${stu_minor}</div>
				<div>전화번호 : ${stu_phone}</div>
				<div>이메일 : ${stu_email}</div>
			</div>
			<br>
			<div style="float: right;"> <input type="submit" value="로그아웃"/> </div>
			<br><br>
			<div>
				<input type="button" value="수강신청" onclick="location.href='register1'"/>&nbsp;&nbsp;
				<input type="button" value="수강조회" onclick="location.href='registered1'"/>
			</div><br>
			<div>	
				<input type="button" value="성적조회" onclick="location.href='scored1'"/>&nbsp;&nbsp;
				<input type="button" value="강의평가보기" onclick="location.href='readReview'"/>
			</div>

		</c:when>

		<c:otherwise>
			<div style="border-bottom: 1px solid black; padding: 1%; text-align: center;"> 교수 학번 : ${prof_num}</div>
			<br>
			<div> <input type="button" value="로그인 정보" onclick="prof_info();"/> <input type="submit" value="로그아웃"/> </div>
			<br>
			<div id="prof_info" style="display:none; background-color: beige; padding:3%;">
				<div>이름 : ${prof_name}</div>
				<div>전공 : ${prof_maj}</div>
				<div>전화번호 : ${prof_phone}</div>
				<div>이메일 : ${prof_email}</div>
			</div>
			
			<br><br>
			<div>
				<input type="button" value="성적등록" onclick="location.href='score1'"/>	
			</div>
		</c:otherwise>
	</c:choose>

	</div>

	

</form>
</td>
</tr>
</table>

<script type="text/javascript">
	function stu_info() {
		var stu_info = document.getElementById("stu_info");
		if (stu_info.style.display == "block"){
			stu_info.style.display = "none";
		}else{
			stu_info.style.display = "block";
		}
	}
	
	function prof_info() {
		var prof_info = document.getElementById("prof_info");
		if (prof_info.style.display == "block"){
			prof_info.style.display = "none";
		}else{
			prof_info.style.display = "block";
		}
	}
</script>

</body>
</html>