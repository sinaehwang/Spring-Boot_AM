package com.hsn.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.hsn.exam.demo.vo.Article;

@Mapper
public interface ArticleRepository {

	@Select("SELECT * FROM article WHERE id = #{id}") 
	public Article getArticle(int id);
	//SELECT * FROM article WHERE id=?
	public Article writeArticle(String title, String body);
	//INSERT INTO article SET regDate=NOW(),updateDate = NOW(), title=?,`body`=?;
	public void deleteArticle(int id);
	//DELETE FROM article WHERE id=?
	public void modifyArticle(int id, String title, String body);
	//UPDATE article SET title=?, `body`=? WHERE id=?
	public List<Article> articles();
	//SELECT * FROM article ORDER BY id DESC;
}