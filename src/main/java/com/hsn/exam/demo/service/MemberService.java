package com.hsn.exam.demo.service;

import org.springframework.stereotype.Service;

import com.hsn.exam.demo.repository.MemberRepository;
import com.hsn.exam.demo.vo.Member;

@Service
public class MemberService {
	
	private MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository=memberRepository;
	}
	

	public int doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,String email) {
		
		int id=-1;
		
		Member foundMember = memberRepository.getMemberByLogId(loginId);
		
		if(foundMember!=null) {
		
			return id;
		}
		
		memberRepository.doJoin(loginId, loginPw, name, nickname, cellphoneNum,email);
		
		id= memberRepository.getLastMemberId();
		 
		 return id;
	}


	public Member getMemberbyId(int id) {
		
		return memberRepository.getMemberById(id);
	}

	
	

}
