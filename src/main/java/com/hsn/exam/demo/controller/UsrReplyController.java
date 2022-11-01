package com.hsn.exam.demo.controller;

import javax.naming.spi.DirStateFactory.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsn.exam.demo.service.ReplyService;
import com.hsn.exam.demo.util.Ut;
import com.hsn.exam.demo.vo.Reply;
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
	public String doWriteReply(String relTypeCode, int relId, String body, String replaceUri) {


		ResultData<Integer> replyRd = replyService.doWriteReply(rq.getLoginedMemberId(), relTypeCode, relId, body);// 코드,메세지,데이터,네임
																													// 저장됨

		int id = replyRd.getData1();

		if (Ut.empty(replaceUri)) {

			switch (relTypeCode) {
			case "article": // 글의 댓글일경우가있고 댓글에 댓글일경우가있기때문에 경우의수를 나눠줌
				replaceUri = Ut.f("../article/detail?id=%d", relId);
				break;

			default:
				break;
			}

		}

		return rq.jsReplace(replyRd.getMsg(), replaceUri);

	}
	
	@RequestMapping("usr/reply/doDelete")
	@ResponseBody
	public String doDelteReply(int id, String replaceUri) {
		
		//댓글작성자와 로그인사용자가 일치하는지 체크필요
		//댓글id로 댓글을 먼저가져오고 가져온댓글로  사용자일치여부체크후 성공/실패의결과메세지로 구분
		
		Reply reply = replyService.getForPrintReply(id,rq.getLoginedMemberId());
		
		if(reply==null) {
			
			return rq.jsHistoryBack("일치하는 댓글이 없습니다.");
		}
		
		ResultData actorCanReplyDeleteRd = replyService.actorCanReplyDelete(rq.getLoginedMemberId(), reply);
		
		if(actorCanReplyDeleteRd.isFail()) {
			return rq.jsHistoryBack(actorCanReplyDeleteRd.getMsg());
		}
		
		replyService.doDelteReply(id);
		
		return rq.jsReplace(Ut.f("%d번 댓글을 삭제했습니다.", id) , replaceUri);
		
	}
	


}
