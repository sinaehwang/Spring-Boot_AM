package com.hsn.exam.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsn.exam.demo.service.ArticleService;
import com.hsn.exam.demo.service.BoardService;
import com.hsn.exam.demo.service.ReactionPointService;
import com.hsn.exam.demo.service.ReplyService;
import com.hsn.exam.demo.util.Ut;
import com.hsn.exam.demo.vo.Article;
import com.hsn.exam.demo.vo.Board;
import com.hsn.exam.demo.vo.Reply;
import com.hsn.exam.demo.vo.ResultData;
import com.hsn.exam.demo.vo.Rq;

@Controller
public class UsrArticleController {

// 인스턴스 변수
	@Autowired
	private ArticleService articleService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private Rq rq;
	@Autowired
	private ReactionPointService reactionPointService;
	@Autowired
	private ReplyService replyService;

	// 액션메서드
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public String doAdd(int boardId, String title, String body, String replaceUri) {

		if (Ut.empty(title)) {
			// return ResultData.from("F-1", "제목을 입력해주세요");
			return rq.jsHistoryBack(Ut.f("제목을 입력해주세요"));
		}
		if (Ut.empty(body)) {
			// return ResultData.from("F-2", "내용을 입력해주세요");
			return rq.jsHistoryBack(Ut.f("내용을 입력해주세요"));
		}

		ResultData<Integer> writeArticleRd = articleService.writeArticle(boardId, title, body, rq.getLoginedMemberId());

		int id = (int) writeArticleRd.getData1();

		if (Ut.empty(replaceUri)) {
			replaceUri = Ut.f("../article/detail?id=%d", id);
		}

		return rq.jsReplace(Ut.f("%d번 게시물을 작성했습니다", id), replaceUri);
	}

	@RequestMapping("/usr/article/write")
	public String showWrite(String title, String body) {
		return "usr/article/write";
	}

	@RequestMapping("/usr/article/list")
	public String showList(Model model, @RequestParam(defaultValue = "1") int boardId,
			@RequestParam(defaultValue = "title,body") String searchKeywordTypeCode,
			@RequestParam(defaultValue = "") String searchKeyword, @RequestParam(defaultValue = "1") int page) {// boardId의
																												// 기본값을
																												// 1로 설정

		Board board = boardService.getForBoard(boardId);

		if (board == null) {

			return rq.jsHistoryBackOnView("존재하지 않는 게시판입니다.");
		}

		int articlesCount = articleService.getArticlesCount(boardId, searchKeywordTypeCode, searchKeyword);

		int itemsInAPage = 10;

		int pagesCount = (int) Math.ceil((double) articlesCount / itemsInAPage);

		List<Article> articles = articleService.getForPrintArticles(rq.getLoginedMemberId(), boardId, itemsInAPage,
				page, searchKeywordTypeCode, searchKeyword);

		model.addAttribute("boardId", boardId);
		model.addAttribute("board", board);
		model.addAttribute("page", page);
		model.addAttribute("articlesCount", articlesCount);
		model.addAttribute("pagesCount", pagesCount);
		model.addAttribute("articles", articles);

		return "usr/article/list";
	}

	@RequestMapping("/usr/article/detail")
	public String showDetail(Model model, int id) {

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		model.addAttribute("article", article);
		
		//현재 해당글에 달린 모든 댓글리스트를 가져오는메소드
		List<Reply> replies = replyService.getForPrintReplies(rq.getLoginedMemberId(), "article", id);
		
		int repliesCount = replies.size();

		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(),"article", id);

		model.addAttribute("actorCanMakeReactionRd", actorCanMakeReactionRd);

		model.addAttribute("actorCanMakeReaction", actorCanMakeReactionRd.isSuccess());// 성공의경우의수,처음으로 추천수버튼을 클릭하는경우
		
		model.addAttribute("repliesCount", repliesCount);
		
		model.addAttribute("replies", replies);

