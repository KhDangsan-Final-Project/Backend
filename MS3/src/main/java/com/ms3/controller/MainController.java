package com.ms3.controller;

import java.util.HashMap;
import java.util.Map;

<<<<<<< HEAD
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms3.dto.UserDTO;
import com.ms3.service.UserService;
import com.ms3.util.JwtUtil;


@RestController
@RequestMapping("/ms3/user")
public class MainController {
    
    private final UserService service;
    private final JwtUtil jwtUtil;
    
    public MainController(UserService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id){
    	UserDTO user = service.getUserById(id);
    	return ResponseEntity.ok(user);
    }
    
    @PostMapping("/ms3/user/select")
    public Map<String, Object> login(@RequestBody Map<String, String> param) {
        String id = param.get("id");
        String password = param.get("password");

        Map<String, Object> map = new HashMap<>();
        
        UserDTO user = service.selectUser(id, password);
        if (user != null) {
            String token = jwtUtil.generateToken(user.getId(), user.getNickname(), user.getGrantNo(), user.getProfile());
            map.put("msg", "로그인 성공");
            map.put("result", true);
            map.put("token", token);
        } else {
            map.put("msg", "로그인 실패");
            map.put("result", false);
        }

        return map;
    }
    
    @PostMapping("/ms3/user/insert")
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
    
    //토큰에 있는 아이디 , 닉 , 프로필 , 랭크번호
    @GetMapping("/ms3/user/info")
    public ResponseEntity<Map<String, Object>> getInfo(@RequestParam String token) {
    	boolean flag = true;
    	Map<String, Object> map = new HashMap<>();
    	try {
    		String id = jwtUtil.extractId(token);
    		String nickname = jwtUtil.extractNickname(token);
    		String profile = jwtUtil.extractProfile(token);
    		int grantNo = jwtUtil.extractGrantNo(token);
    	}catch (Exception e) {
    		e.printStackTrace();
    		flag = false;
    	}
    	map.put("flag", flag);
    	return ResponseEntity.ok(map);
    }
    
    
=======
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms3.dto.UserDTO;
import com.ms3.service.UserService;
import com.ms3.util.JwtUtil;

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
            String token = jwtUtil.generateToken(user.getId(), user.getNickname(), user.getGrantNo(), user.getProfile());
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
>>>>>>> branch 'main' of https://github.com/KhDangsan-Final-Project/Backend.git
}
