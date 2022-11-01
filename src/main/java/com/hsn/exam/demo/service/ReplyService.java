package com.hsn.exam.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsn.exam.demo.repository.ReplyRepository;
import com.hsn.exam.demo.util.Ut;
import com.hsn.exam.demo.vo.ResultData;
@Service
public class ReplyService {
	@Autowired
	private ReplyRepository replyRepository;
	
	ReplyService(ReplyRepository replyRepository) { //ReplyRepository에서부터 가져온 변수 replyRepository를 서비스에서 만든 replyRepository변수값에 넣어준다
		this.replyRepository = replyRepository;
	}

	public ResultData<Integer> doWriteReply(int actorId, String relTypeCode, int relId, String body) {

		 replyRepository.doWriteReply(actorId,relTypeCode,relId,body);

		 int id = replyRepository.getLastInsertId();//마지막 댓글의 id번호를 가져온다
		 
		 return ResultData.from("S-1", Ut.f("%d번 댓글이 등록되었습니다.", id), id, "id");//코드,메세지,데이터,네임
		 
	}

	public void doDelteReply(int replyid) {

		replyRepository.doDelteReply(replyid);
		

	}

}
