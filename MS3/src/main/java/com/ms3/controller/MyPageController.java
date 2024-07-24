package com.ms3.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms3.dto.FriendDTO;
import com.ms3.dto.MailDTO;
import com.ms3.dto.UserDTO;
import com.ms3.service.MailService;
import com.ms3.service.FriendService;
import com.ms3.service.UserService;
import com.ms3.util.JwtUtil;

@RestController
@RequestMapping("/ms3")
public class MyPageController {

    private final FriendService friendService;
    private final UserService service;
    private final JwtUtil jwtUtil;
    private final MailService mailService;

    public MyPageController(FriendService friendService, UserService service, JwtUtil jwtUtil,
			MailService mailService) {
		this.friendService = friendService;
		this.service = service;
		this.jwtUtil = jwtUtil;
		this.mailService = mailService;
	}
    
	@GetMapping("/mypage")
    public UserDTO selectInfoUser(@RequestParam String token) {
        String userId = jwtUtil.extractId(token);
        return service.selectInfoUser(userId);
    }

    @PutMapping("/mypage/update")
    public Map<String, Object> userUpdate(@RequestBody UserDTO userDTO) {
        Map<String, Object> map = new HashMap<>();
        int result = service.userUpdate(userDTO);
        map.put("status", result > 0 ? "success" : "fail");
        return map;
    }

    @PostMapping("/friend/add")
    public Map<String, Object> addFriendRequest(@RequestBody FriendDTO friendDTO, @RequestParam String token) {
        Map<String, Object> map = new HashMap<>();
        String userId = jwtUtil.extractId(token);
        friendDTO.setUserId(userId);
        friendDTO.setStatus("pending");
        int result = friendService.addFriendRequest(friendDTO);
        map.put("status", result > 0 ? "success" : "fail");
        return map;
    }

    @GetMapping("/friend")
    public List<FriendDTO> selectFriend(@RequestParam String token) {
        String userId = jwtUtil.extractId(token);
        return friendService.selectFriend(userId);
    }

    @GetMapping("/friend/search")
    public List<UserDTO> searchFriend(@RequestParam String query, @RequestParam String token) {
        String userId = jwtUtil.extractId(token);
        return service.searchFriend(query, userId);
    }

    @GetMapping("/friend/request")
    public List<FriendDTO> requestFriend(@RequestParam String token) {
        String userId = jwtUtil.extractId(token);
        return friendService.requestFriend(userId);
    }
    
    @PutMapping("/friend/accept")
    public Map<String, Object> acceptFriendRequest(@RequestBody FriendDTO friendDTO, @RequestParam String token) {
        Map<String, Object> map = new HashMap<>();
        String userId = jwtUtil.extractId(token);
        friendDTO.setFriendId(userId);
        friendDTO.setStatus("accepted");
        int result = friendService.acceptFriendRequest(friendDTO);
        map.put("status", result > 0 ? "success" : "fail");
        return map;
    }

    @DeleteMapping("/friend/reject")
    public Map<String, Object> rejectFriendRequest(@RequestBody FriendDTO friendDTO, @RequestParam String token) {
        Map<String, Object> map = new HashMap<>();
        String userId = jwtUtil.extractId(token);
        friendDTO.setFriendId(userId);
        int result = friendService.rejectFriendRequest(friendDTO);
        map.put("status", result > 0 ? "success" : "fail");
        return map;
    }

    
    @DeleteMapping("/friend/delete")
    public Map<String, Object> deleteFriend(@RequestBody FriendDTO friendDTO, @RequestParam String token) {
        Map<String, Object> map = new HashMap<>();
        String userId = jwtUtil.extractId(token);
        friendDTO.setUserId(userId);
        int result = friendService.deleteFriend(friendDTO);
        map.put("status", result > 0 ? "success" : "fail");
        System.out.println(result);
        return map;
    }

    @GetMapping("/mail")
    public Map<String, Object> selectMail(@RequestParam String token) {
        String userId = jwtUtil.extractId(token);
        Map<String, Object> map = new HashMap<String, Object>();
        List<MailDTO> result = mailService.selectMail(userId);
        System.out.println(result);
        map.put("result", result);
        return map;
    }

    @PostMapping("/mail/send")
    public Map<String, Object> sendMail(@RequestBody MailDTO mailDTO, @RequestParam String token) {
        String userId = jwtUtil.extractId(token);
        mailDTO.setSender(userId);
        mailDTO.setTimestamp(LocalDateTime.now());
        Map<String, Object> map = new HashMap<>();
        try {
            if (!service.isUserExists(mailDTO.getReceiver())) {
                map.put("status", "fail");
                map.put("message", "아이디를 확인해주세요");
                return map;
            }
            int result = mailService.sendMail(mailDTO);
            map.put("status", result > 0 ? "success" : "fail");
            if (result <= 0) {
                map.put("message", "메일 전송에 실패했습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "fail");
            map.put("message", "서버 오류가 발생했습니다.");
        }
        return map;
    }


    @DeleteMapping("/mail/delete")
    public Map<String, Object> deleteMail(@RequestParam int mailNo, @RequestParam String token) {
        Map<String, Object> map = new HashMap<>();
        String userId = jwtUtil.extractId(token);
        try {
            int result = mailService.deleteMail(mailNo);
            map.put("status", result > 0 ? "success" : "fail");
            map.put("message", result > 0 ? "메일이 성공적으로 삭제되었습니다." : "메일 삭제에 실패했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "fail");
            map.put("message", "서버 오류가 발생했습니다.");
        }
        return map;
    }

    @GetMapping("/mail/detail")
    public MailDTO selectMailDetail(@RequestParam int mailNo, @RequestParam String token) {
        String userId = jwtUtil.extractId(token);
        return mailService.selectMailDetail(mailNo, userId);
    }
        
    
    
}