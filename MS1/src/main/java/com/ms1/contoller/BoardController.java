package com.ms1.contoller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ms1.service.BoardService;
import com.ms1.dto.BoardDTO;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("ms1")
public class BoardController {
	
	private final BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@GetMapping("/board/list")
	@ResponseBody
	public List<BoardDTO> list(
							@RequestParam(defaultValue = "1") int pageNo,
							@RequestParam(defaultValue = "15") int pageContentEa) {
		List<BoardDTO> boardList = boardService.selectBoardNewList(pageNo, pageContentEa);

		return boardList;
	}
	
	@PostMapping("/board/write")
	public Map<String, Object> boardUpload(@RequestBody Map<String, String> param){
		BoardDTO dto = new BoardDTO();
		dto.setBoardNo(Integer.parseInt(param.get("boardNo")));
		dto.setCategory(param.get("category"));
		dto.setBoardTitle(param.get("title"));
		dto.setBoardContent(param.get("content"));
		Map<String, Object> map = new HashMap<>();
		try {
			boardService.insertBoard(dto);
			map.put("msg", "게시물 등록 성공");
			map.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "게시물 등록 실패");
			map.put("result", false);
		}
		return map;
	}
}
