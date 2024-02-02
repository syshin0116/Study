package pulu.com.member;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j;

@Service("memberService")
@Log4j
public class MemberServiceImpl implements MemberService {
	
	@Resource(name = "memberDAO")
	private MemberDao memberDAO;

	protected void printQueryId(String queryId) {
		if(log.isDebugEnabled()) {
			log.debug("\t QueryId \t: " + queryId);
		}
	}
	
	
	@Override // 회원정보를 DB에 등록
	public void insertMember(Map<String, Object> map) throws Exception {
		log.info(map.get("id")); //
		log.info("ServiceImple의 insertMember() 동작");
		memberDAO.insertMember(map);
	}

	@Override
	public String findId(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return memberDAO.findId(map);
	}

	@Override
	public String findPw(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return memberDAO.findPw(map);
	}

	@Override
	public Map<String, Object> selectId(Map<String, Object> map) throws Exception {
		
		return memberDAO.selectId(map);
	}
	
	@Override
	public Map<String, Object> findFail0(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return memberDAO.findFail0(map);
	}
	
	 @Override
		public Map<String, Object> findFail(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return memberDAO.findFail(map);
	
	 }
	 @Override
		public Map<String,Object> memberInfo(Map<String,Object> map) throws Exception {
			// TODO Auto-generated method stub
			return memberDAO.memberInfo(map);
		}
		
		
	 
		@Override // 회원정보를 DB에 등록
		public void memberDelete(Map<String, Object> map) throws Exception {
			log.info(map.get("str_Num")); 
			log.info("ServiceImple의 memberDelete() 동작");
			memberDAO.memberDelete(map);
		}

}
