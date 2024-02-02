package com.medi.pubmed.member;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MediMemberDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	// 디비에 구글이메일 유무확인
	public int checkEmailExist(String email) {
		return sqlSession.selectOne("checkEmailExist", email);

	}

	// 구글로그인 계정 정보
	public HashMap<String, Object> googleUserInfo(HashMap<String, Object> param) {
		return sqlSession.selectOne("googleUserInfo", param);
	}

	// 사용자 로그인 정보
	public HashMap<String, Object> getUserInfo(HashMap<String, Object> param) {
		return sqlSession.selectOne("getUserInfo", param);
	}
	
	// 회원가입 아이디 중복 체크
	public int isUserIdDuplicate(String userId) {
		return sqlSession.selectOne("isUserIdDuplicate", userId);
	}

	// 회원가입
	public int insertUserInfo(HashMap<String, Object> param) {
		return sqlSession.insert("insertUserInfo", param);

	}

	// 국가 선택지 리스트
	public List<HashMap<String, Object>> getnation(HashMap<String, Object> param) {
		return sqlSession.selectList("getnation", param);
	}

	// 직업 선택지 리스트
	public List<HashMap<String, Object>> getjob(HashMap<String, Object> param) {
		return sqlSession.selectList("getjob", param);
	}

	// 주분야 선택지 리스트
	public List<HashMap<String, Object>> getsignList(String primary) {
		return sqlSession.selectList("getsignList", primary);
	}

	// 관심분야 주
	public List<HashMap<String, Object>> getpriList(HashMap<String, Object> param) {
		return sqlSession.selectList("getpriList", param);
	}
		


	}
