package com.ms3.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ms3.dto.PokemonDTO;

@Mapper
public interface PokemonMapper {

	List<PokemonDTO> pokemonSelect(String koreanName);

}
