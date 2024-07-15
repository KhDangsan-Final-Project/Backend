package com.ms4.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ms4.dto.UserDTO;
import com.ms4.mapper.UserMapper;

@Service
public class UserService {
	private final UserMapper mapper;
 
	public UserService(UserMapper mapper) {
		this.mapper = mapper;
 }

	public List<UserDTO> selectAllUser() {
		return mapper.selectAllUser();
	}

	public int updateUser(UserDTO dto) {
		return mapper.updateUser(dto);
	}

 
}
