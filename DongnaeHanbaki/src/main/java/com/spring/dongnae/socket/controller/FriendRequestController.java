package com.spring.dongnae.socket.controller;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dongnae.socket.repo.FriendRoomRepository;
import com.spring.dongnae.socket.scheme.FriendRequest;
import com.spring.dongnae.socket.scheme.FriendRoom;
import com.spring.dongnae.utils.auth.GetAuthenticInfo;

@RestController
public class FriendRequestController {

    @Autowired
    private final GetAuthenticInfo getAuthenticInfo;
    @Autowired
	private final FriendRoomRepository friendRoomRepository;
    
    public FriendRequestController(FriendRoomRepository friendRoomRepository, GetAuthenticInfo getAuthenticInfo) {
		this.getAuthenticInfo = getAuthenticInfo;
		this.friendRoomRepository = friendRoomRepository;
    }
    
    @PostMapping("/api/sendFriendRequest") // POST 요청을 처리하는 메서드를 정의합니다.
    public ResponseEntity<String> sendFriendRequest(@RequestBody FriendRequest friendRequest) { // 요청 본문에 있는 FriendRequest 객체를 인자로 받습니다. 
    	try {
            Optional<FriendRoom> optionalFriendRoom = friendRoomRepository.findByEmail(friendRequest.getRequestEmail()); // 친구 요청을 받는 사용자의 이메일로 FriendRoom 객체를 찾습니다.
            FriendRoom friendRoom = optionalFriendRoom.get(); // Optional 객체에서 FriendRoom 객체를 가져옵니다. 
            friendRoom.addFriendRequest(getAuthenticInfo.GetEmail()); // 로그인한 사용자의 이름을 친구 요청 목록에 추가합니다.
            friendRoom.setRequestIds(friendRoom.getRequestIds().stream().distinct().collect(Collectors.toList())); // 친구 요청 목록에서 중복 항목을 제거합니다.
            friendRoomRepository.save(friendRoom); // 변경된 FriendRoom 객체를 데이터베이스에 저장합니다.
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그인 해주세요."); // 사용자가 로그인하지 않은 경우, 에러 메시지를 반환합니다.
        }
        return ResponseEntity.ok("친구 요청이 성공적으로 전송되었습니다!"); // 모든 작업이 성공적으로 완료되면, 성공 메시지를 반환합니다.
    }

}
