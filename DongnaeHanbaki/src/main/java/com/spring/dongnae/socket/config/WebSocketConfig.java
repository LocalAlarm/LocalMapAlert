package com.spring.dongnae.socket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.spring.dongnae.socket.handler.ChatListWebSocketHandler;
import com.spring.dongnae.socket.handler.FriendWebSocketHandler;
import com.spring.dongnae.socket.handler.HandlerInterceptor;
import com.spring.dongnae.socket.handler.MoimWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	
	@Autowired
    private ChatListWebSocketHandler chatListWebSocketHandler;
	@Autowired
    private FriendWebSocketHandler friendWebSocketHandler;
	@Autowired
    private MoimWebSocketHandler moimWebSocketHandler;
	@Autowired
    private HandlerInterceptor handlerInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatListWebSocketHandler, "/chatList")
                .addInterceptors(handlerInterceptor) // 스프링 시큐리티에 토큰이 있는지 확인
                .setAllowedOrigins("*");
        registry.addHandler(friendWebSocketHandler, "/friend")
	       		.addInterceptors(handlerInterceptor) // 스프링 시큐리티에 토큰이 있는지 확인 / 친구추가
	       		.setAllowedOrigins("*");
        registry.addHandler(moimWebSocketHandler, "/moim")
		        .addInterceptors(handlerInterceptor) // 스프링 시큐리티에 토큰이 있는지 확인 / 친구추가
		        .setAllowedOrigins("*");
    }
}