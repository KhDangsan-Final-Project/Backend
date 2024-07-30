package com.ms1.contoller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms1.service.ViewService;



@RestController
@RequestMapping("/ms1")
public class ViewController {
	
	private ViewService viewService;
	
	
	public ViewController(ViewService viewService) {
		this.viewService = viewService;
	}


    @PostMapping("/view/up")
    public ResponseEntity<Integer> updateViewCount(@RequestBody Map<String, Object> requestBody) {
        // 클라이언트의 IP 주소를 요청 객체에서 추출합니다.
        String ipAddress = (String) requestBody.get("ipAddress");

        // 추출된 IP 주소를 사용하여 조회수를 업데이트합니다.
        int updatedCount = viewService.updateViewCount(ipAddress);

        // 업데이트된 조회수를 클라이언트에게 응답으로 반환합니다.
        return ResponseEntity.ok(updatedCount);
    }


    @GetMapping("/view/count")
    public ResponseEntity<Integer> getViewCount() {
        int viewCount = viewService.getViewCount();
        return ResponseEntity.ok(viewCount);
    }
    
    @GetMapping("/view/rank")
    public String getRanking() {
    	
    	
    	
        return new String();
    }
    
}
