package com.medi.pubmed.member;

import java.util.HashMap;
import java.util.List;

public interface MediMemberService {
	
	// 구글로그인 이메일 db유무 확인
	int checkEmailExist(String email);

	// 구글로그인 시 회원 가입한 사용자의 사용자 정보 조회
	HashMap<String, Object> googleUserInfo(HashMap<String, Object> param);

	// 로그인시 사용자 정보 조회
	HashMap<String, Object> getUserInfo(HashMap<String, Object> param);

	// 중복아이디 조회
	int isUserIdDuplicate(String userId);

	// 사용자 정보 입력
	void insertUserInfo(HashMap<String, Object> param);

	// 국가 선택지 목록
	List<HashMap<String, Object>> getnation(HashMap<String, Object> param);

	// 직업 선택지 목록
	List<HashMap<String, Object>> getjob(HashMap<String, Object> param);

	// 관심 주분야 목록 조회
	List<HashMap<String, Object>> getpriList(HashMap<String, Object> param);

	// 세부 분야 목록 조회
	List<HashMap<String, Object>> getsignList(String primary);

	
	
}
