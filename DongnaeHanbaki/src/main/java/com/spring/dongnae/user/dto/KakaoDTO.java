package com.spring.dongnae.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KakaoDTO {
	private String email;
	private String nickname;
	@JsonProperty("thumbnail_image_url")
    private String thumbnailImageUrl;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getThumbnailImageUrl() {
		return thumbnailImageUrl;
	}
	public void setThumbnailImageUrl(String thumbnailImageUrl) {
		this.thumbnailImageUrl = thumbnailImageUrl;
	}
	@Override
	public String toString() {
		return "kakaoDTO [email=" + email + ", nickname=" + nickname + ", thumbnailImageUrl=" + thumbnailImageUrl + "]";
	}

    
    
}
