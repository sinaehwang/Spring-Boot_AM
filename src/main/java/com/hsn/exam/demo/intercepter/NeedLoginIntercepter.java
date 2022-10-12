package com.hsn.exam.demo.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.hsn.exam.demo.vo.Rq;

//로그인을 미리 체크하는 역활을 함
@Component
public class NeedLoginIntercepter implements HandlerInterceptor {
	
	
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		
		Rq rq = (Rq) req.getAttribute("rq");
		
		if(rq.isLogined()==false) {
			
			print();
			res.getWriter().append(String.format("<script> alert('해당글에 대한 권한이 없습니다.');location.replace('../home/main');</script>"));
			
			return false;
		}
		
		return HandlerInterceptor.super.preHandle(req, res, handler);
	}

	public void print() {
		// TODO Auto-generated method stub
		
	}

}
