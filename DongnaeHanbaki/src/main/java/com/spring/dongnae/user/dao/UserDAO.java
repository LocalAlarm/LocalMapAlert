package com.spring.dongnae.user.dao;

import com.spring.dongnae.user.vo.UserVO;

public interface UserDAO {
	UserVO getUser(UserVO vo);
	UserVO getUserByEmail(String email);
	void insertKakaoUser(UserVO vo);
	void insertUser(UserVO vo);
	UserVO getIdUser(String email);
	// 이메일 중복 체크 - 건희
	int doubleCheckEmail(String email);
	 // 이메일 찾기 - 건희
	String findUserEmail(UserVO vo);
}
