package com.hsn.exam.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.hsn.exam.demo.vo.Board;

@Mapper
public interface BoardRepository {
	
	@Select("""
			SELECT *FROM board AS b
			WHERE b.id = #{id}
			AND board.delStatus = 0
				""")

	public Board getForBoard(int boardId);
}
