package com.hsn.exam.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsn.exam.demo.service.MemberService;
import com.hsn.exam.demo.vo.Article;
import com.hsn.exam.demo.vo.Member;

@Controller
public class UsrMemberController {

	@Autowired
	private MemberService memberservice;
	
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public Object doAdd(String loginId, String loginPw,String name, String nickname,String cellphoneNum, String email) {
		
		if(loginId == null || loginId.trim().length()==0) {
			return "아이디를 입력해주세요";
		}
		
		if(loginPw == null || loginPw.trim().length()==0) {
			return "비번를 입력해주세요";
		}
		
		if(name == null || name.trim().length()==0) {
			return "이름를 입력해주세요";
		}
				
		if(nickname == null || nickname.trim().length()==0) {
			return "닉네임를 입력해주세요";
		}
		
		if(cellphoneNum == null || cellphoneNum.trim().length()==0) {
			return "연락처를 입력해주세요";
		}
		
		if(email == null || email.trim().length()==0) {
			return "이메일를 입력해주세요";
		}
		
		int id = memberservice.doJoin(loginId,loginPw,name,nickname,cellphoneNum,email);
		
		if(id==-1) {
			return "이미사용중인 아이디입니다.";
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