package com.spring.dongnae.socket.scheme;


//친구정보
public class FriendInfo {
	
	private ChatRoom chatRoomId;
	private String friendToken;
	private String roomName;
	
	public ChatRoom getChatRoomId() {
		return chatRoomId;
	}
	
	public void setChatRoomId(ChatRoom chatRoomId) {
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

	@Override
	public String toString() {
		return "FriendInfo [chatRoomId=" + chatRoomId + ", friendToken=" + friendToken + ", roomName=" + roomName + "]";
	}
	
}
