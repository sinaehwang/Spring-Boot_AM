package com.hsn.exam.demo.service;

import org.springframework.beans.factory.annotation.Autowired;

public class ReactionPointService {
	
	@Autowired
	private ArticleService articleService;
	
	// detail.jsp의 ajax 관련 메서드(article 테이블에 진입해야 하므로 articleService로 넘김)
		public void increaseGoodRp(int id) {
			articleService.increaseGoodRp(id);
		}

		public void increaseBadRp(int id) {
			articleService.increaseBadRp(id);
		}

		public void decreaseGoodRp(int id) {
			articleService.decreaseGoodRp(id);
		}

		public void decreaseBadRp(int id) {
			articleService.decreaseBadRp(id);
		}

		public int getGoodRpCount(int id) {
			return articleService.getGoodRpCount(id);
		}

		public int getBadRpCount(int id) {
			return articleService.getBadRpCount(id);
		}

		// reactionPoint 테이블에 좋아요/싫어요 로그 기록 관련 메서드
		public void addIncreasingGoodRpInfo(int articleId, int memberId) {
			// 현재 게시물이 소속된 게시판 id를 가져옴
	        int boardId = articleService.getBoardIdByArticle(articleId);
			reactionPointRepository.addIncreasingGoodRpInfo(boardId, articleId, memberId);
		}

		public void deleteGoodRpInfo(int articleId, int memberId) {
			int boardId = articleService.getBoardIdByArticle(articleId);
			reactionPointRepository.deleteGoodRpInfo(boardId, articleId, memberId);
		}

		public void addIncreasingBadRpInfo(int articleId, int memberId) {
			int boardId = articleService.getBoardIdByArticle(articleId);
			reactionPointRepository.addIncreasingBadRpInfo(boardId, articleId, memberId);
		}

		public void deleteBadRpInfo(int articleId, int memberId) {
			int boardId = articleService.getBoardIdByArticle(articleId);
			reactionPointRepository.deleteBadRpInfo(boardId, articleId, memberId);
		}

		public boolean isAlreadyAddGoodRp(int articleId) {
			// 좋아요 = 1, 싫어요 = 2, 취소 시 데이터 삭제
			// 현재 게시물에서, loginedMemberId의 pointTypeCode값이 1이면 좋아요 상태
			int getPointTypeCodeByMemberId = getRpInfoByMemberId(articleId, rq.getLoginedMemberId());

			if (getPointTypeCodeByMemberId == 1) {
				return true;
			}
			return false;
		}

		public boolean isAlreadyAddBadRp(int articleId) {
			// 좋아요 = 1, 싫어요 = 2, 취소 시 데이터 삭제
			// 현재 게시물에서, loginedMemberId의 pointTypeCode값이 2면 좋아요 상태
			int getPointTypeCodeByMemberId = getRpInfoByMemberId(articleId, rq.getLoginedMemberId());

			if (getPointTypeCodeByMemberId == 2) {
				return true;
			}
			return false;
		}
	    
	    private Integer getRpInfoByMemberId(int articleId, int memberId) {
			// 현재 사용자 id와 게시물 id로 좋아요/싫어요 기록을 가져옴
	        Integer getPointTypeCodeByMemberId = reactionPointRepository.getRpInfoByMemberId(articleId, memberId);
			// 로그인 상태가 아닐 경우, null 에러를 방지하기 위해 임의로 99값을 부여
			if (getPointTypeCodeByMemberId == null) {
				getPointTypeCodeByMemberId = 99;
			}

			return (int) getPointTypeCodeByMemberId;
		}
	    

}
