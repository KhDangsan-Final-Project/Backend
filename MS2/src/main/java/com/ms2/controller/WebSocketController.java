package com.ms2.controller;

//WebSocketController.java
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import com.ms2.socket.ChatMessage;

@Controller
public class WebSocketController {

 @MessageMapping("/sendMessage")
 public void sendMessage(@Payload ChatMessage chatMessage) {
     System.out.println("Received message: " + chatMessage.getSender() + ": " + chatMessage.getContent());
 }
}
