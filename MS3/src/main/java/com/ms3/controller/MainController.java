package com.ms3.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ms3.dto.BoardDTO;
import com.ms3.dto.MailDTO;
import com.ms3.dto.UserDTO;
import com.ms3.service.MailService;
import com.ms3.service.UserService;
import com.ms3.util.JwtUtil;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/ms3")
public class MainController {

	private final UserService service;
	private final JwtUtil jwtUtil;

	public MainController(UserService service, JwtUtil jwtUtil) {
		this.service = service;
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
	
	@DeleteMapping("/user/delete")
	public Map<String, Object> deleteUser(@RequestParam String id, @RequestParam String token) {
	    Map<String, Object> response = new HashMap<>();
	    try {
	        String userId = jwtUtil.extractId(token);
	        if (!userId.equals(id)) {
	            response.put("status", "fail");
	            response.put("message", "사용자 ID가 일치하지 않습니다.");
	            return response;
	        }
	        int result = service.deleteUser(id);
	        if (result > 0) {
	            response.put("status", "success");
	            response.put("message", "회원 탈퇴가 성공적으로 처리되었습니다.");
	        } else {
	            response.put("status", "fail");
	            response.put("message", "회원 탈퇴에 실패했습니다.");
	        }
	    } catch (Exception e) {
	        response.put("status", "error");
	        response.put("message", e.getMessage());
	    }
	    return response;
	}

  
   

  
    
    
}