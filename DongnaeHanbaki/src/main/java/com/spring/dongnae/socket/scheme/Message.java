package com.spring.dongnae.socket.scheme;

public class Message {
	private static final long serialVersionUID = 1L; // serialVersionUID 필드 추가
	private String roomId;
	private String senderToken;
	private String content;
	private long timestamp;

	public Message() {
	}
	
	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

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

	@Override
	public String toString() {
		return "Message [roomId=" + roomId + ", senderToken=" + senderToken + ", content=" + content + ", timestamp="
				+ timestamp + "]";
	}

}
