package com.spring.dongnae.socket.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.spring.dongnae.socket.repo.ChatRoomRepository;
import com.spring.dongnae.socket.scheme.ChatRoom;
import com.spring.dongnae.socket.scheme.UserRooms;

@Service
public class ChatRoomService {
	@Autowired
	private ChatRoomRepository chatRoomRepository;
	
	// In-memory storage for WebSocket sessions and user IDs
	private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

	public Optional<ChatRoom> getChatRoomByUserIds(String userId) {
        return chatRoomRepository.findByUserIdsContaining(userId);
    }
	
	public Optional<ChatRoom> getChatRoomByUserIds(List<String> userIds) {
        return chatRoomRepository.findByUserIdsContaining(userIds);
    }
	
    public ChatRoom saveChatRoom(ChatRoom chatRoom) {
        return chatRoomRepository.save(chatRoom);
    }
    
    public ChatRoom createChatRoom() {
    	ChatRoom chatRoom = new ChatRoom();
    	return chatRoomRepository.save(chatRoom);
    }
    
    public ChatRoom createChatRoom(List<UserRooms> userRooms) {
    	ChatRoom chatRoom = new ChatRoom();
    	chatRoom.setUserRooms(userRooms);
    	return chatRoomRepository.save(chatRoom);
    }

	// 세션 추가 - 연결
	public void addSession(String id, WebSocketSession session) {
		sessions.put(id, session);
	}
	
	// 세션삭제
	public void removeSession(String id) {
		sessions.remove(id);
	}

	// id통해서 세션 가져오기
	public WebSocketSession getSession(String id) {
		return sessions.get(id);
	}
}
