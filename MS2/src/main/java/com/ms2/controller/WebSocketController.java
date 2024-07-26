package com.ms2.controller;

//WebSocketController.java
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms2.dto.ChatMessage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/ms2")
public class WebSocketController {
	
	@MessageMapping("/sendMessage")
	 public void sendMessage(@Payload ChatMessage chatMessage) {
	     System.out.println("Received message: " + chatMessage.getNickname() + ": " + chatMessage.getContent());
	 }

}
