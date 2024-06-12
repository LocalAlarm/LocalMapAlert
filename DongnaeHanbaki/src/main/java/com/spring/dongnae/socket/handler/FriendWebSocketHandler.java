package com.spring.dongnae.socket.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.dongnae.socket.repo.ChatRoomRepository;
import com.spring.dongnae.socket.repo.FriendRoomRepository;
import com.spring.dongnae.socket.scheme.FriendInfo;
import com.spring.dongnae.socket.scheme.FriendRoom;
import com.spring.dongnae.user.service.UserService;
import com.spring.dongnae.user.vo.UserVO;

@Component
public class FriendWebSocketHandler extends TextWebSocketHandler {
	
	@Autowired
	private final UserService userService;
	@Autowired
	private final FriendRoomRepository friendRoomRepository;
	@Autowired
	private final ChatRoomRepository chatRoomRepository;

	private final ObjectMapper objectMapper = new ObjectMapper();
	
	// 사용자 ID를 키로 하고 해당 사용자의 웹소켓 세션을 값으로 가지는 맵을 생성
	private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

	// 서비스 주입 받는 생성자 정의
	public FriendWebSocketHandler(UserService userService, FriendRoomRepository friendRoomRepository,
			ChatRoomRepository chatRoomRepository) {
		this.userService = userService;
		this.friendRoomRepository = friendRoomRepository;
		this.chatRoomRepository = chatRoomRepository;
	}
	
	// 처음에 Socket 연결시에 실행되는 코드
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String token = (String) session.getAttributes().get("userToken");
		if (token != null) {
			sessions.put(token, session);
			// 사용자 ID로 기존 메시지 문서 검색
			Optional<FriendRoom> optionalFriendRoom = friendRoomRepository.findByToken(token);
			FriendRoom friendRoom = new FriendRoom();
			// 기존 채팅방이 있으면 해당 데이터를 가져옴
			if (optionalFriendRoom.isPresent()) {
				friendRoom = optionalFriendRoom.get();
				String jsonFriendRoomId = objectMapper.writeValueAsString(friendRoom);
				session.sendMessage(new TextMessage(jsonFriendRoomId));
			} else {
				// 새로운 메시지 문서 생성
				friendRoom = new FriendRoom(userService.getUserByToken(token));
//				friendRoomRepository.save(friendRoom);
				friendRoomRepository.save(createTestValue(token, friendRoom));
			}
		} else {
			session.close(CloseStatus.NOT_ACCEPTABLE);
		}
	}
	
	// 친구 요청을 받는 부분의 처리 메서드
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
	    // 받은 메시지를 FriendInfo 객체로 변환
	    FriendInfo friendInfo = objectMapper.readValue(message.getPayload(), FriendInfo.class);
	    // 친구 요청을 보낸 사용자의 토큰 가져오기
	    String senderToken = friendInfo.getToken();
	    // 토큰을 사용하여 해당 사용자의 WebSocket 세션을 가져옴
	    WebSocketSession senderSession = sessions.get(senderToken);
	    if (senderSession != null) {
	        // 알림 메시지 생성
	        String notificationMessage = "친구 요청이 도착했습니다.";

	        // JSON 형식의 메시지 생성
	        String friendJsonMesaage = objectMapper.writeValueAsString(notificationMessage);
	        senderSession.sendMessage(new TextMessage(friendJsonMesaage));
	        
//	        for (FriendInfo friendRoomUser : friendJsonMesaage) {
//	        	여기부터
//	        }
	    }
	}


	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

	}
	
	private FriendRoom createTestValue(String token, FriendRoom friendRoom) {
		friendRoom = new FriendRoom(userService.getUserByToken(token));
		ArrayList<String> requestIds = new ArrayList<String>();
		ArrayList<FriendInfo> friendIds = new ArrayList<FriendInfo>();
		requestIds.add("b@naver.com");
		FriendInfo friendInfo = new FriendInfo();
		friendInfo.setRoomId("66669cdeb3474e4adc175dda");
		friendInfo.setRoomName("test");
		friendInfo.setToken("$2a$10$H/U.H8Qqi2BgmBL.jI0VkeJEMpDeR3Mc49f95uJDqpinK8tyW/EOK");
		friendInfo.setChatRoomId("66669cdeb3474e4adc175dda");
		friendIds.add(friendInfo);
		friendRoom.setFriendIds(friendIds);
		friendRoom.setRequestIds(requestIds);
		return friendRoom;
	}
	
}
