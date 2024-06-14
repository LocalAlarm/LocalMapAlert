package com.spring.dongnae.user.dto;

public class UserCacheDTO {
	private String email;
	private String token;
	private String nickname;
	private String image;
	
    // Constructors, Getters, and Setters
    public UserCacheDTO() {}
    
    public UserCacheDTO(String email, String token, String nickname, String image) {
    	this.email = email;
    	this.token = token;
    	this.nickname = nickname;
    	this.image = image;
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
	@Override
	public String toString() {
		return "UserCacheVO [email=" + email + ", token=" + token + ", nickname=" + nickname + ", image=" + image + "]";
	}
	
}
