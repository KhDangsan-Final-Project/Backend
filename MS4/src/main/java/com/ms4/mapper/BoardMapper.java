package com.ms4.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ms4.dto.BoardCommentDTO;
import com.ms4.dto.BoardDTO;


@Mapper
public interface BoardMapper {

	List<BoardDTO> selectBoardNewList(Map<String, Object> map);

	int selectBoardTotalCount();

	BoardDTO selectBoard(int bno);

	List<BoardCommentDTO> selectBoardCommentList(int bno);

	int deleteBoard(int bno);

	int getBoardNo();

	int insertBoard(BoardDTO dto);
}
