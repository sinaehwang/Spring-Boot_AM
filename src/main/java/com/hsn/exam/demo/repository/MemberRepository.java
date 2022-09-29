package com.hsn.exam.demo.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
			`nickname` = #{nickname},
			cellphoneNum = #{cellphoneNum},
			email = #{email}""")
	
	public void doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,String email);

	@Select("""
			SELECT LAST_INSERT_ID()
			""")
	public int getLastMemberId();
	
	@Select("""
			SELECT * FROM `member` 
			WHERE id = #{id}
			""")
	public Member getMemberById(int id);

	
	@Select("""
			SELECT * FROM `member` 
			WHERE loginId = #{loginId}
			""")
	public Member getMemberByLogId(String loginId);

}
