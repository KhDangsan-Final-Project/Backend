package com.ms2.socket;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RoomWebSocketHandler extends TextWebSocketHandler {

    // 방 번호와 세션을 매핑하는 저장소
    private final Map<String, Set<WebSocketSession>> roomSessions = Collections.synchronizedMap(new HashMap<>());

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();
        System.out.println("Received message: " + payload);

        try {
            // 메시지 파싱
            JSONObject jsonObject = new JSONObject(payload);
            String type = jsonObject.getString("type");
            String roomId = jsonObject.optString("roomId", null);

            if ("CREATE_OR_JOIN_ROOM".equals(type) && roomId != null) {
                handleRoomJoin(roomId, session);
            } else {
                // 잘못된 메시지 형식 처리
                session.sendMessage(new TextMessage("{\"error\": \"Invalid message format.\"}"));
            }
        } catch (JSONException e) {
            session.sendMessage(new TextMessage("{\"error\": \"Invalid JSON format.\"}"));
        }
    }

    private void handleRoomJoin(String roomId, WebSocketSession session) throws IOException {
        // 방 번호에 대한 세션 관리
        synchronized (roomSessions) {
            roomSessions.putIfAbsent(roomId, Collections.synchronizedSet(new HashSet<>()));
            roomSessions.get(roomId).add(session);
        }

        // 방 번호와 연결된 세션을 클라이언트에게 전송
        String joinMessage = "{\"type\": \"ROOM_JOINED\", \"roomId\": \"" + roomId + "\"}";
        session.sendMessage(new TextMessage(joinMessage));

        // 방에 있는 다른 클라이언트에게 새로운 클라이언트가 들어왔다는 메시지 전송
        String newClientMessage = "{\"type\": \"NEW_CLIENT\", \"roomId\": \"" + roomId + "\"}";
        for (WebSocketSession s : roomSessions.get(roomId)) {
            if (s != session) { // 현재 세션은 제외
                s.sendMessage(new TextMessage(newClientMessage));
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        // 새로운 클라이언트가 연결되면 호출됩니다.
        System.out.println("New WebSocket session established: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws IOException {
        // 클라이언트가 연결을 종료하면 호출됩니다.
        System.out.println("WebSocket session closed: " + session.getId());

        // 방에서 세션 제거
        synchronized (roomSessions) {
            for (Set<WebSocketSession> sessions : roomSessions.values()) {
                sessions.remove(session);
            }
        }
    }
}
