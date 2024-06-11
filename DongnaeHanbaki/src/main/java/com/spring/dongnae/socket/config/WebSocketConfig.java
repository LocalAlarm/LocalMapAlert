package com.spring.dongnae.socket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.spring.dongnae.socket.handler.ChatListWebSocketHandler;
import com.spring.dongnae.socket.handler.HandlerInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatListWebSocketHandler chatListWebSocketHandler;
    private final HandlerInterceptor handlerInterceptor;

    public WebSocketConfig(ChatListWebSocketHandler chatListWebSocketHandler, HandlerInterceptor handlerInterceptor) {
        this.chatListWebSocketHandler = chatListWebSocketHandler;
        this.handlerInterceptor = handlerInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatListWebSocketHandler, "/chatList")
                .addInterceptors(handlerInterceptor) // 스프링 시큐리티에 토큰이 있는지 확인
                .setAllowedOrigins("*");
        registry.addHandler(chatListWebSocketHandler, "/friend")
	       		.addInterceptors(handlerInterceptor) // 스프링 시큐리티에 토큰이 있는지 확인 / 친구추가
	       		.setAllowedOrigins("*");
    }
}