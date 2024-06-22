package com.spring.dongnae.socket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dongnae.socket.dto.MessageDto;
import com.spring.dongnae.socket.repo.ChatRoomRepository;
import com.spring.dongnae.socket.scheme.ChatRoom;
import com.spring.dongnae.socket.scheme.Message;
import com.spring.dongnae.user.service.UserService;
import com.spring.dongnae.user.vo.UserVO;

@Service
public class ChatRoomService {
	@Autowired
	private ChatRoomRepository chatRoomRepository;
	@Autowired
	private UserService userService;
	
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
		   chatRoom.addMessage(newMessage);
		   return chatRoom.getUserTokens();
	   } catch(Exception e) {
		   return null;
	   }
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
