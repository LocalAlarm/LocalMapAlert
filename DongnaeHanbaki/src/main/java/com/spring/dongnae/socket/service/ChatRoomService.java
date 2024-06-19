package com.spring.dongnae.socket.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dongnae.socket.repo.ChatRoomRepository;
import com.spring.dongnae.socket.scheme.ChatRoom;
import com.spring.dongnae.socket.scheme.UserRooms;

@Service
public class ChatRoomService {
	@Autowired
	private ChatRoomRepository chatRoomRepository;
	
    public ChatRoom saveChatRoom(ChatRoom chatRoom) {
        return chatRoomRepository.save(chatRoom);
    }
    
    public ChatRoom createChatRoom() {
    	ChatRoom chatRoom = new ChatRoom();
    	return chatRoomRepository.save(chatRoom);
    }
    
//    public ChatRoom createChatRoom(List<UserRooms> userRooms) {
//    	ChatRoom chatRoom = new ChatRoom();
//    	chatRoom.set(userRooms);
//    	return chatRoomRepository.save(chatRoom);
//    }
}
