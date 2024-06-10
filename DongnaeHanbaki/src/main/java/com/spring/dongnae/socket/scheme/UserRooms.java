package com.spring.dongnae.socket.scheme;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "userRooms")
public class UserRooms {

	@Id
	private String id;
	private String email;
	private String token;
	private List<String> chatRoomIds;

	public UserRooms() {
	}
	
	public UserRooms(String email, String token) {
		this.email = email;
		this.token = token;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<String> getChatRoomIds() {
		return chatRoomIds;
	}

	public void setChatRoomIds(List<String> chatRoomIds) {
		this.chatRoomIds = chatRoomIds;
	}

	public void addChatRoom(String roomId) {
		this.chatRoomIds.add(roomId);
	}

	@Override
	public String toString() {
		return "UserRooms [id=" + id + ", email=" + email + ", token=" + token + ", chatRoomIds=" + chatRoomIds + "]";
	}

}
