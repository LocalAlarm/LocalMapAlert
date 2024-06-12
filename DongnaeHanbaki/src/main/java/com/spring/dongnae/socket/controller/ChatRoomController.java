package com.spring.dongnae.socket.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.dongnae.socket.repo.ChatRoomRepository;
import com.spring.dongnae.socket.scheme.ChatRoom;


@RestController
public class ChatRoomController {
	
	@Autowired
	private final ChatRoomRepository chatRoomRepository;
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	public ChatRoomController(ChatRoomRepository chatRoomRepository) {
		this.chatRoomRepository = chatRoomRepository;
	}
	
	@PostMapping(value = "/api/getChatHistory", produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> getChatHistory(@RequestBody ChatRoom chatRoomId) {
	    try {
	        // 룸ID 찾기
	        Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findById(chatRoomId.getId());
	        if (optionalChatRoom.isPresent()) {
	            ChatRoom chatRoom = optionalChatRoom.get();
	            return ResponseEntity.ok(chatRoom);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chat room not found");
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
	    }
	}
}
