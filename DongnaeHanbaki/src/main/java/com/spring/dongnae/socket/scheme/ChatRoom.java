package com.spring.dongnae.socket.scheme;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

// userIds엔 유저의 토큰이 들어간다.
@Document(collection = "chatRoom")
public class ChatRoom {
	@Id
	private String id;
	@DBRef
	private List<UserRooms> userRooms;
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
	public List<UserRooms> getUserRooms() {
		return userRooms;
	}
	
	public void setUserRooms(List<UserRooms> userRooms) {
		this.userRooms = userRooms;
	}

	public void addUser(UserRooms userRooms) {
		this.userRooms.add(userRooms);
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
		this.userRooms = new ArrayList<UserRooms>();
		this.messages = new ArrayList<Message>();
	}

	@Override
	public String toString() {
		return "ChatRoom [id=" + id + ", userRooms=" + userRooms + ", messages=" + messages + "]";
	}
	

}
