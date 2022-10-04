package com.hsn.exam.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
		public ResultData<Article> doAdd(HttpSession httpSession,String title, String body) {
			boolean isLogined = false;
			
			int loginedMemberId  = 0;
			
			if(httpSession.getAttribute("loginedMemberId")!=null) {
				
				isLogined =true;
				
				loginedMemberId = (int)httpSession.getAttribute("loginedMemberId");
				
			}
			
			if(isLogined==false) {
				return ResultData.from("F-3", "로그인후 이용해주시기 바랍니다.");
			}
			
			if (Ut.empty(title)) {
				return ResultData.from("F-1", "제목을 입력해주세요");
			}
			if (Ut.empty(body)) {
				return ResultData.from("F-2", "내용을 입력해주세요");
			}
			

			ResultData<Integer> writeArticleRd = articleService.writeArticle(title, body,loginedMemberId);

			int id = (int) writeArticleRd.getData1();

			Article article = articleService.getArticle(id);

			return ResultData.newData(writeArticleRd, article);
		}

		@RequestMapping("/usr/article/getArticles")
		@ResponseBody
		public ResultData<List<Article>> getArticles() {
			List<Article> articles = articleService.getArticles();

			return ResultData.from("S-1", "Article List", articles);
		}

		@RequestMapping("/usr/article/doDelete")
		@ResponseBody
		public ResultData<Integer> doDelete(HttpSession httpSession,int id) {
			
			boolean isLogined = false;
			
			int loginedMemberId  = 0;
			
			if(httpSession.getAttribute("loginedMemberId")!=null) {
				
				isLogined =true;
				
				loginedMemberId = (int)httpSession.getAttribute("loginedMemberId");
				
			}
			
			if(isLogined==false) {
				return ResultData.from("F-3", "로그인후 이용해주시기 바랍니다.");
			}
			
			
			Article article = articleService.getArticle(id);

			if (article == null) {
				return ResultData.from("F-1", Ut.f("%d번 게시물은 존재하지 않습니다", id), id);
			}
			
			if(article.getMemberId()!=loginedMemberId) {
				
				return ResultData.from("F-2", Ut.f("%d번 게시물에 대한 권한이 없습니다.", id));
			}

			articleService.deleteArticle(id);

			return ResultData.from("S-1", Ut.f("%d번 게시물을 삭제했습니다", id), id);
		}

		@RequestMapping("/usr/article/doModify")
		@ResponseBody
		public ResultData<Integer> doModify(HttpSession httpSession,int id, String title, String body) {
			
			boolean isLogined = false;
			
			int loginedMemberId  = 0;
			
			if(httpSession.getAttribute("loginedMemberId")!=null) {
				
				isLogined =true;
				
				loginedMemberId = (int)httpSession.getAttribute("loginedMemberId");
				
			}
			
			if(isLogined==false) {
				return ResultData.from("F-3", "로그인후 이용해주시기 바랍니다.");
			}
			
			Article article = articleService.getArticle(id);

			if (article == null) {
				return ResultData.from("F-1", Ut.f("%d번 게시물은 존재하지 않습니다", id), id);
			}
			
			if(article.getMemberId()!=loginedMemberId) {
				
				return ResultData.from("F-2", Ut.f("%d번 게시물에 대한 수정권한이 없습니다.", id));
			}

			articleService.modifyArticle(id, title, body);

			return ResultData.from("S-1", Ut.f("%d번 게시물을 수정했습니다", id), id);
		}

		@RequestMapping("/usr/article/getArticle")
		@ResponseBody
		public ResultData<Article> getArticle(int id) {
			Article article = articleService.getArticle(id);

			if (article == null) {
				return ResultData.from("F-1", Ut.f("%d번 게시물은 존재하지 않습니다.", id));
			}

			return ResultData.from("S-1", Ut.f("%d번 게시물입니다.", id), article);
		}

	}