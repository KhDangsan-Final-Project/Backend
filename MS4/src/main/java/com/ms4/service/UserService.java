package com.ms4.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ms4.dto.UserDTO;
import com.ms4.mapper.UserMapper;

@Service
public class UserService {
    private final UserMapper mapper;
    
    public UserService(UserMapper mapper) {
        this.mapper = mapper;
    }

    public int insertUser(UserDTO dto) {
        return mapper.insertUser(dto);
    }

    public UserDTO selectUser(String id, String password) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("password", password);
        return mapper.selectUser(map);
    }

	public UserDTO getUserById(String id) {
		Map<String, Object> map = new HashMap<>();
		map.put("id",id);
		return mapper.selectUserById(map);
	}
}
