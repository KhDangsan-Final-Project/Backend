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
	int isUserExists(String userId);
	int deleteUser(String id);
    int idcheck(String id);

    // 이메일로 사용자 조회
    UserDTO selectUserByEmail(String email);
    // 비밀번호 재설정 토큰 저장
    void savePasswordResetToken(String id, String token, java.util.Date expiryTime);
    // 토큰으로 사용자 조회
    UserDTO selectUserByToken(String token);
    // 비밀번호 업데이트
    void updateUserPassword(@Param("userId") String userId, @Param("password") String newPassword);
	
}

