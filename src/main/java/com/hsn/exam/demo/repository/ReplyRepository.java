package com.hsn.exam.demo.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
			SELECT LAST_INSERT_ID()
			""")
	public int getLastInsertId();


	@Delete("""
			DELETE FROM reply
			WHERE reply.id = #{replyid}
			""")
	public void doDelteReply(int replyid);

}
