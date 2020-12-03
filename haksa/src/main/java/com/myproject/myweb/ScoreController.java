package com.myproject.myweb;

import java.util.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.myproject.myweb.domain.Score2VO;
import com.myproject.myweb.domain.ScoreVO;
import com.myproject.myweb.domain.ScoredVO;
import com.myproject.myweb.persistence.ScoreDAO;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

@Controller
public class ScoreController {
	@Inject
	private ScoreDAO dao;
	
	
	@RequestMapping("score1")
	public String view() {
		return "score1";
	}
	
	@RequestMapping(value="score2", method=RequestMethod.POST)
	public ModelAndView goScore(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		//Map<String, String> score_list = new HashMap<String, String>();
		MultiValuedMap<String, String> name_list = new ArrayListValuedHashMap<String, String>();
		Map<String, Integer> num_list = new HashMap<String, Integer>();
		Map<String, String> maj_list = new HashMap<String, String>();
		MultiValuedMap<String, Integer> b_list = new ArrayListValuedHashMap<String, Integer>();
		MultiValuedMap<String, Integer> c_list = new ArrayListValuedHashMap<String, Integer>();
		MultiValuedMap<String, Integer> nc_list = new ArrayListValuedHashMap<String, Integer>();
		
		try {
			int prof_num = Integer.valueOf(request.getParameter("prof_num")); // 나중엔 교수학번도 같이 받기
			String prof_password = request.getParameter("prof_password");

			
			ScoreVO vv = new ScoreVO();
			vv.setProf_num(prof_num);
			vv.setProf_password(prof_password);
			List<ScoreVO> getList = dao.selectScore(vv); // 전체
			List<ScoredVO> cList = dao.selectComplete(prof_num); // 성적 입력된 학생
			
			if(getList.isEmpty()) {
				String msg = "아이디 또는 비밀번호 불일치입니다";
				mav.setViewName("score1");
				mav.addObject("msg", msg);
				
			}else {
				for(ScoreVO vo : getList) {
					name_list.put(vo.getLec_name(), vo.getStu_name());
					num_list.put(vo.getStu_name(), vo.getStu_num());
					maj_list.put(vo.getStu_name(), vo.getStu_major());
					b_list.put(vo.getLec_name(), vo.getStu_num());
				}
				
				if(cList.isEmpty() == false) {
					for(ScoredVO vo: cList) {
						Object[] obj = b_list.get(vo.getLec_name()).toArray();
						
						for(int i=0; i<obj.length; i++) {
							if(vo.getReg_stu() == Integer.parseInt(obj[i].toString())){
								c_list.put(vo.getLec_name(), vo.getReg_stu()); // 이미 성적 입력된 학번 리스트
								
							}
						}
					}
				}
				
				// b_list.get(vo.getLec_name())을 Object[]로 만들고 거기서 c_list.get(vo.getLec_name()) Object[]를 뺀 Object[]를
				// nc_list에 for문으로 put하기 
				for(ScoredVO vo: cList) {
					List<Integer> a = new ArrayList<Integer>();
					
					for(Object o: b_list.get(vo.getLec_name()).toArray()) {
						a.add(Integer.valueOf(o.toString()));
					}
					
					Object[] obj = c_list.get(vo.getLec_name()).toArray();
					for(int i=0; i<obj.length; i++) {
						if(a.contains(obj[i])) {
							a.remove(obj[i]);
						}
					}
					
					for(int i=0; i<a.size(); i++) {
						nc_list.put(vo.getLec_name(), a.get(i));
					}
					
				}
				
				
				mav.setViewName("score2");
				mav.addObject("name_list", name_list);
				mav.addObject("num_list", num_list);
				mav.addObject("maj_list", maj_list);
				mav.addObject("c_list", c_list);
				mav.addObject("nc_list", nc_list);
			}
			
		}catch(NumberFormatException e2){
			String msg = "아이디, 비밀번호를 입력해주세요";
			mav.setViewName("score1");
			mav.addObject("msg", msg);
		}
		
		return mav;
	}
	
	@RequestMapping(value="score3", method=RequestMethod.POST)
	public ModelAndView goScore2(HttpServletRequest request) {
		String stu_name = request.getParameter("student");
		String cour_name = request.getParameter("course");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("score3");
		mav.addObject("stu_name", stu_name);
		mav.addObject("cour_name", cour_name);
		mav.addObject("msg", "");
		return mav;
	} 
	
	@RequestMapping(value="score4", method=RequestMethod.POST)
	public String goScore3(HttpServletRequest request) {
		String stu_name = request.getParameter("stu_name");
		String cour_name = request.getParameter("cour_name");
		int mid = Integer.valueOf(request.getParameter("mid"));
		int final_ = Integer.valueOf(request.getParameter("final"));
		int assign = Integer.valueOf(request.getParameter("assign"));
		int attend = Integer.valueOf(request.getParameter("attend"));
		
		Score2VO vo = new Score2VO();
		vo.setStu_name(stu_name);
		vo.setLec_name(cour_name);
		vo.setMid_exam(mid);
		vo.setFinal_exam(final_);
		vo.setAssignment(assign);
		vo.setAttendence(attend);
		dao.insertScore(vo);
		
		return "redirect:/score2";
	} 
	
	
	
	
	@RequestMapping("/scored1")
	public String view2() {
		return "scored1";
	}
	
	
	@RequestMapping(value="/scored2", method=RequestMethod.POST)
	public ModelAndView goScored(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		
		// Map<String, String> val = new HashMap<String, String>();
		MultiValuedMap<String, String> val = new ArrayListValuedHashMap<String, String>();
		
		
		try {
			int stu_number = Integer.valueOf(request.getParameter("stu_number"));
			String stu_password = request.getParameter("stu_password");
			
			ScoredVO vo = new ScoredVO();
			vo.setStu_num(stu_number);
			vo.setStu_password(stu_password);
			
			List<ScoredVO> ready = dao.selectScored(vo);		
			System.out.println(dao.selectScored(vo));
		
			if(ready.isEmpty()){
				String msg = "아이디 또는 비밀번호 불일치입니다";
				mav.setViewName("scored1");
				mav.addObject("msg", msg);
				
			}else {
				for(int i=0; i < ready.size(); i++) {
					System.out.println(ready.get(i));
				}
				
				for(ScoredVO so: ready) {
					val.put(so.getLec_sem(), so.getLec_sem());
					val.put(so.getLec_sem(), so.getLec_name());
					val.put(so.getLec_sem(), so.getLec_maj());
					val.put(so.getLec_sem(), String.valueOf( so.getMid_exam()));
					val.put(so.getLec_sem(), String.valueOf( so.getFinal_exam()));
					val.put(so.getLec_sem(), String.valueOf( so.getAssignment()));
					val.put(so.getLec_sem(), String.valueOf( so.getAttendence()));
					val.put(so.getLec_sem(), String.valueOf( so.getRes_score()));
					val.put(so.getLec_sem(), so.getRes_grade());
				}
				
				System.out.println(val);
				
				mav.setViewName("scored2");
				mav.addObject("stu_number", stu_number);
				mav.addObject("score_list", val);
				
			}
		
		
		}catch(NumberFormatException e2) {
			String msg = "아이디, 비밀번호를 입력해주세요";
			mav.setViewName("scored1");
			mav.addObject("msg", msg);
		}
		
		return mav;
	}
}
