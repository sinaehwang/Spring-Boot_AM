package com.hsn.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hsn.exam.demo.vo.Article;

@Mapper
public interface ArticleRepository {

	public void writeArticle(String title, String body,int loginedMemberId);

	public Article getForPrintArticle(int id);

	public List<Article> getArticles();

	public void deleteArticle(int id);

	public void modifyArticle(int id, String title, String body);

	public int getLastInsertId();
	
}