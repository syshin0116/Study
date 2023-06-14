package pulu.com.member;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.extern.log4j.Log4j;
import pulu.com.common.AbstractDAO;

@Repository("memberDAO")
@Log4j
public class MemberDao extends AbstractDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	/* ---------------------- (1) 회원가입 ---------------------- */

	public void insertMember(Map<String, Object> map) throws Exception {
		log.info(map.get("id")); //
		log.info("memberDao의 insertMember() 동작");
		insert("member.insertMember", map);
	}

	/* ---------------------- (2) 로그인 ---------------------- */

	// 로그인 정보 불러오기
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectId(Map<String, Object> map) throws Exception {
		return (Map<String, Object>)selectOne("member.selectId", map);
	}

	// 아이디 찾기
	public String findId(Map<String, Object> map) throws Exception {
		return sqlSession.selectOne("login.findId", map);
	}

	// 비번 찾기
	public String findPw(Map<String, Object> map) throws Exception {
		return sqlSession.selectOne("login.findPw", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> findFail0(Map<String,Object>map) throws Exception{
		return (Map<String,Object>)selectOne("member.findId_fail",map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> findFail(Map<String,Object>map) throws Exception{
		return (Map<String,Object>)selectOne("member.findPw_fail",map);
	}

	/* ---------------------- (3) 마이페이지 ---------------------- */
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> memberInfo(Map<String,Object> map) throws Exception{
		return (Map<String,Object>)selectOne("member.info",map);
	}

	public void memberDelete(Map<String,Object> map) throws Exception{
		//return (Map<String,Object>)update("member.memberDelete",map);
		log.info(map.get("str_Num")); //
		log.info("memberDao의 memberDelete() 동작");
		update("member.memberDelete", map);
		
	}

	
}
