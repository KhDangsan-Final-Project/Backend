package com.ms2.socket;

import com.ms2.dto.UserDTO;
import com.ms2.service.UserService;
import com.ms2.util.JwtUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class UpdateWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("UpdateWebSocketHandler WebSocket 연결 성공: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Received message from client by UpdateWebSocketHandler: " + payload);

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
                    response.put("profile", profile != null ? profile : "No profile");

                    // matchWin을 클라이언트에서 가져왔는지 확인
                    if (json.has("matchWin")) {
                        Integer matchWin = json.getInt("matchWin");
                        
                        // DB에 있는 matchWin 데이터 업데이트
                        int updateResult = userService.updateUserVictoryCount(id);
                        System.out.println("updateResult" + updateResult);
                        if (updateResult > 0) {
                            System.out.println("MatchWin updated successfully.");
                            
                            // 업데이트된 matchWin 데이터 가져와서 DTO 에 저장
                            UserDTO user = userService.selectUserVictoryCount(id);
                            if (user != null) {
                                response.put("matchWin", user.getMatchWin() != null ? user.getMatchWin().toString() : "No matchWin info");
                            } else {
                                response.put("matchWin", "No matchWin info");
                            }
                        } else {
                            response.put("error", "Failed to update matchWin");
                        }
                    } else {
                        UserDTO user = userService.selectUserVictoryCount(id);
                        if (user != null) {
                            response.put("matchWin", user.getMatchWin() != null ? user.getMatchWin().toString() : "No matchWin info");
                        } else {
                            response.put("matchWin", "No matchWin info");
                        }
                    }

                    System.out.println("Valid token received by updateWebSocketHandler:");
                    System.out.println("ID: " + id);
                    System.out.println("Nickname: " + nickname);
                    System.out.println("Grant No: " + grantNo);
                    System.out.println("Profile: " + profile);
                    System.out.println("Match Win: " + response.getString("matchWin"));

                    session.sendMessage(new TextMessage(response.toString()));
                } else {
                    response.put("error", "Invalid token");
                    System.out.println("Invalid token received: " + token);
                    session.sendMessage(new TextMessage(response.toString()));
                }
            } else {
                response.put("error", "Token not provided");
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
        session.close(CloseStatus.SERVER_ERROR);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("UpdateWebSocketHandler WebSocket 연결 종료: " + session.getId() + ", 상태: " + status);
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
