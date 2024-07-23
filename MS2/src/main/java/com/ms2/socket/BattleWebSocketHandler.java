package com.ms2.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms2.dto.BattleDTO;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BattleWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, WebSocketSession> sessions = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Add session to sessions map
        sessions.put(session.getId(), session);
        System.out.println("Connected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Parse the incoming message
        BattleDTO battleDTO = objectMapper.readValue(message.getPayload(), BattleDTO.class);

        // Process the battle logic
        BattleDTO updatedBattle = processBattle(battleDTO);

        // Send the updated HP to all clients
        for (WebSocketSession webSocketSession : sessions.values()) {
            sendUpdatedHp(webSocketSession, updatedBattle);
        }
    }

    private BattleDTO processBattle(BattleDTO battleDTO) {
        // Simulate battle logic here
        // For example, we will just decrease HP by a fixed value for demonstration
        int damage = 10; // Fixed damage value for simplicity
        int newHp = battleDTO.getNewHp() - damage;

        if (newHp < 0) newHp = 0; // HP should not be negative

        // Return the updated battle state
        BattleDTO updatedBattle = new BattleDTO();
        updatedBattle.setRoomId(battleDTO.getRoomId());
        updatedBattle.setTargetPokemonId(battleDTO.getTargetPokemonId());
        updatedBattle.setNewHp(newHp);

        return updatedBattle;
    }

    private void sendUpdatedHp(WebSocketSession session, BattleDTO updatedBattle) {
        try {
            String updatedBattleJson = objectMapper.writeValueAsString(updatedBattle);
            session.sendMessage(new TextMessage(updatedBattleJson));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Remove session from sessions map
        sessions.remove(session.getId());
        System.out.println("Disconnected: " + session.getId());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        exception.printStackTrace();
    }
}
