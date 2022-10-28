package com.hsn.exam.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsn.exam.demo.repository.ReactionPointRepository;

@Service
public class ReactionPointService {
	
	@Autowired
	private ReactionPointRepository reactionPointRepository;
	
	@Autowired
	private ArticleService articleService;

	public boolean actorCanMakeReaction(int actorId, String relTypeCode,int relId) {
		if(actorId==0) {
			return false;
		}
		return reactionPointRepository.getSumReactionPointByMemberId(actorId,relTypeCode, relId) == 0;
	}

	public void doGoodReaction(int actorId, String relTypeCode, int relId) {

		 reactionPointRepository.doGoodReaction(actorId,relTypeCode,relId);
		 
		 if(relTypeCode.equals("article")) {
			 
			  articleService.increaseGoodReaction(actorId,relId);
			 
		 }
		 
	}

	public void doBadReaction(int actorId, String relTypeCode, int relId) {

		reactionPointRepository.doBadReaction(actorId,relTypeCode,relId);
		
		 if(relTypeCode.equals("article")) {
			 
			  articleService.increaseBadReaction(actorId,relId);
			 
		 }
		 
	}

}
