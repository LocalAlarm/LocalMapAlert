package com.spring.dongnae.home.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.spring.dongnae.user.vo.CustomUserDetails;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if(authentication == null) {
    		System.out.println("authentication is null1");
    	};
    	CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
    	System.out.println("Usertoken" + userDetails.getToken());
        return "home/home";
    }
}