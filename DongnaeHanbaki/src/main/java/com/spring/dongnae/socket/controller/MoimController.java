package com.spring.dongnae.socket.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MoimController {
	
	@PostMapping("/api/createMoim")
	public ResponseEntity<String> createMoim(            
			@RequestParam("title") String title,
            @RequestParam("introduce") String introduce,
            @RequestPart(value = "profilePic", required = false) MultipartFile profilePic) {
		
		return ResponseEntity.ok("모임이 성공적으로 생성되었습니다.");
	}
}
