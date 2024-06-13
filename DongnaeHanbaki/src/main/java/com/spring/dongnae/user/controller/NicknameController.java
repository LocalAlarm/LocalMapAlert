package com.spring.dongnae.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dongnae.socket.scheme.TokenRequest;
import com.spring.dongnae.user.service.UserService;

@RestController
public class NicknameController {
	@Autowired
	private final UserService userService;
	
	public NicknameController(UserService userService) {
		this.userService = userService;
	}
	
    @PostMapping("/api/getNickname")
    public ResponseEntity<String> getNickname(@RequestBody TokenRequest tokenRequest) {
        String nickname = userService.getUserByToken(tokenRequest.getToken()).getNickname();
        String jsonResponse = "{\"nickname\": \"" + nickname + "\"}";
        // ResponseEntity로 JSON 형식의 데이터 응답
        return ResponseEntity.ok(jsonResponse);
    }
}
