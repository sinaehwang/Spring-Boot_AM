package com.hsn.exam.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsn.exam.demo.service.ArticleService;
import com.hsn.exam.demo.util.Ut;
import com.hsn.exam.demo.vo.Article;
import com.hsn.exam.demo.vo.ResultData;
import com.hsn.exam.demo.vo.Rq;

@Controller
public class UsrArticleController {

	// 인스턴스 변수
	@Autowired
	private ArticleService articleService;

	// 액션메서드
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public String doAdd(HttpServletRequest req, String title, String body,String replaceUri) {
		
		Rq rq= (Rq) req.getAttribute("rq");

		if (Ut.empty(title)) {
			//return ResultData.from("F-1", "제목을 입력해주세요");
			return rq.jsHistoryBack(Ut.f("제목을 입력해주세요"));
		}
		if (Ut.empty(body)) {
			//return ResultData.from("F-2", "내용을 입력해주세요");
			return rq.jsHistoryBack(Ut.f("내용을 입력해주세요"));
		}

		ResultData<Integer> writeArticleRd = articleService.writeArticle(title, body, rq.getLoginedMemberId());

		int id = (int) writeArticleRd.getData1();

		//Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(),id);
		
		if(Ut.empty(replaceUri)) {
			replaceUri = Ut.f("../article/detail?id=%d", id);
		}

		//return ResultData.newData(writeArticleRd,"Article" ,article);
		return rq.jsReplace(Ut.f("%d번 게시물을 작성했습니다", id), replaceUri); 
	}
	
	@RequestMapping("/usr/article/write")
	public String write(HttpServletRequest req, Model model) {


		return "usr/article/write";

	}
	

	@RequestMapping("/usr/article/list")
	public String showList(HttpServletRequest req, Model model) {
		
		Rq rq= (Rq) req.getAttribute("rq");
		
		List<Article> articles = articleService.getForPrintArticles(rq.getLoginedMemberId());
		
		model.addAttribute("articles", articles);

		return "usr/article/list";
	}
	
	@RequestMapping("/usr/article/detail")
	public String showDetail(HttpServletRequest req, Model model, int id) {
		
		Rq rq= (Rq) req.getAttribute("rq");

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		model.addAttribute("article", article);

		return "usr/article/detail";
		
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, int id) {

		Rq rq= (Rq) req.getAttribute("rq");


		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		if (article == null) {
			return rq.jsHistoryBack(Ut.f("%d번 게시물은 존재하지 않습니다", id));
		}

		if (article.getMemberId() != rq.getLoginedMemberId()) {

			return rq.jsHistoryBack(Ut.f("%d번 게시물에 대한 권한이 없습니다.", id));
		}

		articleService.deleteArticle(id);
		
		return rq.jsReplace(Ut.f("%d번 게시물을 삭제했습니다", id), "../article/list");

		//return ResultData.from("S-1", Ut.f("%d번 게시물을 삭제했습니다", id), id,"id");
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(HttpServletRequest req, int id, String title, String body) {

		Rq rq= (Rq) req.getAttribute("rq");


		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(),id);

		if (article == null) {
			return rq.jsHistoryBack(Ut.f("%d번 게시물은 존재하지 않습니다", id));
		}

		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);// 권한체크를 실현-> 권한이 없거나
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

		Rq rq= (Rq) req.getAttribute("rq");

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(),id);

		if (article == null) {
			//return ResultData.from("F-1", Ut.f("%d번 게시물은 존재하지 않습니다", id), id,"id");
			return rq.jsHistoryBackOnView(Ut.f("%d번 게시물은 존재하지 않습니다", id));
		}

		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);// 권한체크를 실현-> 권한이 없거나
																								// 성공코드가 리턴됨 성공코드가 리턴이
																								// 된후에 실제 doModify가 실행됨

		if (actorCanModifyRd.isFail()) {// 권한실패라면 Fail이 실행되기 때문에 그대로 리턴해준다.
			return rq.jsHistoryBackOnView(actorCanModifyRd.getMsg());
		}

		model.addAttribute("article", article);
		
		//return articleService.modifyArticle(id, title, body);
		
		return "usr/article/modify";

//			return ResultData.from("S-1", Ut.f("%d번 게시물을 수정했습니다", id), id);
	}



}