package com.ms3.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.ms3.dto.TokenDTO;
import com.ms3.dto.UserDTO;
import com.ms3.mapper.TokenMapper;
import com.ms3.mapper.UserMapper;

@Service
public class TokenService {
	private final TokenMapper tokenMapper;
    private final UserMapper userMapper;
    private final EmailService emailService;

    public TokenService(TokenMapper tokenMapper, UserMapper userMapper, EmailService emailService) {
        this.tokenMapper = tokenMapper;
        this.userMapper = userMapper;
        this.emailService = emailService;
    }

	// 비밀번호 재설정 요청 처리
	public String createPasswordResetToken(String email) {
		UserDTO user = userMapper.selectUserByEmail(email);
		System.out.println(email);
		if (user == null) {
			throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
		}
		
		TokenDTO existingToken = tokenMapper.selectTokenByToken(user.getId());
        if (existingToken != null && existingToken.getExpiryTime().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("이미 유효한 비밀번호 재설정 요청이 존재합니다.");
        }
		
		String token = UUID.randomUUID().toString();
		System.out.println("보낸토큰"+token);
		Date expiryTime = new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1)); // 1시간 유효
		
		tokenMapper.savePasswordResetToken(user.getId(), token, expiryTime);
		return token;
	}
	
	// 비밀번호 재설정 처리
	public void resetPassword(String token, String newPassword) {
	    TokenDTO tokenDTO = tokenMapper.selectTokenByToken(token);
	    System.out.println("받은토큰"+token);
	    System.out.println("저장된 시간 : "+tokenDTO.getExpiryTime());
	    System.out.println(tokenDTO);
	    if (tokenDTO == null) {
	        throw new IllegalArgumentException("유효하지 않은 토큰입니다."); // 토큰이 없는 경우
	    }
	    if (tokenDTO.getExpiryTime() == null) {
	        throw new IllegalArgumentException("토큰의 만료 시간이 설정되지 않았습니다."); // expiryTime이 null인 경우
	    }
	    if (tokenDTO.getExpiryTime().isBefore(LocalDateTime.now())) {
	        throw new IllegalArgumentException("유효하지 않은 토큰입니다."); // 토큰이 만료된 경우
	    }
	    userMapper.updateUserPassword(tokenDTO.getUserId(), newPassword);
	}
	
	
}