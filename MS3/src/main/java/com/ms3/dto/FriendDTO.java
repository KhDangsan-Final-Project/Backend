package com.ms3.dto;

import org.apache.ibatis.type.Alias;

@Alias("friend")
public class FriendDTO {
    private String userId;
    private String friendId;
    private String status;
    
	public FriendDTO(String userId, String friendId, String status) {
		this.userId = userId;
		this.friendId = friendId;
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFriendId() {
		return friendId;
	}

	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "FriendDTO [userId=" + userId + ", friendId=" + friendId + ", status=" + status + "]";
	}

    
}