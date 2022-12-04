package pulu.com.member;

import java.util.Map;

public interface MemberService {

	/* ---------------------- (1) 회원가입 ---------------------- */

	// 회원정보를 DB에 등록
	void insertMember(Map<String, Object> commandMap) throws Exception;

	
	/* ---------------------- (2) 로그인 ---------------------- */
	
	// 아이디 찾기
	String findId(Map<String, Object> map) throws Exception;

	// 비번 찾기
	String findPw(Map<String, Object> map) throws Exception;

	// 로그인 정보 불러오기
	Map<String, Object> selectId(Map<String, Object> map) throws Exception;

	//아이디찾기실패
	Map<String,Object> findFail0(Map<String,Object>map)throws Exception;
	
	//비밀번호찾기 실패
	Map<String,Object> findFail(Map<String,Object>map)throws Exception;
	
	
	
	/* ---------------------- (3) 마이페이지 ---------------------- */
	
	//회원정보조회
		Map<String,Object> memberInfo(Map<String,Object>map)throws Exception;
		
	
		
		/*
		 * //회원탈퇴 Map<String,Object> memberDelete(Map<String,Object>map)throws
		 * Exception;
		 */
	
		void memberDelete(Map<String, Object> commandMap) throws Exception;
}
