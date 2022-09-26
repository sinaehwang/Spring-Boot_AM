package com.hsn.exam.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsn.exam.demo.service.ArticleService;
import com.hsn.exam.demo.vo.Article;

@Controller
public class UsrArticleController {

	//인스턴스변수
	@Autowired //new ArticleService 객체를 만든거나 마찬가지(서비스와 컨트롤러를 연결해준다)
	
	private ArticleService articleService;
	

	//액션메서드
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {
		
		Article article = articleService.writeArticle(title, body);

				return article;

	}
	
	
	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
		
	      return articles;
	   }
	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		Article article = articleService.getArticle(id);

		if (article == null) {
			return id + "번 게시물은 존재하지 않습니다";
		}

		deleteArticle(id);

		return id + "번 게시물을 삭제했습니다";
	}
	
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public Object doModify(int id,String title, String body) {
		Article article = articleService.getArticle(id);

		if (article == null) {
			return id + "번 게시물은 존재하지 않습니다";
		}

		modifyArticle(id,title,body);

		return article;
	}
	
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public Object getArticle(int id,String title, String body) {
		Article article = getArticle(id);

		if (article == null) {
			return id + "번 게시물은 존재하지 않습니다";
		}

		return article;
	}



	



	
	
}


