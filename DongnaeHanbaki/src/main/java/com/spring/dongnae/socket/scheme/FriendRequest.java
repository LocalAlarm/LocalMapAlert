package com.spring.dongnae.socket.scheme;

public class FriendRequest {

	private String request;
	private String requestEmail;
	private String sendEmail;

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getRequestEmail() {
		return requestEmail;
	}

	public void setRequestEmail(String requestEmail) {
		this.requestEmail = requestEmail;
	}

	public String getSendEmail() {
		return sendEmail;
	}

	public void setSendEmail(String sendEmail) {
		this.sendEmail = sendEmail;
	}

	@Override
	public String toString() {
		return "FriendRequest [request=" + request + ", requestEmail=" + requestEmail + ", sendEmail=" + sendEmail
				+ "]";
	}

}
