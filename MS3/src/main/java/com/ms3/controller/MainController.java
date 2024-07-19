package com.ms3.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ms3.dto.BoardDTO;
import com.ms3.dto.UserDTO;
import com.ms3.service.BoardService;
import com.ms3.service.UserService;
import com.ms3.util.JwtUtil;
import com.ms3.vo.PaggingVO;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/ms3")
public class MainController {

	private final UserService service;
	private final BoardService boardService;
	private final JwtUtil jwtUtil;

	public MainController(UserService service, BoardService boardService, JwtUtil jwtUtil) {
		this.service = service;
		this.boardService = boardService;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/user/select")
	public Map<String, Object> login(@RequestBody Map<String, String> param) {
		String id = param.get("id");
		String password = param.get("password");

		Map<String, Object> map = new HashMap<>();

		UserDTO user = service.selectUser(id, password);
		if (user != null) {
			String token = jwtUtil.generateToken(user.getId(), user.getNickname(), user.getGrantNo(),
					user.getProfile());
			map.put("msg", "로그인 성공");
			map.put("result", true);
			map.put("token", token);
		} else {
			map.put("msg", "로그인 실패");
			map.put("result", false);
		}

		return map;
	}

	@PostMapping("/user/insert")
	public Map<String, Object> insertUser(@RequestBody Map<String, String> param) {
		UserDTO dto = new UserDTO();
		dto.setId(param.get("id"));
		dto.setGrantNo(Integer.parseInt(param.get("grantNo")));
		dto.setPassword(param.get("password"));
		dto.setEmail(param.get("email"));
		dto.setName(param.get("name"));
		dto.setNickname(param.get("nickname"));
		dto.setProfile(param.get("profile"));
		Map<String, Object> map = new HashMap<>();
		try {
			service.insertUser(dto);
			map.put("msg", "회원 등록 성공");
			map.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "회원 등록 실패");
			map.put("result", false);
		}
		return map;
	}

	@PostMapping("/user/idcheck")
	public Map<String, Object> idcheck(@RequestBody Map<String, String> payload) {
		String id = payload.get("id");
		int count = service.idcheck(id);
		Map<String, Object> map = new HashMap<String, Object>();

		count = service.idcheck(id);
		map.put("count", count);

		return map;
	}
	
	@GetMapping("/board/list")
	@ResponseBody
	public List<BoardDTO> list(
							@RequestParam(defaultValue = "1") int pageNo,
							@RequestParam(defaultValue = "15") int pageContentEa) {
		List<BoardDTO> boardList = boardService.selectBoardNewList(pageNo, pageContentEa);
		System.out.println(boardList);
		return boardList;
	}
	
	@PostMapping("/board/write")
	public String boardWrite(BoardDTO dto, HttpSession session){
		UserDTO memberDTO = (UserDTO) session.getAttribute("user");
		dto.setId(memberDTO.getId());
		int bno = boardService.getBoardNo();
		dto.setBoardNo(bno);
		boardService.insertBoard(dto);
		return "redirect:/board/"+bno;
	}

}
