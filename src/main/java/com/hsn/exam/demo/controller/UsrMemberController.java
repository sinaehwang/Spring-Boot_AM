package com.hsn.exam.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsn.exam.demo.service.MemberService;
import com.hsn.exam.demo.util.Ut;
import com.hsn.exam.demo.vo.Member;
import com.hsn.exam.demo.vo.ResultData;
import com.hsn.exam.demo.vo.Rq;

@Controller
public class UsrMemberController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private Rq rq;
	
	public UsrMemberController(MemberService memberService, Rq rq) {
		this.memberService = memberService;
		this.rq = rq;
	}
	

	@RequestMapping("usr/member/doJoin")
	@ResponseBody
	public ResultData doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,String email) {

		if (Ut.empty(loginId)) {
			return ResultData.from("F-1", "아이디를 입력해주세요");
		}
		if (Ut.empty(loginPw)) {
			return ResultData.from("F-2", "비밀번호를 입력해주세요");
		}
		if (Ut.empty(name)) {
			return ResultData.from("F-3", "이름을 입력해주세요");
		}
		if (Ut.empty(nickname)) {
			return ResultData.from("F-4", "닉네임을 입력해주세요");
		}
		if (Ut.empty(cellphoneNum)) {
			return ResultData.from("F-5", "전화번호를 입력해주세요");
		}
		if (Ut.empty(email)) {
			return ResultData.from("F-6", "이메일을 입력해주세요");
		}
		// S-1
		// 회원가입이 완료되었습니다
		// F-1~8
		// 실패
		ResultData joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNum, email);

		if (joinRd.isFail()) {
			return joinRd;
		}

		Member member = memberService.getMemberById((int) joinRd.getData1());

		return ResultData.newData(joinRd,"Member" ,member);
	}
	
	@RequestMapping("usr/member/doLogin")
	@ResponseBody
	public String doLogin(HttpServletRequest req,String loginId, String loginPw) {
		
		
		if(rq.isLogined()) {
			return Ut.jsHistoryBack("이미 로그인상태입니다.");
		}
		
		if (Ut.empty(loginId)) {
			//return ResultData.from("F-1", "아이디를 입력해주세요");
			return Ut.jsHistoryBack("아이디를 입력해주세요");
		}
		if (Ut.empty(loginPw)) {
			//return ResultData.from("F-2", "비밀번호를 입력해주세요");
			return Ut.jsHistoryBack("비밀번호를 입력해주세요");
		}

		Member member=memberService.getMemberByLoginId(loginId);
		
		if(member==null) {
			//return ResultData.from("F-3", "일치하는 회원이 없습니다.");
			return Ut.jsHistoryBack("일치하는 회원이 없습니다.");
		}
		
		if(member.getLoginPw().equals(loginPw)==false) {
			//return ResultData.from("F-4", "비밀번호가 일치하지 않습니다.");
			return Ut.jsHistoryBack("비밀번호가 일치하지 않습니다.");
		}
		
		
		rq.login(member);
		
		return Ut.jsReplace(Ut.f("%s님 로그인 성공", member.getName()), "/");
		
	}
	
	@RequestMapping("usr/member/Login")
	public String login(HttpSession httpSession) {
		
		return "usr/member/login";
	}
	
	
	@RequestMapping("usr/member/doLogout")
	@ResponseBody
	public String doLogout(HttpServletRequest req) {
		
		
		if(rq.isLogined()==false) {
			//return ResultData.from("F-3", "이미 로그아웃상태입니다.");
			return Ut.jsHistoryBack("이미 로그아웃상태입니다.");
		}
		
		rq.logout();
		
		return Ut.jsReplace("로그아웃되었습니다.", "/");
	}
	
	

}