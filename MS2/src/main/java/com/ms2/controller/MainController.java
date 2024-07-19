package com.ms2.controller;

import com.ms2.dto.UserDTO;
import com.ms2.event.UserConnectedEvent;
import com.ms2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

@Controller
public class MainController {

    private String userId;

    @Autowired
    private UserService userService;

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

    public void someOtherMethod() {
        System.out.println("User ID in someOtherMethod: " + userId);
        try {
            // userId를 사용하여 사용자 정보를 조회합니다.
            UserDTO user = userService.selectUserVictoryCount(userId);
            if (user != null) {
                System.out.println("User Info: " + user.toString()); // UserDTO의 toString() 메서드 호출
            } else {
                System.out.println("User not found for ID: " + userId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred while fetching user information.");
        }
    }
}
