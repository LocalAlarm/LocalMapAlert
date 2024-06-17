package com.spring.dongnae.socket.scheme;

import org.springframework.stereotype.Component;

@Component
public class ApproveFriendRequest {
	
	private String requestId;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
}
