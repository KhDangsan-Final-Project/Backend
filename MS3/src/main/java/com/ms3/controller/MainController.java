package com.ms3.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ms3.dto.UserDTO;
import com.ms3.service.UserService;
import com.ms3.util.JwtUtil;

//test
@RestController
public class MainController {
    
    private final UserService service;
    private final JwtUtil jwtUtil;
    
    public MainController(UserService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
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
}
