package com.myproject.myweb.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.myproject.myweb.domain.CourseVO;
import com.myproject.myweb.domain.MajorVO;
import com.myproject.myweb.domain.RegisterVO;
import com.myproject.myweb.domain.RegisteredVO;

@Service
public class MajorDAO{
	@Inject
	private SqlSession sqlSession;
	
	private static final String SelectMajor = "MajorMapper.selectMajor";
	private static final String SelectCourse = "MajorMapper.selectCourse";
	private static final String SelectMinCount = "MajorMapper.selectMinCount";
	private static final String SelectMaxCount = "MajorMapper.selectMaxCount";
	private static final String InsertRegister = "MajorMapper.insertRegister";
	private static final String SelectRegister = "MajorMapper.selectRegistered";
	
	public List<MajorVO> selectMajor() {
		return sqlSession.selectList(SelectMajor);
	}
	
	public List<CourseVO> selectCourse(String maj_name){ 
		return sqlSession.selectList(SelectCourse, maj_name);
	}
	
	public Integer selectMinCount(RegisterVO vo) {
		return sqlSession.selectOne(SelectMinCount, vo);
	}
	
	public RegisterVO selectMaxCount(RegisterVO vo) {
		return sqlSession.selectOne(SelectMaxCount, vo);
	}
	
	public void insertRegister(RegisterVO vo) {
		sqlSession.insert(InsertRegister, vo);
	}

	public List<RegisteredVO> selectRegistered(int stu_number) {
		return sqlSession.selectList(SelectRegister, stu_number);
	}
}
