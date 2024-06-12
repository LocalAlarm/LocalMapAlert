package com.spring.dongnae.socket.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dongnae.socket.scheme.ChatRoom;
@RestController
public class ChatRoomController {
	
	
	
    @PostMapping("/api/getChatHistory")
	public ChatRoom getChatHistory(@RequestBody ChatRoom chatRoom) {
		return null;
	}
}
