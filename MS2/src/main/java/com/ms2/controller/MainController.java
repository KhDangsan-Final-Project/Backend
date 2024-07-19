package com.ms2.controller;

import com.ms2.dto.UserDTO;
import com.ms2.event.UserConnectedEvent;
import com.ms2.service.UserService; // 서비스 클래스 import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users") // base path 설정
public class MainController {

    private String userId;

    @Autowired
    private UserService userService; // UserService 주입

    @EventListener
    public void handleUserConnected(UserConnectedEvent event) {
        this.userId = event.getId();
        System.out.println("User connected with ID//mainController: " + userId);

        // ID를 설정한 후 someOtherMethod를 호출합니다.
        someOtherMethod();
    }

    public String getUserId() {
        return userId;
    }

    // 다른 메서드에서 userId를 사용하는 예제 메서드
    public void someOtherMethod() {
        System.out.println("User ID in someOtherMethod: " + userId);
        // 여기에 userId를 사용하는 로직을 추가합니다.
    }

    // userId를 기반으로 DB에서 정보를 조회하고 JSON 형태로 응답
    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@RequestParam String id) {
        // 서비스 메서드를 호출하여 사용자 정보를 조회합니다.
        try {
            // userService를 사용하여 ID로 사용자 정보를 조회합니다.
            UserDTO user = userService.selectUserVictoryCount(id);
            if (user != null) {
                return ResponseEntity.ok(user); // 사용자 정보를 JSON 형태로 반환
            } else {
                return ResponseEntity.status(404).body("User not found"); // 사용자 미발견 시
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal server error"); // 서버 오류 시
        }
    }
}
