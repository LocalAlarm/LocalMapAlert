package com.spring.dongnae.socket.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dongnae.socket.repo.ChatRoomRepository;
import com.spring.dongnae.socket.scheme.ChatRoom;

@Service
public class ChatRoomService {
	@Autowired
	private ChatRoomRepository chatRoomRepository;
	
	public Optional<ChatRoom> getChatRoomByUserIds(String userId) {
        return chatRoomRepository.findByUserIdsContaining(userId);
    }
	
	public Optional<ChatRoom> getChatRoomByUserIds(List<String> userIds) {
        return chatRoomRepository.findByUserIdsContaining(userIds);
    }
	
    public ChatRoom saveChatRoom(ChatRoom chatRoom) {
        return chatRoomRepository.save(chatRoom);
    }
    
    public ChatRoom createChatRoom(List<String> userIds) {
    	ChatRoom chatRoom = new ChatRoom();
    	chatRoom.setUserIds(userIds);
    	return chatRoomRepository.save(chatRoom);
    }
}
