# 학사관리 웹 프로그래밍 코드 설명
+ 웹페이지의 기능은 사용자 인증, 수강 등록, 강의 평가, 성적으로 이루어져 있습니다.
+ 그 중 **최종 수강 신청 페이지**를 중심으로 코드를 소개하겠습니다. 
+ 주석 중심으로 살펴봐주세요.

## Controller

``` java
@RequestMapping(value="/register3", method=RequestMethod.POST)
public ModelAndView goRegister2(HttpServletRequest httpServletRequest) {
	ModelAndView mav = new ModelAndView();
	String lec_name = httpServletRequest.getParameter("course");
	String maj_name = httpServletRequest.getParameter("maj_name");
	String lec_sem = httpServletRequest.getParameter("lec_sem");
	Integer stu_num = Integer.valueOf(httpServletRequest.getParameter("stu_num"));
	Integer reg_count = 0;
	String msg = "";

	# 신청한 강의인지 DB에서 확인
	List<RegisteredVO> alreadyRegistered = dao.selectRegistered(stu_num);

	# 이미 신청한 강의인 경우
	if(alreadyRegistered.contain(lec_name)){
		msg = "이미 신청한 강의입니다.";

	# 미신청 강의인 경우 신청하기 위해 아래의 코드 진행
	}else{
		# 1. 신청한 모든 수강생의 "수"를 뽑는 majorDAO의 메서드
		RegisterVO voForNum = new RegisterVO();
		voForNum.setLec_sem(lec_sem);
		voForNum.setLec_name(lec_name);
		RegisterVO numCheckBeforeRegister = dao.selectMaxCount(voForNum); 

		try {
			# 1-1. 최대 신청 인원이 초과된 경우
			if(numCheckBeforeRegister.getReg_count() >= numCheckBeforeRegister.getLec_limit()) {
				RegisterVO voForWait = new RegisterVO();
				voForWait.setLec_name(lec_name);
				voForWait.setLec_sem(lec_sem);
				reg_count = dao.selectMinCount(voForWait) - 1;

				msg = "수강인원 초과로 " + lec_name + " 강의가 대기처리 되었습니다.";

			# 1-2. 수강 자리가 남아 신청 가능한 경우  
			}else{
				reg_count = numCheckBeforeRegister.getReg_count() + 1;
				msg = lec_name + " 수강신청 되었습니다.";
			}

		# 1-3. 신청인원이 없을 경우(null 반환)
		}catch(NullPointerException e) {
			reg_count = 1;
			msg = lec_name + " 수강신청 되었습니다.";
		}	

		RegisterVO voForRegister = new RegisterVO(); 
		voForRegister.setStu_num(stu_num);
		voForRegister.setLec_name(lec_name);
		voForRegister.setLec_sem(lec_sem);
		voForRegister.setReg_count(reg_count);         # 강의 신청한 학생 수 - 각 경우에서 정의됨
		dao.insertRegister(voForRegister);             # 최종 수강신청 테이블에 insert
	}


	# 다시 신청 페이지로 돌아갈 때 msg(변수) 전달을 위해 redirect가 아닌 ModelAndView 사용
	# 신청 페이지에는 학생이 선택한 전공의 강의들이 목록으로 나와있기에 강의 리스트 등을 변수로 
	MultiValuedMap<String, String> lecturePushList = new ArrayListValuedHashMap<String, String>();
	List<CourseVO> courseList = dao.selectCourse(maj_name);
	for(CourseVO voForCourse : courseList) {
		lecturePushList.put(voForCourse.getLec_name(), vo2.getLec_prof() + " 교수");
		lecturePuchList.put(voForCourse.getLec_name(), vo2.getLec_time());
	}
	mav.setViewName("register2");
	mav.addObject("msg", msg); 					# 각 경우에서 정의된 msg 전달
	mav.addObject("stu_num", stu_num);
	mav.addObject("maj_name", maj_name);
	mav.addObject("val", lecturePushList);
	mav.addObject("lec_sem", lec_sem);

	return mav;
}
```
------------------------------------------------------

## VO

```java
public class RegisteredVO {
	private String lec_name;      # 강의 이름을 담을 변수
	private String lec_sem;       # 강의 개설 학기를 담을 변수
	private String lec_maj;       # 강의 소속 학과를 담을 변수
	
	public RegisteredVO() {};
	public RegisteredVO(String cour_name) {   # 생성자
		this.lec_name = cour_name;
	}
  
	public String getLec_name() {             # getter & setter
		return lec_name;
	}
	public void setLec_name(String cour_name) {
		this.lec_name = cour_name;
	}
	...
	}
}
```

