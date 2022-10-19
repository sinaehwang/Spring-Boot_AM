package com.hsn.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hsn.exam.demo.vo.Article;

@Mapper
public interface ArticleRepository {

	public void writeArticle(int boardId,String title, String body, int loginedMemberId);

	public Article getForPrintArticle(int id);

	public List<Article> getArticles(int boardId, int limitStart, int limitTake);

	public void deleteArticle(int id);

	public void modifyArticle(int id, String title, String body);

	public int getLastInsertId();

	public int getTotalCount(int boardId, String searchkeyword,String TypeCode);
	
}