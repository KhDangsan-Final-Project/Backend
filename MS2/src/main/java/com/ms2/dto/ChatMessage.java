package com.ms2.dto;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ChatMessage {
    private String nickname;
    private String content;

    public ChatMessage() {}

    public ChatMessage(String nickname, String content) {
        this.nickname = nickname;
        this.content = content;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ChatMessage fromJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, ChatMessage.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
