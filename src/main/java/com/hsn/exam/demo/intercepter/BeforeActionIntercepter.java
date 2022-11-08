package com.hsn.exam.demo.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.hsn.exam.demo.vo.Rq;

@Component
public class BeforeActionIntercepter implements HandlerInterceptor {
	@Autowired
	private Rq rq;
	
	public BeforeActionIntercepter(Rq rq) {
		this.rq = rq;
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
		
		
		//rq.initOnBeforeActionInterceptor(); beforeAction을 거치는 모든 메소드마다 무조건 처음부터 rq 객체를 생성해서 가지게됨
		
		req.setAttribute("rq", rq);//req가 호출될때 rq 대리자를 생성
		
		return HandlerInterceptor.super.preHandle(req, resp, handler);
	}
}
