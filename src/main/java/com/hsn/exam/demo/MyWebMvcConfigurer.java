package com.hsn.exam.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.hsn.exam.demo.intercepter.BeforeActionIntercepter;
import com.hsn.exam.demo.intercepter.LoginIntercepter;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
	//BeforeActionIntercepter불러오기
	@Autowired
	BeforeActionIntercepter beforeActionIntercepter;
	
	@Autowired
	LoginIntercepter loginIntercepter;
	
	//인터셉터 적용부분
    public void addInterceptors(InterceptorRegistry registry) {
    	
    	InterceptorRegistration interceptorRegistration = registry.addInterceptor(beforeActionIntercepter);
    	
		
    	interceptorRegistration.addInterceptor(loginIntercepter)
                .addPathPatterns("/**") // 해당 경로에 접근하기 전에 인터셉터가 가로챈다.
                .excludePathPatterns("/resource/**") // 해당 경로는 인터셉터가 가로채지 않는다.
        		.excludePathPatterns("/error");
        
	}
	
	

}
