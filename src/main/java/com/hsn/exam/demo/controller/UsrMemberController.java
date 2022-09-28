package com.hsn.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsn.exam.demo.service.MemberService;

@Controller
public class UsrMemberController {

	@Autowired
	private MemberService memberservice;
	
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public String doAdd(String loginId, String loginPw,String name, String nickname,String cellphoneNum, String email) {
		
		memberservice.doJoin(loginId,loginPw,name,nickname,cellphoneNum,email);

		 
		return "hi";
	}


	}