package com.myproject.myweb.persistence;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.myproject.myweb.domain.LoginVO;

import javax.inject.Inject;

@Service
public class LoginDAO {
	@Inject
	private SqlSession sqlSession;
	
	private static final String SelectStudent = "LoginMapper.selectStudent";
	private static final String SelectProfessor = "LoginMapper.selectProfessor";
	private static final String SelectStuByNum = "LoginMapper.selectStuByNum";
	private static final String SelectProfByNum = "LoginMapper.selectProfByNum";
	
	public LoginVO selectStudent(LoginVO vo) {
		return sqlSession.selectOne(SelectStudent, vo);
	}
	
	public LoginVO selectProfessor(LoginVO vo) {
		return sqlSession.selectOne(SelectProfessor, vo);
	}
	
	public LoginVO selectStuByNum(int stu_num) {
		return sqlSession.selectOne(SelectStuByNum, stu_num);
	}
	
	public LoginVO selectProfByNum(int prof_num) {
		return sqlSession.selectOne(SelectProfByNum, prof_num);
	}
}
