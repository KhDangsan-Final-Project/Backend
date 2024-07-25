package com.ms2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms2.dto.RoomInfoDTO;
import com.ms2.dto.UserDTO;
import com.ms2.mapper.BattleMapper;

@Service
public class UserService {
    
    private final BattleMapper battleMapper;

    @Autowired
    public UserService(BattleMapper battleMapper) {
        this.battleMapper = battleMapper;
    }

    public UserDTO selectUserVictoryCount(String id) {
        return battleMapper.selectUserVictoryCount(id);
    }

	public int insertRoomInfo(RoomInfoDTO roomInfoDTO) {
		return battleMapper.insertRoomInfo(roomInfoDTO);
	}

	public int updateUserVictoryCount(String id) {
		return battleMapper.updateUserVictoryCount(id);
	}
}
