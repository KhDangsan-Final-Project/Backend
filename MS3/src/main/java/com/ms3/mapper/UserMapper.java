package com.ms3.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ms3.dto.PokemonDTO;
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
	// 게시글 사용자 프로필 조회
    String boardProfile(String id);
    
	List<String> getPokemonListById(String id);
	
	String changeEnglishName(String pokemonId);
	
	int updateGrantNo2(String id);
	int updateGrantNo3(String id);
	int updateGrantNo4(String id);
	int updateGrantNo5(String id);
	int updateGrantNo6(String id);
	


}