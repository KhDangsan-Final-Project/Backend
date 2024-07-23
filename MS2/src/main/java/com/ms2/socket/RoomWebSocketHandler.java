package com.ms2.socket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms2.event.RoomInfoEvent;
import com.ms2.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class RoomWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, Set<WebSocketSession>> rooms = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 파서

    @Autowired
    private JwtUtil jwtUtil; // JwtUtil 주입

    @Autowired
    private ApplicationEventPublisher eventPublisher; // 이벤트 발행기

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();
        System.out.println("Received payload: " + payload); // 디버깅을 위한 로그

        try {
            // JSON으로 파싱
            JsonNode jsonNode = objectMapper.readTree(payload);
            String roomId = jsonNode.get("roomId").asText();
            String type = jsonNode.get("type").asText();
            String token = jsonNode.has("token") ? jsonNode.get("token").asText() : null;

            if (token != null && !token.isEmpty()) {
                // JWT 토큰을 해석하여 사용자 정보를 얻습니다.
                String userId = jwtUtil.extractId(token);
                String nickname = jwtUtil.extractNickname(token);
                Integer grantNo = jwtUtil.extractGrantNo(token);
                String profile = jwtUtil.extractProfile(token);

                System.out.println("User ID: " + userId);
                System.out.println("Nickname: " + nickname);
                System.out.println("Grant No: " + grantNo);
                System.out.println("Profile: " + profile);

                // 방 번호를 클라이언트에게 응답으로 보내줍니다.
                switch (type) {
                    case "CREATE_OR_JOIN_ROOM":
                        handleRoomCreationOrJoining(session, roomId, userId, nickname, grantNo, profile);
                        break;
                    case "DELETE_ROOM":
                        handleRoomDeletion(roomId);
                        break;
                    default:
                        System.out.println("Unknown message type: " + type); // 디버깅을 위한 로그
                        break;
                }

                // 방 정보 이벤트 발행
                RoomInfoEvent event = new RoomInfoEvent(this, roomId, userId, nickname, grantNo, profile);
                eventPublisher.publishEvent(event);

            } else {
                session.sendMessage(new TextMessage("{\"error\": \"Invalid token\"}"));
            }
        } catch (Exception e) {
            e.printStackTrace(); // 예외 로그
            session.sendMessage(new TextMessage("{\"error\": \"Invalid message format\"}"));
        }
    }

    private void handleRoomCreationOrJoining(WebSocketSession session, String roomId, String userId, String nickname, Integer grantNo, String profile) throws IOException {
        rooms.computeIfAbsent(roomId, k -> new HashSet<>()).add(session);
        String response = String.format(
            "{\"type\": \"ROOM_JOINED\", \"roomId\": \"%s\", \"userId\": \"%s\", \"nickname\": \"%s\", \"grantNo\": %d, \"profile\": \"%s\"}",
            roomId, userId, nickname, grantNo, profile
        );
        session.sendMessage(new TextMessage(response));
        System.out.println("Room created or joined: " + roomId);
    }

    private void handleRoomDeletion(String roomId) throws IOException {
        rooms.remove(roomId);
        String response = "{\"type\": \"ROOM_DELETED\", \"roomId\": \"" + roomId + "\"}";
        // 방 삭제 메시지를 모든 클라이언트에게 전송
        broadcastToRoom(roomId, new TextMessage(response));
        System.out.println("Room deleted: " + roomId);
    }

    private void broadcastToRoom(String roomId, TextMessage message) throws IOException {
        Set<WebSocketSession> sessions = rooms.get(roomId);
        if (sessions != null) {
            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            }
        }
    }
}
