package com.ms3.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ms3.dto.UserDTO;

@Mapper
public interface UserMapper {
    int insertUser(UserDTO dto);
    UserDTO selectUser(Map<String, Object> map);

}
