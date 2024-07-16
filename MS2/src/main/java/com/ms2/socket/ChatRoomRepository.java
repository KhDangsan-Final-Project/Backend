package com.ms2.socket;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class ChatRoomRepository {
	//채팅방 객체 저장할 맵
	private Map<String, ChatRoom> chatRoomMap;
	
	public ChatRoomRepository() {
		chatRoomMap = new HashMap<String, ChatRoom>();
		for(int i=0;i<2;i++) {
			String uuid = UUID.randomUUID().toString();
			ChatRoom room = new ChatRoom(uuid);
			chatRoomMap.put(room.getId(), room);
			System.out.println("chat room 클래스 복제중");
			System.out.println("chatroom -> " + room);
		}
	}
	
	public ChatRoom getChatRoom(String id) {
		return chatRoomMap.get(id);
	}
	
	public Map<String, ChatRoom> getChatRooms(){
		return chatRoomMap;
	}
	
	public void remove(WebSocketSession session) {
		getRooms().parallelStream().forEach(chatRoom -> chatRoom.remove(session));
    }
	
	//채팅방 생성하여 맵에 등록
	public String createChatRoom() {
		String uuid = UUID.randomUUID().toString();
		ChatRoom room = new ChatRoom(uuid);
		chatRoomMap.put(room.getId(), room);
		return uuid;
	}
	
	//채팅방 목록 가져오기
	public Collection<ChatRoom> getRooms(){
		return chatRoomMap.values();
	}
	
}
