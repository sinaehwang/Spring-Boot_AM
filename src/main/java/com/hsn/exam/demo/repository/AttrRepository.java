package com.hsn.exam.demo.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.hsn.exam.demo.vo.Attr;
@Mapper
public interface AttrRepository {

	@Delete("""
			DELETE FROM attr
			WHERE relId = #{relId}
			AND relTypeCode = #{relTypeCode}
			AND typeCode = #{typeCode}
			AND type2Code = #{type2Code}
			""")
	int remove(String relTypeCode, int relId, String typeCode, String type2Code);

	@Insert("""
			INSERT INTO attr (
				regDate,
				updateDate,
				relTypeCode,
				relId,
				typeCode,
				type2Code,
				value,
				expireDate
			)
			VALUES
			(
				NOW(),
				NOW(),
				#{relTypeCode},
				#{relId},
				#{typeCode},
				#{type2Code},
				#{value},
				#{expireDate}
			)
			ON DUPLICATE KEY UPDATE
			updateDate = NOW(),
			`value` = #{value},
			expireDate = #{expireDate}
			""") //유니크 인덱스를 걸어놓은 컬럼들에 중복키(DUPLICATE KEY)가발생되면 값만 변경되도록함
	int setValue(String relTypeCode, int relId, String typeCode, String type2Code, String value, String expireDate); //컬럼의 value값만저장

	@Select("""
			SELECT *
			FROM attr
			WHERE relId = #{relId}
			AND relTypeCode = #{relTypeCode}
			AND typeCode = #{typeCode}
			AND type2Code = #{type2Code}
			AND (expireDate >= NOW() OR expireDate IS NULL)
			""")
	Attr get(String relTypeCode, int relId, String typeCode, String type2Code);//컬럼조회

	@Select("""
			SELECT value
			FROM attr
			WHERE relId = #{relId}
			AND relTypeCode = #{relTypeCode}
			AND typeCode = #{typeCode}
			AND type2Code = #{type2Code}
			AND (expireDate >= NOW() OR expireDate IS NULL)
			""")
	String getValue(String relTypeCode, int relId, String typeCode, String type2Code);//컬럼의 value값만 조회하기

}
