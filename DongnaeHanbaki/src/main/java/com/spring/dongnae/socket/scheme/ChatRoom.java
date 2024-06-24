package com.spring.dongnae.socket.scheme;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// userIds엔 유저의 토큰이 들어간다.
@Document(collection = "chatRoom")
public class ChatRoom {
	@Id
	private String id;
	private List<String> userTokens;
	private List<Message> messages;
	
	public ChatRoom() {
		setInitValue();
	}

	// id는 채팅방 고유 번호를 뜻한다.
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	// 유저 관련 명령어
	public List<String> getUserTokens() {
		return userTokens;
	}
	
	public void setUserTokens(List<String> userTokens) {
		this.userTokens = userTokens;
	}
	
	public void addUser(UserRooms userRooms) {
		this.userTokens.add(userRooms.getId());
	}
	
	public void addUser(String token) {
		this.userTokens.add(token);
	}
	
	public void banUser(String token) {
		this.userTokens.remove(token);
	}
	
	public void banUser(UserRooms userRoom) {
		this.userTokens.remove(userRoom.getId());
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
	
	private void setInitValue() {
		this.userTokens = new ArrayList<String>();
		this.messages = new ArrayList<Message>();
	}

	@Override
	public String toString() {
		return "ChatRoom [id=" + id + ", userTokens=" + userTokens + ", messages=" + messages + "]";
	}
	
	

}
