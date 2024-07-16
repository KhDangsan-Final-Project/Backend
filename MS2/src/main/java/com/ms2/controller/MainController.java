package com.ms2.controller;

import java.util.Collection;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ms2.socket.ChatRoom;
import com.ms2.socket.ChatRoomRepository;

@Controller
public class MainController {
	
	private ChatRoomRepository chatRoomRepository;
	
	public MainController(ChatRoomRepository chatRoomRepository) {
		this.chatRoomRepository = chatRoomRepository;
	}

	@GetMapping("/")
	public ModelAndView main(ModelAndView view) {
		view.addObject("collection", chatRoomRepository.getRooms());
		view.setViewName("index");
		return view;
	}
	
	@GetMapping("/room")
    public ModelAndView roomController(ModelAndView view, String id) {
        view.setViewName("room");
        view.addObject("roomId", id);
        return view;
    }
	
	//채팅방 생성
	@GetMapping("/room/new")
	public String newRoom() {
		String id = chatRoomRepository.createChatRoom();
		return "redirect:/room?id="+id;
	}
	
}
