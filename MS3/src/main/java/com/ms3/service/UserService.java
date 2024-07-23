package com.ms3.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.ms3.dto.UserDTO;
import com.ms3.mapper.UserMapper;

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
	
}
