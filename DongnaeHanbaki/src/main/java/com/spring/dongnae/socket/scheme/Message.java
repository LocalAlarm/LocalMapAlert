package com.spring.dongnae.socket.scheme;

public class Message {
	private String senderToken;
	private String content;
	private long timestamp;
	
	public String getSenderToken() {
		return senderToken;
	}
	public void setSenderToken(String senderToken) {
		this.senderToken = senderToken;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
