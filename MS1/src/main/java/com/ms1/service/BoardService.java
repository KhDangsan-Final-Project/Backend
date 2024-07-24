package com.ms1.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms1.dto.BoardDTO;
import com.ms1.dto.FileDTO;
import com.ms1.mapper.BoardMapper;

@Service
public class BoardService {
	private BoardMapper boardMapper;

	public BoardService(BoardMapper boardMapper) {
		this.boardMapper = boardMapper;
	}

	public List<BoardDTO> selectBoardList(int pageNo, int pageContentEa) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNo", pageNo);
		map.put("pageContentCount", pageContentEa);
		return boardMapper.selectBoardList(map);
	}
	public List<BoardDTO> selectBoardListByCategory(int pageNo, int pageContentEa, String category) {
		Map<String, Object> map = new HashMap<>();
		map.put("pageNo", pageNo);
		map.put("pageContentCount", pageContentEa);
		map.put("category", category);
		return boardMapper.selectBoardListByCategory(map);
	}

	public BoardDTO boardSelect(int boardNo) {
		return boardMapper.boardSelect(boardNo);
	}

	public int boardInsert(BoardDTO dto) {
		return boardMapper.boardInsert(dto);
	}

	public int boardNoSelect() {
		return boardMapper.boardNoSelect();
	}

	@Transactional
    public void insertBoardFile(FileDTO fileDTO) {
        boardMapper.insertBoardFile(fileDTO);
    }

	public int getNextFileNo() {
        return boardMapper.getNextFileNo();
    }

	public int selectBoardTotalCount() {
		return boardMapper.selectBoardTotalCount();
	}

	public int selectBoardTotalCountByCategory(String category) {
		return boardMapper.selectBoardTotalCountByCategory(category);
	}


	
	
}