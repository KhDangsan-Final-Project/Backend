package com.ms2.socket;

import com.ms2.dto.UserDTO;
import com.ms2.dto.PokemonDTO;
import com.ms2.service.UserService;
import com.ms2.util.JwtUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class TokenWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("TokenWebSocketHandler WebSocket 연결 성공: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Received message from client: " + payload);

        JSONObject response = new JSONObject();
        try {
            JSONObject json = new JSONObject(payload);

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
                    response.put("profile", profile != null ? profile : "1");

                    //사용자의 모든 데이터 조회
                    UserDTO user = userService.selectUserPokemonNum(id);
                    if (user != null) {
                        response.put("matchWin", user.getMatchWin() != null ? user.getMatchWin().toString() : "No matchWin info");

                        //포켓몬 리스트 추가
                        if (user.getPokemonList() != null && !user.getPokemonList().isEmpty()) {
                            JSONArray pokemonArray = new JSONArray();
                            for (PokemonDTO pokemon : user.getPokemonList()) {
                                JSONObject pokemonJson = new JSONObject();
                                pokemonJson.put("id", pokemon.getId());
                                pokemonJson.put("englishName", pokemon.getEnglishName());
                                pokemonJson.put("koreanName", pokemon.getKoreanName());
                                pokemonArray.put(pokemonJson);
                            }
                            response.put("pokemonList", pokemonArray);
                        } else {
                            response.put("pokemonList", "No pokemon info");
                        }
                    } else {
                        response.put("matchWin", "No matchWin info");
                        response.put("pokemonList", "No pokemon info");
                    }

                    System.out.println(response.toString());
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
        System.out.println("TokenWebSocketHandler WebSocket 연결 종료: " + session.getId() + ", 상태: " + status);
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