-------------------------------------------------------------------

## DAO

```java
@Repository
public class MajorDAO{
	@Inject
	private SqlSession sqlSession;    # mybatis 연결
  ...
	private static final String InsertRegister = "MajorMapper.insertRegister";      # mapper 지정
	private static final String SelectRegister = "MajorMapper.selectRegistered";
	
  ...
	public void insertRegister(RegisterVO vo) {
		sqlSession.insert(InsertRegister, vo);          			# insert 메서드(DB 기능) 실행
	}
  
	public List<RegisteredVO> selectRegistered(int stu_number) {
		return sqlSession.selectList(SelectRegister, stu_number);        	# select 메서드(DB 기능) 실행
	}
}
```

--------------------------------------------------------

## Mapper

+ 전달받은 변수를 활용하여 등록 테이블 insert 및 select SQL문

```xml
<insert id="insertRegister">
	  insert into register_l(reg_id, reg_stu, reg_lec, reg_sem, reg_count) 
	  values( 
	  (select MAX(reg_id) + 1 from register_l),
	  #{stu_num}, 
	  (select lec_id from lecture where lec_name=#{lec_name} and lec_sem=#{lec_sem}), 
	  #{lec_sem},
	  #{reg_count}
	  )
  </insert>
  
  <select id="selectRegistered" resultType="registeredvo">        # resultType 해당 VO로 명시
	select lec_name, lec_sem, lec_maj
	from lecture, register_l
	where reg_stu = #{stu_number} 
	and lec_id = reg_lec
	and lec_sem=(select lec_sem
				from (select lec_sem 
			             from lecture
			             order by lec_id desc)
			    where rownum = 1)
  </select>
```

-----------------------------------------------------------------------

# jsp

1. 최종 강의 신청 페이지
```jsp
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

    # response 결과를 변수(msg)로 전달하여 alert 방식으로 화면에 띄우기
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
  
	# 전달받은 강의 리스트를 for문으로 나열 
	<c:forEach var="i" items="${val.keySet()}"> 
  
  	    # form post submit 방식으로 각 강의마다 강의 신청 버튼
	    <form action="register3" method="post"> 
		    <span style="margin: 3%;"> <span style="font-weight: bold;">${i}</span> &ensp;&ensp;
		    	<span><c:forEach var="j" items="${val.get(i)}">${j}&ensp;&ensp;&ensp;</c:forEach></span>
		    	<input type="hidden" value="${i}" name="course"/>
		    </span>
		    <input type="submit" value="수강신청"/>
        
   			# DB와의 상호작용에 필요한 파라미터들 hidden으로 담아서 전달     
			<input type="hidden" value="${stu_num}" name="stu_num"/>    
			<input type="hidden" value="${maj_name}" name="maj_name"/>
			<input type="hidden" value="${lec_sem}" name="lec_sem"/>
	    </form>
	    <br>
	</c:forEach>
	</div>
</body>
</html>
```

2. 강의 신청 후 신청 목록 보는 페이지

```jsp
<body>
<jsp:include page="base.jsp"></jsp:include>
	
	<div style="margin-left: 3%">
		<h3>수강신청 내역</h3><br>
    		
		# 강의가 list의 key에 위치 - key for문
		<c:forEach var="i" items="${majList.keySet()}"> 
			<span style="display: inline-block; width: 800px;">
				<span style="display: inline-block; width: 150px; font-weight: bold;">${i}</span>
					
				# 전공 리스트에서 각 강의의 소속 전공 출력
				<c:forEach var="j" items="${majList.get(i)}">
					<span style="display: inline-block; width: 150px;">${j}</span>
				</c:forEach>
					
				# 학기 리스트에서 각 강의의 소속 학기 출력
				<c:forEach var="v" items="${semList.get(i)}">
					<span style="display: inline-block; width: 150px;">${v}</span>
				</c:forEach>
				
				# 강의 평가리스트에서 각 강의의 평가 데이터 존재 여부 확인
				<c:forEach var="l" items="${starList.get(i)}">
					<c:choose>
						# 강의 없을 경우 강의 평가 페이지로 이동 버튼
						<c:when test="${l eq 'null'}"> 
							<span style="display: inline-block; width: 150px;">
								<form action="writeReview" method="post"> 
								# form post 방식으로 페이지 이동
									<input type="submit" value="강의평가하기"/>
									<input type="hidden" value="${i}" name="lec_name"/>
								</form>
							</span>
						</c:when>
						# 강의 평가 데이터 이미 있을 경우
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
```
