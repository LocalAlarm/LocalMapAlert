package com.spring.dongnae.socket.scheme;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.annotation.Id;

//친구추가
public class FriendRoom {

	@Id
	private String id;
	private String email;
	private String token;
	// 키 값에 요청한 사람의 이메일 , value값에 토큰 넣음
	private HashMap<String, String> requestIds; // 친구추가 요청받은 아이디(목록) 들
	private HashMap<String, FriendInfo> friendIds;

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

	public HashMap<String, String> getRequestIds() {
		return requestIds;
	}

	public void setRequestIds(HashMap<String, String> requestIds) {
		this.requestIds = requestIds;
	}

	public HashMap<String, FriendInfo> getFriendIds() {
		return friendIds;
	}

	public void setFriendIds(HashMap<String, FriendInfo> friendIds) {
		this.friendIds = friendIds;
	}

	@Override
	public String toString() {
		return "FriendRoom [id=" + id + ", email=" + email + ", token=" + token + ", requestIds=" + requestIds
				+ ", friendIds=" + friendIds + "]";
	}

}
