package com.hsn.exam.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsn.exam.demo.repository.ReplyRepository;
import com.hsn.exam.demo.util.Ut;
import com.hsn.exam.demo.vo.Reply;
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

	public void doDelteReply(int replyId) {

		replyRepository.doDelteReply(replyId);
		

	}

	public List<Reply> getForPrintReplies(int actorId, String relTypeCode, int relId) {
		return replyRepository.getForPrintReplies(actorId,relTypeCode,relId);
	}

	public Reply getForPrintReply(int id, int actorId) {
		
		Reply reply = replyRepository.getForPrintReply(id,actorId);
		
		if(reply==null) {
			return null;
		}
		
		updateReplyData(actorId,reply);
		
		return reply;
	}

	private void updateReplyData(int actorId, Reply reply) {
		
		ResultData actorCanDeleteReplyRd = actorCanDelete(actorId, reply);
		
		reply.setExtra__actorCanDelete(actorCanDeleteReplyRd.isSuccess());
		
		ResultData actorCanUpdateReplyRd = actorCanUpdate(actorId, reply);
		
		reply.setExtra__actorCanModify(actorCanUpdateReplyRd.isSuccess());
		
	}

	private ResultData actorCanUpdate(int actorId, Reply reply) {
		
		if(reply==null) {
			return ResultData.from("F-1", "해당댓글이 존재하지 않습니다.");
		}
		
		if(reply.getMemberId()!=actorId) {
			return ResultData.from("F-2","해당댓글에 대해 삭제권한이 없습니다.");
		}
		
		return ResultData.from("S-1",Ut.f("%d번 댓글 수정완료", reply.getId()));
		
	}

	private ResultData actorCanDelete(int actorId, Reply reply) {
		
		if(reply==null) {
			return ResultData.from("F-1", "해당댓글이 존재하지 않습니다.");
		}
		
		if(reply.getMemberId()!=actorId) {
			return ResultData.from("F-2","해당댓글에 대해 삭제권한이 없습니다.");
		}
		
		return ResultData.from("S-1",Ut.f("%d번 댓글 삭제완료", reply.getId()));
		
	}
	
	public ResultData actorCanReplyUpdate(int actorId, Reply reply) {
		
		if (reply.getMemberId() != actorId) {

			return ResultData.from("F-2", "해당 댓글에 대한 수정권한이 없습니다.");
		}
		return ResultData.from("S-1", "수정가능");
	}
	

	public ResultData actorCanReplyDelete(int actorId, Reply reply) {
		
		if (reply.getMemberId() != actorId) {

			return ResultData.from("F-2", "해당 댓글에 대한 삭제권한이 없습니다.");
		}
		return ResultData.from("S-1", "삭제가능");
	}

}
