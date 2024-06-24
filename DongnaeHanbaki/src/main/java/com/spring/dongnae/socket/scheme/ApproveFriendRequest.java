package com.spring.dongnae.socket.scheme;

import org.springframework.stereotype.Component;

@Component
public class ApproveFriendRequest {
	
	private String requestEmail;

	public String getRequestEmail() {
		return requestEmail;
	}

	public void setRequestEmail(String requestEmail) {
		this.requestEmail = requestEmail;
	}

}
