package com.ms2.socket;

import com.ms2.dto.UserDTO;
import com.ms2.util.JwtUtil;
import com.ms2.event.UserConnectedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private Map<String, String> sessionNicknameMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("WebSocket 연결 성공: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Received message from client: " + payload);

        try {
            JSONObject json = new JSONObject(payload);
            JSONObject response = new JSONObject();

            if (json.has("token")) {
                String token = json.getString("token");

                if (validateToken(token)) {
                    String id = jwtUtil.extractId(token);
                    String nickname = jwtUtil.extractNickname(token);
                    Integer grantNo = jwtUtil.extractGrantNo(token);
                    String profile = jwtUtil.extractProfile(token);

                    response.put("id", id);
                    response.put("nickname", nickname);
                    response.put("grantNo", grantNo);
                    response.put("profile", profile);

                    UserDTO user = new UserDTO();
                    user.setId(id);
                    user.setGrantNo(grantNo);
                    user.setNickname(nickname);
                    user.setProfile(profile);

                    System.out.println("Valid token received:");
                    System.out.println("ID: " + id);
                    System.out.println("Nickname: " + nickname);
                    System.out.println("Grant No: " + grantNo);
                    System.out.println("Profile: " + profile);

                    sessionNicknameMap.put(session.getId(), nickname);

                    // UserConnectedEvent 게시
                    eventPublisher.publishEvent(new UserConnectedEvent(this, id));
                } else {
                    response.put("error", "Invalid token");
                    System.out.println("Invalid token received: " + token);
                }
                session.sendMessage(new TextMessage(response.toString()));
            } else {
                String senderNickname = sessionNicknameMap.getOrDefault(session.getId(), "unknown");

                response.put("sender", senderNickname);
                response.put("content", json.optString("content", "user"));

                System.out.println("Message received:");
                System.out.println("Sender: " + response.getString("sender"));
                System.out.println("Content: " + response.getString("content"));

                session.sendMessage(new TextMessage(response.toString()));
            }
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
            e.printStackTrace();
            session.close(CloseStatus.SERVER_ERROR);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.err.println("WebSocket transport error: " + exception.getMessage());
        exception.printStackTrace();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("WebSocket 연결 종료: " + session.getId() + ", 상태: " + status);
        sessionNicknameMap.remove(session.getId());
    }

    private boolean validateToken(String token) {
        try {
            jwtUtil.extractClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
