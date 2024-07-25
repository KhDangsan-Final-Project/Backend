package com.ms4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ms4.dto.BoardCommentDTO;
import com.ms4.dto.BoardDTO;
import com.ms4.dto.RankDTO;
import com.ms4.dto.UserDTO;
import com.ms4.service.BoardService;
import com.ms4.service.UserService;
import com.ms4.vo.PagingVO;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/ms4")
public class MainController {
	
	private final UserService service;
	private final BoardService boardService;
	
	public MainController(UserService service, BoardService boardService) {
		this.service = service;
		this.boardService = boardService;
	}

	@GetMapping("/user/list")
	public ModelAndView userList(ModelAndView view) {
		List<UserDTO> userList = service.selectAllUser();
		List<RankDTO> rankList = service.selectAllRank();

		view.addObject("userList", userList);
		view.addObject("rankList", rankList);

		view.setViewName("user_list");
		return view;
	}
	
	@PutMapping("/user/admin/update")
    public ResponseEntity<String> updateMember(@RequestBody UserDTO dto)  {
        int count = service.updateUser(dto);
        Map<String, Object> map = new HashMap<>();
        map.put("count", count);
        map.put("msg", count == 0 ? "유저정보 수정 실패" : "유저정보 수정 성공");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @DeleteMapping("/user/admin/delete")
    public ResponseEntity<Map<String, Object>> deleteMember(@RequestBody Map<String, String> request) {
        String id = request.get("id");
        System.out.println("내용 : " + id);
        int count = service.deleteUser(id);
        Map<String, Object> map = new HashMap<>();
        map.put("count", count);
        map.put("msg", count == 0 ? "유저정보 삭제 실패" : "유저정보 삭제 성공");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
    @GetMapping("/user/admin/search")
	public ResponseEntity<String> searchMember(@RequestParam Map<String , String> param){
		List<UserDTO> list = service.searchUser(param);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("msg", "유저 조회 성공");
		map.put("list", list);
		map.put("rank",service.selectAllRank());
		return new ResponseEntity(map, HttpStatus.OK);
	}
    
    @PatchMapping("/user/admin/update/rank")
	public ResponseEntity<String> updateUserRank(@RequestBody Map<String, String> param){
		int count = service.updateRank(param);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("count", count);
		map.put("msg", count == 0 ? "유저 랭크 수정 실패" : "유저 랭크 수정 성공" );
		return new ResponseEntity(map, HttpStatus.OK);
	}
    
    @GetMapping("/login/view")
	public String loginView() {
		return "login";
	}
    
    @PostMapping("/login")
	public ModelAndView login(ModelAndView view, HttpSession session,
			String id, String passwd, HttpServletResponse response) throws IOException {
		
		UserDTO dto = service.selectUser(id, passwd);
		if(dto == null) {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().println("<script>"
					+ "alert('로그인 실패 \\n아이디와 비밀번호 확인하세요');"
					+ "history.back();"
					+ "</script>");
			return null;
		}else if (dto.getGrantNo() != 0){
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().println("<script>"
					+ "alert('로그인 실패 \\ 관리자에게 문의 하세요');"
					+ "</script>");
			return null;
		}else {
			session.setAttribute("user", dto);
			view.setViewName("redirect:/ms4/manage/view");
		}
		
		return view;
	}
    
    @GetMapping("/manage/view")
    public String manageView() {
    	return "manage";
    }
    
    @GetMapping(value = {"/board/view", "/main"})
	public ModelAndView main(ModelAndView view,
			@RequestParam(defaultValue = "1") int pageNo,
			@RequestParam(defaultValue = "20") int pageContentEa) {
		//해당 페이지 게시글 목록 읽어옴
		List<BoardDTO> boardList = 
				boardService.selectBoardNewList(pageNo,pageContentEa);
		System.out.println("게시물 목록 : "+boardList);
		//페이징 정보도 읽어옴
		//	전체 페이지 개수 읽어옴
		int totalCount = boardService.selectBoardTotalCount();
		PagingVO vo = new PagingVO(totalCount, pageNo, pageContentEa);
		//request 영역에 저장
		view.addObject("list", boardList);
		view.addObject("pagging", vo);
		
		view.setViewName("board_list");
		return view;
	}
    
	@GetMapping("/board/{bno}")
	public ModelAndView boardView(ModelAndView view,
			@PathVariable int bno, HttpSession session) {
		//글번호에 해당하는 게시글 조회
		BoardDTO dto = boardService.selectBoard(bno);
		//해당 게시글의 댓글 조회 
		List<BoardCommentDTO> commentList = boardService.selectBoardCommentList(bno);
		
		System.out.println("게시글 : " + dto );
		System.out.println("댓글 : " + commentList );
		//request 영역에 저장
		view.addObject("board", dto);
		view.addObject("commentList", commentList);
		//board_view.html로 이동해서 게시글을 출력
		view.setViewName("board_view");
		return view;
	}
	
	@GetMapping("/board/write/view")
	public String boardWriteView() {
		return "board_write";
	}
	
	@PostMapping("/board/write")
	public String boardWrite(BoardDTO dto, HttpSession session ) throws IllegalStateException, IOException {
		//1. 사용자가 작성한 게시글 제목, 내용, 파일 받아옴
		//2. 작성자는 세션에서 아이디만 빌려옴
		UserDTO userDTO = (UserDTO) session.getAttribute("user");
		dto.setId(userDTO.getId());
		//3. 게시글 새번호 받아옴
		int bno = boardService.getBoardNo();
		dto.setBoardNo(bno);
		//4. 해당 게시글 DB에 등록
		boardService.insertBoard(dto);
		
		return "redirect:/ms4/board/"+bno;
	}
	
	@GetMapping("/board/delete/{bno}")
	public String deleteBoard(@PathVariable int bno,
			HttpSession session,HttpServletResponse response) throws IOException {
		UserDTO user = (UserDTO) session.getAttribute("user");
		BoardDTO board = boardService.selectBoard(bno);
		
		if(user != null && 
				user.getGrantNo() == 0) {
			boardService.deleteBoard(bno);
		}else {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().println("<script>"
					+ "alert('삭제할 권한이 없습니다.');"
					+ "history.back();"
					+ "</script>");
			return null;
		}
		
		
		return "redirect:/";
	}
	
}
