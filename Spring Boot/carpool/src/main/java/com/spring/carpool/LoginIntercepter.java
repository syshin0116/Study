package com.spring.carpool;

import com.spring.carpool.dto.MemberDTO;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginIntercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //컨트롤러 리퀘스트맵핑 주소를 호출하기전에 로그인 세션을 체크한다.
        HttpSession session = request.getSession();
        MemberDTO loginInfo = (MemberDTO) session.getAttribute("loginInfo");

        //로그인 세션정보를 인터셉터에서 체크한다. 없으면 로그인 페이지로 이동, 접근 통제는 WebConfig.java에서함
        if(ObjectUtils.isEmpty(loginInfo)){
            //로그인정보가 없으면 로그인 페이지로 강제이동
            response.sendRedirect("/memberPages/loginForm"); //이건 컨트롤러 리퀘스트 맵핑주소
        }

        return true;


    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}