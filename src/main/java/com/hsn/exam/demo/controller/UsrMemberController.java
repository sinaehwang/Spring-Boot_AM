package com.hsn.exam.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	
	@RequestMapping("/usr/member/join")
	public String Join() {
		
		return "usr/member/join";
		
		
	}

	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public String doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,String email,@RequestParam(defaultValue = "/") String afterLoginUri) {
		if (Ut.empty(loginId)) {
			return rq.jsHistoryBack("아이디를 입력해주세요");
		}
		if (Ut.empty(loginPw)) {
			return rq.jsHistoryBack("비밀번호를 입력해주세요");
		}
		if (Ut.empty(name)) {
			return rq.jsHistoryBack("이름을 입력해주세요");
		}
		if (Ut.empty(nickname)) {
			return rq.jsHistoryBack("닉네임을 입력해주세요");
		}
		if (Ut.empty(cellphoneNum)) {
			return rq.jsHistoryBack("전화번호를 입력해주세요");
		}
		if (Ut.empty(email)) {
			return rq.jsHistoryBack("이메일을 입력해주세요");
		}
		// S-1
		// 회원가입이 완료되었습니다
		// F-1~8
		// 실패
		ResultData joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNum, email);
		
		if (joinRd.isFail()) {
			
			return rq.jsHistoryBack(joinRd.getMsg());
		}
		
		Member member = memberService.getMemberById((int) joinRd.getData1());
		
		String afterJoinUri = "../member/Login?afterLoginUri=" + Ut.getUriEncoded(afterLoginUri);//회원가입후 로그인페이지로 돌려보내기
		
		return rq.jsReplace(Ut.f("%s님 회원가입이 완료됬습니다.", member.getName()), afterJoinUri);
	}
	
	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public String doLogin(HttpServletRequest req,String loginId, String loginPw, @RequestParam(defaultValue = "/") String afterLoginUri) {
		
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
		
		return Ut.jsReplace(Ut.f("%s님 로그인 성공", member.getName()), afterLoginUri);
		
	}
	
	@RequestMapping("/usr/member/Login")
	public String login(HttpSession httpSession) {
		
		return "usr/member/login";
	}
	
	
	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public String doLogout(@RequestParam(defaultValue = "/") String afterLogoutUri) {
		
		
		rq.logout();
		
		return rq.jsReplace("로그아웃되었습니다.", afterLogoutUri);
	}
	
	@RequestMapping("/usr/member/Mypage")
	public String Mypage(Model model) {
		
		Member member = rq.getLoginedMember();
		
		model.addAttribute(member);
		
		
		return "usr/member/mypage";
	}
	
	@RequestMapping("/usr/member/checkPassword")
	public String checkPassword() {

		return "usr/member/checkPassword";
		
	}
	
	
	@RequestMapping("/usr/member/doCheckPassword")
	@ResponseBody
	public String doCheckPassword(String loginPw,String replaceUri) {


		if(Ut.empty(loginPw)) {
			return rq.jsHistoryBack("비밀번호를 입력해주시 바랍니다.");
		}
		
		if(rq.getLoginedMember().getLoginPw().equals(loginPw)==false) {
			return rq.jsHistoryBack("비밀번호가 일치하지 않습니다.");
		}
		
		if(replaceUri.equals("../member/modify")) {
			String memberModifyAuthKey = memberService.genMemberModifyAuthKey(rq.getLoginedMemberId()); //비밀번호확인완료되면 인증코드생성
			
			replaceUri += "?memberModifyAuthKey="+memberModifyAuthKey; //인증키가 더해진 replaceUri가됨
			
		}
		
		return rq.jsReplace("", replaceUri);
	}
	
	@RequestMapping("/usr/member/modify")
	public String modify(String memberModifyAuthKey) {
		
		if(Ut.empty(memberModifyAuthKey)) { //인증번호 코드없이 주소가 들어올경우를 막기위해서
			return rq.jsHistoryBackOnView("인증번호 코드가 필요합니다");
		}
		//입력한 인증번호 코드와 부여한 인증번호 코드가 일치하는지 체크
		
		ResultData CheckmemberModifyAuthKeyrd = memberService.CheckmemberModifyAuthKey(rq.getLoginedMemberId(),memberModifyAuthKey);
		
		if(CheckmemberModifyAuthKeyrd.isFail()) {
			
			return rq.jsHistoryBackOnView(CheckmemberModifyAuthKeyrd.getMsg());
		}

		return "usr/member/modify";
	}
	
	
	@RequestMapping("/usr/member/doModify") //doModify주소로 바로 접근하는 경우도 막아야함
	@ResponseBody //modify에서 보내는 인증코드랑 doModify에서 받는 코드가 동일한지 확인해야 doModify로 접근하는 경우를 막을수 있음
	public String doModify(String loginPw,String name,String nickname,String cellphoneNum,String email,String memberModifyAuthKey) {
		
		if(Ut.empty(memberModifyAuthKey)) {
			return rq.jsHistoryBackOnView("인증번호 코드가 필요합니다");
		}
		
		ResultData CheckmemberModifyAuthKeyrd = memberService.CheckmemberModifyAuthKey(rq.getLoginedMemberId(),memberModifyAuthKey);
		
		if(CheckmemberModifyAuthKeyrd.isFail()) {
			
			return rq.jsHistoryBack(CheckmemberModifyAuthKeyrd.getMsg());
		}
		
		
		if (Ut.empty(loginPw)) {
			loginPw = null;
		}
		if (Ut.empty(name)) {
			return rq.jsHistoryBack("이름을 입력해주세요");
		}
		if (Ut.empty(nickname)) {
			return rq.jsHistoryBack("닉네임을 입력해주세요");
		}
		if (Ut.empty(cellphoneNum)) {
			return rq.jsHistoryBack("전화번호를 입력해주세요");
		}
		if (Ut.empty(email)) {
			return rq.jsHistoryBack("이메일을 입력해주세요");
		}
		
		ResultData modifyRd  = memberService.doModify(rq.getLoginedMemberId(),loginPw,name,nickname,cellphoneNum,email);

		return rq.jsReplace(modifyRd .getMsg(),"/");
	}
	
	
	

}