package com.spring.dongnae.socket.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.dongnae.socket.scheme.TokenRequest;
import com.spring.dongnae.user.service.UserService;
import com.spring.dongnae.user.vo.UserVO;

@Component
@RestController
public class NicknameRestController {
	@Autowired
    private final UserService userService;
	
	private final ObjectMapper objectMapper = new ObjectMapper();

    public NicknameRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/api/getNickname", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> getNickname(@RequestBody TokenRequest tokenRequest) {
        System.out.println(tokenRequest.getToken());
        String nickname = userService.getUserByToken(tokenRequest.getToken()).getNickname();
        try {
            Map<String, String> jsonResponse = new HashMap<>();
            jsonResponse.put("nickname", nickname);
            String json = objectMapper.writeValueAsString(jsonResponse);
            System.out.println(json);
            return ResponseEntity.ok(json);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("{\"error\": \"Failed to generate JSON\"}");
        }
    }
    
    @PostMapping(value = "/api/getUserVOByToken", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> getUserVOByToken(@RequestBody String token) {
        UserVO userVO = userService.getUserByToken(token);
        try {
            String json = objectMapper.writeValueAsString(userVO);
            return ResponseEntity.ok(json);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("{\"error\": \"Failed to generate JSON\"}");
        }
    }
}