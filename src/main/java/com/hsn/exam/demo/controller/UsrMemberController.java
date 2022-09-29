package com.hsn.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsn.exam.demo.service.MemberService;
import com.hsn.exam.demo.vo.Member;

@Controller
public class UsrMemberController {

	@Autowired
	private MemberService memberservice;
	
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public Object doAdd(String loginId, String loginPw,String name, String nickname,String cellphoneNum, String email) {
		
		int id = memberservice.doJoin(loginId,loginPw,name,nickname,cellphoneNum,email);
		
		if(id==-1) {
			return "이미사용중인 아이디입니다.";
		}
		
		Member member = memberservice.getMemberbyId(id);

		return member;
	}
	


}