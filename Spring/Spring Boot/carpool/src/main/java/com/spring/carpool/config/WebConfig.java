package com.spring.carpool.config;

import com.spring.carpool.LoginIntercepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LoginIntercepter loginIntercepter = new LoginIntercepter();

        //addInterceptor()란 ? 애플리케이션 내에 인터셉터를(loginIntercepter) 등록해주는 기능을 한다.
        registry.addInterceptor(loginIntercepter)
                //   .excludePathPatterns()의 경우 인터셉터를 제외할 url 패턴을 등록하는 메서드로써
                //   해당 url로 접근 시에는 인터셉터 체크를 적용하지 않게 한다.

                //   addPathPatterns는 인터셉터 체크를 적용할 url 패턴을 설정한다.
                //   *, **등을 사용한 URI Pattern String을 전송하거나, URI Pattern 배열을 전송할 수 있다.
                // 게시판 작성페이지, 게시판 등록 프로세스는 로그인한 사용자만 접근가능 하기때문에 인터셉터 체크를 적용한다.
                .addPathPatterns("/member/myPage","/member/myPageUpdate","/member/passwordUpdate");

    }

}