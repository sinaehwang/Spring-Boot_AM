package com.hsn.exam.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsn.exam.demo.service.ArticleService;
import com.hsn.exam.demo.util.Ut;
import com.hsn.exam.demo.vo.Article;
import com.hsn.exam.demo.vo.ResultData;

@Controller
public class UsrArticleController {

	// 인스턴스 변수
	@Autowired
	private ArticleService articleService;

	// 액션메서드
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public ResultData<Article> doAdd(HttpSession httpSession, String title, String body) {
		boolean isLogined = false;

		int loginedMemberId = 0;

		if (httpSession.getAttribute("loginedMemberId") != null) {

			isLogined = true;

			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");

		}

		if (isLogined == false) {
			return ResultData.from("F-3", "로그인후 이용해주시기 바랍니다.");
		}

		if (Ut.empty(title)) {
			return ResultData.from("F-1", "제목을 입력해주세요");
		}
		if (Ut.empty(body)) {
			return ResultData.from("F-2", "내용을 입력해주세요");
		}

		ResultData<Integer> writeArticleRd = articleService.writeArticle(title, body, loginedMemberId);

		int id = (int) writeArticleRd.getData1();

		Article article = articleService.getForPrintArticle(id);

		return ResultData.newData(writeArticleRd,"Article" ,article);
	}

	@RequestMapping("/usr/article/list")
	public String showList(Model model) {
		List<Article> articles = articleService.getForPrintArticles();
		
		model.addAttribute("articles", articles);

		return "usr/article/list";
	}
	
	@RequestMapping("/usr/article/detail")
	public String showDetail(Model model,int id) {
		
		Article article = articleService.getForPrintArticle(id);
		
		model.addAttribute("article", article);

		return "usr/article/detail";
		
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public ResultData<Integer> doDelete(HttpSession httpSession, int id) {

		boolean isLogined = false;// 로그인안된상태로 가정

		int loginedMemberId = 0;

		if (httpSession.getAttribute("loginedMemberId") != null) {// 널값이 아니라면 로그인상태기때문에 true로 변경

			isLogined = true;

			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");

		}

		if (isLogined == false) {// true가 아니기때문에 로그인이 안된상태
			return ResultData.from("F-3", "로그인후 이용해주시기 바랍니다.");
		}

		Article article = articleService.getForPrintArticle(id);

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물은 존재하지 않습니다", id), id,"id");
		}

		if (article.getMemberId() != loginedMemberId) {

			return ResultData.from("F-2", Ut.f("%d번 게시물에 대한 권한이 없습니다.", id));
		}

		articleService.deleteArticle(id);

		return ResultData.from("S-1", Ut.f("%d번 게시물을 삭제했습니다", id), id,"id");
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData doModify(HttpSession httpSession, int id, String title, String body) {

		boolean isLogined = false;

		int loginedMemberId = 0;

		if (httpSession.getAttribute("loginedMemberId") != null) {

			isLogined = true;

			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");

		}

		if (isLogined == false) {
			return ResultData.from("F-3", "로그인후 이용해주시기 바랍니다.");
		}

		Article article = articleService.getForPrintArticle(id);

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물은 존재하지 않습니다", id), id,"id");
		}

		ResultData actorCanModifyRd = articleService.actorCanModify(loginedMemberId, article);// 권한체크를 실현-> 권한이 없거나
																								// 성공코드가 리턴됨 성공코드가 리턴이
																								// 된후에 실제 doModify가 실행됨

		if (actorCanModifyRd.isFail()) {// 권한실패라면 Fail이 실행되기 때문에 그대로 리턴해준다.
			return actorCanModifyRd;
		}

		return articleService.modifyArticle(id, title, body);

//			return ResultData.from("S-1", Ut.f("%d번 게시물을 수정했습니다", id), id);
	}



}