package com.spring.dongnae.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dongnae.dao.UserDAO;
import com.spring.dongnae.service.UserService;
import com.spring.dongnae.vo.UserVO;


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






