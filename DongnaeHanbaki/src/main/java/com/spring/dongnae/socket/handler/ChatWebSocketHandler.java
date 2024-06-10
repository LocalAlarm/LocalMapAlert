package com.spring.dongnae.socket.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.dongnae.socket.scheme.ChatMessage;
import com.spring.dongnae.socket.scheme.ChatMessageRepository;
import com.spring.dongnae.user.service.UserService;
import com.spring.dongnae.user.vo.UserVO;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    
    @Autowired
    private final ChatMessageRepository chatMessageRepository;
    @Autowired
    private final UserService userService;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    // In-memory storage for WebSocket sessions and user IDs
    private final Map<WebSocketSession, String> sessions = new ConcurrentHashMap<>();

    public ChatWebSocketHandler(ChatMessageRepository chatMessageRepository, UserService userService) {
        this.chatMessageRepository = chatMessageRepository;
		this.userService = userService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	String token = (String) session.getAttributes().get("userToken");
    	 if (token != null) {
             sessions.put(session, token);
             // 사용자 ID로 기존 메시지 문서 검색
             Optional<ChatMessage> optionalChatMessage = chatMessageRepository.findByUserId(token);
             ChatMessage chatMessage;
             // 기존 채팅방이 있으면 해당 데이터를 가져옴
             if (optionalChatMessage.isPresent()) { //몽고디비에서 토큰에 해당되는게 있는지 체크 없으면 else로 감
                 chatMessage = optionalChatMessage.get();
                 for (Map<String, String> messageMap : chatMessage.getMessages()) {
                 	String fromToken = messageMap.get("from");
                 	String dbMessage = messageMap.get("message");
                 	String jsonMessage = objectMapper.writeValueAsString(transToJson(userService.getUserByToken(fromToken), dbMessage));
                 	session.sendMessage(new TextMessage(jsonMessage));
                 }
             } else {
                 // 새로운 메시지 문서 생성
                 chatMessage = new ChatMessage(token);
                 chatMessageRepository.save(chatMessage);
             }
         } else {
             session.close(CloseStatus.NOT_ACCEPTABLE);
         }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String token = sessions.get(session);
        String payload = message.getPayload();
        Optional<ChatMessage> optionalChatMessage = chatMessageRepository.findByUserId(token);
        ChatMessage newMessage;
        newMessage = optionalChatMessage.get();
        
        
        //현재 로그인한 유저의 토큰값 가져와서 hashmap으로 만들어야함.
        HashMap<String, String> messageMap = new HashMap<String, String>();
        messageMap.put("from", token);
        messageMap.put("message", payload);
        newMessage.addMessage(messageMap);
        chatMessageRepository.save(newMessage);
        
        // JSON 메시지 생성
        String jsonMessage = objectMapper.writeValueAsString(transToJson(userService.getUserByToken(token), payload));

        // Broadcast the message to all connected clients
        for (WebSocketSession s : sessions.keySet()) {
            s.sendMessage(new TextMessage(jsonMessage));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }
    
    private Map<String,String> transToJson(UserVO userVO, String message) {
    	Map<String, String> jsonMap = new HashMap<>();
    	jsonMap.put("fromEmail", userVO.getEmail());
    	jsonMap.put("fromNickName", userVO.getNickname());
    	jsonMap.put("message", message);
    	return jsonMap;
    }
}
