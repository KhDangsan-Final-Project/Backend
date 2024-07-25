package com.ms1.contoller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.util.Value;
import com.ms1.dto.BoardCommentDTO;
import com.ms1.dto.BoardDTO;
import com.ms1.dto.FileDTO;
import com.ms1.service.BoardService;
import com.ms1.util.JwtUtil;
import com.ms1.vo.PaggingVO;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/ms1")
public class BoardController {

	private BoardService boardService;
	private JwtUtil jwtUtil;

	public BoardController(BoardService boardService, JwtUtil jwtUtil) {
		this.boardService = boardService;
		this.jwtUtil = jwtUtil;
	}

	@Value("${file.upload-dir}")
	private String uploadDir;

	@GetMapping("/board/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(defaultValue = "1") int pageNo,
			@RequestParam(defaultValue = "15") int pageContentEa, @RequestParam(required = false) String category) {
		List<BoardDTO> boardList;
		int totalCount;
		if (category != null && !category.isEmpty()) {
			boardList = boardService.selectBoardListByCategory(pageNo, pageContentEa, category);
			totalCount = boardService.selectBoardTotalCountByCategory(category);
		} else {
			boardList = boardService.selectBoardList(pageNo, pageContentEa);
			totalCount = boardService.selectBoardTotalCount();
		}
		PaggingVO vo = new PaggingVO(totalCount, pageNo, pageContentEa);
		System.out.println(vo.toString());
		Map<String, Object> response = new HashMap<>();
		response.put("boards", boardList);
		response.put("pagging", vo);

		return response;
	}

	@GetMapping("/board/{boardNo}")
	public BoardDTO BoardSelect(@PathVariable int boardNo) {
		BoardDTO dto = boardService.boardSelect(boardNo);
		return dto;
	}

	@GetMapping("/comments/{boardNo}")
	public ResponseEntity<List<BoardCommentDTO>> getComments(@PathVariable("boardNo") int boardNo) {
	    List<BoardCommentDTO> comments = boardService.boardSelectComment(boardNo);
	    System.out.println(comments);
	    return ResponseEntity.ok(comments);
	}


	
	@PostMapping("/boardLike/{boardNo}")
	public Map<String, Object> BoardLike(@PathVariable int boardNo,
			@RequestHeader("Authorization") String authorization) {
		Map<String, Object> response = new HashMap<>();
		String id;
		try {
			// JWT 토큰 검증
			if (authorization == null || !authorization.startsWith("Bearer ")) {
				throw new Exception("계정을 확인해주세요!");
			}

			// 토큰에서 사용자 ID 추출
			String token = authorization.substring(7);
			id = jwtUtil.extractId(token);
			System.out.println(id);

			boolean isLiked = boardService.isLiked(boardNo, id);

			if (isLiked) {
				boardService.deleteBoardLike(boardNo, id);
				response.put("msg", "해당 게시글에 좋아요를 취소하였습니다.");
				System.out.println("취소" + boardNo);
				System.out.println("취소" + id);
			} else {
				boardService.insertBoardLike(boardNo, id);
				response.put("msg", "해당 게시글에 좋아요를 하였습니다.");
				System.out.println(boardNo);
				System.out.println(id);
			}
			response.put("code", 1);
		} catch (Exception e) {
			response.put("code", 2);
			e.printStackTrace();
			response.put("msg", "로그인하셔야 이용하실 수 있습니다.");
		}
		int count = boardService.selectBoardLikeCount(boardNo);
		response.put("count", count);
		System.out.println(count);

		return response;
	}

	@GetMapping("/boardLikeView/{boardNo}")
	public Map<String, Object> getBoardLikeStatus(@PathVariable int boardNo,
			@RequestHeader("Authorization") String authorization) {
		Map<String, Object> response = new HashMap<>();
		try {
			if (authorization == null || !authorization.startsWith("Bearer ")) {
				throw new Exception("계정을 확인해주세요!");
			}
			String token = authorization.substring(7);
			String id = jwtUtil.extractId(token);

			boolean isLiked = boardService.isLiked(boardNo, id);
			int count = boardService.selectBoardLikeCount(boardNo);

			response.put("liked", isLiked);
			response.put("count", count);
			response.put("code", 1);
		} catch (Exception e) {
			response.put("code", 2);
			e.printStackTrace();
			response.put("msg", "로그인하셔야 이용하실 수 있습니다.");
		}

		return response;
	}

