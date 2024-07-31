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
    	// 친구 요청 삽입
    	int result1 = mapper.addFriendRequest(friendDTO);
    	// 반대 친구 요청 삽입
    	int result2 = mapper.insertFriend(friendDTO);
    	return (result1 > 0 && result2 > 0) ? 1 : 0;
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