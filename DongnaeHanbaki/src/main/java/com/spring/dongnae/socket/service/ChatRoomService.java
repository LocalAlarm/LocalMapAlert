package com.spring.dongnae.socket.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dongnae.socket.dto.MessageDto;
import com.spring.dongnae.socket.error.AccessDenied;
import com.spring.dongnae.socket.repo.ChatRoomRepository;
import com.spring.dongnae.socket.scheme.ChatRoom;
import com.spring.dongnae.socket.scheme.Message;
import com.spring.dongnae.user.service.UserService;
import com.spring.dongnae.user.vo.UserVO;
import com.spring.dongnae.utils.auth.GetAuthenticInfo;

@Service
public class ChatRoomService {
	@Autowired
	private ChatRoomRepository chatRoomRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private GetAuthenticInfo getAuthenticInfo;
	
    public ChatRoom saveChatRoom(ChatRoom chatRoom) {
        return chatRoomRepository.save(chatRoom);
    }
    
    public ChatRoom createChatRoom() {
    	ChatRoom chatRoom = new ChatRoom();
    	return chatRoomRepository.save(chatRoom);
    }
    
    
    public List<String> addMessage(Message newMessage) {
    	try {
    		ChatRoom chatRoom = chatRoomRepository.findById(newMessage.getRoomId()).orElseThrow(() -> new RuntimeException("ChatRoom not found"));
    		System.out.println("chatROom: " + chatRoom);
    		chatRoom.addMessage(newMessage);
    		return chatRoom.getUserTokens();
    	} catch(Exception e) {
    		return null;
    	}
    }
    
    public List<MessageDto> getChatHistory(String ChatRoomId) {
    	try {
    		ChatRoom chatRoom = chatRoomRepository.findById(ChatRoomId).orElseThrow(() -> new RuntimeException("ChatRoom not found"));
    		if (!chatRoom.getUserTokens().contains(getAuthenticInfo.GetToken())) {
    			throw new AccessDenied("접근 권한이 없습니다.");
    		}
    		List<Message> messages = chatRoom.getMessages();
    		if (!messages.isEmpty()) {
    			return convertMessagesToDto(messages);
    		}
    		return null;
    	} catch(Exception e) {
    		return null;
    	}
    }
    
    public List<MessageDto> convertMessagesToDto(List<Message> messages) {
        return messages.stream()
                .map(this::transferDto)
                .collect(Collectors.toList());
    }
   
    public MessageDto transferDto(Message message) {
    	try {
            UserVO userVO = userService.getUserByToken(message.getSenderToken());
    		MessageDto messageDto = new MessageDto(userVO, message);
    		return messageDto;
    	} catch(Exception e) {
    		return null;
    	}
    }
}
