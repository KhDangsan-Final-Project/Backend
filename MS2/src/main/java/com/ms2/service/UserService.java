package com.ms2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public int updateUserVictoryCount(String id) {
		return battleMapper.updateUserVictoryCount(id);
	}

	public UserDTO selectUserPokemonNum(String id) {
		return battleMapper.selectUserPokemonNum(id);
	}


}
