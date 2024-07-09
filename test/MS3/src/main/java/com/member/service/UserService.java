package com.member.service;

import org.springframework.stereotype.Service;

import com.member.dto.UserDTO;
import com.member.mapper.UserMapper;

@Service
public class UserService {
	private UserMapper mapper;
	
	public UserService(UserMapper mapper) {
		this.mapper = mapper;
	}

	public int insertUser(UserDTO dto) {
		return mapper.insertUser(dto);
	}
}
