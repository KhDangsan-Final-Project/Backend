package com.ms3.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.ms3.dto.UserDTO;
import com.ms3.mapper.UserMapper;

@Service
public class UserService {
    private final UserMapper mapper;
    
    public UserService(UserMapper mapper) {
        this.mapper = mapper;
    }

    public int insertUser(UserDTO dto) {
        return mapper.insertUser(dto);
    }

    public UserDTO selectUser(String id, String password) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("password", password);
        return mapper.selectUser(map);
    }

    public UserDTO selectInfoUser(String id) {
        return mapper.selectInfoUser(id);
    }

    public int userUpdate(UserDTO userDTO) {
        return mapper.userUpdate(userDTO);
    }

	public List<UserDTO> searchFriend(String query, String userId) {
		return mapper.searchFriend(query, userId);
	}
    
 // 비밀번호 재설정 요청 처리
    public String createPasswordResetToken(String email) {
        UserDTO user = mapper.selectUserByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }

        String token = UUID.randomUUID().toString();
        Date expiryTime = new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1)); // 1시간 유효

        mapper.savePasswordResetToken(user.getId(), token, expiryTime);
        return token;
    }

    // 비밀번호 재설정 처리
    public void resetPassword(String token, String newPassword) {
        UserDTO user = mapper.selectUserByToken(token);
        if (user == null || user.getTokenExpiryTime().before(new Date())) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }

        mapper.updateUserPassword(user.getId(), newPassword);
    }

	public int idcheck(String id) {
		return mapper.idcheck(id);
	}

}