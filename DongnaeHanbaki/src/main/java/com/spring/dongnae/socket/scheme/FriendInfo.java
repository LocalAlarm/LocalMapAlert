package com.spring.dongnae.socket.scheme;


//친구정보
public class FriendInfo {
	
	private String chatRoomId;
	private String friendToken;
	private String roomName;
	private String email;
	
	public FriendInfo(String chatRoomId, String friendToken, String email) {
		this.setChatRoomId(chatRoomId);
		this.setFriendToken(friendToken);
		this.setRoomName(email);
		this.setEmail(email);
	}
	
	public String getChatRoomId() {
		return chatRoomId;
	}

	public void setChatRoomId(String chatRoomId) {
		this.chatRoomId = chatRoomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}


	public String getFriendToken() {
		return friendToken;
	}

	public void setFriendToken(String friendToken) {
		this.friendToken = friendToken;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "FriendInfo [chatRoomId=" + chatRoomId + ", friendToken=" + friendToken + ", roomName=" + roomName
				+ ", email=" + email + "]";
	}

	
	
}
