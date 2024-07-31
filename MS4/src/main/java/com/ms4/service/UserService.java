package com.ms4.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ms4.dto.RankDTO;
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

	public int deleteUser(String id) {
		return mapper.deleteUser(id);
	}

	public List<RankDTO> selectAllRank() {
		return mapper.selectAllRank();
	}
	
	public List<RankDTO> selectRankUser(int[] grantNo) {
		return mapper.selectRankUser(grantNo);
	}

	public List<UserDTO> searchUser(Map<String, String> param) {
		return mapper.searchUser(param);
	}

	public int updateRank(Map<String, String> param) {
		return mapper.updateRank(param);
	}

	public UserDTO selectUser(String id, String password) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("password", password);
		return mapper.selectUser(map);
	}

 
}