package com.spring.dongnae.socket.scheme;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "userRooms")
public class UserRooms {
	
	@Id
	private String id;
	private String email;
	private List<String> chatRoomIds;
	
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
	public List<String> getChatRoomIds() {
		return chatRoomIds;
	}
	public void setChatRoomIds(List<String> chatRoomIds) {
		this.chatRoomIds = chatRoomIds;
	}
	@Override
	public String toString() {
		return "UserRoom [id=" + id + ", email=" + email + ", chatRoomIds=" + chatRoomIds + "]";
	}

}
