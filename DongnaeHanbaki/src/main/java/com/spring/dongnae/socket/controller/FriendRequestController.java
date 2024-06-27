package com.spring.dongnae.socket.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dongnae.socket.dto.UserRoomsDto;
import com.spring.dongnae.socket.repo.ChatRoomRepository;
import com.spring.dongnae.socket.repo.FriendRoomRepository;
import com.spring.dongnae.socket.repo.UserRoomsRepository;
import com.spring.dongnae.socket.scheme.ApproveFriendRequest;
import com.spring.dongnae.socket.scheme.ChatRoom;
import com.spring.dongnae.socket.scheme.FriendInfo;
import com.spring.dongnae.socket.scheme.FriendRequest;
import com.spring.dongnae.socket.scheme.FriendRoom;
import com.spring.dongnae.socket.scheme.UserRooms;
import com.spring.dongnae.socket.service.UserRoomsService;
import com.spring.dongnae.user.service.UserService;
import com.spring.dongnae.utils.auth.GetAuthenticInfo;

@RestController
public class FriendRequestController {

    @Autowired
    private GetAuthenticInfo getAuthenticInfo;
    @Autowired
	private FriendRoomRepository friendRoomRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private UserRoomsRepository userRoomsRepository;
    @Autowired
    private UserRoomsService userRoomsService;
    
    private ApproveFriendRequest approveFriendRequest;
    
    //친구요청 보내는쪽
    @PostMapping(value ="/api/sendFriendRequest", consumes = "application/json", produces ="text/plain;charset=UTF-8") // POST 요청을 처리하는 메서드를 정의합니다.
    public ResponseEntity<String> sendFriendRequest(@RequestBody FriendRequest friendRequest) { // 요청 본문에 있는 FriendRequest 객체를 인자로 받습니다.
    	System.out.println("시작~~~~~~~~~~~");
    	System.out.println("friendRequest : " + friendRequest);
    	try {
    		// 이메일로 사용자 방 정보를 조회하고, 조회 결과에 따라 응답을 반환
    		Optional<UserRooms> optionalUserRoom = userRoomsRepository.findByEmail(friendRequest.getRequestEmail());	
    		System.out.println("optionalUserRoom : " + optionalUserRoom);
    		// optionalUserRoom이 비어있는지 확인
    		if (!optionalUserRoom.isPresent() || getAuthenticInfo.GetToken().isEmpty()) {
    			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("잘못된 요청입니다.");
    		}
    		UserRooms userRoom =  optionalUserRoom.get(); // 비어있지 않으면 UserRooms객체 가져옴
    		userRoom.addRequestIds(getAuthenticInfo.GetEmail());
    		System.out.println("userRoom : " + userRoom);
    		userRoomsRepository.save(userRoom);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그인 해주세요."); // 사용자가 로그인하지 않은 경우, 에러 메시지를 반환합니다.
        }
        return ResponseEntity.ok("친구 요청이 성공적으로 전송되었습니다!"); // 모든 작업이 성공적으로 완료되면, 성공 메시지를 반환합니다.
    }

    //친구요청 받는쪽
    @PostMapping(value="/api/receiveFriendRequest", consumes = "application/json", produces ="text/plain;charset=UTF-8")
    public ResponseEntity<List<String>> receiveFriendRequest() {
    	// getAuthenticInfo.GetToken() 메서드를 사용하여 현재 인증된 사용자의 토큰을 가져옴
    	Optional<UserRooms> optionalReceiveFriend = userRoomsRepository.findByEmail(getAuthenticInfo.GetToken());
    	if (optionalReceiveFriend.isPresent()) { // 객체에 값이 있으면?
    		UserRooms userRoom = optionalReceiveFriend.get(); // UserRooms 객체 가져옴
    		return ResponseEntity.ok(userRoom.getRequestIds()); // 친구요청 id 반환
    	}
    	return ResponseEntity.notFound().build(); // 비어있으면 에러 반환
    }
    
    @PostMapping(value = "/api/approveFriendRequest", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> approveFriendRequest(@RequestBody ApproveFriendRequest approveRequest) {
        try {
        	String requestId = approveRequest.getRequestId();
        	Optional<UserRooms> optionalFriendRoom = userRoomsRepository.findByEmail(requestId);
        	Optional<UserRooms> optionalMyFriendRoom = userRoomsRepository.findByEmail(getAuthenticInfo.GetEmail());
        	
        	ChatRoom chatRoom = new ChatRoom();
        	chatRoom.addUser(userRoomsRepository.findById(userService.getUserByEmail(requestId).getToken()).get());
        	chatRoom.addUser(userRoomsRepository.findById(getAuthenticInfo.GetToken()).get());
        	chatRoomRepository.save(chatRoom);
        	System.out.println(chatRoom.toString()); // 테스트
        	
        	if (optionalFriendRoom.isPresent()) { // 있으면 
        		UserRooms friendRoom = optionalFriendRoom.get(); // 가져옴
        		
        		// "친구요청 목록"에서 해당 요청 제거하기
        		friendRoom.getRequestIds().remove(getAuthenticInfo.GetEmail()); //받은 이메일 삭제
        		FriendInfo friendInfo = new FriendInfo();
        		friendInfo.setFriendToken(userService.getUserByEmail(getAuthenticInfo.GetEmail()).getToken());
	            friendInfo.setRoomName(getAuthenticInfo.GetEmail());
	            friendInfo.setChatRoomId(chatRoom);
	            friendRoom.getFriendIds().add(friendInfo); //추가
	            userRoomsRepository.save(friendRoom); //저장
        	} else {
        		return ResponseEntity.notFound().build(); // 에러 반환
        	}
        	
            if (optionalMyFriendRoom.isPresent()) {
                UserRooms friendRoom = optionalMyFriendRoom.get();

                // 친구 요청 목록에서 해당 요청 제거
                friendRoom.getRequestIds().remove(requestId);
                FriendInfo friendInfo = new FriendInfo();
                friendInfo.setFriendToken(userService.getUserByEmail(requestId).getToken());
                friendInfo.setRoomName(requestId);
                friendInfo.setChatRoomId(chatRoom);
                friendRoom.getFriendIds().add(friendInfo);
                userRoomsRepository.save(friendRoom);
                // 친구 목록에 요청한 이메일 추가
            } else {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok("친구 요청을 수락하였습니다.");
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("친구 요청 수락 중 오류가 발생하였습니다.");
        }
    }
    
    public ResponseEntity<String> rejectFriendRequest(@RequestBody ApproveFriendRequest approveRequest) {
		return null;
    	
    }
}
