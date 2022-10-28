package com.hsn.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsn.exam.demo.service.ReactionPointService;
import com.hsn.exam.demo.vo.Rq;

@Controller
public class UsrReactionPointController {
	
	@Autowired
	private ReactionPointService reactionPointService;
	
	@Autowired
	private Rq rq ;
	
	@RequestMapping("/usr/reactionPoint/doGoodReaction") //해당value url로 접속이 들어오면 매핑해줌(기본RequsetMethod.방식은 get방식)
	@ResponseBody //요청들어온거에 대해 응답결과를 Body에 넣어서 보내줌(RequestBoby는 요청하는 요청본문을 말함)
	public String doGoodReaction(String relTypeCode, int relId,String replaceUri) {//리액션포인트 테이블의 좋아요가 증가하는 결과를 보내줘야함
																//replaceUri는 detail에서 좋아요후에 페이지를 Body에 응답결과로 보여주기 위해 파라미터를 받아옴
		
		
		int isAlreadyPoint = reactionPointService.isAlreadyPoint(rq.getLoginedMemberId(),relTypeCode,relId);
		
		if(isAlreadyPoint > 0) { //0보다 크면 이미 눌렸음
			
			reactionPointService.decreaseGoodReaction(rq.getLoginedMemberId(),relTypeCode,relId);
			
			return rq.jsReplace("좋아요!취소실행",replaceUri);
			
		}
		
		reactionPointService.doGoodReaction(rq.getLoginedMemberId(),relTypeCode,relId);
		
		return rq.jsReplace("좋아요!실행",replaceUri);
		
	}
	
	@RequestMapping("/usr/reactionPoint/doBadReaction") 
	@ResponseBody 
	public String doBadReaction(String relTypeCode, int relId,String replaceUri) {

		reactionPointService.doBadReaction(rq.getLoginedMemberId(),relTypeCode,relId);
		
		return rq.jsReplace("싫어요!실행",replaceUri);
		
	}

}
