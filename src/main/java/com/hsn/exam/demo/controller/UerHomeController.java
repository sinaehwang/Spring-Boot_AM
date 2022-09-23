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
public class UerHomeController {

	@RequestMapping("usr/home/getString")
	@ResponseBody
	public String getString() {
		return "hi";
	}
	
	@RequestMapping("usr/home/getInt")
	@ResponseBody
	public int getIn() {
		return 1;
	}
	
	@RequestMapping("usr/home/getFloat")
	@ResponseBody
	public float getFloat() {
		return 1.1f;
	}
	
	@RequestMapping("usr/home/getDouble")
	@ResponseBody
	public double getDouble() {
		return 1.1;
	}
	

	@RequestMapping("usr/home/getBoolean")
	@ResponseBody
	public boolean getBoolean() {
		return true;
	}
	
	@RequestMapping("usr/home/getChar")
	@ResponseBody
	public char getChar() {
		return 'a';
	}
	
	@RequestMapping("usr/home/getMap")
	@ResponseBody
	public Map getMap() {
		
		Map<String, Object> map = new HashMap<>();
		map.put("나이", 20);
		map.put("지역", "대전");
		return map;//리턴시 웹브라우저에 보여지도록 정제해줌, 문장형식으로 나타나게됨 중괄호로 나타남
	}
	
	@RequestMapping("usr/home/getList")
	@ResponseBody
	public List<String> getList() {
		
		List<String> list = new ArrayList();
		list.add("나이");
		list.add("지역");
		return list;//리턴시 웹브라우저에 보여지도록 정제해줌, 문장형식으로 나타나게됨 대괄호로 나타남(자바스트립트문법으로 전환됨=>문장으로 나타나게되는것)
	}
	
	@RequestMapping("usr/home/getArticle")
	@ResponseBody
	public List<Article> getArticle() {
		
		
		Article article = new Article(1,"제목");
		Article article2 = new Article(2,"제목2");
		
		List<Article>articles  = new ArrayList<>();
		articles.add(article);
		articles.add(article2);
		
		return articles;
	}
	
	

	
}

@Data //getter를 일괄적으로 사용것과 동일
@AllArgsConstructor// 생성자를 대신해서사용
@NoArgsConstructor //생성자를 args없이도 만들수 있게해줌

class Article{
	@Getter //private을 보여주기 위해 사용됨
	 private int id;
	@Getter
	private String title;
	 
	 
}
