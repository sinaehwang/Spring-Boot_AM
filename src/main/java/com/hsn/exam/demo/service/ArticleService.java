package com.hsn.exam.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsn.exam.demo.repository.ArticleRepository;
import com.hsn.exam.demo.vo.Article;

@Service
public class ArticleService {
	@Autowired//객체생성없이도 연결해줌
	private ArticleRepository articleRepository;

	// 생성자
	public ArticleService(ArticleRepository articleRepository) {//생성자 주입
		
		this.articleRepository = articleRepository;

	}

	// 서비스메서드

	public Article getArticle(int id) {
		
		return articleRepository.getArticle(id);
	}
		

	public Article writeArticle(String title, String body) {

		 articleRepository.writeArticle(title,body);
		 
		 int id = articleRepository.getLastArticleId();
	}

	public void deleteArticle(int id) {
		
		articleRepository.deleteArticle(id);
	}

	public void modifyArticle(int id, String title, String body) {
		
		articleRepository.modifyArticle(id, title, body);
		
	}

	public List<Article> articles() {
		
		return articleRepository.articles();
	}
}