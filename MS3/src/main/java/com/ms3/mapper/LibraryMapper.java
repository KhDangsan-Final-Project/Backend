package com.ms3.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ms3.dto.FriendDTO;
import com.ms3.dto.LibraryDTO;

@Mapper
public interface LibraryMapper {

	List<LibraryDTO> getPokemon(String userId);
}