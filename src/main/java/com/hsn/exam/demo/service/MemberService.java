package com.hsn.exam.demo.service;

import org.springframework.stereotype.Service;

import com.hsn.exam.demo.repository.MemberRepository;
import com.hsn.exam.demo.util.Ut;
import com.hsn.exam.demo.vo.Member;
import com.hsn.exam.demo.vo.ResultData;

@Service
public class MemberService {
	
	private MemberRepository memberRepository;
	private AttrService attrService; //AttrService 클래스추가

	public MemberService(AttrService attrService,MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
		this.attrService = attrService;
	}

	public ResultData<Integer> join(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {
		// 로그인아이디 중복체크
		Member existsMember = getMemberByLoginId(loginId);

		if (existsMember != null) {
			return ResultData.from("F-7", Ut.f("이미 사용중인 아이디(%s)입니다", loginId));
		}

		// 이름 + 이메일 중복체크
		existsMember = getMemberByNameAndEmail(name, email);

		if (existsMember != null) {
			return ResultData.from("F-8", Ut.f("이미 사용중인 이름(%s)과 이메일(%s)입니다", name, email));
		}

		memberRepository.join(loginId, loginPw, name, nickname, cellphoneNum, email);
		int id = memberRepository.getLastInsertId();

		return ResultData.from("S-1", "회원가입이 완료되었습니다", id,"id");
	}

	private Member getMemberByNameAndEmail(String name, String email) {
		return memberRepository.getMemberByNameAndEmail(name, email);

	}

	public Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);

	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}

	public ResultData doModify(int actorId,String loginPw, String name, String nickname, String cellphoneNum,String email) {
		
		 memberRepository.doModify(actorId,loginPw,name,nickname,cellphoneNum,email);
		 
		 return ResultData.from("S-1", "회원정보수정완료");
	}

	public String genMemberModifyAuthKey(int actorId) {
		
		String memberModifyAuthKey = Ut.getTempPassword(10); //인증키갯수지정해서 만들기

		attrService.setValue("member", actorId, "extra", "memberModifyAuthKey", memberModifyAuthKey, Ut.getDateStrLater(60 * 5)); //4개의변수,값,유효기간

		return memberModifyAuthKey;
	}

	public ResultData CheckmemberModifyAuthKey(int actorId, String memberModifyAuthKey) {

		String CheckmemberModifyAuthKey = attrService.getValue("member", actorId, "extra", "memberModifyAuthKey");//해당변수  actorId에 맞는 값을 리턴해준다.
		
		if(memberModifyAuthKey.equals(CheckmemberModifyAuthKey)==false) {
			
			return ResultData.from("F-1", "인증번호코드 불일치 또는 만료된 인증번호코드입니다.");
		}
		
		 return ResultData.from("S-1", "인증번호코드 일치 확인완료");
	}
}