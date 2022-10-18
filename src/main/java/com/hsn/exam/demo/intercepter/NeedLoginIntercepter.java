package com.hsn.exam.demo.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.hsn.exam.demo.vo.Rq;

//로그인을 미리 체크하는 역활을 함
@Component
public class NeedLoginIntercepter implements HandlerInterceptor {
	
	private Rq rq;

	public NeedLoginIntercepter(Rq rq) {
		this.rq = rq;
	}
	
	
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
		
		
		if(rq.isLogined()==false) {
			
			rq.printHistoryBackJs("로그인후 이용해주세요");
			//resp.setContentType("text/html; charset=UTF-8");
			//res.getWriter().append(String.format("<script> alert('해당글에 대한 권한이 없습니다.');location.replace('../home/main');</script>"));
			
			return false;
		}
		
		return HandlerInterceptor.super.preHandle(req, resp, handler);
	}


}
