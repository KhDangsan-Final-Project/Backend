package com.ms3.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ms3.dto.UserDTO;

@Mapper
public interface UserMapper {
    int insertUser(UserDTO dto);
    UserDTO selectUser(Map<String, Object> map);
    int userUpdate(UserDTO dto);
    UserDTO selectInfoUser(String id);
    List<UserDTO> searchFriend(@Param("query") String query, @Param("userId") String userId);
}