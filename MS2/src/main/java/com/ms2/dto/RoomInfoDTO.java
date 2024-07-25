package com.ms2.dto;

public class RoomInfoDTO {

    private String roomId;
    private String userId;
    private String nickname;
    private Integer grantNo;
    private String profile;

    // 기본 생성자
    public RoomInfoDTO() {}

    // 파라미터를 받는 생성자
    public RoomInfoDTO(String roomId, String userId, String nickname, Integer grantNo, String profile) {
        this.roomId = roomId;
        this.userId = userId;
        this.nickname = nickname;
        this.grantNo = grantNo;
        this.profile = profile;
    }

    // Getter 및 Setter
    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getGrantNo() {
        return grantNo;
    }

    public void setGrantNo(Integer grantNo) {
        this.grantNo = grantNo;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "RoomInfoDTO{" +
                "roomId='" + roomId + '\'' +
                ", userId='" + userId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", grantNo=" + grantNo +
                ", profile='" + profile + '\'' +
                '}';
    }
}
