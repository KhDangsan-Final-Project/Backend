package com.ms4.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ms4.dto.UserDTO;
import com.ms4.service.UserService;

import ch.qos.logback.core.model.Model;

@RestController
@RequestMapping("/ms4")
public class MainController {
	
	private final UserService service;
	
	public MainController(UserService service) {
        this.service = service;
    }
	
	@GetMapping("/user/list")
	public ModelAndView userList (ModelAndView view) {
		List<UserDTO> userList = service.selectAllUser();
		
		view.addObject("userList", userList);
		
		view.setViewName("user_list");
		return view;
	}
	
	@PutMapping("/user/admin/update")
    public ResponseEntity<String> updateMember(UserDTO dto) {
        int count = service.updateUser(dto);        
        Map<String, Object> map = new HashMap<>();
        map.put("count", count);
        map.put("msg", count == 0 ? "회원정보 수정 실패" : "회원정보 수정 성공");
        return new ResponseEntity(map, HttpStatus.OK);
    }
	
}
