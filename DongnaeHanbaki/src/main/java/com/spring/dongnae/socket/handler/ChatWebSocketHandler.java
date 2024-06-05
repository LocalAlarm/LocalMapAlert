package com.spring.dongnae.socket.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.spring.dongnae.socket.scheme.ChatMessage;
import com.spring.dongnae.socket.scheme.ChatMessageRepository;

public class ChatWebSocketHandler extends TextWebSocketHandler {
	
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

        // Save the message to MongoDB
        ChatMessage chatMessage = new ChatMessage(userId, payload);
        chatMessageRepository.save(chatMessage);

        // Broadcast the message to all connected clients
        for (WebSocketSession s : sessions.keySet()) {
            s.sendMessage(new TextMessage(userId + ": " + payload));
        }
    }

    private String authenticateUser(WebSocketSession session) {
        // Implement your user authentication logic here
        return "sampleUserId"; // Replace with actual user ID
    }
}