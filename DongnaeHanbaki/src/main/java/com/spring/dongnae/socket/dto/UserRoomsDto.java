package com.spring.dongnae.socket.dto;

import java.util.List;

import com.spring.dongnae.socket.scheme.FriendInfo;

public class UserRoomsDto {
    private String id;
    private String email;
    private List<MoimDto> moims;
    private List<MoimDto> masterMoims;
    private List<String> requestIds;
    private List<FriendInfo> friendIds;
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
	public List<MoimDto> getMoims() {
		return moims;
	}
	public void setMoims(List<MoimDto> moims) {
		this.moims = moims;
	}
	public List<MoimDto> getMasterMoims() {
		return masterMoims;
	}
	public void setMasterMoims(List<MoimDto> masterMoims) {
		this.masterMoims = masterMoims;
	}
	public List<String> getRequestIds() {
		return requestIds;
	}
	public void setRequestIds(List<String> requestIds) {
		this.requestIds = requestIds;
	}
	public List<FriendInfo> getFriendIds() {
		return friendIds;
	}
	public void setFriendIds(List<FriendInfo> friendIds) {
		this.friendIds = friendIds;
	}
	@Override
	public String toString() {
		return "UserRoomsDto [id=" + id + ", email=" + email + ", moims=" + moims + ", masterMoims=" + masterMoims
				+ ", requestIds=" + requestIds + ", friendIds=" + friendIds + "]";
	}

}
