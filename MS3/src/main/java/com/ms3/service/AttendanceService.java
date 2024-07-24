package com.ms3.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ms3.dto.AttendanceDTO;
import com.ms3.mapper.AttendanceMapper;

@Service
public class AttendanceService {
	private AttendanceMapper attendanceMapper;

	public AttendanceService(AttendanceMapper attendanceMapper) {
		this.attendanceMapper = attendanceMapper;
	}

	public List<String> selectAllList(String id) {
		return attendanceMapper.selectAllList(id);
	}

	public int selectCount(String id) {
		return attendanceMapper.selectCount(id);
	}

	public int insertAttendance(String id) {
		return attendanceMapper.insertAttendance(id);
	}

	public int getPokemon809(String id) {
		return attendanceMapper.getPokemon809(id);
	}

	public int getPokemon248(String id) {
		return attendanceMapper.getPokemon248(id);		
	}

	public int getPokemon887(String id) {
		return attendanceMapper.getPokemon887(id);		
	}

	public int getPokemon635(String id) {
		return attendanceMapper.getPokemon635(id);		
	}

	public int getPokemon359(String id) {
		return attendanceMapper.getPokemon359(id);		
	}

	public int getPokemon448(String id) {
		return attendanceMapper.getPokemon448(id);		
	}
	
	
	
}
