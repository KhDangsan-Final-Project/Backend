package com.ms2.socket;

import com.ms2.util.JwtUtil;
import com.ms2.event.UserConnectedEvent;
import com.ms2.service.UserService;
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
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private Map<String, String> sessionNicknameMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("MyWebSocketHandler WebSocket 연결 성공: " + session.getId());
        // Optionally send a welcome message
        sendMessage(session, "Welcome to the WebSocket server!");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Received message from client: " + payload);

        try {
            // Parse the received message payload as a JSON object
            JSONObject json = new JSONObject(payload);

            if (json.has("token")) {
                String token = json.getString("token");

                // Validate the token and extract id and nickname
                if (validateToken(token)) {
                    String id = jwtUtil.extractId(token);
                    String nickname = jwtUtil.extractNickname(token);

                    // Print the extracted id and nickname to the console
                    System.out.println("Valid token received:");
                    System.out.println("ID: " + id);
                    System.out.println("Nickname: " + nickname);

                    // Save the nickname associated with the session
                    sessionNicknameMap.put(session.getId(), nickname);

                    // Optionally, you can also publish the UserConnectedEvent if needed
                    eventPublisher.publishEvent(new UserConnectedEvent(this, id));
                    
                    // Send acknowledgment message back to client
                    sendMessage(session, "Token validated. You are connected as " + nickname);

                } else {
                    System.out.println("Invalid token received: " + token);
                    sendMessage(session, "Invalid token. Connection closed.");
                    session.close(CloseStatus.BAD_DATA);
                }
            } else {
                System.out.println("No token found in message.");
                sendMessage(session, "No token found. Connection closed.");
                session.close(CloseStatus.BAD_DATA);
            }
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
            e.printStackTrace();
            sendMessage(session, "Error processing message.");
            session.close(CloseStatus.SERVER_ERROR);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.err.println("WebSocket transport error: " + exception.getMessage());
        exception.printStackTrace();
        sendMessage(session, "Transport error occurred.");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("MyWebSocketHandler WebSocket 연결 종료: " + session.getId() + ", 상태: " + status);
        // Remove the nickname associated with the session
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

    private void sendMessage(WebSocketSession session, String message) throws Exception {
        session.sendMessage(new TextMessage(message));
    }
}
