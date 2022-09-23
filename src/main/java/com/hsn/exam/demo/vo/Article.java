package com.hsn.exam.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Data //getter를 일괄적으로 사용것과 동일
@AllArgsConstructor// 생성자를 대신해서사용
@NoArgsConstructor //생성자를 args없이도 만들수 있게해줌


	public class Article {

	@Getter //private을 보여주기 위해 사용됨
	 private int id;
	@Getter
	private String title;
	
	private String body;
	 
}
