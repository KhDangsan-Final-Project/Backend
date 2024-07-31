package com.ms3.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ms3.dto.TokenDTO;

@Mapper
public interface TokenMapper {


	// 비밀번호 재설정 토큰 저장
	void savePasswordResetToken(@Param("userId") String userId, @Param("token") String token,
			@Param("expiryTime") Date expiryTime);

	// 토큰으로 사용자 조회
	TokenDTO selectTokenByToken(String token);
}