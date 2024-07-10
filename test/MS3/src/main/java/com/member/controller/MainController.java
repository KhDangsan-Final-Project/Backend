package com.member.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.member.dto.UserDTO;
import com.member.service.UserService;
import com.member.util.JwtUtil;

import jakarta.servlet.http.HttpSession;

@RestController
public class MainController {
	private UserService service;
	private JwtUtil jwtUtil;
	
	
	public MainController(UserService service, JwtUtil jwtUtil) {
		this.service = service;
		this.jwtUtil = jwtUtil;
	}
	// test
	@PostMapping("/ms3/login")
	public Map<String, Object> login (@RequestBody Map<String, String> param, HttpSession session) {
		String id = param.get("id");
		String password = param.get("password");
		
		Map<String, Object> map = new HashMap<>();
		
		UserDTO user = service.findUserById(id);
		
		return null;
	}
	
	@PostMapping("/ms3/user/insert")
	public Map<String , Object> insertUser(@RequestBody Map<String, String> param) {
		UserDTO dto = new UserDTO();
		dto.setId(param.get("id"));
		dto.setGrantNo(Integer.parseInt(param.get("grantNo")));
		dto.setPassword(param.get("password"));
		dto.setEmail(param.get("email"));
		dto.setName(param.get("name"));
		dto.setNickname(param.get("nickname"));
		dto.setProfile(Integer.parseInt(param.get("profile")));
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			service.insertUser(dto);
			map.put("msg", "회원 등록 성공");
			map.put("result", true);
		}catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "회원 등록 실패");
			map.put("result", false);
		}
		return map;
	}
	
	public String mypageView() {
		return null;
	}
	
	public String updateView() {
		return null;
	}

}
