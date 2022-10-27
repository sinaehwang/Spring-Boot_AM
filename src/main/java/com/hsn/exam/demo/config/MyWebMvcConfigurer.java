package com.hsn.exam.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
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
    	
    	InterceptorRegistration ir;
    	
    	
    	ir = registry.addInterceptor(beforeActionIntercepter);
                ir.addPathPatterns("/**"); // 해당 경로에 접근하기 전에 인터셉터가 가로챈다.
                ir.addPathPatterns("/favicon.ico");
                ir.excludePathPatterns("/resource/**"); // 해당 경로는 인터셉터가 가로채지 않는다.
                ir.excludePathPatterns("/error");
    	
    	ir = registry.addInterceptor(needLoginIntercepter);
    			ir.addPathPatterns("/usr/article/doAdd");
    			ir.addPathPatterns("/usr/article/write");
    			ir.addPathPatterns("/usr/article/doDelete");
    			ir.addPathPatterns("/usr/article/modify");
    			ir.addPathPatterns("/usr/article/doModify");
    			ir.addPathPatterns("/usr/reactionPoint.doGoodReaction");
    			ir.addPathPatterns("/usr/reactionPoint.doBadReaction");
        
	}
	
	

}
