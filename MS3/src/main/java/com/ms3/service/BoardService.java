package com.ms3.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ms3.dto.BoardDTO;
import com.ms3.mapper.BoardMapper;

@Service
public class BoardService {
	private BoardMapper mapper;

	public BoardService(BoardMapper mapper) {
		this.mapper = mapper;
	}

	public List<BoardDTO> selectBoardNewList(int pageNo, int pageContentEa) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNo", pageNo);
		map.put("pageContentCount", pageContentEa);
		return mapper.selectBoardNewList(map);
	}

	public int getBoardNo() {
		return mapper.getBoardNo();
	}

	public int insertBoard(BoardDTO dto) {
		return mapper.insertBoard(dto);
	}
}
