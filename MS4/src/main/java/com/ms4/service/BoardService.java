package com.ms4.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ms4.dto.BoardCommentDTO;
import com.ms4.dto.BoardDTO;
import com.ms4.mapper.BoardMapper;

@Service
public class BoardService {
	private final BoardMapper mapper;

	public BoardService(BoardMapper mapper) {
		this.mapper = mapper;
	}

	public List<BoardDTO> selectBoardNewList(int pageNo, int pageContentEa) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNo", pageNo);
		map.put("pageContentCount", pageContentEa);
		return mapper.selectBoardNewList(map);
	}

	public int selectBoardTotalCount() {
		return mapper.selectBoardTotalCount();
	}

	public BoardDTO selectBoard(int bno) {
		return mapper.selectBoard(bno);
	}

	public List<BoardCommentDTO> selectBoardCommentList(int bno) {
		return mapper.selectBoardCommentList(bno);
	}

	public int deleteBoard(int bno) {
		return mapper.deleteBoard(bno);
	}

	public int getBoardNo() {
		return mapper.getBoardNo();
	}

	public int insertBoard(BoardDTO dto) {
		return mapper.insertBoard(dto);
	}

	public int insertBoardComment(BoardCommentDTO dto) {
		return mapper.insertBoardComment(dto);
		
	}

	public int deleteBoardComment(int cno) {
		return mapper.deleteBoardComment(cno);
	}

	public int updateBoard(BoardDTO dto) {
		return mapper.updateBoard(dto);
	}

	public int adminDeleteBoardComment(int cno) {
		return mapper.adminDeleteBoardComment(cno);
	}

	public void adminDeleteBoard(int bno) {
		mapper.adminDeleteBoard(bno);
	}

	public List<BoardDTO> selectReportBoard(int pageNo, int pageContentEa) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNo", pageNo);
		map.put("pageContentCount", pageContentEa);
		return mapper.selectReportBoard(map);
	}

	public List<BoardCommentDTO> selectReportComment(int commentPageNo, int pageContentEa) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNo", commentPageNo);
		map.put("pageContentCount", pageContentEa);
		return mapper.selectReportComment(map);
	}

	public int selectReportBoardTotalCount() {
		return mapper.selectReportBoardTotalCount();
	}

	public int selectReportBoardCommentTotalCount() {
		return mapper.selectReportBoardCommentTotalCount();
	}
	
}
