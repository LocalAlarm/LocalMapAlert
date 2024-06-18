package com.spring.dongnae.user.dao;

import java.util.List;
import java.util.Map;

import com.spring.dongnae.user.vo.UserVO;

public interface UserDAO {
	UserVO getUser(UserVO vo);
	UserVO getUserByEmail(String email);
	void insertKakaoUser(UserVO vo);
	int insertUser(UserVO vo);
	UserVO getIdUser(String email);
	// 이메일 중복 체크 - 건희
	int doubleCheckEmail(String email);
	 // 이메일 찾기 - 건희
	String findUserEmail(UserVO vo);
	
	// 토큰 값으로 이메일, 닉네임 찾기
	UserVO getUserByToken(String token);
	UserVO searchUserByEmail(String email);
	//비번찾기 중 이메일찾기
	String findPasswordByEmail(String email);
	//비번바꾸기
	void updatePassowrd(UserVO vo);
	//프로필 수정
	void updateProfile(Map<String, Object> map);
	//친구 아이디 찾아서 데이터 가져오기
	List<UserVO> searchFriendByEmail(String email);
}
