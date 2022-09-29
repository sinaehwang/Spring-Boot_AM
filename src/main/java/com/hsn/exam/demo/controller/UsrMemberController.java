package com.hsn.exam.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsn.exam.demo.service.MemberService;
import com.hsn.exam.demo.util.Ut;
import com.hsn.exam.demo.vo.Member;

@Controller
public class UsrMemberController {

	@Autowired
	private MemberService memberservice;
	
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public Object doAdd(String loginId, String loginPw,String name, String nickname,String cellphoneNum, String email) {
		
		if(Ut.empty(loginId)) {
			return "아이디를 입력해주세요";
		}
		
		if(Ut.empty(loginPw)) {
			return "비번를 입력해주세요";
		}
		
		if(Ut.empty(name)) {
			return "이름를 입력해주세요";
		}
				
		if(Ut.empty(nickname)) {
			return "닉네임를 입력해주세요";
		}
		
		if(Ut.empty(cellphoneNum)) {
			return "연락처를 입력해주세요";
		}
		
		if(Ut.empty(email)) {
			return "이메일를 입력해주세요";
		}
		
		int id = memberservice.doJoin(loginId,loginPw,name,nickname,cellphoneNum,email);
		
		
		if(id==-1) {
			return "이미사용중인 아이디입니다.";
		}
		
		if(id==-2) {
			return "이미사용중인 이름/메일입니다.";
		}
		
		Member member = memberservice.getMemberById(id);

		return member;
	}
	
	@RequestMapping("/usr/member/getMembers")
	@ResponseBody
	public List<Member> getMembers() {
		
		return memberservice.getMembers();
	}
	


}