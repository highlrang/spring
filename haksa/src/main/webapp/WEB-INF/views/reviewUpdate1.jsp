<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<style>
	#rev_stars a{
		text-decoration: none;
		color: lightgray;
	}		
	
	#rev_stars a.on{
		color: black;
	}
</style>
<script>
	function starCheck(){
		var star = document.getElementsByName("rev_stars")[0].value;
		if(star != ""){
			return true;
		}else{
			alert("별점을 등록해주세요");
			return false;
		}
	}
	
	$(function(){
		$("input[type='checkbox']").click(function(){
			$("input[type='checkbox']").prop('checked', false);
			$(this).prop('checked', true);
		})
		
	});
</script>
</head>
<body>
	<jsp:include page="base.jsp"></jsp:include>
	
	<div style="margin-left: 3%">
	
		<div style="font-weight: bold;"> ${lec_name} - 강의 평가 수정</div>
		
		<br>
		
		<form action="updateReview2" method="post" onsubmit="return starCheck();">
			<div><input type="hidden" value="${lec_name}" name="lec_name"/><input type="hidden" value="${stu_num}" name="stu_num"/></div>
			
			<div>
				<div id="rev_stars">
					<a href="#">★</a>
					<a href="#">★</a>
					<a href="#">★</a>
					<a href="#">★</a>
					<a href="#">★</a>
					<input type="hidden" name="rev_stars"/>
				</div>
			</div><br>
			
			<div><textarea cols="60" rows="20" name="rev_content" wrap="hard" style="border-radius: 10px; padding:1%;" required>${rev_content}</textarea></div><br>
			
			<div>
				<input type="checkbox" name="rev_public" value="1"/>공개 
				<input type="checkbox" name="rev_public" value="0"/>비공개
			</div><br>
			
			<div><input type="submit" value="확인"/></div>
		</form>
		
	</div>
	<script>
		$("#rev_stars a").click(function(){
			$(this).parent().children("a").removeClass("on");
			$(this).addClass("on").prevAll("a").addClass("on");
			document.getElementsByName("rev_stars")[0].value = $('.on').length;
			console.log(document.getElementsByName("rev_stars")[0].value);
			return false;
		});
		
		$(document).ready(function(){
			var rev_pub = ${rev_public};
			if(rev_pub == "1"){
				$("input[type=checkbox][value='1']").prop("checked", true);
			}else{
				$("input[type=checkbox][value='0']").prop("checked", true);
			}	
			
			var star_index = ${rev_stars};
			$("#rev_stars a").eq(star_index).trigger("click");
			
		});
	</script>
</body>
</html>