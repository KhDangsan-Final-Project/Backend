package com.ms4.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ms4.dto.UserDTO;

@Mapper
public interface UserMapper {
	List<UserDTO> selectAllUser();
	int updateUser(UserDTO dto);
}
