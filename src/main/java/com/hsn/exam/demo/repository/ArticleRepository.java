package com.hsn.exam.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hsn.exam.demo.vo.Article;

@Service
public class ArticleRepository {
	
	private int lastArticleId;
	private List<Article> articles;

	// 생성자
	public ArticleRepository() {
		lastArticleId = 0;
		articles = new ArrayList<>();

	}

	// 서비스메서드
	public void makeTestData() {
		for (int i = 1; i <= 10; i++) {
			String title = "제목 " + i;
			String body = "내용 " + i;

			writeArticle(title, body);
		}
	}

	public Article getArticle(int id) {
		for (Article article : articles) {
			if (article.getId() == id) {
				return article;
			}
		}

		return null;
	}

	public Article writeArticle(String title, String body) {
		int id = lastArticleId + 1;
		Article article = new Article(id, title, body);

		articles.add(article);
		lastArticleId = id;

		return article;
	}

	public void deleteArticle(int id) {
		Article article = getArticle(id);

		articles.remove(article);
	}

	public void modifyArticle(int id, String title, String body) {
		Article article = getArticle(id);

		article.setTitle(title);
		article.setBody(body);
	}

	public List<Article> articles() {
		return articles;
	}
}