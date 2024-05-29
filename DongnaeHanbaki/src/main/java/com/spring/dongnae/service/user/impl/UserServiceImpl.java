package com.spring.dongnae.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dongnae.user.dao.UserDAO;
import com.spring.dongnae.user.service.UserService;
import com.spring.dongnae.user.vo.UserVO;


@Service(value = "userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDAO;

	public UserServiceImpl() {
		System.out.println(">> UserServiceImpl() 객체생성");
	}

	@Override
	public UserVO getUser(UserVO vo) {
		return userDAO.getUser(vo);
	}
	
}






