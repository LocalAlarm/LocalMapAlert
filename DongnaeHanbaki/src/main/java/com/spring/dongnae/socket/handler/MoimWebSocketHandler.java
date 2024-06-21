package com.spring.dongnae.socket.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.dongnae.socket.scheme.UserRooms;
import com.spring.dongnae.socket.service.UserRoomsService;

@Component
public class MoimWebSocketHandler extends TextWebSocketHandler {
	
	@Autowired
	private UserRoomsService userRoomsService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	// In-memory storage for WebSocket sessions and user IDs
	private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("세션연결완료");
	    String token = (String) session.getAttributes().get("userToken");
	    if (token == null) {
	        session.close(CloseStatus.NOT_ACCEPTABLE);
	        throw new NullPointerException("User's token is null.");
	    }
	    sessions.put(token, session);
	    UserRooms userRooms = userRoomsService.getUserRoomsById(token);
	    System.out.println(userRooms);
	    String json = objectMapper.writeValueAsString(userRooms);
	    session.sendMessage(new TextMessage(json));
	}

	// 메시지를 입력하고 보냈을 때를 처리하는 코드
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		String token = (String) session.getAttributes().get("userToken");
		sessions.remove(token);
	}
}
