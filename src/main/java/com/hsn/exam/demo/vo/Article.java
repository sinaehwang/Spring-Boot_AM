package com.hsn.exam.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
	private int id;
	private String title;
	private String body;
	private String regDate;
	private String updateDate;
	private int memberId;
	private String extra_WriterName;
	private boolean extra__actorCanDelete;
	
}