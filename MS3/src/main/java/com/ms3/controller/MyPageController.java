package com.ms3.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms3.dto.FriendDTO;
import com.ms3.dto.UserDTO;
import com.ms3.service.FriendService;
import com.ms3.service.UserService;
import com.ms3.util.JwtUtil;

@RestController
@RequestMapping("/ms3")
public class MyPageController {

    private final FriendService friendService;
    private final UserService service;
    private final JwtUtil jwtUtil;

    public MyPageController(UserService service, FriendService friendService, JwtUtil jwtUtil) {
        this.service = service;
        this.friendService = friendService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/mypage")
    public UserDTO selectInfoUser(@RequestParam String token) {
        String userId = jwtUtil.extractId(token);
        return service.selectInfoUser(userId);
    }

    @PutMapping("/mypage/update")
    public Map<String, Object> userUpdate(@RequestBody UserDTO userDTO) {
        Map<String, Object> map = new HashMap<>();
        int result = service.userUpdate(userDTO);
        map.put("status", result > 0 ? "success" : "fail");
        return map;
    }

    @PostMapping("/friend/add")
    public Map<String, Object> addFriendRequest(@RequestBody FriendDTO friendDTO, @RequestParam String token) {
        Map<String, Object> map = new HashMap<>();
        String userId = jwtUtil.extractId(token);
        friendDTO.setUserId(userId);
        friendDTO.setStatus("pending"); // 상태를 'pending'으로 설정
        int result = friendService.addFriendRequest(friendDTO);
        map.put("status", result > 0 ? "success" : "fail");
        return map;
    }

    @GetMapping("/friend")
    public List<FriendDTO> selectFriend(@RequestParam String token) {
        String userId = jwtUtil.extractId(token);
        return friendService.selectFriend(userId);
    }

    @GetMapping("/friend/search")
    public List<UserDTO> searchFriend(@RequestParam String query) {
    	System.out.println(query);
        return service.searchFriend(query);
    }

    @GetMapping("/friend/request")
    public List<FriendDTO> requestFriend(@RequestParam String token) {
        String userId = jwtUtil.extractId(token);
        return friendService.requestFriend(userId);
    }
    
    @PutMapping("/friend/accept")
    public Map<String, Object> acceptFriendRequest(@RequestBody FriendDTO friendDTO, @RequestParam String token) {
        Map<String, Object> map = new HashMap<>();
        String userId = jwtUtil.extractId(token);
        friendDTO.setFriendId(userId);
        friendDTO.setStatus("accepted");
        int result = friendService.acceptFriendRequest(friendDTO);
        map.put("status", result > 0 ? "success" : "fail");
        return map;
    }
    
    

    @DeleteMapping("/friend/reject")
    public Map<String, Object> rejectFriendRequest(@RequestBody FriendDTO friendDTO, @RequestParam String token) {
        Map<String, Object> map = new HashMap<>();
        String userId = jwtUtil.extractId(token);
        friendDTO.setFriendId(userId);
        int result = friendService.rejectFriendRequest(friendDTO);
        map.put("status", result > 0 ? "success" : "fail");
        return map;
    }

    
    @DeleteMapping("/friend/delete")
    public Map<String, Object> deleteFriend(@RequestBody FriendDTO friendDTO, @RequestParam String token) {
    	Map<String, Object> map = new HashMap<>();
    	String userId = jwtUtil.extractId(token);
    	friendDTO.setUserId(userId);
    	int result = friendService.deleteFriend(friendDTO);
    	map.put("status", result > 0 ? "success" : "fail");
    	return map;
    }
    
    
    
    
    
    
    
}
