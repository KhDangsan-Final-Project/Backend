package com.ms3.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ms3.dto.AttendanceDTO;

@Mapper
public interface AttendanceMapper {

	List<String> selectAllList(String id);

	int selectCount(String id);

	int insertAttendance(String id);

	int getPokemon809(String id);

	int getPokemon248(String id);

	int getPokemon887(String id);

	int getPokemon635(String id);

	int getPokemon359(String id);

	int getPokemon448(String id);


}
