package com.ms1.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms1.dto.BoardCommentDTO;
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

	public boolean isLiked(int bno, String id) {
	    Map<String, Object> map = new HashMap<>();
	    map.put("bno", bno);
	    map.put("id", id);
	    return boardMapper.selectBoardLike(map) > 0; // selectBoardLike 메소드가 좋아요 존재 여부를 반환해야 함
	}
	
	public int selectBoardLike(Map<String, Object> map) {
	    return boardMapper.selectBoardLike(map);
	}

	public int deleteBoardLike(int bno, String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bno", bno);
		map.put("id", id);
		return boardMapper.deleteBoardLike(map);
	}

	public int insertBoardLike(int bno, String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bno", bno);
		map.put("id", id);
		return boardMapper.insertBoardLike(map);
	}

	public int selectBoardLikeCount(int bno) {
		return boardMapper.selectBoardLikeCount(bno);
	}

	public int boardCommentInsert(BoardCommentDTO dto) {
		return boardMapper.boardCommentInsert(dto);
	}

	public int boardCommentNoSelect() {
		return boardMapper.boardCommentNoSelect();
	}

	public List<BoardCommentDTO> boardSelectComment(int boardNo) {
		return boardMapper.boardSelectComment(boardNo);
	}
	
	public boolean isCommentLiked(int cno,int boardNo, String id) {
	    Map<String, Object> map = new HashMap<>();
	    map.put("cno", cno);
	    map.put("boardNo", boardNo);
	    map.put("id", id);
	    return boardMapper.selectCommentLike(map) > 0; //
	}
	
	public int selectCommentLike(Map<String, Object> map) {
	    return boardMapper.selectCommentLike(map);
	}
	
	public int deleteCommentLike(int cno,int boardNo, String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cno", cno);
		map.put("boardNo", boardNo);
		map.put("id", id);
		return boardMapper.deleteCommentLike(map);
	}

	public int insertCommentLike(int cno,int boardNo, String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cno", cno);
		map.put("boardNo", boardNo);
		map.put("id", id);
		System.out.println("좋아요:" +map);
		return boardMapper.insertCommentLike(map);
	}

	public int selectCommentLikeCount(int cno) {
		return boardMapper.selectCommentLikeCount(cno);
	}
	
	//-----------------------------------------------------------------------------

	public boolean isCommentHated(int cno,int boardNo, String id) {
	    Map<String, Object> map = new HashMap<>();
	    map.put("cno", cno);
	    map.put("boardNo", boardNo);
	    map.put("id", id);
	    return boardMapper.selectCommentHate(map) > 0; //
	}
	
	public int selectCommentHate(Map<String, Object> map) {
	    return boardMapper.selectCommentHate(map);
	}
	
	public int deleteCommentHate(int cno,int boardNo, String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cno", cno);
		map.put("boardNo", boardNo);
		map.put("id", id);
		return boardMapper.deleteCommentHate(map);
	}

	public int insertCommentHate(int cno,int boardNo, String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cno", cno);
		map.put("boardNo", boardNo);
		map.put("id", id);
		System.out.println("싫어요:" +map);
		return boardMapper.insertCommentHate(map);
	}

	public int selectCommentHateCount(int cno) {
		return boardMapper.selectCommentHateCount(cno);
	}
	
	@Transactional
	public int increaseViewCount(int boardNo) {
		return boardMapper.increaseViewCount(boardNo);
	}

	@Transactional
	public boolean boardDelete(int boardNo) {
	    // 게시물에 연결된 모든 파일 정보 조회
	    List<FileDTO> files = boardMapper.selectFilesByBoardNo(boardNo);
	    // 게시물에 연결된 댓글 정보 조회
	    List<BoardCommentDTO> comments = boardMapper.selectCommentsByBoardNo(boardNo);

	    // 댓글 삭제
	    boolean commentsDeleted = boardMapper.deleteCommentByBoardNo(boardNo) > 0;

	    // 파일 정보 삭제
	    boardMapper.deleteFilesByBoardNo(boardNo);

	    // 파일 삭제 (물리적으로)
	    File root = new File("c:\\fileupload");
	    boolean filesDeleted = true;
	    for (FileDTO file : files) {
	        File f = new File(root, file.getFileName());
	        if (f.exists()) {
	            if (!f.delete()) {
	                filesDeleted = false;
	            }
	        }
	    }
	    // 게시물 삭제
	    boolean boardDeleted = boardMapper.deleteBoard(boardNo) > 0;

	    return commentsDeleted && filesDeleted && boardDeleted;
	}

}