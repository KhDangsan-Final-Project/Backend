	package com.ms4.mapper;
	
	import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ms4.dto.RankDTO;
import com.ms4.dto.UserDTO;
	
	@Mapper
	public interface UserMapper {
		List<UserDTO> selectAllUser();
		int updateUser(UserDTO dto);
		int deleteUser(String id);
		List<RankDTO> selectAllRank();
		List<RankDTO> selectRankUser(int[] grantNo);
		List<UserDTO> searchUser(Map<String, String> param);
		int updateRank(Map<String, String> param);
	}
