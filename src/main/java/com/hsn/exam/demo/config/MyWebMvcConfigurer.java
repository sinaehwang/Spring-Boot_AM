package com.hsn.exam.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.hsn.exam.demo.intercepter.BeforeActionIntercepter;
import com.hsn.exam.demo.intercepter.NeedLoginIntercepter;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
	//BeforeActionIntercepter불러오기
	@Autowired
	BeforeActionIntercepter beforeActionIntercepter;
	
	@Autowired
	NeedLoginIntercepter needLoginIntercepter;
	
	//인터셉터 적용부분
    public void addInterceptors(InterceptorRegistry registry) {
    	
    	registry.addInterceptor(beforeActionIntercepter)
                .addPathPatterns("/**") // 해당 경로에 접근하기 전에 인터셉터가 가로챈다.
                .excludePathPatterns("/resource/**") // 해당 경로는 인터셉터가 가로채지 않는다.
        		.excludePathPatterns("/error");
    	
    	registry.addInterceptor(needLoginIntercepter)
    			.addPathPatterns("/usr/article/doAdd")
    			.addPathPatterns("/usr/article/doDelete")
    			.addPathPatterns("/usr/article/modify")
    			.addPathPatterns("/usr/article/doModify");
        
	}
	
	

}
