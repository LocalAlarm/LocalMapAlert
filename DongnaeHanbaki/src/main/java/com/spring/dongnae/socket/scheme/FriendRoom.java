package com.spring.dongnae.socket.scheme;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.spring.dongnae.user.vo.UserVO;

//친구추가
@Document(collection = "friendRoom")
public class FriendRoom {

	@Id
	private String id;
	private String email;
	private String token;
	// 키 값에 요청한 사람의 이메일 , value값에 토큰 넣음
	private List<String> requestIds; // 친구추가 요청받은 아이디(목록) 들
	private List<FriendInfo> friendIds;
	
	public FriendRoom() {
	}
	
	public FriendRoom(UserVO userVO) {
		this.email = userVO.getEmail();
		this.token = userVO.getToken();
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	@Override
	public String toString() {
		return "FriendRoom [id=" + id + ", email=" + email + ", token=" + token + ", requestIds=" + requestIds
				+ ", friendIds=" + friendIds + "]";
	}

}
