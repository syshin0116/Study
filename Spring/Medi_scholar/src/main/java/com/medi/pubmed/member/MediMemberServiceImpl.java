package com.medi.pubmed.member;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediMemberServiceImpl implements MediMemberService{

	private static final Logger logger = LoggerFactory.getLogger(MediMemberServiceImpl.class);

	@Autowired
	private MediMemberDAO medimemberdao;


	//사용자정보
	@Override
	public HashMap<String, Object> getUserInfo(HashMap<String, Object> param) {
		HashMap<String, Object> result  = medimemberdao.getUserInfo(param);
		logger.info("Member service activated");
		logger.info(String.valueOf(param));
		logger.info(String.valueOf(result));
		return result;
	}
	//회원 정보 추가
	@Override
	public void insertUserInfo(HashMap<String, Object> param) {
		medimemberdao.insertUserInfo(param);
	}
	//구글 이메일 db 유무
	@Override
	public int checkEmailExist(String email) {
		return medimemberdao.checkEmailExist(email);
	}
	//구글 계정 확인
	@Override
	public HashMap<String, Object> googleUserInfo(HashMap<String, Object> param) {
		return medimemberdao.googleUserInfo(param);
	}
	//
	@Override
	public int isUserIdDuplicate(String userId) {
		return medimemberdao.isUserIdDuplicate(userId);
	}
	//국가 선택지 목록
	@Override
	public List<HashMap<String, Object>> getnation(HashMap<String, Object> param) {
		return medimemberdao.getnation(param);
	}
	//직업 선택지 목록
	@Override
	public List<HashMap<String, Object>> getjob(HashMap<String, Object> param) {
		return medimemberdao.getjob(param);
	}
	//관심분야 선택지 목록 - 주분야
	@Override
	public List<HashMap<String, Object>> getpriList(HashMap<String, Object> param) {
		return medimemberdao.getpriList(param);
	}

	@Override
	public List<HashMap<String, Object>> getsignList(String primary) {
		return medimemberdao.getsignList(primary);
	}


}
