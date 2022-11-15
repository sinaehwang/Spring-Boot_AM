package com.hsn.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hsn.exam.demo.vo.Member;

@Mapper
public interface MemberRepository {

	//SQL문을 여러줄 써야할때 """를 붙여준다
	@Insert("""
			INSERT INTO `member`
			SET regDate = NOW(),
			updateDate = NOW(),
			loginId = #{loginId},
			loginPw = #{loginPw},
			`name` = #{name},
			nickname = #{nickname},
			cellphoneNum = #{cellphoneNum},
			email = #{email}
				""")
	void join(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email);

	@Select("SELECT LAST_INSERT_ID()")
	public int getLastInsertId();

	@Select("""
			SELECT *
			FROM `member` AS M
			WHERE M.id = #{id}
				""")
	Member getMemberById(int id);

	@Select("""
			SELECT *
			FROM `member` AS M
			WHERE M.loginId = #{loginId}
				""")
	Member getMemberByLoginId(String loginId);

	@Select("""
			SELECT *
			FROM `member` AS M
			WHERE M.name = #{name}
			AND M.email = #{email}
				""")
	Member getMemberByNameAndEmail(String name, String email);

	
	@Update("""
			<script>
			UPDATE `member`
			<set>
				updateDate = NOW(),
				<if test="loginPw != null">
					loginPw = #{loginPw},
				</if>
				<if test="name != null">
					name = #{name},
				</if>
				<if test="nickname != null">
					nickname = #{nickname},
				</if>
				<if test="cellphoneNum != null">
					cellphoneNum = #{cellphoneNum},
				</if>
				<if test="email != null">
					email = #{email}
				</if>
			</set>
			WHERE id = #{actorId};
			</script>
				""")
	void doModify(int actorId, String loginPw, String name, String nickname, String cellphoneNum,String email);

	
	@Select("""
			<script>
			SELECT COUNT(*) AS cnt
			FROM `member` AS M
			WHERE 1
			<if test="authLevel != 0">
				AND M.authLevel = #{authLevel}
			</if>
			<if test="searchKeyword != ''">
				<choose>
					<when test="searchKeywordTypeCode == 'loginId'">
						AND M.loginId LIKE CONCAT('%', #{searchKeyword}, '%')
					</when>
					<when test="searchKeywordTypeCode == 'name'">
						AND M.name LIKE CONCAT('%', #{searchKeyword}, '%')
					</when>
					<when test="searchKeywordTypeCode == 'nickname'">
						AND M.nickname LIKE CONCAT('%', #{searchKeyword}, '%')
					</when>
					<otherwise>
						AND (
							M.loginId LIKE CONCAT('%', #{searchKeyword}, '%')
							OR M.name LIKE CONCAT('%', #{searchKeyword}, '%')
							OR M.nickname LIKE CONCAT('%', #{searchKeyword}, '%')
							)
					</otherwise>
				</choose>
			</if>
			</script>
							""")
	int getMemberCount(int authLevel, String searchKeywordTypeCode, String searchKeyword);

	
	@Select("""
			<script>
			 
						SELECT M.*
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
	
	List<Member> getForPrintMembers(int authLevel, String searchKeywordTypeCode, String searchKeyword, int page);
	
	

}