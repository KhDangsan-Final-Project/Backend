package com.ms3.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ms3.dto.PokemonDTO;
import com.ms3.dto.UserDTO;
import com.ms3.mapper.UserMapper;

@Service
public class UserService {
    private final UserMapper mapper;
    
    public UserService(UserMapper mapper) {
        this.mapper = mapper;
    }

    public int insertUser(UserDTO dto) {
        return mapper.insertUser(dto);
    }

    public UserDTO selectUser(String id, String password) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("password", password);
        return mapper.selectUser(map);
    }

    public UserDTO selectInfoUser(String id) {
        return mapper.selectInfoUser(id);
    }

    public int userUpdate(UserDTO userDTO) {
        return mapper.userUpdate(userDTO);
    }

	public List<UserDTO> searchFriend(String query, String userId) {
		return mapper.searchFriend(query, userId);
	}
	
	public boolean isUserExists(String userId) {
	    Integer count = mapper.isUserExists(userId);
	    return count != null && count > 0;
	}
	
	public int idcheck(String id) {
		return mapper.idcheck(id);
	}

	public int deleteUser(String id) {
		return mapper.deleteUser(id);
	}

	public String boardProfile(String id) {
		return mapper.boardProfile(id);
	}

	public List<String> getPokemonListById(String id) {
		return mapper.getPokemonListById(id);
	}

	public String changeEnglishName(String pokemonId) {
		return mapper.changeEnglishName(pokemonId);
	}

	public int updateGrantNo2(String id) {
		return mapper.updateGrantNo2(id);
	}
	
	public int updateGrantNo3(String id) {
		return mapper.updateGrantNo3(id);
	}
	
	public int updateGrantNo4(String id) {
		return mapper.updateGrantNo4(id);
	}
	
	public int updateGrantNo5(String id) {
		return mapper.updateGrantNo5(id);
	}
	
	public int updateGrantNo6(String id) {
		return mapper.updateGrantNo6(id);
	}



}