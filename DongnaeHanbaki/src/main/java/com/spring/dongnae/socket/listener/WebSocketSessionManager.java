package com.spring.dongnae.socket.listener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import com.spring.dongnae.utils.auth.GetAuthenticInfo;

@Component
public class WebSocketSessionManager {
	
	private GetAuthenticInfo getAuthenticInfo;
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void addSession(String userId, WebSocketSession session) {
        sessions.put(userId, session);
    }

    public void removeSession() {
        sessions.remove(getAuthenticInfo.GetToken());
    }

    public WebSocketSession getSession(String userId) {
        return sessions.get(userId);
    }

    public Map<String, WebSocketSession> getSessions() {
        return sessions;
    }
}