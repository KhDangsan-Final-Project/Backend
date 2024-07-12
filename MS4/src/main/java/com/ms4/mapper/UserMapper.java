package com.ms4.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ms4.dto.UserDTO;

@Mapper
public interface UserMapper {
    int insertUser(UserDTO dto);
    UserDTO selectUser(Map<String, Object> map);
	UserDTO selectUserById(Map<String, Object> map);
}
