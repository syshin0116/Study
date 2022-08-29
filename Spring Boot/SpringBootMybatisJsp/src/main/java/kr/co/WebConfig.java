package kr.co;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
* @packageName    : kr.co
* @fileName       : WebConfig.java
* @author         : HYE
* @date           : 2022.08.28
* @description    : 웹관련 설정파일
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2022.08.28        Hye      			최초 생성
*/
@Configuration
public class WebConfig implements WebMvcConfigurer {
	
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        LoginIntercepter loginIntercepter = new LoginIntercepter();
        registry.addInterceptor(loginIntercepter)
                .addPathPatterns()
                .excludePathPatterns();
    }
	
}
