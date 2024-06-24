package com.spring.dongnae.socket.scheme;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.spring.dongnae.user.vo.UserVO;

@Document(collection = "userRooms")
public class UserRooms {

	@Id
	private String id;
	private String email;
	@DBRef(lazy = true)
    private List<Moim> moims;
	@DBRef(lazy = true)
    private List<Moim> masterMoims; // 유저가 master인 모임 리스트
	private List<String> requestIds; // 친구추가 요청받은 아이디(목록) 들
	private List<FriendInfo> friendIds;

	public UserRooms() {
		setInitValue();
	}
	
	public UserRooms(UserVO vo) {
		setInitValue();
		this.email = vo.getEmail();
	}
	
	public UserRooms(String email) {
		setInitValue();
		this.email = email;
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
	
	public List<Moim> getMoims() {
		return moims;
	}

	public void addMoim(Moim moim) {
		this.moims.add(moim);
	}
	
    public void removeMoim(Moim moim) {
        this.moims.remove(moim);
    }
	
    // 자기가 Master인 모임
    public void addMasterMoims(Moim moim) throws Exception {
        if (this.masterMoims.size() >= 4) {
            throw new Exception("모임은 4개까지 개설할 수 있어요!");
        }
        this.masterMoims.add(moim);
    }

    public List<Moim> getMasterMoims() {
    	return masterMoims;
    }
    
    public void removeMasterMoim(Moim moim) {
        this.masterMoims.remove(moim);
    }
	
    
	public List<String> getRequestIds() {
		return requestIds;
	}
	
    public void removeFriendRequest(String email) {
        this.requestIds.remove(email);
    }

	public void addFriendRequest(String email) throws Exception {
		if (requestIds.contains(email)) throw new Exception("already Request!");
		this.requestIds.add(email);
	}
	
	public List<FriendInfo> getFriendIds() {
		return friendIds;
	}
	
	public void addFriendId(FriendInfo friendInfo) {
		this.removeFriendRequest(friendInfo.getFriendToken());
		
		this.friendIds.add(friendInfo);
	}
	
    public void removeFriendId(FriendInfo friendInfo) {
        this.friendIds.remove(friendInfo);
    }
	
	private void setInitValue() {
		this.moims = new ArrayList<Moim>();
		this.masterMoims = new ArrayList<Moim>();
		this.requestIds = new ArrayList<String>();
		this.friendIds = new ArrayList<FriendInfo>();
	}

	@Override
	public String toString() {
		return "UserRooms [id=" + id + ", email=" + email + ", moims=" + moims + ", masterMoims=" + masterMoims
				+ ", requestIds=" + requestIds + ", friendIds=" + friendIds + "]";
	}
	
	


}
