package com.myproject.myweb.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.myproject.myweb.domain.ReviewVO;

@Service
public class ReviewDAO {
	@Inject
	private SqlSession sqlSession;
	
	private static final String InsertReview = "ReviewMapper.insertReview";
	private static final String SelectLecture = "ReviewMapper.selectLecture";
	private static final String SelectAllReview = "ReviewMapper.selectAllReview";
	private static final String SelectMyReview = "ReviewMapper.selectMyReview";
	private static final String SelectMyStars = "ReviewMapper.selectMyStars";
	private static final String SelectForUpdate = "ReviewMapper.selectForUpdate";
	private static final String UpdateMyReview = "ReviewMapper.updateMyReview";
	private static final String DeleteMyReview = "ReviewMapper.deleteMyReview";
	
	
	
	public void insertReview(ReviewVO vo) {
		sqlSession.insert(InsertReview, vo);
	}
	
	public List<ReviewVO> selectLecture(String lec_name) {
		return sqlSession.selectList(SelectLecture, lec_name);
	}
	
	public List<ReviewVO> selectAllReview(int lec_id) {
		return sqlSession.selectList(SelectAllReview, lec_id);
	}
	
	public List<ReviewVO> selectMyReview(int stu_num) {
		return sqlSession.selectList(SelectMyReview, stu_num);
	}
	
	public Integer selectMyStars(ReviewVO vo) {
		return sqlSession.selectOne(SelectMyStars, vo);
	}
	
	public ReviewVO selectForUpdate(ReviewVO vo){
		return sqlSession.selectOne(SelectForUpdate, vo);
	}
	
	public void updateMyReview(ReviewVO vo) {
		sqlSession.update(UpdateMyReview, vo);
	}
	
	public void deleteMyReview(ReviewVO vo) {
		sqlSession.delete(DeleteMyReview, vo);
	}
}
