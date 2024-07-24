package com.ms3.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ms3.dto.FriendDTO;

@Mapper
public interface FriendMapper {
    int addFriendRequest(FriendDTO friendDTO);

    List<FriendDTO> selectFriend(String userId);

    List<FriendDTO> requestFriend(String userId);

    int acceptFriendRequest(FriendDTO friendDTO);
    
    int rejectFriendRequest(FriendDTO friendDTO);

	int deleteFriend(FriendDTO friendDTO);

	int insertFriend(FriendDTO friendDTO);



//	int checkFriendRequestExist(FriendDTO friendDTO);
//
//	int checkFriendExist(FriendDTO friendDTO);
}