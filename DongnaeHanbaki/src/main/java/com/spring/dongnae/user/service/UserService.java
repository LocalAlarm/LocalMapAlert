package com.spring.dongnae.user.service;

import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.dongnae.user.vo.UserVO;

public interface UserService {
	UserVO getUser(UserVO vo);
	UserVO getUserByEmail(String email);
	void insertUser(UserVO vo);
	void insertKakaoUser(UserVO vo);
	UserVO getIdUser (String email);
	// kakao
//	public String getAccessToken(String authorize_code);
//	public String getuserinfo(String access_Token, HttpSession session, RedirectAttributes rttr);
	
	// 이메일 중복 체크 - 건희
	int doubleCheckEmail(String email);
	
	// 이메일 찾기 - 건희
	String findUserEmail(UserVO vo);
	
	//비번찾기 중 이메일찾기
	String findPasswordByEmail(String email);
}
