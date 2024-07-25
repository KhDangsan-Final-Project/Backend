package com.ms2.event;

import org.springframework.context.ApplicationEvent;

public class RoomInfoEvent extends ApplicationEvent {

    private final String roomId;
    private final String userId;
    private final String nickname;
    private final Integer grantNo;
    private final String profile;

    public RoomInfoEvent(Object source, String roomId, String userId, String nickname, Integer grantNo, String profile) {
        super(source);
        this.roomId = roomId;
        this.userId = userId;
        this.nickname = nickname;
        this.grantNo = grantNo;
        this.profile = profile;
    }

    // Getters
    public String getRoomId() { return roomId; }
    public String getUserId() { return userId; }
    public String getNickname() { return nickname; }
    public Integer getGrantNo() { return grantNo; }
    public String getProfile() { return profile; }
}
