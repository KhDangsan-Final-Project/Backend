package com.ms1.service;

import java.util.List;

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

	public List<BoardDTO> selectAllList() {
		return boardMapper.selectAllList();
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

	
	
}
