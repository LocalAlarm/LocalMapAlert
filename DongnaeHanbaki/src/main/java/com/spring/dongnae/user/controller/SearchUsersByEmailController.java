package com.spring.dongnae.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dongnae.socket.scheme.EmailRequest;
import com.spring.dongnae.user.service.UserService;
import com.spring.dongnae.user.vo.UserVO;

@RestController
public class SearchUsersByEmailController {
	@Autowired
	private final UserService userService;
	
	public SearchUsersByEmailController(UserService userService) {
		this.userService = userService;
	}
	
    @PostMapping("/api/searchUserByEmail")
    public List<UserVO> searchUserByEmail(@RequestBody EmailRequest emailRequest) {
        return userService.searchFriendByEmail(emailRequest.getEmail());
    }
}
