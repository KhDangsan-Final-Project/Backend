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
<<<<<<< HEAD
=======
	
	public boolean isUserExists(String userId) {
	    Integer count = mapper.isUserExists(userId);
	    return count != null && count > 0;
	}
	
	public int idcheck(String id) {
		return mapper.idcheck(id);
	}
	
 // 비밀번호 재설정 요청 처리
    public String createPasswordResetToken(String email) {
        UserDTO user = mapper.selectUserByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }
>>>>>>> 930680a6f270399ebf3b0992538fa7cb05d8bea2

	public boolean isUserExists(String userId) {
	    Integer count = mapper.isUserExists(userId);
	    return count != null && count > 0;
	}

	public int deleteUser(String id) {
		return mapper.deleteUser(id);
	}


<<<<<<< HEAD
}
=======
        mapper.updateUserPassword(user.getId(), newPassword);
    }


}
>>>>>>> 930680a6f270399ebf3b0992538fa7cb05d8bea2
