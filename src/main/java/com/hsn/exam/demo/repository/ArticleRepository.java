package com.hsn.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hsn.exam.demo.vo.Article;
import com.hsn.exam.demo.vo.Reply;

@Mapper
public interface ArticleRepository {

	public void writeArticle(int boardId, String title, String body, int loginedMemberId);
	
	@Select("""
			<script>
				SELECT A.*,
				M.nickname AS extra__writerName
				FROM article AS A
				LEFT JOIN `member` AS M
				ON A.memberId = M.id
				WHERE 1
				AND A.id = #{id}
			</script>
											""")

	public Article getForPrintArticle(int id);

	@Select("""
			<script>
			
						SELECT A.*, M.nickname AS extra__writerName
						FROM article AS A
						LEFT JOIN `member` AS M
						ON A.memberId= M.id 
						WHERE 1
						<if test="boardId != 0">
							AND A.boardId = #{boardId}
						</if>
						<if test="searchKeyword != ''">
							<choose>
								<when test="searchKeywordTypeCode == 'title'">
									AND A.title LIKE CONCAT('%', #{searchKeyword}, '%')
								</when>
								<when test="searchKeywordTypeCode == 'body'">
									AND A.body LIKE CONCAT('%', #{searchKeyword}, '%')
								</when>
								<otherwise>
									AND (
										A.title LIKE CONCAT('%', #{searchKeyword}, '%')
										OR A.body LIKE CONCAT('%', #{searchKeyword}, '%')
										)
								</otherwise>
							</choose>
						</if>
						ORDER BY A.id DESC
						<if test="limitTake != -1">
							LIMIT #{limitStart}, #{limitTake}
						</if>
					</script>
							""")

	public List<Article> getArticles(int boardId, String searchKeywordTypeCode, String searchKeyword, int limitStart,
			int limitTake);

	public void deleteArticle(int id);

	public void modifyArticle(int id, String title, String body);

	public int getLastInsertId();

	@Select("""
			<script>
			SELECT COUNT(*) AS cnt
			FROM article AS A
			WHERE 1
			<if test="boardId != 0">
				AND A.boardId = #{boardId}
			</if>
			<if test="searchKeyword != ''">
				<choose>
					<when test="searchKeywordTypeCode == 'title'">
						AND A.title LIKE CONCAT('%', #{searchKeyword}, '%')
					</when>
					<when test="searchKeywordTypeCode == 'body'">
						AND A.body LIKE CONCAT('%', #{searchKeyword}, '%')
					</when>
					<otherwise>
						AND (
							A.title LIKE CONCAT('%', #{searchKeyword}, '%')
							OR A.body LIKE CONCAT('%', #{searchKeyword}, '%')
							)
					</otherwise>
				</choose>
			</if>
			</script>
							""")
	public int getArticlesCount(int boardId, String searchKeywordTypeCode, String searchKeyword);

	@Update("""
			<script>
			UPDATE article
			SET hitCount = hitCount + 1
			WHERE id = #{id}
			</script>
			""")

	public int increaseHitCount(int id);

	@Select("""
			<script>
			SELECT hitCount
			FROM article
			WHERE id = #{id}
			</script>
			""")
	public int getArticleHitCount(int id);

	
	@Update("""
			<script>
			UPDATE article
			SET goodReactionPoint = goodReactionPoint+1
			WHERE id = #{relId}
			</script>
			""")
	public void increaseGoodReaction(int actorId, int relId);


	@Update("""
			<script>
			UPDATE article
			SET goodReactionPoint = goodReactionPoint-1
			WHERE id = #{relId}
			</script>
			""")
	
	public void decreaseGoodReaction(int actorId, int relId);
	
	@Update("""
			<script>
			UPDATE article
			SET badReactionPoint = badReactionPoint+1
			WHERE id = #{relId}
			</script>
			""")
	public void increaseBadReaction(int actorId, int relId);

	
	@Update("""
			<script>
			UPDATE article
			SET badReactionPoint = badReactionPoint-1
			WHERE id = #{relId}
			</script>
			""")
	public void decreaseBadReaction(int actorId, int relId);

	
	@Select("""
			<script>
				SELECT reply.*,
				`member`.name AS extra__writerName
				FROM reply
				INNER JOIN `member`
				ON reply.memberId = `member`.id
				WHERE reply.relId = #{relId}
				ORDER BY reply.id DESC
			</script>
			""")
	public List<Reply> getForPrintArticleReplyes(int relId);

	
	

	


}