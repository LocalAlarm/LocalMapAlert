package com.spring.dongnae.admin.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin/main")
	public String adminMain() {
		System.out.println("관리자 메인페이지로 이동 !!!!!!");
		return "admin/main";
	}
}
