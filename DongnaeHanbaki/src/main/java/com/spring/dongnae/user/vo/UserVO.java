package com.spring.dongnae.user.vo;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
public class UserVO {
	private String email;
	private String password;
	private String nickname;
	private String address;
	private String detailAddress;
	private String recoverEmail;
	private String image;
	private int kakaoCheck;
<<<<<<< HEAD
	
//	private Collection<? extends GrantedAuthority> authorities;
	
=======
	// 상세주소, 복구이메일 추가 - 건희
>>>>>>> branch 'gun1' of https://github.com/LocalAlarm/LocalMapAlert.git
	
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
	
<<<<<<< HEAD

=======
>>>>>>> branch 'gun1' of https://github.com/LocalAlarm/LocalMapAlert.git
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
	
<<<<<<< HEAD
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
=======
	public String getRecoverEmail() {
		return recoverEmail;
	}

	public void setRecoverEmail(String recoverEmail) {
		this.recoverEmail = recoverEmail;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
>>>>>>> branch 'gun1' of https://github.com/LocalAlarm/LocalMapAlert.git

	@Override
	public String toString() {
		return "UserVO [email=" + email + ", password=" + password + ", nickname=" + nickname + ", address=" + address
				+ ", detailAddress=" + detailAddress + ", recoverEmail=" + recoverEmail + ", image=" + image
				+ ", kakaoCheck=" + kakaoCheck + "]";
	}
	
}
