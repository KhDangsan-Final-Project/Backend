package com.ms2.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms2.dto.PokemonDTO;
import com.ms2.dto.UserDTO;
import com.ms2.service.PokemonService;
import com.ms2.util.JwtUtil;

@RestController
@RequestMapping("/ms2")
public class PokemonContoller {

	private final PokemonService service;
	private final JwtUtil jwtUtil;
	
	public PokemonContoller(PokemonService service, JwtUtil jwtUtil) {
		super();
		this.service = service;
		this.jwtUtil = jwtUtil;
	}

	@GetMapping("/pokemon/search")
    public List<PokemonDTO> searchPokemon(String koreanName) {
        return service.pokemonSelect(koreanName);
    }
	
	@SuppressWarnings("unused")
	@PostMapping("/game/user")
	public ResponseEntity<Map<String, Object>> profileCheck(@RequestHeader("Authorization") String authorization) throws Exception {
	    if (authorization == null || !authorization.startsWith("Bearer ")) {
	        throw new Exception("계정을 확인해주세요!");
	    }

	    String token = authorization.substring(7);
	    String id = jwtUtil.extractId(token);
	    String nickname = jwtUtil.extractNickname(token);
	    String profile = jwtUtil.extractProfile(token);
	    int grantNo = jwtUtil.extractGrantNo(token);

	    Map<String, Object> map = new HashMap<String, Object>();
	    Integer matchWin = service.selectUser(id);
	    
//	    if (matchWin == null) {
//	    	service.insertUser(id);
//	        matchWin = 0;
//	    }

	    map.put("id", id);
	    map.put("nickname", nickname);
	    map.put("profile", profile);
	    map.put("grantNo", grantNo);
	    map.put("matchWin", matchWin);

	    return ResponseEntity.ok(map);
	}
	
	@GetMapping("/update")
	public ResponseEntity<?> updateUserVictoryCount(@RequestHeader("Authorization") String authorization) throws Exception {
    	
		if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new Exception("계정을 확인해주세요!");
        }

        String token = authorization.substring(7);
        String id = jwtUtil.extractId(token);
        
		int result = service.updateUserVictoryCount(id);
		
		return ResponseEntity.ok(result);
	}
	
	@PostMapping("/game/insert")
	public ResponseEntity<?> registerGameMatch(@RequestBody Map<String, String> param) {
		String id = param.get("id");
		
		service.insertUser(id);
		Map<String, String> msg = new HashMap<String, String>();
		
		msg.put("msg", "회원가입 성공!");
		return ResponseEntity.ok(msg);
	}
	
	@DeleteMapping("/game/delete")
	public ResponseEntity<?> deleteGameMatch(@RequestHeader("Authorization") String authorization) throws Exception {
		if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new Exception("계정을 확인해주세요!");
        }

        String token = authorization.substring(7);
        String id = jwtUtil.extractId(token);
		
		service.deleteUser(id);
		Map<String, String> msg = new HashMap<String, String>();
		msg.put("msg", "게임 데이터 삭제 성공");

		return ResponseEntity.ok(msg);
	}
	

}
