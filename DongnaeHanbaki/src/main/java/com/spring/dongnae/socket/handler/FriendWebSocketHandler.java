package com.spring.dongnae.socket.handler;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.dongnae.socket.listener.WebSocketSessionManager;
import com.spring.dongnae.socket.repo.ChatRoomRepository;
import com.spring.dongnae.socket.repo.FriendRoomRepository;
import com.spring.dongnae.socket.repo.UserRoomsRepository;
import com.spring.dongnae.socket.scheme.FriendInfo;
import com.spring.dongnae.socket.scheme.FriendRoom;
import com.spring.dongnae.user.service.UserService;
import com.spring.dongnae.utils.auth.GetAuthenticInfo;

@Component
public class FriendWebSocketHandler extends TextWebSocketHandler {
	
	@Autowired
	private UserService userService;
	@Autowired
	private FriendRoomRepository friendRoomRepository;
	@Autowired
	private ChatRoomRepository chatRoomRepository;
	@Autowired
	private UserRoomsRepository userRoomsRepository;
	@Autowired
	private GetAuthenticInfo getAutehnticInfo; 

	private final ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
    private WebSocketSessionManager sessionManager;
	
	// 처음에 Socket 연결시에 실행되는 코드
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String token = (String) session.getAttributes().get("userToken");
		if (token != null) {
			sessionManager.addSession(token, session);
			// 사용자 ID로 기존 메시지 문서 검색
			Optional<FriendRoom> optionalFriendRoom = friendRoomRepository.findByUserRoomsId(token);
			FriendRoom friendRoom = new FriendRoom();
			// 기존 채팅방이 있으면 해당 데이터를 가져옴
			if (optionalFriendRoom.isPresent()) {
				friendRoom = optionalFriendRoom.get();
				String jsonFriendRoomId = objectMapper.writeValueAsString(friendRoom);
				session.sendMessage(new TextMessage(jsonFriendRoomId));
			} else {
				// 새로운 메시지 문서 생성
				friendRoom = new FriendRoom(userRoomsRepository.findById(getAutehnticInfo.GetToken()).get());
				friendRoomRepository.save(friendRoom);
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
	    String senderToken = friendInfo.getFriendToken();
	    // 토큰을 사용하여 해당 사용자의 WebSocket 세션을 가져옴
	    WebSocketSession senderSession= sessionManager.getSession(senderToken);
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
		sessionManager.removeSession();
	}
}
