package com.hsn.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hsn.exam.demo.vo.Article;

@Mapper
public interface ArticleRepository {

	public Article getArticle(int id);
	
	public void writeArticle(String title, String body);
	
	public void deleteArticle(int id);
	
	public void modifyArticle(int id, String title, String body);
	
	public List<Article> articles();
	
	public int getLastArticleId();
	
	
}