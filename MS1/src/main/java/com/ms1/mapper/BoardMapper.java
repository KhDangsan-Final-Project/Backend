package com.ms1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ms1.dto.BoardDTO;
import com.ms1.dto.FileDTO;

@Mapper
public interface BoardMapper {

	List<BoardDTO> selectAllList();

	BoardDTO boardSelect(int boardNo);

	int boardInsert(BoardDTO dto);

	int boardNoSelect();

	void insertBoardFile(FileDTO fileDTO);

	int getNextFileNo();


}
