package com.ms4.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ms4.dto.RankDTO;
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
		System.out.println(param);
		List<UserDTO> list = service.searchUser(param);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("msg", "유저 조회 성공");
		map.put("list", list);
		map.put("rank",service.selectAllRank());
		return new ResponseEntity(map, HttpStatus.OK);
	}
    
    @PatchMapping("/user/admin/update/rank")
	public ResponseEntity<String> updateMemberGreade(@RequestParam Map<String, String> param){
		System.out.println(param);
		int count = service.updateRank(param);		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("count", count);
		map.put("msg", count == 0 ? "회원정보 등급 수정 실패" : "회원정보 등급 수정 성공" );
		return new ResponseEntity(map, HttpStatus.OK);
	}
    
	
}
