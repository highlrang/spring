package com.myproject.myweb.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.myproject.myweb.domain.Score2VO;
import com.myproject.myweb.domain.ScoreVO;
import com.myproject.myweb.domain.ScoredVO;

@Service
public class ScoreDAO {
	@Inject
	private SqlSession sqlSession;

	private static final String SelectScore = "ScoreMapper.selectScore";
	private static final String InsertScore = "ScoreMapper.insertScore";
	private static final String SelectScored = "ScoreMapper.selectScored";
	private static final String SelectComplete = "ScoreMapper.selectComplete";
	
	public List<ScoreVO> selectScore(ScoreVO vo) {
		return sqlSession.selectList(SelectScore, vo);
	}
	
	public void insertScore(Score2VO vo) {
		sqlSession.insert(InsertScore, vo);
	}
	
	
	public List<ScoredVO> selectScored(ScoredVO vo){
		return sqlSession.selectList(SelectScored, vo);
	}
	
	public List<ScoredVO> selectComplete(int prof_num){
		return sqlSession.selectList(SelectComplete, prof_num);
	}
}
