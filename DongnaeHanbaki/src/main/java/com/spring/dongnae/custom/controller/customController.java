package com.spring.dongnae.custom.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class customController {

	public customController() {
		System.out.println("========= customController() 객체생성");
	}
	
	@PostMapping("/saveMap")
	@ResponseBody
	public boolean saveMap(HttpServletRequest request) {
		boolean check = false;
		return check;
	}
}
