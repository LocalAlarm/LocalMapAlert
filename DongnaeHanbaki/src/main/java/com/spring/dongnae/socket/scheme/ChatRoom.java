package com.spring.dongnae.socket.scheme;

import java.util.List;

import org.springframework.data.annotation.Id;

// userIds엔 유저의 토큰이 들어간다.
public class ChatRoom {
	@Id
	private String id;
	private List<String> userIds;
	private List<Message> messages;
	private String roomName; //채팅방이름

	// id는 채팅방 고유 번호를 뜻한다.
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	// 유저 관련 명령어
	public List<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}

	public void addUser(String token) {
		this.userIds.add(token);
	}

	// 메세지 관련 명령어
	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public void addMessage(Message message) {
		this.messages.add(message);
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	@Override
	public String toString() {
		return "ChatRoom [id=" + id + ", userIds=" + userIds + ", messages=" + messages + ", roomName=" + roomName
				+ "]";
	}

}
