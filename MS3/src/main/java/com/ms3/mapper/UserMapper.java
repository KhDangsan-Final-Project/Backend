package com.ms3.mapper;

import java.util.Date;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ms3.dto.UserDTO;

@Mapper
public interface UserMapper {
	int insertUser(UserDTO dto);

	UserDTO selectUser(Map<String, Object> map);

	// 이메일로 사용자 조회
	UserDTO selectUserByEmail(String email);

	// 비밀번호 업데이트
	void updateUserPassword(@Param("userId") String userId, @Param("password") String newPassword);

}
