package kr.co.login.vo;

import lombok.Data;

@Data
public class LoginVo {
	
	/*lombok은 아직 설정안함*/
	/*테이블 프라이머리 키 (유일값) */
	private String userNo;
	/*회원아이디*/
	private String userId;
	/*회원비밀번호*/
	private String userPw;
	/*회원이름*/
	private String userNm;
	/*회원메일*/
	private String userMail;
	/*회원가입일*/
	private String userRegDt;
}
