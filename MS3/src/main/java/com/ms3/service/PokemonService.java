package com.ms3.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ms3.dto.PokemonDTO;
import com.ms3.mapper.PokemonMapper;


@Service
public class PokemonService {
    private final PokemonMapper mapper;
    
	public PokemonService(PokemonMapper mapper) {
		this.mapper = mapper;
	}


	public List<PokemonDTO> pokemonSelect(String koreanName) {
		return mapper.pokemonSelect(koreanName);
	}

}
