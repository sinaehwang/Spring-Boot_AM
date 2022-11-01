package com.hsn.exam.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
	
	private int id;
	private String regDate;
	private String updateDate;
	private int memberId;
	private String relTypeCode;
	private int relId;
	private String body;
	private int goodReactionPoint;
	private int badReactionPoint;
	
	private String extra__writerName;
	private boolean extra__actorCanDelete;
	private boolean extra__actorCanModify;
	
	public String getForPrintType1RegDate() {
		return regDate.substring(2, 16).replace(" ", "<br />");
	}

	public String getForPrintBody() {
		return body.replaceAll("\n", "<br>");
	}

}
