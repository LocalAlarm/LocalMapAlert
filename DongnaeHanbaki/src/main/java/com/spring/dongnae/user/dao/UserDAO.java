package com.spring.dongnae.user.dao;

import com.spring.dongnae.user.vo.UserVO;

public interface UserDAO {
	UserVO getUser(UserVO vo);
	UserVO getUserByEmail(String email);
	void insertKakaoUser(UserVO vo);
	void insertUser(UserVO vo);
	UserVO getIdUser(String email);
	
	String doubleCheckEmail(String email);
}
