package com.hsn.exam.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
	//테이블구조 
	private int id;
	private String title;
	private String body;
	private String regDate;
	private String updateDate;
	private int memberId;
	
	//테이블구조 추가 요소(조인을 통해서 가져온다)
	private String extra_WriterName;
	private boolean extra__actorCanDelete;
	private boolean extra__actorCanModify;
	
}