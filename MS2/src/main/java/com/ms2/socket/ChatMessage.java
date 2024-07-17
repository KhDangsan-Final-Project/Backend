package com.ms2.socket;

public class ChatMessage {
    private String content;
    private String sender;

    // 기본 생성자
    public ChatMessage() {
    }

    // 매개변수가 있는 생성자
    public ChatMessage(String content, String sender) {
        this.content = content;
        this.sender = sender;
    }

    // Getter와 Setter 메서드
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
