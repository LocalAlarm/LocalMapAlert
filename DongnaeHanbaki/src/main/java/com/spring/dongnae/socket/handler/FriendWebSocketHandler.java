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
import com.spring.dongnae.socket.dto.UserRoomsDto;
import com.spring.dongnae.socket.repo.ChatRoomRepository;
import com.spring.dongnae.socket.repo.UserRoomsRepository;
import com.spring.dongnae.socket.service.UserRoomsService;
import com.spring.dongnae.user.service.UserService;
import com.spring.dongnae.utils.auth.GetAuthenticInfo;

@Component
public class FriendWebSocketHandler extends TextWebSocketHandler {
	@Autowired
	private UserRoomsService userRoomsService;

	private final ObjectMapper objectMapper = new ObjectMapper();
	
	// In-memory storage for WebSocket sessions and user IDs
	private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

	
	// 처음에 Socket 연결시에 실행되는 코드
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String token = (String) session.getAttributes().get("userToken");
		if (token == null) session.close(CloseStatus.NOT_ACCEPTABLE);
		
		sessions.put(token, session);
		UserRoomsDto userRoomsDto = userRoomsService.getUserRoomsDtoById(token);
		String jsonUserRoomsDto = objectMapper.writeValueAsString(userRoomsDto);
		session.sendMessage(new TextMessage(jsonUserRoomsDto));
	}
	
	// 친구 요청을 받는 부분의 처리 메서드
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String token = message.getPayload();
		System.out.println(token);
		if (token != null) {
			session = sessions.get(token);
			if (session != null && session.isOpen()) {
				session.sendMessage(new TextMessage("gogo"));
			}
		}
	}


	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		String token = (String) session.getAttributes().get("userToken");
		sessions.remove(token);
	}
}