		if (actorCanMakeReactionRd.getResultCode().equals("F-2")) {// 기존추천버튼을 취소먼저해야하는경우의수

			int checkButton = (int) actorCanMakeReactionRd.getData1(); //기존선택버튼이 좋아요인지 싫어요인지 구분하기위해서

			if (checkButton > 0) {

				model.addAttribute("actorCanCancelGoodReaction", true);// 좋아요버튼을 먼저취소해야하는경우
			} 
			
			else  {
				
				model.addAttribute("actorCanCancelBadReaction", true);// 싫어요버튼을 먼저취소해야하는경우
			}
		}

		if (actorCanMakeReactionRd.getResultCode().equals("F-1")) {//추천버튼을 누를수없는경우의수(로그인을 하지 않은경우)

			return rq.jsHistoryBackOnView(actorCanMakeReactionRd.getMsg());

		}

		return "usr/article/detail";

	}

	@RequestMapping("/usr/article/doIncreaseHitCountRd")
	@ResponseBody
	public ResultData<Integer> doIncreaseHitCountRd(int id) {
		ResultData<Integer> increaseHitCountRd = articleService.increaseHitCount(id);

		if (increaseHitCountRd.isFail()) {
			return increaseHitCountRd;
		}

		ResultData<Integer> rd = ResultData.newData(increaseHitCountRd, "hitCount",
				articleService.getArticleHitCount(id));

		rd.setData2("id", id);

		return rd;
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, int id) {

		ResultData<Integer> increaseHitCountRd = articleService.increaseHitCount(id);

		if (increaseHitCountRd.isFail()) {
			return rq.jsHistoryBackOnView(increaseHitCountRd.getMsg());
		}

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		if (article == null) {
			return rq.jsHistoryBack(Ut.f("%d번 게시물은 존재하지 않습니다", id));
		}

		if (article.getMemberId() != rq.getLoginedMemberId()) {

			return rq.jsHistoryBack(Ut.f("%d번 게시물에 대한 권한이 없습니다.", id));
		}

		articleService.deleteArticle(id);

		return rq.jsReplace(Ut.f("%d번 게시물을 삭제했습니다", id), "../article/list");

		// return ResultData.from("S-1", Ut.f("%d번 게시물을 삭제했습니다", id), id,"id");
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(HttpServletRequest req, int id, String title, String body) {

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		if (article == null) {
			return rq.jsHistoryBack(Ut.f("%d번 게시물은 존재하지 않습니다", id));
		}

		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);// 권한체크를 실현-> 권한이
																										// 없거나
		// 성공코드가 리턴됨 성공코드가 리턴이
		// 된후에 실제 doModify가 실행됨

		if (actorCanModifyRd.isFail()) {// 권한실패라면 Fail이 실행되기 때문에 그대로 리턴해준다.
			return rq.jsHistoryBack(actorCanModifyRd.getMsg());
		}

		articleService.modifyArticle(id, title, body);

		return rq.jsReplace(Ut.f("%d번 게시물을 수정했습니다", id), Ut.f("../article/detail?id=%d", id));

//			return ResultData.from("S-1", Ut.f("%d번 게시물을 수정했습니다", id), id);
	}

	@RequestMapping("/usr/article/modify")
	public String modify(HttpServletRequest req, int id, String title, String body, Model model) {

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		if (article == null) {
			// return ResultData.from("F-1", Ut.f("%d번 게시물은 존재하지 않습니다", id), id,"id");
			return rq.jsHistoryBackOnView(Ut.f("%d번 게시물은 존재하지 않습니다", id));
		}

		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);// 권한체크를 실현-> 권한이
																										// 없거나
		// 성공코드가 리턴됨 성공코드가 리턴이
		// 된후에 실제 doModify가 실행됨

		if (actorCanModifyRd.isFail()) {// 권한실패라면 Fail이 실행되기 때문에 그대로 리턴해준다.
			return rq.jsHistoryBackOnView(actorCanModifyRd.getMsg());
		}

		model.addAttribute("article", article);

		// return articleService.modifyArticle(id, title, body);

		return "usr/article/modify";

//			return ResultData.from("S-1", Ut.f("%d번 게시물을 수정했습니다", id), id);
	}
	


}