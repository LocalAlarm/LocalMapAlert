package com.spring.dongnae.socket.handler;

import java.util.ArrayList;
import java.util.List;
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
	private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

	public ChatListWebSocketHandler(UserService userService, UserRoomsRepository userRoomsRepository,
			ChatRoomRepository chatRoomRepository) {
		this.userService = userService;
		this.userRoomsRepository = userRoomsRepository;
		this.chatRoomRepository = chatRoomRepository;
	}

	// 처음에 Socket 연결시에 실행되는 코드
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String id = (String) session.getAttributes().get("id"); // token -> id 로 바
		if (id != null) {
			sessions.put(id, session);
			// 사용자 ID로 기존 메시지 문서 검색
			Optional<UserRooms> optionalUserRooms = userRoomsRepository.findById(id);
			UserRooms userRooms = new UserRooms();
			// 기존 채팅방이 있으면 해당 데이터를 가져옴
			if (optionalUserRooms.isPresent()) {
				userRooms = optionalUserRooms.get();
				String json = objectMapper.writeValueAsString(userRooms);
				session.sendMessage(new TextMessage(json));
			} else {
				// 새로운 메시지 문서 생성
				userRooms = new UserRooms(userService.getUserByToken(id));
				userRoomsRepository.save(userRooms);
			}
		} else {
			session.close(CloseStatus.NOT_ACCEPTABLE);
		}
	}

	// 메시지를 입력하고 보냈을 때를 처리하는 코드
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String token = (String) session.getAttributes().get("userToken");
		String payload = message.getPayload();
		System.out.println("payload : " + payload);
		Message newMessage = objectMapper.readValue(payload, Message.class); // Message.class - 받아온 데이터를 message클래스로 변환시킴
		newMessage.setSenderToken(token);

		Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findById(newMessage.getRoomId());
		if (optionalChatRoom.isPresent()) {
			ChatRoom chatRoom = optionalChatRoom.get();
			try {
			    chatRoom.addMessage(newMessage);
			} catch (NullPointerException e) {
				chatRoom.setMessages(new ArrayList<Message>());
				chatRoom.addMessage(newMessage);
			}
			chatRoomRepository.save(chatRoom);
			
			// JSON 메시지 생성
			String jsonMessage = objectMapper.writeValueAsString(newMessage);

			// 특정 채팅방에 속한 사용자들에게만 메시지를 전송
			List<String> userTokens = chatRoom.getUserIds(); // 사용자 토큰들 가져오기
			for (String chatRoomUser : userTokens) {
				session = sessions.get(chatRoomUser); // 세션 정보 가져오기
				if (session != null && session.isOpen()) { // null이 아니거나 유효하면
					session.sendMessage(new TextMessage(jsonMessage));
				}
			}
		} else {
			session.sendMessage(new TextMessage("Error: Chat room not found"));
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		String token = (String) session.getAttributes().get("userToken");
		sessions.remove(token);
	}
}
