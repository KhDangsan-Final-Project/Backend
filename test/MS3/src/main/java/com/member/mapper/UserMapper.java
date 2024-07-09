package com.member.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.member.dto.UserDTO;

@Mapper
public interface UserMapper {

	int insertUser(UserDTO dto);

}
