package com.myproject.myweb;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.myproject.myweb.domain.LoginVO;
import com.myproject.myweb.persistence.LoginDAO;




@Controller
public class LoginController {
	@Inject
	private LoginDAO dao;
	
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ModelAndView goLogin(HttpServletRequest httpServletRequest) {
		ModelAndView mav = new ModelAndView(); 
		
		try { // 학생
			int stu_num = Integer.valueOf(httpServletRequest.getParameter("stuNum"));
			String stu_password = httpServletRequest.getParameter("stuPassword"); 			
			
			LoginVO vo = new LoginVO();
			vo.setStu_num(stu_num);
			vo.setStu_password(stu_password);
			
			try {
				LoginVO vo2 = dao.selectStudent(vo); // 학생 정보 ALL
				System.out.println(vo2.getStu_name());
			
				HttpSession session = httpServletRequest.getSession();
				session.setAttribute("ssUser", stu_num); //number > name으로 바꾸기
				session.setAttribute("whoIs", "stu");
				// session.getAttribute || session.invalidate()	
				
				mav.setViewName("home2");
				mav.addObject("stu_num", stu_num);
				//mav.addObject("stu_password", stu_password);
				mav.addObject("stu_name", vo2.getStu_name());
				mav.addObject("stu_phone", vo2.getStu_phone());
				mav.addObject("stu_email", vo2.getStu_email());
				mav.addObject("stu_major", vo2.getStu_major());
				mav.addObject("stu_minor", vo2.getStu_minor());
				
			}catch(NullPointerException e) {
				String msg = "아이디 또는 비밀번호 불일치입니다";
				mav.setViewName("home");
				mav.addObject("msg", msg);
				
			}
			return mav;
		
			
			
		}catch(NumberFormatException e) { // 교수
			
			try {
				int prof_num = Integer.valueOf(httpServletRequest.getParameter("profNum"));
				String prof_password = httpServletRequest.getParameter("profPassword"); 
				
				
				LoginVO vo = new LoginVO();
				vo.setProf_num(prof_num);
				vo.setProf_password(prof_password);
				
				try {
					LoginVO vo2 = dao.selectProfessor(vo);
					System.out.println(vo2.getProf_name());
				
					HttpSession session = httpServletRequest.getSession();
					session.setAttribute("ssUser", prof_num); //number > name으로 바꾸기
					session.setAttribute("whoIs", "prof");
					// session.getAttribute || session.invalidate()	
					
					mav.setViewName("home2");
					mav.addObject("prof_num", prof_num);
					//mav.addObject("stu_password", stu_password);
					mav.addObject("prof_name", vo2.getProf_name());
					mav.addObject("prof_phone", vo2.getProf_phone());
					mav.addObject("prof_email", vo2.getProf_email());
					mav.addObject("prof_maj", vo2.getProf_maj());
	
					
				}catch(NullPointerException e2) {
					String msg = "아이디 또는 비밀번호 불일치입니다";
					mav.setViewName("home");
					mav.addObject("msg", msg);
					
				}
				
			}catch(NumberFormatException e3) {
				String msg = "아이디, 비밀번호를 입력해주세요";
				mav.setViewName("home");
				mav.addObject("msg", msg);
			} 	
			
			return mav;
			
		}
		
	}
	
	
	@RequestMapping(value="/logout")
	public String home2(Locale locale, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "home";
	}
	
	
	@RequestMapping(value="/goHome")
	public ModelAndView home3(Locale locale, Model model, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(); 
		HttpSession session = request.getSession();
		try {
			if (session.getAttribute("whoIs") == "stu") {
				int stu_num = (Integer)session.getAttribute("ssUser");
				LoginVO vo = dao.selectStuByNum(stu_num);
				
				mav.setViewName("home2");
				mav.addObject("stu_num", stu_num);
				//
				mav.addObject("stu_name", vo.getStu_name());
				mav.addObject("stu_phone", vo.getStu_phone());
				mav.addObject("stu_email", vo.getStu_email());
				mav.addObject("stu_major", vo.getStu_major());
				mav.addObject("stu_minor", vo.getStu_minor());
				
				
				
			}else if(session.getAttribute("whoIs") == "prof"){
				int prof_num = (Integer)session.getAttribute("ssUser");
				LoginVO vo = dao.selectProfByNum(prof_num);
				
				mav.setViewName("home2");
				mav.addObject("prof_num", prof_num);
				//
				mav.addObject("prof_name", vo.getProf_name());
				mav.addObject("prof_phone", vo.getProf_phone());
				mav.addObject("prof_email", vo.getProf_email());
				mav.addObject("prof_maj", vo.getProf_maj());
				
				
				
			}else if(session.getAttribute("whoIs") == null) {
				mav.setViewName("home");
				
			}
		}catch(NullPointerException e) {
			mav.setViewName("home");
			mav.addObject("msg", "세션이 만료되었습니다. 다시 로그인 해주세요.");
		}
			
		return mav;
	}	
}
