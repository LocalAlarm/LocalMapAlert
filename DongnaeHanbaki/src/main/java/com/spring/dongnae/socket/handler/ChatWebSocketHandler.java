package com.spring.dongnae.socket.handler;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.spring.dongnae.socket.scheme.ChatMessage;
import com.spring.dongnae.socket.scheme.ChatMessageRepository;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    
    @Autowired
    private final ChatMessageRepository chatMessageRepository;

    // In-memory storage for WebSocket sessions and user IDs
    private final Map<WebSocketSession, String> sessions = new ConcurrentHashMap<>();

    public ChatWebSocketHandler(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Here you would authenticate the user and fetch their user ID
        String userId = authenticateUser(session);
        sessions.put(session, userId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String userId = sessions.get(session);
        String payload = message.getPayload();

        // 사용자 ID로 기존 메시지 문서 검색
        Optional<ChatMessage> optionalChatMessage = chatMessageRepository.findByUserId(userId);
        ChatMessage chatMessage;

        if (optionalChatMessage.isPresent()) {
            // 기존 메시지 문서에 새로운 메시지 추가
            chatMessage = optionalChatMessage.get();
        } else {
            // 새로운 메시지 문서 생성
            chatMessage = new ChatMessage(userId);
        }
        
        chatMessage.addMessage(payload);
        chatMessageRepository.save(chatMessage);

        // Broadcast the message to all connected clients
        for (WebSocketSession s : sessions.keySet()) {
            s.sendMessage(new TextMessage(userId + ": " + payload));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    private String authenticateUser(WebSocketSession session) {
        // Implement your user authentication logic here
        return "sampleUserId"; // Replace with actual user ID
    }
}
