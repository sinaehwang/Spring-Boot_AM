package com.hsn.exam.demo.vo;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hsn.exam.demo.service.MemberService;
import com.hsn.exam.demo.util.Ut;

import lombok.Getter;

public class Rq {
	@Getter
	private boolean isLogined;
	@Getter
	private int loginedMemberId;
	@Getter
	private Member loginedMember;
	

	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession httpSession;
	
	public Rq(HttpServletRequest req, HttpServletResponse resp,MemberService memberService) {
		
		this.req = req;
		this.resp = resp;
		this.httpSession = req.getSession();
		
		boolean isLogined = false;
		int loginedMemberId = 0;
		Member loginedMember = null;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
			loginedMember = memberService.getMemberById(loginedMemberId);
		}

		this.isLogined = isLogined;
		this.loginedMemberId = loginedMemberId;
		this.loginedMember = loginedMember;
	}

	public void printHistoryBackJs(String msg) {
		
		resp.setContentType("text/html; charset=UTF-8");

		print(Ut.jsHistoryBack(msg));
		
		
	}

	public void print(String str) {
		try {
			resp.getWriter().append(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void println(String str) { //스크립트 작성시 문장 붙임 오류방지를 위해
		print(str + "\n");
	}

	public void login(Member member) {
		
		httpSession.setAttribute("loginedMemberId", member.getId());
		
	}

	public void logout() {
		
		httpSession.removeAttribute("loginedMemberId");
		
	}

	public String jsHistoryBackOnView(String msg) {
		req.setAttribute("msg", msg);
		req.setAttribute("historyBack", true);
		return "usr/common/js";
	}

	public String jsHistoryBack(String msg) {
		
		return Ut.jsHistoryBack(msg);
	}

	public String jsReplace(String msg, String url) {
		
		return Ut.jsReplace(msg, url);
	}

}