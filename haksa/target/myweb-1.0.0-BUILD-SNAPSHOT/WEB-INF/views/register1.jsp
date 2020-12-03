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

	<form action="register2" method="post" style="margin-left: 5%;">
	
		<div><span style="font-weight: bold;">학부 선택</span> 
			<select onchange="deptChange(this.value)" required="required" style="margin: 2%;">
				<c:forEach var="i" items="${val.keySet() }">
					<option value="${i }"/>${i }
				</c:forEach>
			</select>
		</div>
		
		<div><span style="font-weight: bold;">학과 선택</span> 	
			<select id="majName" name="majName" required="required" style="margin:2%;">
				<option>학과 선택</option>
			</select>&ensp;&ensp;&ensp;
			<span><input type="submit" value="확인"/></span>
		</div>
		
		
	</form>
	
	<script>
		function deptChange(e){
			var target = document.getElementById("majName");
			var maj;
			if(e == "경상대학"){
				maj = ${val.get('경상대학')};
			}else if(e == "교양"){
				maj = ${val.get('교양')};
			}else if(e == "예술대학"){
				maj = ${val.get('예술대학')};
			}else if(e == "자연과학대학"){
				maj = ${val.get('자연과학대학')};
			}else if(e == "인문대학"){
				maj = ${val.get('인문대학')};
			}else if(e == "사회과학대학"){
				maj = ${val.get('사회과학대학')};
			}else if(e == "공학대학"){
				maj = ${val.get('공학대학')};
			}else if(e == "관광문화대학"){
				maj = ${val.get('관광문화대학')};
			}
			
			target.options.length = 0;
			
			maj.forEach(function (item, index, array){
				var opt = document.createElement("option");
				opt.value = item;
				opt.innerHTML = item;
				target.appendChild(opt);
				
			});
				
			
				
		

		}
	</script>
</body>
</html>