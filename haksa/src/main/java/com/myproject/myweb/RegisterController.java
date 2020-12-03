package com.myproject.myweb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.myproject.myweb.domain.CourseVO;
import com.myproject.myweb.domain.MajorVO;
import com.myproject.myweb.domain.RegisterVO;
import com.myproject.myweb.domain.RegisteredVO;
import com.myproject.myweb.domain.ReviewVO;
import com.myproject.myweb.persistence.MajorDAO;
import com.myproject.myweb.persistence.ReviewDAO;

@Controller
public class RegisterController {
	@Inject
	private MajorDAO dao;
	@Inject
	private ReviewDAO dao2;
	
	@RequestMapping("register1") // stu_num VARCHAR2 에서 INT로 변경했는데 그것 때문인지 TEST하기
	public String view(Model model) {
		//배열 선언
		MultiValuedMap<String, String> val = new ArrayListValuedHashMap<String, String>();
		
		// 모든 전공 가져오기
		List<MajorVO> majorList = dao.selectMajor();

		// iterator | for( MembersDTO dto : list )
		// for(int i = 0; i < majorList.size()-1 ; i++ )
		// val.add(majorList.get(i));
		
		for(MajorVO before : majorList)
			val.put(before.getUpper_dept(), "'" + before.getDept_name() + "'");
		
		System.out.println(val);
		
		model.addAttribute("val", val); // 값 설정과 전달
		
		return "/register1";

	}
	
	
	@RequestMapping(value="/register2", method=RequestMethod.POST)
	public ModelAndView goRegister(HttpServletRequest httpServletRequest) {
		HttpSession session = httpServletRequest.getSession();
		ModelAndView mav = new ModelAndView();
		try {
			int studentID = (Integer)session.getAttribute("ssUser");
			String maj_name = httpServletRequest.getParameter("majName");
			String lec_sem = null;
			MultiValuedMap<String, String> val = new ArrayListValuedHashMap<String, String>();
			
			System.out.println(maj_name);
			System.out.println(dao.selectCourse(maj_name));
			List<CourseVO> courseList = dao.selectCourse(maj_name);
			
			// val 리스트변수에  lecture table 순회 값 넣기
			for(CourseVO vo : courseList) {
				val.put(vo.getLec_name(), vo.getLec_prof() + " 교수");
				val.put(vo.getLec_name(), vo.getLec_time());
			}	
			
			for(CourseVO vo2 : courseList)
				lec_sem = vo2.getLec_sem();
			
			System.out.println(courseList);
			System.out.println(val);
			
			mav.setViewName("register2");
			mav.addObject("val", val); //강의 리스트 전달
			mav.addObject("stu_num", studentID); //학번 전달
			mav.addObject("maj_name", maj_name); // 전공 전달
			mav.addObject("lec_sem", lec_sem);
		
		}catch(NullPointerException e) {
			mav.setViewName("home");
			mav.addObject("msg", "세션이 만료되었습니다. 다시 로그인 해주세요.");
		}	
		return mav;
		
			
	}
	
	@RequestMapping(value="/register3", method=RequestMethod.POST)
	public ModelAndView goRegister2(HttpServletRequest httpServletRequest) {
		ModelAndView mav = new ModelAndView();
	
		String lec_name = httpServletRequest.getParameter("course");
		Integer stu_num = Integer.valueOf(httpServletRequest.getParameter("stu_num"));
		String maj_name = httpServletRequest.getParameter("maj_name");
		String lec_sem = httpServletRequest.getParameter("lec_sem");
		Integer reg_count = 0;
		String msg = "";
		
		String answer = "";
		List<RegisteredVO> checklec = dao.selectRegistered(stu_num);
		for(RegisteredVO a : checklec) {
			if(a.getLec_name().equals(lec_name)){
				answer = "true";
			}
		}
		
		
		if(answer == "true"){
			msg = "이미 신청한 강의입니다.";
			
		}else {
			RegisterVO vo3 = new RegisterVO();
			vo3.setLec_sem(lec_sem);
			vo3.setLec_name(lec_name);
			RegisterVO beforeCheck = dao.selectMaxCount(vo3);
			
			try {
			
				if(beforeCheck.getReg_count() >= beforeCheck.getLec_limit()) {
					RegisterVO vo4 = new RegisterVO();
					vo4.setLec_name(lec_name);
					vo4.setLec_sem(lec_sem);
					reg_count = dao.selectMinCount(vo4) - 1;
					
					msg = "수강인원 초과로 " + lec_name + " 강의가 대기처리 되었습니다.";
					
				}else {
					reg_count = beforeCheck.getReg_count() + 1;
					msg = lec_name + " 수강신청 되었습니다.";
				}
			
			}catch(NullPointerException e) {
				reg_count = 1;
				msg = lec_name + " 수강신청 되었습니다.";
			}	
			
			
			RegisterVO vo = new RegisterVO();
			vo.setStu_num(stu_num);
			vo.setLec_name(lec_name);
			vo.setLec_sem(lec_sem);
			vo.setReg_count(reg_count);
			dao.insertRegister(vo);
		}
			
		
		// 공통코드
		MultiValuedMap<String, String> val = new ArrayListValuedHashMap<String, String>();
		List<CourseVO> courseList = dao.selectCourse(maj_name);
		for(CourseVO vo2 : courseList) {
			val.put(vo2.getLec_name(), vo2.getLec_prof() + " 교수");
			val.put(vo2.getLec_name(), vo2.getLec_time());
		}
		mav.setViewName("register2");
		mav.addObject("msg", msg); //
		mav.addObject("stu_num", stu_num);
		mav.addObject("maj_name", maj_name);
		mav.addObject("val", val);
		mav.addObject("lec_sem", lec_sem);
		
		return mav;
		
	}
	
	
	@RequestMapping(value="registered1", method= {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView goRegistered(HttpServletRequest request) {
		HttpSession session = request.getSession();
		ModelAndView mav = new ModelAndView();
		try {
			int stu_num = (Integer)session.getAttribute("ssUser");
			
			
			MultiValuedMap<String, String> maj_list = new ArrayListValuedHashMap<String, String>();
			MultiValuedMap<String, String> sem_list = new ArrayListValuedHashMap<String, String>();
			MultiValuedMap<String, String> star_list = new ArrayListValuedHashMap<String, String>();
			
			
			System.out.println(dao.selectRegistered(stu_num)); //
			List<RegisteredVO> registeredList = dao.selectRegistered(stu_num);
			
			for(RegisteredVO vo: registeredList) {
				sem_list.put(vo.getLec_name(), vo.getLec_sem());
				maj_list.put(vo.getLec_name(), vo.getLec_maj());
			}
			
			for(RegisteredVO vo: registeredList) {
				ReviewVO vo2 = new ReviewVO();
				vo2.setLec_name(vo.getLec_name());
				vo2.setStu_num(stu_num);
	
				try {
					Integer star = dao2.selectMyStars(vo2);
					System.out.println(star);
					star_list.put(vo.getLec_name(), String.valueOf(star));
				}catch(NullPointerException e) {
					star_list.put(vo.getLec_name(), "0");
				}
			}
			
			System.out.println(star_list);
			
			mav.setViewName("/registered1");
			mav.addObject("majList", maj_list);
			mav.addObject("semList", sem_list);
			mav.addObject("starList", star_list);
		
		}catch(NullPointerException e) {
			mav.setViewName("home");
			mav.addObject("msg", "세션이 만료되었습니다. 다시 로그인 해주세요.");
		}	
		return mav;
	}
}