	@PostMapping("/comment/insert/{boardNo}")
	public ResponseEntity<String> CommentInsert(@PathVariable int boardNo,
			@RequestParam("comment") String comment, @RequestHeader("Authorization") String authorization) {
		String id;
		try {
			// JWT 토큰 검증
			if (authorization == null || !authorization.startsWith("Bearer ")) {
				throw new IllegalArgumentException("계정을 확인해주세요!");
			}

			// 토큰에서 사용자 ID 추출
			String token = authorization.substring(7);
			id = jwtUtil.extractId(token);
			
			// BoardCommentDTO 객체 생성 및 값 설정
			BoardCommentDTO dto = new BoardCommentDTO();
			dto.setId(id);
			dto.setComment(comment);
			dto.setBno(boardNo);
			dto.setCno(boardService.boardCommentNoSelect());
			// 게시판 글 삽입
			boardService.boardCommentInsert(dto);
			return ResponseEntity.ok("댓글 작성 성공");
		} catch (Exception e) {
			e.printStackTrace(); // 예외 스택 트레이스를 출력하여 디버깅 정보 추가
			return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body("댓글 작성 실패: " + e.getMessage());
		}
	}

	/**
	 * 게시판 작성 메서드
	 * 
	 * @param param         게시판 글의 제목, 내용, 카테고리를 포함한 Map
	 * @param authorization 요청 헤더에 포함된 JWT 토큰
	 * @param file          업로드할 파일 배열
	 * @return 게시판 작성 결과 메시지
	 */

	@PostMapping("/board/insert")
	public ResponseEntity<String> BoardInsert(@RequestParam Map<String, String> param,
			@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "file", required = false) MultipartFile[] file)
			throws IllegalStateException, IOException {

		String id;
		System.out.println(authorization);
		try {
			// JWT 토큰 검증
			if (authorization == null || !authorization.startsWith("Bearer ")) {
				throw new Exception("계정을 확인해주세요!");
			}

			// 토큰에서 사용자 ID 추출
			String token = authorization.substring(7);
			id = jwtUtil.extractId(token);
			System.out.println(id);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body("로그인을 다시 해주세요!");
		}

		// BoardDTO 객체 생성 및 값 설정
		BoardDTO dto = new BoardDTO();
		dto.setId(id);
		dto.setBoardTitle(param.get("title"));
		dto.setBoardContent(param.get("content"));
		dto.setBoardCategory(param.get("category"));

		// 새로운 boardNo 생성 및 설정
		int boardNo = boardService.boardNoSelect();
		dto.setBoardNo(boardNo);

		System.out.println(dto);

		try {
			// 게시판 글 삽입
			boardService.boardInsert(dto);

			// 파일 업로드 처리
			if (file != null && file.length > 0) {
				File root = new File("c:\\fileupload");
				if (!root.exists()) {
					root.mkdirs(); // 디렉토리가 존재하지 않으면 생성
				}

				for (MultipartFile multipartFile : file) {
					if (multipartFile.isEmpty()) {
						continue;
					}

					// 파일 저장
					File f = new File(root, multipartFile.getOriginalFilename());
					multipartFile.transferTo(f);

					// 파일 정보 DTO 생성 및 삽입
					int fileNo = boardService.getNextFileNo(); // 새로운 파일 번호 생성
					FileDTO fileDTO = new FileDTO(f, boardNo, fileNo);
					boardService.insertBoardFile(fileDTO);
				}
			}

			return ResponseEntity.ok("게시판 작성 성공");
		} catch (Exception e) {
			e.printStackTrace(); // 예외 스택 트레이스를 출력하여 디버깅 정보 추가
			return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body("게시판 작성 실패: " + e.getMessage());
		}
	}

}