package com.spring.dongnae.socket.scheme;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.spring.dongnae.socket.repo.UserRoomsRepository;

//친구추가
@Document(collection = "friendRoom")
public class FriendRoom {
	
	@Autowired
	private UserRoomsRepository userRoomsRepository;

	@Id
	private String id;
	private String email;
	private String userRoomsId;
	private List<String> requestIds; // 친구추가 요청받은 아이디(목록) 들
	private List<FriendInfo> friendIds;
	
	public FriendRoom() {
	}
	
	public FriendRoom(UserRooms userRooms) {
		this.email = userRooms.getEmail();
		this.userRoomsId = userRooms.getId(); // userRooms의 id를 설정
		this.requestIds = new ArrayList<String>();
		this.friendIds = new ArrayList<FriendInfo>();
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
	
	public UserRooms getUserRooms() {
	    return userRoomsRepository.findById(userRoomsId).orElse(null);
	}
	
	public List<String> getRequestIds() {
		return requestIds;
	}

	public void setRequestIds(List<String> requestIds) {
		this.requestIds = requestIds;
	}
	
	public void addFriendRequest(String email) {
		this.requestIds.add(email);
	}

	public List<FriendInfo> getFriendIds() {
		return friendIds;
	}

	public void setFriendIds(List<FriendInfo> friendIds) {
		this.friendIds = friendIds;
	}

}
