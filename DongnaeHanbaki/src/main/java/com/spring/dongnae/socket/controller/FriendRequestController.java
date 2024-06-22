package com.spring.dongnae.socket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dongnae.socket.scheme.ApproveFriendRequest;
import com.spring.dongnae.socket.scheme.FriendInfo;
import com.spring.dongnae.socket.scheme.FriendRequest;
import com.spring.dongnae.socket.service.UserRoomsService;
import com.spring.dongnae.user.vo.UserVO;
import com.spring.dongnae.utils.auth.GetAuthenticInfo;

@RestController
@RequestMapping("/friendData")
public class FriendRequestController {

    @Autowired
    private GetAuthenticInfo getAuthenticInfo;
    @Autowired
    private UserRoomsService userRoomsService;
    
    @PostMapping("/sendFriendRequest") // POST 요청을 처리하는 메서드를 정의합니다.
    public ResponseEntity<String> sendFriendRequest(@RequestBody FriendRequest friendRequest) { // 요청 본문에 있는 FriendRequest 객체를 인자로 받습니다. 
    	try {
    		userRoomsService.addFriendRequest(friendRequest.getRequestEmail(), getAuthenticInfo.GetToken());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미 친구요청을 보냈습니다."); // 사용자가 로그인하지 않은 경우, 에러 메시지를 반환합니다.
        }
        return ResponseEntity.ok("친구 요청이 성공적으로 전송되었습니다!"); // 모든 작업이 성공적으로 완료되면, 성공 메시지를 반환합니다.
    }

    //친구요청 받아온걸 화면에 띄워주는 코드
    @PostMapping("/friendRequest")
    public ResponseEntity<List<UserVO>> receiveFriendRequest() {
    	List<UserVO> requestVO = null;
    	try {
    		requestVO = userRoomsService.getRequestIds();
    	} catch(Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    	}
    	return ResponseEntity.ok(requestVO);
    }
    
    // 친구의 리스트를 보내주는 코드
    @PostMapping("/friendIds")
    public ResponseEntity<List<FriendInfo>> receiveFriendIds() {
    	List<FriendInfo> friendsInfo = null;
    	try {
    		friendsInfo = userRoomsService.getFriendIds();
    	} catch(Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    	}
    	return ResponseEntity.ok(friendsInfo);
    }
    
    @PostMapping(value = "/approveFriendRequest", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> approveFriendRequest(@RequestBody ApproveFriendRequest approveRequest) {
        try {
            if (userRoomsService.processApproveFriendRequest(approveRequest.getRequestEmail())) {
            	return ResponseEntity.ok("친구 요청을 수락하였습니다.");            	
            } else {
            	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("친구 요청 수락 중 오류가 발생하였습니다.");
            }
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("친구 요청 수락 중 오류가 발생하였습니다.");
        }
    }
    
    @PostMapping(value="/rejectFriendRequest", produces="text/plain;charset=UTF-8")
    public ResponseEntity<String> rejectFriendRequest(@RequestBody FriendRequest friendRequest) {
    	try {
    		if (userRoomsService.processRejectFriendReqeust(friendRequest.getRequestEmail())) {
    			return ResponseEntity.ok("친구 요청을 거절하였습니다.");  
    		} else {
    			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("데이터 처리 중 오류가 발생하였습니다.");
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("데이터 처리 중 오류가 발생하였습니다.");
    	}
    }
}
