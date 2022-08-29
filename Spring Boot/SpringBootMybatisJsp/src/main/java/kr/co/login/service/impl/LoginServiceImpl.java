package kr.co.login.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.login.dao.LoginDao;
import kr.co.login.service.LoginService;
import kr.co.login.vo.LoginVo;

/*implements는 인터페이스를 상속받아서 구현하는 클래스*/

@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	public LoginDao  loginDao;  // 파일의 이름이 Mapper나 Dao나 같은의미 구글검색ㄱ

	/*게시판 상세조회*/
	public LoginVo selectLogin(LoginVo vo) {
		return loginDao.selectLogin(vo);
	}
}
