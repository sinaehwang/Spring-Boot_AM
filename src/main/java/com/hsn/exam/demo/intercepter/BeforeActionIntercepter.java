package com.hsn.exam.demo.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.hsn.exam.demo.service.MemberService;
import com.hsn.exam.demo.vo.Rq;

@Component
public class BeforeActionIntercepter implements HandlerInterceptor {
	
	private Rq rq;

	public BeforeActionIntercepter(Rq rq) {
		this.rq = rq;
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
		
		
		rq.initOnBeforeActionInterceptor();
		
		return HandlerInterceptor.super.preHandle(req, resp, handler);
	}
}
