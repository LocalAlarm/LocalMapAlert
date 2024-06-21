package com.spring.dongnae.socket.scheme;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "moimProjection", types = { Moim.class })
public interface MoimProjection {
    String getId();
    String getName();
    String getProfilePic();
    String getChatRoomId();
}