package com.ms1.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ms1.dto.BoardDTO;
import com.ms1.dto.FileDTO;

@Mapper
public interface BoardMapper {

	List<BoardDTO> selectBoardList(Map<String, Object> map);
	List<BoardDTO> selectBoardListByCategory(Map<String, Object> params);


	int getBoardNo();
	BoardDTO boardSelect(int boardNo);

	int boardInsert(BoardDTO dto);

	int boardNoSelect();

	void insertBoardFile(FileDTO fileDTO);

	int getNextFileNo();
	int selectBoardTotalCount();


}