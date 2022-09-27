package com.hsn.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hsn.exam.demo.vo.Article;

@Mapper
public interface ArticleRepository {

	@Select("SELECT * FROM article WHERE id = #{id}") 
	public Article getArticle(int id);
	//SELECT * FROM article WHERE id=?
	
	
	public Article writeArticle(String title, String body);
	//INSERT INTO article SET regDate=NOW(),updateDate = NOW(), title=?,`body`=?;
	
	@Delete("DELETE FROM article WHERE id=#{id}")
	public void deleteArticle(int id);
	//DELETE FROM article WHERE id=?
	
	@Update("UPDATE article SET title=#{title}, `body`=#{body} WHERE id=#{id}")
	public void modifyArticle(int id, String title, String body);
	//UPDATE article SET title=?, `body`=? WHERE id=?
	
	@Select("SELECT * FROM article ORDER BY id DESC")
	public List<Article> articles();
	//SELECT * FROM article ORDER BY id DESC;
}