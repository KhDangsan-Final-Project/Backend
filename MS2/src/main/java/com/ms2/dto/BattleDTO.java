package com.ms2.dto;

public class BattleDTO {
    private String type; // 추가된 필드
    private String roomId;
    private String targetPokemonId;
    private int newHp;

    
    
    public BattleDTO() {
	}

	// Getters and setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getTargetPokemonId() {
        return targetPokemonId;
    }

    public void setTargetPokemonId(String targetPokemonId) {
        this.targetPokemonId = targetPokemonId;
    }

    public int getNewHp() {
        return newHp;
    }

    public void setNewHp(int newHp) {
        this.newHp = newHp;
    }
}
