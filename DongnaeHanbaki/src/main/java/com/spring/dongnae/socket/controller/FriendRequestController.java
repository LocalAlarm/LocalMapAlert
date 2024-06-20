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

import com.spring.dongnae.socket.repo.FriendRoomRepository;
import com.spring.dongnae.socket.repo.UserRoomsRepository;
import com.spring.dongnae.socket.scheme.ApproveFriendRequest;
import com.spring.dongnae.socket.scheme.ChatRoom;
import com.spring.dongnae.socket.scheme.FriendInfo;
import com.spring.dongnae.socket.scheme.FriendRequest;
import com.spring.dongnae.socket.scheme.FriendRoom;
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
    
    private ApproveFriendRequest approveFriendRequest;
    
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

    //친구요청 받아온걸 화면에 띄워주는 코드
    @PostMapping("/api/receiveFriendRequest")
    public ResponseEntity<List<FriendRoom>> receiveFriendRequest(@RequestBody FriendRequest friendRequest) {
        Optional<FriendRoom> optionalReceiveFriend = friendRoomRepository.findByEmail(friendRequest.getRequestEmail()); // 친구요청 받는 사용자 이메일로 찾기
        if (optionalReceiveFriend.isPresent()) { // 있으면
            FriendRoom friendRoom = optionalReceiveFriend.get();
            // 필요한 로직 수행 후 친구 요청 목록 반환 
            List<FriendRoom> friendRequests = Arrays.asList(friendRoom); 
            return ResponseEntity.ok(friendRequests);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping(value = "/api/approveFriendRequest", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> approveFriendRequest(@RequestBody ApproveFriendRequest approveRequest) {
        try {
            String requestId = approveRequest.getRequestId();
            Optional<FriendRoom> optionalFriendRoom = friendRoomRepository.findByEmail(requestId);
            Optional<FriendRoom> optionalMyFriendRoom = friendRoomRepository.findByEmail(getAuthenticInfo.GetEmail());
            ChatRoom chatRoom = new ChatRoom();
            chatRoom.addUser(userRoomsRepository.findById(userService.getUserByEmail(requestId).getToken()).get());
            chatRoom.addUser(userRoomsRepository.findById(getAuthenticInfo.GetToken()).get());
            chatRoomRepository.save(chatRoom);
            
            System.out.println(chatRoom.toString());
            if (optionalFriendRoom.isPresent()) {
                FriendRoom friendRoom = optionalFriendRoom.get();

                // 친구 요청 목록에서 해당 요청 제거
                friendRoom.getRequestIds().remove(getAuthenticInfo.GetEmail());
                FriendInfo friendInfo = new FriendInfo();
                System.out.println(requestId);
                friendInfo.setFriendToken(userService.getUserByEmail(getAuthenticInfo.GetEmail()).getToken());
                friendInfo.setRoomName(getAuthenticInfo.GetEmail());
                friendInfo.setChatRoomId(chatRoom);
                friendRoom.getFriendIds().add(friendInfo);
                friendRoomRepository.save(friendRoom);
                // 친구 목록에 요청한 이메일 추가
            } else {
                return ResponseEntity.notFound().build();
            }
            
            if (optionalMyFriendRoom.isPresent()) {
                FriendRoom friendRoom = optionalMyFriendRoom.get();

                // 친구 요청 목록에서 해당 요청 제거
                friendRoom.getRequestIds().remove(requestId);
                FriendInfo friendInfo = new FriendInfo();
                friendInfo.setFriendToken(userService.getUserByEmail(requestId).getToken());
                friendInfo.setRoomName(requestId);
                friendInfo.setChatRoomId(chatRoom);
                friendRoom.getFriendIds().add(friendInfo);
                friendRoomRepository.save(friendRoom);
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
