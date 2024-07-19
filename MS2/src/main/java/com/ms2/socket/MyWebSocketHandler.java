package com.ms2.socket;

import com.ms2.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.json.JSONObject;

public class MyWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("WebSocket 연결 성공: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Received message from client: " + payload);

        try {
            // JSON 형식으로 토큰을 추출
            JSONObject json = new JSONObject(payload);
            String token = json.getString("token");

            // 토큰 검증 및 사용자 정보 조회
            JSONObject response = new JSONObject();
            if (validateToken(token)) {
                // 클레임을 추출하여 응답에 포함
                String id = jwtUtil.extractId(token);
                String nickname = jwtUtil.extractNickname(token);
                Integer grantNo = jwtUtil.extractGrantNo(token);
                String profile = jwtUtil.extractProfile(token);

                response.put("id", id);
                response.put("nickname", nickname);
                response.put("grantNo", grantNo);
                response.put("profile", profile);

                // 콘솔에 토큰과 클레임 정보 출력
                System.out.println("Valid token received:");
                System.out.println("ID: " + id);
                System.out.println("Nickname: " + nickname);
                System.out.println("Grant No: " + grantNo);
                System.out.println("Profile: " + profile);
            } else {
                response.put("error", "Invalid token");

                // 유효하지 않은 토큰에 대한 정보 출력
                System.out.println("Invalid token received: " + token);
            }

            session.sendMessage(new TextMessage(response.toString()));
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
        // 에러 발생 시에도 연결을 닫지 않고 에러 로그만 출력합니다.
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("WebSocket 연결 종료: " + session.getId() + ", 상태: " + status);
        // 연결 종료 로그를 남기고, 연결 종료 상태를 모니터링합니다.
    }

    private boolean validateToken(String token) {
        try {
            jwtUtil.extractClaims(token);  // 예외가 발생하지 않으면 토큰이 유효하다고 간주
            return true;
        } catch (Exception e) {
            return false;  // 토큰이 유효하지 않거나 파싱 에러 발생 시
        }
    }
}
