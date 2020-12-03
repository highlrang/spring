package com.myproject.myweb;

import java.util.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.openkoreantext.processor.KoreanTokenJava;
import org.openkoreantext.processor.OpenKoreanTextProcessorJava;
import org.openkoreantext.processor.tokenizer.KoreanTokenizer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.myproject.myweb.domain.RegisteredVO;
import com.myproject.myweb.domain.ReviewVO;
import com.myproject.myweb.persistence.MajorDAO;
import com.myproject.myweb.persistence.ReviewDAO;

import scala.collection.Seq;

@Controller
public class ReviewController {
	@Inject
	private ReviewDAO dao;
	@Inject
	private MajorDAO dao2;
	
	@RequestMapping(value="readReview")
	public ModelAndView readView(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		try {
			HttpSession session = request.getSession();
			int stu_num = (Integer)session.getAttribute("ssUser");
			
			MultiValuedMap<String, String> myReview = new ArrayListValuedHashMap<String, String>();
			MultiValuedMap<String, String> myStar = new ArrayListValuedHashMap<String, String>();
			MultiValuedMap<String, String> myPublic = new ArrayListValuedHashMap<String, String>();
			MultiValuedMap<String, String> myKeyword = new ArrayListValuedHashMap<String, String>();
			Map<String, Integer> myCount = new HashMap<String, Integer>();
			String keyword = "";
			List<ReviewVO> getReview = dao.selectMyReview(stu_num);
			
			for(ReviewVO vo : getReview) {
				System.out.println(vo.getRev_content());
				myReview.put(vo.getLec_name(), vo.getRev_content());
				myStar.put(vo.getLec_name(), String.valueOf(vo.getRev_stars()));
				myPublic.put(vo.getLec_name(), String.valueOf(vo.getRev_public()));
				
				// 키워드
				keyword = vo.getRev_keyword();
				String[] keywordList = keyword.split("\\."); //Arrays.toString(keywordList);
				List<String> keyword2 = new ArrayList<String>();
				for(int i=0; i<keywordList.length; i++) {
					keyword2.add(keywordList[i]);
				}
				// List<String> list = Arrays.asList(str.split(",")); 도 가능 Map-HashMap
				
				for(int i=0; i<keyword2.size(); i=i+2) {
					myKeyword.put(vo.getLec_name(), keyword2.get(i));
				}
				
				for(int i=1; i<keyword2.size(); i=i+2) {
					myCount.put(keyword2.get(i-1), Integer.valueOf(keyword2.get(i)));
				}
				System.out.println(myKeyword);
				System.out.println(myCount);
			}
			
		
			
			mav.setViewName("reviewRead1");
			mav.addObject("myReview", myReview);
			mav.addObject("myStar", myStar);
			mav.addObject("myPublic", myPublic);
			mav.addObject("myKeyword", myKeyword);
			mav.addObject("myCount", myCount);
			
			
		}catch(NullPointerException e) {
			mav.setViewName("home");
			mav.addObject("msg", "세션이 만료되었습니다. 다시 로그인 해주세요.");
		}	
		return mav;
	}
	
