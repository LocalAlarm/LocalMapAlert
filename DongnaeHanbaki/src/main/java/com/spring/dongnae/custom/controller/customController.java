package com.spring.dongnae.custom.controller;

import java.io.IOException;
import java.util.stream.Collectors;

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
	public boolean saveMap(HttpServletRequest request) throws IOException {
		boolean check = false;
		String jsonString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		System.out.println("커스텀 맵 데이터 받기 성공!!" + jsonString);
		return check;
	}
}
