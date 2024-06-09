package com.spring.dongnae.socket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.spring.dongnae.socket.handler.ChatWebSocketHandler;
import com.spring.dongnae.socket.handler.HandlerInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatWebSocketHandler chatWebSocketHandler;
    private final HandlerInterceptor handlerInterceptor;

    public WebSocketConfig(ChatWebSocketHandler chatWebSocketHandler, HandlerInterceptor handlerInterceptor) {
        this.chatWebSocketHandler = chatWebSocketHandler;
        this.handlerInterceptor = handlerInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatWebSocketHandler, "/chat")
                .addInterceptors(handlerInterceptor) // 스프링 시큐리티에 토큰이 있는지 확인
                .setAllowedOrigins("*");
    }
}