	@RequestMapping(value="readReview2", method=RequestMethod.POST)
	public ModelAndView readView2(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String lec_name = request.getParameter("lec_name");
		List<ReviewVO> lec_id = dao.selectLecture(lec_name);
		
		MultiValuedMap<String, String> allReview = new ArrayListValuedHashMap<String, String>();
		MultiValuedMap<String, String> allStar = new ArrayListValuedHashMap<String, String>();
		
		for(ReviewVO vo : lec_id) {
			List<ReviewVO> getReview = dao.selectAllReview(vo.getLec_id());
			for(ReviewVO vo2: getReview) {
				allReview.put(vo2.getLec_name(), vo2.getRev_content());
				allStar.put(vo2.getLec_name(), String.valueOf(vo2.getRev_stars()));
			}
		}

		mav.setViewName("reviewRead2");
		mav.addObject("lec_name", lec_name);
		mav.addObject("allReview", allReview);
		mav.addObject("allStar", allStar);
		return mav;
	}
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="writeReview", method=RequestMethod.POST)
	public ModelAndView writeView(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		try {
			int stu_num = (Integer)session.getAttribute("ssUser");
			String lec_name = request.getParameter("lec_name");
			
			mav.setViewName("reviewWrite1");
			mav.addObject("stu_num", stu_num);
			mav.addObject("lec_name", lec_name);
		
		}catch(NullPointerException e) {
			mav.setViewName("home");
			mav.addObject("msg", "세션이 만료되었습니다. 다시 로그인 해주세요.");
		}
		return mav;
	}
	
	@RequestMapping(value="writeReview2", method=RequestMethod.POST)
	public ModelAndView writeView2(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();

		int stu_num = Integer.valueOf(request.getParameter("stu_num"));
		String lec_name = request.getParameter("lec_name");
		
		String rev_content = request.getParameter("rev_content");
		int rev_stars = Integer.valueOf(request.getParameter("rev_stars"));
		int rev_public = Integer.valueOf(request.getParameter("rev_public"));
		
		ReviewVO vo = new ReviewVO();
		vo.setLec_name(lec_name);
		vo.setStu_num(stu_num);
		vo.setRev_content(rev_content);
		vo.setRev_stars(rev_stars);
		vo.setRev_public(rev_public);
		
		// 빈도수 >> 워드 클라우드
		CharSequence normalized = OpenKoreanTextProcessorJava.normalize(rev_content);
		
		Seq<KoreanTokenizer.KoreanToken> tokens = OpenKoreanTextProcessorJava.tokenize(normalized);
		Map<String, Integer> arr = new HashMap<String, Integer>();
	    int count;
	    List<KoreanTokenJava> content = OpenKoreanTextProcessorJava.tokensToJavaKoreanTokenList(tokens);
	    for(KoreanTokenJava a : content) {
	    	String strPos = String.valueOf(a.getPos());
	    	
	    	if(strPos == "Noun") {
	    		if(arr.containsKey(a.getText())) {
	    			count = arr.get(a.getText()) + 1;
	    			arr.put(a.getText(), count);
	    		}else {
	    			arr.put(a.getText(), 1);
	    		}
	    	}
	    }
	   
	    String keyword = "";
	    for(String i: arr.keySet()) {
    		if(keyword.length() < 100) {
		    	if(arr.get(i) > 1) { // 수정하기
		    		keyword += i + "." + String.valueOf(arr.get(i)) + ".";
		    		// keyword += "'" + i + "'" + "||" + "'+'" + "||" + "'" + String.valueOf(arr.get(i)) + "'" + "||" + "'+'" + "||";
	    	    }
    		}	
	    }
	    
	    
	    keyword = keyword.substring(0, keyword.length()-1); // 뒤에 . 떼기
	    
	    System.out.println(keyword);
	    vo.setRev_keyword(keyword);
	    //
	    dao.insertReview(vo);
	    //

		
		
		
		MultiValuedMap<String, String> sem_list = new ArrayListValuedHashMap<String, String>();
		MultiValuedMap<String, String> maj_list = new ArrayListValuedHashMap<String, String>();
		MultiValuedMap<String, String> star_list = new ArrayListValuedHashMap<String, String>();
		
		System.out.println(dao2.selectRegistered(stu_num)); //
		List<RegisteredVO> registeredList = dao2.selectRegistered(stu_num);
		
		for(RegisteredVO vo2: registeredList) {
			sem_list.put(vo2.getLec_name(), vo2.getLec_sem());
			maj_list.put(vo2.getLec_name(), vo2.getLec_maj());
		}
		
		for(RegisteredVO vo2: registeredList) {
			vo.setLec_name(vo2.getLec_name());
			vo.setStu_num(stu_num);
			
			try {
				Integer star = dao.selectMyStars(vo);
				star_list.put(vo.getLec_name(), String.valueOf(star));
			}catch(NullPointerException e){
				star_list.put(vo.getLec_name(), "0");
			}
		}
		
		String msg = "강의 평가가 등록되었습니다";
		
		mav.setViewName("/registered1");
		mav.addObject("majList", maj_list);
		mav.addObject("semList", sem_list);
		mav.addObject("starList", star_list);
		mav.addObject("msg", msg);
		return mav;
	}
	
	
	
	@RequestMapping(value="updateReview", method=RequestMethod.POST)
	public ModelAndView updateView(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		try {
			int stu_num = (Integer)session.getAttribute("ssUser");
			String lec_name = request.getParameter("my_lec");
			
			ReviewVO vo = new ReviewVO();
			vo.setStu_num(stu_num);
			vo.setLec_name(lec_name);
			
			ReviewVO review = dao.selectForUpdate(vo);
			String rev_content = review.getRev_content();
			int rev_stars = review.getRev_stars();
			int rev_public = review.getRev_public();
			
			mav.setViewName("reviewUpdate1");
			mav.addObject("stu_num", stu_num);
			mav.addObject("lec_name", lec_name);
			mav.addObject("rev_content", rev_content);
			mav.addObject("rev_stars", rev_stars);
			mav.addObject("rev_public", rev_public);
		
		}catch(NullPointerException e) {
			mav.setViewName("home");
			mav.addObject("msg", "세션이 만료되었습니다. 다시 로그인 해주세요");
		}	
		return mav;
	}
	
	@RequestMapping(value="updateReview2", method=RequestMethod.POST)
	public String updateView2(HttpServletRequest request) {
		int stu_num = Integer.valueOf(request.getParameter("stu_num"));
		String lec_name = request.getParameter("lec_name");
		String rev_content = request.getParameter("rev_content");
		int rev_stars = Integer.valueOf(request.getParameter("rev_stars"));
		int rev_public = Integer.valueOf(request.getParameter("rev_public"));
		
		ReviewVO vo = new ReviewVO();
		vo.setStu_num(stu_num);
		vo.setLec_name(lec_name);
		vo.setRev_content(rev_content);
		vo.setRev_stars(rev_stars);
		vo.setRev_public(rev_public);
		
		CharSequence normalized = OpenKoreanTextProcessorJava.normalize(rev_content);
		Seq<KoreanTokenizer.KoreanToken> tokens = OpenKoreanTextProcessorJava.tokenize(normalized);
		Map<String, Integer> arr = new HashMap<String, Integer>();
	    int count;
	    List<KoreanTokenJava> content = OpenKoreanTextProcessorJava.tokensToJavaKoreanTokenList(tokens);
	    for(KoreanTokenJava a : content) {
	    	String strPos = String.valueOf(a.getPos());
	    	
	    	if(strPos == "Noun") {
	    		if(arr.containsKey(a.getText())) {
	    			count = arr.get(a.getText()) + 1;
	    			arr.put(a.getText(), count);
	    		}else {
	    			arr.put(a.getText(), 1);
	    		}
	    	}
	    }
	    String keyword = "";
	    for(String i: arr.keySet()) {
    		if(keyword.length() < 100) {
		    	if(arr.get(i) > 1) { // 수정하기
		    		keyword += i + "." + String.valueOf(arr.get(i)) + ".";
		    		// keyword += "'" + i + "'" + "||" + "'+'" + "||" + "'" + String.valueOf(arr.get(i)) + "'" + "||" + "'+'" + "||";
	    	    }
    		}	
	    }
	    keyword = keyword.substring(0, keyword.length()-1); // 뒤에 . 떼기
	    System.out.println(keyword);
	    
	    vo.setRev_keyword(keyword);
		dao.updateMyReview(vo);
		
		return "redirect:/readReview"; // 이거
	}
	
	
	@RequestMapping(value="deleteReview", method=RequestMethod.POST)
	public ModelAndView deleteView(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		try {
			int stu_num = (Integer)session.getAttribute("ssUser");
			String lec_name = request.getParameter("my_lec");
			
			ReviewVO vo = new ReviewVO();
			vo.setStu_num(stu_num);
			vo.setLec_name(lec_name);
			dao.deleteMyReview(vo);
			
			
			
			
			
			
			MultiValuedMap<String, String> myReview = new ArrayListValuedHashMap<String, String>();
			MultiValuedMap<String, String> myStar = new ArrayListValuedHashMap<String, String>();
			MultiValuedMap<String, String> myPublic = new ArrayListValuedHashMap<String, String>();
			List<ReviewVO> getReview = dao.selectMyReview(stu_num);
			
			for(ReviewVO vo2 : getReview) {
				myReview.put(vo2.getLec_name(), vo2.getRev_content());
				myStar.put(vo2.getLec_name(), String.valueOf(vo2.getRev_stars()));
				myPublic.put(vo2.getLec_name(), String.valueOf(vo2.getRev_public()));
			}
			
			String msg = "강의평가가 삭제되었습니다.";
			mav.setViewName("reviewRead1");
			mav.addObject("msg", msg);
			mav.addObject("myReview", myReview);
			mav.addObject("myStar", myStar);
			mav.addObject("myPublic", myPublic);
		
		}catch(NullPointerException e){
			mav.setViewName("home");
			mav.addObject("msg", "세션이 만료되었습니다. 다시 로그인 해주세요.");
		}	
		return mav;
	}
}
