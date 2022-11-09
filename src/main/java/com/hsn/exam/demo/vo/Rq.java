package com.hsn.exam.demo.vo;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.hsn.exam.demo.service.MemberService;
import com.hsn.exam.demo.util.Ut;

import lombok.Getter;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Rq {// request요청에 의해 rq객체를 무조건 생성하는게 아니라 로직상 필요시에만 rq객체를 만들어서 호출한 클래스에 맞춰서 rq객체를 보내줌

	@Getter
	private boolean isLogined;
	@Getter
	private int loginedMemberId;
	@Getter
	private Member loginedMember;

	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession session;
	private Map<String, String> paramMap;

	public Rq(HttpServletRequest req, HttpServletResponse resp, MemberService memberService) {
		this.req = req;
		this.resp = resp;

		paramMap = Ut.getParamMap(req);

		this.session = req.getSession();

		boolean isLogined = false;
		int loginedMemberId = 0;
		Member loginedMember = null;

		if (session.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
			loginedMember = memberService.getMemberById(loginedMemberId);
		}

		this.isLogined = isLogined;
		this.loginedMemberId = loginedMemberId;
		this.loginedMember = loginedMember;

		// this.req.setAttribute("rq", this);

	}

	public void printHistoryBackJs(String msg) {
		resp.setContentType("text/html; charset=UTF-8");
		print(Ut.jsHistoryBack(msg));
	}

	public void printReplaceJs(String msg, String url) {
		resp.setContentType("text/html; charset=UTF-8");
		print(Ut.jsReplace(msg, url));

	}

	public void print(String str) {
		try {
			resp.getWriter().append(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void println(String str) {
		print(str + "\n");
	}

	public void login(Member member) {
		session.setAttribute("loginedMemberId", member.getId());
	}

	public void logout() {
		session.removeAttribute("loginedMemberId");
	}

	public boolean isNotLogined() {
		return !isLogined;
	}

	public String jsHistoryBackOnView(String msg) {
		req.setAttribute("msg", msg);
		req.setAttribute("historyBack", true);
		return "usr/common/js";
	}

	public String jsHistoryBack(String msg) {
		return Ut.jsHistoryBack(msg); // alert로 메세지 출력후 history.back
	}

	public String jsReplace(String msg, String uri) {
		return Ut.jsReplace(msg, uri); // alert로 메세지 출력후 원하는 url주소로 돌아가게함
	}

	// 해당 메서드는 Rq 객체의 생성을 유도한다.
	// 삭제 금지, 편의를 위하여 BeforeActionInterceptor 에서 호출해줘야 한다.
//	public void initOnBeforeActionInterceptor() {
//		
//	}

	public String getCurrentUri() {

		String CurrentUri = req.getRequestURI();// 현재URI를 가져오고

		String queryString = req.getQueryString();// 현재쿼리문을가져오고

		if (queryString != null && queryString.length() > 0) {

			CurrentUri += "?" + queryString;// URI에 쿼리문을 붙여줌

		}

		return CurrentUri;

	}

	public String getEncodedCurrentUri() { // URI를 정제하는 메소드실행

		return Ut.getUriEncoded(getCurrentUri());
	}

	public String getLoginUri() { // URI를 정제하는 메소드실행

		return "../member/Login?afterLoginUri=" + getAfterLoginUri();
	}

	public String getAfterLoginUri() {

		String requestUri = req.getRequestURI();

		switch (requestUri) { //로그인이후라면 접근할수없는 페이지주소들
		case "/usr/member/Login":
		case "/usr/member/join":
		case "/usr/member/findLoginId":
		case "/usr/member/findLoginPw":
			return Ut.getUriEncoded(paramMap.get("afterLoginUri"));
		}

		return getEncodedCurrentUri(); 
	}

}