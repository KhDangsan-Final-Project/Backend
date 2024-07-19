package com.ms2.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms2.dto.UserDTO;
import com.ms2.mapper.BattleMapper;

@Service
public class UserService {
	
	 private final BattleMapper battleMapper;
	 
	
    public UserService(BattleMapper battleMapper) {
		this.battleMapper = battleMapper;
	}


	// 이곳에 사용자 데이터를 저장하거나 처리하는 메서드를 추가합니다
    public void processUserData(String id, String nickname, Integer grantNo, String profile) {
        // 데이터 처리 로직을 여기에 구현합니다
        System.out.println("Processing user data:");
        System.out.println("ID: " + id);
        System.out.println("Nickname: " + nickname);
        System.out.println("Grant No: " + grantNo);
        System.out.println("Profile: " + profile);
    }


	public UserDTO selectUserVictoryCount(String id) {
		return battleMapper.selectUserVictoryCount(id);
	}



}
