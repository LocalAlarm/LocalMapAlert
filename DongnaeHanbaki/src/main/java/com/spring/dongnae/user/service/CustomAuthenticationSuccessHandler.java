package com.spring.dongnae.user.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserSessionService userSessionService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    	System.out.println("CustomAuthenticationSuccessHandler 작동");
    	String username = authentication.getName();
        userSessionService.addUserSession(username);
        System.out.println("User " + username + " logged in with hash: " + username.hashCode());
        System.out.println("Active User Hashes: " + userSessionService.getActiveUserHashes());
        response.sendRedirect("/dongnae/home");
    }
}