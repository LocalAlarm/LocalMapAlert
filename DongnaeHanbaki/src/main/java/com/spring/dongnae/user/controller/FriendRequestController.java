package com.spring.dongnae.user.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dongnae.socket.repo.FriendRoomRepository;
import com.spring.dongnae.socket.scheme.FriendRequest;
import com.spring.dongnae.socket.scheme.FriendRoom;
import com.spring.dongnae.user.service.UserService;
import com.spring.dongnae.user.vo.CustomUserDetails;

@RestController
public class FriendRequestController {

    @Autowired
    private final UserService userService;
    
    @Autowired
	private final FriendRoomRepository friendRoomRepository;
    
    public FriendRequestController(UserService userService, FriendRoomRepository friendRoomRepository) {
        this.userService = userService;
		this.friendRoomRepository = friendRoomRepository;
    }
    
    @PostMapping("/api/sendFriendRequest")
    public ResponseEntity<String> sendFriendRequest(@RequestBody FriendRequest friendRequest) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Optional<FriendRoom> optionalFriendRoom = friendRoomRepository.findByEmail(friendRequest.getRequestEmail());
            FriendRoom friendRoom = optionalFriendRoom.get();
            friendRoom.addFriendRequest(userDetails.getUsername());
            friendRoom.setRequestIds(friendRoom.getRequestIds().stream().distinct().collect(Collectors.toList()));
            friendRoomRepository.save(friendRoom);
        } else {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그인 해주세요.");
        }
        return ResponseEntity.ok("친구 요청이 성공적으로 전송되었습니다!");
    }
}
