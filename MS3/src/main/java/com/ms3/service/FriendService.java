package com.ms3.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ms3.dto.FriendDTO;
import com.ms3.mapper.FriendMapper;

@Service
public class FriendService {
    private final FriendMapper mapper;

    public FriendService(FriendMapper mapper) {
        this.mapper = mapper;
    }

    public int addFriendRequest(FriendDTO friendDTO) {
    	if (mapper.checkFriendRequestExist(friendDTO) > 0) {
            return -1; // 이미 존재하는 친구 요청
        }
    	
        if (mapper.checkFriendExist(friendDTO) > 0) {
            return -2; // 이미 친구 상태
        }
        return mapper.addFriendRequest(friendDTO);
    }

    public List<FriendDTO> selectFriend(String userId) {
        return mapper.selectFriend(userId);
    }

    public List<FriendDTO> requestFriend(String userId) {
        return mapper.requestFriend(userId);
    }

    public int acceptFriendRequest(FriendDTO friendDTO) {
        return mapper.acceptFriendRequest(friendDTO);
    }

    public int rejectFriendRequest(FriendDTO friendDTO) {
        return mapper.rejectFriendRequest(friendDTO);
    }

	public int deleteFriend(FriendDTO friendDTO) {
		return mapper.deleteFriend(friendDTO);
	}
    
}