package com.medi.pubmed.journal;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JournalDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;

	public List<HashMap<String, Object>> getJournalList(HashMap<String, Object> param){
		return sqlSession.selectList("getJournalList", param); 
	}
}