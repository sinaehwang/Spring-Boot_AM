package com.hsn.exam.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.hsn.exam.demo.intercepter.BeforeActionIntercepter;
import com.hsn.exam.demo.intercepter.NeedLoginIntercepter;
import com.hsn.exam.demo.intercepter.NeedLogoutInterceptor;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
	//BeforeActionIntercepter불러오기
	@Autowired
	BeforeActionIntercepter beforeActionIntercepter;
	
	@Autowired
	NeedLoginIntercepter needLoginIntercepter;
	
	@Autowired
	NeedLogoutInterceptor needLogoutInterceptor ;
	
	//인터셉터 적용부분
    public void addInterceptors(InterceptorRegistry registry) {
    	
    	InterceptorRegistration ir;
    	
    	
    	ir = registry.addInterceptor(beforeActionIntercepter);
                ir.addPathPatterns("/**"); // 해당 경로에 접근하기 전에 인터셉터가 가로챈다.
                ir.addPathPatterns("/favicon.ico");
                ir.excludePathPatterns("/resource/**"); // 해당 경로는 인터셉터가 가로채지 않는다.
                ir.excludePathPatterns("/error");
    	
                //요청이 실행되기전 로그인이 필요함
    	ir = registry.addInterceptor(needLoginIntercepter);
    			ir.addPathPatterns("/usr/article/doAdd");
    			ir.addPathPatterns("/usr/article/write");
    			ir.addPathPatterns("/usr/article/doDelete");
    			ir.addPathPatterns("/usr/article/modify");
    			ir.addPathPatterns("/usr/article/doModify");
    			ir.addPathPatterns("/usr/article/doIncreaseHitCountRd");
    			ir.addPathPatterns("/usr/member/doLogout");
    			ir.addPathPatterns("/usr/member/Mypage");
    			ir.addPathPatterns("/usr/member/modify");
    			ir.addPathPatterns("/usr/reactionPoint/doGoodReaction");
    			ir.addPathPatterns("/usr/reactionPoint/doBadReaction");
    			ir.addPathPatterns("/usr/reply/doWriteReply");
    			ir.addPathPatterns("/usr/reply/doDelete");
    			ir.addPathPatterns("/usr/reply/modify");
    			ir.addPathPatterns("/usr/reply/doModify");
    			ir.addPathPatterns("/usr/reply/modify");
    			
    			//요청이 실행되기전에 로그아웃이 필요함
    	ir = registry.addInterceptor(needLogoutInterceptor);
    			ir.addPathPatterns("/usr/member/doJoin");
    			ir.addPathPatterns("/usr/member/doLogin");
    			ir.addPathPatterns("/usr/member/Login");
	}
	
	

}
