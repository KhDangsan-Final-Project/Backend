package com.ms2.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ms2.dto.UserDTO;

@Mapper
public interface BattleMapper {
    UserDTO selectUserVictoryCount(String id);

	int updateUserVictoryCount(String id);

	UserDTO selectUserPokemonNum(String id);
}
