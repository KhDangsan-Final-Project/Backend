package com.ms3.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ms3.dto.BoardDTO;

@Mapper
public interface BoardMapper {

	List<BoardDTO> selectBoardNewList(Map<String, Object> map);
	int getBoardNo();
	int insertBoard(BoardDTO dto);



}
