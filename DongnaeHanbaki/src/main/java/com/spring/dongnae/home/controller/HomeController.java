package com.spring.dongnae.home.controller;

import com.spring.dongnae.user.service.UserSessionService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private UserSessionService userSessionService;

    @GetMapping("/home")
    public String home(Model model) {
        // 현재 활성 사용자 해시 목록을 가져옵니다.
        System.out.println("home Hash List" + userSessionService.getActiveUserHashes());
        // 모델에 추가합니다.
        model.addAttribute("activeUserHashes", userSessionService.getActiveUserHashes());

        // home.html 뷰를 반환합니다.
        return "home/home";
    }
}