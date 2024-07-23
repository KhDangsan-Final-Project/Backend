package com.ms1.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtUtil {
	private static final String SECRET_KEY = "g2d2b2PpL1tFs3uHd9qF8h2vF3sR6wU4g7zH9k2mQ1pF3x2zJ1oL";

	public String generateToken(String id, String nickname, int grantNo, String profile) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("nickname", nickname);
		claims.put("grantNo", grantNo);
		claims.put("profile", profile);

		return Jwts.builder().setClaims(claims).setSubject(id).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1일
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
	}

	public Claims extractClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	public String extractId(String token) {
		return extractClaims(token).getSubject();
	}

	public String extractNickname(String token) {
		return extractClaims(token).get("nickname", String.class);
	}

	public Integer extractGrantNo(String token) {
		return extractClaims(token).get("grantNo", Integer.class);
	}

	public String extractProfile(String token) {
		return extractClaims(token).get("profile", String.class);
	}

	public String getUserIdFromToken(String token) {
		return extractClaims(token).getSubject();
	}

	public static String extractUserId(String token) {
		try {
			Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
			return claims.getSubject(); // 사용자 ID는 subject로 저장
		} catch (SignatureException e) {
			return null;
		}
	}
}
