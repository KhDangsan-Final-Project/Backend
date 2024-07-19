package com.ms2.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ms2.service.BattleService;
import com.ms2.dto.UserDTO;

@Controller
public class MainController {

	private BattleService battleService;
	
	public MainController(BattleService battleService) {
		this.battleService = battleService;
	}
	
	

}
