package com.hsn.exam.demo.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.hsn.exam.demo.vo.Rq;

//로그인을 미리 체크하는 역활을 함
@Component
public class NeedLoginIntercepter implements HandlerInterceptor {
	@Autowired
	private Rq rq;

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
		
		
		if(rq.isLogined()==false) {
			
			String afterLoginUri = rq.getAfterLoginUri();
			
			rq.printReplaceJs("로그인후 이용해주세요", "../member/Login?afterLoginUri="+afterLoginUri);
			
			return false;
			
		}
		
		return HandlerInterceptor.super.preHandle(req, resp, handler);
	}


}
