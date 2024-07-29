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
    public boolean boardDelete(int boardNo) throws Exception {
        try {
        	//댓글 좋아요 싫어요 삭제
        	int deletedCommentLikes = boardMapper.deleteCommentLikeByBoardNo(boardNo);
        	int deletedCommentHates = boardMapper.deleteCommentHateByBoardNo(boardNo);
        	
        	//게시글 좋아요 삭제
        	int deletedBoardLikes = boardMapper.deleteBoardLikeByBoardNo(boardNo);
        	
            // 댓글 삭제
            int deletedComments = boardMapper.deleteCommentByBoardNo(boardNo);
            
            // 게시물에 연결된 모든 파일 정보 조회
            List<FileDTO> files = boardMapper.selectFilesByBoardNo(boardNo);
            
            // 파일 정보 삭제
            boardMapper.deleteFilesByBoardNo(boardNo);
            
            // 물리적 파일 삭제
            File root = new File("c:\\fileupload");
            if (files != null) {
                for (FileDTO file : files) {
                    if (file != null) {
                        File f = new File(root, file.getFileName());
                        if (f.exists() && !f.delete()) {
                            throw new Exception("파일 삭제 실패: " + file.getFileName());
                        }
                    }
                }
            }
            // 게시물 삭제
            int deletedBoard = boardMapper.deleteBoard(boardNo);
            if (deletedBoard == 0) {
                throw new RuntimeException("게시물 삭제 실패: 게시물이 존재하지 않습니다.");
            }

            return true;
        } catch (Exception e) {
            // 예외 발생 시 트랜잭션 롤백
            e.printStackTrace();
            throw e;  // 예외를 다시 던져 상위 레이어에서 처리할 수 있도록 함
        }
    }

	public int boardUpdate(BoardDTO dto) {
		return boardMapper.boardUpdate(dto);
	}

	public int boardCommentDelete(int cno) {
		return boardMapper.boardCommentDelete(cno);		
	}

	public BoardCommentDTO boardCommentSelect(int cno) {
		return boardMapper.boardCommentSelect(cno);
	}

	public int deleteCommentLikes(int cno) {
		return boardMapper.deleteCommentLikes(cno);
	}

	public int deleteCommentHates(int cno) {
		return boardMapper.deleteCommentHates(cno);
	}
	


}