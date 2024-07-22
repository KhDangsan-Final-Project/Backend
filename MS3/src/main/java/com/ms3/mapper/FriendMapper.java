package com.ms3.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ms3.dto.FriendDTO;

@Mapper
public interface FriendMapper {
    // 친구 추가 요청
    int addFriendRequest(FriendDTO friendDTO);

    // 친구 목록 조회
    List<FriendDTO> selectFriend(String userId);

    // 친구 요청 조회
    List<FriendDTO> requestFriend(String userId);

    int acceptFriendRequest(FriendDTO friendDTO);
    
    int rejectFriendRequest(FriendDTO friendDTO);

	int deleteFriend(FriendDTO friendDTO);

	int checkFriendRequestExist(FriendDTO friendDTO);

	int checkFriendExist(FriendDTO friendDTO);

}