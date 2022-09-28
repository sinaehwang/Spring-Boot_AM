package com.hsn.exam.demo.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

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
	
	 void doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,String email);

}
