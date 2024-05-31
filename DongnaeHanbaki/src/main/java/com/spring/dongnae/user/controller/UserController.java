package com.spring.dongnae.user.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.dongnae.user.service.UserService;
import com.spring.dongnae.user.vo.UserVO;


@Controller
public class UserController {
	@Autowired
	private UserService userService;

	public UserController() {
		System.out.println("========= UserController() 객체생성");
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST) //POST 요청처리
//	@PostMapping("/login") //4.3 버전부터 사용가능 + <mvc:annotation-driven />
	public String login(UserVO vo) throws Exception {
		System.out.println(">> 로그인 처리");
		System.out.println("vo : " + vo);

		//일부러 예외 발생
		if (vo.getId() == null || vo.getId().equals("")) {
			throw new IllegalArgumentException("아이디는 반드시 입력해야 합니다");
		}
		
		UserVO user = userService.getUser(vo);
		System.out.println("user : " + user);
		
		if (user != null) {
			System.out.println(">>로그인 성공");
			return "user/main"; 
		} else {
			System.out.println(">>로그인 실패~~~");
			
//			return "login.jsp"; // forward 처리
			return "user/login"; 
		}
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET) //GET 요청처리
//	@GetMapping(value = "/login.do")
	public String loginView(@ModelAttribute("user") UserVO vo) {
		System.out.println(">> 로그인 화면이동 - loginView()");
		vo.setId("test");
		vo.setPassword("test");
		return "user/login"; // /WEB-INF/jsp/ + user/login + .jsp
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		System.out.println(">> 로그아웃 처리");
		session.invalidate();
		
		return "user/login";
	}
	
	// 회원가입 페이지로 이동 - 건희
	@RequestMapping("/join")
	   public String join() {
	       System.out.println(">> 회원가입 화면 이동 - join()");
	       return "user/join";
	   }

	
}










