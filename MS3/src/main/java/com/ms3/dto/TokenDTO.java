package com.ms3.dto;

import java.time.LocalDateTime;

import org.apache.ibatis.type.Alias;

@Alias("token")
public class TokenDTO {
	public String userId;
	public String token;
	private LocalDateTime expiryTime;
	
	public TokenDTO() {
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(LocalDateTime expiryTime) {
		this.expiryTime = expiryTime;
	}

	@Override
	public String toString() {
		return "TokenDTO [userId=" + userId + ", token=" + token + ", expiryTime=" + expiryTime + "]";
	}
	
	
}