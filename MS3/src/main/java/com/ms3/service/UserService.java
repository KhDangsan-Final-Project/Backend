package com.ms3.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

    public UserDTO selectInfoUser(String id) {
        return mapper.selectInfoUser(id);
    }

    public int userUpdate(UserDTO userDTO) {
        return mapper.userUpdate(userDTO);
    }

	public List<UserDTO> searchFriend(String query, String userId) {
		return mapper.searchFriend(query, userId);
	}

	public boolean isUserExists(String userId) {
        return mapper.isUserExists(userId) > 0;
    }
    

}