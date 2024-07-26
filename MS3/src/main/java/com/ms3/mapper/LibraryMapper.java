package com.ms3.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ms3.dto.LibraryDTO;

@Mapper
public interface LibraryMapper {

	List<LibraryDTO> getPokemon(String userId);

}