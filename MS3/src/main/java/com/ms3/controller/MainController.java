package com.ms3.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;

import com.ms3.dto.UserDTO;
import com.ms3.service.EmailService;
import com.ms3.service.TokenService;
import com.ms3.service.UserService;
import com.ms3.util.JwtUtil;


@RestController
@RequestMapping("/ms3")
public class MainController {

	private final UserService service;
    private final EmailService emailService;
    private final TokenService tokenService;
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	private final JwtUtil jwtUtil;
	
	public MainController(UserService service, JwtUtil jwtUtil, EmailService emailService, TokenService tokenService) {
		this.service = service;
		this.jwtUtil = jwtUtil;
		this.emailService = emailService;
		this.tokenService = tokenService;
	}
	

	@PostMapping("/user/select")
	public Map<String, Object> login(@RequestBody Map<String, String> param) {
		String id = param.get("id");
		String password = param.get("password");

		Map<String, Object> map = new HashMap<>();

		UserDTO user = service.selectUser(id, password);
		if (user != null) {
			String token = jwtUtil.generateToken(user.getId(), user.getNickname(), user.getGrantNo(),
					user.getProfile());
			map.put("msg", "로그인 성공");
			map.put("result", true);
			map.put("token", token);
		} else {
			map.put("msg", "로그인 실패");
			map.put("result", false);
		}

		return map;
	}
	
	@PostMapping("/user/insert")
	public Map<String, Object> insertUser(@RequestBody Map<String, String> param) {
		UserDTO dto = new UserDTO();
		dto.setId(param.get("id"));
		dto.setPassword(param.get("password"));
		dto.setEmail(param.get("email"));
		dto.setName(param.get("name"));
		dto.setNickname(param.get("nickname"));
	    dto.setProfile(param.get("profile") != null ? param.get("profile") : "");
		System.out.println(dto);
		Map<String, Object> map = new HashMap<>();
		try {
			service.insertUser(dto);
			map.put("msg", "회원 등록 성공");
			map.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "회원 등록 실패");
			map.put("result", false);
		}
		return map;
	}

	@PostMapping("/user/idcheck")
	public Map<String, Object> idcheck(@RequestBody Map<String, String> payload) {
		String id = payload.get("id");
		int count = service.idcheck(id);
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("count", count);

		return map;
	}
  
	@PostMapping("/password-reset-request")
    public Map<String, Object> requestPasswordReset(@RequestBody Map<String, String> request) {
    	String email = request.get("email");
        Map<String, Object> map = new HashMap<>();
        
        try {
            // 비밀번호 재설정 토큰 생성
            String token = tokenService.createPasswordResetToken(email);
                
            // 이메일 전송
            emailService.sendPasswordResetEmail(email, token);
                
            map.put("message", "비밀번호 재설정 이메일이 전송되었습니다.");
            map.put("result", true);
        }catch (Exception e) {
            logger.error("Error during password reset request", e);
            map.put("message", "이메일 확인 바랍니다.");
            map.put("result", false);
        }
        return map;
    }

    @PostMapping("/password-reset")
    public Map<String, Object> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");
        System.out.println("받은토큰 : " + token);
        Map<String, Object> map = new HashMap<>();
        try {
            tokenService.resetPassword(token, newPassword);
            map.put("msg", "비밀번호가 성공적으로 재설정되었습니다.");
            map.put("result", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "비밀번호 재설정 실패");
            map.put("result", false);
        }
        return map;
    }
    
    @GetMapping("/rankcheck")
    public ResponseEntity<Integer> RankCheck(@RequestHeader("Authorization") String authorization) throws Exception {
    	if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new Exception("계정을 확인해주세요!");
        }

        // 토큰에서 사용자 ID 추출
        String token = authorization.substring(7);
        int grantNo = jwtUtil.extractGrantNo(token);
        
    	return ResponseEntity.ok(grantNo);
    }


}



