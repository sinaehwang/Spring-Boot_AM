package com.hsn.exam.demo.repository;

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
	
	

}