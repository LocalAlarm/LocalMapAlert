package com.spring.dongnae.user.dao;

import java.util.Map;

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
	//비번찾기 중 이메일찾기
	String findPasswordByEmail(String email);
	//비번바꾸기
	void updatePassowrd(UserVO vo);
	//프로필 수정
	void updateProfile(Map<String, Object> map);
}
