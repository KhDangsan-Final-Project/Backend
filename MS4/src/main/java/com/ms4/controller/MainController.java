package com.ms4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/userList")
	public String main () {
		return "user_list";
	}
}
