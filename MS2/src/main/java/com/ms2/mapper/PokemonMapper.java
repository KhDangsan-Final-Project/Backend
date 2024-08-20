package com.ms2.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ms2.dto.PokemonDTO;
import com.ms2.dto.UserDTO;

@Mapper
public interface PokemonMapper {

	List<PokemonDTO> pokemonSelect(String koreanName);

	Integer selectUser(String id);

	int insertUser(String id);

	int updateUserVictoryCount(String id);

	int deleteUser(String id);

}
