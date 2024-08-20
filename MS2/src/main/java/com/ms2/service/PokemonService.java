package com.ms2.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ms2.dto.PokemonDTO;
import com.ms2.dto.UserDTO;
import com.ms2.mapper.PokemonMapper;


@Service
public class PokemonService {
    private final PokemonMapper mapper;
    
	public PokemonService(PokemonMapper mapper) {
		this.mapper = mapper;
	}


	public List<PokemonDTO> pokemonSelect(String koreanName) {
		return mapper.pokemonSelect(koreanName);
	}


	public int selectUser(String id) {
		return mapper.selectUser(id);
	}


	public int insertUser(String id) {	
		return mapper.insertUser(id);
	}


	public int updateUserVictoryCount(String id) {
		return mapper.updateUserVictoryCount(id);
	}


	public int deleteUser(String id) {
		return mapper.deleteUser(id);
	}



}
