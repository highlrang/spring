<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<!-- <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css"> css link 걸기
-->
<!--  msg ALERT로 받기 -->

<html>
<head>
	<title>Home</title>
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
<table>
	<tr>
			<jsp:include page="base.jsp"></jsp:include>
	</tr>
	
	<tr>
		<td width="100%" style="position: relative;">
			<jsp:include page="slide.jsp"></jsp:include>	
		</td>
		
		<td height="1000px" style="float: right; margin-right: 5%;">
			<form action="login" method="post" style="height: 100%">
				<div style="border: 1px solid black; border-radius: 10px; padding: 5%; width: 300px; height: 100%;">
					<div style="text-align: center;">
						<input type="button" id="btnStu" value="학생" onclick="selectStu();"/> <input type="button" id="btnProf" style="background-color:black; color: white;" value="교수" onclick="selectProf();"/>
					</div><br>
					
					<div id="showStu" style="display: block;">
						<div><input type="text" name="stuNum" placeholder="학생 학번"/></div><br>
						<div><input type="password" name="stuPassword" placeholder="비밀번호"/></div><br>
						<div style="float: right; margin-right: 5%;"> <input type="submit" value="로그인" style="padding: 20%"/> </div><br><br>
						
					</div>
					
					<div id="showProf" style="display: none;">
						<div><input type="text" name="profNum" placeholder="교수 학번" style="background-color: lightgray;"/></div><br>
						<div><input type="password" name="profPassword" placeholder="비밀번호" style="background-color: lightgray;"/> </div><br>
						<div style="float: right; margin-right: 5%;"><input type="submit" value="로그인" style="padding: 20%"/> </div><br><br>
					</div>
					<br><br>
				</div>
			</form>
			<br><br>
		</td>
	</tr>
</table>


<br>

<script type="text/javascript">
	function selectStu(){
		var stu = document.getElementById("showStu");
		var prof = document.getElementById("showProf");
		if(stu.style.display == "none"){
			prof.style.display = "none";
			stu.style.display = "block";
		}
	}
	
	function selectProf(){
		var stu = document.getElementById("showStu");
		var prof = document.getElementById("showProf");
		if(prof.style.display == "none"){
			stu.style.display = "none";
			prof.style.display = "block";
		}
	}
	
</script>

</body>
</html>
