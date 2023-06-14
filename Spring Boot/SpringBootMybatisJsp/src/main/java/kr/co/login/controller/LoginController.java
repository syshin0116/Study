package kr.co.login.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.login.service.LoginService;
import kr.co.login.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
/**
* @packageName    : kr.co.login.controller
* @fileName       : LoginController.java
* @author         : HYE
* @date           : 2022.08.28
* @description    : 로그인 컨트롤러
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2022.08.28        Hye      			최초 생성
*/
@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	LoginService loginService;
    
    /**
    * @methodName    : login
    * @description 	 : 로그인 페이지 이동
    * @author        : Hye
    * @date        : 2022.08.27
    * @param 
    * @return
    * @throws Exception
    */  
    @GetMapping("/login")
	public String login() throws Exception{
    	log.info("LoginController.java > login() 호출");
    	return "/login/login"; //생성한 jsp명
	}

    /**
    * @methodName    : selectLogin
    * @description 	 : 로그인 정보 조회
    * @author        : Hye
    * @date        : 2022.08.27
    * @param 
    * @return
    * @throws Exception
    */  
    @PostMapping("/selectLogin")
	public void selectLogin(HttpServletResponse response, HttpSession session, LoginVo vo) throws Exception{
    	
    	response.setContentType("text/html; charset=euc-kr");
    	PrintWriter out;
    	out = response.getWriter();

    	log.info("BoardController.java > boardList() 호출");
    	log.info("LoginVo.java > vo "+vo.toString());
    	
		try {
			//1-1. 마이바티스에서는 조회결과 확인
			if(loginService.selectLogin(vo) != null) {
				
				//로그인 정보를 세션에 담는다.
				session.setAttribute("loginUserData", loginService.selectLogin(vo));
				out.println("<script>alert('로그인을 성공 하였습니다.');  location.href='/main';</script>");
				out.flush();		
			}else {
				out.println("<script>alert('회원정보가 없습니다.');  location.href='/login/login';</script>");
				out.flush();	
			}
		} catch (Exception e) {
			out.println("<script>alert('회원정보가 없습니다.');  location.href='/login/login';</script>");
			out.flush();	
			e.printStackTrace();
		}    
	}    

    /**
    * @methodName    : logout
    * @description 	 : 로그아웃 처리
    * @author        : Hye
    * @date        : 2022.08.27
    * @param 
    * @return
    * @throws Exception
    */  
    @GetMapping("/logout")
	public void logout(HttpServletResponse response, HttpSession session) throws Exception{

    	//모든 세션정보를 제거한다.
    	session.invalidate();
    	
    	response.setContentType("text/html; charset=euc-kr");
    	PrintWriter out;
    	out = response.getWriter();
		out.println("<script>alert('로그아웃을 하였습니다.');  location.href='/main';</script>");
		out.flush();	
    }
    
}
