package com.spring.dongnae.user.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class UserApiController {
	 private static final Logger log = LoggerFactory.getLogger(UserApiController.class);

	//카카오 로그인 기능이 처리되는 페이지
	@RequestMapping(value = "/kakaoLoginForm/getKakaoAuthUrl")
	public @ResponseBody String getKakaoAuthUrl(HttpServletRequest request) throws Exception {
		System.out.println("카카오실행!!!!!");
	    String reqUrl =
	            "https://kauth.kakao.com/oauth/authorize?client_id=9177b1d8e0e9ad5c8d3ad7ac72a869b7&redirect_uri=http://localhost:8088/dongnae/login&response_type=code";

	    return reqUrl;
	}
	
}
