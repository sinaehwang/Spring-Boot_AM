package com.hsn.exam.demo.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface ReactionPointRepository {

	@Select("""
			<script>
				SELECT IFNULL(SUM(RP.point),0) AS s
				FROM reactionPoint AS RP
				WHERE RP.relTypeCode = #{relTypeCode}
				AND RP.relId = #{relId}
				AND RP.memberId = #{actorId}
			</script>
						""")
	public int getSumReactionPointByMemberId(int actorId,String relTypeCode, int relId);

	
	@Insert("""
			<script>
				INSERT INTO reactionPoint
				SET regDate = NOW(),
				updateDate = NOW(),
				memberId = #{actorId},
				relTypeCode = #{relTypeCode},
				relId = #{relId},
				`point` = 1
			</script>
			""")
	public void doGoodReaction(int actorId, String relTypeCode, int relId);//reactionPoint테이블에 goodreaction 내용을 추가해줘야함


	@Insert("""
			<script>
				INSERT INTO reactionPoint
				SET regDate = NOW(),
				updateDate = NOW(),
				memberId = #{actorId},
				relTypeCode = #{relTypeCode},
				relId = #{relId},
				`point` = -1
			</script>
			""")
	public void doBadReaction(int actorId, String relTypeCode, int relId);


	

	@Select("""
			<script>
			
				SELECT COUNT(*) FROM reactionPoint 
				WHERE relId = #{relId} 
				AND memberId = #{actorId}
				
			</script>
						""")
	public int isAlreadyPoint(int actorId, String relTypeCode, int relId);


	
	@Delete("""
			<script>
			
				DELETE FROM reactionPoint
				WHERE relId = #{relId}
				AND memberId = #{actorId}
				
			</script>
						""")
	public void decreaseGoodReaction(int actorId, String relTypeCode, int relId);

}
