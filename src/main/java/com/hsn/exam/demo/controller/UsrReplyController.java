package com.hsn.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsn.exam.demo.service.ReplyService;
import com.hsn.exam.demo.util.Ut;
import com.hsn.exam.demo.vo.ResultData;
import com.hsn.exam.demo.vo.Rq;

@Controller
public class UsrReplyController {

	@Autowired
	private ReplyService replyService;

	@Autowired
	private Rq rq;
	

	@RequestMapping("/usr/reply/doWriteReply") // 해당value url로 접속이 들어오면 매핑해줌(기본RequsetMethod.방식은 get방식)
	@ResponseBody // 요청들어온거에 대해 응답결과를 Body에 넣어서 보내줌(RequestBoby는 요청하는 요청본문을 말함)
	public String doWriteReply(String relTypeCode, int relId, String body,String replaceUri) {

		ResultData<Integer> replyRd = replyService.doWriteReply(rq.getLoginedMemberId(), relTypeCode, relId,body);//코드,메세지,데이터,네임 저장됨

		int id = replyRd.getData1();
		
		if (Ut.empty(replaceUri)) {
			
			switch (relTypeCode) {
			case "article":
				replaceUri = Ut.f("../article/detail?id=%d", id);
				break;

			default:
				break;
			}
			
		}
		
		return rq.jsReplace(replyRd.getMsg(), replaceUri);

	}



}
