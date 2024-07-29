package com.ms2.socket;

import com.ms2.dto.UserDTO;
import com.ms2.dto.PokemonDTO;
import com.ms2.service.UserService;
import com.ms2.util.JwtUtil;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class PokemonWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("WebSocket connection established: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Received message: " + payload);

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

                    UserDTO user = userService.selectUserPokemonNum(id);
                    if (user != null) {
                        response.put("pokemonList", user.getPokemonList() != null ? convertPokemonListToJson(user.getPokemonList()) : new JSONArray());
                    } else {
                        response.put("pokemonList", new JSONArray());
                    }

                    session.sendMessage(new TextMessage(response.toString()));
                } else {
                    response.put("error", "Invalid token");
                    session.sendMessage(new TextMessage(response.toString()));
                }
            } else {
                response.put("error", "Token not provided");
                session.sendMessage(new TextMessage(response.toString()));
            }
        } catch (Exception e) {
            response.put("error", "Error processing message");
            session.sendMessage(new TextMessage(response.toString()));
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
        System.out.println("WebSocket connection closed: " + session.getId() + ", status: " + status);
    }

    private boolean validateToken(String token) {
        try {
            jwtUtil.extractClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private JSONArray convertPokemonListToJson(List<PokemonDTO> pokemonList) {
        JSONArray jsonArray = new JSONArray();
        for (PokemonDTO pokemon : pokemonList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", pokemon.getId());
            jsonObject.put("englishName", pokemon.getEnglishName());
            jsonObject.put("koreanName", pokemon.getKoreanName());
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }
}
