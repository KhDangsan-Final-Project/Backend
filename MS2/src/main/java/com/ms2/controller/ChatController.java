package com.ms2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ms2.socket.ChatMessage;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private List<ChatMessage> messages = new ArrayList<>();

    @PostMapping("/sendMessage")
    public ResponseEntity<Void> sendMessage(@RequestBody ChatMessage chatMessage) {
        // 콘솔에 메시지 출력
        System.out.println("Received message: " + chatMessage.getSender() + ": " + chatMessage.getContent());
        
        messages.add(chatMessage);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/messages")
    public ResponseEntity<List<ChatMessage>> getMessages() {
        return ResponseEntity.ok(messages);
    }
}
