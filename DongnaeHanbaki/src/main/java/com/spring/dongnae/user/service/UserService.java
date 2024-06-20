package com.spring.dongnae.user.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.dongnae.user.vo.UserVO;

public interface UserService {
	UserVO getUser(UserVO vo);
	UserVO getUserByEmail(String email);
	int insertUser(UserVO vo);
	void insertKakaoUser(UserVO vo);
	UserVO getIdUser (String email);
	// kakao
//	public String getAccessToken(String authorize_code);
//	public String getuserinfo(String access_Token, HttpSession session, RedirectAttributes rttr);
	
	// 이메일 중복 체크 - 건희
	int doubleCheckEmail(String email);
	
	// 이메일 찾기 - 건희
	String findUserEmail(UserVO vo);
	
	// token 값으로 이메일, 닉네임, 사진, 토큰 불러오는 기능
	UserVO getUserByToken(String token);
	
	// email 값으로 이메일, 닉네임, 사진, 토큰 불러오는 기능
	UserVO searchUserByEmail(String email);

	//비번찾기 중 이메일찾기
	String findPasswordByEmail(String email);
	
	//비번바꾸기
	void updatePassowrd(UserVO vo);
	
	//프로필 수정
	void updateProfile(Map<String, Object> map);
	
	//친구아이디 찾기
	List<UserVO> searchFriendByEmail(String email);
	
}
