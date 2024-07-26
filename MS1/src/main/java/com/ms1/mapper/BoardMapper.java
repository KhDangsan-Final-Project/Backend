package com.ms1.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ms1.dto.BoardCommentDTO;
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
	int selectBoardTotalCountByCategory(String category);
	int deleteBoardLike(Map<String, Object> map);
	int insertBoardLike(Map<String, Object> map);
	int selectBoardLikeCount(int bno);
	int selectBoardLike(Map<String, Object> map);
	
	int boardCommentNoSelect();
	int boardCommentInsert(BoardCommentDTO dto);
	List<BoardCommentDTO> boardSelectComment(int boardNo);
	int selectCommentLike(Map<String, Object> map);
	int deleteCommentLike(Map<String, Object> map);
	int insertCommentLike(Map<String, Object> map);
	int selectCommentLikeCount(int cno);
	
	int selectCommentHate(Map<String, Object> map);
	int deleteCommentHate(Map<String, Object> map);
	int insertCommentHate(Map<String, Object> map);
	int selectCommentHateCount(int cno);


}