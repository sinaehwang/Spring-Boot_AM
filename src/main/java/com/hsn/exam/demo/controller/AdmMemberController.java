package com.hsn.exam.demo.controller;

import java.util.List;

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
import com.hsn.exam.demo.vo.Article;
import com.hsn.exam.demo.vo.Member;
import com.hsn.exam.demo.vo.ResultData;
import com.hsn.exam.demo.vo.Rq;

@Controller
public class AdmMemberController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private Rq rq;
	
	public AdmMemberController(MemberService memberService, Rq rq) {
		this.memberService = memberService;
		this.rq = rq;
	}
	
	
	@RequestMapping("/adm/member/list")
	public String list(Model model, @RequestParam(defaultValue = "0") int authLevel,
			@RequestParam(defaultValue = "loginId,name,nickname") String searchKeywordTypeCode,
			@RequestParam(defaultValue = "") String searchKeyword, @RequestParam(defaultValue = "1") int page) {
		
		int memberCount = memberService.getMemberCount(authLevel, searchKeywordTypeCode, searchKeyword);
		
		int itemsInAPage = 10;

		int pagesCount = (int) Math.ceil((double) memberCount / itemsInAPage);
		
		List<Member> members = memberService.getForPrintMembers(authLevel, searchKeywordTypeCode, searchKeyword,page);
		
		
		model.addAttribute(members);
		
		model.addAttribute(memberCount);
		
		return "adm/member/list";
		
		
	}
	
	
	
	

}