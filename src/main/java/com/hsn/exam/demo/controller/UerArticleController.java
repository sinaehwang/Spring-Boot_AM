package com.hsn.exam.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Controller
public class UerArticleController {

	private int lastArticleId;
	private List<Article> articles;
	
	public UerArticleController() {
		lastArticleId =0;
		articles = new ArrayList<>();
	}
	
	@RequestMapping("usr/article/doAdd")
	@ResponseBody
	public String doAdd(String title,String body) {
		
		int id = lastArticleId+1;
		
		Article article = new Article(id,title,body);
		
		article.add(article);
		lastArticleId = id;
		
		return article;
	}
	
	
}


