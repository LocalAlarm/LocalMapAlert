package com.spring.dongnae.socket.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.dongnae.socket.listener.WebSocketSessionManager;

@Component
public class MoimWebSocketHandler extends TextWebSocketHandler {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
    private WebSocketSessionManager sessionManager;

	// 처음에 Socket 연결시에 실행되는 코드
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
	}

	// 메시지를 입력하고 보냈을 때를 처리하는 코드
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		
	}
}
