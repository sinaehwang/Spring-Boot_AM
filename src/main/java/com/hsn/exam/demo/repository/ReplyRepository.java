package com.hsn.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.hsn.exam.demo.vo.Reply;

@Mapper
public interface ReplyRepository {

	@Insert("""
			<script>
				INSERT INTO reply
				SET regDate = NOW(),
				updateDate = NOW(),
				memberId = #{actorId},
				relTypeCode = #{relTypeCode},
				relId = #{relId},
				`body` = #{body};
				
			</script>
			""")
	public void doWriteReply(int actorId, String relTypeCode, int relId, String body);

	
	@Select("""
			<script>
				SELECT LAST_INSERT_ID()
			</script>
			""")
	public int getLastInsertId();


	@Delete("""
			<script>
				DELETE FROM reply
				WHERE reply.id = #{replyId}
			</script>
			""")
	public void doDelteReply(int replyId);


	@Select("""
			<script>
				SELECT R.*, M.nickname AS extra__writerName
				FROM reply AS R
				LEFT JOIN `member` AS M
				ON R.memberId = M.id
				WHERE R.relTypeCode = #{relTypeCode}
				AND R.relId = #{relId}
				ORDER BY R.id 
			
			</script>
			""")
	public List<Reply> getForPrintReplies(int actorId, String relTypeCode, int relId);


	@Select("""
			<script>
				SELECT R.*, M.nickname AS extra__writerName
				FROM reply AS R
				LEFT JOIN `member` AS M
				ON R.memberId = M.id
				WHERE R.relTypeCode = 'article'
				AND R.id = #{id}
			</script>
			""")
	
	public Reply getForPrintReply(int id, int actorId);

}
