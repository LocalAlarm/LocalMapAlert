package com.spring.dongnae.socket.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.spring.dongnae.socket.scheme.ChatMessage;
import com.spring.dongnae.socket.scheme.ChatMessageRepository;
import com.spring.dongnae.user.vo.UserVO;

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
    	System.out.println("afterConneoctionEstablished : " + getAuthenticatedUser().toString());
//        String token = getAuthenticatedUser().getToken();
    	String token = "dsa";
        sessions.put(session, token);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String token = sessions.get(session);
        String payload = message.getPayload();
        

        // 사용자 ID로 기존 메시지 문서 검색
        Optional<ChatMessage> optionalChatMessage = chatMessageRepository.findByUserId(token);
        ChatMessage chatMessage;

        if (optionalChatMessage.isPresent()) {
            // 기존 메시지 문서에 새로운 메시지 추가
            chatMessage = optionalChatMessage.get();
        } else {
            // 새로운 메시지 문서 생성
            chatMessage = new ChatMessage(token);
        }
        
        //현재 로그인한 유저의 토큰값 가져와서 hashmap으로 만들어야함.
        HashMap<String, String> messageMap = new HashMap<String, String>();
        messageMap.put("from", token);
        messageMap.put("message", payload);
        chatMessage.addMessage(messageMap);
        chatMessageRepository.save(chatMessage);

        // Broadcast the message to all connected clients
        for (WebSocketSession s : sessions.keySet()) {
            s.sendMessage(new TextMessage(token + ": " + payload));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    public UserVO getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
        	System.out.println("userVo tostring : " + ((UserVO) authentication.getPrincipal()).toString());
            return (UserVO) authentication.getPrincipal();
        }
        return null;
    }
}
