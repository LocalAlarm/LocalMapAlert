  package com.spring.dongnae.socket.handler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.dongnae.socket.dto.MessageDto;
import com.spring.dongnae.socket.scheme.Message;
import com.spring.dongnae.socket.service.ChatRoomService;
import com.spring.dongnae.user.service.UserService;

@Component
public class ChatListWebSocketHandler extends TextWebSocketHandler {

	@Autowired
	private UserService userService;
	@Autowired
	private ChatRoomService chatRoomService;

	private final ObjectMapper objectMapper = new ObjectMapper();
	
	// In-memory storage for WebSocket sessions and user IDs
	private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

	// 처음에 Socket 연결시에 실행되는 코드
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String token = (String) session.getAttributes().get("userToken");
		if (token != null) {
			sessions.put(token, session);
		} else {
			session.close(CloseStatus.NOT_ACCEPTABLE);
		}
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String token = (String) session.getAttributes().get("userToken");
		
		if (token == null) {
			session.close(CloseStatus.NOT_ACCEPTABLE);
		}
		
		String payload = message.getPayload();
		Message newMessage = objectMapper.readValue(payload, Message.class); // Message.class - 받아온 데이터를 message클래스로 변환시킴
		newMessage.setSenderToken(token);
		List<String> userTokens = chatRoomService.addMessage(newMessage);
		MessageDto messageDto = chatRoomService.transferDto(newMessage);
		String jsonMessageDto = objectMapper.writeValueAsString(messageDto);
		for (String userToken: userTokens) {
			session = sessions.get(userToken);
			if (session != null && session.isOpen()) {
				session.sendMessage(new TextMessage(jsonMessageDto));
			}
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		String token = (String) session.getAttributes().get("userToken");
		sessions.remove(token);
	}
}
