package com.spring.dongnae.socket.scheme;

import org.springframework.data.mongodb.core.mapping.DBRef;

//친구정보
public class FriendInfo {
	
	private String roomName;
	private String token;
	@DBRef
	private ChatRoom chatRoom;
	
	public ChatRoom getChatRoom() {
		return chatRoom;
	}

	public void setChatRoom(ChatRoom chatRoom) {
		this.chatRoom = chatRoom;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "FriendInfo [roomName=" + roomName + ", token=" + token + ", chatRoom=" + chatRoom + "]";
	}
	
	
	
}
