package com.ms3.mapper;

import java.util.Date;
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
<<<<<<< HEAD
	int deleteUser(String id);
=======
>>>>>>> 930680a6f270399ebf3b0992538fa7cb05d8bea2

    int idcheck(String id);


<<<<<<< HEAD
}
=======
    // 이메일로 사용자 조회
    UserDTO selectUserByEmail(String email);
    // 비밀번호 재설정 토큰 저장
    void savePasswordResetToken(String id, String token, java.util.Date expiryTime);
    // 토큰으로 사용자 조회
    UserDTO selectUserByToken(String token);
    // 비밀번호 업데이트
    void updateUserPassword(@Param("userId") String userId, @Param("password") String newPassword);
	


}
>>>>>>> 930680a6f270399ebf3b0992538fa7cb05d8bea2
