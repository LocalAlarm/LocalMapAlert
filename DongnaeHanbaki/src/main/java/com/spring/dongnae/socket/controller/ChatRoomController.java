package com.spring.dongnae.socket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dongnae.socket.dto.MessageDto;
import com.spring.dongnae.socket.scheme.ChatRoom;
import com.spring.dongnae.socket.service.ChatRoomService;


@RestController
@RequestMapping("/chat")
public class ChatRoomController {
	
	@Autowired
	private ChatRoomService chatRoomService;
	
	@PostMapping(value = "/getChatHistory", produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> getChatHistory(@RequestBody ChatRoom chatRoomId) {
	    try {
	    	List<MessageDto> chatHistory = chatRoomService.getChatHistory(chatRoomId.getId());
	    	return ResponseEntity.ok(chatHistory);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
	    }
	}
}
