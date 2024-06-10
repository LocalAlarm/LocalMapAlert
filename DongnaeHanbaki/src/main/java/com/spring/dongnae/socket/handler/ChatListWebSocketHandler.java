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
import com.spring.dongnae.socket.repo.ChatRoomRepository;
import com.spring.dongnae.socket.repo.UserRoomsRepository;
import com.spring.dongnae.socket.scheme.ChatRoom;
import com.spring.dongnae.socket.scheme.Message;
import com.spring.dongnae.socket.scheme.UserRooms;
import com.spring.dongnae.user.service.UserService;
import com.spring.dongnae.user.vo.UserVO;

@Component
public class ChatListWebSocketHandler extends TextWebSocketHandler {
    
    @Autowired
    private final UserService userService;
    @Autowired
    private final UserRoomsRepository userRoomsRepository;
    @Autowired
    private final ChatRoomRepository chatRoomRepository;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    // In-memory storage for WebSocket sessions and user IDs
    private final Map<WebSocketSession, String> sessions = new ConcurrentHashMap<>();

    public ChatListWebSocketHandler(UserService userService, UserRoomsRepository userRoomsRepository, ChatRoomRepository chatRoomRepository) {
		this.userService = userService;
		this.userRoomsRepository = userRoomsRepository;
		this.chatRoomRepository = chatRoomRepository;
    }
    
    // 처음에 Socket 연결시에 실행되는 코드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	String token = (String) session.getAttributes().get("userToken");
    	 if (token != null) {
             sessions.put(session, token);
             // 사용자 ID로 기존 메시지 문서 검색
             Optional<UserRooms> optionalUserRooms = userRoomsRepository.findByToken(token); // 여기서부터 시작
             UserRooms userRooms = new UserRooms();
             // 기존 채팅방이 있으면 해당 데이터를 가져옴
             if (optionalUserRooms.isPresent()) { //몽고디비에서 토큰에 해당되는게 있는지 체크 없으면 else로 감
            	 userRooms = optionalUserRooms.get();
            	 for (String chatRoomId : userRooms.getChatRoomIds()) {
            		 Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(chatRoomId);
            		 ChatRoom chatRoom = chatRoomOptional.get();
            		 String json = objectMapper.writeValueAsString(chatRoom);
            		 session.sendMessage(new TextMessage(json));
            	 }
             } else {
                 // 새로운 메시지 문서 생성
            	 userRooms = new UserRooms(userService.getUserByToken(token).getEmail(), token);
            	 userRoomsRepository.save(userRooms);
             }
         } else {
             session.close(CloseStatus.NOT_ACCEPTABLE);
         }
    }

    //메세지를 입력하고 보냈을때를 처리하는 코드
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String token = sessions.get(session);
        String payload = message.getPayload();
        System.out.println("payload : " + payload);
        Message newMessage = objectMapper.readValue(payload, Message.class);
        newMessage.setSenderToken(token);
        Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findById(newMessage.getRoomId());
        ChatRoom chatRoom = optionalChatRoom.get();
        chatRoom.addMessage(newMessage);
        chatRoomRepository.save(chatRoom);
        
//        // JSON 메시지 생성
//        String jsonMessage = objectMapper.writeValueAsString(transToJson(userService.getUserByToken(token), payload));
//
//        // Broadcast the message to all connected clients 지금은 전체채팅 개념임 -> 개인채팅으로 바꿀계획.
//        for (WebSocketSession s : sessions.keySet()) {
//            s.sendMessage(new TextMessage(jsonMessage));
//        }
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
