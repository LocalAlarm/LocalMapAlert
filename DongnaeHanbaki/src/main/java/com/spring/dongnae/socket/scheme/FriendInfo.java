package com.spring.dongnae.socket.scheme;

//친구정보
public class FriendInfo {

	private String roomId;
	private String roomName;
	private String token;

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
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
		return "FriendInfo [roomId=" + roomId + ", roomName=" + roomName + ", token="
				+ token + "]";
	}

}
