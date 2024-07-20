package com.ms1.contoller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ms1.service.AiService;
import com.ms1.util.JwtUtil;

@RestController
@RequestMapping("/ms1")
public class AiContoller {

	private AiService aiService;
	private JwtUtil jwtUtil;

	public AiContoller(AiService aiService, JwtUtil jwtUtil) {
		this.aiService = aiService;
		this.jwtUtil = jwtUtil;
	}


	// AI 카드 요청
	@PostMapping("/detect")
    public ResponseEntity<Map<String, Object>> uploadImage(@RequestPart("image") MultipartFile file, @RequestHeader("Authorization") String authorizationHeader) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		// JWT 토큰에서 사용자 ID 추출
		String token = authorizationHeader.replace("Bearer ", "");
	    String id = jwtUtil.extractId(token);
		
		// ---!중! 요청 확인 !요!--- 
		// 한달에 1000번 요청은 무료로 사용 가능
		// 한달 기준으로 요청 횟수가 800번 이상이면 더이상 요청을 받지 않음
		
		// 리퀘스트 요청 몇번했는지 확인
		int request = aiService.requestImg();
		System.out.println(request);

		// 리퀘스트 요청 횟수에 맞춰서 요청 취소시킬지 성공시킬지 반환
		if(request > 800) {
			String msg = "요청량 초과로 인해 사용 불가능합니다.😪";
			map.put("msg", msg);
			return ResponseEntity.ok(map);
		} else {
			// 리퀘스트 요청 데이터베이스에 입력
			aiService.insertRequest();
			
			// 텍스트 추출
			String detectedText = aiService.uploadImage(file);
			
			// 데이터베이스에 저장
			aiService.insertDetected(detectedText, id);
			
			// 데이터베이스에 저장한 id 값으로 텍스트에 포켓몬이 있는지 가져오기
			List<String> detectedResult = aiService.selectDetected(id);
			map.put("detectedResult", detectedResult);

			return ResponseEntity.ok(map);
		}
    }
	
	
	
	
	
}
