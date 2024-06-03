package com.spring.dongnae.user.dao;

import com.spring.dongnae.user.vo.UserVO;

public interface UserDAO {
	UserVO getUser(UserVO vo);
	void insertKakaoUser(UserVO vo);
	void insertUser(UserVO vo);
	UserVO getIdUser(String email);
	
}
