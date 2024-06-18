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
	@DBRef
    private List<Moim> moims;

	public UserRooms() {
	}
	
	public UserRooms(UserVO vo) {
		this.email = vo.getEmail();
		this.moims = new ArrayList<Moim>();
	}
	
	public UserRooms(String email, String token) {
		this.email = email;
		this.moims = new ArrayList<Moim>();
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

	public void setMoims(List<Moim> moims) {
		this.moims = moims;
	}

	public void addChatRoom(Moim moim) {
		this.moims.add(moim);
	}

	@Override
	public String toString() {
		return "UserRooms [id=" + id + ", email=" + email + ", moims=" + moims + "]";
	}
	
	

}
