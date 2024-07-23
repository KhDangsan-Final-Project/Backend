package com.ms2.socket;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class RoomWebSocketHandler extends TextWebSocketHandler {

    // 방 번호를 저장할 간단한 저장소 (실제 애플리케이션에서는 데이터베이스나 분산 캐시 사용 고려)
    private final Set<String> roomNumbers = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();
        // 방 번호가 포함된 메시지를 처리
        // 예: { "roomNumber": "12345" }
        System.out.println("Received message: " + payload);

        // 방 번호 처리 (여기에서는 단순히 저장만 함)
        // 실제로는 더 복잡한 로직을 여기에 추가할 수 있습니다.
        if (payload.contains("roomNumber")) {
            String roomNumber = payload.split(":")[1].replaceAll("[^0-9]", ""); // 방 번호 추출
            roomNumbers.add(roomNumber); // 방 번호 저장

            // 클라이언트에게 방 번호 저장 성공 메시지 전송
            session.sendMessage(new TextMessage(roomNumber));
        } else {
            // 잘못된 메시지 형식 처리
            session.sendMessage(new TextMessage("Invalid message format."));
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
    }
}
