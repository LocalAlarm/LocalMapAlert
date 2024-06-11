package com.spring.dongnae.user.service;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
        System.out.println("UserSessionService instance: " + userSessionService);
        //권한 확인
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        System.out.println("권한 : " + authorities); 
//        boolean isAdmin = false;
//        for (GrantedAuthority authority : authorities) {
//            if (authority.getAuthority().equals("ADMIN")) {
//                isAdmin = true;
//                System.out.println("for문성공");
//                break;
//            }
//        }
//        System.out.println(isAdmin);
//        if (isAdmin) {
//        	System.out.println("관리자!!!!!");
//        	response.sendRedirect("/dongnae/admin/main");
//        } else {
//        	response.sendRedirect("/dongnae/main");
//        }
        
        
        if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            System.out.println("관리자!!!!!");
            response.sendRedirect("/dongnae/admin/main");
        } else {
            response.sendRedirect("/dongnae/main");
        }

    }
}