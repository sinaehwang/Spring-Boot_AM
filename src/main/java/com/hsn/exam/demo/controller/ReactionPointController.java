package com.hsn.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsn.exam.demo.service.ReactionPointService;
import com.hsn.exam.demo.vo.Rq;

@Controller
public class ReactionPointController {
	
	@Autowired
	private Rq rq;
	
	@Autowired
	private ReactionPointService reactionPointService;
	
	@RequestMapping("/usr/reactionPoint/increaseGoodRp")
	@ResponseBody
	public int increaseGoodRp(int id) {
    	// article 테이블에서 해당 게시물의 좋아요 1 증가 
		reactionPointService.increaseGoodRp(id);
        // article 테이블에서 해당 게시물의 최신화된 좋아요 수 불러오기
		int goodRp = reactionPointService.getGoodRpCount(id);
		
        // reactionPoint 테이블에 리액션 정보(게시판 id, 게시물 id, 사용자 id)를 기록
		reactionPointService.addIncreasingGoodRpInfo(id, (int) rq.getLoginedMemberId());

		return goodRp;
	}
	
	@RequestMapping("/usr/reactionPoint/decreaseGoodRp")
	@ResponseBody
	public int decreaseGoodRp(int id) {
    	// article 테이블에서 해당 게시물의 좋아요 1 감소
		reactionPointService.decreaseGoodRp(id);
        // article 테이블에서 해당 게시물의 최신화된 좋아요 수 불러오기
		int goodRp = reactionPointService.getGoodRpCount(id);
		
        // reactionPoint 테이블에 리액션 정보(게시판 id, 게시물 id, 사용자 id) 기록을 삭제
		reactionPointService.deleteGoodRpInfo(id, (int) rq.getLoginedMemberId());

		return goodRp;
	}

	@RequestMapping("/usr/reactionPoint/increaseBadRp")
	@ResponseBody
	public int increaseBadRp(int id) {
		reactionPointService.increaseBadRp(id);
		int badRp = reactionPointService.getBadRpCount(id);

		reactionPointService.addIncreasingBadRpInfo(id, (int) rq.getLoginedMemberId());

		return badRp;
	}
	
	@RequestMapping("/usr/reactionPoint/decreaseBadRp")
	@ResponseBody
	public int decreaseBadRp(int id) {
		reactionPointService.decreaseBadRp(id);
		int badRp = reactionPointService.getBadRpCount(id);

		reactionPointService.deleteBadRpInfo(id, (int) rq.getLoginedMemberId());

		return badRp;
	}



}
