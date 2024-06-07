package com.spring.dongnae.socket.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.spring.dongnae.user.service.UserSessionService;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Configuration
public class SessionListener implements HttpSessionListener {

    @Autowired
    private UserSessionService userSessionService;

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        // 세션 생성 시 로직
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        String username = (String) event.getSession().getAttribute("username");
        if (username != null) {
            userSessionService.removeUserSession(username);
        }
    }
}