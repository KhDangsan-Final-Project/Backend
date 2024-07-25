package com.ms3.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms3.dto.AttendanceDTO;
import com.ms3.service.AttendanceService;
import com.ms3.util.JwtUtil;

@RestController
@RequestMapping("/ms3")
public class AttendanceController {
	
	private AttendanceService attendanceService;
	private JwtUtil jwtUtil;

	public AttendanceController(AttendanceService attendanceService, JwtUtil jwtUtil) {
		this.attendanceService = attendanceService;
		this.jwtUtil = jwtUtil;
	}

	// 출석 리스트 출력
    @GetMapping("/attendance/list")
    public List<String> listAttendance(@RequestHeader("Authorization") String authorization) throws Exception {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new Exception("계정을 확인해주세요!");
        }

        // 토큰에서 사용자 ID 추출
        String token = authorization.substring(7);
        String id = jwtUtil.extractId(token);

        return attendanceService.selectAllList(id);
    }
	
	
	// 출석 도장
	@GetMapping("/attendance/add")
	public ResponseEntity<String> AddAttendance(@RequestHeader("Authorization") String authorization) throws Exception {
		if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new Exception("계정을 확인해주세요!");
        }

        // 토큰에서 사용자 ID 추출
        String token = authorization.substring(7);
        String id = jwtUtil.extractId(token);
        
        int insertResult = attendanceService.insertAttendance(id);
		
        if(insertResult == 0) {
        	return ResponseEntity.ok("이미 출석체크를 하셨습니다.");
        }
        
        
		int count = attendanceService.selectCount(id);
		
		switch (count) {
		case 5:
			attendanceService.getPokemon809(id);
			break;
		case 10:
			attendanceService.getPokemon248(id);
			break;
		case 15:
			attendanceService.getPokemon887(id);
			break;
		case 20:
			attendanceService.getPokemon635(id);
			break;
		case 25:
			attendanceService.getPokemon359(id);
			break;
		case 30:
			attendanceService.getPokemon448(id);
			break;
		}
		
		return ResponseEntity.ok("출석체크 완료!");
	}
}
