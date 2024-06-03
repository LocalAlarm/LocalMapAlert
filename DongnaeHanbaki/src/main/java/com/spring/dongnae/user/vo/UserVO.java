package com.spring.dongnae.user.vo;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
public class UserVO {
	private String email;
	private String password;
	private String address;
	private String nickname;
	private String image;
	private int kakaoCheck;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	
	public UserVO() {
		System.out.println(">> UserVO() 객체생성");
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getKakaoCheck() {
		return kakaoCheck;
	}

	public void setKakaoCheck(int kakaoCheck) {
		this.kakaoCheck = kakaoCheck;
	}
	
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

	@Override
	public String toString() {
		return "UserVO [email=" + email + ", password=" + password + ", address=" + address + ", nickname=" + nickname
				+ ", image=" + image + ", kakaoCheck=" + kakaoCheck + "]";
	}

	

	
	
}
