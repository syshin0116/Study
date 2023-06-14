package kr.co;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.log4j.Log4j2;

/**
* @packageName    : kr.co
* @fileName       : LoginIntercepter.java
* @author         : HYE
* @date           : 2022.08.28
* @description    : 로그인 인터셉터 관련 설정파일, 추후작업 예정
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2022.08.28        Hye      			최초 생성
*/

//1.1 인터셉터란?
//컨트롤러(Controller)의 '핸들러(Handler)'를 호출하기 전과 후에 요청과 응답을 참조하거나 가공할수 있는 일종의 필터
//1.2 왜 사용하는가?
//개발자는 특정 Controller의 핸들러가 실행되기 전이나 후에 추가적인 작업을 원할때 Interceptor를 사용한다.
// 1) preHandle()란 ? 컨트롤러가 호출되기 전에 실행됨
// 2) postHandle()란? 핸들러가 실행은 완료 되었지만 아직 View가 생성되기 이전에 호출된다.
// 3) afterCompletion()란? 모든 View에서 최종 결과를 생성하는 일을 포함한 모든 작업이 완료된 후에 실행된다.

@Log4j2
public class LoginIntercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	log.info("호출한 컨트롤러 주소는? : {}", request.getRequestURI());
    	return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    	log.info("view로 보내기전 작업은? : {}", response.getStatus());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
