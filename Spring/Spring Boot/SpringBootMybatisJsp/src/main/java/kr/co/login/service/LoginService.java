package kr.co.login.service;

import kr.co.login.vo.LoginVo;

public interface LoginService {

	/*로그인 정보 조회*/
	public LoginVo selectLogin(LoginVo vo);
	
}
