package com.ms1.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ms1.dto.BoardCommentDTO;
import com.ms1.dto.BoardDTO;
import com.ms1.dto.FileDTO;
import com.ms1.dto.ReportDTO;

@Mapper
public interface BoardMapper {

	List<BoardDTO> selectBoardList(Map<String, Object> map);
	List<BoardDTO> selectBoardListByCategory(Map<String, Object> params);


	int getBoardNo();
	BoardDTO boardSelect(int boardNo);

	int boardInsert(BoardDTO dto);
	void insertBoardFile(FileDTO fileDTO);
	int boardNoSelect();

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
	
	int increaseViewCount(int boardNo);
	int deleteBoard(int boardNo);
	List<BoardCommentDTO> selectCommentsByBoardNo(int boardNo);
	int deleteComment(int boardNo);
	int deleteCommentByBoardNo(int boardNo);
	int boardUpdate(BoardDTO dto);
	int boardCommentDelete(int cno);
	BoardCommentDTO boardCommentSelect(int cno);
	int deleteCommentLikes(int cno);
	int deleteCommentHates(int cno);
	int deleteBoardLikeByBoardNo(int boardNo);
	int deleteCommentLikeByBoardNo(int boardNo);
	int deleteCommentHateByBoardNo(int boardNo);
//	List<FileDTO> boardSelectFile(int boardNo);
//	FileDTO selectFileByNo(int fileNo);
//	int deleteFile(int fno);
	
	void boardReport(ReportDTO dto);
    void boardCommentReport(ReportDTO dto);
    ReportDTO findReportByUserAndBoard(ReportDTO dto);
    ReportDTO findRportByBoardComment(ReportDTO dto);
    
    
	int deleteBoardReport(int boardNo);
	List<Integer> boardUserSelect(String id);
	
	int deleteUserCommentLikeByBoardNo(List<Integer> boardNo);
	int deleteUserCommentHateByBoardNo(List<Integer> boardNo);
	int deleteUserBoardLikeByBoardNo(List<Integer> boardNo);
	int deleteUserBoardReport(List<Integer> boardNo);
	int deleteUserCommentByBoardNo(List<Integer> boardNo);
	int deleteUserBoard(List<Integer> boardNo);
}