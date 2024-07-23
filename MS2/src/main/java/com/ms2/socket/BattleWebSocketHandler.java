package com.ms2.socket;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BattleWebSocketHandler extends TextWebSocketHandler {

    // 클라이언트와의 연결을 관리하기 위한 Map
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 클라이언트가 연결되었을 때 호출
        String sessionId = session.getId();
        sessions.put(sessionId, session);
        System.out.println("New connection established: " + sessionId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 클라이언트로부터 메시지를 수신했을 때 호출
        String sessionId = session.getId();
        System.out.println("Received message from " + sessionId + ": " + message.getPayload());

        // 메시지를 JSON으로 파싱 (여기서는 단순히 텍스트로 처리)
        String payload = message.getPayload();

        // 예를 들어 전투 상태 업데이트를 모든 클라이언트에 브로드캐스트
        for (WebSocketSession webSocketSession : sessions.values()) {
            if (webSocketSession.isOpen() && !webSocketSession.getId().equals(sessionId)) {
                webSocketSession.sendMessage(new TextMessage(payload));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 클라이언트가 연결을 종료했을 때 호출
        String sessionId = session.getId();
        sessions.remove(sessionId);
        System.out.println("Connection closed: " + sessionId);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        // 전송 오류가 발생했을 때 호출
        String sessionId = session.getId();
        System.err.println("Error on session " + sessionId + ": " + exception.getMessage());
    }
}
